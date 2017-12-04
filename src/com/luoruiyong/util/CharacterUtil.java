package com.luoruiyong.util;


public class CharacterUtil {
	/**
	 * 过滤非字母字符
	 * @param text 待处理文本
	 * @return 纯字母文本
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
	 * 去除重复的字母
	 * @param text 待处理文本
	 * @param isIgnoreCase  是否忽略大小写
	 * @return 处理结果文本
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
	 * 去除重复的字母,不忽略大小写
	 * @param text
	 * @return
	 */
	public static String removeRepeatedLetter(String text) {
		return removeRepeatedLetter(text, false);
	}
	
	/**
	 * 去除指定字母
	 * @param text 待处理文本
	 * @param target 目标字母
	 * @param isIgnoreCase 是否忽略大小写
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
