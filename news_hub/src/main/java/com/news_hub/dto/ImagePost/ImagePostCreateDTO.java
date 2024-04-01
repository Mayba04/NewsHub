package com.news_hub.dto.ImagePost;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
@Data
public class ImagePostCreateDTO {
    private MultipartFile file;
}
