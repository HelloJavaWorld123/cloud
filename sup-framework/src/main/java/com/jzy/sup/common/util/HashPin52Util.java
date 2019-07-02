package com.jzy.sup.common.util;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;

/**
 * <b>功能：</b>HashPin生成工具（52字节算法，用来生成用户名即HaPin）<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class HashPin52Util {
    private static StringBuilder resultString = new StringBuilder();
    private static ThreadLocal<Map<String, String>> characMapLocal = new ThreadLocal<>();// 大小写字母排序和编号


    /**
     * @method-name: getHaPin
     * @description: 对一组数字进行52进制运算，然后转换成对应的字母
     * @author: 刘宏超
     * @date: 2017/9/25 11:12
     * @param: [str]
     * @return: java.lang.String
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static String getHaPin(String str) {
        String haPinResult = "";
        try {
            characMapLocal.set(getHaPinMap());
            ModNum(str);
            haPinResult = resultString.toString();
        } catch (Exception e) {
            throw (e);
        } finally {
            resultString = new StringBuilder();
        }
        return haPinResult;
    }


    /**
     * @method-name: getHaPinMap
     * @description: 获取52个大小写字母与0到51的对应关系
     * @author: 刘宏超
     * @date: 2017/9/25 11:15
     * @param: []
     * @return: java.util.Map<java.lang.String,java.lang.String>
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    private static Map<String, String> getHaPinMap() {
        Map<String, String> map = new HashMap<String, String>();
        int a = -1;
        for (int i = 26; i < 52; i++) {
            a++;
            map.put(String.valueOf(a), String.valueOf((char) (i + 71)));
        }
        for (int i = 0; i < 26; i++) {
            a++;
            map.put(String.valueOf(a), String.valueOf((char) (i + 65)));
        }
        return map;
    }


    /**
     * @method-name: ModNum
     * @description: 取余运算，并转换成对应的字母
     * @author: 刘宏超
     * @date: 2017/9/25 11:15
     * @param: dividend 除数
     * @return: void
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    private static void ModNum(String dividend) {
        BigInteger result[] = ArithUtil.divBigNum(dividend, "52");
        resultString.append(characMapLocal.get().get(result[1].toString()));
        if (result[0].longValue() > 52) {
            ModNum(result[0].toString());
        }
    }

    public static void main(String[] args) {
        String bString = "170923143839000096";
        // String bString = "5768";
        System.out.println(bString + ":" + getHaPin(bString));
        System.out.println("size:" + getHaPin(bString).length());

       /* String bString2 = "1000000061" + "100";
        System.out.println(bString2 + ":" + getHaPin(bString2));*/
    }
}
