package com.jzy.sup.starter.swagger;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * 配置Swagger不被SpringSecurity拦截的配置类
 */
@Configuration
public class WebMvcSwaggerConfig /*implements WebMvcConfigurer {*/
        extends WebMvcConfigurationSupport {

    @Bean
    LoginInterceptor localInterceptor() {
        return new LoginInterceptor();
    }

    /**
     * 设置不过滤和拦截的URL地址
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localInterceptor())
                .addPathPatterns("/**")
                .excludePathPatterns("/loginExit/login")
                .excludePathPatterns("/api/login_tyg/register", // 注册接口"
                        "/api/userserver_xxj/user/registerUser", // 注册Fegin接口
                        "/auth/register",
                        "/api/userserver_xxj/user/findUserByUserName",
                        "/v2/api-docs",  // swagger api json
                        "/swagger-resources/configuration/ui",  // 用来获取支持的动作
                        "/swagger-resources",  // 用来获取api-docs的URI
                        "/swagger-resources/configuration/security", // 安全选项
                        "/webjars/springfox-swagger-ui/**", // CSS和JS
                        "/swagger-ui.html",    // 安全选项
                        "/swagger-ui.html#",   // 安全选项
                        "/swagger-ui.html#/",  // 安全选项
                        "/swagger-ui.html#/**");
    }

    @Override
    protected void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html#")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
