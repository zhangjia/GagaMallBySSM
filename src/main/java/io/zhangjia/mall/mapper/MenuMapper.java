package io.zhangjia.mall.mapper;


import java.util.List;
import java.util.Map;

public interface MenuMapper {
    List<Map<String,Object>> queryLevel1Menu();
    List<Map<String,Object>> queryLevel2Menu(Integer id);
}
