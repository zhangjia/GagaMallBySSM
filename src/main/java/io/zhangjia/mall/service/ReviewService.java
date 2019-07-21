package io.zhangjia.mall.service;

import org.springframework.ui.Model;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Map;

public interface ReviewService {
   List<Map<String,Object>> queryReview(Map<String,Object> params);
   Integer addReview(Integer orderDetailId,String anonymity, String content,
                     Integer description, Integer service,
                     Integer express, Integer orderId,Integer userId,
                     Integer reviewGrade,
                     Integer commoditySpecsId,
                     String[] reviewImgs);
}
