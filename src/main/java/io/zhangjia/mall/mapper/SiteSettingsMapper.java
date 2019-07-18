package io.zhangjia.mall.mapper;

import org.apache.ibatis.annotations.Param;

public interface SiteSettingsMapper {
    String queryByKey(String key);
    Integer doUpdate(@Param("key") String key, @Param("value")String value);
}
