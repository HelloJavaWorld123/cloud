package com.jzy.sup.oss.aliyun.impl;


import com.aliyun.oss.ClientException;
import com.aliyun.oss.OSSClient;
import com.aliyun.oss.OSSException;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.OSSObject;
import com.jzy.sup.oss.aliyun.OSSStoreClient;
import com.jzy.sup.oss.aliyun.properties.AliyunProperties;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * <b>功能：</b>阿里云文件存储实现类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190510&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class AliyunOSSStoreClient implements OSSStoreClient {

    private OSSClient ossClient;

    private AliyunProperties aliyunProperties;



    public AliyunOSSStoreClient(OSSClient ossClient,AliyunProperties aliyunProperties){
        this.ossClient = ossClient;
        this.aliyunProperties = aliyunProperties;
    }



    @Override
    public String putObject(String key, InputStream input) throws IOException {

        ossClient.putObject(aliyunProperties.getBucketName(), key, input);

        return aliyunProperties.getOssUri() + key;
    }

    @Override
    public String putObjectAndDirectory(String key, InputStream input, String directory) throws IOException {

        String dir_key = directory+"/"+key;

        ossClient.putObject(aliyunProperties.getBucketName(), dir_key, input);

        return aliyunProperties.getOssUri() + dir_key;
    }

    @Override
    public InputStream getObject(String key) throws OSSException, ClientException {

        OSSObject ossObject = ossClient.getObject(new GetObjectRequest(aliyunProperties.getBucketName(), key));
        return ossObject.getObjectContent();
    }

    @Override
    public void getObject(String key, String fileName) throws OSSException, ClientException {
        ossClient.getObject(new GetObjectRequest(aliyunProperties.getBucketName(), key), new File(fileName));
    }

    @Override
    public InputStream getObjectAndDirectory(String key, String directory) throws OSSException, ClientException {
        String dir_key = directory+"/"+key;
        OSSObject ossObject = ossClient.getObject(new GetObjectRequest(aliyunProperties.getBucketName(), key));
        return ossObject.getObjectContent();
    }

    @Override
    public void getObjectAndDirectory(String key, String fileName, String directory) throws OSSException, ClientException {
        String dir_key = directory+"/"+key;
        ossClient.getObject(new GetObjectRequest(aliyunProperties.getBucketName(), dir_key), new File(fileName));
    }
}
