package com.jzy.sup.config.server.config.repository.service.model;

import org.springframework.data.mongodb.core.index.CompoundIndex;
import org.springframework.data.mongodb.core.index.CompoundIndexes;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <b>功能：</b>各个子服务模块数据结构mode<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Document()
@CompoundIndexes({
        @CompoundIndex(name = "age_idx", def = "{'application': 1, 'profile': 1,'label': 1}")
})
public class ConfigRepository implements Serializable{


    private String application;   //应用名称
    private String profile;       //应用模块
    private String label;         //应用环境
    private String version;       //应用版本

    private Map<String,String> conf =new HashMap<>();

    public Map<String, String> getConf() {
        return conf;
    }

    @Field("conf-ref")
    private List<String> confRef = new ArrayList();

    public void setConf(Map<String, String> conf) {
        this.conf = conf;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public List<String> getConfRef() {
        return confRef;
    }

    public void setConfRef(List<String> confRef) {
        this.confRef = confRef;
    }
}
