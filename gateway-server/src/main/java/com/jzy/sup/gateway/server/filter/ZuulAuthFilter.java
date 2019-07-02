package com.jzy.sup.gateway.server.filter;


import com.jzy.sup.framework.auth.AccessToken;
import com.jzy.sup.framework.cache.model.EmpCache;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.servlet.http.HttpServletRequest;

/**
 * <b>功能：</b>上下文设值<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190528&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Component
public class ZuulAuthFilter extends ZuulFilter {

    //登录权限的微服务接口，需要将这个微服务完成以后进行加入

    private static String HEADER_PRIVILEGE = "HEADER_PRIVILEGE";


    private AntPathMatcher matcher = new AntPathMatcher();

    //private static final String[] excludeUris={"/sup/api/auth/login","/sup/api/external/**"};
   // private static final String[] excludeUris={"/sup/api/external/**","/sup/api/system/getNewKey/**","/sup/api/system/getNewCode/**","/sup/api/inventory/bsc/items/updateItemDailySale","/sup/api/takeout/platforms/open/back","/sup/api/takeout/platforms/applications/back", "/sup/api/inventory/bsc/test/test"};
    private static final String[] excludeUris={};
    @Override
    public Object run() {
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();

        log.info("-----进入网关-----");
        // 从请求头中获取员工登录标识
        String accessTokenUser = request.getHeader(AccessToken.EMP.getValue());

        EmpCache cacheEmp = new EmpCache();

//        if (StringUtils.isNotEmpty(accessTokenShop)) {
//
//            cacheShop = shopCacheRedis.getCacheShopByKey(accessTokenShop);
//
//            cacheEmp = employeeCacheRedis.getCacheEmpByKey(accessTokenUser);
//
//            log.info("拿到shop缓存对象:{},拿到emp缓存对象:{}", cacheShop, cacheEmp);
//
//        }else{
//
//            faild(ctx, JSONObj.toJSONString(ResponseResult.failure(ServiceResultConstant.AUTH_TOKEN_EXPIRE.getCode(), ServiceResultConstant.AUTH_TOKEN_EXPIRE.getMsg())));
//            log.error("shopToken不存在");
//        }

//        String p_str = null;
//        try {
//            p_str = new String(JSONUtil.toStr(privilege).getBytes("utf8"),"ISO8859-1");
//        } catch (UnsupportedEncodingException e) {
//            p_str = JSONUtil.toStr(privilege);
//        }
//        ctx.addZuulRequestHeader(ParamConstant.HEADER_PRIVILEGE,p_str);
        log.info("-----离开网关-----");
        return null;

    }

    @Override
    public boolean shouldFilter() {
        RequestContext context = RequestContext.getCurrentContext();
        String uri = context.getRequest().getRequestURI();
        boolean shouldFilter=false;
        for(String excludeUri:excludeUris){
            shouldFilter=shouldFilter|matcher.match(excludeUri,uri);
        }
        return !shouldFilter;// 是否执行该过滤器，此处为true，说明需要过滤
    }


    @Override
    public int filterOrder() {
        return 100;// 优先级为0，数字越大，优先级越低
    }

    @Override
    public String filterType() {
        return "pre";// 前置过滤器
    }


    private void faild(RequestContext ctx,String result){
        ctx.setSendZuulResponse(false);// 过滤该请求，不对其进行路由
        ctx.setResponseStatusCode(HttpStatus.OK.value());// 返回错误码
        ctx.setResponseBody(result);// 返回错误内容
        ctx.getResponse().setHeader("Content-Type", "application/json;charset=UTF-8");
        ctx.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        ctx.getResponse().setHeader("Access-Control-Allow-Methods", "POST, GET, DELETE, PUT");
        ctx.getResponse().setHeader("Access-Control-Allow-Headers", "Content-Type, x-requested-with, X-Custom-Header, SL-Access-Token,Tcsl-Slyun-Token,Tcsl-Loongboss-Token,tcsl-embed-anpay-token");
        ctx.getResponse().setHeader("Access-Control-Expose-Headers", "Content-Type, x-requested-with, X-Custom-Header, SL-Access-Token,Tcsl-Slyun-Token,Tcsl-Loongboss-Token,tcsl-embed-anpay-token");
    }


}