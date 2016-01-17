package com.zhouchaowei.forms;

import lombok.Data;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * @author wee
 * @date 1/17/16.
 */

@Data
public class SettingsForm {

    @NotEmpty
    @NotNull
    private String siteName;

    @NotNull
    private String siteSlogan;

    @NotNull
    private Integer pageSize;

}
