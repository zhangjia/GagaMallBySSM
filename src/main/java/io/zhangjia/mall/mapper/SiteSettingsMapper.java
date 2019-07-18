package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

public interface SiteSettingsMapper {
    /**
     * 根据key查找value
     * @param key
     * @return
     */
    String queryByKey(String key);

    /**
     * 根据key更新value
     * @param key
     * @param value
     * @return
     */
    Integer doUpdate(@Param("key") String key, @Param("value")String value);
}
