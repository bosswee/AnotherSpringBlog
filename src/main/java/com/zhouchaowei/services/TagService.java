package com.zhouchaowei.services;

import com.zhouchaowei.models.*;
import com.zhouchaowei.repositories.*;
import org.slf4j.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

/**
 * @author wee
 * @date 1/3/16.
 */
@Service
public class TagService {
    private TagRepository tagRepository;

    private static final Logger logger = LoggerFactory.getLogger(PostService.class);

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public Tag findOrCreateByName(String name) {
        Tag tag = tagRepository.findByName(name);
        if (tag == null) {
            tag = tagRepository.save(new Tag(name));
        }
        return tag;
    }

    public Tag getTag(String tagName) {
        return tagRepository.findByName(tagName);
    }

    public void deleteTag(Tag tag) {
        tagRepository.delete(tag);
    }

    public List<Tag> getAllTags() {
        return tagRepository.findAll();
    }


}
