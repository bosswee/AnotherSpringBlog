package com.zhouchaowei.services;


import com.zhouchaowei.error.NotFoundException;
import com.zhouchaowei.models.Post;
import com.zhouchaowei.models.Tag;
import com.zhouchaowei.models.support.PostStatus;
import com.zhouchaowei.models.support.PostType;
import com.zhouchaowei.repositories.PostRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author wee
 * @date 1/3/16.
 */

@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    @Autowired
    private TagService tagService;

    @Autowired
    private UserService userService;


    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    public Post getPost(Long postId) {
        logger.debug("Get post " + postId);

        Post post = postRepository.findOne(postId);

        if (post == null) {
            throw new NotFoundException("Post with id " + postId + " is not found.");
        }

        return post;
    }


    //public Post createPost(Post post) {
    //    if (post.getPostFormat() == PostFormat.MARKDOWN) {
    //        post.setRenderedContent(Markdown.markdownToHtml(post.getContent()));
    //    }
    //
    //    return postRepository.save(post);
    //}


    //public Post updatePost(Post post) {
    //    if (post.getPostFormat() == PostFormat.MARKDOWN) {
    //        post.setRenderedContent(Markdown.markdownToHtml(post.getContent()));
    //    }
    //
    //    return postRepository.save(post);
    //}


    public void deletePost(Post post) {
        postRepository.delete(post);
    }

    public List<Post> getArchivePosts() {
        logger.debug("Get all archive posts from database.");

        Iterable<Post> posts = postRepository.findAllByPostTypeAndPostStatus(
                PostType.POST,
                PostStatus.PUBLISHED,
                new PageRequest(0, Integer.MAX_VALUE, Sort.Direction.DESC, "createdAt"));

        List<Post> cachedPosts = new ArrayList<>();
        posts.forEach(post -> cachedPosts.add(extractPostMeta(post)));

        return cachedPosts;
    }

    public List<Tag> getPostTags(Post post) {
        logger.debug("Get tags of post " + post.getId());

        List<Tag> tags = new ArrayList<>();

        // Load the post first. If not, when the post is cached before while the tags not,
        // then the LAZY loading of post tags will cause an initialization error because
        // of not hibernate connection session
        postRepository.findOne(post.getId()).getTags().forEach(tags::add);
        return tags;
    }


    private Post extractPostMeta(Post post) {
        Post archivePost = new Post();
        archivePost.setId(post.getId());
        archivePost.setTitle(post.getTitle());
        archivePost.setPermalink(post.getPermalink());
        archivePost.setCreatedAt(post.getCreatedAt());

        return archivePost;
    }

    public Page<Post> getAllPublishedPostsByPage(int page, int pageSize) {
        logger.debug("Get posts by page " + page);

        return postRepository.findAllByPostTypeAndPostStatus(
                PostType.POST,
                PostStatus.PUBLISHED,
                new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
    }

    //public Post createAboutPage() {
    //    logger.debug("Create default about pageff");
    //
    //    Post post = new Post();
    //    post.setTitle(Constants.ABOUT_PAGE_PERMALINK);
    //    post.setContent(Constants.ABOUT_PAGE_PERMALINK.toLowerCase() + "aaa");
    //    post.setPermalink(Constants.ABOUT_PAGE_PERMALINK);
    //    post.setUser(userService.getSuperUser());
    //    post.setPostFormat(PostFormat.MARKDOWN);
    //
    //    return createPost(post);
    //}

    public Set<Tag> parseTagNames(String tagNames) {
        Set<Tag> tags = new HashSet<>();

        if (tagNames != null && !tagNames.isEmpty()) {
            tagNames = tagNames.toLowerCase();
            String[] names = tagNames.split("\\s*,\\s*");
            for (String name : names) {
                tags.add(tagService.findOrCreateByName(name));
            }
        }

        return tags;
    }

    public String getTagNames(Set<Tag> tags) {
        if (tags == null || tags.isEmpty())
            return "";

        StringBuilder names = new StringBuilder();
        tags.forEach(tag -> names.append(tag.getName()).append(","));
        names.deleteCharAt(names.length() - 1);

        return names.toString();
    }


    public Page<Post> findPostsByTag(String tagName, int page, int pageSize) {
        return postRepository.findByTag(tagName, new PageRequest(page, pageSize, Sort.Direction.DESC, "createdAt"));
    }

    public List<Map<String, Long>> countPostsByTags() {
        logger.debug("Count posts group by tags.");

        return postRepository.countPostsByTags(PostStatus.PUBLISHED);
    }
}
