package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Insert;

import java.util.List;
import java.util.Map;

public interface ReviewMapper {
    List<Map<String,Object>> query(Map<String,Object> params);

    Insert doInsert(Map<String,Object> params);


}
