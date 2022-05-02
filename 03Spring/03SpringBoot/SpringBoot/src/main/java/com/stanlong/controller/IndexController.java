package com.stanlong.controller;

import com.stanlong.bean.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpSession;

@Controller
public class IndexController {

    /**
     * 访问登录页
     */
    @GetMapping(value = {"/", "/login"})
    public String loginPage(){
        return "login";
    }

    /**
     * 登录主页, 登录成功后重定向到 mainPage， 防止刷新页面时表单重复提交
     */
    @PostMapping("/login")
    public String index(User user, HttpSession httpSession, Model model){
        if(user != null && user.getUserName() != null && user.getPassword() != null ){
            httpSession.setAttribute("loginUser", user);
            return "redirect:main.html";
        }else {
            model.addAttribute("msg","用户名或密码错误");
            // 回到登录页
            return "login";
        }

    }

    @GetMapping("/main.html")
    public String mainPage(HttpSession httpSession, Model model){
        Object loginUser = httpSession.getAttribute("loginUser");
        if(loginUser != null){ // 如果登录的用户存在于会话中，可以打开主页面。
            return "main";
        }else {
            model.addAttribute("msg" , "请重新登录");
            return "login";
        }

    }
}
