package com.luoruiyong.password;

import com.luoruiyong.util.CharacterUtil;
import com.luoruiyong.util.MatrixUtil;

/**
 * Hill�����㷨��
 * @author Administrator
 *
 */

public class Hill {
	
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
		return praser(plaintext, matrixData);
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
		if(inverseMatrixData == null) {
			return null;
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
		if(text == null || text.length() == 0) {
			return null;
		}else if(text.length() % 3 == 1) {
			// ���һ������ֻ��1����ĸ������ַ�'XX'
			text = text+"XX";
		}else if(text.length() % 3 == 2) {
			// ���һ������ֻ��1����ĸ������ַ�'x'
			text = text+"X";
		}
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
				textBuilder.append((char)(sum%26+'a'));
			}
		}
		return textBuilder.toString();
	}
}
