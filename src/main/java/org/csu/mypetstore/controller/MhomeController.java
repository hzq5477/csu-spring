package org.csu.mypetstore.controller;

import org.csu.mypetstore.domain.Manager;

import org.csu.mypetstore.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/Mhome/")
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

    @GetMapping("personal")
    public String personal(){
        return "M_info/info";
    }

    @GetMapping("editPassword")
    public String editPassword(){
        return "M_info/password";
    }

    @GetMapping("editInfo")
    public String editInfo(){
        return "M_info/newInfo";
    }
}
