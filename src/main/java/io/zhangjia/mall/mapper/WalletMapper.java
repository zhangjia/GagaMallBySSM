package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface WalletMapper {
    int doInsert(Integer userId);
    Map<String,Object> queryByUserId(Integer userId);
    int doUpdate(@Param("userId") Integer userId, @Param("money") Double money);
}
