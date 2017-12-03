package com.luoruiyong.password;

import com.luoruiyong.util.CharacterUtil;

/**
 * Playfair�����㷨��
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
	 * �����㷨
	 * @param plaintext ����
	 * @param key ��Կ
	 * @return ����
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
	 * �����㷨
	 * @param ciphertext ����
	 * @param key ��Կ
	 * @return ����
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
	 * �����㷨�������ӽ���
	 * @param text ���Ļ�����
	 * @param key ��Կ
	 * @param praserType �������ͣ�����/���ܣ�
	 * @return ���Ļ�����
	 */
	private static String praser(String text,char[][] keyMatrix,int praserType) {
		if(praserType == TYPE_ENCRYPT) {
			text = getDevidePlaintext(text);
		}
		StringBuilder textBuilder = new StringBuilder();
		for(int index = 0;index < text.length();index+=2) {
			char firstCharacter = text.charAt(index);
			char secondCharacter = text.charAt(index + 1);
			// �ֱ��¼��һ�ڶ�����ĸ����Կ�����г��ֵ�λ��
			int m1 = 0,n1 = 0,m2 = 0,n2 = 0;
			// �ֱ��¼������ĸ�Ƿ�ƥ�䵽��Կ�����е���ĸ��ƥ��ʧ�����ʾ��ǰ��ĸΪ������ĸ
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
						// ͬһ�У�ѭ������һλ
						firstCharacter = keyMatrix[m1][(n1+1)%M];
						secondCharacter = keyMatrix[m2][(n2+1)%M];
					}else if(n1 == n2) {
						// ͬһ�У�ѭ������һλ
						firstCharacter = keyMatrix[(m1+1)%M][n1];
						secondCharacter = keyMatrix[(m2+1)%M][n2];
					}else {
						// ��ͬ�в�ͬ��
						firstCharacter =  keyMatrix[m1][n2];
						secondCharacter = keyMatrix[m2][n1];
					}
					break;
				default:
					if(m1 == m2) {
						// ͬһ�У�ѭ������һλ
						firstCharacter = keyMatrix[m1][(n1-1+M)%M];
						secondCharacter = keyMatrix[m2][(n2-1+M)%M];
					}else if(n1 == n2) {
						// ͬһ�У�ѭ������һλ
						firstCharacter = keyMatrix[(m1-1+M)%M][n1];
						secondCharacter = keyMatrix[(m2-1+M)%M][n2];
					}else {
						// ��ͬ�в�ͬ��
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
	 * ��ʼ����Կ����
	 * @param key �ַ�����Կ
	 * @return ��Կ����
	 */
	public static char[][] getKeyMatrix(String key) {
		char[][] keyMatrix = new char[M][M];
		// ������Կ�еķ���ĸ�ַ�
		key = CharacterUtil.filterNotLetter(key);
		if(key == null) {
			return null;
		}
		// ��д��ĸ
		key = key.toUpperCase();
		// ȥ���ظ�����ĸ
		key = CharacterUtil.removeRepeatedLetter(key+"ABCDEFGHIJKLMNOPQRSTUVWXYZ",true);
		// ȥ��Ĭ�Ϻ��Ե��ַ�
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
	 * ������������Ϊ�����飬�����ظ����'K'�ַ���'K'�ַ��ظ����'Z'�����һ�鲻���ַ����'K'��'Z'
	 * @param plaintext ����
	 * @return ���鴦��������
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
				// ��������ĸ�ظ������ָ���ַ�
				if(firstCharacter == secondCharacter) {
					plaintextBuilder.insert(plaintextBuilder.length()-1,fillCharacter(firstCharacter));
				}
			}	
		}
		if(plaintextBuilder.length() % 2 == 1) {
			// ���һ������ֻ��һ����ĸ�����ָ���ַ�
			plaintextBuilder.append(fillCharacter(firstCharacter));
		}
		return plaintextBuilder.toString();
	}
	
	/**
	 * ��ȡ�����ĸ
	 * @param ch �����ĸ��ǰ����ĸ
	 * @return �����ĸ
	 */
	private static char fillCharacter(char ch) {
		return ch != DEFAULT_FILL_CHARACTER ? DEFAULT_FILL_CHARACTER : SPARE_FILL_CHARACTER;
	}
}
