package com.jzy.sup.eureka.server;


import lombok.extern.log4j.Log4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.PropertySource;

/**
 * <b>功能：</b>Eureka<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190522&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;田星亮&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@SpringBootApplication
@EnableEurekaServer
@EnableDiscoveryClient
@PropertySource("classpath:config/bootstrap.properties")
public class EurekaApplication {
    /**
     * <b>功能描述：</b>主启动类<br>
     * <b>修订记录：</b><br>
     * <li>20190522&nbsp;&nbsp;|&nbsp;&nbsp;田星亮&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static void main(String[] args) {
        SpringApplication.run(EurekaApplication.class, args);
    }
}
