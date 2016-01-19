package com.zhouchaowei.forms;

import com.zhouchaowei.models.support.PostFormat;
import com.zhouchaowei.models.support.PostStatus;
import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author wee
 * @date 1/18/16.
 */

@Data
public class PostForm {

    @NotEmpty
    private String title;

    @NotEmpty
    private String content;

    @NotNull
    private PostFormat postFormat;

    @NotNull
    private PostStatus postStatus;

    @NotNull
    private String permalink;

    @NotNull
    private String postTags;
}
