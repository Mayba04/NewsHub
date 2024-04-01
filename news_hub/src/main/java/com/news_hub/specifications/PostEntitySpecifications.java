package com.news_hub.specifications;


import com.news_hub.entities.PostEntity;
import org.springframework.data.jpa.domain.Specification;

public class PostEntitySpecifications {
    public static Specification<PostEntity> findByCategoryId(int categoryId) {
        return (root, query, cb) -> {
            //criteriaBuilder.
            if (categoryId == 0) {
                return cb.notEqual(root.get("category").get("id"), 0);
            } else {
                return cb.equal(root.get("category").get("id"), categoryId);
            }
        };
    }

    public static Specification<PostEntity> findByTag(int tagId) {
        return (root, query, cb) -> {
                query.distinct(true);
            if (tagId == 0) {
                return cb.notEqual(root.get("postTags").get("tag").get("id"), 0);
            } else {
                return cb.equal(root.get("postTags").get("tag").get("id"), tagId);
            }
        };
    }
    public static Specification<PostEntity> findByCategoryName(String name) {
        return (root, query, cb) -> {
        query.distinct(true);
            return cb.like(root.get("category").get("name"), "%"+name+"%");
        };
    }

    public static Specification<PostEntity> findByTagName(String name) {
        return (root, query, cb) -> {
            query.distinct(true);
            return cb.like(root.get("postTags").get("tag").get("name"), "%"+name+"%");
        };
    }
}
