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


    /**
     * 添加商品规格信息
     * @param commoditySpecs
     * @return
     */
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

    /**
     * 更新商品规格的数量和库存
     * @param commoditySpecsId
     * @param commodityCount
     * @return
     */
    Integer updateCommoditySpecs4InventoryAndSales(@Param("commodity_specs_id") Integer commoditySpecsId , @Param("commodity_count") Integer commodityCount);


    Map<String,Object> queryMenuNameByCommodity(Integer commodityId);
}
