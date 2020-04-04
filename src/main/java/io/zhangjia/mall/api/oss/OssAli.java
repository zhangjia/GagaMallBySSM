package io.zhangjia.mall.api.oss;


import com.alibaba.fastjson.JSON;
import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.PutObjectResult;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.net.URL;
import java.util.Date;
import java.util.UUID;

@Component
public class OssAli {
    //    public void createBucket(){
//        // Endpoint以杭州为例，其它Region请按实际情况填写。
//        String endpoint = "http://oss-cn-hangzhou.aliyuncs.com";
//// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
//        String accessKeyId = "<yourAccessKeyId>";
//        String accessKeySecret = "<yourAccessKeySecret>";
//        String bucketName = "<yourBucketName>";
//
//// 创建OSSClient实例。
//        OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
//
//// 创建存储空间。
//        ossClient.createBucket(bucketName);
//
//// 关闭OSSClient。
//        ossClient.shutdown();
//    }
    public static void main(String[] args) {

    }

    public String upload(MultipartFile file) {

        String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
// 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建RAM账号。
        String accessKeyId = "";
        String accessKeySecret = "";
        String bucketName = "";
        String objectName = file.getOriginalFilename();



        // 生成OSSClient，您可以指定一些参数，详见“SDK手册 > Java-SDK > 初始化”，
        // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/init.html?spm=5176.docoss/sdk/java-sdk/get-start
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        try {

            // 判断Bucket是否存在。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
            // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
            if (ossClient.doesBucketExist(bucketName)) {
                System.out.println("您已经创建Bucket：" + bucketName + "。");
            } else {
                System.out.println("您的Bucket不存在，创建Bucket：" + bucketName + "。");
                // 创建Bucket。详细请参看“SDK手册 > Java-SDK > 管理Bucket”。
                // 链接地址是：https://help.aliyun.com/document_detail/oss/sdk/java-sdk/manage_bucket.html?spm=5176.docoss/sdk/java-sdk/init
                ossClient.createBucket(bucketName);
            }


            // 上传文件流。
            InputStream inputStream = file.getInputStream();
            UUID uuid = UUID.randomUUID();
            System.out.println("uuid = " + uuid);
            String fileSuffix = objectName.substring(objectName.lastIndexOf("."));
            objectName = "img/web/gagamall/zhangjia/" + uuid + fileSuffix;
            System.out.println("objectNameAfter = " + objectName);
            PutObjectResult putObjectResult = ossClient.putObject(bucketName, objectName, inputStream);
            System.out.println("putObjectResult = " + JSON.toJSONString(putObjectResult));
            Date expiration = new Date(new Date().getTime() + 3600l * 1000 * 24 * 365 * 10);
            URL url = ossClient.generatePresignedUrl(bucketName, objectName, expiration);
            System.out.println("file = [" + JSON.toJSONString(ossClient) + "]");
            System.out.println("url = " + url);


            System.out.println("Object：" + objectName + "存入OSS成功。");
//https://zhangjia.oss-cn-qingdao.aliyuncs.com/img/web/gagamall/other/timg%20(1).gif
            return "https://zhangjia.oss-cn-qingdao.aliyuncs.com/" + objectName;

        } catch (OSSException oe) {
            oe.printStackTrace();
        } catch (ClientException ce) {
            ce.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ossClient.shutdown();
        }

        return null;

    }





}
