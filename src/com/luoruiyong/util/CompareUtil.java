package com.luoruiyong.util;

import java.util.ArrayList;

import com.luoruiyong.bean.Message;

public class CompareUtil {
	/**
	 * 加解密分组对比（可供分组查看）
	 * @param plaintext 明文
	 * @param ciphertext 密文
	 * @return 返回加解密详情
	 */
	public static ArrayList<Message> getDetailMessage(String plaintext,String ciphertext,int groupCount){
		plaintext = CharacterUtil.filterNotLetter(plaintext);
		ciphertext = CharacterUtil.filterNotLetter(ciphertext);
		if(plaintext == null || ciphertext == null) {
			return null;
		}
		ArrayList<Message> messages = new ArrayList<>();
		int length = Math.min(plaintext.length(), ciphertext.length());
		if(length % groupCount != 0) {
			length -= (length %groupCount);
		}
		for(int i = 0;i<length;i+=groupCount) {
			messages.add(new Message(plaintext.substring(i, i+groupCount), ciphertext.substring(i, i+groupCount)));
		}
		return messages;
	}
}
