package com.luoruiyong.util;


public class CharacterUtil {
	/**
	 * ���˷���ĸ�ַ�
	 * @param text �������ı�
	 * @return ����ĸ�ı�
	 */
	public static String filterNotLetter(String text) {
		if(text == null) {
			return null;
		}
		StringBuilder textBuilder = new StringBuilder();
		for(int i = 0;i<text.length();i++) {
			char ch = text.charAt(i);
			if(ch >= 'a' && ch <= 'z' || ch >= 'A' && ch <= 'Z') {
				textBuilder.append(ch);
			}
		}
		if(textBuilder.length() == 0) {
			return null;
		}
		return textBuilder.toString();
	}
	
	/**
	 * ȥ���ظ�����ĸ
	 * @param text �������ı�
	 * @param isIgnoreCase  �Ƿ���Դ�Сд
	 * @return �������ı�
	 */
	public static String removeRepeatedLetter(String text,boolean isIgnoreCase) {
		if(text == null) {
			return null;
		}
		StringBuilder textBuilder = new StringBuilder();
		for(int i = 0;i<text.length();i++) {
			char ch = text.charAt(i);
			int index = 0;
			for(;index < textBuilder.length();index ++) {
				if(isIgnoreCase && (textBuilder.charAt(index) == Character.toLowerCase(ch) 
							|| textBuilder.charAt(index) == Character.toUpperCase(ch))) {
						break;
				}else if(textBuilder.charAt(index) == ch) {
					break;
				}
			}
			if(index >= textBuilder.length()) {
				textBuilder.append(ch);
			}
		}
		return textBuilder.toString();
	}
	
	/**
	 * ȥ���ظ�����ĸ,�����Դ�Сд
	 * @param text
	 * @return
	 */
	public static String removeRepeatedLetter(String text) {
		return removeRepeatedLetter(text, false);
	}
	
	/**
	 * ȥ��ָ����ĸ
	 * @param text �������ı�
	 * @param target Ŀ����ĸ
	 * @param isIgnoreCase �Ƿ���Դ�Сд
	 * @return
	 */
	public static String removeLetter(String text,char target,boolean isIgnoreCase) {
		if(text == null) {
			return null;
		}
		if(!text.contains(target+"")) {
			return text;
		}
		char target1 = Character.toUpperCase(target);
		char target2 = Character.toLowerCase(target);
		StringBuilder textBuilder = new StringBuilder();
		for(int i = 0;i<text.length();i++) {
			char ch = text.charAt(i);
			if(isIgnoreCase) {
				if(ch != target1 && ch != target2) {
					textBuilder.append(ch);
				}
			}else if(ch != target) {
				textBuilder.append(ch);
			}
		}
		return textBuilder.toString();
	}
	
	public static String removeLetter(String text,char target) {
		return removeLetter(text, target,false);
	}
	
	
	
}
