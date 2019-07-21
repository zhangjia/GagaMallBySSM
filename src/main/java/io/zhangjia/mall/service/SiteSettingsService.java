package io.zhangjia.mall.service;

public interface SiteSettingsService {
    String getValue(String key);
    Integer editValue(String key, String value);

}
