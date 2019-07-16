package io.zhangjia.mall.service.impl;


import com.github.pagehelper.PageHelper;
import io.zhangjia.mall.mapper.CartMapper;
import io.zhangjia.mall.mapper.CommodityMapper;
import io.zhangjia.mall.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("commodityService")
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;


    @Autowired
    private CartMapper cartMapper;


    @Override
    public List<Map<String, Object>> query(Integer commodityId, String commodityName,
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

        List<Map<String, Object>> commodities = commodityMapper.query(params);
        /*
         * 未加 PageHelper.startPage(page,4之前，
         * commoditiesgetClass为class java.util.ArrayList
         * 加了后，commoditiesgetClass为class com.github.pagehelper.Page
         * */
//        System.out.println(commodities.getClass());

        return commodities;
//        return null;
    }


    @Override
    public Map<String, Object> getCommoditySpecs(String specs) {

        return commodityMapper.queryCommoditySpecs(specs);
    }


    @Override
    public Map<String, Object> updateCount2CommodityDetail(String action, Integer userId, Integer commoditySpecsId, Integer count, Integer vals) {

        Map<String, Object> map = new HashMap<>();

        System.out.println("进入了嘎嘎");

        /*思路整理：
         * 先获取当前商品的库存
         * 再获取当前商品在当前用户的购物车中数量，
         * 如果是0，则添加，如果不是0，则修改
         * 如果购物车中的数量加当前选中的数量 > 库存，则失败
         * */

//        获取当前商品的库存
        Integer commoditySpecsInventory = commodityMapper.queryCommoditySpecsInventory(commoditySpecsId);

//        再获取当前商品在当前用户的购物车中数量
        int commodityCount;
        Map<String, Object> cartCommoditySpecs = cartMapper.queryByUserIdAndCommoditySpecsId(userId, commoditySpecsId);
        if (cartCommoditySpecs == null) {
            map.put("nowInventory", commoditySpecsInventory);
            commodityCount = 0;
        } else {
            System.out.println(cartCommoditySpecs);
            map.put("nowInventory", commoditySpecsInventory);
            commodityCount = ((BigDecimal) cartCommoditySpecs.get("COMMODITY_COUNT")).intValue();

        }
        if (action.equals("add") || action.equals("input")) {
            System.out.println("Sku" + commodityCount + "val" + vals + "ct" + count + "commoditySpecsInventory" + commoditySpecsInventory);
            if ((vals + count) > commoditySpecsInventory) {
                System.out.println("库存吵了");
                map.put("error", "超出库存");

            }

        }


        //如果是null，说明购物车里没有

        System.out.println("action = " + action + "--" + "userId = " + userId + "--" + "count = " + count + "--" + commodityCount + "--" + commoditySpecsId);
        System.out.println();


        return map;

    }
}
