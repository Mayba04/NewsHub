package com.news_hub.controllers;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import com.news_hub.dto.post.PostCreateDTO;
import com.news_hub.dto.post.PostEditDTO;
import com.news_hub.dto.post.PostItemDTO;
import com.news_hub.dto.post.PostSearchDTO;
import com.news_hub.services.PostService;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@AllArgsConstructor
@RequestMapping("api/posts")
public class PostController {
    private final PostService postService;
    @GetMapping("/{id}")
    public ResponseEntity<PostItemDTO> getById(@PathVariable("id") int id) {
        var result = postService.getById(id);
        if (result == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<PostItemDTO>> GetAll() {
        List<PostItemDTO> result = postService.getAll();
        if (result.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<PostSearchDTO> searchByName(
            @RequestParam (defaultValue = "", name = "category")String category,
            @RequestParam (defaultValue = "", name = "tag")String tag,
            @RequestParam(defaultValue = "0", name = "page") int page,
            @RequestParam(defaultValue = "5", name = "size") int size) {
        try {
            Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());

            PostSearchDTO posts = postService.searchGetAllPost(category, tag, pageable);
            return new ResponseEntity<>(posts, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }


    @PostMapping(value = "", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> create(@Valid @ModelAttribute PostCreateDTO dto) {
        try {
            var result = postService.create(dto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @PutMapping
    public ResponseEntity<PostItemDTO> editPost(@RequestBody PostEditDTO dto) {
        try {
            var result = postService.editPost(dto);
            return new ResponseEntity<>(result, HttpStatus.OK);
        } catch (Exception ex) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable("id") int id) throws IOException {
        try {
            postService.deletePost(id);
            return new ResponseEntity<>("Success", HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>("Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}