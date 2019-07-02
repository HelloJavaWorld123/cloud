package com.jzy.sup.gateway.server;


import com.jzy.sup.common.model.ResponseResult;
import com.jzy.sup.common.util.JSONUtil;
import com.jzy.sup.common.util.HttpUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * <b>功能：</b>系统全局异常处理类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190508&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
@RestController
@RequestMapping("/error")
public class GlobelErrorHandler {

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<ResponseResult> error() {
        return new ResponseEntity(JSONUtil.toStr(ResponseResult.failure("没有可用的服务")), HttpUtil.headers(), HttpStatus.OK);
    }
}
