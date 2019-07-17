package io.zhangjia.mall.controller;

import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.mapper.WalletMapper;
import io.zhangjia.mall.service.IouService;
import io.zhangjia.mall.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Map;

@Controller
public class PersonalController {
    @Autowired
    private WalletService walletService;

    @Autowired
    private IouService iouService;

    @GetMapping("/personal")
    public String personal() {
        return "personal";
    }

    @GetMapping("/addressManage")
    public String addressManage() {
        return "personal-address-manage";
    }


    @GetMapping("/userInfo")
    public String userInfo() {
        return "personal-info";
    }

    @GetMapping("/wallet")
    public String wallet() {
        return "personal-wallet";
    }

    @GetMapping("/orders")

    public String orders() {
        return "personal-orders";
    }

    @GetMapping("/order-detail")
    public String orderDetail() {
        return "personal-order-detail";
    }


    @PostMapping(value = "/wallet",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String doWallet(HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = (Integer) user.get("user_id");
        Map<String, Object> userWallet = walletService.getUserWallet(userId);
        System.out.println("PersonalController.doWallet" + JSON.toJSONString(userWallet));
        return JSON.toJSONString(userWallet);

    }

    @GetMapping(value = "/iou",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String doIou(HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = (Integer) user.get("user_id");
        Map<String, Object> stringObjectMap = iouService.queryUserIou(userId);
        if(stringObjectMap != null){
            stringObjectMap.put("success",true);
            System.out.println("PersonalController.doIou" + JSON.toJSONString(stringObjectMap));
            return (JSON.toJSONString(stringObjectMap));
        }
        return "{\"error\":null}";
    }

    @GetMapping(value = "/recharge",produces = "application/json;charset=utf-8")
    @ResponseBody
    public String doIou(Double money, HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = (Integer) user.get("user_id");


        int recharge = walletService.recharge(userId, money);
        return "{\"success\":"+(recharge == 1)+"}";

    }


}
