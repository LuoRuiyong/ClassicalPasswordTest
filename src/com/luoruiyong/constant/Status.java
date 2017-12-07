package com.luoruiyong.constant;

public class Status {
	// 提示面板事件
	public static final String NO_SECRET_KEY = "密钥未验证";
	public static final String SECRET_KEY_AVAILABLE = "密钥有效";
	public static final String SECRET_KEY_INVALID = "密钥无效";
	
	// 功能面板事件
	public static final String ENCRYPT_SUCCEED = "加密成功";
	public static final String ENCRYPT_FAILED = "加密失败";
	public static final String DECRYPT_SUCCEED = "解密成功";
	public static final String DECRYPT_FAILED = "解密失败";
	public static final String EXHAUST_CRACK = "穷举法破解";
	public static final String CRACK_FAILED = "破解失败";
	public static final String PROBABILITY_CRACK = "概率破解成功";
	public static final String ENCRYPT_ANALYSIS = "加密分析";
	public static final String DECRYPT_ANALYSIS = "解密分析";
	public static final String EXHAUST_CRACK_ANAYLYZE = "穷举法破解分析";
	public static final String PROBABILITY_CRACK_ANALYZE = "概率破解分析";
	
	// 明文密文输入框事件
	public static final String NO_ANAYLYSIS = "不能进行分析"; 
	
	public static final String OPEN_FILE_SUCCEED = "成功打开文件"; 
	public static final String OPEN_FILE_FAILED = "打开文件失败"; 
	public static final String SAVE_FILE_SUCCEED = "成功保存文件"; 
	public static final String SAVE_FILE_FAILED = "保存文件失败"; 
	
	public static final String CLEAR_PLAINTEXT_SUCCEED = "成功删除明文数据"; 
	public static final String CLEAR_CIPHERTEXT_SUCCEED = "成功删除密文数据"; 
}
