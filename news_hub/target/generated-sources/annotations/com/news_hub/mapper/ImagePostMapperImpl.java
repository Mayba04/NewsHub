package com.news_hub.mapper;

import com.news_hub.dto.ImagePost.ImagePostItemDTO;
import com.news_hub.entities.ImagePostEntity;
import com.news_hub.entities.PostEntity;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-01T20:47:40+0300",
    comments = "version: 1.5.5.Final, compiler: Eclipse JDT (IDE) 3.37.0.v20240206-1609, environment: Java 17.0.10 (Eclipse Adoptium)"
)
@Component
public class ImagePostMapperImpl implements ImagePostMapper {

    @Override
    public ImagePostItemDTO toImagePostItemDTO(ImagePostEntity imagePostEntity) {
        if ( imagePostEntity == null ) {
            return null;
        }

        ImagePostItemDTO imagePostItemDTO = new ImagePostItemDTO();

        imagePostItemDTO.setPost_id( imagePostEntityPostId( imagePostEntity ) );
        imagePostItemDTO.setPost_title( imagePostEntityPostTitle( imagePostEntity ) );
        imagePostItemDTO.setDateCreated( imagePostEntity.getDateCreated() );
        imagePostItemDTO.setId( imagePostEntity.getId() );
        imagePostItemDTO.setName( imagePostEntity.getName() );

        return imagePostItemDTO;
    }

    private int imagePostEntityPostId(ImagePostEntity imagePostEntity) {
        if ( imagePostEntity == null ) {
            return 0;
        }
        PostEntity post = imagePostEntity.getPost();
        if ( post == null ) {
            return 0;
        }
        int id = post.getId();
        return id;
    }

    private String imagePostEntityPostTitle(ImagePostEntity imagePostEntity) {
        if ( imagePostEntity == null ) {
            return null;
        }
        PostEntity post = imagePostEntity.getPost();
        if ( post == null ) {
            return null;
        }
        String title = post.getTitle();
        if ( title == null ) {
            return null;
        }
        return title;
    }
}
