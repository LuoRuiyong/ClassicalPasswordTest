package com.luoruiyong.password;

import com.luoruiyong.util.CharacterUtil;

/**
 * Playfair密码算法类
 * @author Administrator
 *
 */
public class Playfair {
	
	private static final int M = 5;
	private static final char DOUBLE_MEAN_LETTER = 'I';
	private static final char DEFAULT_IGNORE_LETTER = 'J';
	private static final char DEFAULT_FILL_CHARACTER = 'K';
	private static final char SPARE_FILL_CHARACTER = 'Z';
	private static final int TYPE_ENCRYPT = 1;
	private static final int TYPE_DECRYPT = 2;
	private static int doubleMeanLetterRow = 0;
	private static int doubleMeanLetterColumn = 0;
	
	/**
	 * 加密算法
	 * @param plaintext 明文
	 * @param key 密钥
	 * @return 密文
	 */
	public static String encrypt(String plaintext,String key) {
		plaintext = CharacterUtil.filterNotLetter(plaintext);
		key = CharacterUtil.filterNotLetter(key);
		if(plaintext == null || key == null) {
			return null;
		}
		plaintext = plaintext.toUpperCase();
		return encrypt(plaintext, getKeyMatrix(key));
	}
	
	public static String encrypt(String plaintext,char[][] keyMatrix) {
		plaintext = CharacterUtil.filterNotLetter(plaintext);
		if(plaintext == null || keyMatrix == null) {
			return null;
		}
		plaintext = plaintext.toUpperCase();
		return praser(plaintext, keyMatrix, TYPE_ENCRYPT);
	}
	
	/**
	 * 解密算法
	 * @param ciphertext 密文
	 * @param key 密钥
	 * @return 明文
	 */
	public static String decrypt(String ciphertext,String key) {
		ciphertext = CharacterUtil.filterNotLetter(ciphertext);
		key = CharacterUtil.filterNotLetter(key);
		if(ciphertext == null || key == null) {
			return null;
		}
		ciphertext = ciphertext.toUpperCase();
		return decrypt(ciphertext, getKeyMatrix(key));
	}
	
	public static String decrypt(String ciphertext,char[][] keyMatrix) {
		ciphertext = CharacterUtil.filterNotLetter(ciphertext);
		if(ciphertext == null || keyMatrix == null) {
			return null;
		}
		ciphertext = ciphertext.toUpperCase();
		return praser(ciphertext, keyMatrix, TYPE_DECRYPT);
	}
	
	/**
	 * 解析算法，包含加解密
	 * @param text 明文或密文
	 * @param key 密钥
	 * @param praserType 解析类型（加密/解密）
	 * @return 明文或密文
	 */
	private static String praser(String text,char[][] keyMatrix,int praserType) {
		if(praserType == TYPE_ENCRYPT) {
			text = getDevidePlaintext(text);
		}
		StringBuilder textBuilder = new StringBuilder();
		for(int index = 0;index < text.length();index+=2) {
			char firstCharacter = text.charAt(index);
			char secondCharacter = text.charAt(index + 1);
			// 分别记录第一第二个字母在密钥矩阵中出现的位置
			int m1 = 0,n1 = 0,m2 = 0,n2 = 0;
			// 分别记录两个字母是否匹配到密钥矩阵中的字母，匹配失败则表示当前字母为忽略字母
			boolean flag1 = false,flag2 = false;
			for(int i = 0; i < M;i++) {
				for(int j = 0;j < M; j++) {
					if(keyMatrix[i][j] == firstCharacter) {
						m1 = i;
						n1 = j;
						flag1 = true;
					}else if(keyMatrix[i][j] == secondCharacter) {
						m2 = i;
						n2 = j;
						flag2 = true;
					}
				}
			}
			if(!flag1) {
				m1 = doubleMeanLetterRow;
				n1 = doubleMeanLetterColumn;
			}else if(!flag2) {
				m2 = doubleMeanLetterRow;
				n2 = doubleMeanLetterColumn;
			}
			switch (praserType) {
				case TYPE_ENCRYPT:
					if(m1 == m2) {
						// 同一行，循环右移一位
						firstCharacter = keyMatrix[m1][(n1+1)%M];
						secondCharacter = keyMatrix[m2][(n2+1)%M];
					}else if(n1 == n2) {
						// 同一列，循环下移一位
						firstCharacter = keyMatrix[(m1+1)%M][n1];
						secondCharacter = keyMatrix[(m2+1)%M][n2];
					}else {
						// 不同行不同列
						firstCharacter =  keyMatrix[m1][n2];
						secondCharacter = keyMatrix[m2][n1];
					}
					break;
				default:
					if(m1 == m2) {
						// 同一行，循环左移一位
						firstCharacter = keyMatrix[m1][(n1-1+M)%M];
						secondCharacter = keyMatrix[m2][(n2-1+M)%M];
					}else if(n1 == n2) {
						// 同一列，循环上移一位
						firstCharacter = keyMatrix[(m1-1+M)%M][n1];
						secondCharacter = keyMatrix[(m2-1+M)%M][n2];
					}else {
						// 不同行不同列
						firstCharacter =  keyMatrix[m1][n2];
						secondCharacter = keyMatrix[m2][n1];
					}
					break;
			}
			textBuilder.append(firstCharacter);
			textBuilder.append(secondCharacter);
		}
		return textBuilder.toString();
	}
	
	/**
	 * 初始化密钥矩阵
	 * @param key 字符串密钥
	 * @return 密钥矩阵
	 */
	public static char[][] getKeyMatrix(String key) {
		char[][] keyMatrix = new char[M][M];
		// 过滤密钥中的非字母字符
		key = CharacterUtil.filterNotLetter(key);
		if(key == null) {
			return null;
		}
		// 大写字母
		key = key.toUpperCase();
		// 去除重复的字母
		key = CharacterUtil.removeRepeatedLetter(key+"ABCDEFGHIJKLMNOPQRSTUVWXYZ",true);
		// 去除默认忽略的字符
		key = CharacterUtil.removeLetter(key, DEFAULT_IGNORE_LETTER,true);
		for(int i = 0;i<M;i++) {
			for(int j = 0;j<M;j++) {
				char ch = key.charAt(i*M+j);
				keyMatrix[i][j] = ch;
				if(ch == DOUBLE_MEAN_LETTER) {
					doubleMeanLetterRow = i;
					doubleMeanLetterColumn = j;
				}
			}
		}
		return keyMatrix;
	}
	
	/**
	 * 明文两两划分为若干组，组内重复填充'K'字符，'K'字符重复填充'Z'，最后一组不够字符填充'K'或'Z'
	 * @param plaintext 明文
	 * @return 分组处理后的明文
	 */
	public static String getDevidePlaintext(String plaintext) {
		char firstCharacter = '0';
		char secondCharacter = '0';
		StringBuilder plaintextBuilder = new StringBuilder();
		for(int i = 0 ;i < plaintext.length();i++) {
			char ch = plaintext.charAt(i);
			plaintextBuilder.append(ch);
			if(plaintextBuilder.length() % 2 == 1) {
				firstCharacter = ch;
			}else {
				secondCharacter = ch;
				// 分组中字母重复，填充指定字符
				if(firstCharacter == secondCharacter) {
					plaintextBuilder.insert(plaintextBuilder.length()-1,fillCharacter(firstCharacter));
				}
			}	
		}
		if(plaintextBuilder.length() % 2 == 1) {
			// 最后一个分组只有一个字母，填充指定字符
			plaintextBuilder.append(fillCharacter(firstCharacter));
		}
		return plaintextBuilder.toString();
	}
	
	/**
	 * 获取填充字母
	 * @param ch 填充字母的前行字母
	 * @return 填充字母
	 */
	private static char fillCharacter(char ch) {
		return ch != DEFAULT_FILL_CHARACTER ? DEFAULT_FILL_CHARACTER : SPARE_FILL_CHARACTER;
	}
}
