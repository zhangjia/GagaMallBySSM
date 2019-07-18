package io.zhangjia.mall.service.impl;


import io.zhangjia.mall.mapper.OrderMapper;
import io.zhangjia.mall.service.DeliverGoodsService;
import io.zhangjia.mall.service.OrderService;
import io.zhangjia.mall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service("deliverGoodsService")
public class DeliverGoodsServiceImpl implements DeliverGoodsService {
    @Autowired
    private OrderService orderService;
    @Autowired
    private UserService userService;
    @Autowired
    private OrderMapper orderMapper;
    @Override
    public List<Map<String, Object>> getUnShippedOrders() {
        List<Map<String,Object>> users = userService.queryUserAll();
        List<Map<String, Object>> unShippedOrders = new ArrayList<>();
        for (Map<String,Object> user: users  ) {
            List<Map<String, Object>> orders = orderService.getOrders(Integer.parseInt(user.get("user_id").toString()));
            for (Map<String,Object> order: orders ) {
                Integer orderStatus = Integer.parseInt(order.get("order_status").toString());
                if((orderStatus) == 1){
                    unShippedOrders.add(order);
                }
            }

        }
        return unShippedOrders;
    }

    @Override
    public int deliverGoods(Integer orderId, String logistic) {
        System.out.println("123orderId = " + orderId);
        System.out.println("123orderId = " + logistic);
        int i = orderMapper.doUpdateByDeliverGoods(orderId, logistic);
        System.out.println("i = " + i);
        return  i;
    }
}
