package io.zhangjia.mall.service;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface WalletService {
    int addWallet(Integer userId);
    Map<String,Object> getUserWallet(Integer userId);
    int recharge(@Param("userId") Integer userId,
                 @Param("money") Double money);
    Map<String,Object> orderPayByBalance(@Param("userId") Integer userId,
                                         @Param("money") Double money,
                                         @Param("orderId") Integer orderId);
}
