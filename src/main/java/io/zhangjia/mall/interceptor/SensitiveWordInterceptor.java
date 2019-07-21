package io.zhangjia.mall.interceptor;


import io.zhangjia.mall.service.MenuService;
import io.zhangjia.mall.service.SiteSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Component
public class SensitiveWordInterceptor implements HandlerInterceptor {
    @Autowired
    private SiteSettingsService siteSettingsService;
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("Interceptor.postHandle");
        Map<String, Object> model = modelAndView.getModel();
        System.out.println("hashCode" + model.hashCode());
        List<Map<String, Object>>  contents = ( List<Map<String, Object>> )model.get("reviews");
        String sensitiveWords = siteSettingsService.getValue("敏感词");
        String[] sensitiveWordsArray=sensitiveWords.split(",");
        System.out.println("3" + Arrays.toString(sensitiveWordsArray));
        for (Map<String,Object> content: contents ) {
            String reviewContent = content.get("review_content").toString();
            String sb="";
            for (String sensitiveWord: sensitiveWordsArray  ) {
                 sb = reviewContent.replaceAll(sensitiveWord, "***");
                 reviewContent = sb;
            }
            content.put("review_content",reviewContent);
        }

        model.put("reviews",contents);
    }
}
