package io.zhangjia.mall.service;

import java.util.List;
import java.util.Map;

public interface DeliverGoodsService {
    List<Map<String,Object>> getUnShippedOrders();
    int deliverGoods(Integer orderId, String logistic);
}
