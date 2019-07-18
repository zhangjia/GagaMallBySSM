package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface AddressMapper {
    /**
     * 根据用户ID查询该用户的所有收货地址
     * @param userId
     * @return
     */
    List<Map<String,Object>> queryByUserId(Integer userId);

    /**
     * 添加新的收货地址
     * 因为订单中的地址是根据收货地址的id查询的
     * 所以这里更改收货地址的信息的时候也是INSERT，而不是UPDATE
     * @param address
     * @return
     */
    int doInsert(Map<String,Object> address);

    /**
     * 根据收货地址ID查询该收货地址相关信息
     * @param addressId
     * @return
     */
    Map<String,Object> queryByAddressId(Integer addressId);

    /**
     * 更新收货地址信息状态
     * 场景1：设置默认地址
     * 场景2：删除收货地址
     * @param status
     * @param addressId
     * @param userId
     * @return
     */
    int updateAddressStatus(@Param("address_status") Integer status, @Param("address_id")Integer addressId, @Param("user_id")Integer userId);

    /**
     * 取消默认地址
     * @param userId
     * @return
     */
    int cancelDefaultAddress(Integer userId);
}
