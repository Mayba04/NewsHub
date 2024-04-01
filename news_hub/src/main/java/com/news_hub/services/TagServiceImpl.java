package com.news_hub.services;

import lombok.AllArgsConstructor;
import com.news_hub.dto.tag.TagCreateDTO;
import com.news_hub.dto.tag.TagEditDTO;
import com.news_hub.dto.tag.TagItemDTO;
import com.news_hub.entities.TagEntity;
import com.news_hub.mapper.TagMapper;
import com.news_hub.repositories.TagRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;


@Service
@AllArgsConstructor
public class TagServiceImpl implements TagService{
    private final TagRepository tagRepository;
    private final TagMapper tagMapper;
    @Override
    public TagItemDTO getById(int id) {
        var result =  tagMapper.tagItemDTO(tagRepository.findById(id).orElse(null));
        return result;
    }

    @Override
    public List<TagItemDTO> getAll(Sort sort) {
        List<TagEntity> tags = tagRepository.findAll(sort);
        return tagMapper.tagListItemDTO(tags);
    }
    @Override
    public Page<TagItemDTO> getAllByPage(Pageable pageable) {
        Page<TagEntity> tags = tagRepository.findAll(pageable);
        return tags.map(tagMapper::tagItemDTO);
    }
    @Override
    public TagItemDTO create(TagCreateDTO dto) throws IOException {
        var entity = tagMapper.tagCreateDTO(dto);
        tagRepository.save(entity);
        return tagMapper.tagItemDTO(entity);
    }

    @Override
    public TagItemDTO editTag(TagEditDTO dto) throws IOException {
        var entity = tagRepository.findById(dto.getId()).orElse(null);
        entity.setName(dto.getName());
        tagRepository.save(entity);
        return tagMapper.tagItemDTO(entity);
    }

    @Override
    public void deleteTag(int id) throws IOException {
        var entity = tagRepository.findById(id).orElse(null);
        if (tagRepository.existsById(id)) {
            tagRepository.save(entity);
            tagRepository.deleteById(id);
        }
    }
}