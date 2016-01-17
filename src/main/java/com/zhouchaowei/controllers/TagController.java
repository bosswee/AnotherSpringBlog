package com.zhouchaowei.controllers;

import com.zhouchaowei.error.NotFoundException;
import com.zhouchaowei.models.Post;
import com.zhouchaowei.models.Tag;
import com.zhouchaowei.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

/**
 * @author wee
 * @date 1/4/16.
 */


@Controller
@RequestMapping("tags")
public class TagController {
    @Autowired
    private TagService tagService;

    @Autowired
    private PostService postService;

    @Autowired
    private AppSetting appSetting;


    @RequestMapping(value = "", method = GET)
    public String index(Model model) {

        List<Map<String, Long>> counts = postService.countPostsByTags();

        model.addAttribute("tags", counts);

        return "tags/index";
    }

    @RequestMapping(value = "{tagName}", method = GET)
    public String showTag(@PathVariable String tagName, @RequestParam(defaultValue = "1") int page, Model model) {
        Tag tag = tagService.getTag(tagName);

        if (tag == null) {
            throw new NotFoundException("Tag " + tagName + " is not found.");
        }

        page = page < 1 ? 0 : page - 1;
        Page<Post> posts = postService.findPostsByTag(tagName, page, appSetting.getPageSize());

        model.addAttribute("tag", tag);
        model.addAttribute("posts", posts);
        model.addAttribute("page", page + 1);
        model.addAttribute("totalPages", posts.getTotalPages());

        return "tags/show";
    }



}
