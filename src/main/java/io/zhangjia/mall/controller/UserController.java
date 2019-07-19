package io.zhangjia.mall.controller;


import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.service.CommodityService;
import io.zhangjia.mall.service.MailCodeService;
import io.zhangjia.mall.service.PhoneCodeService;
import io.zhangjia.mall.service.UserService;
import io.zhangjia.mall.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.Mapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private MailCodeService mailCodeService;
    @Autowired
    private PhoneCodeService phoneCodeService;


    /**
     * 打开登录页
     *
     * @return
     */
    @GetMapping("/login")
    public String login() {
        return "login";
    }

    /**
     * 点击登录按钮进行登录
     *
     * @return
     */
    @PostMapping(value = "/login", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String doLogin(String userName, String userPassword, String uri, HttpSession session) {
        System.out.println("UserController.doLogin");
        Map<String, Object> map = userService.login(userName, userPassword);
        Map<String, Object> json = new HashMap<>();
        if (map.containsKey("user")) {
            session.setAttribute("user", map.get("user"));       //登录成功，将用户信息存入session
            json.put("result", true);
            if (uri != null) {
                json.put("uri", uri);//如果不是直接登录，将原地址存入uri
            }
        } else {
            Object error = map.get("error");
            json.put("result", false);
            json.put("error", error);
        }
        String s = JSON.toJSONString(json);
        System.out.println("doLogins = " + s);
        return s;
    }

    /**
     * 退出登录
     * @param session
     * @return
     */
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();//销毁session
        return "redirect:index"; //去登录页面
    }

    /**
     * 打开注册页     *
     * @return
     */
    @GetMapping("/register")
    public String register() {
        return "register";
    }

    /**
     * 点击注册按钮进行注册
     *
     * @return
     */
    @PostMapping(value = "/register", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String doRegister(String userPassword, String action, String code, HttpSession session, HttpServletRequest request) {

        Map<String, Object> json = new HashMap<>();
        Map<String, Object> user = new HashMap<>();


        if (action.equals("userTel")) {    //判断是不是手机号注册
            System.out.println("\"是手机号登录\" = " + "是手机号登录");
            boolean isTruePhoneCode = !code.equals(session.getAttribute("phoneCode"));// 判断输入的验证码是否正确
            if (isTruePhoneCode) {
                System.out.println("\"不正确\" = " + "是手机号登录");
                json.put("result", false);
                json.put("error", "验证码不正确");
                return JSON.toJSONString(json);
            } else {
                user.put("user_tel", request.getParameter(action));
            }
        }


        if (action.equals("userMail")) {// 判断是不是邮箱注册
            System.out.println("\"是Mail登录\" = " + "是Mail登录");
            boolean isTrueMailCode = !code.equals(session.getAttribute("mailCode"));  //判断输入的验证码是否正确
            if (isTrueMailCode) {
                System.out.println("\"不正确\" = " + "是Mail录");
                json.put("result", false);
                json.put("error", "验证码不正确");
                return JSON.toJSONString(json);
            } else {
                user.put("user_mail", request.getParameter(action));
            }
        }

        user.put("user_name", request.getParameter(action));
        user.put("user_password", userPassword);

        Map<String, Object> map = userService.register(user);

        /*如果注册成功*/
        if (map.containsKey("user")) {

            session.setAttribute("user", map.get("user"));
            json.put("result", true);
            System.out.println("UserController.doregister");
        } else {
            Object error = map.get("error");
            json.put("result", false);
            json.put("error", error);
        }
        return JSON.toJSONString(json);
    }

    /**
     * 获取邮件验证码
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/getMailCode", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getMailCode(HttpServletRequest request) {
        String mail = request.getParameter("type");
        System.out.println("mail = " + mail);
        String mailCode = mailCodeService.getMailCode(mail);
        request.getSession().setAttribute("mailCode", mailCode);

        boolean result = mailCode != null;
        return "{\"success\":" + result + "}";
    }

    /**
     * 获取手机
     *
     * @param request
     * @return
     */
    @GetMapping(value = "/getPhoneCode", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getPhoneCode(HttpServletRequest request) {
        String tel = request.getParameter("type");
        System.out.println("tel = " + tel);
        String phoneCode = phoneCodeService.getPhoneCode(tel);
        request.getSession().setAttribute("phoneCode", phoneCode);

        boolean result = phoneCode != null;
        return "{\"success\":" + result + "}";
    }

    /**
     * 修改用户信息
     */
    @PostMapping(value = "/editUserInfo", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String editUserInfo(String nickname, String gender,
                               String birthday, String tel, String mail,
                               String password, String payPassword,
                               HttpSession session) {
        System.out.println("nickname = [" + nickname + "], gender = [" + gender + "], birthday = [" + birthday + "], tel = [" + tel + "], mail = [" + mail + "], password = [" + password + "], payPassword = [" + payPassword);
        Map<String, Object> userBefore = (Map<String, Object>) session.getAttribute("user");

        Map<String, Object> userAfter = new HashMap<>();
        userAfter.put("user_id", userBefore.get("user_id"));
        userAfter.put("user_password", password);
        userAfter.put("user_pay_password", payPassword);
        userAfter.put("user_tel", tel);
        userAfter.put("user_mail", mail);
        userAfter.put("user_nick", nickname);
        userAfter.put("user_gender", gender);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        try {
            if (birthday != null) {
                Date date = sdf.parse(birthday);
                userAfter.put("user_birthday", date);
                System.out.println("时间" + userAfter.get("user_birthday"));
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("userAfter = " + userAfter);
        int i = userService.editUserInfo(userAfter);
//        更新session信息
        if (i == 1) {
            Map<String, Object> uid = new HashMap<>();
            uid.put("user_id", userAfter.get("user_id"));
            session.setAttribute("user", userService.getUser(uid));
        }
        return "{\"success\":" + (i == 1) + "}";
    }


    @GetMapping(value = "/isTrueUserName", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String isTrueUserName(String username, String action) {
        Map<String, Object> trueUsername = userService.isTrueUsername(username, action);
        System.out.println("JSON.toJSONString(trueUsername)" + JSON.toJSONString(trueUsername));
        return JSON.toJSONString(trueUsername);
    }


}
