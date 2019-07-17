import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.mapper.*;
import io.zhangjia.mall.service.MenuService;
import io.zhangjia.mall.service.impl.MenuServiceImpl;
import org.springframework.context.MessageSource;
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
//        UserMapper bean = context.getBean(UserMapper.class);
//        Map<String,Object> map  = new HashMap<>();
//        map.put("userName","gagaga");
//        map.put("userPassword","1");
//        map.put("userTel","15628791990");
//        bean.doInsert(map);

//        AddressMapper bean = context.getBean(AddressMapper.class);
////        Integer a[] = {2,3,4};
////        List<Map<String, Object>> maps = bean.queryCommodities4Settlement(1, a);
////        System.out.println("JSON.toJSONString(maps) = " + JSON.toJSONString(maps));
////        List<Map<String, Object>> map = bean.queryById(1);
////        List<Map<String,Object>> map = bean.queryUserId();
//        System.out.println(JSON.toJSONString(bean.queryByUserId(1)));

        Map<String,Object> map = new HashMap<>();

        System.out.println(map == null );
        System.out.println(map.isEmpty() );
    }
}
