package io.zhangjia.mall.controller;

import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.api.oss.OssAli;
import io.zhangjia.mall.service.CommodityService;
import io.zhangjia.mall.service.UploadImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class WebController {
    @Autowired
    private CommodityService commodityService;
    @Autowired
    private UploadImgService uploadImgService;

    @GetMapping("/index")
    public String index(Model model){
        List<Map<String,Object>> commodities4Index = commodityService.commodities4Index(20);
//        req.getRequestDispatcher("proList.jsp").forward(req,resp);
        System.out.println("liuwenlu2" + JSON.toJSONString(commodities4Index));
        model.addAttribute("commodities4Index",commodities4Index);


        return "index";
    }

    @GetMapping("/test2")
    public String test2(MultipartFile file){

//        System.out.println(file.getName());
        return "test";
    }

    @PostMapping(value = "/upload", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String upload(MultipartFile file){

        Map<String,Object> result = new HashMap<>();
        String url = uploadImgService.uploadImg(file);
        System.out.println("gag" + url);
        if(url!= null) {

            result.put("success",true);
            result.put("url",url);
        }

        System.out.println("jsonrr" + JSON.toJSONString(result));
        return JSON.toJSONString(result);
    }
}
