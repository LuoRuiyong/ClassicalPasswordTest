package com.luoruiyong.password;

import java.util.ArrayList;

import com.luoruiyong.bean.CaesarMessage;

/**
 * Caesar�����㷨��
 * @author Administrator
 *
 */
public class Caesar {
	private final static int LETTER_LENGTH = 26; 
	private final static double TARGET =  0.065379;
	private final static double STANDARD_FREQUENCY[] = {0.082, 0.015, 0.028, 0.042, 0.127,
	                      					   			0.022, 0.020, 0.061, 0.070, 0.001,
	                      					   			0.008, 0.040, 0.024, 0.067, 0.075,
	                      					   			0.019, 0.001, 0.060, 0.063, 0.090,
	                      					   			0.028, 0.010, 0.024, 0.020, 0.001, 
	                      					   			0.001};
	
	/**
	 * �����㷨
	 * @param plaintext  ����
	 * @param key	��Կ
	 * @param isIgnoreCase  �Ƿ���Դ�Сд
	 * @return	����
	 */
	public static String encrypt(String plaintext,int key,boolean isIgnoreNotLetter,boolean isIgnoreCase) {
		if(plaintext == null || plaintext.equals("")) {
			return null;
		}
		StringBuilder ciphertext = new StringBuilder();
		int length = plaintext.length();
		for(int i=0;i < length; i++) {
			char ch = plaintext.charAt(i);
			if(isIgnoreCase && ch >= 'A' &&  ch <= 'Z') {
				ch = (char) ((Character.toLowerCase(ch) - 'a' + key) % LETTER_LENGTH + 'a');
			}else if(ch >= 'A' &&  ch <= 'Z') {
				ch = (char) ((ch - 'A' + key) % LETTER_LENGTH + 'A');
			}else if(ch >= 'a' &&  ch <= 'z') {
				ch = (char) ((ch - 'a' + key) % LETTER_LENGTH + 'a');
			}else if(isIgnoreNotLetter) {
				continue;
			}
			ciphertext.append(ch);
		}
		return ciphertext.toString();
	}
	
	public static String encrypt(String plaintext,int key,boolean isIgnoreNotLetter) {
		return encrypt(plaintext,key,isIgnoreNotLetter,true);
	}
	
	public static String encrypt(String plaintext,int key) {
		return encrypt(plaintext,key,true,true);
	}
	
	/**
	 * �����㷨
	 * @param ciphertext ����
	 * @param key ��Կ
	 * @param isIgnoreNotLetter �Ƿ���Է���ĸ�ַ�
	 * @param isIgnoreCase �Ƿ������ĸ��Сд
	 * @return ����
	 */
	public static String decrypt(String ciphertext,int key,boolean isIgnoreNotLetter,boolean isIgnoreCase) {
		if(ciphertext == null || ciphertext.equals("")) {
			return null;
		}
		StringBuilder plaintext = new StringBuilder();
		int length = ciphertext.length();
		for(int i=0;i < length; i++) {
			char ch = ciphertext.charAt(i);
			if(isIgnoreCase && ch >= 'A' &&  ch <= 'Z') {
				ch = (char) ((Character.toLowerCase(ch) - 'a' - key + LETTER_LENGTH) % LETTER_LENGTH + 'a');
			}else if(ch >= 'A' &&  ch <= 'Z') {
				ch = (char) ((ch - 'A' - key + LETTER_LENGTH) % LETTER_LENGTH + 'A');
			}else if(ch >= 'a' &&  ch <= 'z') {
				ch = (char) ((ch - 'a' - key + LETTER_LENGTH) % LETTER_LENGTH + 'a');
			}else if(isIgnoreNotLetter) {
				continue;
			}
			plaintext.append(ch);
		}
		return plaintext.toString();
	}
	
	public static String decrypt(String plaintext,int key,boolean isIgnoreNotLetter) {
		return decrypt(plaintext,key,isIgnoreNotLetter,true);
	}
	
	public static String decrypt(String plaintext,int key) {
		return decrypt(plaintext,key,true,true);
	}
	
	/**
	 * �����ƽ�
	 * @param ciphertxt ����
	 * @return �ƽ���
	 */
	public static CaesarMessage autoCrack(String ciphertext,boolean isIgnoreNotLetter,boolean isIgnoreCase) {
		int targetKey = 0;		// ���ܵ���Կ
		float probability = 0;	// �Ǻ϶�
		double targetMinus = 1; 
		double[] letterFrequency = getLetterFrequency(ciphertext);
		if(letterFrequency == null) {
			return null;
		}
		for(int key = 0; key< LETTER_LENGTH;key++) {
			double sum = 0;
			for(int j=0;j< LETTER_LENGTH;j++) {
				sum += (letterFrequency[(j+key)%26]*STANDARD_FREQUENCY[j]);
			}
			if(Math.abs(sum-TARGET) <= targetMinus) {
				probability = (float) (sum > TARGET ? TARGET /sum : sum / TARGET) * 100;
				targetMinus = Math.abs(sum-TARGET);
				targetKey = key;
			}
		}
		return new CaesarMessage(decrypt(ciphertext, targetKey,isIgnoreNotLetter,isIgnoreCase), targetKey, ciphertext,probability);
	}
	
	/**
	 * ��ȡ�����и���ĸ���ֵ�Ƶ��
	 * @param ciphertext ����
	 * @return ��ĸ����Ƶ�ʼ�
	 */
	private static double[] getLetterFrequency(String ciphertext) {
		int sum = 0;
		double[] letterFrequency = new double[26];
		int length = ciphertext.length();
		for(int i = 0;i < length; i++) {
			char ch = Character.toLowerCase(ciphertext.charAt(i));
			if(ch >= 'a' &&  ch <= 'z') {
				letterFrequency[ch -'a']++;
				sum++;
			}
		}
		if(sum == 0) {
			return null;
		}
		for(int i = 0;i < LETTER_LENGTH;i++) {
			letterFrequency[i] /= sum;
		}
		return letterFrequency;
	}
	
	/**
	 * ��ٷ��ƽ�
	 * @param ciphertext ����
	 * @param isIgnoreNotLetter �Ƿ���Է���ĸ�ַ�
	 * @param isIgnoreCase �Ƿ������ĸ��Сд
	 * @return ���ܵ����Ľ����
	 */
	public static ArrayList<CaesarMessage> exhaustCrack(String ciphertext,boolean isIgnoreNotLetter,boolean isIgnoreCase){
		if(null == ciphertext || ciphertext.equals("")) {
			return null;
		}
		ArrayList<CaesarMessage> messages = new ArrayList<>();
		for(int key=0;key<LETTER_LENGTH;key++) {
			String plaintext = decrypt(ciphertext, key,isIgnoreNotLetter,isIgnoreCase);
			messages.add(new CaesarMessage(plaintext, key, ciphertext));
		}
		return messages;
	}
	
	public static ArrayList<CaesarMessage> exhaustCrack(String ciphertext,boolean isIgnoreNotLetter){
		return exhaustCrack(ciphertext, isIgnoreNotLetter,true);
	}
	
	public static ArrayList<CaesarMessage> exhaustCrack(String ciphertext){
		return exhaustCrack(ciphertext, true,true);
	}
}
