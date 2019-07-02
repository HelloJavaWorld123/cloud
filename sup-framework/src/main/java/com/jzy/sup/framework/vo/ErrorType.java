package com.jzy.sup.framework.vo;

/**
 * <b>功能：</b>错误类型枚举类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public enum ErrorType {

    /**
     * 业务逻辑正确
     */
    SUCCESS(0),
    /**
     * 业务处理失败
     */
    FAILURE(-1),
    /**
     * 店铺会话过期
     */
    SHOP_SESSION_EXPIRED(-2),

    /**
     * 员工会话过期
     */
    EMP_SESSION_EXPIRED(-22),

    /**
     * Token重复
     */
    REPEAT_TOKEN(-3),

    /**
     * 版本错误
     */
    VERSION_CODE_ERROR(-4),

    /**
     * 老用户登录提示修改密码
     */
    OLDSHOP_CHANGE_PWD(-5);

    /**
     * 错误类型 *
     */
    private int value;

    ErrorType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
