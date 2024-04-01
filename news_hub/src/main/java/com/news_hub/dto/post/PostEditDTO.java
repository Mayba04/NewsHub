package com.news_hub.dto.post;

import lombok.Data;

import com.news_hub.dto.ImagePost.ImagePostDTO;

import java.util.ArrayList;
import java.util.List;
@Data
public class PostEditDTO {
    private int id;
    private String title;
    private String shortDescription;
    private String description;
    private String category;
    private int category_id;
    private List<ImagePostDTO> oldPhotos;
    private List<ImagePostDTO> newPhotos;
    private List<Integer> tags = new ArrayList<>();
}
