package com.news_hub.dto.category;

import org.springframework.web.multipart.MultipartFile;

import lombok.Data;
@Data
public class CategoryCreateDTO {
    private String name;
    private MultipartFile file;
    private String description;
}