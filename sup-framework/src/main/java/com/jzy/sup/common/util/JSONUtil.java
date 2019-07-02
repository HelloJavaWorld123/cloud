package com.jzy.sup.common.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;

import java.util.List;

/**
 * <b>功能：</b>Json字符串工具类，基于FastJson实现<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class JSONUtil {

    public static String toStr(Object object) {
        return JSON.toJSONString(object);
    }

    /**
     * 将JSON字符串转为对象
     *
     * @param json  json字符串
     * @param clazz 要造型成的Class对象T
     * @return T 对象
     */
    public static <T> T toObj(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将JSON字符串转化为JSON对象
     *
     * @param json
     * @return JSONObj
     */
    public static JSONObj toJSONObj(String json) {
        return new JSONObj(json);
    }

    /**
     * <b>功能描述：</b>JSON数组格式的字符串转化成List<T></><br>
     * <b>修订记录：</b><br>
     * <li>20190618&nbsp;&nbsp;|&nbsp;&nbsp;唐永刚&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     */
    public static  <T> List<T> toJavaList(String record, Class<T> clazz) {
        JSONArray jsonArray = JSON.parseArray(record);
        return jsonArray.toJavaList(clazz);

    }




}
