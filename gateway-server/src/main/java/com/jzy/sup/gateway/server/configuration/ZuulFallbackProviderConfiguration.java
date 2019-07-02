package com.jzy.sup.gateway.server.configuration;


import com.jzy.sup.common.constant.ServiceResultConstant;
import com.jzy.sup.common.model.ResponseResult;
import com.jzy.sup.common.model.ServiceResult;
import com.jzy.sup.common.util.HttpUtil;
import com.jzy.sup.common.util.JSONUtil;
import com.jzy.sup.gateway.server.route.HttpClientRibbonCommandFactoryEX;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.zuul.RibbonCommandFactoryConfiguration;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.cloud.netflix.zuul.filters.route.RibbonCommandFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.Set;

/**
 * <b>功能：</b>ZuulFallbackProviderConfiguration<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Configuration
public class ZuulFallbackProviderConfiguration extends RibbonCommandFactoryConfiguration {

    @Bean
    public FallbackProvider getZuulFallbackProvider(){
        return new FallbackProvider(){

            @Override
            public String getRoute() {
                return "*";
            }

            @Override
            public ClientHttpResponse fallbackResponse(String route, Throwable cause) {
                return new ClientHttpResponse(){

                    @Override
                    public HttpHeaders getHeaders() {
                        return HttpUtil.headers();
                    }

                    @Override
                    public InputStream getBody() throws IOException {
                        ResponseResult rr = ResponseResult.result(ServiceResult.failure(ServiceResultConstant.SYS_NO_AVAILABLE_SERVICE));
                        return new ByteArrayInputStream(JSONUtil.toStr(rr).getBytes("utf-8"));
                    }

                    @Override
                    public HttpStatus getStatusCode() throws IOException {
                        return HttpStatus.OK;
                    }

                    @Override
                    public int getRawStatusCode() throws IOException {
                        return 0;
                    }

                    @Override
                    public String getStatusText() throws IOException {
                        return HttpStatus.OK.getReasonPhrase();
                    }

                    @Override
                    public void close() {

                    }
                };
            }
        };
    }


    @Bean
    public CorsFilter corsFilter() {
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        final CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("OPTIONS");
        config.addAllowedMethod("HEAD");
        config.addAllowedMethod("GET");
        config.addAllowedMethod("PUT");
        config.addAllowedMethod("POST");
        config.addAllowedMethod("DELETE");
        config.addAllowedMethod("PATCH");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }


}
