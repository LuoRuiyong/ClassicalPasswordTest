package com.luoruiyong.myview;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ClientInfoStatus;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.luoruiyong.constant.Status;
import com.luoruiyong.password.Caesar;
import com.luoruiyong.password.Hill;
import com.luoruiyong.password.Playfair;

public class FunctionPanel extends JPanel {
	private JButton btnEncrypt;
	private JButton btnDecrypt;
	private JButton btnExhaustCrack;
	private JButton btnProbabilityCrack;
	private JButton btnAnalyze;
	
	private int key = -1;
	private char[][] keyMatrix = null;
	private int[][] matrixData = null;
	private boolean isReserveNotLetter = false;
	private boolean isIgnoreCase = false;
	private String plaintext = null;
	private String ciphertext = null;
	private boolean isCaeserEncypted = false;
	private boolean isPlayfairEncypted = false;
	private boolean isHillEncypted = false;
	private int arthmeticType = ArithmeticPanel.CAESAR;
	private String status = Status.READY;
	private OnDisposeTextChangedListener disposeTextChangedListener;
	private OnStatusChangedListener statusChangedListener;
	
	public FunctionPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
		btnEncrypt = new JButton("加密");
		btnDecrypt = new JButton("解密");
		btnExhaustCrack = new JButton("穷举法破解");
		btnProbabilityCrack = new JButton("概率统计破解");
		btnAnalyze = new JButton("分析");
		btnEncrypt.setEnabled(false);
		btnDecrypt.setEnabled(false);
		btnExhaustCrack.setEnabled(false);
		btnProbabilityCrack.setEnabled(false);
		btnAnalyze.setEnabled(false);
		add(btnEncrypt);
		add(btnDecrypt);
		add(btnExhaustCrack);
		add(btnProbabilityCrack);
		add(btnAnalyze);
		initActitonListener();
	}
	
	private void initActitonListener() {
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Object source = e.getSource();
				if(source.equals(btnEncrypt)) {
					onEncrypt();
				}else if(source.equals(btnDecrypt)) {
					onDecrypt();
				}else if(source.equals(btnExhaustCrack)) {
					onExhaustCrack();
				}else if(source.equals(btnProbabilityCrack)) {
					onProbabilityCrack();
				}else if(source.equals(btnAnalyze)) {
					onAnalyze();
				}
			}
		};
		btnEncrypt.addActionListener(actionListener);
		btnDecrypt.addActionListener(actionListener);
		btnExhaustCrack.addActionListener(actionListener);
		btnProbabilityCrack.addActionListener(actionListener);
		btnAnalyze.addActionListener(actionListener);
	}
	
	public void onEncrypt() {
		String ciphertext;
		switch (arthmeticType) {
		case ArithmeticPanel.CAESAR:
			ciphertext = Caesar.encrypt(plaintext, key,isReserveNotLetter,isIgnoreCase);
			if(ciphertext == null) {
				JOptionPane.showMessageDialog(getParent(),"对不起，明文不包含英文字母或为空。","提示",JOptionPane.WARNING_MESSAGE);
			}else {
				this.ciphertext = ciphertext;
				if(disposeTextChangedListener != null) {
					disposeTextChangedListener.onCipherextChanged(ciphertext);
				}
				isCaeserEncypted = true;
				btnAnalyze.setEnabled(isCaeserEncypted);
				JOptionPane.showMessageDialog(getParent(), "使用Caesar密码算法加密成功。","提示",JOptionPane.PLAIN_MESSAGE);
			}
			break;
		case ArithmeticPanel.PLAYFAIR:
			ciphertext = Playfair.encrypt(plaintext, keyMatrix);
			if(ciphertext == null) {
				JOptionPane.showMessageDialog(getParent(),"对不起，明文不包含英文字母或为空，\n请确认后重新输入。","提示",JOptionPane.WARNING_MESSAGE);
			}else {
				this.ciphertext = ciphertext;
				if(disposeTextChangedListener != null) {
					disposeTextChangedListener.onCipherextChanged(ciphertext);
				}
				isPlayfairEncypted = true;
				btnAnalyze.setEnabled(isPlayfairEncypted);
				JOptionPane.showMessageDialog(getParent(), "使用Playfair密码算法加密成功。","提示",JOptionPane.PLAIN_MESSAGE);
			}
			break;
		case ArithmeticPanel.HILL:
			ciphertext = Hill.encrypt(plaintext, matrixData);
			if(ciphertext == null) {
				JOptionPane.showMessageDialog(getParent(),"对不起，明文不包含英文字母或为空，\n请确认后重新输入。","提示",JOptionPane.WARNING_MESSAGE);
			}else {
				this.ciphertext = ciphertext;
				if(disposeTextChangedListener != null) {
					disposeTextChangedListener.onCipherextChanged(ciphertext);
				}
				isHillEncypted = true;
				btnAnalyze.setEnabled(isHillEncypted);
				JOptionPane.showMessageDialog(getParent(), "使用Hill3密码算法加密成功。","提示",JOptionPane.PLAIN_MESSAGE);
			}
			break;
		}
	}
	
	public void onDecrypt() {
		String plaintext;
		switch (arthmeticType) {
		case ArithmeticPanel.CAESAR:
			plaintext = Caesar.decrypt(ciphertext, key,isReserveNotLetter,isIgnoreCase);
			if(plaintext == null) {
				JOptionPane.showMessageDialog(getParent(),"对不起，密文不包含英文字母或为空。","提示",JOptionPane.WARNING_MESSAGE);
			}else {
				this.plaintext = plaintext;
				if(disposeTextChangedListener != null) {
					disposeTextChangedListener.onPlaintextChanged(plaintext);
				}
				JOptionPane.showMessageDialog(getParent(), "使用Caesar密码算法解密成功。","提示",JOptionPane.PLAIN_MESSAGE);
			}
			break;
		case ArithmeticPanel.PLAYFAIR:
			plaintext = Playfair.decrypt(ciphertext, keyMatrix);
			if(plaintext == null) {
				JOptionPane.showMessageDialog(getParent(),"对不起，密文不包含英文字母或为空，\n请确认后重新输入。","提示",JOptionPane.WARNING_MESSAGE);
			}else {
				this.plaintext = plaintext;
				if(disposeTextChangedListener != null) {
					disposeTextChangedListener.onPlaintextChanged(plaintext);;
				}
				JOptionPane.showMessageDialog(getParent(), "使用Playfair密码算法解密成功。","提示",JOptionPane.PLAIN_MESSAGE);
			}
			break;
		case ArithmeticPanel.HILL:
			plaintext = Hill.decrypt(ciphertext, matrixData);
			if(plaintext == null) {
				JOptionPane.showMessageDialog(getParent(),"对不起，密文不包含英文字母或为空，\n请确认后重新输入。","提示",JOptionPane.WARNING_MESSAGE);
			}else {
				this.plaintext = plaintext;
				if(disposeTextChangedListener != null) {
					disposeTextChangedListener.onPlaintextChanged(plaintext);;
				}
				JOptionPane.showMessageDialog(getParent(), "使用Hill3密码算法解密成功。","提示",JOptionPane.PLAIN_MESSAGE);
			}
			break;
		}
	}
	
	public void onExhaustCrack() {
		
	}
	
	public void onProbabilityCrack() {
		
	}
	
	public void onAnalyze() {
		switch (arthmeticType) {
		case ArithmeticPanel.CAESAR:
			
			break;
		case ArithmeticPanel.PLAYFAIR:
			
			break;
		case ArithmeticPanel.HILL:
	
			break;
		}
	}
	
	private void setFunctionEnabled(boolean tag) {
		btnEncrypt.setEnabled(tag);
		btnDecrypt.setEnabled(tag);
		btnExhaustCrack.setEnabled(tag);
		btnProbabilityCrack.setEnabled(tag);
		if(arthmeticType != ArithmeticPanel.CAESAR) {
			btnExhaustCrack.setVisible(false);
			btnProbabilityCrack.setVisible(false);
		}else {
			btnExhaustCrack.setVisible(true);
			btnProbabilityCrack.setVisible(true);
		}
	}
	
	// 设置密码算法对应的界面
	public void setArithmeticType(int arithmeticType) {
		this.arthmeticType = arithmeticType;
		switch (arithmeticType) {
		case ArithmeticPanel.CAESAR:
			btnExhaustCrack.setVisible(true);
			btnProbabilityCrack.setVisible(true);
			setFunctionEnabled(key == -1 ? false : true);
			btnAnalyze.setEnabled(isCaeserEncypted);
			break;
		case ArithmeticPanel.PLAYFAIR:
			btnExhaustCrack.setVisible(false);
			btnProbabilityCrack.setVisible(false);
			setFunctionEnabled(keyMatrix == null ? false : true);
			btnAnalyze.setEnabled(isPlayfairEncypted);
			break;
		case ArithmeticPanel.HILL:
			btnExhaustCrack.setVisible(false);
			btnProbabilityCrack.setVisible(false);
			setFunctionEnabled(matrixData == null ? false : true);
			btnAnalyze.setEnabled(isHillEncypted);
			break;
		}
	}
	
	public void setCaesarSecretKey(int key) {
		this.key = key;
		isCaeserEncypted = false;
		btnAnalyze.setEnabled(isCaeserEncypted);
		setFunctionEnabled(key == -1 ? false : true);
	}
	
	public void setPlayfairSecretKey(char[][] keyMatrix) {
		this.keyMatrix = keyMatrix;
		isPlayfairEncypted = false;
		btnAnalyze.setEnabled(isPlayfairEncypted);
		setFunctionEnabled(keyMatrix == null ? false : true);
	}

	public void setHillSecretKey(int[][] matrixData) {
		this.matrixData = matrixData;
		isHillEncypted = false;
		btnAnalyze.setEnabled(isHillEncypted);
		setFunctionEnabled(matrixData == null ? false : true);
	}
	
	public void setCaesarSetting(boolean isReserveNotLetter,boolean isIgnoreCase) {
		this.isReserveNotLetter = isReserveNotLetter;
		this.isIgnoreCase = isIgnoreCase;
	}
	
	public void setPlaintext(String plaintext) {
		this.plaintext = plaintext;
	}
	
	public void setCiphertext(String ciphertext) {
		this.ciphertext = ciphertext;
		btnAnalyze.setEnabled(false);
		isCaeserEncypted = false;
		isPlayfairEncypted = false;
	}
	
	public void setOnStatusChangedListener(OnStatusChangedListener listener) {
		this.statusChangedListener = listener;
	}
	
	public void setOnDisposeTextChangedListener(OnDisposeTextChangedListener listener) {
		this.disposeTextChangedListener = listener;
	}
	
	public interface OnDisposeTextChangedListener {
		void onPlaintextChanged(String plaintext);
		void onCipherextChanged(String ciphertext);
	}
	
	public interface OnStatusChangedListener{
		void onStatusChanged();
	}
}
