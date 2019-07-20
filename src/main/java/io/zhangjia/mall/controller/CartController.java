package io.zhangjia.mall.controller;

import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.service.CarService;
import io.zhangjia.mall.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;


@Controller
public class CartController {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private CarService carService;

    @GetMapping("/cart")
    public String cart(Model model, HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        System.out.println("user = " + user);
        Integer userId = Integer.parseInt(user.get("user_id").toString());
        System.out.println("userId22 = " + userId);
        List<Map<String, Object>> commodities = carService.getCarCommodities(userId);
        System.out.println("commodities" + JSON.toJSONString(commodities));
        model.addAttribute("commodities", commodities);

        return "cart";
    }


    @GetMapping(value = "/addCart", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String addCart(String commoditySpecsValue,
                          Integer commodityCount, Integer commodityId,HttpSession session) {
//        HttpSession session = request.getSession();
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());

        //先根据json字符串判断是哪个sku表中的商品，并获取该商品的ID
        Map<String, Object> commoditySpecs = commodityService.getCommoditySpecs(commoditySpecsValue,commodityId);


        Integer commoditySpecsId = (Integer) commoditySpecs.get("commodity_specs_id");
        Map<String, Object> map = carService.addCart(userId, commoditySpecsId, commodityCount);

        return (JSON.toJSONString(map));


//        writer.close();
    }

    @GetMapping(value = "/updateCount", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updateCount(String action, Integer commoditySpecsId, Integer commodityCount, HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());

        System.out.println("action = [" + action + "], commoditySpecsId = [" + commoditySpecsId + "], commodityCount = [" + commodityCount + "], userId = [" + userId + "]");

        Map<String, Object> stringObjectMap = carService.updateCount(action, userId, commoditySpecsId, commodityCount);
        String s = JSON.toJSONString(stringObjectMap);
        System.out.println("gaga" + s);
        return s;

    }

    @GetMapping(value = "/cart/delete", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String updateCount(Integer[] commoditySpecsId, HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());
        boolean b = carService.deleteCart(userId, commoditySpecsId);
        return ("{\"success\":" + b + "}");

    }


}
