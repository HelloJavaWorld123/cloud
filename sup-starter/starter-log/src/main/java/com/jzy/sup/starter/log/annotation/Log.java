package com.jzy.sup.starter.log.annotation;



import com.jzy.sup.starter.log.constant.LogFunction;
import com.jzy.sup.starter.log.constant.LogModule;
import com.jzy.sup.starter.log.constant.LogOpt;
import com.jzy.sup.starter.log.constant.LogType;

import java.lang.annotation.*;

/**
 * <b>功能：</b>Log<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
public @interface Log {
    /**
     *  日志保存类型
     */
    LogType persistence()default LogType.File;
    /**
     * 日志模块
     */
    LogModule module()default LogModule.DEFAULT;
    /**
     * 日志功能
     */
    LogFunction fun()default LogFunction.DEFAULT;
    /**
     * 操作类型
     */
    LogOpt opt()default LogOpt.DEFAULT;
}
