package com.jzy.sup.framework.context;

import com.jzy.sup.framework.cache.model.EmpCache;
import com.jzy.sup.framework.exception.SupException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>功能：</b>这部分内容要除去   只允许保持用户信息在线程中 ，防止在server和dao在开发中使用request和response<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class RequestContext {

    private static ThreadLocal<ContextHolder> context = new ThreadLocal<>();

    public static void setContext(ContextHolder contextHolder){
        context.set(contextHolder);
    }

    public static HttpServletRequest getHttpRequest() {
        return getContext().getRequest();
    }

    public static HttpServletResponse getHttpResponse() {
        return getContext().getResponse();
    }

    public static EmpCache getEmpCache() {
        return getContext().getEmpCache();
    }

    private static ContextHolder getContext() {
        ContextHolder contextHolder = context.get();
        if (contextHolder == null) {
            throw new SupException("会话已过期!");
        }
        return contextHolder;
    }

    public static void remove(){
        context.remove();
    }
}
