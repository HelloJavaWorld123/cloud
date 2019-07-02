package com.jzy.sup.framework.cache.service;

import com.jzy.sup.framework.cache.model.EmpCache;

/**
 * <b>功能：</b>员工信息缓存<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190528&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface CacheEmpService {

    /**
     * <b>功能描述：</b>获取缓存的员工登录信息<br>
     * <b>修订记录：</b><br>
     * <li>20190507&nbsp;&nbsp;|&nbsp;&nbsp;邓冲&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    EmpCache getCacheEmpByKey(String key);

}
