package igouc.com.nodepad.util;


import org.bouncycastle.util.encoders.Base64;

/**
 * Created by gouchao on 16-4-28.
 */
public class Base64Util {
	public static String base64EncodeByString(String src){
		String result = null;
		result = new String(Base64.encode(src.getBytes()));
		return result;
	}
	public static String base64DecodeByString(String src){
		String result =null;
		byte[] bytes = Base64.decode(src.getBytes());
		result = new String(bytes);
		return result;
	}
}
