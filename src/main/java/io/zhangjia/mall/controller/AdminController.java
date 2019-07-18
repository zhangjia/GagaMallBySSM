package io.zhangjia.mall.controller;

import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.service.DeliverGoodsService;
import io.zhangjia.mall.service.OrderService;
import io.zhangjia.mall.service.SiteSettingsService;
import io.zhangjia.mall.service.impl.DeliverGoodsServiceImpl;
import io.zhangjia.mall.service.impl.OrderServiceImpl;
import io.zhangjia.mall.service.impl.SiteSettingsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private DeliverGoodsService deliverGoodsService;

    @Autowired
    private SiteSettingsService siteSettingsService;

//    @GetMapping("/admin")
//    public String admin(HttpSession session, Model model) {
//        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
//        if (Integer.parseInt(user.get("user_status").toString()) != 2) {
//            return "index";
//        } else {
//            return "back-stage";
//        }
//    }


//    @GetMapping(value = "/deliverGoods", produces = "application/json;charset=utf-8")
//    @ResponseBody
//    public String deliverGoods(HttpSession session, Model model) {
//        List<Map<String, Object>> orders = deliverGoodsService.getUnShippedOrders();
//        model.addAttribute("orders", orders);
//        System.out.println("orders-json" + JSON.toJSONString(orders));
//        return "{\"result\":true}";
//    }


    @GetMapping("/admin")
    public String admin(HttpSession session, Model model) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        List<Map<String, Object>> orders = deliverGoodsService.getUnShippedOrders();
        model.addAttribute("orders", orders);
        System.out.println("orders-json" + JSON.toJSONString(orders));
        if (Integer.parseInt(user.get("user_status").toString()) != 2) {
            return "index";
        } else {
            return "back-stage";
        }
    }

    @GetMapping(value = "/setPageSize", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String setPageSize(String pageSize) {

        int result = siteSettingsService.editValue("分页", pageSize);
        if (result == 1) {
            System.out.println(" 78787878= ");

        }
        return "{\"success\":" + (result == 1) + "}";
    }

    @GetMapping(value = "/deliverGoods", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deliverGoods(String logistics, Integer orderId) {

        int i = deliverGoodsService.deliverGoods(orderId, logistics);
        System.out.println(" = 剪辑");
        return ("{\"success\":" + (i == 1) + "}");


    }

}
