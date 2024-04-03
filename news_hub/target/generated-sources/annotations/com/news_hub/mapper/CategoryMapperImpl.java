package com.news_hub.mapper;

import com.news_hub.dto.category.CategoryCreateDTO;
import com.news_hub.dto.category.CategoryItemDTO;
import com.news_hub.entities.CategoryEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-03T19:14:44+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240325-1403, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class CategoryMapperImpl implements CategoryMapper {

    @Override
    public CategoryItemDTO categoryItemDTO(CategoryEntity category) {
        if ( category == null ) {
            return null;
        }

        CategoryItemDTO categoryItemDTO = new CategoryItemDTO();

        categoryItemDTO.setDescription( category.getDescription() );
        categoryItemDTO.setId( category.getId() );
        categoryItemDTO.setName( category.getName() );

        return categoryItemDTO;
    }

    @Override
    public List<CategoryItemDTO> categoriesListItemDTO(List<CategoryEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<CategoryItemDTO> list1 = new ArrayList<CategoryItemDTO>( list.size() );
        for ( CategoryEntity categoryEntity : list ) {
            list1.add( categoryItemDTO( categoryEntity ) );
        }

        return list1;
    }

    @Override
    public CategoryEntity categoryCreateDTO(CategoryCreateDTO category) {
        if ( category == null ) {
            return null;
        }

        CategoryEntity categoryEntity = new CategoryEntity();

        categoryEntity.setDescription( category.getDescription() );
        categoryEntity.setName( category.getName() );

        return categoryEntity;
    }
}
