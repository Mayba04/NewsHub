package com.news_hub.dto.post;

import lombok.Data;
import java.util.List;

@Data
public class PostSearchDTO {
    private List<PostItemDTO> list;
    private int totalCount;

    public PostSearchDTO(List<PostItemDTO> list, int totalCount) {
        this.list = list;
        this.totalCount = totalCount;
    }
}