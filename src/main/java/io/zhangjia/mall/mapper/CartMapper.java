package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CartMapper {
//    List<Map<String,Object>> queryByUserId(Integer userId);
//    int doInsert(Map<String, Object> param);
//    Map<String,Object> queryByUserIdAndSKUId(Integer userId, Integer SKUId);
    int doUpdateCommodityCount(Map<String, Object> param);
    int doUpdateCartCount(Map<String, Object> param);
    int doDelete(Integer userId, List<String> SKUId);
//
    int addCount(Integer userId, Integer SKUId);
    int subCount(Integer userId, Integer SKUId);
    List<Map<String,Object>> queryCommodities4Settlement(Integer userId, String[] CommoditySKUIds);
//
    Map<String,Object> queryTotal(Integer userId, String[] commoditySKUIds);
    Map<String,Object> querylogistics(Integer userId, String[] commoditySKUIds);


    List<Map<String,Object>> query(Map<String,Object> params);


    Map<String,Object> queryByUserIdAndCommoditySpecsId(@Param("userId") Integer userId, @Param("commoditySpecsId") Integer commoditySpecsId);


    int doInsert(Map<String, Object> param);
}
