package com.education.api;

import cn.hutool.json.JSONUtil;
import com.qcloud.cos.COSClient;
import com.qcloud.cos.ClientConfig;
import com.qcloud.cos.auth.BasicCOSCredentials;
import com.qcloud.cos.auth.COSCredentials;
import com.qcloud.cos.http.HttpMethodName;
import com.qcloud.cos.model.GeneratePresignedUrlRequest;
import com.qcloud.cos.model.PutObjectResult;
import com.qcloud.cos.region.Region;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.net.URL;
import java.util.Date;

/**
 * @author zengjintao
 * @create_at 2021年11月1日 0001 14:13
 * @since version 1.6.5
 */
public class OssTest {

    String secretId = "AKIDSjlW1GEvLUs3xax4tRijX4LL5XioiNmw";
    String secretKey = "wc3D8oEPKzPZVBblfKjtnTt3xc1A3oJY";
    String bucketName = "education-1253719016";

    private COSClient cosClient;

    @Before
    public void initClient() {
        COSCredentials cred = new BasicCOSCredentials(secretId, secretKey);
        Region region = new Region("ap-nanjing");
        ClientConfig clientConfig = new ClientConfig(region);
        // 如果要生成一个使用 https 协议的 URL，则设置此行，推荐设置。
        // clientConfig.setHttpProtocol(HttpProtocol.https);
        // 生成 cos 客户端。
        this.cosClient = new COSClient(cred, clientConfig);
    }


    @Test
    public void generateUrl() {
      //  cosClient.putObject("education-1253719016", "/images/test.png", new File("C:\\Users\\Administrator\\Desktop\\6406823e-4814-48b9-ba3c-eff85ce2374d_180x180.png"));
        // 此处的key为对象键，对象键是对象在存储桶内的唯一标识
        String key = "/images/test.png";
        GeneratePresignedUrlRequest req =
                new GeneratePresignedUrlRequest(bucketName, key, HttpMethodName.GET);
        // 设置签名过期时间(可选), 若未进行设置, 则默认使用 ClientConfig 中的签名过期时间(1小时)
        // 可以设置任意一个未来的时间，推荐是设置 10 分钟到 3 天的过期时间
        // 这里设置签名在半个小时后过期
        Date expirationDate = new Date(System.currentTimeMillis() + 10000);
        req.setExpiration(expirationDate);

        URL url = cosClient.generatePresignedUrl(req);
        System.out.println(url.toString());
        cosClient.shutdown();
    }

    @Test
    public void putObject() {
        PutObjectResult putObjectResult = cosClient.putObject(bucketName, "/images/test1.png",
                new File("C:\\Users\\Administrator\\Desktop\\6406823e-4814-48b9-ba3c-eff85ce2374d_180x180.png"));
        System.out.println(JSONUtil.toJsonStr(putObjectResult));
    }
}
