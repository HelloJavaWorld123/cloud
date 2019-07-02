package com.jzy.sup.gateway.server.route;

import org.springframework.cloud.netflix.ribbon.SpringClientFactory;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;
import org.springframework.cloud.netflix.zuul.filters.route.FallbackProvider;
import org.springframework.cloud.netflix.zuul.filters.route.apache.HttpClientRibbonCommandFactory;

import java.util.Set;

/**
 * <b>功能：</b>HttpClientRibbonCommandFactoryEX<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class HttpClientRibbonCommandFactoryEX extends HttpClientRibbonCommandFactory {

    private static String GLOBAL_FALLBACK_PROVIDER_FREFIX = "*";

    public HttpClientRibbonCommandFactoryEX(SpringClientFactory clientFactory, ZuulProperties zuulProperties,
                                            Set<FallbackProvider> fallbackProviders) {
        super(clientFactory, zuulProperties, fallbackProviders);
    }

    @Override
    protected FallbackProvider getFallbackProvider(String route) {
        FallbackProvider provider = super.getFallbackProvider(GLOBAL_FALLBACK_PROVIDER_FREFIX);
        return provider == null ? super.getFallbackProvider(route) : provider;
    }
}
