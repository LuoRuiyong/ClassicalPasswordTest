package com.luoruiyong.password;

import java.util.ArrayList;
import com.luoruiyong.bean.CaesarMessage;
import com.luoruiyong.bean.Message;
import com.luoruiyong.util.CharacterUtil;

/**
 * Caesar�����㷨��
 * @author Administrator
 *
 */
public class Caesar {
	private final static int LETTER_LENGTH = 26; 
	private final static double TARGET =  0.065379;
	public final static double STANDARD_FREQUENCY[] = {0.082, 0.015, 0.028, 0.042, 0.127,
	                      					   			0.022, 0.020, 0.061, 0.070, 0.001,
	                      					   			0.008, 0.040, 0.024, 0.067, 0.075,
	                      					   			0.019, 0.001, 0.060, 0.063, 0.090,
	                      					   			0.028, 0.010, 0.024, 0.020, 0.001, 
	                      					   			0.001};
	
	/**
	 * �����㷨
	 * @param plaintext  ����
	 * @param key	��Կ
	 * isReserveNotLetter �Ƿ�������ĸ�ַ�
	 * @param isIgnoreCase  �Ƿ���Դ�Сд
	 * @return	����
	 */
	public static String encrypt(String plaintext,int key,boolean isReserveNotLetter,boolean isIgnoreCase) {
		if(!isReserveNotLetter) {
			plaintext = CharacterUtil.filterNotLetter(plaintext);
		}
		if(plaintext == null || plaintext.equals("")) {
			return null;
		}
		StringBuilder ciphertext = new StringBuilder();
		for(int i=0;i < plaintext.length(); i++) {
			char ch = plaintext.charAt(i);
			if(isIgnoreCase && ch >= 'a' &&  ch <= 'z') {
				ch = (char) ((Character.toUpperCase(ch) - 'A' + key) % LETTER_LENGTH + 'A');
			}else if(ch >= 'A' &&  ch <= 'Z') {
				ch = (char) ((ch - 'A' + key) % LETTER_LENGTH + 'A');
			}else if(ch >= 'a' &&  ch <= 'z') {
				ch = (char) ((ch - 'a' + key) % LETTER_LENGTH + 'a');
			}
			ciphertext.append(ch);
		}
		return ciphertext.toString();
	}
	
	public static String encrypt(String plaintext,int key,boolean isReserveNotLetter) {
		return encrypt(plaintext,key,isReserveNotLetter,true);
	}
	
	public static String encrypt(String plaintext,int key) {
		return encrypt(plaintext,key,true,true);
	}
	
	/**
	 * �����㷨
	 * @param ciphertext ����
	 * @param key ��Կ
	 * @param isReserveNotLetter �Ƿ�������ĸ�ַ�
	 * @param isIgnoreCase �Ƿ������ĸ��Сд
	 * @return ����
	 */
	public static String decrypt(String ciphertext,int key,boolean isReserveNotLetter,boolean isIgnoreCase) {
		if(!isReserveNotLetter) {
			ciphertext = CharacterUtil.filterNotLetter(ciphertext);
		}
		if(ciphertext == null || ciphertext.equals("")) {
			return null;
		}
		StringBuilder plaintext = new StringBuilder();
		int length = ciphertext.length();
		for(int i=0;i < length; i++) {
			char ch = ciphertext.charAt(i);
			if(isIgnoreCase && ch >= 'a' &&  ch <= 'z') {
				ch = (char) ((Character.toUpperCase(ch) - 'A' - key + LETTER_LENGTH) % LETTER_LENGTH + 'A');
			}else if(ch >= 'A' &&  ch <= 'Z') {
				ch = (char) ((ch - 'A' - key + LETTER_LENGTH) % LETTER_LENGTH + 'A');
			}else if(ch >= 'a' &&  ch <= 'z') {
				ch = (char) ((ch - 'a' - key + LETTER_LENGTH) % LETTER_LENGTH + 'a');
			}
			plaintext.append(ch);
		}
		return plaintext.toString();
	}
	
	public static String decrypt(String ciphertext,int key,boolean isReserveNotLetter) {
		return decrypt(ciphertext,key,isReserveNotLetter,true);
	}
	
	public static String decrypt(String ciphertext,int key) {
		return decrypt(ciphertext,key,true,true);
	}
	
	
	/**
	 * �����ƽ�
	 * @param ciphertxt ����
	 * @return �ƽ���
	 */
	public static CaesarMessage autoCrack(String ciphertext,boolean isReserveNotLetter,boolean isIgnoreCase) {
		int targetKey = 0;		// ���ܵ���Կ
		float similarity = 0;	// ���ƶ�
		double[] letterFrequency = getLetterFrequency(ciphertext);
		if(letterFrequency == null) {
			return null;
		}
		for(int key = 0; key< LETTER_LENGTH;key++) {
			double sumpq = 0;
			double sumqq = 0;
			for(int j=0;j< LETTER_LENGTH;j++) {
				sumpq += (letterFrequency[(j+key)%26]*STANDARD_FREQUENCY[j]);
				sumqq += Math.pow(letterFrequency[j], 2);
			}
			if(sumqq == 0) {
				return null;
			}
			float temp = (float) (sumpq / Math.sqrt(TARGET * sumqq) * 100); // �������ƶ�*100
			if(temp > similarity) {
				similarity = temp;
				targetKey = key;
			}
		}
		return new CaesarMessage(decrypt(ciphertext, targetKey,isReserveNotLetter,isIgnoreCase), targetKey, ciphertext,similarity);
	}
	
	/**
	 * ��ȡ�����и���ĸ���ֵ�Ƶ��
	 * @param ciphertext ����
	 * @return ��ĸ����Ƶ�ʼ�
	 */
	public static double[] getLetterFrequency(String ciphertext) {
		int sum = 0;
		double[] letterFrequency = new double[LETTER_LENGTH];
		ciphertext = ciphertext.toUpperCase();
		for(int i = 0;i < ciphertext.length(); i++) {
			char ch = ciphertext.charAt(i);
			if(ch >= 'A' &&  ch <= 'Z') {
				letterFrequency[ch -'A']++;
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
	 * @param isReserveNotLetter �Ƿ�������ĸ�ַ�
	 * @param isIgnoreCase �Ƿ������ĸ��Сд
	 * @return ���ܵ����Ľ����
	 */
	public static ArrayList<Message> exhaustCrack(String ciphertext,boolean isReserveNotLetter,boolean isIgnoreCase){
		if(!isReserveNotLetter) {
			ciphertext = CharacterUtil.filterNotLetter(ciphertext);
		}
		if(ciphertext == null || ciphertext.length() == 0) {
			return null;
		}
		ArrayList<Message> messages = new ArrayList<>();
		for(int key=0;key<LETTER_LENGTH;key++) {
			String plaintext = decrypt(ciphertext, key,isReserveNotLetter,isIgnoreCase);
			messages.add(new Message( key +"",plaintext,ciphertext));
		}
		return messages;
	}
	
	public static ArrayList<Message> exhaustCrack(String ciphertext,boolean isReserveNotLetter){
		return exhaustCrack(ciphertext, isReserveNotLetter,true);
	}
	
	public static ArrayList<Message> exhaustCrack(String ciphertext){
		return exhaustCrack(ciphertext, true,true);
	}
	
	public static boolean isKeyAvailable(String testKey) {
		int key;
		try {
			key = Integer.parseInt(testKey);
		}catch (Exception e) {
			return false;
		}
		if(key >= 0 && key < 26) {
			return true;
		}
		return false;
	}
}
