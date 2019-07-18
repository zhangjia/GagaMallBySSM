package io.zhangjia.mall.interceptor;

import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class MenuInterceptor implements HandlerInterceptor {
    @Autowired
    private MenuService menuService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        request.setAttribute("nav",menuService.getMenu());
        System.out.println("JSON.toJSONString(menuService.getMenu()) = " + JSON.toJSONString(menuService.getMenu()));
        return  true;
    }
}
