import io.zhangjia.mall.api.mailcode.MailCode;
import io.zhangjia.mall.api.verificationCode.PhoneCode;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
       /* PhoneCode phoneCode = new PhoneCode();
        phoneCode.sendCode("15628791997");*/

        MailCode mailCode = new MailCode();
        mailCode.sample("zhangjia@188.com");
    }
}