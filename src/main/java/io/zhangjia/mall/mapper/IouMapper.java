package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface IouMapper {
    Map<String,Object> queryByUserId(Integer userId);
    int doUpdate(@Param("userId") Integer userId,@Param("money") Double money);
    int doInsert(Integer userId);
}
