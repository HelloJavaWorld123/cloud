package com.jzy.sup.common.converter.json;

import java.io.OutputStream;
import java.io.Writer;

/**
 * <b>功能：</b>json转换器接口, 实现json字符串-类, 类-json字符串的转换<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public interface JsonConverter {

    /**
     * <b>功能描述：</b>json字符串转换为相应类型<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    public <T> T fromJson(String json, Class<T> resultType);

    /**
     * <b>功能描述：</b>Object转换为json字符串<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    public String toJson(Object value);

    /**
     * <b>功能描述：</b>Object转换为json字符串, 写入到流<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    public void toJson(OutputStream out, Object value);

    /**
     * <b>功能描述：</b>Object转换为json字符串, 写入到流<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
     */
    public void toJson(Writer out, Object value);

}
