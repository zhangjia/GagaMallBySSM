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
    public String settlement(Integer[] commoditySpecsId, String type,Integer commodityCount,HttpSession session, Model model) {
        System.out.println("commoditySpecsId = [" + commoditySpecsId + "], type = [" + type + "], session = [" + session + "], model = [" + model + "]");
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());

        System.out.println("commoditySKUIds = " + Arrays.toString(commoditySpecsId));
        List<Map<String, Object>> userAddress = addressService.getUserAddress(userId);
        System.out.println("userAddressjson = " + JSON.toJSONString(userAddress));
        List<Map<String, Object>> commoditySpecs = orderService.getCarCommodities4Settlement(userId, commoditySpecsId);
        List<Map<String, Object>> commoditySpecsByBuyNow = orderService.getCarCommodities4SettlementByBuyNow(userId, commoditySpecsId);
        System.out.println("commoditySKUsjson = " + JSON.toJSONString(commoditySpecs));

        Map<String, Object> total = orderService.getTotal(userId, commoditySpecsId);
        Map<String, Object> totalByBuyNow = orderService.getTotalByBuyNow(userId, commoditySpecsId, commodityCount);
        System.out.println("JSON.toJSONString(totalByBuyNow) = " + JSON.toJSONString(totalByBuyNow));
        System.out.println("total = " + total);


        model.addAttribute("userAddress", userAddress);


        if(("buyNow").equals(type)) { //如果是直接购买
            model.addAttribute("commoditySpecs", commoditySpecsByBuyNow);
            model.addAttribute("total", totalByBuyNow);
        } else { //如果是从购物车下单
        model.addAttribute("total", total);
            model.addAttribute("commoditySpecs", commoditySpecs);
        }

        return "settlement";

    }


    @GetMapping(value = "/placeOrder", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String placeOrder(Integer[] commoditySpecsId, Integer addressId, String note, String type,Integer commodityCount, HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());
        System.out.println("submitSKUIds = " + commoditySpecsId);
        System.out.println("addressIdsubmit = " + addressId);
        System.out.println("note = " + note);
        Integer orderId;
        if("buyNow".equals(type)) {
            System.out.println("直接购买下单");
            orderId  = orderService.submit(userId, addressId, "未支付", note, commoditySpecsId,commodityCount);

        } else {
            System.out.println("普通购买下单");
             orderId = orderService.submit(userId, addressId, "未支付", note, commoditySpecsId,null);
        }
        System.out.println("插入的订单记录ID是 = " + orderId);
//        向session中插入订单Id
        session.setAttribute("order_id", orderId);

        return "{\"success\":" + (orderId != 0) + "}";
    }


    @GetMapping("/orders")

    public String orders(HttpSession session,Model model) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());
        List<Map<String, Object>> orders = orderService.getOrders(userId);
        System.out.println("OrderController.orders" + JSON.toJSONString(orders));
        model.addAttribute("orders",orders);
        return "personal-orders";
    }

    @GetMapping("/orderDetail")
    public String orderDetail(HttpSession session,Integer orderId,Model model) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());
        System.out.println("user = " + user);
        System.out.println("orderId = " + orderId);
        Map<String, Object> order = orderService.getOrder(userId,orderId);

        System.out.println("JSON.toJSONString(order) = " + JSON.toJSONString(order));
        model.addAttribute("order",order);
        return "personal-order-detail";
    }



    @GetMapping(value = "/doReceive", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String doReceive(Integer orderId,HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());
        Integer integer = orderService.doReceive(orderId, userId);
        return "{\"success\":" + (integer== 1) + "}";
    }




}
