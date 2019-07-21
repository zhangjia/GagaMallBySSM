package io.zhangjia.mall.service.impl;

import io.zhangjia.mall.mapper.ReviewMapper;
import io.zhangjia.mall.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("reviewService")
public class ReviewServiceImpl implements ReviewService {
    @Autowired
    private ReviewMapper reviewMapper;
    @Override
    public List<Map<String, Object>> queryReview(Map<String, Object> params) {
        return reviewMapper.query(params);
    }
}
