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
 * �������
 * @author Administrator
 *
 */
public class Main {
	public static void main(String[] args) {
		System.out.println("Hello World!");
//		������������֤��
		int key = 3;
		String plaintext1 = "We are discovered save yourself!";
		String ciphertext1_1 = Caesar.encrypt(plaintext1, key);
		String ciphertext1_2 = Caesar.encrypt(plaintext1, key,false);
		String ciphertext1_3 = Caesar.encrypt(plaintext1, key,false,false);
		System.out.println("\nCaesar�����㷨��֤");
		System.out.println("1.���ܣ�");
		System.out.println("���ģ�"+plaintext1);
		System.out.println("��Կ��"+key);
		System.out.println("���Է���ĸ�ַ���������ĸ��Сд���ܣ�"+ciphertext1_1);
		System.out.println("�����Է���ĸ�ַ���������ĸ��Сд���ܣ�"+ciphertext1_2);
		System.out.println("�����Է���ĸ�ַ�����������ĸ��Сд���ܣ�"+ciphertext1_3);
		
		System.out.println("\n2.���ܣ�");
		System.out.println("���ģ�"+ciphertext1_3);
		System.out.println("��Կ��"+key);
		String plaintext1_1 = Caesar.decrypt(ciphertext1_3, key);
		String plaintext1_2 = Caesar.decrypt(ciphertext1_3, key,false);
		String plaintext1_3 = Caesar.decrypt(ciphertext1_3, key,false,false);
		System.out.println("���Է���ĸ�ַ���������ĸ��Сд���ܣ�"+plaintext1_1);
		System.out.println("�����Է���ĸ�ַ���������ĸ��Сд���ܣ�"+plaintext1_2);
		System.out.println("�����Է���ĸ�ַ�����������ĸ��Сд���ܣ�"+plaintext1_3);
		
		System.out.println("\n3.��ٷ�����:");
		System.out.println("���ģ�"+ciphertext1_3);
		ArrayList<CaesarMessage> messages = Caesar.exhaustCrack(ciphertext1_3, false, false);
		System.out.println("���ܵ���Կ\t��Ӧ������");
		for(CaesarMessage message : messages) {
			System.out.println(message.getKey() + "\t"+message.getPlaintext());
		}
		
		System.out.println("\n4.���ʷ����ƽ⣺");
		System.out.println("���ģ�"+ciphertext1_3);
		double[] frequency = Caesar.getLetterFrequency(ciphertext1_3);
		double[] standardFrequency = Caesar.STANDARD_FREQUENCY;
		System.out.println("��ĸ\t����Ƶ��\t��׼Ƶ��");
		for(int i = 0;i < 26 ;i++) {
			char ch = (char) (i + 65);
			System.out.println(ch+"\t"+frequency[i]+"\t"+standardFrequency[i]);
		}
		CaesarMessage msg1 = Caesar.autoCrack(ciphertext1_3, false, false);
		System.out.println("�ƽ�õ�����Կ��"+msg1.getKey());
		System.out.println("��Կ�ɲο��ԣ�"+msg1.getSimilarity() + "%");
		System.out.println("�ƽ�õ������ģ�"+msg1.getPlaintext());
		
		System.out.println("\n5.����Աȷ�����");
		ArrayList<Message> messages1 = CompareUtil.getDetailMessage(plaintext1, ciphertext1_3, 1);
		System.out.print("���ģ�");
		for(Message message1 : messages1) {
			System.out.print(message1.getPlaintext()+" ");
		}
		System.out.print("\n���ģ�");
		for(Message message1 : messages1) {
			System.out.print(message1.getCiphertext()+" ");
		}
		System.out.println();
		
//	��Playfair������֤��
		String key2 = "monarchy";
		String plaintext2 = "We are discovered save yourself!";
		String ciphertext2 = Playfair.encrypt(plaintext2, key2);
		
		System.out.println("\nPlayfair�����㷨��֤");
		System.out.println("1.����");
		System.out.println("���ģ�"+plaintext2);
		System.out.println("��Կ��"+key2);
		System.out.println("������Կ����");
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
		System.out.println("���ܵõ�������:"+ciphertext2);
		
		System.out.println("\n2.����");
		System.out.println("���ģ�"+ciphertext2);
		System.out.println("��Կ��"+key2);
		System.out.println("������Կ����");
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
		System.out.println("���ܵõ�������:"+plaintext2_1);
		
		System.out.println("\n3.����Ա�");
		String plaintext2_2 = Playfair.getDevidePlaintext(plaintext2);
		System.out.println("����������"+plaintext2_2);
		ArrayList<Message> messages2 = CompareUtil.getDetailMessage(plaintext2_2, ciphertext2, 2);
		System.out.print("���ģ�");
		for(Message message2 : messages2) {
			System.out.print(message2.getPlaintext()+" ");
		}
		System.out.print("\n���ģ�");
		for(Message message2 : messages2) {
			System.out.print(message2.getCiphertext()+" ");
		}
		System.out.println();
		
//	Hill����
		String key3 = "helloword";
		String plaintext3 = "paymoremeney";
		int[][] matrixData3 = {{17,17,5},{21,18,21},{2,2,19}};
		String ciphertext3 = Hill.encrypt(plaintext3, matrixData3);
		ArrayList<Message> messages3 = CompareUtil.getDetailMessage(plaintext3, ciphertext3, 3);
		
		System.out.println("\nHill�����㷨��֤");
		System.out.println("1.����");
		System.out.println("����:"+plaintext3);
		char[][] charMatrix3 = MatrixUtil.intMatrixToCharMatrix(matrixData3);
		System.out.println("�ַ���Կ����\t\t\t������Կ����");
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
		System.out.println("���ܵõ�������:"+ciphertext3);
		
		System.out.println("\n2.����");
		System.out.println("���ģ�"+ciphertext3);
		System.out.println("ԭ�ַ���Կ����\t\t\tԭ������Կ����");
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
		System.out.println("�ַ���Կ�����\t\t\t������Կ�����");
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
		System.out.println("���ܵõ�������:"+plaintext3_1);
		
		System.out.println("\n3.����Ա�");
		System.out.print("���ģ�");
		for(Message message : messages3) {
			System.out.print(message.getPlaintext()+" ");
		}
		System.out.print("\n���ģ�");
		for(Message message : messages3) {
			System.out.print(message.getCiphertext()+" ");
		}	
		
		System.out.println("\n4.ͨ���ַ���������Կ");
		System.out.println("�ַ�����Կ��"+key3);
		char[][] charMatrix3_2 = MatrixUtil.stringToCharMatrix(key3);
		int[][] matrixData3_2 = MatrixUtil.stringToIntMatrix(key3);
		int[][] matrixData3_3 = MatrixUtil.getForceInverseMatrix(matrixData3_2);
		char[][] charMatrix3_3 = MatrixUtil.intMatrixToCharMatrix(matrixData3_3);
		System.out.println("�����ַ���Կ����\t\t\t��Ӧ��������Կ����");
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
		System.out.println("�ַ���Կ�����\t\t\t��Ӧ��������Կ�����");
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
		System.out.println("���ģ�"+plaintext3_2);
		String plaintext3_3 = Hill.getDividePlaintext(plaintext3_2);
		String ciphertext3_2 = Hill.encrypt(plaintext3_2, matrixData3_2);
		System.out.println("���ܺ�����ģ�"+ciphertext3_2);
		String plaintext3_4 = Hill.decrypt(ciphertext3_2, matrixData3_2);
		System.out.println("���ܺ�����ģ�"+plaintext3_4);
		System.out.println("����Ա�:");
		ArrayList<Message> messages3_1 = CompareUtil.getDetailMessage(plaintext3_3, ciphertext3_2, 3);
		System.out.print("���ģ�");
		for(Message message : messages3_1) {
			System.out.print(message.getPlaintext()+" ");
		}
		System.out.print("\n���ģ�");
		for(Message message : messages3_1) {
			System.out.print(message.getCiphertext()+" ");
		}	
	}
}
