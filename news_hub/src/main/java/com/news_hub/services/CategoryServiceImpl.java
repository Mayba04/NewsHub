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
import com.news_hub.repositories.PostRepository;
import com.news_hub.storage.FileSaveFormat;
import com.news_hub.storage.StorageService;

import jakarta.persistence.EntityNotFoundException;

import com.news_hub.mapper.CategoryMapper;

import java.io.IOException;
import java.util.List;
@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final CategoryMapper categoryMapper;
    private final CategoryRepository categoryRepository;
    private final PostRepository postRepository;
    private final StorageService storageService;

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
          // Перед збереженням категорії зберігаємо зображення
        if (dto.getFile() != null && !dto.getFile().isEmpty()) 
        {
            String fileName = storageService.SaveImage(dto.getFile(), FileSaveFormat.WEBP);
            entity.setImage(fileName);
        }
        categoryRepository.save(entity);
        return categoryMapper.categoryItemDTO(entity);
    }

    @Override
    public CategoryItemDTO editCategory(CategoryEditDTO dto) throws IOException {
        // Знаходимо існуючий запис категорії в базі даних.
        CategoryEntity existingEntity = categoryRepository.findById(dto.getId())
            .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + dto.getId()));

        // Оновлюємо поля сутності даними з DTO.
        existingEntity.setName(dto.getName());
        existingEntity.setDescription(dto.getDescription());

        // Якщо передано файл з зображенням, обробляємо його.
        if (dto.getFile() != null && !dto.getFile().isEmpty()) {

            if (existingEntity.getImage() != null && !existingEntity.getImage().isEmpty()) {
                storageService.deleteImage(existingEntity.getImage());
            }

            String newFileName = storageService.SaveImage(dto.getFile(), FileSaveFormat.WEBP);
            existingEntity.setImage(newFileName);
        }

        // Зберігаємо оновлену сутність в базі даних.
        CategoryEntity savedEntity = categoryRepository.save(existingEntity);

        // Перетворюємо оновлену сутність на DTO.
        return categoryMapper.categoryItemDTO(savedEntity);
    }


    @Override
    public void deleteCategory(int id) {
        CategoryEntity category = categoryRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Category not found with id: " + id));
        
        if (category.getImage() != null && !category.getImage().isEmpty()) {
            try {
                storageService.delete(category.getImage());
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("Error when deleting category images", e);
            }
        }
        categoryRepository.delete(category);
    }
}
