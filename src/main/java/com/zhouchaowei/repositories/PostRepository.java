package com.zhouchaowei.repositories;

import com.zhouchaowei.models.Post;
import com.zhouchaowei.models.support.PostStatus;
import com.zhouchaowei.models.support.PostType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author wee
 * @date 12/31/15.
 */

@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long>{


    Post findByPermalinkAndPostStatus(String permalink, PostStatus postStatus);

    Page<Post> findAllByPostType(PostType postType, Pageable pageRequest);

    Page<Post> findAllByPostTypeAndPostStatus(PostType postType, PostStatus postStatus, Pageable pageRequest);

    @Query("SELECT p FROM Post p INNER JOIN p.tags t WHERE t.name = :tag")
    Page<Post> findByTag(@Param("tag") String tag, Pageable pageable);

    @Query("SELECT t.name, count(p) as tag_count from Post p " +
            "INNER JOIN p.tags t " +
            "WHERE p.postStatus = :status " +
            "GROUP BY t.id " +
            "ORDER BY tag_count DESC")
    List<Map<String, Long>> countPostsByTags(@Param("status") PostStatus status);


}
