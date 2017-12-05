package com.luoruiyong.myview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.TextField;
import java.awt.TrayIcon.MessageType;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.luoruiyong.OnMessageChangedListener;
import com.luoruiyong.bean.Message;
import com.luoruiyong.constant.ArithmeticType;
import com.luoruiyong.constant.Status;
import com.luoruiyong.password.Caesar;
import com.luoruiyong.password.Hill;
import com.luoruiyong.password.Playfair;
import com.luoruiyong.ui.MainFrame;
import com.luoruiyong.ui.MainFrameConstraints;
import com.luoruiyong.util.MatrixUtil;

public class TipPanel extends JPanel {
	
	private static final int PLAYFAIR_M = 5;
	private static final int HILL_M = 3;
	
	private JTextField tfKey;
	private JCheckBox cbReserveNotLetter;
	private JCheckBox cbIgnoreCase;
	private JButton btnTestKey;
	
	private JPanel playfairMatrixPanel;
	private JLabel playfairMatrixLabels[];
	private JLabel playfairMatrixLabel;
	private JPanel hillMatrixPanel;
	private JLabel hillMatrixLabels[];
	private JLabel hillMatrixLabel;
	
	private ArithmeticType arithmeticType = ArithmeticType.CAESAR;
	private OnCaesarSettingChangedListener settingChangedListener;
	private OnMessageChangedListener messageChangedListener;
	
	public TipPanel() {
		JLabel labelKey = new JLabel("��Կ",JLabel.CENTER);
		tfKey = new JTextField(60);
		btnTestKey = new JButton("������Կ");
		tfKey.setMaximumSize(tfKey.getPreferredSize());
		cbReserveNotLetter = new JCheckBox("��������ĸ�ַ�");
		cbIgnoreCase = new JCheckBox("������ĸ��Сд");
	
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.add(labelKey);
		horizontalBox.add(Box.createHorizontalStrut(10));
		horizontalBox.add(tfKey);
		horizontalBox.setMaximumSize(horizontalBox.getPreferredSize());
		
		// playfair��Կ����
		playfairMatrixPanel = new JPanel();
		playfairMatrixPanel.setBorder(BorderFactory.createLineBorder(Color.black,1));
		playfairMatrixPanel.setLayout(new GridLayout(PLAYFAIR_M, PLAYFAIR_M));
		playfairMatrixLabel = new JLabel("��Կ����",JLabel.CENTER);
		playfairMatrixLabels = new JLabel[PLAYFAIR_M*PLAYFAIR_M];
		for(int i = 0;i < PLAYFAIR_M*PLAYFAIR_M;i++) {
			playfairMatrixLabels[i] = new JLabel("",JLabel.CENTER);
			playfairMatrixLabels[i].setBorder(BorderFactory.createLineBorder(Color.black,1));
			playfairMatrixLabels[i].setMinimumSize(new Dimension(25, 25));
			playfairMatrixPanel.add(playfairMatrixLabels[i]);
		}
		playfairMatrixPanel.setVisible(false);
		playfairMatrixLabel.setVisible(false);
		
		// hill��Կ����
		hillMatrixPanel = new JPanel();
		hillMatrixPanel.setBorder(BorderFactory.createLineBorder(Color.black,1));
		hillMatrixPanel.setLayout(new GridLayout(HILL_M, HILL_M));
		hillMatrixLabel = new JLabel("��Կ����",JLabel.CENTER);
		hillMatrixLabels = new JLabel[HILL_M*HILL_M];
		for(int i = 0;i < HILL_M*HILL_M;i++) {
			hillMatrixLabels[i] = new JLabel("",JLabel.CENTER);
			hillMatrixLabels[i].setBorder(BorderFactory.createLineBorder(Color.black,1));
			hillMatrixLabels[i].setMinimumSize(new Dimension(25, 25));
			hillMatrixPanel.add(hillMatrixLabels[i]);
		}
		hillMatrixLabel.setVisible(false);
		hillMatrixPanel.setVisible(false);

		setLayout(new GridBagLayout());
		add(horizontalBox,new MainFrameConstraints(0, 0).setFill(GridBagConstraints.BOTH).setWeight(100, 0).setInsets(0, 10, 0, 10));
		add(btnTestKey, new MainFrameConstraints(0, 1).setFill(GridBagConstraints.BOTH).setWeight(100, 0).setInsets(0, 10, 0, 10));
		add(cbReserveNotLetter, new MainFrameConstraints(0, 2));
		add(cbIgnoreCase, new MainFrameConstraints(0, 3));
		add(new JLabel(" "), new MainFrameConstraints(0, 4));
		add(playfairMatrixLabel, new MainFrameConstraints(0, 4));
		add(hillMatrixLabel, new MainFrameConstraints(0, 5));
		add(playfairMatrixPanel, new MainFrameConstraints(0, 5));
		add(hillMatrixPanel, new MainFrameConstraints(0, 6));
		setMaximumSize(getPreferredSize());
		
		initActionListener();
	}
	
	public void initActionListener() {
		btnTestKey.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean tag = false;
				String key = tfKey.getText().trim();
				switch (arithmeticType) {
				case CAESAR:
					tag = checkCaesarSecretKey(key);
					break;
				case PLAYFAIR:
					tag = checkPlayfairSecretKey(key);
					break;
				case HILL:
					tag = checkHillSecretKey(key);
					break;
				}
				if(messageChangedListener != null) {
					Message message = new Message();
					message.setKey(key);
					if(tag) {
						message.setStatus(Status.SECRET_KEY_AVAILABLE);
					}else {
						message.setStatus(Status.SECRET_KEY_INVALID);
					}
					messageChangedListener.onSecretKeyChanged(message);
				}
			}
		});
		ItemListener itemListener = new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if(settingChangedListener != null) {
					settingChangedListener.onCaserSettingChanged(cbReserveNotLetter.isSelected(), cbIgnoreCase.isSelected());
				}
			}
		};
		cbReserveNotLetter.addItemListener(itemListener);
		cbIgnoreCase.addItemListener(itemListener);
		DocumentListener documentListener = new DocumentListener() {
			@Override
			public void removeUpdate(DocumentEvent e) {
				textChanged();
			}
			@Override
			public void insertUpdate(DocumentEvent e) {
				textChanged();
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				textChanged();
			}
			
			private void textChanged() {
				if(messageChangedListener != null) {
					Message message = new Message();
					message.setKey(tfKey.getText());
					message.setStatus(Status.NO_SECRET_KEY);
					messageChangedListener.onSecretKeyChanged(message);
				}
				if(arithmeticType == ArithmeticType.PLAYFAIR) {
					for(int i = 0;i < PLAYFAIR_M * PLAYFAIR_M;i++) {
						playfairMatrixLabels[i].setText("");
					}
				}else if(arithmeticType == ArithmeticType.HILL) {
					for(int i = 0;i < HILL_M * HILL_M;i++) {
						hillMatrixLabels[i].setText("");
					}
				}
			}
		};
		tfKey.getDocument().addDocumentListener(documentListener);
	}
	
	public boolean checkCaesarSecretKey(String testKey) {
		if(testKey.equals("")) {
			JOptionPane.showMessageDialog(getParent(), "�Բ�����Կ���ܿա�","��ʾ",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if(Caesar.isKeyAvailable(testKey)) {
			return true;
		}else {
			JOptionPane.showMessageDialog(getParent(), 
					"�Բ���Caesar�����㷨Ҫ����ԿΪ0~25��������\n��ȷ�Ϻ��������롣","��ʾ",JOptionPane.WARNING_MESSAGE);
			return false;
		}
	}
	
	public boolean checkPlayfairSecretKey(String testKey) {
		if(testKey.equals("")) {
			JOptionPane.showMessageDialog(getParent(), "�Բ�����Կ���ܿա�","��ʾ",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		char[][] keyMatrix = Playfair.isKeyAvailable(testKey);
		if(keyMatrix == null) {
			JOptionPane.showMessageDialog(getParent(), "�Բ������������Կ����Ӣ����ĸ����ȷ�Ϻ��������롣","��ʾ",JOptionPane.WARNING_MESSAGE);
			return false;
		}else {
			for(int i = 0;i < PLAYFAIR_M * PLAYFAIR_M;i++) {
				playfairMatrixLabels[i].setText(keyMatrix[i/PLAYFAIR_M][i%PLAYFAIR_M] + "");
			}
			return true;
		}
	}
	
	public boolean checkHillSecretKey(String testKey){
		if(testKey.equals("")) {
			JOptionPane.showMessageDialog(getParent(), "�Բ�����Կ���ܿա�","��ʾ",JOptionPane.WARNING_MESSAGE);
			return false;
		}
		int [][] matrixData = Hill.isKeyAvailable(testKey);
		if(matrixData == null) {
			JOptionPane.showMessageDialog(getParent(), "�Բ������������Կ��Ч�����ܴ��ڵ�ԭ���У�\n1.��ĸ��������"+HILL_M*HILL_M+"��\n2.�������뷶Χ���������ַ�ΧΪ0~25\n3.���������ʽ���ԣ�����֮��ʹ��һ���ո����\n4.��������Կ�����޶�Ӧ�������\n��ȷ�Ϻ��������롣","��ʾ",JOptionPane.WARNING_MESSAGE);
			return false;
		}else {
			for(int i = 0;i < HILL_M * HILL_M;i++) {
				hillMatrixLabels[i].setText(matrixData[i/HILL_M][i%HILL_M]+"");
			}
			return true;
		}	
	}
	
	
	// ���������㷨��Ӧ�Ľ���
	public void setArithmeticType(Message message) {
		tfKey.setText(message.getKey());
		arithmeticType = message.getArithmeticType();
		switch (arithmeticType) {
		case CAESAR:
			setCaesarVisible(true);
			setPlayfairVisible(false);
			setHillVisible(false);
			break;
		case PLAYFAIR:
			setCaesarVisible(false);
			setPlayfairVisible(true);
			setHillVisible(false);
			break;
		case HILL:
			setCaesarVisible(false);
			setPlayfairVisible(false);
			setHillVisible(true);
			break;
		}
	}
	
	private void setCaesarVisible(boolean tag) {
		cbIgnoreCase.setVisible(tag);
		cbReserveNotLetter.setVisible(tag);
	}
	
	private void setPlayfairVisible(boolean tag) {
		playfairMatrixLabel.setVisible(tag);
		playfairMatrixPanel.setVisible(tag);
	}
	
	private void setHillVisible(boolean tag) {
		hillMatrixLabel.setVisible(tag);
		hillMatrixPanel.setVisible(tag);
	}
	
	public void setOnCaesarSettingChangedListener(OnCaesarSettingChangedListener listener) {
		this.settingChangedListener = listener;
	}
	
	public void setOnMessageChangedListener(OnMessageChangedListener listener) {
		this.messageChangedListener = listener;
	}
	
	public interface OnCaesarSettingChangedListener{
		void onCaserSettingChanged(boolean isReserveNotLetter,boolean isIgnoreCase);
	}
}
