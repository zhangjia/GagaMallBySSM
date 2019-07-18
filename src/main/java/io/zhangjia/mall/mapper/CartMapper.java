package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CartMapper {
    /**
     * 更新购物车中的商品数量
     * 场景1：从单个商品的详情页选择n个数量，添加到购物车
     * @param param
     * @return
     */
    int doUpdateCommodityCount(Map<String, Object> param);

    /**
     * 更新购物车中商品的数量
     * 场景1：在购物车中手动输入商品数量
     * @param param
     * @return
     */
    int doUpdateCartCount(Map<String, Object> param);

    /**
     * 购物车中删除商品
     * @param userId
     * @param commoditySpecsId
     * @return
     */
    int doDelete(@Param("user_id") Integer userId, @Param("commodity_specs_id") Integer[] commoditySpecsId);

    /**
     * 购物车中点击 > 号按钮,商品数量+1
     * @param userId
     * @param commoditySpecsId
     * @return
     */
    int addCount(@Param("user_id") Integer userId, @Param("commodity_specs_id") Integer commoditySpecsId);

    /**
     * 购物车中点击 < 号按钮,商品数量-1
     * @param userId
     * @param commoditySpecsId
     * @return
     */
    int subCount(@Param("user_id") Integer userId,@Param("commodity_specs_id")  Integer commoditySpecsId);

    /**
     * 根据商品的金额判断运费是多少
     * @param userId
     * @param commoditySpecsId
     * @return
     */
    Double queryOrderFreightPrice(@Param("user_id") Integer userId, @Param("commodity_specs_id") Integer[] commoditySpecsId);

    /**
     * 查询购物车中商品信息
     * @param params
     * @return
     */
    List<Map<String,Object>> query(Map<String,Object> params);

    /**
     * 查询购物车中的商品规格信息
     * @param userId
     * @param commoditySpecsId
     * @return
     */
    Map<String,Object> queryByUserIdAndCommoditySpecsId(@Param("user_id") Integer userId, @Param("commodity_specs_id") Integer commoditySpecsId);

    /**
     * 向购物车中添加商品
     * @param param
     * @return
     */
    int doInsert(Map<String, Object> param);
}
