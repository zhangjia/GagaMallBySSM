package io.zhangjia.mall.api.verificationCode;

import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.exceptions.ServerException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;


public class PhoneCode {
    public static void main(String[] args) {
        new PhoneCode().sendCode("15628791997");
    }

    public String sendCode(String tel) {
        DefaultProfile profile = DefaultProfile.getProfile("default", "",
                "");
        IAcsClient client = new DefaultAcsClient(profile);

        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.POST);
        request.setDomain("dysmsapi.aliyuncs.com");
        request.setVersion("2017-05-25");
        request.setAction("SendSms");
        request.putQueryParameter("PhoneNumbers", tel);
        request.putQueryParameter("SignName", "嘎嘎商城");
        request.putQueryParameter("TemplateCode", "SMS_168725713");
        String random = RandomStringUtils.random(4, "0123456789");
        request.putQueryParameter("TemplateParam", "{\"code\":\"" + random + "\"}");
        try {
            CommonResponse response = client.getCommonResponse(request);
            System.out.println(response.getData());
        } catch (ServerException e) {
            e.printStackTrace();
        } catch (ClientException e) {
            e.printStackTrace();
        }
        return random;
    }
}