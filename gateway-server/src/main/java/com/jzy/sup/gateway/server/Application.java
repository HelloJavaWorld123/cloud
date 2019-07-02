package com.jzy.sup.gateway.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;


/**
 * <b>功能：</b>网关微服务Application<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@EnableZuulProxy
@SpringBootApplication
@ComponentScan(basePackages = "com.jzy")
@RestController
@PropertySource("classpath:config/bootstrap.properties")
@EnableWebMvc
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.jzy"})
public class Application {

	/**
	 * <b>功能描述：</b>服务启动主方法<br>
	 * <b>修订记录：</b><br>
	 * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
	 */
	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(Application.class);

		application.run(args);
	}

}