package com.luoruiyong.util;

public class MatrixUtil {
private final static int M = 3;  // 3�׾���
	
	/**
	 * ��ٻ�ȡ����󣨲���ʵ�������ϵ������
	 * @param matrixData ԭ��������
	 * @return ���������
	 */
	public static int[][] getForceInverseMatrix(int[][] matrixData){
		int[][] newMatrixData = new int[M][M];
		int[] tempMatrix = {1,0,0};
		boolean flag = false;
		for(int num = 0;num < M;num ++) {
			// ��
			flag = false;
			for(int i = 0;i < 26 && !flag;i++) {
				for(int j = 0;j < 26 && !flag;j++) {
					for(int k = 0; k < 26 && !flag;k++) {
						if((matrixData[0][0] * i + matrixData[1][0] * j + matrixData[2][0] * k)%26 == tempMatrix[num%M]
							&& (matrixData[0][1] * i + matrixData[1][1] * j + matrixData[2][1] * k)%26 == tempMatrix[(num+2)%M]
								&& (matrixData[0][2] * i + matrixData[1][2] * j + matrixData[2][2] * k)%26 == tempMatrix[(num+1)% M]) {
							newMatrixData[num][0] = i;
							newMatrixData[num][1] = j;
							newMatrixData[num][2] = k;
							flag = true;
						}
					}
				}
			}
			if(!flag) {
				return null;
			}
		}
		return newMatrixData;
	}
	
	/**
	 * �ַ�����Կת��Ϊ���;�����Կ
	 * @param key  �ַ�����Կ
	 * @return ���;�����Կ
	 */
	public static int[][] stringToIntMatrix(String key){
		key = CharacterUtil.filterNotLetter(key);
		if(key == null || key.equals("")) {
			return null;
		}
		key = key.toUpperCase();
		int m = (int) Math.sqrt(key.length());
		int[][] matrixData = new int[m][m];
		for(int i = 0;i < m;i++) {
			for(int j=0;j< m;j++) {
				matrixData[i][j] = key.charAt(i*m+j) - 'A';
			}
		}
		return matrixData;
	}
	
	/**
	 * �ַ�����Կת��Ϊ�ַ�������Կ
	 * @param key  �ַ�����Կ
	 * @return �ַ�������Կ
	 */
	public static char[][] stringToCharMatrix(String key){
		key = CharacterUtil.filterNotLetter(key);
		if(key == null || key.equals("")) {
			return null;
		}
		key = key.toUpperCase();
		int m = (int) Math.sqrt(key.length());
		char[][] matrixData = new char[m][m];
		for(int i = 0;i < m;i++) {
			for(int j=0;j< m;j++) {
				matrixData[i][j] = key.charAt(i*m+j);
			}
		}
		return matrixData;
	}
	
	/**
	 * ���;���ת�ַ�����
	 * @param matrixData ���;���
	 * @return �ַ�����
	 */
	public static char[][] intMatrixToCharMatrix(int[][] matrixData){
		if(matrixData == null || matrixData.length == 0 || matrixData[0].length == 0) {
			return null;
		}
		int row = matrixData.length;
		int column = matrixData[0].length;
		char[][] newMatrixData = new char[row][column];
		for(int i = 0;i < row;i++) {
			for(int j = 0;j<column;j++) {
				newMatrixData[i][j] = (char) (matrixData[i][j] + 'A');
			}
		}
		return newMatrixData;
	}
	
	/**
	 * ��ȡ�����ά�ȣ��ǷǷ��󷵻�0
	 * @param matrixData ��������
	 * @return ����ά�Ȼ�0
	 */
	public static int getDimensionality(int[][] matrixData) {
		int dimensionality = 0;
		if(matrixData != null && matrixData.length != 0 && matrixData[0].length != 0) {
			if(matrixData.length == matrixData[0].length) {
				dimensionality = matrixData.length;
			}
		}
		return dimensionality;
	}
}
