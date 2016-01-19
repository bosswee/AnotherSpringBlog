package com.zhouchaowei.admin.controllers;

import com.zhouchaowei.forms.SettingsForm;
import com.zhouchaowei.services.AppSetting;
import com.zhouchaowei.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;

/**
 * @author wee
 * @date 1/17/16.
 */

@Controller
@RequestMapping("admin")
public class AdminController {

    private AppSetting appSetting;

    @Autowired
    public AdminController(AppSetting appSetting) {
        this.appSetting = appSetting;
    }


    @RequestMapping("")
    public String index() {
        return "admin/home/index";
    }

    @RequestMapping(value = "settings")
    public String settings(Model model) {
        SettingsForm settingsForm = DTOUtil.map(appSetting, SettingsForm.class);

        model.addAttribute("settings", settingsForm);
        return "admin/home/settings";
    }

    @RequestMapping(value = "settings", method = RequestMethod.POST)
    public String updateSettings(@Valid SettingsForm settingsForm, Errors errors, Model model, RedirectAttributes ra) {
        if (errors.hasErrors()) {
            return "admin/settings";
        } else {
            appSetting.setSiteName(settingsForm.getSiteName());
            appSetting.setSiteSlogan(settingsForm.getSiteSlogan());
            appSetting.setPageSize(settingsForm.getPageSize());

          //  MessageHelper.addSuccessAttribute(ra, "Update settings successfully.");

            return "redirect:settings";
        }
    }






}
