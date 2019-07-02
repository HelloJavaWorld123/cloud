package com.jzy.sup.config.server.config.repository.service.dao.impl;

import com.jzy.sup.config.server.config.repository.service.model.ConfigRepository;
import com.jzy.sup.config.server.config.repository.service.dao.EnviDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * <b>功能：</b>获取系统环境变量dao<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Repository
public class EnviDaoImpl implements EnviDao {

    @Value("${sup.mongodb.collection.name}")
    private String collectionName;

    private static String DEFAULT_PREFIX = "default";
    @Resource
    private MongoTemplate mongoTemplate;

    /**
     * <b>功能描述：</b>获取子服务的配置信息方法<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    @Override
    public ConfigRepository findByApplicationAndProfileAndLabel(String application, String profile, String label) {
        Query query = new Query(
                Criteria.where("application").in(application,DEFAULT_PREFIX)
                        .and("profile").is(profile)
                        .and("label").in(label,DEFAULT_PREFIX));
        query.with(new Sort(Sort.Direction.DESC,"level"));
        return mongoTemplate.findOne(query,ConfigRepository.class,collectionName);
    }

}
