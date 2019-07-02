package com.jzy.sup.starter.log.bean;

import java.io.Serializable;
import java.util.Date;

/**
 * <b>功能：</b>LogInfo<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class LogInfo implements Serializable{

    private String userName;

    private String fullName;

    private String mobile;

    private String ip;

    private String function;

    private int functionCode;

    private String module;

    private int moduleCode;

    private String opt;

    private int optCode;

    private String resCode;

    private String resMsg;

    private Date createTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFunction() {
        return function;
    }

    public void setFunction(String function) {
        this.function = function;
    }

    public int getFunctionCode() {
        return functionCode;
    }

    public void setFunctionCode(int functionCode) {
        this.functionCode = functionCode;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public int getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(int moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getOpt() {
        return opt;
    }

    public void setOpt(String opt) {
        this.opt = opt;
    }

    public int getOptCode() {
        return optCode;
    }

    public void setOptCode(int optCode) {
        this.optCode = optCode;
    }

    public String getResCode() {
        return resCode;
    }

    public void setResCode(String resCode) {
        this.resCode = resCode;
    }

    public String getResMsg() {
        return resMsg;
    }

    public void setResMsg(String resMsg) {
        this.resMsg = resMsg;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getMobile() {
        return mobile;
    }

    public String getIp() {
        return ip;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
