package com.news_hub.dto.ImagePost;

import lombok.Data;

import java.time.LocalDateTime;
@Data
public class ImagePostItemDTO 
{
    private int id;
    private String name;
    private LocalDateTime dateCreated;
    private String post_title;
    private int post_id;    
}
