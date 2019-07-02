package com.jzy.sup.starter.boot.exception;


import com.jzy.sup.common.converter.json.JsonConverter;
import com.jzy.sup.common.model.ResponseResult;
import com.jzy.sup.framework.exception.SupException;
import com.jzy.sup.framework.vo.ErrorType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * <b>功能：</b>全局异常处理器，借鉴商龙云的架构<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @Resource
    private JsonConverter jsonConverter;

    @Resource
    private MessageSource messageSource;


    public ResponseEntity<ResponseResult> applicationErrorHandler(HttpServletResponse response, Exception ex) {
        try {
            if (ex instanceof SupException) {
                // 业务错误异常
                log.error(ResponseResult.failure(messageSource, ((SupException) ex).getMessageCode()).getMsg(), ex);

                SupException error = (SupException) ex;
                if ("SUP.MSG.00004".equals(error.getMessageCode())) {
                    return ResponseEntity.ok(ResponseResult.failure(messageSource, ErrorType.SHOP_SESSION_EXPIRED.getValue(), error.getMessageCode(), error.getArgs()));
                } else if ("SUP.MSG.00005".equals(error.getMessageCode())) {
                    return ResponseEntity.ok(ResponseResult.failure(messageSource, ErrorType.EMP_SESSION_EXPIRED.getValue(), error.getMessageCode(), error.getArgs()));
                } else if ("SUP.MSG.00354".equals(error.getMessageCode())) {
                    return ResponseEntity.ok(ResponseResult.failure(messageSource, ErrorType.VERSION_CODE_ERROR.getValue(), error.getMessageCode(), error.getArgs()));
                } else if ("SUP.ERR.00314".equals(error.getMessageCode())) {
                    return ResponseEntity.ok(ResponseResult.failure(messageSource, ErrorType.REPEAT_TOKEN.getValue(), error.getMessageCode(), error.getArgs()));
                } else if ("SUP.ERR.00578".equals(error.getMessageCode())) {
                    return ResponseEntity.ok(ResponseResult.failure(messageSource, ErrorType.OLDSHOP_CHANGE_PWD.getValue(), error.getMessageCode(), error.getArgs()));
                } else {
                    return ResponseEntity.ok(ResponseResult.failure(messageSource, error.getMessageCode(), error.getArgs()));
                }
            } else {
                log.error("未知异常", ex);
                return ResponseEntity.ok(ResponseResult.failure(messageSource,"SUP.ERR.10013"));
            }
        } catch (NoSuchMessageException e) {
            return ResponseEntity.ok(ResponseResult.failure("未找到当前语言翻译内容，请使用其它国家语言"));
        }
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ResponseResult> exceptionHandler(HttpServletResponse response, Exception e) {

        if (e instanceof DuplicateKeyException) {
            log.error("主键冲突",e.getMessage());
            return ResponseEntity.ok(ResponseResult.failure(messageSource,"SUP.ERR.00005"));
        }
        if (e instanceof SupException) {
            return applicationErrorHandler(response, e);
        }
        log.error(ResponseResult.getMessage(messageSource,"SUP.ERR.00005"), e);
        return ResponseEntity.ok(ResponseResult.failure(messageSource,"SUP.ERR.00005"));
    }


    /**
     * <b>功能描述：</b>ajax输出json。<br>
     * <b>修订记录：</b><br>
     * <li>20140112&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     * <br><br>
     *
     * @param jsonObject 要输出的json对象, 会被 {@link # jsonConverter}转换
     */
    private void ajaxJson(Object jsonObject, HttpServletResponse response) {
        response.setContentType("application/json;charset=UTF-8");
        try {
            jsonConverter.toJson(response.getWriter(), jsonObject);
            log.debug("返回内容给客户端：{}", jsonConverter.toJson(jsonObject));
        } catch (IOException ignore) {
        }
    }
}
