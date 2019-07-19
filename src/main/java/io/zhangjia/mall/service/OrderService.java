package io.zhangjia.mall.service;

import java.util.List;
import java.util.Map;

public interface OrderService {

    /**
     * 查询用户的所有订单
     * @param userId
     * @return
     */
    List<Map<String, Object>> getOrders(Integer userId);

    /**
     * 查询用户的单个订单
     * @param userId
     * @param orderId
     * @return
     */
    Map<String, Object> getOrder(Integer userId, Integer orderId);

    /**
     * 提交订单
     * @param userId
     */
    int submit(Integer userId, Integer addressId,
               String orderPayType, String orderNote, Integer[] commoditySpecsId);

    /**
     * 获取下单的商品
     * @param userId
     * @param commoditySpecsId
     * @return
     */
    List<Map<String, Object>> getCarCommodities4Settlement(Integer userId,Integer[] commoditySpecsId);

    /**
     * 计算总价
     * @param userId
     * @param commoditySpecsId
     * @return
     */
    Map<String, Object> getTotal(Integer userId, Integer[] commoditySpecsId);
}
