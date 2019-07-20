package io.zhangjia.mall.service.impl;


import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.mapper.AddressMapper;
import io.zhangjia.mall.mapper.CartMapper;
import io.zhangjia.mall.mapper.CommodityMapper;
import io.zhangjia.mall.mapper.OrderMapper;
import io.zhangjia.mall.service.CarService;
import io.zhangjia.mall.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("orderService")
public class OrderServiceImpl implements OrderService {
    @Autowired
    private AddressMapper addressMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private CommodityMapper commodityMapper;


    @Override
    public List<Map<String, Object>> getOrders(Integer userId) {
/*        if (userId != null && !"".equals(userId)) {
            List<Map<String, Object>> maps = orderMapper.queryByUserId(userId);
            for (Map<String, Object> map : maps) {
//               获取地址编号
                Integer addressId = Integer.parseInt(map.get("address_id").toString());
                Map<String, Object> stringObjectMap = addressMapper.queryByAddressId(addressId);
                map.put("address", stringObjectMap);
                Integer orderId = Integer.parseInt(map.get("order_id").toString());
                System.out.println("orderIdzz = " + orderId);
                Double aDouble = orderMapper.queryOrderPrice(orderId);
                map.put("order_pay_price", aDouble);
                List<Map<String, Object>> maps1 = orderMapper.queryCommodityByOrderId(orderId);
                map.put("commodities", maps1);
                Double aDouble1 = orderMapper.queryOrderDiscountPrice(orderId);
                map.put("discount_price", aDouble1);
                Double aDouble2 = orderMapper.queryOrderOriginalPrice(orderId);
                map.put("original_price", aDouble2);
            }
            System.out.println("jsonmaps" + JSON.toJSONString(maps));
            return maps;
        } else {
            return null;
        }*/

        List<Map<String, Object>> maps = orderMapper.queryByUserId(userId, null);
        System.out.println("jsonmaps" + JSON.toJSONString(maps));
        return maps;
    }

    @Override
    public Map<String, Object> getOrder(Integer userId, Integer orderId) {
       /* System.out.println("1userId = [" + userId + "], orderId = [" + orderId + "]");
        List<Map<String, Object>> orders = getOrders(userId);
        for (Map<String, Object> m : orders) {
            System.out.println(("m" + JSON.toJSONString(m)));
            System.out.println(Integer.parseInt(m.get("order_id").toString()) == (orderId));
            //必须加""，否则是false
            if (Integer.parseInt(m.get("order_id").toString()) == (orderId)) {
                System.out.println("进入orderId");
                return m;
            }
        }
        return null;*/

        Map<String, Object> map = orderMapper.queryByUserId(userId, orderId).get(0);
        System.out.println("liuwenlu" + JSON.toJSONString(map));
        return map;

    }

    @Override
    public Map<String, Object> getTotal(Integer userId, Integer[] commoditySpecsId) {
        if (userId != null && !"".equals(userId)) {
            System.out.println("catmmpawer == null " + cartMapper == null);
            Double orderFreightPrice = cartMapper.queryOrderFreightPrice(userId, commoditySpecsId);
            return orderMapper.queryTotal(orderFreightPrice, userId, commoditySpecsId);
        } else {
            return null;
        }
    }

    @Override
    public Map<String, Object> getTotalByBuyNow(Integer userId, Integer[] commoditySpecsId, Integer commodityCount) {
        if (userId != null && !"".equals(userId)) {
            Double orderFreightPrice = cartMapper.queryOrderFreightPrice(userId, commoditySpecsId);
            return orderMapper.queryTotalByBuyNow(orderFreightPrice, userId, commoditySpecsId, commodityCount);
        } else {
            return null;
        }
    }

    @Override
    public int submit(Integer userId, Integer addressId,
                      String orderPayType, String orderNote, Integer[] commoditySpecsId, Integer commodityCount) {
        System.out.println("1111111userId = [" + userId + "], addressId = [" + addressId + "], orderPayType = [" + orderPayType + "], orderNote = [" + orderNote + "], commoditySpecsId = [" + commoditySpecsId + "], commodityCount = [" + commodityCount + "]");
        int result = 1;
        if (userId != null && !"".equals(userId) &&
                addressId != null && !"".equals(addressId)) {
////            判断运费
//            Map<String, Object> total = getTotal(userId, commoditySpecsId);
//            System.out.println("1运费 = " + total);
            Double orderFreightPrice = cartMapper.queryOrderFreightPrice(userId, commoditySpecsId);

            //        1.向订单表插入数据

            Map<String, Object> params = new HashMap<>();
            params.put("user_id", userId);
            params.put("address_id", addressId);
//            params.put("orderLogistics", 10);
//            TODO：订单物流
            params.put("order_logistics", "未支付");
            params.put("order_freight_price", orderFreightPrice);
            params.put("order_pay_type", "未支付");
            params.put("order_note", orderNote);
            params.put("order_status", 4); //默认是待支付
            params.put("order_id", null);
            int orderId = orderMapper.doInsert(params);
            System.out.println("paramsJSON = " + JSON.toJSONString(params));
            result *= orderId;
            System.out.println("result0 = " + result);
//        2.  向订单明细表插入数据

            List<Map<String, Object>> maps = new ArrayList<>();
            if (commodityCount != null) { //如果是直接购买
                maps = orderMapper.queryCommodities4SettlementByBuyNow(userId, commoditySpecsId);
                System.out.println("ga1JSON.toJSONString(maps) = " + JSON.toJSONString(maps));

            } else {

                maps = orderMapper.queryCommodities4Settlement(userId, commoditySpecsId);
                System.out.println("ga2JSON.toJSONString(maps) = " + JSON.toJSONString(maps));
            }
            System.out.println("JSON.toJSONString(maps) = " + JSON.toJSONString(maps));
            for (Map<String, Object> map : maps) {
                map.put("order_id", params.get("order_id"));
//            TODO：订单优惠金额，留着做优惠券和京豆使用
                map.put("order_detail_discount_price", 0);
                System.out.println("JSON.mapmapmapmap= " + JSON.toJSONString(map));

                if (commodityCount != null) {
                    map.put("commodity_count",commodityCount);
                }

                result *= orderMapper.doInsert4Detail(map);
                System.out.println("result1 = " + result);

//                4. 更改库存和销量

                if (commodityCount != null) {
                    result *= commodityMapper.updateCommoditySpecs4InventoryAndSales(Integer.parseInt(map.get("commodity_specs_id").toString()),
                            commodityCount);

                } else {
                    result *= commodityMapper.updateCommoditySpecs4InventoryAndSales(Integer.parseInt( map.get("commodity_specs_id").toString()),
                            Integer.parseInt("" + map.get("commodity_count")));
                    System.out.println("result3 = " + result);
                }

            }
            if (commodityCount == null) {
//                3. 删除购物车中的商品
                System.out.println("orderserviceSKUIDs = " + commoditySpecsId);
                result *= cartMapper.doDelete(userId, commoditySpecsId);
            }
//
//       如果插入成功，将id返回
            if (result != 0) {
                return Integer.parseInt(params.get("order_id").toString());
            } else {
                return 0;
            }

        } else {

            return 0;
        }
    }

    @Override
    public List<Map<String, Object>> getCarCommodities4Settlement(Integer userId, Integer[] commoditySpecsId) {

        System.out.println("uid = " + userId);
        return orderMapper.queryCommodities4Settlement(userId, commoditySpecsId);

    }

    @Override
    public List<Map<String, Object>> getCarCommodities4SettlementByBuyNow(Integer userId, Integer[] commoditySpecsId) {

        System.out.println("uid = " + userId);
        return orderMapper.queryCommodities4SettlementByBuyNow(userId, commoditySpecsId);

    }
}
