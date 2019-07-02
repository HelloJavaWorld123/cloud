package com.jzy.sup.common.constant;


import com.alibaba.fastjson.annotation.JSONType;
import com.jzy.sup.common.util.JSONUtil;

/**
 * <b>功能：</b>Service层返回结果常量<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@JSONType(serializeEnumAsJavaBean = true)
public enum ServiceResultConstant {

    //系统异常 -10000开头
    SYS_WARN("-1", "非正常操作，后果你懂的"),
    SYS_SUCCESS("0", "成功"), SYS_ERROR("-10001", "系统异常"),
    SYS_ERROR_DATA_STATE_CHANGED("-10002", "系统状态已改变"),
    SYS_USERNAME_OR_PASSWORD("-10003", "用户名或密码错误"),
    SYS_NO_LOGIN("-10004", "未登录"),
    SYS_NO_DATA("-10005", "数据错误"),
    AUTH_TOKEN_EXPIRE("-10006", "尚未登陆"),
    NO_QUALIFIED_DATA("-10007", "无符合条件数据"),
    OPERATION_FAILURE("-10008", "操作失败"),
    NO_REQUEST_PARAM("-10010", "请求参数为空"),
    ERROR_PARAM_FROMAT("-10011", "请求参数格式错误"),
    NO_USER_IN_CONTEXT("-10012", "上下文中不存在用户信息"),
    NO_ROLE_IN_CONTEXT("-10013", "上下文中不存在角色信息"),
    SYS_NO_AVAILABLE_SERVICE("-10014", "没有可用的服务"),
    NO_USERNAME_IN_CONTEXT("-10015", "上下文信息中用户名为空"),
    NO_ORG_IN_CONTEXT("-10016", "上下文中不存在组织信息"),
    NO_USER_TYPE_IN_CONTEXT("-10017", "上下文中不存在用户类型信息"),
    NO_SERVICER_IN_CONTEXT("-10018", "上下文中不存在服务商信息"),
    NO_EXIST_USER_NAME("-10019", "账号不存在"),
    THE_USER_IS_STOPED("-10020", "账号已被停用"),
    FIND_RESULT_IS_NULL("-10021", "数据库查询结果为空"),

    //系统管理 -20000开头
    NO_EXIST_ORG("-20001", "组织机构不存在"),
    NO_EXIST_USER("-20002", "用户不存在"),
    ALREADY_EXIST_ORG("-20003", "组织机构编码已存在"),
    PARENT_ORG_MISMATCH("-20004", "父组织编码不匹配"),
    USER_ALREADY_EXIST("-20005", "用户已存在"),
    ORIGINAL_PASSWORD_ERROR("-20006", "系统密码不正确"),
    PASSWORD_NOT_MATCH("-20007", "密码不一致"),



    //服务对接 -70000开头
    NO_SERIAL_NUM_KEY("-70001", "没有参数serialNum（流水号），请检查JSON入参。"),

    NO_AUTH("-80001", "您没有权限进行操作此项！");

    private String code;

    private String msg;

    ServiceResultConstant(String code, String msg) {
        this.code = code;
        this.msg = msg;

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean equals(ServiceResultConstant code) {
        return this.code.equals(code.getCode());
    }

    public String jsonStr() {
        return JSONUtil.toStr(this);
    }
}
