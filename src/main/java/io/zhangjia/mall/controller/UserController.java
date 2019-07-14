package io.zhangjia.mall.controller;


import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;


@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {

        return  "login";
    }

    @PostMapping( value = "/login",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String doLogin(String userName, String userPassword, Model model, HttpSession session) {
        System.out.println("UserController.doLogin");
        Map<String, Object> map = userService.login(userName, userPassword);
        Map<String,Object> json = new HashMap<>();
        if(map.containsKey("user")){
            //登录成功，将用户信息存入session
            session.setAttribute("user",map.get("user"));
            json.put("result",true);
            //不过不是直接登录，将原地址存入uri
           /* if(uri != null){
                json.put("uri",uri);
            }*/
        }else{
            Object error = map.get("error");
            json.put("result",false);
            json.put("error",error);
        }

        String s = JSON.toJSONString(json);
        System.out.println("doLogins = " + s);
        return s;
    }


    @GetMapping("/personal")

    public String personal() {
        return "personal";
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        //销毁session
        session.invalidate();
        //去登录页面
       return "redirect:index";
    }
}
