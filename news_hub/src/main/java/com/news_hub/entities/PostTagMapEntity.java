package com.news_hub.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="tbl_post_tag_map")
@IdClass(PostTagPK.class)
public class PostTagMapEntity {
    @Id
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private PostEntity post;

    @Id
    @ManyToOne
    @JoinColumn(name = "tag_id", nullable = false)
    private TagEntity tag;
}