package io.zhangjia.mall.service.impl;


import io.zhangjia.mall.mapper.AddressMapper;
import io.zhangjia.mall.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressMapper addressMapper;


    @Override
    public List<Map<String, Object>> getUserAddress(Integer userId) {
        List<Map<String, Object>> maps = addressMapper.queryByUserId(userId);
        return maps;

    }

    @Override
    public boolean addUserAddress(Map<String, Object> maps) {

        if ((maps.get("address_id") + "") != null || !"".equals((maps.get("address_id") + "")) || !"".equals((maps.get("user_id") + ""))) {
            String addressId = maps.get("address_id") + "";
            String userId = (maps.get("user_id") + "");
            addressMapper.updateAddressStatus(0, Integer.parseInt(addressId), Integer.parseInt(userId));
        }
        return addressMapper.doInsert(maps) == 1;

    }

    @Override
    public Map<String, Object> getAddress4Edit(Integer addressId) {
        return addressMapper.queryByAddressId(addressId);
    }

    @Override
    public int setDefaultAddress(Integer addressId, Integer userId) {
//            (String status,Integer addressId,Integer userId)
        int i = addressMapper.cancelDefaultAddress(userId);
        System.out.println("iwer = " + i);
        i *= addressMapper.updateAddressStatus(2, addressId, userId);
        System.out.println("iwww = " + i);
        return i;

    }

    @Override
    public int deleteAddress(Integer addressId, Integer userId) {

            return addressMapper.updateAddressStatus(0, addressId,userId);

    }
}
