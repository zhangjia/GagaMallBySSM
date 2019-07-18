package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CommodityMapper {


    /**
     * 使用动态sql查询商品信息
     * @param params
     * @return
     */
    List<Map<String, Object>> query(Map<String, Object> params);

    /**
     * 添加商品信息
     * @param commodity
     * @return
     */
    Integer doCommodityInsert(Map<String,Object> commodity);
    Integer doCommoditySpecsInsert(Map<String,Object> commoditySpecs);

    /**
     * 根据指定规格查询该规则的相关数据
     *
     * @param specs 指定的规格
     * @return
     */
    Map<String, Object> queryCommoditySpecs(String specs);

    /**
     * 根据某个商品规格查询该商品的库存
     * @param commoditySpecsId
     * @return
     */
    Integer queryCommoditySpecsInventory(Integer commoditySpecsId);

    Integer updateCommoditySpecs4InventoryAndSales(@Param("commoditySpecsId") Integer commoditySpecsId , @Param("commodityCount") Integer commodityCount);

}
