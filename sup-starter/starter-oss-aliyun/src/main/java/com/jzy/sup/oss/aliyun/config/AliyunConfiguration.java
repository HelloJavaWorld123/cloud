package com.jzy.sup.oss.aliyun.config;


import com.aliyun.oss.OSSClient;
import com.jzy.sup.oss.aliyun.OSSStoreClient;
import com.jzy.sup.oss.aliyun.impl.AliyunOSSStoreClient;
import com.jzy.sup.oss.aliyun.properties.AliyunProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <b>功能：</b>配置阿里客户端实例<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190510&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Configuration
@EnableConfigurationProperties({AliyunProperties.class})
public class AliyunConfiguration {


    //阿里云客户端
    @Bean
    public OSSClient aliOSSClient(AliyunProperties aliyunProperties){
        return new OSSClient(aliyunProperties.getEndPoint(),aliyunProperties.getAccessKeyId(),aliyunProperties.getAccessKeySecret());
    }


    //封装的存储对象
    @Bean
    public OSSStoreClient ossStoreClient(OSSClient ossClient, AliyunProperties aliyunProperties){
        return new AliyunOSSStoreClient(ossClient,aliyunProperties);
    }

}
