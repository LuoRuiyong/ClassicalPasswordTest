package com.luoruiyong.myview;

import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

public class FunctionPanel extends JPanel {
	private JButton btnEncrypt;
	private JButton btnDecrypt;
	private JButton btnExhaustCrack;
	private JButton btnProbabilityCrack;
	private JButton btnAnalyze;
	
	public FunctionPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER,20,10));
		btnEncrypt = new JButton("����");
		btnDecrypt = new JButton("����");
		btnExhaustCrack = new JButton("��ٷ��ƽ�");
		btnProbabilityCrack = new JButton("����ͳ���ƽ�");
		btnAnalyze = new JButton("����");
		add(btnEncrypt);
		add(btnDecrypt);
		add(btnExhaustCrack);
		add(btnProbabilityCrack);
		add(btnAnalyze);
	}
	
	// ���������㷨��Ӧ�Ľ���
	public void setArithmeticType(int arithmeticType) {
		switch (arithmeticType) {
		case ArithmeticPanel.CAESAR:
			btnExhaustCrack.setVisible(true);
			btnProbabilityCrack.setVisible(true);
			break;
		case ArithmeticPanel.PLAYFAIR:
			btnExhaustCrack.setVisible(false);
			btnProbabilityCrack.setVisible(false);
			break;
		case ArithmeticPanel.HILL:
			btnExhaustCrack.setVisible(false);
			btnProbabilityCrack.setVisible(false);
			break;
		}
	}
}
