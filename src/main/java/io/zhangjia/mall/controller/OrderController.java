package io.zhangjia.mall.controller;

import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.mapper.AddressMapper;
import io.zhangjia.mall.service.AddressService;
import io.zhangjia.mall.service.CarService;
import io.zhangjia.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class OrderController {
    @Autowired
    private AddressService addressService;

    @Autowired
    private CarService carService;


    @Autowired
    private OrderService orderService;

    @GetMapping("/settlement")
    public String settlement(Integer[] commoditySpecsId, HttpSession session, Model model) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = (Integer) user.get("user_id");

        System.out.println("commoditySKUIds = " + Arrays.toString(commoditySpecsId));
        List<Map<String, Object>> userAddress = addressService.getUserAddress(userId);
        System.out.println("userAddressjson = " + JSON.toJSONString(userAddress));
        List<Map<String, Object>> commoditySpecs= orderService.getCarCommodities4Settlement(userId,commoditySpecsId);
        System.out.println("commoditySKUsjson = " + JSON.toJSONString(commoditySpecs));

        Map<String, Object> total = orderService.getTotal(userId, commoditySpecsId);
        System.out.println("total = " + total);

        model.addAttribute("userAddress",userAddress);
        model.addAttribute("commoditySpecs",commoditySpecs);
        model.addAttribute("total",total);

        return "settlement";

    }


    @GetMapping(value = "/placeOrder", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String placeOrder(Integer[] commoditySpecsId,Integer addressId,String note,HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = (Integer) user.get("user_id");
        System.out.println("submitSKUIds = " + commoditySpecsId);
        System.out.println("addressIdsubmit = " + addressId);
        System.out.println("note = " + note);
        int submit = orderService.submit(userId,addressId,"未支付",note,commoditySpecsId);
        System.out.println("插入的订单记录ID是 = " + submit);
//        向session中插入订单Id
        session.setAttribute("orderId",submit);

        return "{\"success\":"+(submit != 0)+"}";
    }
}
