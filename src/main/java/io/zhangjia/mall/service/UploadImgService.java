package io.zhangjia.mall.service;


import org.springframework.web.multipart.MultipartFile;

public interface UploadImgService {
    String uploadImg(MultipartFile file);
}
