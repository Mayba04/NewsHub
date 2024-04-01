package com.news_hub.dto.tag;

import lombok.Data;
import com.news_hub.dto.post.PostItemDTO;
import java.util.List;

@Data
public class TagItemDTO {
    private int id;
    private String name;
    private List<PostItemDTO> posts;
}
