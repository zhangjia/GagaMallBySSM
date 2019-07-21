package io.zhangjia.mall.service.impl;

import com.alibaba.fastjson.JSON;
import io.zhangjia.mall.mapper.CommodityMapper;
import io.zhangjia.mall.mapper.ImgMapper;
import io.zhangjia.mall.mapper.OrderMapper;
import io.zhangjia.mall.mapper.ReviewMapper;
import io.zhangjia.mall.service.ReviewService;
import org.apache.ibatis.annotations.Insert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;
    @Autowired
    private CommodityMapper commodityMapper;

    @Autowired
    private ImgMapper imgMapper;

    @Autowired
    private OrderMapper orderMapper;
    @Override
    public List<Map<String, Object>> queryReview(Map<String, Object> params) {
        return reviewMapper.query(params);
    }

    @Override
    public  Integer addReview(Integer orderDetailId,String anonymity, String content,
                              Integer description, Integer service,
                              Integer express, Integer orderId,Integer userId,
                              Integer reviewGrade,
                              Integer commoditySpecsId,
                              String[] reviewImgs) {
        Map<String,Object> params = new HashMap<>();
        /*#{review_content}, #{user_id}, #{commodity_id},
                   #{commodity_specs_id}, #{order_id}, #{review_grade},
                   #{review_express}, #{review_service}, #{review_description},
                   #{review_is_anonymity}*/
        Integer result= 1;
        params.put("review_content",content);
        params.put("user_id",userId);
        Integer commodityId;
        Map<String, Object> commodity = commodityMapper.queryCommoditySpecsBySpecsId(commoditySpecsId);
        if(commodity != null) {
            commodityId =  Integer.parseInt(commodity.get("commodity_id").toString());
            params.put("commodity_id",commodityId);
        }

        params.put("commodity_specs_id",commoditySpecsId);
        params.put("order_id",orderId);
        params.put("review_grade",reviewGrade);
        params.put("review_express",express);
        params.put("review_service",service);
        params.put("review_description",description);
        params.put("order_detail_id",orderDetailId);
        params.put("review_is_anonymity","on".equals(anonymity)?1:0);
        params.put("review_id","on".equals(anonymity)?1:0);
        System.out.println("anonymity = [" + anonymity + "], content = [" + content + "], description = [" + description + "], service = [" + service + "], express = [" + express + "], orderId = [" + orderId + "], userId = [" + userId + "], reviewGrade = [" + reviewGrade + "], commoditySpecsId = [" + commoditySpecsId + "], reviewImgs = [" + reviewImgs + "]");
        System.out.println("JSON.toJSONString(review) = " + JSON.toJSONString(params));
        result  = reviewMapper.doInsert(params);
        System.out.println("JSON.toJSONString(review2) = " + JSON.toJSONString(params));
        Integer reviewId = Integer.parseInt(params.get("review_id").toString());
        int i = 1;

        for (String img:reviewImgs) {
             result *= imgMapper.doInsert(img, reviewId, i, "评价图");
            i++;
         }

//        result *= orderMapper.doUpdate4OrderStatus(orderId,6,userId);
        result *= orderMapper.doUpdate4OrderDetailStatus(orderDetailId,6,orderId);
        return result;

    }
}
