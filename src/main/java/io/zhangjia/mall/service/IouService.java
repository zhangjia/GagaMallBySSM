package io.zhangjia.mall.service;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface IouService {
    Map<String, Object> queryUserIou(Integer userId);

    Map<String, Object> orderPayByIou(@Param("userId") Integer userId,
                                      @Param("money") Double money,
                                      @Param("orderId") Integer orderId);
}
