package com.news_hub.services;

import com.news_hub.dto.post.PostCreateDTO;
import com.news_hub.dto.post.PostEditDTO;
import com.news_hub.dto.post.PostItemDTO;
import com.news_hub.dto.post.PostSearchDTO;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface PostService {
    PostItemDTO getById(int id);
    public List<PostItemDTO> getAll();
    PostSearchDTO searchGetAllPost(String category, String tag, Pageable pageable);
    PostItemDTO create(PostCreateDTO model);
    PostItemDTO editPost(PostEditDTO dto) throws IOException;
    void deletePost(int id) throws IOException;
}
