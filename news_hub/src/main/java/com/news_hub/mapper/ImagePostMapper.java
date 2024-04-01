package com.news_hub.mapper;

import com.news_hub.dto.ImagePost.ImagePostItemDTO;
import com.news_hub.entities.ImagePostEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ImagePostMapper {

    @Mapping(source = "post.id", target = "post_id")
    @Mapping(source = "post.title", target = "post_title")
    ImagePostItemDTO toImagePostItemDTO(ImagePostEntity imagePostEntity);
}
