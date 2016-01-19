package com.zhouchaowei.admin.controllers;

import com.zhouchaowei.forms.UserForm;
import com.zhouchaowei.models.User;
import com.zhouchaowei.repositories.UserRepository;
import com.zhouchaowei.services.UserService;
import com.zhouchaowei.support.web.MessageHelper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

/**
 * @author wee
 * @date 1/18/16.
 */

@Controller("adminUserController")
@RequestMapping("admin/users")
public class UserController {

    private UserService userService;
    private UserRepository userRepository;

    @RequestMapping("profile")
    public String profile(Model model) {
        model.addAttribute("user", userService.currentUser());

        return "admin/users/profile";
    }

    @RequestMapping(value = "{userId:[0-9]+}", method = POST)
    public String update(@PathVariable Long userId, @Valid UserForm userForm, Errors errors, RedirectAttributes ra) {
        User user = userRepository.findOne(userId);
        Assert.notNull(user);

        if (errors.hasErrors()) {
            // do something

            return "admin/users/profile";
        }

        if (!userForm.getNewPassword().isEmpty()) {

            if (!userService.changePassword(user, userForm.getPassword(), userForm.getNewPassword()))
                MessageHelper.addErrorAttribute(ra, "Change password failed.");
            else
                MessageHelper.addSuccessAttribute(ra, "Change password successfully.");

        }

        return "redirect:profile";
    }


}
