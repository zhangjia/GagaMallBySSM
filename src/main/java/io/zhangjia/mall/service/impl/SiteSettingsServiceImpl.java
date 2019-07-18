package io.zhangjia.mall.service.impl;


import io.zhangjia.mall.mapper.SiteSettingsMapper;
import io.zhangjia.mall.service.SiteSettingsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("siteSettingsServiceImpl")
public class SiteSettingsServiceImpl implements SiteSettingsService {
    @Autowired
    private SiteSettingsMapper siteSettingsMapper;
    @Override
    public String getValue(String key) {
        return siteSettingsMapper.queryByKey(key);
    }

    @Override
    public Integer editValue(String key, String value) {
        System.out.println("key + \"---\"+ value = " + key + "---"+ value);
        return siteSettingsMapper.doUpdate(key,value);
    }
}
