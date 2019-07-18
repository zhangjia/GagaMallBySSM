package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface IouMapper {
    /**
     * 根据用户ID查询用户的白条信息
     * @param userId
     * @return
     */
    Map<String,Object> queryByUserId(Integer userId);

    /**
     * 更新用户的白条额度
     * @param userId
     * @param money
     * @return
     */
    int doUpdate(@Param("user_id") Integer userId,@Param("money") Double money);

    /**
     * 添加白条记录
     * 场景：注册用户时，自动根据注册用户的ID插入白条信息
     * @param userId
     * @return
     */
    int doInsert(@Param("user_id") Integer userId);
}
