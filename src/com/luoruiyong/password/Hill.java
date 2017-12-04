package com.luoruiyong.password;

import com.luoruiyong.util.CharacterUtil;
import com.luoruiyong.util.MatrixUtil;

/**
 * Hill�����㷨��
 * @author Administrator
 *
 */

public class Hill {
	
	private static final int M = 3;
	/**
	 * �����㷨
	 * @param plaintext ����
	 * @param matrixData ��Կ����
	 * @return ����
	 */
	public static String encrypt(String plaintext,int[][] matrixData) {
		
		if(MatrixUtil.getDimensionality(matrixData) == 0) {
			// ������Կ��Ч
			return null;
		}
		return praser(getDividePlaintext(plaintext), matrixData);
	}
	
	/**
	 * �����㷨
	 * @param ciphertext ����
	 * @param matrixData ��Կ����ע�ⲻ�������
	 * @return ����
	 */
	public static String decrypt(String ciphertext,int[][] matrixData) {
		if(MatrixUtil.getDimensionality(matrixData) == 0) {
			// ������Կ��Ч
			return null;
		}
		int[][] inverseMatrixData = MatrixUtil.getForceInverseMatrix(matrixData);
		ciphertext = CharacterUtil.filterNotLetter(ciphertext);
		if(inverseMatrixData == null || ciphertext == null || ciphertext.length() < M) {
			return null;
		}else {
			if(ciphertext.length() % M != 0) {
				ciphertext = ciphertext.substring(0, ciphertext.length() - ciphertext.length() % 3).toUpperCase();
			}
		}
		return praser(ciphertext, inverseMatrixData);
	}
	
	/**
	 * �����㷨
	 * @param text  �������ı�
	 * @param matrixData ���ڽ����ľ������ݣ�������ԭ����Ҳ����ʱ�����
	 * @return ��������ı���Ϣ
	 */
	private static String praser(String text,int[][] matrixData) {
		text = CharacterUtil.filterNotLetter(text).toUpperCase();
		StringBuilder textBuilder = new StringBuilder();
		for(int index = 0;index< text.length() ;index+=3) {
			int temp1 = text.charAt(index) - 'A';
			int temp2 = text.charAt(index+1) - 'A';
			int temp3 = text.charAt(index+2) - 'A';
			int[] temp = {temp1,temp2,temp3};
			for(int i = 0;i<3;i++) {
				int sum = 0;
				for(int j=0;j<3;j++) {
					sum += temp[j] * matrixData[i][j];
				}
				textBuilder.append((char)(sum%26+'A'));
			}
		}
		return textBuilder.toString();
	}
	
	/**
	 * ���鴦��
	 * @param plaintext
	 * @return
	 */
	public static String getDividePlaintext(String plaintext) {
		plaintext = CharacterUtil.filterNotLetter(plaintext);
		if(plaintext == null || plaintext.length() == 0) {
			return null;
		}else if(plaintext.length() % 3 == 1) {
			// ���һ������ֻ��1����ĸ������ַ�'XX'
			plaintext = plaintext+"XX";
		}else if(plaintext.length() % 3 == 2) {
			// ���һ������ֻ��1����ĸ������ַ�'x'
			plaintext = plaintext+"X";
		}
		return plaintext.toUpperCase();
	}
	
	/**
	 * ������Լ�Ƿ���Ч
	 * @param testKey
	 * @return
	 */
	public static int[][] isKeyAvailable(String testKey) {
		String key = CharacterUtil.filterNotLetter(testKey);
		if(key != null) {
			if(key.length() < M*M) {
				return null;
			}else {
				int[][] matrixData = MatrixUtil.stringToIntMatrix(key);
				if(MatrixUtil.getForceInverseMatrix(matrixData) != null) {
					return matrixData;
				}else {
					return null;
				}
			}
		}else {
			int[][] matrixData = intStringToIntMatrix(testKey);
			if (matrixData == null) {
				return null;
			}else if(MatrixUtil.getForceInverseMatrix(matrixData) != null){
				return matrixData;
			}
			return null;
		}
	}
	
	private static int[][] intStringToIntMatrix(String testKey){
		String[] arrstr = testKey.split(" ");
		if(arrstr == null || arrstr.length < M*M) {
			return null;
		}
		int[][] matrixData = new int[M][M];
		int count = 0;
		for(int i = 0;i<arrstr.length && count < M*M;i++) {
			try {
				int temp = Integer.parseInt(arrstr[i]);
				if(temp < 26 && temp >= 0) {
					matrixData[i/M][i%M] = temp;
					count+=1;
				}
			}catch (Exception e) {
				return null;
			}
		}
		if(count == M*M) {
			return matrixData;
		}
		return null;
	}
}
