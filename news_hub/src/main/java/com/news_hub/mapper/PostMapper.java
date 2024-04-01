package com.news_hub.mapper;

import com.news_hub.dto.post.PostCreateDTO;
import com.news_hub.dto.post.PostItemDTO;
import com.news_hub.entities.PostEntity;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PostMapper {

    @Mapping(source = "category.name", target = "category_name")
    @Mapping(source = "category.id", target = "category_id")
    PostItemDTO postItemDTO(PostEntity post);
    PostEntity postCreateDTO(PostCreateDTO postCreateDTO);
}
