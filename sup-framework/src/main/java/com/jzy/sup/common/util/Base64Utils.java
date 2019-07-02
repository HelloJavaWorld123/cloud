package com.jzy.sup.common.util;

import org.apache.commons.codec.binary.Base64;

import java.nio.charset.Charset;

/**
 * <b>功能：</b>Base64工具类<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class Base64Utils {

	/**
	 * <b>功能描述：</b>BASE64字符串解码为二进制数据<br>
	 * <b>修订记录：</b><br>
	 * <li>20140313&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
	 * @return 二进制数组
	 */
	public static byte[] decode(String base64) {
		return Base64.decodeBase64(base64.getBytes(Charset.forName("UTF-8")));
	}

	/**
	 * <b>功能描述：</b>进制数据编码为BASE64字符串<br>
	 * <b>修订记录：</b><br>
	 * <li>20140313&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li>
	 * @return String
	 */
	public static String encode(byte[] bytes) {
		return new String(Base64.encodeBase64(bytes), Charset.forName("UTF-8"));
	}
}