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
		btnEncrypt = new JButton("加密");
		btnDecrypt = new JButton("解密");
		btnExhaustCrack = new JButton("穷举法破解");
		btnProbabilityCrack = new JButton("概率统计破解");
		btnAnalyze = new JButton("分析");
		add(btnEncrypt);
		add(btnDecrypt);
		add(btnExhaustCrack);
		add(btnProbabilityCrack);
		add(btnAnalyze);
	}
	
	// 设置密码算法对应的界面
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
