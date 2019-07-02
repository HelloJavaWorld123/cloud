package com.jzy.sup.common.model;


import com.alibaba.fastjson.JSONObject;
import com.jzy.sup.common.constant.ServiceResultConstant;

import java.io.Serializable;

/**
 * <b>功能：</b>服务返回结果集，所有Service层统一返回结果对象<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class ServiceResult<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    private String code;
    private T result;
    private String msg;

    private ServiceResult() {
    }

    /**
     * <b>功能描述：</b>返回一个成功的业务结果对象，对于service层统一返回ServiceResult<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static <T> ServiceResult<T> success() {
        ServiceResult<T> res = new ServiceResult<T>();
        res.setCode(ServiceResultConstant.SYS_SUCCESS.getCode());
        res.setMsg(ServiceResultConstant.SYS_SUCCESS.getMsg());
        res.setResult(null);
        return res;
    }

   /**
    * <b>功能描述：</b>返回一个成功的业务结果对象，对于service层统一返回ServiceResult<br>
    * <b>修订记录：</b><br>
    * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
    */
    public static <T> ServiceResult<T> success(T result) {
        ServiceResult<T> res = new ServiceResult<T>();
        res.setCode(ServiceResultConstant.SYS_SUCCESS.getCode());
        res.setMsg(ServiceResultConstant.SYS_SUCCESS.getMsg());
        res.setResult(result);
        return res;
    }

  /**
   * <b>功能描述：</b>返回一个成功的业务结果对象，对于service层统一返回ServiceResult<br>
   * <b>修订记录：</b><br>
   * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
   */
    public static <T> ServiceResult<T> success(ServiceResultConstant resultConstant) {
        ServiceResult<T> res = new ServiceResult<>();
        res.setCode(resultConstant.getCode());
        res.setMsg(resultConstant.getMsg());
        res.setResult(null);
        return res;
    }

    /**
     * <b>功能描述：</b>返回一个成功的业务结果对象，对于service层统一返回ServiceResult<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static <T> ServiceResult<T> success(String code, String msg, T result) {
        ServiceResult<T> res = new ServiceResult<>();
        res.setCode(code);
        res.setMsg(msg);
        res.setResult(result);
        return res;
    }

    /**
     * <b>功能描述：</b>返回一个成功的业务结果对象，对于service层统一返回<code>ServiceResult</code>对象<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static <T> ServiceResult<T> failure() {
        ServiceResult<T> res = new ServiceResult<>();
        res.setCode(ServiceResultConstant.SYS_ERROR.getCode());
        res.setMsg(ServiceResultConstant.SYS_ERROR.getMsg());
        res.setResult(null);
        return res;
    }

   /**
    * <b>功能描述：</b>返回一个失败的业务结果对象，对于service层统一返回<code>ServiceResult</code>对象<br>
    * <b>修订记录：</b><br>
    * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
    */
    public static <T> ServiceResult<T> failure(T result) {
        return failure(result, ServiceResultConstant.SYS_ERROR, ServiceResultConstant.SYS_ERROR.getMsg());
    }

    /**
     * 返回一个可自定义结果集与错误码的失败的业务结果对象，对于service层统一返回<code>ServiceResult</code>对象
     *
     * @param code 错误码
     * @return
     */
    public static <T> ServiceResult<T> failure(ServiceResultConstant code) {
        return failure(null, code, code.getMsg());

    }

    /**
     * 返回一个可自定义错误码和错误信息的失败的业务结果对象，对于service层统一返回<code>ServiceResult</code>对象
     * String.format()格式
     *
     * @param resultConstant 错误码
     * @return
     */
    public static <T> ServiceResult<T> failure(ServiceResultConstant resultConstant, String msg) {
        ServiceResult<T> res = new ServiceResult<T>();
        res.setCode(resultConstant.getCode());
        res.setMsg(String.format(resultConstant.getMsg(), msg));
        res.setResult(null);
        return res;
    }

    /**
     * 返回一个可自定义错误码和错误信息的失败的业务结果对象，对于service层统一返回<code>ServiceResult</code>对象
     * String.format()格式
     *
     * @param code 错误码
     * @param msg  错误信息
     * @return
     */
    public static <T> ServiceResult<T> failure(String code, String msg) {
        ServiceResult<T> res = new ServiceResult<T>();
        res.setCode(code);
        res.setMsg(msg);
        res.setResult(null);
        return res;
    }

    /**
     * 返回一个可自定义结果集与错误吗的失败的业务结果对象，对于service层统一返回<code>ServiceResult</code>对象
     *
     * @param result 要返回的业务对象结果集
     * @param code  错误码
     * @param msg   自定义错误信息
     * @return
     */
    public static <T> ServiceResult<T> failure(T result, ServiceResultConstant code, String msg) {
        ServiceResult<T> res = new ServiceResult<T>();
        res.setCode(code.getCode());
        res.setMsg(msg);
        res.setResult(result);
        return res;
    }

    /**
     * 返回是否包含结果集
     *
     * @return boolean
     */
    public boolean hasResult() {
        return result != null;
    }

    /**
     * 获取结果集对象
     *
     * @return T 结果集对象
     */
    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }

    /**
     * 返回结果码
     *
     * @return ServiceResultConstant 结果码常量
     */
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

    public static void main(String[] args) {
        System.err.println(JSONObject.toJSONString(ServiceResult.success()));
    }

    /**
     * 返回结果是否成功
     *
     * @return boolean 返回结果是否成功
     */
    public boolean isSuccess() {
        return this.code.equals(ServiceResultConstant.SYS_SUCCESS.getCode());
    }

    /**
     * 返回结果是否失败
     *
     * @return boolean 返回结果是否失败
     */
    public boolean isFail() {
        return !this.code.equals(ServiceResultConstant.SYS_SUCCESS.getCode());
    }
}
