import io.zhangjia.mall.api.mailcode.MailCode;
import io.zhangjia.mall.api.verificationCode.PhoneCode;
import io.zhangjia.mall.mapper.CommodityMapper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        CommodityMapper bean = context.getBean(CommodityMapper.class);
        Map<String,Object> map = new HashMap<>();
        map.put("commodityName","apple");
        List<Map<String, Object>> query = bean.query(map);
        System.out.println("query = " + query);
        for (Map<String, Object> stringObjectMap : query) {
            System.out.println(stringObjectMap);

        }
    }
}