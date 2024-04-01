package com.news_hub.repositories;

import com.news_hub.entities.PostEntity;
import com.news_hub.entities.ImagePostEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ImagePostRepository extends JpaRepository<ImagePostEntity, Long> {

    ImagePostEntity findByName(String name);

    @Query("SELECT pi.name FROM ImagePostEntity pi WHERE pi.post = :post")
    List<String> findImageNamesByPost(@Param("post") PostEntity post);
}
