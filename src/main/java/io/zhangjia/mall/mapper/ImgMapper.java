package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
public interface ImgMapper {
    /**
     * 查询每个商品的商品图
     * @param commodityId 要查询的商品id
     * @return
     */
    List<Map<String,Object>> queryCommodityImgs(Integer commodityId);
    /**
     * 查询每个商品的商品详情图
     * @param commodityId 要查询的商品id
     * @return
     */
    List<Map<String,Object>> queryCommodityDetailImgs(Integer commodityId);


    List<Map<String,Object>> queryReviewImgs(Integer commodityId);

    Map<String,Object> queryAvatarImgs(Integer userId);

    /**
     * 插入图片
     * @param imgUrl
     * @param belong
     * @param order
     * @param type
     * @return
     */
    Integer doInsert(@Param("img_url") String imgUrl, @Param("img_belong") Integer belong,
                     @Param("img_order") Integer order/*顺序*/, @Param("img_type") String type);

    Integer doUpdateImgStatus(@Param("img_belong") Integer imgBelong,@Param("img_type") String img_type);
}
