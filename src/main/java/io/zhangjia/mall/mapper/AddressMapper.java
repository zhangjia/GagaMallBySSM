package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AddressMapper {
    List<Map<String,Object>> queryByUserId(Integer userId);
    int doInsert(Map<String,Object> address);
    Map<String,Object> queryByAddressId(Integer addressId);
    int updateAddressStatus(@Param("status") String status, @Param("addressId")Integer addressId, @Param("userId")Integer userId);
    int cancelDefaultAddress(Integer userId);
}
