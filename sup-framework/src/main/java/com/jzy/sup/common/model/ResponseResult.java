/**
 * All rights Reserved, Designed By www.szsl.com
 *
 * @Title: ResponseResult.java
 * @Package com.tcsl.slyun.common.cnd
 * @Description: API统一返回结果
 * @author: 刘凯峰
 * @date: 2017年4月13日 上午10:46:19
 * @version V1.0
 */
package com.jzy.sup.common.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jzy.sup.common.constant.ServiceResultConstant;
import com.jzy.sup.framework.vo.ErrorType;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;

import java.io.Serializable;
import java.util.Locale;

/**
 * <b>功能：</b>API统一返回结果<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class ResponseResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /*返回码*/
    private String code;
    /*返回信息*/
    private String msg;
    /*返回数据对象*/
    private T data;

    /**
     * 国际化资源处理类
     */
    @JsonIgnore
    private MessageSource messageSource;

    public ResponseResult() {
    }

    public ResponseResult(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public ResponseResult(ServiceResult<T> result) {
        data = result.getResult();
        code = result.getCode();
        msg = (result.getMsg() == null || result.getMsg().equals("")) ? result.getCode() : result.getMsg();
    }


 /**
  * <b>功能描述：</b>创建一个MessageBox实例<br>
  * <b>修订记录：</b><br>
  * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
  */
    public static ResponseResult failure(MessageSource messageSource, String code, Object... args) {
        return failure(messageSource, ErrorType.FAILURE.getValue(),code,args);
    }

    public static String getMessage(MessageSource messageSource, String code, Object... args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(code,args,locale);
    }

/**
 * <b>功能描述：</b>创建一个MessageBox实例<br>
 * <b>修订记录：</b><br>
 * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
 */
    public static ResponseResult failure(MessageSource messageSource, Integer type, String code, Object... args) {
        if(null == type){
            type = ErrorType.FAILURE.getValue();
        }
        ResponseResult responseResult = new ResponseResult();
        Locale locale = LocaleContextHolder.getLocale();

        if (StringUtils.isBlank(code)) {
            code = "S1.ERR.10012";
        }

        responseResult.setCode(type+"");
        responseResult.setMsg(messageSource.getMessage(code, args, locale));
        return responseResult;
    }

    /**
     * <b>功能描述：</b>返回一个成功的结果<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static ResponseResult success() {
        return success("", "", null);
    }

   /**
    * <b>功能描述：</b>返回ServiceResult的结果<br>
    * <b>修订记录：</b><br>
    * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
    */
    public static ResponseResult result(ServiceResult serviceResult) {
        return success(serviceResult.getCode(), serviceResult.getMsg(), serviceResult.getResult());
    }

   /**
    * <b>功能描述：</b>返回一个成功的结果<br>
    * <b>修订记录：</b><br>
    * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
    */
    public static ResponseResult success(String code, String msg, Object data) {
        ResponseResult responseResult = new ResponseResult();
        responseResult.setCode(StringUtils.isEmpty(code) ? ServiceResultConstant.SYS_SUCCESS.getCode() : code);
        responseResult.setMsg(StringUtils.isEmpty(msg) ? ServiceResultConstant.SYS_SUCCESS.getMsg() : msg);
        responseResult.setData(data);
        return responseResult;
    }

   /**
    * <b>功能描述：</b>返回一个成功的结果<br>
    * <b>修订记录：</b><br>
    * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
    */
    public static ResponseResult success(Object data) {
        return success("", "", data);
    }

   /**
    * <b>功能描述：</b>返回一个成功的结果<br>
    * <b>修订记录：</b><br>
    * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
    */
    public static ResponseResult success(String code, String msg) {
        return success(code, msg, null);
    }

    /**
     * <b>功能描述：</b>返回一个失败的结果<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static ResponseResult failure() {
        return failure("", "", null);
    }

   /**
    * <b>功能描述：</b>返回一个失败的结果<br>
    * <b>修订记录：</b><br>
    * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
    */
    public static ResponseResult failure(String msg) {
        return failure("", msg, null);
    }


    /**
     * <b>功能描述：</b>返回一个失败的结果<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static ResponseResult failure(ServiceResultConstant serviceResultConstant) {
        return failure(serviceResultConstant.getCode(), serviceResultConstant.getMsg(), null);
    }

    /**
     * <b>功能描述：</b>返回一个失败的结果, 自定义编码和提示信息<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static ResponseResult failure(String code, String msg) {
        return failure(code, msg, null);
    }

    /**
     * <b>功能描述：</b>返回一个失败的结果<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static  ResponseResult failure(String code,String msg,Object data) {
        ResponseResult responseResult=new ResponseResult();
        responseResult.setCode(StringUtils.isEmpty(code)?ServiceResultConstant.SYS_ERROR.getCode():code);
        responseResult.setMsg(StringUtils.isEmpty(msg)?ServiceResultConstant.SYS_ERROR.getMsg():msg);
        responseResult.setData(data);
        return responseResult;
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

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public boolean isSuccess() {
        return this.code.equals(ServiceResultConstant.SYS_SUCCESS.getCode());
    }
}
