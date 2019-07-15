package io.zhangjia.mall.service.impl;


import com.github.pagehelper.PageHelper;
import io.zhangjia.mall.mapper.CommodityMapper;
import io.zhangjia.mall.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("commodityService")
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;


    @Override
    public List<Map<String,Object>> query(Integer commodityId, String commodityName,
                                 Integer level1MenuId, Integer level2MenuId,
                                 Integer order, Integer page) {

        Map<String, Object> params = new HashMap<>();
        params.put("commodityId", commodityId);
        params.put("commodityName", commodityName);
        params.put("level1MenuId", level1MenuId);
        params.put("level2MenuId", level2MenuId);
        params.put("order", order);

//        查询商品详情的时候，page = null
        if (page != null) {
            PageHelper.startPage(page, 4);
        }

       List<Map<String,Object>> commodities = commodityMapper.query(params);
        /*
         * 未加 PageHelper.startPage(page,4之前，
         * commoditiesgetClass为class java.util.ArrayList
         * 加了后，commoditiesgetClass为class com.github.pagehelper.Page
         * */
//        System.out.println(commodities.getClass());

        return commodities;
//        return null;
    }


    /**
     * 根据sku表的sku——value获取该商品的其他sku属性
     *
     * @param specs
     * @return
     */
    @Override
    public  Map<String,Object>  getCommoditySpecs(String specs) {
        return commodityMapper.queryCommoditySpecs(specs);
    }
}
