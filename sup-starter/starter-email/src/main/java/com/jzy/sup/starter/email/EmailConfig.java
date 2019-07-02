package com.jzy.sup.starter.email;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * <b>功能：</b>email配置信息<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190510&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Component
public class EmailConfig {
    /**
     * 发件人邮箱
     */

    private String emailFrom;
    /**
     * 收件人邮箱
     */

    private String emailTo;

    /**
     * 发送host
     */
    private String host;

    /**
     * 发送port
     */
    private String port;

    /**
     * 密码
     */
    private String password;


    public String getEmailFrom() {
        return emailFrom;
    }

    @Value("${spring.mail.username}")
    public void setEmailFrom(String emailFrom) {
        this.emailFrom = emailFrom;
    }

    public String getEmailTo() {
        return emailTo;
    }

    @Value("${spring.mail.to}")
    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getHost() {
        return host;
    }

    @Value("${spring.mail.host}")
    public void setHost(String host) {
        this.host = host;
    }

    public String getPassword() {
        return password;
    }

    @Value("${spring.mail.password}")
    public void setPassword(String password) {
        this.password = password;
    }

    @Value("${spring.mail.port}")
    public void setPort(String port) {
        this.port = port;
    }

    public String getPort() {
        return port;
    }
}
