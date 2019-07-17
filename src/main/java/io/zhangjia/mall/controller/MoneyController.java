package io.zhangjia.mall.controller;


import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.service.IouService;
import io.zhangjia.mall.service.OrderService;
import io.zhangjia.mall.service.UserService;
import io.zhangjia.mall.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MoneyController {

    @Autowired
    private UserService userService;
    @Autowired
    private IouService iouService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private WalletService walletService;

    @GetMapping(value = "/pay", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String pay(String payType, Double payMoney, Integer orderId,
                      String payPassword,String location, HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());
        Map<String, Object> result = new HashMap<>();
        Map<String, Object> judgePayPassword = userService.judgePayPassword(userId, payPassword);
        if(judgePayPassword.containsKey("error")){
            result = judgePayPassword;
        } else {
            //        todo：bug，一直是上一个的session
            System.out.println("orderId11111111111111111111111111111 = " + orderId);

            if(("订单页").equals(location)){
                System.out.println("heihei" + orderService.getOrder(userId,orderId));
//                System.out.println("orderService.getOrder(user.getUserId()+\"\",orderId).get(\"ORDERPRICE\") = " + orderService.getOrder(userId,orderId).get("ORDERPRICE"));
//                System.out.println("orderService.getOrder(user.getUserId()+\"\",orderId).get(\"ORDERPRICE\") = " + orderService.getOrder(userId,orderId).get("ORDERPRICE").getClass());
                Double money = (Double)orderService.getOrder(userId,orderId).get("order_price");
                System.out.println("jiajiajiamoney = " + money);
                if(payType.equals("余额")){
                    result  = walletService.orderPayByBalance(userId, money,orderId);
                }

                if(payType.equals("白条")){
                    result  = iouService.orderPayByIou(userId, money,orderId);
                }

            } else {

                Double money = (Double)orderService.getOrder(userId, Integer.parseInt(session.getAttribute("order_id").toString())).get("order_price");
                System.out.println("直接下单页的orderId是 = " + session.getAttribute("order_id"));
                if(payType.equals("余额")){
                    result  = walletService.orderPayByBalance(userId, money, Integer.parseInt(session.getAttribute("order_id").toString()));
                }

                if(payType.equals("白条")){
                    result  = iouService.orderPayByIou(userId, money,Integer.parseInt(session.getAttribute("order_id").toString()));
                }

            }

        }

        System.out.println("JSON.toJSONString(result)" + JSON.toJSONString(result));

      return JSON.toJSONString(result);

    }

}
