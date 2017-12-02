package com.luoruiyong.password;

import com.luoruiyong.util.CharacterUtil;
import com.luoruiyong.util.MatrixUtil;

/**
 * Hill密码算法类
 * @author Administrator
 *
 */

public class Hill {
	
	/**
	 * 加密算法
	 * @param plaintext 明文
	 * @param matrixData 密钥矩阵
	 * @return 密文
	 */
	public static String encrypt(String plaintext,int[][] matrixData) {
		if(MatrixUtil.getDimensionality(matrixData) == 0) {
			// 矩阵密钥无效
			return null;
		}
		return praser(plaintext, matrixData);
	}
	
	/**
	 * 解密算法
	 * @param ciphertext 密文
	 * @param matrixData 密钥矩阵（注意不是逆矩阵）
	 * @return 明文
	 */
	public static String decrypt(String ciphertext,int[][] matrixData) {
		if(MatrixUtil.getDimensionality(matrixData) == 0) {
			// 矩阵密钥无效
			return null;
		}
		int[][] inverseMatrixData = MatrixUtil.getForceInverseMatrix(matrixData);
		if(inverseMatrixData == null) {
			return null;
		}
		return praser(ciphertext, inverseMatrixData);
	}
	
	/**
	 * 解析算法
	 * @param text  待解析文本
	 * @param matrixData 用于解析的矩阵数据（可能是原矩阵，也可能时逆矩阵）
	 * @return 解析后的文本信息
	 */
	private static String praser(String text,int[][] matrixData) {
		text = CharacterUtil.filterNotLetter(text).toUpperCase();
		if(text == null || text.length() == 0) {
			return null;
		}else if(text.length() % 3 == 1) {
			// 最后一个分组只有1个字母，填充字符'XX'
			text = text+"XX";
		}else if(text.length() % 3 == 2) {
			// 最后一个分组只有1个字母，填充字符'x'
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
