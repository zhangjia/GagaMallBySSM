package io.zhangjia.mall.controller;




import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import io.zhangjia.mall.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import java.util.List;
import java.util.Map;


@Controller
public class CommodityController {
    @Autowired
    private CommodityService commodityService;

    @GetMapping("/index")
    public String index(){
        return "index";
    }

    /**
     * 查询商品
     * @param commodityId 根据ID查询单个商品
     * @param commodityName 根据名字模糊查询
     * @param level1MenuId 根据一级菜单查询
     * @param level2MenuId 根据二级菜单查询
     * @param order 对查询结果进行排序
     * @param model
     * @param page 查询第几页的数据
     * @return
     */
    @GetMapping("/commodities")
    public String commodities(Integer commodityId, String commodityName,
                       Integer level1MenuId, Integer level2MenuId,
                       Integer order,  Model model,
                       @RequestParam(required = false,defaultValue = "1") Integer page){

        List<Map<String,Object>> commodities = commodityService.query(commodityId,
                commodityName, level1MenuId,level2MenuId,order,page);
        model.addAttribute("commodities",commodities);
//        System.out.println("commodities = " + commodities);
//        System.out.println("commoditiesClass = " + commodities.getClass());
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
        return "commodities";
    }


    @GetMapping("/commodityDetail")

    public String commodityDetail(Integer commodityId,Model model) {
        List<Map<String,Object>> commodityDetail = commodityService.query(commodityId, null, null, null, null, null);
        System.out.println("CommodityController.commodityDetail" + JSON.toJSONString(commodityDetail));
        model.addAttribute("commodityDetail",commodityDetail.get(0));
        String commodityAttribute = (String)commodityDetail.get(0).get("commodity_attribute");
        JSONObject specs = JSON.parseObject(commodityAttribute);
        model.addAttribute("specs",specs);
        System.out.println("specs = " + specs);
        return "commodity-detail";
//        return "test";


    }

    @PostMapping(value = "/queryCommoditySpecs",produces = "application/json;charset=utf-8")
    @ResponseBody //如果不添加该注解，那么将会是json字符串.jsp

    public String queryCommoditySpecs(String specs) {

        Map<String,Object>  maps = commodityService.getCommoditySpecs(specs);
        String s = JSON.toJSONString(maps);
        System.out.println("s = " + s);
        return s;

    }
}