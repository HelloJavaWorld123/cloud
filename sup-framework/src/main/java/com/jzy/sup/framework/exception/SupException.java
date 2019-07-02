package com.jzy.sup.framework.exception;

import lombok.Data;

/**
 * <b>功能：</b>sup业务异常<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190528&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;邓冲&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Data
public class SupException extends RuntimeException {

    private String messageCode;

    private Object[] args;

    public SupException(String message) {
        super(message);
    }

    public SupException(String message, Throwable cause) {
        super(message, cause);
    }

    public SupException(String messageCode, Object... args) {
        this.messageCode = messageCode;
        this.args = args;
    }

}
