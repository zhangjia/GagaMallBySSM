package io.zhangjia.mall.controller;




import com.alibaba.fastjson.JSON;
import com.github.pagehelper.Page;
import io.zhangjia.mall.entity.Commodity;
import io.zhangjia.mall.service.impl.CommodityServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


import java.util.List;


@Controller
public class CommodityController {
    @Autowired
    private CommodityServiceImpl commodityServiceImpl;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    @GetMapping("/list")
    public String list(Integer commodityId, String commodityName,
                       Integer level1MenuId, Integer level2MenuId,
                       Integer order,  Model model,
                       @RequestParam(required = false,defaultValue = "1") Integer page){
        /*List<Map<String, Object>> commodities = commodityServiceImpl.query(commodityId,
                commodityName, level1MenuId,level2MenuId,order);*/
        List<Commodity> commodities = commodityServiceImpl.query(commodityId,
                commodityName, level1MenuId,level2MenuId,order,page);
        model.addAttribute("commodities",commodities);
        System.out.println("commodities = " + commodities);
        System.out.println("commoditiesClass = " + commodities.getClass());
        if(commodities instanceof Page){
            Page productPage = (Page) commodities;
            System.out.println("CommodityController.list" + JSON.toJSONString(productPage));
            //当前页数
            int pageNum = productPage.getPageNum();
            //总页数
            int pages = productPage.getPages();
            model.addAttribute("pageNum",pageNum);
            model.addAttribute("pages",pages);
        }
        return "list";
    }
}