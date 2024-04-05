package com.news_hub.mapper;

import com.news_hub.dto.tag.TagCreateDTO;
import com.news_hub.dto.tag.TagItemDTO;
import com.news_hub.entities.TagEntity;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-05T21:26:51+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.38.0.v20240325-1403, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class TagMapperImpl implements TagMapper {

    @Override
    public TagItemDTO tagItemDTO(TagEntity tag) {
        if ( tag == null ) {
            return null;
        }

        TagItemDTO tagItemDTO = new TagItemDTO();

        tagItemDTO.setId( tag.getId() );
        tagItemDTO.setName( tag.getName() );

        return tagItemDTO;
    }

    @Override
    public List<TagItemDTO> tagListItemDTO(List<TagEntity> list) {
        if ( list == null ) {
            return null;
        }

        List<TagItemDTO> list1 = new ArrayList<TagItemDTO>( list.size() );
        for ( TagEntity tagEntity : list ) {
            list1.add( tagItemDTO( tagEntity ) );
        }

        return list1;
    }

    @Override
    public TagEntity tagCreateDTO(TagCreateDTO tag) {
        if ( tag == null ) {
            return null;
        }

        TagEntity tagEntity = new TagEntity();

        tagEntity.setName( tag.getName() );

        return tagEntity;
    }
}
