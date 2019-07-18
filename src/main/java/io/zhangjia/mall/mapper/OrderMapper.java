package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    List<Map<String,Object>> queryCommodities4Settlement(@Param("userId") Integer userId, @Param("commoditySpecsId") Integer[] commoditySpecsId);


    List<Map<String,Object>> queryByUserId(Integer userId);
    List<Map<String,Object>> queryCommodityByOrderId(Integer orderId);
    Double queryOrderPrice(Integer orderId);
    Double queryOrderOriginalPrice(Integer orderId);
    Double queryOrderDiscountPrice(Integer orderId);

    int doInsert(Map<String, Object> param);

    int doInsert4Detail(Map<String, Object> param);

    int doUpdateByPay(@Param("payType") String payType ,@Param("userId")Integer userId, @Param("orderId")Integer orderId);
    int doUpdateByDeliverGoods(@Param("orderId") Integer orderId,@Param("logistics")String logistics);

    Map<String,Object> queryTotal(@Param("orderFreightPrice")Double orderFreightPrice ,@Param("userId") Integer userId, @Param("commoditySpecsId") Integer[] commoditySpecsId);
}
