package com.zhouchaowei.models;

import com.zhouchaowei.models.support.PostFormat;
import com.zhouchaowei.models.support.PostStatus;
import com.zhouchaowei.models.support.PostType;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wee
 * @date 12/29/15.
 */

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post extends BaseModel{

    @ManyToOne
    private User user;

    @Column(nullable = false)
    private String title;

    @Type(type = "text")
    private String content;

    @Type(type = "text")
    private String renderedContent;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus postStatus = PostStatus.PUBLISHED;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostFormat postFormat = PostFormat.MARKDOWN;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PostType postType = PostType.POST;

    @ManyToMany
    private Set<Tag> tags = new HashSet<Tag>();

    private String permalink;

    public String getRenderedContent() {
        if (this.postFormat == PostFormat.MARKDOWN)
            return renderedContent;

        return getContent();
    }

    public void setPermalink(String permalink) {
        String token = permalink.toLowerCase().replace("\n", " ").replaceAll("[^a-z\\d\\s]", " ");
        this.permalink = StringUtils.arrayToDelimitedString(StringUtils.tokenizeToStringArray(token, " "), "-");
    }




}
