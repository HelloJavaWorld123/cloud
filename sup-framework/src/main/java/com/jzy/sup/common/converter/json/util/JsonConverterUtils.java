package com.jzy.sup.common.converter.json.util;


import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jzy.sup.common.converter.json.JsonConverter;

import javax.annotation.Resource;

/**
 * <b>功能：</b>json转换工具类<br>
 * <b>Copyright TCSL</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;
 * 变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20131231&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;孟庆禹&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * <li>v1.1&nbsp;&nbsp;&nbsp;&nbsp;20180507&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;赵伟&nbsp;&nbsp;&nbsp;&nbsp;
 * 修复静态初始化无法加载Spring上下文的问题</li>
 * </ul>
 */
public final class JsonConverterUtils {

    private static class JsonConverterHolder {
        private static JsonConverter jc;
    }

    private static class JsonMapperHolder {
        private static ObjectMapper om;
    }

    public static JsonConverter getJc() {
        return JsonConverterHolder.jc;
    }

    public static ObjectMapper getOm() {
        return JsonMapperHolder.om;
    }

    @Resource(type = JsonConverter.class)
    public void setJc(JsonConverter jsonConverter) {
        if (JsonConverterHolder.jc != null) {
            throw new RuntimeException("System do not allow to change this object.");
        }

        JsonConverterHolder.jc = jsonConverter;
    }

    @Resource(type = ObjectMapper.class)
    public void setOm(ObjectMapper objectMapper) {
        if (JsonMapperHolder.om != null) {
            throw new RuntimeException("System do not allow to change this object.");
        }

        JsonMapperHolder.om = objectMapper;
        //空属性不序列化
        JsonMapperHolder.om.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //json key可以没有双引号
        JsonMapperHolder.om.configure(JsonParser.Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
        //忽略解析未知的字段
        JsonMapperHolder.om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    }
}
