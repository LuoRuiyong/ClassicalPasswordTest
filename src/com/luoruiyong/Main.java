package com.luoruiyong;

import java.util.ArrayList;

import com.luoruiyong.bean.CaesarMessage;
import com.luoruiyong.bean.Message;
import com.luoruiyong.password.Caesar;
import com.luoruiyong.password.Hill;
import com.luoruiyong.password.Playfair;
import com.luoruiyong.util.CharacterUtil;
import com.luoruiyong.util.CompareUtil;
import com.luoruiyong.util.MatrixUtil;

/**
 * 程序入口
 * @author Administrator
 *
 */
public class Main {
	public static void main(String[] args) {
		System.out.println("Hello World!");
//		【凯撒密码验证】
		int key = 3;
		String plaintext1 = "We are discovered save yourself!";
		String ciphertext1_1 = Caesar.encrypt(plaintext1, key);
		String ciphertext1_2 = Caesar.encrypt(plaintext1, key,false);
		String ciphertext1_3 = Caesar.encrypt(plaintext1, key,false,false);
		System.out.println("\nCaesar密码算法验证");
		System.out.println("1.加密：");
		System.out.println("明文："+plaintext1);
		System.out.println("密钥："+key);
		System.out.println("忽略非字母字符，忽略字母大小写加密："+ciphertext1_1);
		System.out.println("不忽略非字母字符，忽略字母大小写加密："+ciphertext1_2);
		System.out.println("不忽略非字母字符，不忽略字母大小写加密："+ciphertext1_3);
		
		System.out.println("\n2.解密：");
		System.out.println("密文："+ciphertext1_3);
		System.out.println("密钥："+key);
		String plaintext1_1 = Caesar.decrypt(ciphertext1_3, key);
		String plaintext1_2 = Caesar.decrypt(ciphertext1_3, key,false);
		String plaintext1_3 = Caesar.decrypt(ciphertext1_3, key,false,false);
		System.out.println("忽略非字母字符，忽略字母大小写解密："+plaintext1_1);
		System.out.println("不忽略非字母字符，忽略字母大小写解密："+plaintext1_2);
		System.out.println("不忽略非字母字符，不忽略字母大小写解密："+plaintext1_3);
		
		System.out.println("\n3.穷举法破密:");
		System.out.println("密文："+ciphertext1_3);
		ArrayList<CaesarMessage> messages = Caesar.exhaustCrack(ciphertext1_3, false, false);
		System.out.println("可能的密钥\t对应的明文");
		for(CaesarMessage message : messages) {
			System.out.println(message.getKey() + "\t"+message.getPlaintext());
		}
		
		System.out.println("\n4.概率分析破解：");
		System.out.println("密文："+ciphertext1_3);
		double[] frequency = Caesar.getLetterFrequency(ciphertext1_3);
		double[] standardFrequency = Caesar.STANDARD_FREQUENCY;
		System.out.println("字母\t出现频率\t标准频率");
		for(int i = 0;i < 26 ;i++) {
			char ch = (char) (i + 65);
			System.out.println(ch+"\t"+frequency[i]+"\t"+standardFrequency[i]);
		}
		CaesarMessage msg1 = Caesar.autoCrack(ciphertext1_3, false, false);
		System.out.println("破解得到的密钥："+msg1.getKey());
		System.out.println("密钥可参考性："+msg1.getSimilarity() + "%");
		System.out.println("破解得到的明文："+msg1.getPlaintext());
		
		System.out.println("\n5.分组对比分析：");
		ArrayList<Message> messages1 = CompareUtil.getDetailMessage(plaintext1, ciphertext1_3, 1);
		System.out.print("明文：");
		for(Message message1 : messages1) {
			System.out.print(message1.getPlaintext()+" ");
		}
		System.out.print("\n密文：");
		for(Message message1 : messages1) {
			System.out.print(message1.getCiphertext()+" ");
		}
		System.out.println();
		
//	【Playfair密码验证】
		String key2 = "monarchy";
		String plaintext2 = "We are discovered save yourself!";
		String ciphertext2 = Playfair.encrypt(plaintext2, key2);
		
		System.out.println("\nPlayfair密码算法验证");
		System.out.println("1.加密");
		System.out.println("明文："+plaintext2);
		System.out.println("密钥："+key2);
		System.out.println("生成密钥矩阵：");
		char[][] keyMatrix = Playfair.getKeyMatrix(key2);
		for(int i = 0;i < keyMatrix.length;i++) {
			for(int j = 0;j<keyMatrix[0].length;j++) {
				if(keyMatrix[i][j] == 'I' || keyMatrix[i][j] == 'J') {
					System.out.print("I/J\t");
				}else {
					System.out.print(keyMatrix[i][j]+"\t");
				}
			}
			System.out.println();
		}
		System.out.println("加密得到的密文:"+ciphertext2);
		
		System.out.println("\n2.解密");
		System.out.println("密文："+ciphertext2);
		System.out.println("密钥："+key2);
		System.out.println("生成密钥矩阵：");
		keyMatrix = Playfair.getKeyMatrix(key2);
		for(int i = 0;i < keyMatrix.length;i++) {
			for(int j = 0;j<keyMatrix[0].length;j++) {
				if(keyMatrix[i][j] == 'I' || keyMatrix[i][j] == 'J') {
					System.out.print("I/J\t");
				}else {
					System.out.print(keyMatrix[i][j]+"\t");
				}
			}
			System.out.println();
		}
		String plaintext2_1 = Playfair.decrypt(ciphertext2, key2);
		System.out.println("解密得到的明文:"+plaintext2_1);
		
		System.out.println("\n3.分组对比");
		String plaintext2_2 = Playfair.getDevidePlaintext(plaintext2);
		System.out.println("分组后的明文"+plaintext2_2);
		ArrayList<Message> messages2 = CompareUtil.getDetailMessage(plaintext2_2, ciphertext2, 2);
		System.out.print("明文：");
		for(Message message2 : messages2) {
			System.out.print(message2.getPlaintext()+" ");
		}
		System.out.print("\n密文：");
		for(Message message2 : messages2) {
			System.out.print(message2.getCiphertext()+" ");
		}
		System.out.println();
		
//	Hill测试
		String key3 = "helloword";
		String plaintext3 = "paymoremeney";
		int[][] matrixData3 = {{17,17,5},{21,18,21},{2,2,19}};
		String ciphertext3 = Hill.encrypt(plaintext3, matrixData3);
		ArrayList<Message> messages3 = CompareUtil.getDetailMessage(plaintext3, ciphertext3, 3);
		
		System.out.println("\nHill密码算法验证");
		System.out.println("1.加密");
		System.out.println("明文:"+plaintext3);
		char[][] charMatrix3 = MatrixUtil.intMatrixToCharMatrix(matrixData3);
		System.out.println("字符密钥矩阵：\t\t\t整型密钥矩阵");
		for(int i = 0;i < charMatrix3.length;i++) {
			for(int j = 0;j < charMatrix3[0].length;j++) {
				System.out.print(charMatrix3[i][j]+"\t");
			}
			if(i == charMatrix3.length / 2) {
				System.out.print("--->\t");
			}else {
				System.out.print("\t");
			}
			for(int j = 0;j < matrixData3[0].length;j++) {
				System.out.print(matrixData3[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("加密得到的密文:"+ciphertext3);
		
		System.out.println("\n2.解密");
		System.out.println("密文："+ciphertext3);
		System.out.println("原字符密钥矩阵\t\t\t原整型密钥矩阵");
		for(int i = 0;i < charMatrix3.length;i++) {
			for(int j = 0;j < charMatrix3[0].length;j++) {
				System.out.print(charMatrix3[i][j]+"\t");
			}
			if(i == charMatrix3.length / 2) {
				System.out.print("--->\t");
			}else {
				System.out.print("\t");
			}
			for(int j = 0;j < matrixData3[0].length;j++) {
				System.out.print(matrixData3[i][j]+"\t");
			}
			System.out.println();
		}
		String plaintext3_1 = Hill.decrypt(ciphertext3, matrixData3);
		int[][] matrixData3_1 = MatrixUtil.getForceInverseMatrix(matrixData3);
		char[][] charMatrix3_1 = MatrixUtil.intMatrixToCharMatrix(matrixData3);
		System.out.println("字符密钥逆矩阵\t\t\t整型密钥逆矩阵");
		for(int i = 0;i < charMatrix3_1.length;i++) {
			for(int j = 0;j < charMatrix3_1[0].length;j++) {
				System.out.print(charMatrix3_1[i][j]+"\t");
			}
			if(i == charMatrix3_1.length / 2) {
				System.out.print("--->\t");
			}else {
				System.out.print("\t");
			}
			for(int j = 0;j < matrixData3_1[0].length;j++) {
				System.out.print(matrixData3_1[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("解密得到的明文:"+plaintext3_1);
		
		System.out.println("\n3.分组对比");
		System.out.print("明文：");
		for(Message message : messages3) {
			System.out.print(message.getPlaintext()+" ");
		}
		System.out.print("\n密文：");
		for(Message message : messages3) {
			System.out.print(message.getCiphertext()+" ");
		}	
		
		System.out.println("\n4.通过字符串生成密钥");
		System.out.println("字符串密钥："+key3);
		char[][] charMatrix3_2 = MatrixUtil.stringToCharMatrix(key3);
		int[][] matrixData3_2 = MatrixUtil.stringToIntMatrix(key3);
		int[][] matrixData3_3 = MatrixUtil.getForceInverseMatrix(matrixData3_2);
		char[][] charMatrix3_3 = MatrixUtil.intMatrixToCharMatrix(matrixData3_3);
		System.out.println("生成字符密钥矩阵\t\t\t对应的整型密钥矩阵");
		for(int i = 0;i < charMatrix3_2.length;i++) {
			for(int j = 0;j < charMatrix3_2[0].length;j++) {
				System.out.print(charMatrix3_2[i][j]+"\t");
			}
			if(i == charMatrix3_2.length / 2) {
				System.out.print("--->\t");
			}else {
				System.out.print("\t");
			}
			for(int j = 0;j < matrixData3_2[0].length;j++) {
				System.out.print(matrixData3_2[i][j]+"\t");
			}
			System.out.println();
		}
		System.out.println("字符密钥逆矩阵\t\t\t对应的整型密钥逆矩阵");
		for(int i = 0;i < charMatrix3_3.length;i++) {
			for(int j = 0;j < charMatrix3_3[0].length;j++) {
				System.out.print(charMatrix3_3[i][j]+"\t");
			}
			if(i == charMatrix3_3.length / 2) {
				System.out.print("--->\t");
			}else {
				System.out.print("\t");
			}
			for(int j = 0;j < matrixData3_3[0].length;j++) {
				System.out.print(matrixData3_3[i][j]+"\t");
			}
			System.out.println();
		}
		String plaintext3_2 = "Nicetomeetyou";
		System.out.println("明文："+plaintext3_2);
		String plaintext3_3 = Hill.getDividePlaintext(plaintext3_2);
		String ciphertext3_2 = Hill.encrypt(plaintext3_2, matrixData3_2);
		System.out.println("加密后的密文："+ciphertext3_2);
		String plaintext3_4 = Hill.decrypt(ciphertext3_2, matrixData3_2);
		System.out.println("解密后的明文："+plaintext3_4);
		System.out.println("分组对比:");
		ArrayList<Message> messages3_1 = CompareUtil.getDetailMessage(plaintext3_3, ciphertext3_2, 3);
		System.out.print("明文：");
		for(Message message : messages3_1) {
			System.out.print(message.getPlaintext()+" ");
		}
		System.out.print("\n密文：");
		for(Message message : messages3_1) {
			System.out.print(message.getCiphertext()+" ");
		}	
	}
}
