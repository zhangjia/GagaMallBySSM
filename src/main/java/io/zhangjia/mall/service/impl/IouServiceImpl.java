package io.zhangjia.mall.service.impl;


import io.zhangjia.mall.mapper.IouMapper;
import io.zhangjia.mall.mapper.OrderMapper;
import io.zhangjia.mall.service.IouService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service("iouService")
public class IouServiceImpl implements IouService {
    @Autowired
    private IouMapper iouMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Map<String, Object> queryUserIou(Integer userId) {
        if (userId != null && !"".equals(userId)) {
            return iouMapper.queryByUserId(userId);
        }
        return null;
    }

    @Override
    public Map<String, Object> orderPayByIou(Integer userId, Double money, Integer orderId) {
        System.out.println(" orderPayByIou");
        Map<String, Object> result = new HashMap<>();
        if (userId != null && !"".equals(userId) && money != null && !"".equals(money) && orderId != null && !"".equals(orderId)) {

            Map<String, Object> balance = iouMapper.queryByUserId(userId);
            System.out.println("gaggagastringObjectMap = " + balance);
//            Double iouBalance =((BigDecimal)stringObjectMap.get("Iou_USABLE_LIMIT")).doubleValue();
            Double iouBalance = (Double) balance.get("iou_usable_limit");
            System.out.println("iouBalance = " + iouBalance);
            System.out.println("iouBalance = " + iouBalance);

            if (iouBalance < money) {
                result.put("error", "额度不足");
            } else {
                int i = iouMapper.doUpdate(userId, money * -1);
                if (i == 1) {
                    result.put("success", "支付成功");
                    orderMapper.doUpdateByPay("白条支付", userId, orderId);
                } else {
                    result.put("error", "支付失败");
                }
            }

        } else {
            result.put("error", "支付失败");

        }
        return result;
    }
}
