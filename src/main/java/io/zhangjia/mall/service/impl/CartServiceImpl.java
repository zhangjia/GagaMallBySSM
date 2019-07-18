package io.zhangjia.mall.service.impl;


import io.zhangjia.mall.mapper.CartMapper;
import io.zhangjia.mall.mapper.CommodityMapper;
import io.zhangjia.mall.service.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("carService")
public class CartServiceImpl implements CarService {

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CommodityMapper commodityMapper;

    @Override
    public Map<String, Object> addCart(Integer userId, Integer commoditySpecsId, Integer commodityCount) {

        Map<String, Object> params = new HashMap<>();

        Map<String, Object> map = new HashMap<>();

        params.put("user_id", userId);
        params.put("commodity_specs_id", commoditySpecsId);
        params.put("commodity_count", commodityCount);


        Map<String, Object> cart = cartMapper.queryByUserIdAndCommoditySpecsId(userId, commoditySpecsId);
        //仔细考虑一下，这里的判断为啥要加在Service里而不是DaoImpl里
        int i;
        if (cart == null) {
            i = cartMapper.doInsert(params);
        } else {
            i = cartMapper.doUpdateCommodityCount(params);
        }


        map.put("success", i == 1);

        return map;

    }



      @Override
      public List<Map<String, Object>> getCarCommodities(Integer userId) {
          Map<String,Object> params = new HashMap<>();
          System.out.println("userId = [" + userId + "]");
          params.put("user_id",userId);
          return cartMapper.query(params);
      }



    @Override
    public boolean deleteCart(Integer userId, Integer[] commoditySpecsId) {
        if (userId != null && !"".equals(userId)) {
            System.out.println("SKUID" + commoditySpecsId);
            boolean b = cartMapper.doDelete(userId, commoditySpecsId) != 0;
            System.out.println("b = " + b);
            return b;
        }
        return false;
    }

    @Override
    public Map<String, Object> updateCount(String action, Integer userId, Integer commoditySpecsId,Integer commodityCount) {

        Map<String, Object> map = new HashMap<>();


//思路整理：
//             * 先获取当前商品的库存
//             * 再获取当前商品在当前用户的购物车中数量
//             * 如果购物车中的数量+1 大于 库存，则失败
//             * 如果购物车中的数量-1 = 0 。则失败
//             *


//        获取当前商品的库存
            Integer commoditySpecsInventory = commodityMapper.queryCommoditySpecsInventory(commoditySpecsId);

        System.out.println("commoditySpecsInventory111 = " + commoditySpecsInventory);

//        再获取当前商品在当前用户的购物车中数量
        Map<String, Object> cartCommoditySpecs = cartMapper.queryByUserIdAndCommoditySpecsId(userId, commoditySpecsId);
            System.out.println("cartCommoditySpecs = " + cartCommoditySpecs);
//            System.out.println("cartCommoditySpecs.get(\"COMMODITY_COUNT\")class = " + cartCommoditySpecs.get("COMMODITY_COUNT").getClass());
//            int commodityCountByCart = ((BigDecimal) cartCommoditySpecs.get("commodity_count")).intValue();
        Integer commodityCountByCart = (Integer) cartCommoditySpecs.get("commodity_count");

            int i = 0;
            if (action.equals("add")) {
                if (commodityCountByCart + 1 > commoditySpecsInventory) {
                    map.put("error", "超出库存");
                } else {
                    i = cartMapper.addCount(userId,commoditySpecsId);
                }

            } else if (action.equals("input")) {
                if (commodityCount > commoditySpecsInventory) {
                    map.put("error", "超出库存");
                    Map<String,Object> m = new HashMap<>();
                    m.put("commodity_count",commoditySpecsInventory);
                    m.put("user_id",userId);
                    m.put("commodity_specs_id",commoditySpecsId);
                    cartMapper.doUpdateCartCount(m);
                    map.put("commodity_specs_inventory",commoditySpecsInventory);
                    System.out.println(commoditySpecsInventory);
                } else {
                    Map<String,Object> m = new HashMap<>();
                    m.put("commodity_count",commodityCount);
                    m.put("user_id",userId);
                    m.put("commodity_specs_id",commoditySpecsId);
                    i = cartMapper.doUpdateCartCount(m);
                    System.out.println("m" + m);
                }
            } else {
                if (commodityCountByCart - 1 == 0) {
                    map.put("error", "不能再少啦");
                } else {
                    i = cartMapper.subCount(userId, commoditySpecsId);
                }

            }
            if (i == 1) {
                map.put("success", true);
            }

            return map;


    }
/*
    @Override
    public List<Map<String, Object>> getCarCommodities4Settlement(String userId,String[] commoditycommoditySpecsId) {
        if(userId != null && !"".equals(userId)) {
            int userId = Integer.parseInt(userId);
            System.out.println("userId = " + userId);
        return cartMapper.queryCommodities4Settlement(userId,commoditycommoditySpecsId);
        } else {
            return null;
        }
    }
*/




}
