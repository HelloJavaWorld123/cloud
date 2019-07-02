package com.jzy.sup.config.server.config.repository.service.dao;

import com.jzy.sup.config.server.config.repository.service.model.ConfigRepository;
/**
 * Created by Administrator on 4月26日.
 */
/**
 * <b>功能：</b>获取系统环境变量dao接口<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface EnviDao {

    /**
     * <b>功能描述：</b>获取子服务的配置信息接口<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    ConfigRepository findByApplicationAndProfileAndLabel(String application, String profile, String label);
}
