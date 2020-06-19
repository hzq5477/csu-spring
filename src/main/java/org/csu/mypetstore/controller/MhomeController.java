package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Manager;
import org.csu.mypetstore.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/Mhome/")
@SessionAttributes({"manager"})
public class MhomeController {
    @Autowired
    private ManagerService managerService;

    @GetMapping("login")
    public String login() {
        return "M_home/login";
    }

    @PostMapping("signon")
    public String signon(String username, String password, Model model) {
        Manager manager = managerService.getManager(username, password);

        if (manager == null) {
            String msg = "Invalid username or password.  Signon failed.";
            model.addAttribute("msg", msg);
            return "M_home/login";
        } else {
            manager.setPassword(null);
            model.addAttribute("manager", manager);
            return "M_home/home";
        }
    }

    //个人信息
    @GetMapping("personal")
    public String personal() {
        return "M_info/info";
    }

    //账号管理
    @GetMapping("editPasswordForm")
    public String editPasswordForm() {
        return "M_info/password";
    }

    //修改个人信息
    @GetMapping("editInfoForm")
    public String editInfoForm() {
        return "M_info/newInfo";
    }

    //修改密码
    @PostMapping("editPassword")
    public String editPassword(Manager manager, String repeatedPassword, Model model) {
        if (manager.getPassword() == null || manager.getPassword().length() == 0 || repeatedPassword == null || repeatedPassword.length() == 0) {
            String msg = "密码不能为空！";
            model.addAttribute("msg", msg);
            return "M_info/password";
        } else if (!manager.getPassword().equals(repeatedPassword)) {
            String msg = "两次密码不一致！";
            model.addAttribute("msg", msg);
            return "M_info/password";
        } else {
            managerService.updatePassword(manager);
            manager = managerService.getManager(manager.getPassword());
            model.addAttribute("manager", manager);
            String msg = "修改成功！";
            model.addAttribute("msg", msg);
            return "redirect:/Mhome/login";
        }
    }

    @PostMapping("editInfo")
    public String editInfo(Model model, String username, String birthday, String sex, String duty) {
        if (username == null || birthday == null || sex == null || duty == null) {
            String msg = "输入不能为空！";
            model.addAttribute("msg",msg);
            return "M_info/newInfo";
        } else {
            managerService.updateInfo(username, birthday, sex, duty);
            String msg = "修改成功！";
            model.addAttribute("msg",msg);
            return "M_info/newInfo";
        }
    }

    @GetMapping("windows")
    public String windows(){
        return "M_home/windows";
    }
}
