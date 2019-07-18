package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface WalletMapper {
    /**
     * 添加钱包记录
     * 场景：注册用户的时候，根据注册用户的id生成对应的钱包
     * @param userId
     * @return
     */
    int doInsert(Integer userId);

    /**
     * 查询钱包信息
     * @param userId
     * @return
     */
    Map<String,Object> queryByUserId(Integer userId);

    /**
     * 更新钱包金额
     * 场景1：支付的时候，扣钱，money为负数
     * 场景2：充值的时候，加钱，moeny为正数
     * @param userId
     * @param money
     * @return
     */
    int doUpdate(@Param("user_id") Integer userId, @Param("money") Double money);
}
