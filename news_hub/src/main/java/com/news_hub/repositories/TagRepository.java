package com.news_hub.repositories;

import com.news_hub.entities.TagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

@Repository
public interface TagRepository extends JpaRepository<TagEntity, Integer> {
    TagEntity findByName(String name);
    Page<TagEntity> findAll(Pageable pageable);

}