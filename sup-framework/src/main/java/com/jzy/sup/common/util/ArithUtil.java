package com.jzy.sup.common.util;

import java.math.BigDecimal;
import java.math.BigInteger;


/**
 * <b>功能：</b>由于Java的简单类型不能够精确的对浮点数进行运算， 这个工具类提供精确的浮点数运算，包括加减乘除和四舍五入<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class ArithUtil {
    // 默认除法运算精度
    private static final int DEF_DIV_SCALE = 10;

    // 这个类不能实例化
    private ArithUtil() {
    }


    /**
     * @param v1 被加数
     * @param v2 加数
     * @method-name: add
     * @description: 提供精确的加法运算
     * @author: 刘宏超
     * @date: 2017/9/25 11:02
     * @return: double
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static double add(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.add(b2).doubleValue();
    }


    /**
     * @param v1 被减数
     * @param v2 减数
     * @method-name: sub
     * @description: 提供精确的减法运算
     * @author: 刘宏超
     * @date: 2017/9/25 11:03
     * @return: double
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static double sub(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.subtract(b2).doubleValue();
    }


    /**
     * @param v1 被乘数
     * @param v2 乘数
     * @method-name: mul
     * @description: 提供精确的乘法运算
     * @author: 刘宏超
     * @date: 2017/9/25 11:04
     * @return: double 两个参数的积
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static double mul(double v1, double v2) {

        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.multiply(b2).doubleValue();
    }


    /**
     * @param v1 被除数
     * @param v2 除数
     * @method-name: div
     * @description: 提供（相对）精确的除法运算，当发生除不尽的情况时，精确到 小数点以后10位，以后的数字四舍五入
     * @author: 刘宏超
     * @date: 2017/9/25 11:05
     * @return: double 两个参数的商
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static double div(double v1, double v2) {
        return div(v1, v2, DEF_DIV_SCALE);
    }


    /**
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @method-name: div
     * @description: 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指 定精度，以后的数字四舍五入
     * @author: 刘宏超
     * @date: 2017/9/25 11:06
     * @return: double 两个参数的商
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static double div(double v1, double v2, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b1 = new BigDecimal(Double.toString(v1));
        BigDecimal b2 = new BigDecimal(Double.toString(v2));
        return b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * @param divisor  除数
     * @param dividend 被除数
     * @method-name: divBigNum
     * @description: 大数取余运算
     * @author: 刘宏超
     * @date: 2017/9/25 11:06
     * @return: 商和余数
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static BigInteger[] divBigNum(String divisor, String dividend) {
        BigInteger bi1 = new BigInteger(dividend);
        BigInteger bi2 = new BigInteger(divisor);
        BigInteger result[] = bi2.divideAndRemainder(bi1);
        return result;
    }


    /**
     * @param v     需要四舍五入的数字
     * @param scale 小数点后保留几位
     * @return 四舍五入后的结果
     * @method-name: round
     * @description: 提供精确的小数位四舍五入处理
     * @author: 刘宏超
     * @date: 2017/9/25 11:07
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static double round(double v, int scale) {
        if (scale < 0) {
            throw new IllegalArgumentException(
                    "The scale must be a positive integer or zero");
        }
        BigDecimal b = new BigDecimal(Double.toString(v));
        BigDecimal one = new BigDecimal("1");
        return b.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
    }


    /**
     * @param v 需要被转换的数字
     * @return 返回转换结果
     * @method-name: convertsToFloat
     * @description: 提供精确的类型转换(Float)
     * @author: 刘宏超
     * @date: 2017/9/25 11:08
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static float convertsToFloat(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.floatValue();
    }


    /**
     * @param v 需要被转换的数字
     * @return 返回转换结果
     * @method-name: convertsToInt
     * @description: 提供精确的类型转换(Int)不进行四舍五入
     * @author: 刘宏超
     * @date: 2017/9/25 11:08
     * @return: int
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static int convertsToInt(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.intValue();
    }

    /**
     * @param v 需要被转换的数字
     * @return 返回转换结果
     * @method-name: convertsToLong
     * @description: 提供精确的类型转换(Long)
     * @author: 刘宏超
     * @date: 2017/9/25 11:09
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static long convertsToLong(double v) {
        BigDecimal b = new BigDecimal(v);
        return b.longValue();
    }

    /**
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 返回两个数中大的一个值
     * @method-name: returnMax
     * @description: 返回两个数中大的一个值
     * @author: 刘宏超
     * @date: 2017/9/25 11:09
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static double returnMax(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.max(b2).doubleValue();
    }


    /**
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @method-name: returnMin
     * @description: 返回两个数中小的一个值
     * @author: 刘宏超
     * @date: 2017/9/25 11:10
     * @return: double 返回两个数中小的一个值
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static double returnMin(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.min(b2).doubleValue();
    }


    /**
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @method-name: compareTo
     * @description: 精确对比两个数字
     * @author: 刘宏超
     * @date: 2017/9/25 11:10
     * @return: int 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static int compareTo(double v1, double v2) {
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.compareTo(b2);
    }
}
