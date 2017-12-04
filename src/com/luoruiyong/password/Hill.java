package com.luoruiyong.password;

import com.luoruiyong.util.CharacterUtil;
import com.luoruiyong.util.MatrixUtil;

/**
 * Hill密码算法类
 * @author Administrator
 *
 */

public class Hill {
	
	private static final int M = 3;
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
		return praser(getDividePlaintext(plaintext), matrixData);
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
	 * 解析算法
	 * @param text  待解析文本
	 * @param matrixData 用于解析的矩阵数据（可能是原矩阵，也可能时逆矩阵）
	 * @return 解析后的文本信息
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
	 * 分组处理
	 * @param plaintext
	 * @return
	 */
	public static String getDividePlaintext(String plaintext) {
		plaintext = CharacterUtil.filterNotLetter(plaintext);
		if(plaintext == null || plaintext.length() == 0) {
			return null;
		}else if(plaintext.length() % 3 == 1) {
			// 最后一个分组只有1个字母，填充字符'XX'
			plaintext = plaintext+"XX";
		}else if(plaintext.length() % 3 == 2) {
			// 最后一个分组只有1个字母，填充字符'x'
			plaintext = plaintext+"X";
		}
		return plaintext.toUpperCase();
	}
	
	/**
	 * 检验密约是否有效
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
