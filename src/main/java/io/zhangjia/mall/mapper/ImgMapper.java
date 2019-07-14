package io.zhangjia.mall.mapper;

import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
public interface ImgMapper {
    /**
     * 查询每个商品的商品图
     * @param commodityId 要查询的商品id
     * @return
     */
    List<Map<String,Object>> queryCommodityImgs(Integer commodityId);
    /**
     * 查询每个商品的商品详情图
     * @param commodityId 要查询的商品id
     * @return
     */
    List<Map<String,Object>> queryCommodityDetailImgs(Integer commodityId);
}
