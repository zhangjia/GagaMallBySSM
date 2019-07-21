package io.zhangjia.mall.mapper;

import java.util.List;
import java.util.Map;

public interface ReviewMapper {
    List<Map<String,Object>> query(Map<String,Object> params);

    Integer doInsert(Map<String,Object> params);


}
