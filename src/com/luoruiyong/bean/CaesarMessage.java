package com.luoruiyong.bean;

public class CaesarMessage {
	
	private String plaintext; 	// ����
	private String ciphertext;	// ����
	private int key;			// ��Կ
	private float probability;  // �Ǻ϶�
	
	public CaesarMessage(String plaintext, int key, String ciphertext) {
		this.plaintext = plaintext;
		this.key = key;
		this.ciphertext = ciphertext;
	}
	
	public CaesarMessage(String plaintext, int key, String ciphertext,float probability) {
		this.plaintext = plaintext;
		this.key = key;
		this.ciphertext = ciphertext;
		this.probability = probability;
	}
	
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
	public int getKey() {
		return key;
	}
	public void setKey(int key) {
		this.key = key;
	}

	public float getProbability() {
		return probability;
	}

	public void setProbability(float probability) {
		this.probability = probability;
	}
	
	
}
