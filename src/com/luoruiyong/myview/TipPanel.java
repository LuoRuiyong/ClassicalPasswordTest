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

import com.luoruiyong.password.Caesar;
import com.luoruiyong.password.Hill;
import com.luoruiyong.password.Playfair;
import com.luoruiyong.ui.MainFrameConstraints;

public class TipPanel extends JPanel {
	
	private static final int PLAYFAIR_M = 5;
	private static final int HILL_M = 3;
	
	private JTextField tfCaesarKey;
	private JTextField tfPlayfairKey;
	private JTextField tfHillKey;
	private JCheckBox cbReserveNotLetter;
	private JCheckBox cbIgnoreCase;
	private JButton btnTestKey;
	private JPanel playfairMatrixPanel;
	private JLabel playfairMatrixLabels[];
	private JLabel playfairMatrixLabel;
	private JPanel hillMatrixPanel;
	private JLabel hillMatrixLabels[];
	private JLabel hillMatrixLabel;
	private int arithmeticType = ArithmeticPanel.CAESAR;
	private OnSecretKeyChangedListener keyChangedListener;
	private OnCaesarSettingChangedListener settingChangedListener;
	
	public TipPanel() {
		JLabel labelKey = new JLabel("密钥",JLabel.CENTER);
		tfCaesarKey = new JTextField(60);
		tfPlayfairKey = new JTextField(60);
		tfHillKey = new JTextField(60);
		btnTestKey = new JButton("测试密钥");
		tfCaesarKey.setMaximumSize(tfCaesarKey.getPreferredSize());
		cbReserveNotLetter = new JCheckBox("保留非字母字符");
		cbIgnoreCase = new JCheckBox("忽略字母大小写");
		tfPlayfairKey.setVisible(false);
		tfHillKey.setVisible(false);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.add(labelKey);
		horizontalBox.add(Box.createHorizontalStrut(10));
		horizontalBox.add(tfCaesarKey);
		horizontalBox.add(tfPlayfairKey);
		horizontalBox.add(tfHillKey);
		horizontalBox.setMaximumSize(horizontalBox.getPreferredSize());
		
		// playfair密钥矩阵
		playfairMatrixPanel = new JPanel();
		playfairMatrixPanel.setBorder(BorderFactory.createLineBorder(Color.black,1));
		playfairMatrixPanel.setLayout(new GridLayout(PLAYFAIR_M, PLAYFAIR_M));
		playfairMatrixLabel = new JLabel("密钥矩阵",JLabel.CENTER);
		playfairMatrixLabels = new JLabel[PLAYFAIR_M*PLAYFAIR_M];
		for(int i = 0;i < PLAYFAIR_M*PLAYFAIR_M;i++) {
			playfairMatrixLabels[i] = new JLabel("",JLabel.CENTER);
			playfairMatrixLabels[i].setBorder(BorderFactory.createLineBorder(Color.black,1));
			playfairMatrixLabels[i].setMinimumSize(new Dimension(25, 25));
			playfairMatrixPanel.add(playfairMatrixLabels[i]);
		}
		playfairMatrixPanel.setVisible(false);
		playfairMatrixLabel.setVisible(false);
		
		// hill密钥矩阵
		hillMatrixPanel = new JPanel();
		hillMatrixPanel.setBorder(BorderFactory.createLineBorder(Color.black,1));
		hillMatrixPanel.setLayout(new GridLayout(HILL_M, HILL_M));
		hillMatrixLabel = new JLabel("密钥矩阵",JLabel.CENTER);
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
				switch (arithmeticType) {
				case ArithmeticPanel.CAESAR:
					int key = checkCaesarSecretKey();
					if(keyChangedListener != null) {
						keyChangedListener.onCaesarSecretKeyChanged(key);
					}
					break;
				case ArithmeticPanel.PLAYFAIR:
					char[][] keyMatrix = checkPlayfairSecretKey();
					if(keyChangedListener != null) {
						keyChangedListener.onPlayfairSecretKeyChanged(keyMatrix);
					}
					break;
				case ArithmeticPanel.HILL:
					int[][] matrixData = checkHillSecretKey();
					if(keyChangedListener != null) {
						keyChangedListener.onHillSecretKeyChanged(matrixData);
					}
					break;
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
				if(keyChangedListener == null) {
					return;
				}
				switch (arithmeticType) {
				case ArithmeticPanel.CAESAR:
					keyChangedListener.onCaesarSecretKeyChanged(-1);
					break;
				case ArithmeticPanel.PLAYFAIR:
					keyChangedListener.onPlayfairSecretKeyChanged(null);
					break;
				case ArithmeticPanel.HILL:
					keyChangedListener.onHillSecretKeyChanged(null);
					break;
				}
			}
		};
		tfCaesarKey.getDocument().addDocumentListener(documentListener);
		tfPlayfairKey.getDocument().addDocumentListener(documentListener);
		tfHillKey.getDocument().addDocumentListener(documentListener);
	}
	
	public int checkCaesarSecretKey() {
		String testKey = tfCaesarKey.getText().trim();
		if(testKey.equals("")) {
			JOptionPane.showMessageDialog(getParent(), "对不起，密钥不能空。","提示",JOptionPane.WARNING_MESSAGE);
			return -1;
		}
		if(Caesar.isKeyAvailable(testKey)) {
			tfCaesarKey.setText(testKey);
			JOptionPane.showMessageDialog(getParent(), "密钥有效,您可以使用该密钥对明文加密或对密文解密。","提示",JOptionPane.PLAIN_MESSAGE);
			return Integer.parseInt(testKey);
		}else {
			JOptionPane.showMessageDialog(getParent(), 
					"对不起，Caesar密码算法要求密钥为0~25的整数，\n请确认后重新输入。","提示",JOptionPane.WARNING_MESSAGE);
			return -1;
		}
	}
	
	public char[][] checkPlayfairSecretKey() {
		String testKey = tfPlayfairKey.getText().trim();
		if(testKey.equals("")) {
			JOptionPane.showMessageDialog(getParent(), "对不起，密钥不能空。","提示",JOptionPane.WARNING_MESSAGE);
			return null;
		}
		char[][] keyMatrix = Playfair.isKeyAvailable(testKey);
		if(keyMatrix == null) {
			for(int i = 0;i < PLAYFAIR_M * PLAYFAIR_M;i++) {
				playfairMatrixLabels[i].setText("");
			}
			JOptionPane.showMessageDialog(getParent(), "对不起，你输入的密钥不含英文字母，请确认后重新输入。","提示",JOptionPane.WARNING_MESSAGE);
			return null;
		}else {
			for(int i = 0;i < PLAYFAIR_M * PLAYFAIR_M;i++) {
				playfairMatrixLabels[i].setText(keyMatrix[i/PLAYFAIR_M][i%PLAYFAIR_M] + "");
			}
			tfPlayfairKey.setText(testKey);
			JOptionPane.showMessageDialog(getParent(), "密钥有效,您可以使用该密钥对明文加密或对密文解密。","提示",JOptionPane.PLAIN_MESSAGE);						
			return keyMatrix;
		}
	}
	
	public int[][] checkHillSecretKey(){
		String testKey = tfHillKey.getText().trim();
		if(testKey.equals("")) {
			JOptionPane.showMessageDialog(getParent(), "对不起，密钥不能空。","提示",JOptionPane.WARNING_MESSAGE);
			return null;
		}
		int [][] matrixData = Hill.isKeyAvailable(testKey);
		if(matrixData == null) {
			for(int i = 0;i < HILL_M * HILL_M;i++) {
				hillMatrixLabels[i].setText("");
			}
			JOptionPane.showMessageDialog(getParent(), "对不起，你输入的密钥无效，可能存在的原因有：\n1.字母个数少于"+HILL_M*HILL_M+"个\n2.数字输入范围不合理，数字范围为0~25\n3.数字输入格式不对，数字之间使用一个空格隔开\n4.生产的密钥矩阵无对应的逆矩阵，\n请确认后重新输入。","提示",JOptionPane.WARNING_MESSAGE);
			return null;
		}else {
			for(int i = 0;i < HILL_M * HILL_M;i++) {
				hillMatrixLabels[i].setText(matrixData[i/HILL_M][i%HILL_M]+"");
			}
			tfHillKey.setText(testKey);
			JOptionPane.showMessageDialog(getParent(), "密钥有效,您可以使用该密钥对明文加密或对密文解密。","提示",JOptionPane.PLAIN_MESSAGE);
			return matrixData;
		}	
	}
	
	
	// 设置密码算法对应的界面
	public void setArithmeticType(int arithmeticType) {
		this.arithmeticType = arithmeticType;
		switch (arithmeticType) {
		case ArithmeticPanel.CAESAR:
			setCaesarVisible(true);
			setPlayfairVisible(false);
			setHillVisible(false);
			break;
		case ArithmeticPanel.PLAYFAIR:
			setCaesarVisible(false);
			setPlayfairVisible(true);
			setHillVisible(false);
			break;
		case ArithmeticPanel.HILL:
			setCaesarVisible(false);
			setPlayfairVisible(false);
			setHillVisible(true);
			break;
		}
	}
	
	private void setCaesarVisible(boolean tag) {
		tfCaesarKey.setVisible(tag);
		cbIgnoreCase.setVisible(tag);
		cbReserveNotLetter.setVisible(tag);
	}
	
	private void setPlayfairVisible(boolean tag) {
		tfPlayfairKey.setVisible(tag);
		playfairMatrixLabel.setVisible(tag);
		playfairMatrixPanel.setVisible(tag);
	}
	
	private void setHillVisible(boolean tag) {
		tfHillKey.setVisible(tag);
		hillMatrixLabel.setVisible(tag);
		hillMatrixPanel.setVisible(tag);
	}
	
	public void setOnCaesarSettingChangedListener(OnCaesarSettingChangedListener listener) {
		this.settingChangedListener = listener;
	}
	
	public void setOnSecretKeyChangedListener(OnSecretKeyChangedListener listener) {
		this.keyChangedListener = listener;
	}
	
	public interface OnSecretKeyChangedListener {
		void onCaesarSecretKeyChanged(int key);
		void onPlayfairSecretKeyChanged(char[][] keyMatrix);
		void onHillSecretKeyChanged(int[][] matrixData);
	}
	
	public interface OnCaesarSettingChangedListener{
		void onCaserSettingChanged(boolean isReserveNotLetter,boolean isIgnoreCase);
	}
}
