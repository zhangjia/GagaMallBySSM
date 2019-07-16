package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CartMapper {
//    List<Map<String,Object>> queryByUserId(Integer userId);
//    int doInsert(Map<String, Object> param);
//    Map<String,Object> queryByUserIdAndcommoditySpecsId(Integer userId, Integer commoditySpecsId);
    int doUpdateCommodityCount(Map<String, Object> param);
    int doUpdateCartCount(Map<String, Object> param);
    int doDelete(@Param("userId") Integer userId, @Param("commoditySpecsId") Integer[] commoditySpecsId);
//
    int addCount(@Param("userId") Integer userId, @Param("commoditySpecsId") Integer commoditySpecsId);
    int subCount(@Param("userId") Integer userId,@Param("commoditySpecsId")  Integer commoditySpecsId);
//    List<Map<String,Object>> queryCommodities4Settlement(Integer userId, Integer[] commoditySpecsId);
//
//    Map<String,Object> queryTotal(@Param("orderFreightPrice")Double orderFreightPrice ,@Param("userId") Integer userId, @Param("commoditySpecsId") Integer[] commoditySpecsId);
//    Map<String,Object> queryOrderFreightPrice(Integer userId, Integer[] commoditySpecsId);
    Double queryOrderFreightPrice(@Param("userId") Integer userId, @Param("commoditySpecsId") Integer[] commoditySpecsId);


    List<Map<String,Object>> query(Map<String,Object> params);


    Map<String,Object> queryByUserIdAndCommoditySpecsId(@Param("userId") Integer userId, @Param("commoditySpecsId") Integer commoditySpecsId);


    int doInsert(Map<String, Object> param);
}
