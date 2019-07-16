package io.zhangjia.mall.controller;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Arrays;
import java.util.List;

@Controller
public class TestController {

    @GetMapping("/zhangjia")
    public String test(int[] a,String b) {
        System.out.println("b" + b);
        System.out.println("a" + Arrays.toString(a));
        return "index";

    }
}
