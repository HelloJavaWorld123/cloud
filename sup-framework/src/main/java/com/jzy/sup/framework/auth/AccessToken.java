package com.jzy.sup.framework.auth;

/**
 * <b>功能：</b>登录成功访问标识，此标识登录成功后请求接口必须加在HTTP请求头中<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public enum AccessToken {
    /**
     * 员工登录成功标识
     */
    EMP("Access-Token-Emp"),

    /**
     * 客户端版本号
     */
    APP_VERSION("APP-Version-Code");

    /**
     * 错误类型 *
     */
    private String value;

    AccessToken(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

}
