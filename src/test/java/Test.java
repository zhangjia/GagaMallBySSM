import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.mapper.CommodityMapper;
import io.zhangjia.mall.mapper.MenuMapper;
import io.zhangjia.mall.mapper.UserMapper;
import io.zhangjia.mall.service.MenuService;
import io.zhangjia.mall.service.impl.MenuServiceImpl;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
//        MenuMapper bean = context.getBean(MenuMapper.class);
//        List<Map<String, Object>> maps =bean.queryLevel1Menu();
//        System.out.println(JSON.toJSONString(maps));
        /*for (Map<String, Object> map : maps) {
            System.out.println(JSON.toJSONString(map));
        }*/
//        System.out.println(maps);
//        System.out.println(JSON.toJSONString(maps));
//        MenuService bean = context.getBean(MenuService.class);
        UserMapper bean = context.getBean(UserMapper.class);
        Map<String,Object> map  = new HashMap<>();
        map.put("userName","gagaga");
        map.put("userPassword","1");
        map.put("userTel","15628791990");
        bean.doInsert(map);

    }
}
