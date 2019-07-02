package com.jzy.sup.common.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * <b>功能：</b>SpringContextUtil<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class SpringContextUtil implements ApplicationContextAware {

    private static ApplicationContext ctx = null;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }

    public static Object getBean(String name){
       return ctx.getBean(name);
    }

    public static <T>T getBean(String name,Class<T> clazz){
        return ctx.getBean(name,clazz);
    }

    public static <T>T getBean(Class<T> clazz){
        return ctx.getBean(clazz);
    }

    public static ApplicationContext getCtx() {
        return ctx;
    }
}
