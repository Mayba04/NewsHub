package com.news_hub.mapper;

import com.news_hub.dto.tag.TagCreateDTO;
import com.news_hub.dto.tag.TagItemDTO;
import com.news_hub.entities.TagEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TagMapper {
    TagItemDTO tagItemDTO(TagEntity tag);
    List<TagItemDTO> tagListItemDTO (List<TagEntity> list);
    TagEntity tagCreateDTO(TagCreateDTO tag);
}
