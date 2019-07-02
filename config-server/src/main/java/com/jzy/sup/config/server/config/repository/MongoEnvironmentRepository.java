package com.jzy.sup.config.server.config.repository;


import com.jzy.sup.config.server.config.repository.service.MongoRepositoryService;
import com.jzy.sup.config.server.config.repository.service.model.ConfigRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.environment.Environment;
import org.springframework.cloud.config.environment.PropertySource;
import org.springframework.cloud.config.server.environment.EnvironmentRepository;
import org.springframework.util.StringUtils;

/**
 * <b>功能：</b>获取子服务的配置信息方法<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class MongoEnvironmentRepository implements EnvironmentRepository{

    private final static String prefix="mongo:";
    @Autowired
    private MongoRepositoryService configRepositoryService;
    private final Logger logger = LogManager.getLogger(this.getClass());

    @Override
    public Environment findOne(String application, String profile, String label) {

        if (StringUtils.isEmpty(application) || StringUtils.isEmpty(profile))
            return null;
        ConfigRepository configRepositorie = configRepositoryService.findByApplicationAndProfileAndLabel(application, profile, label);
        logger.info(configRepositorie );
        if (configRepositorie != null) {
            Environment environment = new Environment(application, StringUtils.commaDelimitedListToStringArray(profile),configRepositorie.getLabel(), configRepositorie.getVersion(),null);
            environment.add(new PropertySource(configRepositorie.getLabel()+"/"+configRepositorie.getApplication()+"-"+configRepositorie.getProfile(), configRepositorie.getConf()));
            for (String profile_:configRepositorie.getConfRef())
            {
                configRepositorie = configRepositoryService.findByApplicationAndProfileAndLabel(application, profile_, label);
                if(configRepositorie!=null) {
                    environment.add(new PropertySource(configRepositorie.getLabel()+"/"+configRepositorie.getApplication()+"-"+configRepositorie.getProfile(), configRepositorie.getConf()));
                } else {
                    /** 加入日志 */
                    logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                    logger.info(String.format("请到%s环境的mongodb中执行下面的语句：", label));
                    logger.info(String.format("db.getCollection('sys_envi').find({'application':'%s','profile':'%s','label':'%s'})", application, profile, label));
                    logger.info("如果没有结果请执行下面的语句：");
                    logger.info(String.format("db.getCollection('sys_envi').find({'application':'%s','profile':'%s'})", application, profile, label));
                    logger.info("执行结果为出问题的服务。查看json中的conf-ref内的配置。");
                    logger.info("该服务缺少配置：" + profile_);
                    logger.info("执行下面语句查询，如果没有请加上。");
                    logger.info(String.format("db.getCollection('sys_envi').find({'profile':'%s'})", profile_));
                    logger.info("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
                }

            }
            return environment;
        }
        return new Environment(application, profile);
    }
}
