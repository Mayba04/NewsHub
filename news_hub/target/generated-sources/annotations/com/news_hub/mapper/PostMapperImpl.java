package com.news_hub.mapper;

import com.news_hub.dto.post.PostCreateDTO;
import com.news_hub.dto.post.PostItemDTO;
import com.news_hub.entities.CategoryEntity;
import com.news_hub.entities.PostEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-03T19:14:44+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240325-1403, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class PostMapperImpl implements PostMapper {

    @Override
    public PostItemDTO postItemDTO(PostEntity post) {
        if ( post == null ) {
            return null;
        }

        PostItemDTO postItemDTO = new PostItemDTO();

        postItemDTO.setCategory_name( postCategoryName( post ) );
        postItemDTO.setCategory_id( postCategoryId( post ) );
        postItemDTO.setDateCreated( post.getDateCreated() );
        postItemDTO.setDescription( post.getDescription() );
        postItemDTO.setId( post.getId() );
        postItemDTO.setModified( post.isModified() );
        postItemDTO.setPosted( post.isPosted() );
        postItemDTO.setPublished( post.isPublished() );
        postItemDTO.setShortDescription( post.getShortDescription() );
        postItemDTO.setTitle( post.getTitle() );

        return postItemDTO;
    }

    @Override
    public PostEntity postCreateDTO(PostCreateDTO postCreateDTO) {
        if ( postCreateDTO == null ) {
            return null;
        }

        PostEntity postEntity = new PostEntity();

        postEntity.setDescription( postCreateDTO.getDescription() );
        postEntity.setShortDescription( postCreateDTO.getShortDescription() );
        postEntity.setTitle( postCreateDTO.getTitle() );

        return postEntity;
    }

    private String postCategoryName(PostEntity postEntity) {
        if ( postEntity == null ) {
            return null;
        }
        CategoryEntity category = postEntity.getCategory();
        if ( category == null ) {
            return null;
        }
        String name = category.getName();
        if ( name == null ) {
            return null;
        }
        return name;
    }

    private int postCategoryId(PostEntity postEntity) {
        if ( postEntity == null ) {
            return 0;
        }
        CategoryEntity category = postEntity.getCategory();
        if ( category == null ) {
            return 0;
        }
        int id = category.getId();
        return id;
    }
}
