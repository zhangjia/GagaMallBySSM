package io.zhangjia.mall.mapper;

import java.util.List;
import java.util.Map;

public interface CommodityMapper {

    List<Map<String, Object>> query(Map<String, Object> params);

    List<Map<String, Object>> queryCommodityImgs(List<Map<String,Object>> commodities);



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
    int queryCommoditySpecsInventory(Integer commoditySpecsId);
}
