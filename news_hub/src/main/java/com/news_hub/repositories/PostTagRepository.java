package com.news_hub.repositories;

import com.news_hub.entities.PostEntity;
import com.news_hub.entities.PostTagMapEntity;
import com.news_hub.entities.PostTagPK;
import com.news_hub.entities.TagEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostTagRepository extends JpaRepository<PostTagMapEntity, PostTagPK> {
    List<PostTagMapEntity> findByPost(PostEntity post);
    List<PostTagMapEntity> findByTag(TagEntity tag);
    List<PostTagMapEntity> findByPostIn(List<PostEntity> posts);
    void deleteByPost(PostEntity post);

}