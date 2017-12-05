package com.luoruiyong.myview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.TrayIcon.MessageType;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.luoruiyong.OnMessageChangedListener;
import com.luoruiyong.bean.Message;
import com.luoruiyong.constant.Status;
import com.luoruiyong.myview.TipPanel.OnCaesarSettingChangedListener;
import com.luoruiyong.ui.MainFrameConstraints;

public class ProcessPanel extends JPanel{
	
	private JPanel plaintextPanel;
	private JPanel ciphertextPanel;
	private TipPanel tipPanel;
	private FunctionPanel functionPanel; 
	private JTextArea taPlaintext ;
	private JTextArea taCiphertext;
	private OnMessageChangedListener messageChangedListener;
	
	public ProcessPanel() {
		setBackground(Color.orange);
		initPlaintextPanel();
		initCiphertextPanel();
		tipPanel = new TipPanel();
		functionPanel = new FunctionPanel();
		
		setLayout(new GridBagLayout());
		add(plaintextPanel,new MainFrameConstraints(0, 0,1,1).setFill(GridBagConstraints.BOTH).setWeight(5, 1).setInsets(0,1,0,0));
		add(tipPanel,new MainFrameConstraints(1, 0,1,1).setFill(GridBagConstraints.BOTH).setWeight(2, 1).setInsets(0,1,0,0));
		add(ciphertextPanel,new MainFrameConstraints(2,0,1,1).setFill(GridBagConstraints.BOTH).setWeight(5, 1).setInsets(0,1,0,1));
		add(functionPanel,new MainFrameConstraints(0,1,3,1).setFill(GridBagConstraints.BOTH).setWeight(1, 0).setInsets(1,1,1,1));
		initActionListener();
	}
	
	public void initPlaintextPanel() {
		plaintextPanel = new JPanel();
		JLabel label = new JLabel("明文",JLabel.CENTER);
		taPlaintext = new JTextArea();
		taPlaintext.setLineWrap(true);  // 自动换行
		taPlaintext.setWrapStyleWord(true); // 断行不断字
		JScrollPane scrollPane = new JScrollPane(taPlaintext);
		plaintextPanel.setLayout(new BorderLayout());
		plaintextPanel.add(label,BorderLayout.NORTH);
		plaintextPanel.add(scrollPane,BorderLayout.CENTER);
	}
	
	public void initCiphertextPanel() {
		ciphertextPanel = new JPanel();
		taCiphertext = new JTextArea();
		taCiphertext.setLineWrap(true);  // 自动换行
		taCiphertext.setWrapStyleWord(true); // 断行不断字
		JScrollPane scrollPane = new JScrollPane(taCiphertext);
		JLabel labelCiphertext = new JLabel("密文",JLabel.CENTER);
		taCiphertext.setLineWrap(true);
		ciphertextPanel.setLayout(new BorderLayout());
		ciphertextPanel.add(labelCiphertext,BorderLayout.NORTH);
		ciphertextPanel.add(scrollPane,BorderLayout.CENTER);
	}
	
	public void initActionListener() {
		DocumentListener documentListener = new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				textChanged(e);
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				textChanged(e);
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				textChanged(e);
			}
			
			private void textChanged(DocumentEvent event) {
				if(messageChangedListener !=null) {
					Message message = new Message();
					if(event.getDocument().equals(taPlaintext.getDocument())) {
						message.setPlaintext(taPlaintext.getText());
						message.setStatus(Status.NO_ANAYLYSIS);
						messageChangedListener.onPlaintextChanged(message);
					}else {
						message.setCiphertext(taCiphertext.getText());
						message.setStatus(Status.NO_ANAYLYSIS);
						messageChangedListener.onCiphertextChanged(message);
					}
				}
			}
		};
		taPlaintext.getDocument().addDocumentListener(documentListener);
		taCiphertext.getDocument().addDocumentListener(documentListener);
	}
	
	public TipPanel getTipPanel() {
		return tipPanel;
	}
	
	public FunctionPanel getFunctionPanel() {
		return functionPanel;
	}
	
	public void showPlaintext(String plaintext) {
		taPlaintext.setText(plaintext);;
	}
	
	public void showCiphertext(String ciphertext) {
		taCiphertext.setText(ciphertext);;
	}
	
	public void updateInterface(Message message) {
		if(message == null) {
			return;
		}
		functionPanel.updateInterface(message);
		tipPanel.setArithmeticType(message);
		taPlaintext.setText(message.getPlaintext());
		taCiphertext.setText(message.getCiphertext());
		
		
	}
	
	public void setOnMessageChangedListener(OnMessageChangedListener listener) {
		this.messageChangedListener = listener;
	}
}
