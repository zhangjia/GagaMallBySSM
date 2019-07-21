package io.zhangjia.mall.controller;

import io.zhangjia.mall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

@Controller
public class ReviewController {
    @Autowired
    private ReviewService reviewService;

}
