package io.zhangjia.mall.service;

import java.util.List;
import java.util.Map;

public interface AddressService {
    List<Map<String,Object>>  getUserAddress(Integer userId);
    boolean  addUserAddress(Map<String, Object> maps);

   Map<String,Object>  getAddress4Edit(Integer addressId);

   int setDefaultAddress(Integer addressId, Integer userId);
   int deleteAddress(Integer addressId, Integer userId);


}
