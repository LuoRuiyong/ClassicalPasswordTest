package com.luoruiyong.myview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.luoruiyong.OnMessageChangedListener;
import com.luoruiyong.bean.Message;
import com.luoruiyong.constant.Status;
import com.luoruiyong.ui.MainFrameConstraints;

public class ProcessPanel extends JPanel{

	private static final long serialVersionUID = 1L;
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
		add(plaintextPanel,new MainFrameConstraints(0, 0,1,1).setFill(GridBagConstraints.BOTH).setWeight(5, 1).setInsets(0,2,0,0));
		add(tipPanel,new MainFrameConstraints(1, 0,1,1).setFill(GridBagConstraints.BOTH).setWeight(2, 1).setInsets(0,2,0,0));
		add(ciphertextPanel,new MainFrameConstraints(2,0,1,1).setFill(GridBagConstraints.BOTH).setWeight(5, 1).setInsets(0,2,0,2));
		add(functionPanel,new MainFrameConstraints(0,1,3,1).setFill(GridBagConstraints.BOTH).setWeight(1, 0).setInsets(2,2,0,2));
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
		taPlaintext.setFont(new Font("宋体", Font.PLAIN, 15));
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setPreferredSize(new Dimension(50, 30));
	}
	
	public void initCiphertextPanel() {
		ciphertextPanel = new JPanel();
		taCiphertext = new JTextArea();
		taCiphertext.setLineWrap(true);  // 自动换行
		taCiphertext.setWrapStyleWord(true); // 断行不断字
		JScrollPane scrollPane = new JScrollPane(taCiphertext);
		JLabel label = new JLabel("密文",JLabel.CENTER);
		taCiphertext.setLineWrap(true);
		ciphertextPanel.setLayout(new BorderLayout());
		ciphertextPanel.add(label,BorderLayout.NORTH);
		ciphertextPanel.add(scrollPane,BorderLayout.CENTER);
		taCiphertext.setFont(new Font("宋体", Font.PLAIN, 15));
		label.setFont(new Font("宋体", Font.BOLD, 15));
		label.setPreferredSize(new Dimension(50, 30));
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
