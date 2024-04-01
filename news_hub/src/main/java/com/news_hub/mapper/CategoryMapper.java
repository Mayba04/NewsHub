package com.news_hub.mapper;

import com.news_hub.dto.category.CategoryCreateDTO;
import com.news_hub.dto.category.CategoryItemDTO;
import com.news_hub.entities.CategoryEntity;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CategoryMapper {
    CategoryItemDTO categoryItemDTO(CategoryEntity category);
    List<CategoryItemDTO> categoriesListItemDTO (List<CategoryEntity> list);
    CategoryEntity categoryCreateDTO(CategoryCreateDTO category);
}
