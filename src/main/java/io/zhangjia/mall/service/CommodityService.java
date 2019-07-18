package io.zhangjia.mall.service;


import java.util.List;
import java.util.Map;


/**
 * 用户的业务层
 */
public interface CommodityService {
    /*List<Map<String,Object>> query(Integer commodityId,String commodityName,
                                   Integer level1MenuId,Integer level2MenuId,
                                   Integer order);  */

    /**
     * 查询商品
     * @param commodityId 根据ID查询单个商品
     * @param commodityName 根据名字模糊查询
     * @param level1MenuId 根据一级菜单查询
     * @param level2MenuId 根据二级菜单查询
     * @param order 对查询结果进行排序
     * @param page 查询第几页的数据
     * @return
     */
    List<Map<String,Object>> query(Integer commodityId, String commodityName,
                          Integer level1MenuId, Integer level2MenuId,
                          Integer order,Integer page);

    /**
     *
     * 根据用户选择的规格查询该规格的相关数据
     *
     * @param specs 用户的选择的规格
     * @return 该规格相关的数据
     */
    Map<String,Object>  getCommoditySpecs(String specs);


    /**
     * 在商品详情页更改个数
     * @param action 添加还是喊出
     * @param userId 用户ID
     * @param commoditySpecsId 规格ID
     * @param count 个数
     * @param val 输入框值
     * @return
     */
    Map<String, Object> updateCount2CommodityDetail(String action, Integer userId, Integer commoditySpecsId,Integer count,Integer val);

    Map<String, Object> addCommodity(Map<String, Object> maps);
}
