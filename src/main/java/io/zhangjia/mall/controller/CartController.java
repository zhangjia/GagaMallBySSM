package io.zhangjia.mall.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CartController {


    @GetMapping("/cart")
    public String cart(Integer userId, Model model){

        return "cart";
    }
}
