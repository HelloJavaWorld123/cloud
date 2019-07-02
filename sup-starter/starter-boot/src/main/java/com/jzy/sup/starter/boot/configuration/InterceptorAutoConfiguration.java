package com.jzy.sup.starter.boot.configuration;

import com.jzy.sup.common.util.SpringContextUtil;
import com.jzy.sup.framework.interceptor.BaseActionInterceptor;
import com.jzy.sup.framework.interceptor.annotation.ExcludePathPattern;
import com.jzy.sup.framework.interceptor.annotation.PathPattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * <b>功能：</b>这里写功能描述<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@EnableWebMvc
@Configuration
@ConditionalOnClass(BaseActionInterceptor.class)
@AutoConfigureAfter({BaseActionInterceptor.class,SpringContextUtil.class})
public class InterceptorAutoConfiguration extends WebMvcConfigurerAdapter {

    private static final String PATH_EXTENAL="/extenal/*";

    @Autowired(required = false)
    private List<BaseActionInterceptor> interceptors = new ArrayList<>();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        InterceptorRegistration reg;
        for(BaseActionInterceptor interceptor :sort(interceptors)){
            reg = registry.addInterceptor(interceptor);
            if(interceptor.getClass().getAnnotation(PathPattern.class)!=null)
                reg.addPathPatterns(interceptor.getClass().getAnnotation(PathPattern.class).path());
            if(interceptor.getClass().getAnnotation(ExcludePathPattern.class)!=null)
                reg.excludePathPatterns(interceptor.getClass().getAnnotation(ExcludePathPattern.class).path());
        }
    }

    /**
     * 对拦截器进行升序排序，sequence越小优先级越高
     * @param interceptors 可排序的拦截器对象，建议使用BaseActionInterceptor实现类
     * @return
     */
    private List<BaseActionInterceptor> sort( List <BaseActionInterceptor> interceptors){
        Collections.sort(interceptors, (interceptor1, interceptor2) -> {
            if(interceptor1.sequence()>interceptor2.sequence())
                return 1;
            else
                return -1;
        });
        return interceptors;
    }

}
