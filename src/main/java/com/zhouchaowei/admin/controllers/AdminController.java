package com.zhouchaowei.admin.controllers;

import com.zhouchaowei.forms.SettingsForm;
import com.zhouchaowei.services.AppSetting;
import com.zhouchaowei.utils.DTOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

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


}
