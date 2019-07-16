package io.zhangjia.mall.service;

import java.util.List;
import java.util.Map;

public interface CarService {
    List<Map<String, Object>> getCarCommodities(Integer userId);
    Map<String, Object> addCart(Integer userId, Integer commoditySpecsId, Integer commodityCount);
    boolean deleteCart(Integer userId, Integer[] commoditySpecsId);
    Map<String, Object> updateCount(String action, Integer userId, Integer commoditySpecsId,Integer commodityCount);
//    List<Map<String, Object>> getCarCommodities4Settlement(String userId, String[] CommoditySKUIds);

}
