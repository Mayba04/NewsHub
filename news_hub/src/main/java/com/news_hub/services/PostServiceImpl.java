package com.news_hub.services;



import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import com.news_hub.dto.ImagePost.ImagePostDTO;
import com.news_hub.dto.post.PostCreateDTO;
import com.news_hub.dto.post.PostEditDTO;
import com.news_hub.dto.post.PostItemDTO;
import com.news_hub.dto.post.PostSearchDTO;
import com.news_hub.dto.tag.TagItemDTO;
import com.news_hub.entities.*;
import com.news_hub.mapper.ImagePostMapper;
import com.news_hub.mapper.PostMapper;
import com.news_hub.mapper.TagMapper;
import com.news_hub.repositories.*;
import com.news_hub.storage.FileSaveFormat;
import com.news_hub.storage.StorageService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.news_hub.specifications.PostEntitySpecifications.*;

@Service
@AllArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;
    private final ImagePostRepository postImageRepository;
    private final PostTagRepository postTagRepository;
    private final TagRepository tagRepository;
    private final ImagePostMapper postImageMapper;
    private final CategoryRepository categoryRepository;
    private final StorageService storageService;
    private final PostMapper postMapper;
    private final TagMapper tagMapper;
    @Override
    public PostItemDTO getById(int id) {
        var postEntity =  postRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Post not found with id: " + id));

        PostItemDTO postItemDTO = postMapper.postItemDTO(postEntity);

        postItemDTO.setDateCreated(postEntity.getDateCreated());

        List<TagItemDTO> tagItemDTOs = postEntity.getPostTags().stream()
                .map(postTagMapEntity -> tagMapper.tagItemDTO(postTagMapEntity.getTag()))
                .collect(Collectors.toList());
        postItemDTO.setTags(tagItemDTOs);

        List<String> imageNames = postImageRepository.findImageNamesByPost(postEntity);
        postItemDTO.setFiles(imageNames);

        var items = new ArrayList<String>();
        for (var img : postEntity.getPostImages()) {
            items.add(img.getName());
        }
        postItemDTO.setFiles(items);

        return postItemDTO;
    }

    @Override
    public List<PostItemDTO> getAll() {
        List<PostEntity> postEntities = postRepository.findAll(); // Retrieves all posts
        List<PostItemDTO> postItemDTOs = postEntities.stream().map(postEntity -> {
            PostItemDTO postItemDTO = postMapper.postItemDTO(postEntity);

            postItemDTO.setDateCreated(postEntity.getDateCreated());

            List<TagItemDTO> tagItemDTOs = postEntity.getPostTags().stream()
                    .map(postTagMapEntity -> tagMapper.tagItemDTO(postTagMapEntity.getTag()))
                    .collect(Collectors.toList());
            postItemDTO.setTags(tagItemDTOs);

            List<String> imageNames = postImageRepository.findImageNamesByPost(postEntity);
            postItemDTO.setFiles(imageNames);

            var items = new ArrayList<String>();
            for (var img : postEntity.getPostImages()) {
                items.add(img.getName());
            }
            postItemDTO.setFiles(items);

            return postItemDTO;
        }).collect(Collectors.toList());

        return postItemDTOs;
    }


    @Override
    public PostSearchDTO searchGetAllPost(String category, String tag, Pageable pageable) {
        Page<PostEntity> result = postRepository
                .findAll(findByCategoryName(category).and(findByTagName(tag)),
                       pageable);

        List<PostItemDTO> posts = result.getContent().stream()
                .map(post -> {
                    PostItemDTO postItemDTO = postMapper.postItemDTO(post);

                    postItemDTO.setDateCreated(post.getDateCreated());

                    List<String> imageNames = postImageRepository.findImageNamesByPost(post);
                    postItemDTO.setFiles(imageNames);

                    List<TagEntity> tags = post.getPostTags().stream()
                            .map(PostTagMapEntity::getTag)
                            .collect(Collectors.toList());
                    List<TagItemDTO> tagItemDTOs = tags.stream()
                            .map(tagMapper::tagItemDTO)
                            .collect(Collectors.toList());
                    postItemDTO.setTags(tagItemDTOs);

                    return postItemDTO;
                })
                .collect(Collectors.toList());

        return new PostSearchDTO(posts, (int) result.getTotalElements());
    }

    @Override
    public PostItemDTO create(PostCreateDTO model) {
        CategoryEntity category = categoryRepository.findById(model.getCategory_id())
                .orElseThrow(() -> new EntityNotFoundException("Category not found"));

        var post = postMapper.postCreateDTO(model);
        post.setDateCreated(LocalDateTime.now());
        post.setPublished(true);
        post.setPosted(true);
        post.setCategory(category);
        postRepository.save(post);

        for (var tag : model.getTags()) {
            try {
                TagEntity tagEntity = tagRepository.findById(tag)
                        .orElseThrow(() -> new EntityNotFoundException("Tag not found"));

                PostTagMapEntity postTagEntity = new PostTagMapEntity();
                postTagEntity.setPost(post);
                postTagEntity.setTag(tagEntity);
                postTagRepository.save(postTagEntity);
            }
            catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }


        for (var img : model.getFiles()) {
            try {
                var file = storageService.SaveImage(img, FileSaveFormat.WEBP);
                ImagePostEntity imageEntity = new ImagePostEntity();
                imageEntity.setName(file);
                imageEntity.setDateCreated(LocalDateTime.now());
                imageEntity.setPost(post);
                postImageRepository.save(imageEntity);
            } catch (Exception ex) {
                ex.printStackTrace(); 
            }
        }
        return postMapper.postItemDTO(post);
    }

    @Override
    public PostItemDTO editPost(PostEditDTO model) throws IOException {
        var postRep = postRepository.findById(model.getId());
        if (postRep.isPresent()) {
            try {
                var post = postRep.get();

                var cat = new CategoryEntity();
                cat.setId(model.getCategory_id());
                post.setTitle(model.getTitle());
                post.setDescription(model.getDescription());
                post.setShortDescription(model.getShortDescription());
                post.setCategory(cat);

                postRepository.save(post);
                var imagesDb = post.getPostImages();

                for (var image : imagesDb) {
                     if (!isAnyImage(model.getOldPhotos(), image)) {
                            postImageRepository.delete(image);
                            storageService.deleteImage(image.getName());
                     }
                }
                for (var img : model.getNewPhotos()) {
                    var file = storageService.SaveImageBase64(img.getName(), FileSaveFormat.WEBP);
                    ImagePostEntity pi = new ImagePostEntity();
                    pi.setName(file);
                    pi.setDateCreated(LocalDateTime.now());
                    pi.setPost(post);
                    postImageRepository.save(pi);
                }

                List<Integer> tagIdsToKeep = new ArrayList<>(model.getTags());

                List<PostTagMapEntity> postTags = post.getPostTags();

                for (var postTag : postTags) {
                    if (!tagIdsToKeep.contains(postTag.getTag().getId())) {
                        // Видалення зв'язку з бази даних
                        postTagRepository.delete(postTag);
                    } else {
                        tagIdsToKeep.remove(postTag.getTag().getId());
                    }
                }

                for (Integer tagId : tagIdsToKeep) {
                    TagEntity tagEntity = tagRepository.findById(tagId)
                            .orElseThrow(() -> new EntityNotFoundException("Tag not found"));

                    PostTagMapEntity postTagEntity = new PostTagMapEntity();
                    postTagEntity.setPost(post);
                    postTagEntity.setTag(tagEntity);
                    postTagRepository.save(postTagEntity);
                }

            } catch (Exception ex) {
                System.out.println("Edit product is problem " + ex.getMessage());
            }


        }
        return null;
    }

    @Override
    public void deletePost(int id) throws IOException {

        var entity = postRepository.findById(id).orElse(null);
        if (entity != null) {

            List<PostTagMapEntity> postTagMapEntities = postTagRepository.findByPost(entity);
            for(var item : postTagMapEntities)
            {
                var postTagMapEntity = new PostTagMapEntity();

                var tag = new TagEntity();
                tag.setId(item.getTag().getId());

                var post = new PostEntity();
                post.setId(item.getPost().getId());

                postTagMapEntity.setTag(tag);
                postTagMapEntity.setPost(post);

                postTagRepository.delete(postTagMapEntity);
            }
            postRepository.deleteById(id);

        }
    }

    private boolean isAnyImage(List<ImagePostDTO> list, ImagePostEntity image) {
        boolean result = false;

        for(var item : list) {
            if (item.getName().equals(image.getName()))
                return true;
        }
        return result;
    }

    
   
}