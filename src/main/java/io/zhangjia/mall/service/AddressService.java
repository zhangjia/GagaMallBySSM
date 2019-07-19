package io.zhangjia.mall.service;

import java.util.List;
import java.util.Map;

public interface AddressService {

    /**
     * 根据用户ID获取该用户的所有收货地址
     * @param userId
     * @return
     */
    List<Map<String,Object>>  getUserAddress(Integer userId);

    /**
     * 添加收获地址
     * @param maps
     * @return
     */
    boolean  addUserAddress(Map<String, Object> maps);

    /**
     * 修改地址时数据回显
     * @param addressId
     * @return
     */
   Map<String,Object>  getAddress4Edit(Integer addressId);

    /**
     * 设置某个收货地址为默认地址
     * @param addressId
     * @param userId
     * @return
     */
   int setDefaultAddress(Integer addressId, Integer userId);

    /**
     * 删除收货地址
     * @param addressId
     * @param userId
     * @return
     */
   int deleteAddress(Integer addressId, Integer userId);


}
