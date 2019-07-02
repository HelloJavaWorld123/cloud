package com.jzy.sup.common.util;

import java.util.Random;

/**
 * <b>功能：</b>字符串工具<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class StringUtils {

    /**
     * <b>功能描述：</b>过滤掉$符<br>
     * <b>修订记录：</b><br>
     * <li>20180530&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
     *
     * @param str 替换前的字符串
     * @return 替换后的字符串
     */
    public static String filterDollarStr(String str) {
        String filtered = "";
        if (!org.apache.commons.lang3.StringUtils.trim(str).equals("")) {
            if (str.indexOf('$', 0) > -1) {
                while (str.length() > 0) {
                    if (str.indexOf('$', 0) > -1) {
                        filtered += str.subSequence(0, str.indexOf('$', 0));
                        filtered += "\\$";
                        str = str.substring(str.indexOf('$', 0) + 1, str.length());
                    } else {
                        filtered += str;
                        str = "";
                    }
                }
            } else {
                filtered = str;
            }
        }
        return filtered;
    }


    /**
     * 生成指定长度的字符串
     *
     * @param length 长度
     */
    public static String genRandomString(int length) {
        String base = "abcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            int number = random.nextInt(base.length());
            sb.append(base.charAt(number));
        }
        return sb.toString();
    }


}
