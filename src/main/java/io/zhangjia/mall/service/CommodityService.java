package io.zhangjia.mall.service;


import io.zhangjia.mall.entity.Commodity;

import java.util.List;
import java.util.Map;

/**
 * 用户的业务层
 */
public interface CommodityService {
    /*List<Map<String,Object>> query(Integer commodityId,String commodityName,
                                   Integer level1MenuId,Integer level2MenuId,
                                   Integer order);  */
    List<Commodity> query(Integer commodityId, String commodityName,
                          Integer level1MenuId, Integer level2MenuId,
                          Integer order,Integer page);

}
