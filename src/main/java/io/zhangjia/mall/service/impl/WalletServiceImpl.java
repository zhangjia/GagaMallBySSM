package io.zhangjia.mall.service.impl;




import io.zhangjia.mall.mapper.OrderMapper;
import io.zhangjia.mall.mapper.WalletMapper;
import io.zhangjia.mall.service.WalletService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Service("walletService")
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletMapper walletMapper;
    @Autowired
    private OrderMapper orderMapper;

    @Override
    public int addWallet(Integer userId) {
        if (userId != null && !"".equals(userId)) {
            return walletMapper.doInsert(userId);
        } else {
            return 0;
        }
    }

    @Override
    public Map<String, Object> getUserWallet(Integer userId) {
        if (userId != null && !"".equals(userId)) {
            return walletMapper.queryByUserId(userId);
        } else {
            return null;
        }

    }

    @Override
    public int recharge(Integer userId, Double money) {
        System.out.println(" 进入service的recharge");
        if (userId != null && !"".equals(userId) && money != null && !"".equals(money)) {

            if (money <= 0) {
                System.out.println(" 充值金额小于0");
                return 0;
            }
            return walletMapper.doUpdate(userId, money);
        } else {
            System.out.println(" 充值金额null0");

            return 0;
        }
    }

    @Override
    public Map<String, Object> orderPayByBalance(Integer userId, Double money, Integer orderId) {
        System.out.println(" 进入了orderPayByBalance");
        Map<String, Object> result = new HashMap<>();
        System.out.println("userId = " + userId);
        System.out.println("money = " + money);
        System.out.println("orderId = " + orderId);
        if (userId != null && !"".equals(userId) && money != null && !"".equals(money) && orderId != null && !"".equals(orderId)) {
            Map<String, Object> stringObjectMap = walletMapper.queryByUserId(userId);
            System.out.println("gaggagastringObjectMap = " + stringObjectMap);
//            Double walletBalance = ((BigDecimal) stringObjectMap.get("wallet_balance")).doubleValue();
            Double walletBalance = Double.parseDouble(stringObjectMap.get("wallet_balance").toString());
            System.out.println("walletBalance = " + walletBalance);
            System.out.println("wallet_balance = " + walletBalance);
            if (walletBalance < money) {
                result.put("error", "余额不足");
            } else {
                int i = walletMapper.doUpdate(userId, money * -1);
                if (i == 1) {
                    result.put("success", "支付成功");
                    orderMapper.doUpdateByPay("余额支付", userId, orderId);
                } else {
                    System.out.println(" 路飞= ");
                    result.put("error", "支付失败");
                }
            }

        } else {
            System.out.println(" 索隆= ");
            result.put("error", "支付失败");

        }
        return result;
    }
}
