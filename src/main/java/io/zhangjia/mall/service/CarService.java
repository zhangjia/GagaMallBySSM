package io.zhangjia.mall.service;

import java.util.List;
import java.util.Map;

public interface CarService {
    /**
     * 获取用户购物车中的商品
     * @param userId
     * @return
     */
    List<Map<String, Object>> getCarCommodities(Integer userId);

    /**
     * 添加商品到购物车
     * @param userId
     * @param commoditySpecsId
     * @param commodityCount
     * @return
     */
    Map<String, Object> addCart(Integer userId, Integer commoditySpecsId, Integer commodityCount);

    /**
     * 删除购物车中的商品
     * @param userId
     * @param commoditySpecsId
     * @return
     */
    boolean deleteCart(Integer userId, Integer[] commoditySpecsId);

    /**
     * 在购物车页面更新购物车中商品的数量
     * 场景1：+1按钮
     * 场景2：-1按钮
     * 场景3：input输入数量
     * @param action
     * @param userId
     * @param commoditySpecsId
     * @param commodityCount
     * @return
     */
    Map<String, Object> updateCount(String action, Integer userId, Integer commoditySpecsId,Integer commodityCount);
//    List<Map<String, Object>> getCarCommodities4Settlement(String userId, String[] CommoditySKUIds);

}
