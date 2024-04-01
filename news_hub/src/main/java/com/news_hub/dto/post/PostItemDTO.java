package com.news_hub.dto.post;

import lombok.Data;
import com.news_hub.dto.tag.TagItemDTO;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class PostItemDTO {
    private int id;
    private String title;
    private String shortDescription;
    private String description;
    private LocalDateTime dateCreated;
    private String category_name;
    private int category_id;
    private boolean isPublished;
    private boolean isPosted;
    private boolean isModified;
    private List<String> files = new ArrayList<>();
    private List<TagItemDTO> tags = new ArrayList<>();

}

