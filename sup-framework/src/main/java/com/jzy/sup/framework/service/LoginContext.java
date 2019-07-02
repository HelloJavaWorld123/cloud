package com.jzy.sup.framework.service;



import com.jzy.sup.framework.auth.AccessToken;
import com.jzy.sup.framework.cache.model.EmpCache;
import com.jzy.sup.framework.context.ContextHolder;
import com.jzy.sup.framework.context.RequestContext;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <b>功能：</b>LoginContext<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface LoginContext {

    /**
     * <b>功能描述：</b>获取请求request对象<br>
     * <b>修订记录：</b><br>
     * <li>20190528&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     *
     * @return HttpServletRequest
     */
    default HttpServletRequest getRequest() {
        return RequestContext.getHttpRequest();
    }

    /**
     * <b>功能描述：</b>获取响应的response对象<br>
     * <b>修订记录：</b><br>
     * <li>20190528&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     *
     * @return HttpServletResponse
     */
    default HttpServletResponse getResponse() {
        return RequestContext.getHttpResponse();
    }

    /**
     * <b>功能描述：</b>获取登录用户<br>
     * <b>修订记录：</b><br>
     * <li>20190528&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    default EmpCache getLoginEmp() {
        return RequestContext.getEmpCache();
    }

    /**
     * <b>功能描述：</b>获取渠道商id<br>
     * <b>修订记录：</b><br>
     * <li>20190528&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    default Integer getDealerId() {
        return getLoginEmp().getDealerId();
    }

    /**
     * <b>功能描述：</b>获取登录用户<br>
     * <b>修订记录：</b><br>
     * <li>20190528&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    default Long getEmpId() {
        return getLoginEmp().getEmpId();
    }

}
