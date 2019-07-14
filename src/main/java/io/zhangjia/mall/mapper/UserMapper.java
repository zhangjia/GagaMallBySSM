package io.zhangjia.mall.mapper;

import io.zhangjia.mall.entity.User;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    /**
     * 使用动态sql进行多个条件查询
     * @param params 多个条件
     * @return 查询到的用户数据
     */
    List<Map<String,Object>> query(Map<String, Object> params);

    /**
     *根据用户名和密码添加用户
     * @param user 要添加的用户
     * @return 添加是否成功，成功返回1，不成功返回0
     */
    int doInsert(User user);

    /**
     * 更新用户信息
     * @param user
     * @return
     */
    int updateUserInformation(User user);

}
