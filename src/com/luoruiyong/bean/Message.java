package com.luoruiyong.bean;

public class Message {
	
	private String plaintext;
	private String ciphertext;
	
	public Message(String plainttext, String ciphertext) {
		this.plaintext = plainttext;
		this.ciphertext = ciphertext;
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
	
	
}
