package io.zhangjia.mall.service.impl;



import io.zhangjia.mall.mapper.MenuMapper;
import io.zhangjia.mall.service.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("menuService")
public class MenuServiceImpl implements MenuService {
    @Autowired
    private MenuMapper menuMapper;



    @Override
    public List<Map<String,Object>> getMenu() {
        List<Map<String, Object>> maps = menuMapper.queryLevel1Menu();
//        System.out.println(maps);
//        System.out.println("JSON.toJSONString(maps) = " + JSON.toJSONString(maps));

        return maps;
    }


}
