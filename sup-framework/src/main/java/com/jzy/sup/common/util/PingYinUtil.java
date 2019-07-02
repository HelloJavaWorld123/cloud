package com.jzy.sup.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;
import org.apache.commons.lang3.StringUtils;
import org.assertj.core.util.Strings;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * <b>功能：</b>汉字转拼音工具<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class PingYinUtil {
    /**
     * @method-name: getPingYin
     * @description: 将字符串中的中文转化为拼音, 其他字符不变
     * @author: 刘宏超
     * @date: 2017/5/9 10:13
     * @param: [inputString]
     * @return: java.lang.String
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static String getPingYin(String inputString) {
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        format.setVCharType(HanyuPinyinVCharType.WITH_V);

        char[] input = inputString.trim().toCharArray();
        String output = "";

        try {
            for (int i = 0; i < input.length; i++) {
                if (Character.toString(input[i]).matches("[\\u4E00-\\u9FA5]+")) {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(input[i], format);
                    output += temp[0];
                } else
                    output += Character.toString(input[i]);
            }
        } catch (BadHanyuPinyinOutputFormatCombination e) {
            e.printStackTrace();
        }
        return output;
    }

    /**
     * @method-name: getFirstSpell
     * @description: 获取汉字串拼音首字母，英文字符不变[小写]
     * @author: 刘宏超
     * @date: 2017/5/9 10:14
     * @param: [chinese] 汉字串
     * @return: java.lang.String 汉语拼音首字母[小写]
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static String getFirstSpellLower(String chinese) {
        if (Strings.isNullOrEmpty(chinese))
            return chinese;
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    String[] temp = PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat);
                    if (temp != null) {
                        pybf.append(temp[0].charAt(0));
                    }
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString().replaceAll("\\W", "").trim();
    }
    /**
     * @method-name: getFirstSpell
     * @description: 获取汉字串拼音首字母，英文字符不变[大写]
     * @author: 刘宏超
     * @date: 2017/5/9 10:14
     * @param: [chinese] 汉字串
     * @return: java.lang.String 汉语拼音首字母[大写]
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static String getFirstSpellUpper(String chinese) {
        String result=getFirstSpellLower(chinese);
        return Strings.isNullOrEmpty(result)?result:result.toUpperCase();
    }
    /**
     * @method-name: getFullSpell
     * @description: 获取汉字串拼音，英文字符不变
     * @author: 刘宏超
     * @date: 2017/5/9 10:14
     * @param: [chinese] 汉字串
     * @return: java.lang.String 汉语拼音
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static String getFullSpell(String chinese) {
        StringBuffer pybf = new StringBuffer();
        char[] arr = chinese.toCharArray();
        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
        defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] > 128) {
                try {
                    pybf.append(PinyinHelper.toHanyuPinyinStringArray(arr[i], defaultFormat)[0]);
                } catch (BadHanyuPinyinOutputFormatCombination e) {
                    e.printStackTrace();
                }
            } else {
                pybf.append(arr[i]);
            }
        }
        return pybf.toString();
    }

    /**
     * @method-name:
     * @description: 判断字符串中是否包含中文
     * @author: 刘宏超
     * @date: 2018/4/18 16:54
     * @param: @param str 待校验字符串
     * @return: 是否为中文
     * @version V1.0
     * update-logs:方法变更说明
     * ****************************************************
     * name:
     * date:
     * description:
     * *****************************************************
     */
    public static boolean isContainChinese(String str) {
        Pattern p = Pattern.compile("[\u4e00-\u9fa5]");
        Matcher m = p.matcher(str);
        if (m.find()) {
            return true;
        }
        return false;
    }


    /**
     * 返回汉语拼音简拼(大写),如果包含非汉字非汉字不改变.<br>
     * 例:   城市 - CS.
     * @param s 需要获取拼音的文字
     * @return 首字母拼音大写, 如果非汉字则原封不变返回.
     */
    public static String getPinyinj( String s ) {
        if(StringUtils.isBlank(s)) return null;
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);//大写

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            String[] pinyins = null;
            try {
                pinyins = PinyinHelper.toHanyuPinyinStringArray(s.charAt(i), format);
            } catch (BadHanyuPinyinOutputFormatCombination e) {
                throw new RuntimeException(e);
            }
            String pinyin = null;
            if( pinyins==null ){
                //非汉字,取原字符
                pinyin = String.valueOf(s.charAt(i));
            } else {
                pinyin = pinyins[0];
                pinyin = pinyin.substring(0,1);//取出拼音首字
            }
            result.append(pinyin);
        }
        return result.toString();
    }


    public static void main(String[] args){

        System.out.println(getFullSpell("null"));
        System.out.println(getPingYin("null"));
        System.out.println(isContainChinese("开通成功,发送企业注册邮件失败!建议确认邮箱后,手动进入供应链已开通列表重新发送邮件!"));
        System.out.println(isContainChinese("org.springframework.web.client.ResourceAccessException: I/O error on POST request for \"http://192.168.7.107:8080/cloudplat/slyApi/product/open/sync\": Read timed out; nested exception is java.net.SocketTimeoutException"));

    }
}
