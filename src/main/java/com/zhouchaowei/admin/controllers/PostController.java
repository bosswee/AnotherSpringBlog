package com.zhouchaowei.admin.controllers;

import com.zhouchaowei.forms.PostForm;
import com.zhouchaowei.models.Post;
import com.zhouchaowei.models.support.PostFormat;
import com.zhouchaowei.models.support.PostStatus;
import com.zhouchaowei.repositories.PostRepository;
import com.zhouchaowei.repositories.UserRepository;
import com.zhouchaowei.services.PostService;
import com.zhouchaowei.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * @author wee
 * @date 1/18/16.
 */

@Controller("adminPostController")
@RequestMapping("admin/posts")
public class PostController {

    private static final int PAGE_SIZE = 4;
    @Autowired
    private PostRepository postRepository;
    @Autowired
    private PostService postService;
    @Autowired
    private UserRepository userRepository;

    @RequestMapping(value = "")
    public String index(@RequestParam(defaultValue = "0") int page, Model model) {
        Page<Post> posts = postRepository.findAll(new PageRequest(page, PAGE_SIZE, Sort.Direction.DESC, "id"));

        model.addAttribute("totalPages", posts.getTotalPages());
        model.addAttribute("page", page);
        model.addAttribute("posts", posts);

        return "admin/posts/index";
    }

    @RequestMapping(value = "new")
    public String newPost(Model model) {
        PostForm postForm = DTOUtil.map(new Post(), PostForm.class);
        postForm.setPostTags("");

        model.addAttribute("postForm", postForm);
        model.addAttribute("postFormats", PostFormat.values());
        model.addAttribute("postStatus", PostStatus.values());

        return "admin/posts/new";
    }

    @RequestMapping(value = "{postId:[0-9]+}/edit")
    public String editPost(@PathVariable Long postId, Model model) {
        Post post = postRepository.findOne(postId);
        PostForm postForm = DTOUtil.map(post, PostForm.class);

        postForm.setPostTags(postService.getTagNames(post.getTags()));

        model.addAttribute("post", post);
        model.addAttribute("postForm", postForm);
        model.addAttribute("postFormats", PostFormat.values());
        model.addAttribute("postStatus", PostStatus.values());

        return "admin/posts/edit";
    }

    @RequestMapping(value = "{postId:[0-9]+}/delete", method = {DELETE, POST})
    public String deletePost(@PathVariable Long postId) {
        postService.deletePost(postRepository.findOne(postId));
        return "redirect:/admin/posts";
    }

    @RequestMapping(value = "", method = POST)
    public String create(Principal principal, @Valid PostForm postForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("postFormats", PostFormat.values());
            model.addAttribute("postStatus", PostStatus.values());

            return "admin/posts/new";
        } else {
            Post post = DTOUtil.map(postForm, Post.class);
            post.setUser(userRepository.findByEmail(principal.getName()));
            post.setTags(postService.parseTagNames(postForm.getPostTags()));

            postService.createPost(post);

            return "redirect:/admin/posts";
        }
    }

    @RequestMapping(value = "{postId:[0-9]+}", method = {PUT, POST})
    public String update(@PathVariable Long postId, @Valid PostForm postForm, Errors errors, Model model) {
        if (errors.hasErrors()) {
            model.addAttribute("postFormats", PostFormat.values());
            model.addAttribute("postStatus", PostStatus.values());

            return "admin/posts_edit";
        } else {
            Post post = postRepository.findOne(postId);
            DTOUtil.mapTo(postForm, post);
            post.setTags(postService.parseTagNames(postForm.getPostTags()));

            postService.updatePost(post);

            return "redirect:/admin/posts";
        }
    }


}
