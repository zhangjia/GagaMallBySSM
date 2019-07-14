package io.zhangjia.mall.mapper;

import java.util.List;
import java.util.Map;

public interface ImgMapper {
    List<Map<String,Object>> queryCommodityImgs(Integer commodityId);
    List<Map<String,Object>> queryCommodityDetailImgs(Integer commodityId);
}
