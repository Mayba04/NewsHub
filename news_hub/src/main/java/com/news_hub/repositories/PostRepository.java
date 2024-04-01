package com.news_hub.repositories;

import com.news_hub.entities.PostEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<PostEntity, Integer>, JpaSpecificationExecutor<PostEntity> {

    @Query("SELECT p FROM PostEntity p WHERE LOWER(p.category.name) LIKE LOWER(:category) " +
            "AND LOWER(p.postTags) LIKE LOWER(:tag)")
    Page<PostEntity> searchPosts(
            @Param("category") String category,
            @Param("tag") String tag,
            Pageable pageable);
}