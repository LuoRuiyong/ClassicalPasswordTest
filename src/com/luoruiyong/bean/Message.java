package com.luoruiyong.bean;

import com.luoruiyong.constant.ArithmeticType;

public class Message {

	private String plaintext;
	private String ciphertext;
	private String key;
	private String status;
	private ArithmeticType arithmeticType;
	
	public Message() {
		
	}
	public Message(String plainttext, String ciphertext) {
		this.plaintext = plainttext;
		this.ciphertext = ciphertext;
	}
	
	public Message(String plaintext, String ciphertext, String key, String status) {
		super();
		this.plaintext = plaintext;
		this.ciphertext = ciphertext;
		this.key = key;
		this.status = status;
	}
	
	public String getPlaintext() {
		return plaintext;
	}
	public void setPlainttext(String plainttext) {
		this.plaintext = plainttext;
	}
	public String getCiphertext() {
		return ciphertext;
	}
	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setPlaintext(String plaintext) {
		this.plaintext = plaintext;
	}
	public ArithmeticType getArithmeticType() {
		return arithmeticType;
	}
	public void setArithmeticType(ArithmeticType arithmeticType) {
		this.arithmeticType = arithmeticType;
	}
	@Override
	public String toString() {
		return "Message [plaintext=" + plaintext + ", ciphertext=" + ciphertext + ", key=" + key + ", status=" + status
				+ ", arithmeticType=" + arithmeticType + "]";
	}
	
	
}
