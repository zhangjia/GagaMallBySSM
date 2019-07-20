package io.zhangjia.mall.service.impl;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import io.zhangjia.mall.mapper.CartMapper;
import io.zhangjia.mall.mapper.CommodityMapper;
import io.zhangjia.mall.mapper.ImgMapper;
import io.zhangjia.mall.mapper.SiteSettingsMapper;
import io.zhangjia.mall.service.CommodityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service("commodityService")
public class CommodityServiceImpl implements CommodityService {

    @Autowired
    private CommodityMapper commodityMapper;


    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private SiteSettingsMapper siteSettingsMapper;

    @Autowired
    private ImgMapper imgMapper;

    @Override
    public List<Map<String, Object>> query(Integer commodityId, String commodityName,
                                           Integer level1MenuId, Integer level2MenuId,
                                           Integer order, Integer page) {

        Map<String, Object> params = new HashMap<>();
        params.put("commodity_id", commodityId);
        params.put("commodity_name", commodityName);
        params.put("level1_menu_id", level1MenuId);
        params.put("level2_menu_id", level2MenuId);
        params.put("order", order);

        Integer pageSize = Integer.parseInt(siteSettingsMapper.queryByKey("分页"));

        if (page != null) {// 查询商品详情的时候，page = null
            PageHelper.startPage(page, pageSize);
        }

        List<Map<String, Object>> commodities = commodityMapper.query(params);
        System.out.println("fenye" + JSON.toJSONString(commodities));
        return commodities;
        /*
         * 未加 PageHelper.startPage(page,4之前，
         * commoditiesgetClass为class java.util.ArrayList
         * 加了后，commoditiesgetClass为class com.github.pagehelper.Page
         *
         *  */

    }


    @Override
    public Map<String, Object> getCommoditySpecs(String specs, Integer commodityId) {

        return commodityMapper.queryCommoditySpecs(specs,commodityId);
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


    @Override
    public Map<String, Object> addCommodity(Map<String, Object> params) {
        /*思路整理
         * 先判断key是否存在，存在不动，不存在插入
         * 再判断false是否存在，存在不动，不存在插入
         * 先在商品表插入，返回商品ID
         * 然后根据商品Id，插入图片和详情图
         * 再插入sku表
         * */
        /*---------------------------------插入商品开始------------------------------------*/
        Map<String, Object> commodity = new HashMap<>();
        commodity.put("commodity_name", params.get("commodity_name"));
        commodity.put("commodity_attribute", params.get("jsonAttr"));
        commodity.put("level1_menu_id", params.get("level1_menu_id"));
        commodity.put("level2_menu_id", params.get("level2_menu_id"));
        commodity.put("commodity_id",null);
        System.out.println("commodityJSONJSON = " + JSON.toJSONString(commodity));
        System.out.println(commodityMapper == null);
        System.out.println("commodityJSONJSON2 = " + JSON.toJSONString(commodity));
        int result  = commodityMapper.doCommodityInsert(commodity);
        System.out.println(result + "r1");
        Integer commodityId= Integer.parseInt(commodity.get("commodity_id").toString());
        /*---------------------------------插入商品结束-----------------------------------*/

        /*---------------------------------插入SKU开始------------------------------------*/
        JSONArray skuRecords = JSON.parseArray(params.get("skuRecords").toString());

        System.out.println("skuRecords = " + skuRecords);
        for (Object skrs : skuRecords) {
            System.out.println("srrs" + skrs.getClass());
            JSONObject m = (JSONObject) skrs;

            Map<String, Object> commoditySpecs = new HashMap<>();
            commoditySpecs.put("commodity_id",commodityId );
            commoditySpecs.put("commodity_specs_value", m.getString("skuvalue"));
            commoditySpecs.put("commodity_specs_original_price", 0);
            commoditySpecs.put("commodity_specs_last_price", m.getDouble("price"));
            commoditySpecs.put("commodity_specs_present_price", m.getDouble("price"));
            commoditySpecs.put("commodity_specs_inventor", m.getInteger("inventory"));
            System.out.println("skrssku" + JSON.toJSONString(commoditySpecs));

            result *= commodityMapper.doCommoditySpecsInsert(commoditySpecs);
            System.out.println(result + "r2");
        }



        /*---------------------------------插入SKU结束------------------------------------*/

        /*---------------------------------获取所有图片开始------------------------------------*/
        //将其转换为Java对象
        JSONObject jsonObject = JSON.parseObject(params.get("spt").toString());
        JSONArray jy = (JSONArray) jsonObject.get("spt");
        List<String> spt = new ArrayList<>();
        for (int i = 0; i < jy.size(); i++) {
            spt.add(jy.get(i).toString());
        }
        int order = 0;

        for (String j : spt) {
            result *=  imgMapper.doInsert(j, commodityId, ++order, "商品图");
            System.out.println(result + "r3");
        }



        /*---------------------------------获取所有图片结束------------------------------------*/

        /*---------------------------------获取所有详情图开始------------------------------------*/
        //将其转换为Java对象
        System.out.println(JSON.toJSONString(params) + "params");
        JSONObject jsonObject2 = JSON.parseObject(params.get("spxqt") + "");
        JSONArray jy2 = (JSONArray) jsonObject2.get("spxqt");
        List<String> spxqt = new ArrayList<>();
        for (int i = 0; i < jy2.size(); i++) {
            spxqt.add(jy2.get(i) + "");
        }
        System.out.println(spxqt.size());
        for (String s : spxqt
        ) {
            System.out.print(s + "--");

        }
        int order2 = 0;

        for (String j : spxqt) {
            result *=  imgMapper.doInsert(j, commodityId, ++order2, "商品详情图");
            System.out.println(result + "r4");
        }
        /*---------------------------------获取所有详情图结束------------------------------------*/

        Map<String,Object> results = new HashMap<>();
        if(result != 0) {
            results.put("success",true);
        } else {
            results.put("false",true);
        }

        return results ;
    }

    @Override
    public List<Map<String, Object>> commodities4Index(Integer size) {
        Map<String,Object> param = new HashMap<>();
        param.put("size",size);

        return  commodityMapper.query(param);
    }
}
