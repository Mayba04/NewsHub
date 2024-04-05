package com.news_hub.dto.category;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;

@Data
public class CategoryEditDTO {
    private int id;
    private String name;
    private String description;
    private MultipartFile file;
}
