package io.zhangjia.mall.mapper;


import java.util.List;
import java.util.Map;

public interface MenuMapper {
    /**
     * 查询一级菜单
     * @return
     */
    List<Map<String,Object>> queryLevel1Menu();

    /**
     * 查询二级菜单
     * 将查询结果作为一级菜单的一个属性，所以下面是灰色
     * @return
     */
    List<Map<String,Object>> queryLevel2Menu(Integer id);
}
