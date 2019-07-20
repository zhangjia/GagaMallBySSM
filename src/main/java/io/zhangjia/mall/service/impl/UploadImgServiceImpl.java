package io.zhangjia.mall.service.impl;

import io.zhangjia.mall.api.oss.OssAli;
import io.zhangjia.mall.mapper.ImgMapper;
import io.zhangjia.mall.service.UploadImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
@Service
public class UploadImgServiceImpl implements UploadImgService {

    @Autowired
    private OssAli ossAli;


    @Override
    public String uploadImg(MultipartFile file) {
        String url;
        try {
            url = ossAli.upload(file);
            System.out.println("uplocadimgService = " + url);
            return url == null ? null:url;
        } catch (Exception e) {
            return null;
        }


    }
}
