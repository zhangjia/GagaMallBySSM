package io.zhangjia.mall.controller;

import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

    @GetMapping("/review")
    public String review(HttpSession session, Model model) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());
        Map<String,Object> params = new HashMap<>();
        System.out.println();
        params.put("user_id",userId);
        List<Map<String, Object>> reviews = reviewService.queryReview(params);
        System.out.println("JSON.toJSONString(reviews) = " + JSON.toJSONString(reviews));
        model.addAttribute("reviews",reviews);

        return "personal-review";
    }


    @PostMapping(value = "/doReview", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String doReview(Integer orderDetailId,String anonymity,String content,
                           Integer description,Integer service,
                           Integer express,Integer orderId,
                           Integer reviewGrade,
                           Integer commoditySpecsId,
                           String[] reviewImgs,HttpSession session, Model model) {
        System.out.println("进入了doReview");
        System.out.println("anonymity = [" + anonymity + "], content = [" + content + "], description = [" + description + "], service = [" + service + "], express = [" + express + "], orderId = [" + orderId + "], reviewGrade = [" + reviewGrade + "], commoditySpecsId = [" + commoditySpecsId + "], reviewImgs = [" + reviewImgs + "], session = [" + session + "], model = [" + model + "]");
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());

        Integer integer = reviewService.addReview(orderDetailId,anonymity, content, description, service,
                express, orderId, userId,reviewGrade,  commoditySpecsId, reviewImgs);
        return "{\"success\":" + (integer == 1) + "}";
    }

}
