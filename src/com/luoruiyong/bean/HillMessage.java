package com.luoruiyong.bean;

public class HillMessage {
	
	private String plaintext; 	// ����
	private String ciphertext;	// ����
	private int key[][];		// ��Կ
	
	public String getPlaintext() {
		return plaintext;
	}
	public void setPlaintext(String plaintext) {
		this.plaintext = plaintext;
	}
	public String getCiphertext() {
		return ciphertext;
	}
	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}
	public int[][] getKey() {
		return key;
	}
	public void setKey(int[][] key) {
		this.key = key;
	}
	
	
}
