package io.zhangjia.mall.service;

import java.util.List;
import java.util.Map;

public interface OrderService {
    List<Map<String, Object>> getOrders(Integer userId);

    Map<String, Object> getOrder(Integer userId, Integer orderId);

//    boolean submit(String userId, String addressId, String logistics,
//                   String orderPayType, String orderNote, String[] SKUIds);
    int submit(Integer userId, Integer addressId,
               String orderPayType, String orderNote, Integer[] commoditySpecsId);
    List<Map<String, Object>> getCarCommodities4Settlement(Integer userId,Integer[] commoditySpecsId);


    Map<String, Object> getTotal(Integer userId, Integer[] commoditySpecsId);
}
