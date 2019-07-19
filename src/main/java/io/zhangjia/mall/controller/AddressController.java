package io.zhangjia.mall.controller;


import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AddressController {
    @Autowired
    private AddressService addressService;

    @GetMapping(value = "/setDefaultAddress", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String setDefaultAddress(Integer addressId, HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());
        boolean result = (addressService.setDefaultAddress(addressId, userId) == 1);
        return "{\"success\":" + result + "}";
    }


    @GetMapping(value = "/addAddress", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String addAddress(String name, String postcode, String tel, String province,
                             String city, String district, String country,
                             String detailedAddress, Integer addressId, HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());
        Map<String, Object> params = new HashMap<>();
        params.put("user_id", userId);
        params.put("address_name", name);
        params.put("address_postcode", postcode);
        params.put("address_tel", tel);
        params.put("province", province);
        params.put("city", city);
        params.put("district", district);
        params.put("country", country);
        params.put("address_detail", detailedAddress);
        params.put("address_id", addressId);
        boolean result = addressService.addUserAddress(params);
        return "{\"success\":" + result + "}";
    }


    @GetMapping(value = "/getAddress", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String getAddress(Integer addressId) {
        Map<String, Object> address4Edit = addressService.getAddress4Edit(addressId);
        System.out.println("JSON.toJSONString(address4Edit) = " + JSON.toJSONString(address4Edit));
        return JSON.toJSONString(address4Edit);

    }


    @GetMapping(value = "/deleteAddress", produces = "application/json;charset=utf-8")
    @ResponseBody
    public String deleteAddress(Integer addressId, HttpSession session) {
        Map<String, Object> user = (Map<String, Object>) session.getAttribute("user");
        Integer userId = Integer.parseInt(user.get("user_id").toString());


        boolean result = (addressService.deleteAddress(addressId, userId) == 1);

        return "{\"success\":" + result + "}";
    }
}

