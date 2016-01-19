package com.zhouchaowei.forms;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author wee
 * @date 1/18/16.
 */

@Data
public class UserForm {

    @NotNull
    private String password;

    @NotNull
    private String newPassword;


}
