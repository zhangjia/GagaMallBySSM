package io.zhangjia.mall.mapper;

import io.zhangjia.mall.entity.Commodity;

import java.util.List;
import java.util.Map;

public interface CommodityMapper {

    List<Map<String, Object>> query(Map<String, Object> params);
    //   /*  List<Map<String,Object>> queryCommodityImgs(List<Commodity> commodities);*/

    /**
     * 查询商品
     *
     * @param params 各种查询条件
     * @return 查询结果
     */
//    List<Commodity> query(Map<String, Object> params);
    List<Commodity> queryCommodityImgs(List<Commodity> commodities);

    /**
     * 根据指定规格查询该规则的相关数据
     *
     * @param specs 指定的规格
     * @return
     */
    Map<String, Object> queryCommoditySpecs(String specs);
}
