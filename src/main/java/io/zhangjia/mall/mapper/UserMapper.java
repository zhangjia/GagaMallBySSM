package io.zhangjia.mall.mapper;

import java.util.List;
import java.util.Map;

public interface UserMapper {

    /**
     * 使用动态sql进行多个条件查询
     *
     * @param params 多个条件
     * @return 查询到的用户数据
     */
    List<Map<String, Object>> query(Map<String, Object> params);
//    Map<String, Object> query(Map<String, Object> params);

    /**
     * 用户注册
     */
    Integer doInsert(Map<String, Object> user);


    Integer updateUserInfo(Map<String, Object> user);

    Map<String,Object> isPayPasswordEmpty(Integer userId);


}
