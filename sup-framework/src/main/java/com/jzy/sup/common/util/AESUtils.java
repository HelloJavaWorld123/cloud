package com.jzy.sup.common.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * <b>功能：</b>使用AES算法进行加密解密的工具类、常用的核心方法是encrypt(byte[] data, String key)<br>
 * <b>Copyright JZY</b>
 * <ul>
 * <li>版本&nbsp;&nbsp;&nbsp;&nbsp;修改日期&nbsp;&nbsp;&nbsp;&nbsp;部　　门&nbsp;&nbsp;&nbsp;&nbsp;作　者&nbsp;&nbsp;&nbsp;&nbsp;变更内容</li>
 * <hr>
 * <li>v1.0&nbsp;&nbsp;&nbsp;&nbsp;20190513&nbsp;&nbsp;技术中心&nbsp;&nbsp;&nbsp;&nbsp;刘宏超&nbsp;&nbsp;&nbsp;&nbsp;创建类</li>
 * </ul>
 */
public class AESUtils {

	public static final String KEY_ALGORITHM_AES = "AES";

	public static final String CIPHER_ALGORITHM_AES = "AES/ECB/PKCS5Padding";

	/**
	 *
	 * <b>功能描述：</b>生成密钥 <br>
	 * <b>修订记录：</b><br>
	 * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
	 * @return
	 * @throws Exception
	 */
	public static String initKey() throws Exception {
		return initKey(null);
	}

	/**
	 *
	 * <b>功能描述：</b>生成密钥  <br>
	 * <b>修订记录：</b><br>
	 * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
	 * @param seed
	 * @return
	 * @throws Exception
	 */
	public static String initKey(String seed) throws Exception {
		SecureRandom secureRandom = null;

		if (seed != null) {
			secureRandom = new SecureRandom(Base64Utils.decode(seed));
		} else {
			secureRandom = new SecureRandom();
		}

		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
		kg.init(secureRandom);

		SecretKey secretKey = kg.generateKey();

		return Base64Utils.encode(secretKey.getEncoded());
	}

	/**
	 * <b>功能描述：</b>获取秘钥  <br>
	 * <b>修订记录：</b><br>
	 * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
	 */
	public static Key genKey() {
		KeyGenerator kgen;
		try {
			kgen = KeyGenerator.getInstance(KEY_ALGORITHM_AES);
			kgen.init(128, new SecureRandom());
			return kgen.generateKey();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 *
	 * <b>功能描述：</b>解密二进制文本，使用二进制秘钥  <br>
	 * <b>修订记录：</b><br>
	 * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
	 * @param encryptedData
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] encryptedData, byte[] key) throws Exception {
		SecretKeySpec sks = new SecretKeySpec(key, KEY_ALGORITHM_AES);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);// 创建密码器
		cipher.init(Cipher.DECRYPT_MODE, sks);// 初始化
		return cipher.doFinal(encryptedData);
	}

	/**
	 *
	 * <b>功能描述：</b>解密二进制文本，使用字符串秘钥 <br>
	 * <b>修订记录：</b><br>
	 * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
	 * @param encryptedData
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] encryptedData, String key) throws Exception {
		return decrypt(encryptedData, Base64Utils.decode(key));
	}

	/**
	 *
	 * <b>功能描述：</b>加密二进制文本，使用二进制秘钥 <br>
	 * <b>修订记录：</b><br>
	 * <li>20190513&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		SecretKeySpec sks = new SecretKeySpec(key, KEY_ALGORITHM_AES);
		Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_AES);// 创建密码器
		cipher.init(Cipher.ENCRYPT_MODE, sks);// 初始化
		return cipher.doFinal(data);
	}

	/**
	 *
	 * <b>功能描述：</b>加密二进制文本，使用字符串秘钥 <br>
	 * <b>修订记录：</b><br>
	 * <li>20150202&nbsp;&nbsp;|&nbsp;&nbsp;刘宏超&nbsp;&nbsp;|&nbsp;&nbsp;创建方法</li><br>
	 * @param data
	 * @param key
	 * @return
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, String key) throws Exception {
		return encrypt(data, Base64Utils.decode(key));
	}
}
