package io.zhangjia.mall.mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    /**
     * 使用动态sql进行多个条件查询
     * 因为有的场景可能查到多条，所以采用了list，如果查单条，需要get（0）
     * @param params  多个条件
     * @return 用户信息
     */
    List<Map<String, Object>> query(Map<String, Object> params);
//    Map<String, Object> query(Map<String, Object> params);

    /**
     * 添加用户
     * @param user 要添加的用户数据
     * @return
     */
    Integer doInsert(Map<String, Object> user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    Integer updateUserInfo(Map<String, Object> user);


    /**
     * 根据用户Id判断用户支付密码是否为空
     * 场景1：用户信息显示，如果没设置密码，则显示添加支付密码，如果设置了密码，则显示修改支付密码
     * 场景2：用户下单支付时，如果没设置支付密码，需先去设置支付密码
     * @param userId
     * @return
     */
    Map<String,Object> isPayPasswordEmpty(Integer userId);


}
