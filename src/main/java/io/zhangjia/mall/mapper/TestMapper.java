package io.zhangjia.mall.mapper;


import java.util.List;
import java.util.Map;

public interface TestMapper {

    List<Map<String,Object>> queryById(Integer id);

    List<Map<String,Object>>  queryUserId();
}
