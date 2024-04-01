package com.news_hub.services;


import com.news_hub.dto.tag.TagCreateDTO;
import com.news_hub.dto.tag.TagEditDTO;
import com.news_hub.dto.tag.TagItemDTO;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.List;

public interface TagService {
    TagItemDTO getById(int id);
    List<TagItemDTO> getAll(Sort sort);
    Page<TagItemDTO> getAllByPage(Pageable pageable);
    TagItemDTO create(TagCreateDTO dto) throws IOException;
    TagItemDTO editTag(TagEditDTO dto) throws IOException;
    void deleteTag(int id) throws IOException;
}
