import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.api.mailcode.MailCode;
import io.zhangjia.mall.api.verificationCode.PhoneCode;
import io.zhangjia.mall.mapper.CommodityMapper;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws IOException {
        String a = "手机其实很好呀";
        String b = "好,手机";
        String[] sensitiveWordsArray=b.split(",");
            String sb="";
            for (String sensitiveWord: sensitiveWordsArray  ) {
                sb = a.replaceAll(sensitiveWord, "***");

            System.out.println(sb);
            }



    }
}