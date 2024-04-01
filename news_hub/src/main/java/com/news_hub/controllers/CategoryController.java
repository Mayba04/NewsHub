package com.news_hub.controllers;

import lombok.AllArgsConstructor;
import com.news_hub.dto.category.CategoryCreateDTO;
import com.news_hub.dto.category.CategoryEditDTO;
import com.news_hub.dto.category.CategoryItemDTO;
import com.news_hub.services.CategoryService;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.MediaType;
import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/categories")
public class CategoryController {
    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity <CategoryItemDTO> getById(@PathVariable int id) {
        var result = categoryService.getById(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
    @GetMapping()
    public ResponseEntity<List<CategoryItemDTO>> gtAll() {
        try {
            List<CategoryItemDTO> categories = categoryService.getAll(Sort.by("id"));
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @GetMapping("/search")
    public ResponseEntity<Page<CategoryItemDTO>> searchByName(@RequestParam (defaultValue = "")String name,
                                                              @RequestParam(defaultValue = "0") int page,
                                                              @RequestParam(defaultValue = "5") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());
            Page<CategoryItemDTO> categories = categoryService.getAllByName(name, pageable);
            return new ResponseEntity<>(categories, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<CategoryItemDTO> create(@ModelAttribute CategoryCreateDTO dto) {
        try {
            var result = categoryService.create(dto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<CategoryItemDTO> editCategory(@ModelAttribute CategoryEditDTO dto) {
        try {
            var result = categoryService.editCategory(dto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCategory(@PathVariable int id) throws IOException {
        try {
            categoryService.deleteCategory(id);
            return new ResponseEntity<>("Success", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}