package com.jzy.sup.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * <b>功能：</b>时间转换相关工具类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190618&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;蒋剑&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class DateUtil {

    /**
     * <b>功能描述：</b>获取当前系统时间字符串<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;蒋剑&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return yyyy/MM/dd HH:mm:ss
     */
    public static String getStringNowDates() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateString = formatter.format(new Date());
        return dateString;
    }

    /**
     * <b>功能描述：</b>获取当前系统时间字符串<br>
     * <b>修订记录：</b><br>
     * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;蒋剑&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @return yyyy-MM-dd HH:mm:ss
     */
    public static String getStringNowDatew() {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(new Date());
        return dateString;
    }

}