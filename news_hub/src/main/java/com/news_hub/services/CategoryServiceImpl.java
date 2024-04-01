package com.news_hub.services;


import lombok.AllArgsConstructor;

import com.news_hub.dto.category.CategoryCreateDTO;
import com.news_hub.dto.category.CategoryEditDTO;
import com.news_hub.dto.category.CategoryItemDTO;
import com.news_hub.entities.CategoryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import com.news_hub.repositories.CategoryRepository;
import com.news_hub.mapper.CategoryMapper;

import java.io.IOException;
import java.util.List;
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public CategoryItemDTO getById(int id){
        var result =  categoryMapper.categoryItemDTO(categoryRepository.findById(id).orElse(null));
        return result;
    }
    @Override
    public List<CategoryItemDTO> getAll(Sort sort) {
        List<CategoryEntity> categories = categoryRepository.findAll(sort);
        return categoryMapper.categoriesListItemDTO(categories);
    }
    @Override
    public Page<CategoryItemDTO> getAllByName(String name, Pageable pageable) {
        Page<CategoryEntity> categories = categoryRepository.findByNameContainingIgnoreCase(name, pageable);
        return categories.map(categoryMapper::categoryItemDTO);
    }
    @Override
    public CategoryItemDTO create(CategoryCreateDTO dto) throws IOException {
        var entity = categoryMapper.categoryCreateDTO(dto);
        categoryRepository.save(entity);
        return categoryMapper.categoryItemDTO(entity);
    }
    @Override
    public CategoryItemDTO editCategory(CategoryEditDTO dto) throws IOException {
        if (!categoryRepository.existsById(dto.getId())) {
            return null;
        }

        var entity = categoryRepository.findById(dto.getId()).orElse(null);
        if (entity == null) {
            return null;
        }

        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());

        categoryRepository.save(entity);
        return categoryMapper.categoryItemDTO(entity);
    }
    @Override
    public void deleteCategory(int id) throws IOException {
        // Перевіряємо, чи сутність існує перед її видаленням
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new IOException("Category not found with id: " + id);
        }
    }
}
