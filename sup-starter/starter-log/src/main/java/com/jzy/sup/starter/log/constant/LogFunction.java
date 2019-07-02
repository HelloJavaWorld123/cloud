package com.jzy.sup.starter.log.constant;

/**
 * <b>功能：</b><br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public enum LogFunction {

    DEFAULT(-1, ""),
    SYS_MENU(0, "菜单"),
    SYS_ORG(1, "组织机构"),
    SYS_ROLE(2, "角色"),
    SYS_ORG_RULE(3, "组织角色关系"),
    SYS_USER(4, "用户"),
    AUTH_LOGIN(5, "登录"),
    AUTH_LOGOUT(10, "登出");
    private int code;

    private String name;

    LogFunction(int code, String name) {

        this.code = code;
        this.name = name;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }
}
