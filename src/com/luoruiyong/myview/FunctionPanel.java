package com.luoruiyong.myview;

import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import com.luoruiyong.OnMessageChangedListener;
import com.luoruiyong.bean.CaesarMessage;
import com.luoruiyong.bean.Message;
import com.luoruiyong.constant.Status;
import com.luoruiyong.password.Caesar;
import com.luoruiyong.password.Hill;
import com.luoruiyong.password.Playfair;
import com.luoruiyong.ui.MainFrame;

public class FunctionPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JButton btnEncrypt;
	private JButton btnDecrypt;
	private JButton btnExhaustCrack;
	private JButton btnProbabilityCrack;
	private JButton btnAnalyze;
	
	private boolean isReserveNotLetter = false;
	private boolean isIgnoreCase = false;
	private String analyzeType = Status.NO_ANAYLYSIS;
	private OnMessageChangedListener messageChangedListener;
	
	public FunctionPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
		btnEncrypt = new JButton("����");
		btnDecrypt = new JButton("����");
		btnExhaustCrack = new JButton("��ٷ��ƽ�");
		btnProbabilityCrack = new JButton("����ͳ���ƽ�");
		btnAnalyze = new JButton("����");
		btnAnalyze.setEnabled(false);
		add(btnEncrypt);
		add(btnDecrypt);
		add(btnExhaustCrack);
		add(btnProbabilityCrack);
		add(btnAnalyze);
		
		Font font = new Font("����", Font.BOLD, 15);
		btnAnalyze.setFont(font);
		btnDecrypt.setFont(font);
		btnEncrypt.setFont(font);
		btnExhaustCrack.setFont(font);
		btnProbabilityCrack.setFont(font);
		initActitonListener();
	}
	
	private void initActitonListener() {
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Message message = MainFrame.getMessage();
				Object source = e.getSource();
				if(source.equals(btnEncrypt)) {
					onEncrypt(message);
				}else if(source.equals(btnDecrypt)) {
					onDecrypt(message);
				}else if(source.equals(btnExhaustCrack)) {
					onExhaustCrack(message);
				}else if(source.equals(btnProbabilityCrack)) {
					onProbabilityCrack(message);
				}else if(source.equals(btnAnalyze)) {
					onAnalyze(message);
				}
			}
		};
		btnEncrypt.addActionListener(actionListener);
		btnDecrypt.addActionListener(actionListener);
		btnExhaustCrack.addActionListener(actionListener);
		btnProbabilityCrack.addActionListener(actionListener);
		btnAnalyze.addActionListener(actionListener);
	}
	
	public void onEncrypt(Message message) {
		String ciphertext = null;
		switch (message.getArithmeticType()) {
		case CAESAR:
			ciphertext = Caesar.encrypt(message.getPlaintext(), Integer.parseInt(message.getKey()),isReserveNotLetter,isIgnoreCase);
			break;
		case PLAYFAIR:
			ciphertext = Playfair.encrypt(message.getPlaintext(), message.getKey());
			break;
		case HILL:
			ciphertext = Hill.encrypt(message.getPlaintext(), message.getKey());
			break;
		}
		if(ciphertext == null) {
			if(messageChangedListener != null) {
				messageChangedListener.onOnlyStatusChanged(Status.ENCRYPT_FAILED);
			}
			btnAnalyze.setEnabled(false);
			JOptionPane.showMessageDialog(getParent(),"�Բ������Ĳ�����Ӣ����ĸ��Ϊ�գ�\n��ȷ�Ϻ��������롣","��ʾ",JOptionPane.WARNING_MESSAGE);
		}else if(messageChangedListener != null) {
			message.setCiphertext(ciphertext);
			message.setStatus(Status.ENCRYPT_SUCCEED);
			messageChangedListener.onCiphertextChanged(message);
			btnAnalyze.setEnabled(true);
			analyzeType = Status.ENCRYPT_ANALYSIS;
		}
	}
	
	public void onDecrypt(Message message) {
		String plaintext = null;
		switch (message.getArithmeticType()) {
		case CAESAR:
			plaintext = Caesar.decrypt(message.getCiphertext(), Integer.parseInt(message.getKey()),isReserveNotLetter,isIgnoreCase);
			break;
		case PLAYFAIR:
			plaintext = Playfair.decrypt(message.getCiphertext(), message.getKey());
			break;
		case HILL:
			plaintext = Hill.decrypt(message.getCiphertext(), message.getKey());
			break;
		}
		if(plaintext == null) {
			if(messageChangedListener != null) {
				messageChangedListener.onOnlyStatusChanged(Status.DECRYPT_FAILED);
			}
			btnAnalyze.setEnabled(false);
			JOptionPane.showMessageDialog(getParent(),"�Բ������Ĳ�����Ӣ����ĸ��Ϊ�գ�\n��ȷ�Ϻ��������롣","��ʾ",JOptionPane.WARNING_MESSAGE);
		}
		else if(messageChangedListener != null){
			message.setPlaintext(plaintext);
			message.setStatus(Status.DECRYPT_SUCCEED);
			messageChangedListener.onPlaintextChanged(message);
			btnAnalyze.setEnabled(true);
			analyzeType = Status.DECRYPT_ANALYSIS;
		}
		
	}
	
	public void onExhaustCrack(Message message) {
		ArrayList<Message> messages = Caesar.exhaustCrack(message.getCiphertext(),isReserveNotLetter,isIgnoreCase);
		if(messages != null) {
			StringBuilder builder = new StringBuilder();
			builder.append("Caesar������ٷ��ƽ�\n������Կ  ��Ӧ����\n");
			for(Message msg : messages) {
				builder.append(msg.getKey()+"\t"+msg.getPlaintext()+"\n");
			}
			builder.append("\n����ɵ�����������鿴");
			message.setPlaintext(builder.toString());
			message.setStatus(Status.EXHAUST_CRACK);
			if(messageChangedListener != null) {
				messageChangedListener.onPlaintextChanged(message);
			}
			btnAnalyze.setEnabled(true);
			analyzeType = Status.EXHAUST_CRACK_ANAYLYZE;
		}else {
			if(messageChangedListener != null) {
				messageChangedListener.onOnlyStatusChanged(Status.CRACK_FAILED);
			}
			JOptionPane.showMessageDialog(getParent(),"�Բ�������δ����Ӣ����ĸ��Ϊ�գ��޷������ƽ�\n��ȷ�Ϻ��������롣","��ʾ",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void onProbabilityCrack(Message message) {
		CaesarMessage caesarMessage = Caesar.autoCrack(message.getCiphertext(), isReserveNotLetter, isIgnoreCase);
		if(caesarMessage != null) {
			String text = "Caesar�������ͳ���ƽ�\n������Կ��"+caesarMessage.getKey()+"\n�õ������ģ�"+caesarMessage.getPlaintext()
							+"\n���Ŷȣ�"+caesarMessage.getSimilarity()+"%\n����ɵ�����������鿴";
			message.setPlaintext(text);
			message.setStatus(Status.PROBABILITY_CRACK);
			if(messageChangedListener != null) {
				messageChangedListener.onPlaintextChanged(message);
			}
			btnAnalyze.setEnabled(true);
			analyzeType = Status.PROBABILITY_CRACK_ANALYZE;
		}else {
			if(messageChangedListener != null) {
				messageChangedListener.onOnlyStatusChanged(Status.CRACK_FAILED);
			}
			JOptionPane.showMessageDialog(getParent(),"�Բ�������δ����Ӣ����ĸ��Ϊ�գ��޷������ƽ�\n��ȷ�Ϻ��������롣","��ʾ",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	public void onAnalyze(Message message) {
		if(messageChangedListener != null) {
			messageChangedListener.onOnlyStatusChanged(analyzeType);
		}
	}
	
	// ���������㷨��Ӧ�Ľ���
	public void updateInterface(Message message) {
		//System.out.println(message);
		switch (message.getArithmeticType()) {
		case CAESAR:
			btnExhaustCrack.setVisible(true);
			btnProbabilityCrack.setVisible(true);
			break;
		case PLAYFAIR:
			btnExhaustCrack.setVisible(false);
			btnProbabilityCrack.setVisible(false);
			break;
		case HILL:
			btnExhaustCrack.setVisible(false);
			btnProbabilityCrack.setVisible(false);
			break;
		}
		if(message.getStatus().equals(Status.NO_SECRET_KEY) 
				|| message.getStatus().equals(Status.SECRET_KEY_INVALID)) {
			// ��Կ��Ч
			btnEncrypt.setEnabled(false);
			btnDecrypt.setEnabled(false);
			btnAnalyze.setEnabled(false);
		}else {
			btnEncrypt.setEnabled(true);
			btnDecrypt.setEnabled(true);
		}
	}
	
	public void setAnaylzable(boolean tag) {
		btnAnalyze.setEnabled(tag);
	}
	
	public void setCaesarSetting(boolean isReserveNotLetter,boolean isIgnoreCase) {
		this.isReserveNotLetter = isReserveNotLetter;
		this.isIgnoreCase = isIgnoreCase;
	}
	
	public void setOnMessageChangedListener(OnMessageChangedListener listener){
		this.messageChangedListener = listener;
	}
}
