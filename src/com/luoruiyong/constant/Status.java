package com.luoruiyong.constant;

public class Status {
	public static final String READY = "密钥待验证";
	public static final String SECRET_KEY_AVAILABLE = "密钥有效";
	public static final String SECRET_KEY_INVALID = "密钥无效";
	public static final String ENCRYPT_SUCCEED = "加密成功";
	public static final String ENCRYPT_FAILED = "加密失败";
	public static final String DECRYPT_SUCCEED = "解密成功";
	public static final String DECRYPT_FAILED = "解密失败";
	public static final String EXHAUST_CRACK = "穷举法破解情况显示";
	public static final String PROBABILITY_CRACK_SUCCEED = "概率破解成功";
	public static final String PROBABILITY_CRACK_FAILED = "概率破解失败";
	public static final String ENCRYPT_ANALYSIS = "加密分析";
	public static final String DECRYPT_ANALYSIS = "解密分析";
	public static final String CRACK_ANALYSIS = "破解分析";
	
	public interface OnStatusChangedListener{
		void OnStatusChanged(String status);
	}
}
