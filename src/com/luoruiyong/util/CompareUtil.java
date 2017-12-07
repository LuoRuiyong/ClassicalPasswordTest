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
			Message message = new Message();
			message.setPlaintext(plaintext.substring(i, i+groupCount));
			message.setCiphertext(ciphertext.substring(i, i+groupCount));
			messages.add(message);
		}
		return messages;
	}
	
	public static ArrayList<ArrayList<Message>> getCaesarExhaustCrackDetailMessage(ArrayList<Message> messages){
		int length;
		 ArrayList<ArrayList<Message>> list = new  ArrayList<>();
		String ciphertext = messages.get(0).getCiphertext();
		length = (messages.size() < 200 ? ciphertext.length() : 200);
		for(Message msg : messages) {
			String plaintext = msg.getPlaintext();
			ArrayList<Message> msgs = new ArrayList<>();
			for(int i=0;i<length;i++) {
				Message message = new Message();
				message.setPlaintext(plaintext.substring(i, i+1));
				message.setCiphertext(ciphertext.substring(i, i+1));
				message.setKey(i+"");
				msgs.add(message);
			}
			list.add(msgs);
		}
		return list;
	}
}