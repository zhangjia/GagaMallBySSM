package io.zhangjia.mall.interceptor;

import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class AddressInterceptor implements HandlerInterceptor {

    @Autowired
    private AddressService addressService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());
        List<Map<String, Object>> userAddress = addressService.getUserAddress(userId);
        System.out.println("userAddress2345 = " + JSON.toJSONString(userAddress));
        request.setAttribute("userAddress", userAddress);
        return true;
    }

}
