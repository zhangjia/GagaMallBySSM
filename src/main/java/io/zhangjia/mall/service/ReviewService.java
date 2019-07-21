package io.zhangjia.mall.service;

import java.util.List;
import java.util.Map;

public interface ReviewService {
   List<Map<String,Object>> queryReview(Map<String,Object> params);
}
