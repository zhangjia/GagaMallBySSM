package io.zhangjia.mall.interceptor;

import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Component
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        System.out.println("进入了LoginInterceptor.preHandle");
        if (session.getAttribute("user") == null) {
            System.out.println("session.user == null ");
            String requestURI = request.getRequestURI();
            //获取请求参数（查询字符串）
            String queryString = request.getQueryString();
            System.out.println("queryString = " + queryString);
            System.out.println("requestURI = " + requestURI);

//            if (requestURI.endsWith("delete") || requestURI.endsWith("saveOrUpdate")) {
            if (isAjax(request)) {
                //返回json字符串
                response.setContentType("application/json;charset=utf-8");
                PrintWriter writer = response.getWriter();
                Map<String, Object> map = new HashMap<>();
                map.put("isLogin", false);
                writer.println(JSON.toJSONString(map));
                writer.close();
                //这里用不用再放行
            } else {
                //如果不是ajax
                //没有登录，去登录
                if(queryString != null){
                    requestURI += ("?" + queryString);
                }
                System.out.println("gagaurl = " + request.getContextPath() + "/login?uri=" + requestURI);
                response.sendRedirect(request.getContextPath() + "/login?uri=" + requestURI);
            }
            return false;
        } else {
            //已经登录，放行
            return true;
        }


    }

    private boolean isAjax (HttpServletRequest request){
        String header = request.getHeader("X-Requested-With");
        if (header != null && header.equals("XMLHttpRequest")) {
            return true;
        }
        return false;
    }
}
