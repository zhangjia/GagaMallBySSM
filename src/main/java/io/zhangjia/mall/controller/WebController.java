package io.zhangjia.mall.controller;

import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {
    @Autowired
    private CommodityService commodityService;

    @GetMapping("/index")
    public String index(Model model){
        List<Map<String,Object>> commodities4Index = commodityService.commodities4Index(20);
//        req.getRequestDispatcher("proList.jsp").forward(req,resp);
        System.out.println("liuwenlu2" + JSON.toJSONString(commodities4Index));
        model.addAttribute("commodities4Index",commodities4Index);


        return "index";
    }
}
