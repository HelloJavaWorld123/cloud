package com.jzy.sup.starter.boot.interceptor;


import com.jzy.sup.framework.auth.AccessToken;
import com.jzy.sup.framework.cache.model.EmpCache;
import com.jzy.sup.framework.cache.service.CacheEmpService;
import com.jzy.sup.framework.context.ContextHolder;
import com.jzy.sup.framework.context.RequestContext;
import com.jzy.sup.framework.interceptor.BaseActionInterceptor;
import com.jzy.sup.framework.interceptor.annotation.ExcludePathPattern;
import com.jzy.sup.framework.interceptor.annotation.PathPattern;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>功能：</b>登录认证拦截器<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@Component
@PathPattern(path = "/**")
@ExcludePathPattern(path = {"/api/system/getNewKey/**"})
public class AuthHandlerInterceptor extends BaseActionInterceptor {

    @Resource
    private CacheEmpService cacheEmpService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        log.info("-----进入AuthHandlerInterceptor-----");

        //一些不需要拦截的资源
        //spring静态资源处理不拦截
        if (handler instanceof ResourceHttpRequestHandler) {
            return true;
        }

        //将请求上线文进行线程保存
        ContextHolder holder = new ContextHolder();
        holder.setRequest(request);
        holder.setResponse(response);
        RequestContext.setContext(holder);

        String key = request.getHeader(AccessToken.EMP.getValue());

        log.debug("HEADER_PRIVILEGE：{}", key);

        //判断header里面是否有token信息
        if(!StringUtils.isEmpty(key)){
            // 从redis中获取员工的token信息
            EmpCache empCache = cacheEmpService.getCacheEmpByKey(key);
            holder.setEmpCache(empCache);
            log.debug("PRIVILEGE正常");
            return true;
        }

        log.debug("-----不符合规则，被拦截-----");
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        RequestContext.remove();
    }

    @Override
    public int sequence() {
        return 0;
    }
}
