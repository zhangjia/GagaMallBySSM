package io.zhangjia.mall.mapper;

import io.zhangjia.mall.entity.Commodity;

import java.util.List;
import java.util.Map;

public interface CommodityMapper {

   /* List<Map<String,Object>> query(Map<String, Object> params);
    List<Map<String,Object>> queryCommodityImgs(List<Commodity> commodities);*/
    List<Commodity> query(Map<String, Object> params);
    List<Commodity> queryCommodityImgs(List<Commodity> commodities);
}
