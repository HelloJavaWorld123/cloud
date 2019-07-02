package com.jzy.sup.framework.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.Locale;

/**
 * <b>功能：</b>返回结果对象<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MessageBox implements Serializable {

    /**
     * 请求接口返回的结果
     * <p/>
     * <ul>
     * <li> 0    -> 业务逻辑正确</li>
     * <li>-1    -> 业务逻辑错误</li>
     * <li>-2    -> 登录会话过期</li>
     * <li>-3    -> 版本错误</li>
     * </ul>
     */
    private int result;

    /**
     * 返回结果，JsonObject/JsonArray
     */
    private Object data;

    /**
     * 错误信息，返回给页面展示
     */
    private String errorMsg;

    /**
     * 国际化资源处理类
     */
    @JsonIgnore
    private MessageSource messageSource;

    public MessageBox(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    /**
     * <b>功能描述：</b>创建一个MessageBox实例<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    public static MessageBox build(MessageSource messageSource) {
        return new MessageBox(messageSource);
    }

    /**
     * <b>功能描述：</b>生成成功的请求响应结果<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    public MessageBox success() {
        //this.setResult(ErrorType.SUCCESS.getValue());

        return this;
    }

    /**
     * <b>功能描述：</b>生成成功的请求响应，包含返回结果<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    public MessageBox success(Object returnValue) {
        this.setData(returnValue);
        this.setResult(ErrorType.SUCCESS.getValue());
        return this;
    }

    /**
     * <b>功能描述：</b>业务逻辑错误，传入具体的错误信息<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public MessageBox fail(String messageCode) {
        return fail(messageCode, null);
    }

    /**
     * <b>功能描述：</b>业务逻辑错误，传入具体的错误信息<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     * @param messageCode 国际化编码枚举类
     * @param args 国际化参数占位符参数内容
     */
    public MessageBox fail(String messageCode, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();

        if (StringUtils.isBlank(messageCode)) {
            messageCode = "S1.ERR.10012";
        }

        this.setResult(ErrorType.FAILURE.getValue());
        this.setErrorMsg(messageSource.getMessage(messageCode, args, locale));

        return this;
    }

    /**
     * <b>功能描述：</b>登录会话过期<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param type 类型：1、店铺；2、员工
     */
    public MessageBox sessionExpired(String messageCode, int type, Object... args) {
        return fail(messageCode, args).setResult(type == 1 ?
                ErrorType.SHOP_SESSION_EXPIRED.getValue() : ErrorType.EMP_SESSION_EXPIRED.getValue());
    }

    /**
     * <b>功能描述：</b>令牌重复<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param messageCode 国际化编码
     * @return 本实例对象
     */
    public MessageBox repeatToken(String messageCode) {
        return fail(messageCode).setResult(ErrorType.REPEAT_TOKEN.getValue());
    }

    /**
     * <b>功能描述：</b>版本错误<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param messageCode 国际化编码
     * @return 本实例对象
     */
    public MessageBox versionCodeFail(String messageCode) {
        return fail(messageCode).setResult(ErrorType.VERSION_CODE_ERROR.getValue());
    }

    /**
     * <b>功能描述：</b>令牌重复<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param messageCode 国际化编码
     * @param args 国际化参数占位符参数内容
     * @return 本实例对象
     */
    public MessageBox repeatToken(String messageCode, Object... args) {
        return fail(messageCode, args).setResult(ErrorType.REPEAT_TOKEN.getValue());
    }

    /**
     * <b>功能描述：</b>老用户登录提示修改密码<br>
     * <b>修订记录：</b><br>
     * <li>20190508&nbsp;&nbsp;|&nbsp;&nbsp刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    public MessageBox oldShopLogin(String messageCode,Object... args) {
        return fail(messageCode, args).setResult(ErrorType.OLDSHOP_CHANGE_PWD.getValue());
    }

    /**
     * getter *
     */
    public int getResult() {
        return result;
    }

    /**
     * setter *
     */
    public MessageBox setResult(int result) {
        this.result = result;
        return this;
    }

    /**
     * getter *
     */
    public Object getData() {
        return data;
    }

    /**
     * setter *
     */
    public MessageBox setData(Object data) {
        this.data = data;

        return this;
    }

    /**
     * getter *
     */
    public String getErrorMsg() {
        return errorMsg;
    }

    /**
     * setter *
     */
    public MessageBox setErrorMsg(String errorMsg) {
        if (StringUtils.isBlank(errorMsg)) {
            this.errorMsg = "未知错误";
        } else {
            this.errorMsg = errorMsg;
        }

        return this;
    }

    /**
     * getter *
     */
    public MessageSource getMessageSource() {
        return messageSource;
    }

    /**
     * setter *
     */
    public void setMessageSource(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
