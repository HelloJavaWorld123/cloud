package com.jzy.sup.config.server.config.repository.service;

import com.jzy.sup.config.server.config.repository.service.dao.EnviDao;
import com.jzy.sup.config.server.config.repository.service.model.ConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <b>功能：</b>获取子服务的配置信息服务<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Service
public class MongoRepositoryService {

    @Autowired
    private EnviDao enviDao;

    public ConfigRepository findByApplicationAndProfileAndLabel(String application, String profile, String label) {
        ConfigRepository configRepository = enviDao.findByApplicationAndProfileAndLabel(application, profile, label);
        return configRepository;
    }
}
