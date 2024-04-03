package com.news_hub.controllers;


import lombok.AllArgsConstructor;

import com.news_hub.dto.post.PostEditDTO;
import com.news_hub.dto.post.PostItemDTO;
import com.news_hub.dto.tag.TagCreateDTO;
import com.news_hub.dto.tag.TagEditDTO;
import com.news_hub.dto.tag.TagItemDTO;
import com.news_hub.services.CategoryService;
import com.news_hub.services.TagService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.PageRequest;

import java.io.IOException;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("api/tags")
public class TagController {
    private final TagService tagService;

    private final CategoryService categoryService;

    @GetMapping("/{id}")
    public ResponseEntity<TagItemDTO> getById(@PathVariable("id") int id) {
        var result = tagService.getById(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping()
    public ResponseEntity<List<TagItemDTO>> getAll() {
        try {
            List<TagItemDTO> tags = tagService.getAll(Sort.by("id"));
            return new ResponseEntity<>(tags, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/byPage")
    public ResponseEntity<Page<TagItemDTO>> getAllByPage(@RequestParam(value = "page", defaultValue = "0") int page,
                                                         @RequestParam(value = "size", defaultValue = "10") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").ascending());

            Page<TagItemDTO> tags = tagService.getAllByPage(pageable);
            return new ResponseEntity<>(tags, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<TagItemDTO> create(@ModelAttribute TagCreateDTO dto) {
        try {
            var result = tagService.create(dto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    


    @PutMapping("/{id}")
    public ResponseEntity<TagItemDTO> editTag(@RequestBody TagEditDTO dto) {
        try {
            var result = tagService.editTag(dto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") int id) throws IOException {
        try {
            tagService.deleteTag(id);
            return new ResponseEntity<>("Success", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}