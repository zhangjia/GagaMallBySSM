package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface OrderMapper {
    /**
     * 结算页面查询商品
     * @param userId
     * @param commoditySpecsId
     * @return
     */
    List<Map<String,Object>> queryCommodities4Settlement(@Param("user_id") Integer userId, @Param("commodity_specs_id") Integer[] commoditySpecsId);
    List<Map<String,Object>> queryCommodities4SettlementByBuyNow(@Param("user_id") Integer userId, @Param("commodity_specs_id") Integer[] commoditySpecsId);

    /**
     * 根据用户ID查询其所有的订单
     * @param userId
     * @return
     */
    List<Map<String,Object>> queryByUserId(@Param("user_id") Integer userId,@Param("order_id") Integer orderId);

    /**
     * 查询单个订单中的商品信息
     * @param orderId
     * @return
     */
    List<Map<String,Object>> queryCommodityByOrderId(Integer orderId);

    /**
     * 查询订单价格
     * @param orderId
     * @return
     */
    Double queryOrderPrice(Integer orderId);

    /**
     * 查询订单原价格（优惠前价格，商品原价*数量）
     * @param orderId
     * @return
     */
    Double queryOrderOriginalPrice(Integer orderId);

    /**
     * 查询商品优惠价格
     *
     * @param orderId
     * @return
     */
    Double queryOrderDiscountPrice(Integer orderId);

    /**
     * 添加订单
     * @param param
     * @return
     */
    int doInsert(Map<String, Object> param);

    /**
     * 添加订单详情
     * @param param
     * @return
     */
    int doInsert4Detail(Map<String, Object> param);

    /**
     * 支付后修改订单信息
     * @param payType
     * @param userId
     * @param orderId
     * @return
     */
    int doUpdateByPay(@Param("pay_type") String payType ,@Param("user_id")Integer userId, @Param("order_id")Integer orderId);

    /**
     * 发货后修改订单信息
     * @param orderId
     * @param logistics
     * @return
     */
    int doUpdateByDeliverGoods(@Param("order_id") Integer orderId,@Param("order_logistics")String logistics);

    /**
     * 计算总价
     * @param orderFreightPrice
     * @param userId
     * @param commoditySpecsId
     * @return
     */
    Map<String,Object> queryTotal(@Param("order_freight_price")Double orderFreightPrice ,@Param("user_id") Integer userId, @Param("commodity_specs_id") Integer[] commoditySpecsId);

    Map<String,Object> queryTotalByBuyNow(@Param("order_freight_price")Double orderFreightPrice ,@Param("user_id") Integer userId,
                                          @Param("commodity_specs_id") Integer[] commoditySpecsId,@Param("commodity_count") Integer commodityCount);


}
