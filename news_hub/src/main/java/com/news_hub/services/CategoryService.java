package com.news_hub.services;

import com.news_hub.dto.category.CategoryCreateDTO;
import com.news_hub.dto.category.CategoryEditDTO;
import com.news_hub.dto.category.CategoryItemDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.io.IOException;
import java.util.List;

public interface CategoryService {
    CategoryItemDTO getById(int id);
    List<CategoryItemDTO> getAll(Sort sort);
    Page<CategoryItemDTO> getAllByName(String name, Pageable pageable);
    CategoryItemDTO create(CategoryCreateDTO dto) throws IOException;
    CategoryItemDTO editCategory(CategoryEditDTO dto) throws IOException;
    void deleteCategory(int id) throws IOException;
}
