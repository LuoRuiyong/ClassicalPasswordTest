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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.Border;

import com.luoruiyong.ui.MainFrameConstraints;

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
	private int arithmeticType = ArithmeticPanel.CAESAR;
	
	public TipPanel() {
		JLabel labelKey = new JLabel("√‹‘ø",JLabel.CENTER);
		tfKey = new JTextField(60);
		btnTestKey = new JButton("≤‚ ‘√‹‘ø");
		tfKey.setMaximumSize(tfKey.getPreferredSize());
		cbReserveNotLetter = new JCheckBox("±£¡Ù∑«◊÷ƒ∏◊÷∑˚",true);
		cbIgnoreCase = new JCheckBox("∫ˆ¬‘◊÷ƒ∏¥Û–°–¥",false);
		
		Box horizontalBox = Box.createHorizontalBox();
		horizontalBox.add(labelKey);
		horizontalBox.add(Box.createHorizontalStrut(10));
		horizontalBox.add(tfKey);
		horizontalBox.setMaximumSize(horizontalBox.getPreferredSize());
		
		// playfair√‹‘øæÿ’Û
		playfairMatrixPanel = new JPanel();
		playfairMatrixPanel.setBorder(BorderFactory.createLineBorder(Color.black,1));
		playfairMatrixPanel.setLayout(new GridLayout(PLAYFAIR_M, PLAYFAIR_M));
		playfairMatrixLabel = new JLabel("√‹‘øæÿ’Û",JLabel.CENTER);
		playfairMatrixLabels = new JLabel[PLAYFAIR_M*PLAYFAIR_M];
		for(int i = 0;i < PLAYFAIR_M*PLAYFAIR_M;i++) {
			playfairMatrixLabels[i] = new JLabel("",JLabel.CENTER);
			playfairMatrixLabels[i].setBorder(BorderFactory.createLineBorder(Color.black,1));
			playfairMatrixLabels[i].setMinimumSize(new Dimension(25, 25));
			playfairMatrixPanel.add(playfairMatrixLabels[i]);
		}
		playfairMatrixPanel.setVisible(false);
		playfairMatrixLabel.setVisible(false);
		
		// hill√‹‘øæÿ’Û
		hillMatrixPanel = new JPanel();
		hillMatrixPanel.setBorder(BorderFactory.createLineBorder(Color.black,1));
		hillMatrixPanel.setLayout(new GridLayout(HILL_M, HILL_M));
		hillMatrixLabel = new JLabel("√‹‘øæÿ’Û",JLabel.CENTER);
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
				if(e.getSource().equals(btnTestKey)) {
					switch (arithmeticType) {
					case ArithmeticPanel.CAESAR:
						
						break;
					case ArithmeticPanel.PLAYFAIR:
						
						break;
					case ArithmeticPanel.HILL:
						
						break;
					}
				}
			}
		});
	}
	
	
	// …Ë÷√√‹¬ÎÀ„∑®∂‘”¶µƒΩÁ√Ê
	public void setArithmeticType(int arithmeticType) {
		this.arithmeticType = arithmeticType;
		switch (arithmeticType) {
		case ArithmeticPanel.CAESAR:
			cbReserveNotLetter.setVisible(true);
			cbIgnoreCase.setVisible(true);
			playfairMatrixLabel.setVisible(false);
			playfairMatrixPanel.setVisible(false);
			hillMatrixLabel.setVisible(false);
			hillMatrixPanel.setVisible(false);
			break;
		case ArithmeticPanel.PLAYFAIR:
			cbReserveNotLetter.setVisible(false);
			cbIgnoreCase.setVisible(false);
			playfairMatrixLabel.setVisible(true);
			playfairMatrixPanel.setVisible(true);
			hillMatrixLabel.setVisible(false);
			hillMatrixPanel.setVisible(false);
			break;
		case ArithmeticPanel.HILL:
			cbReserveNotLetter.setVisible(false);
			cbIgnoreCase.setVisible(false);
			playfairMatrixLabel.setVisible(false);
			playfairMatrixPanel.setVisible(false);
			hillMatrixLabel.setVisible(true);
			hillMatrixPanel.setVisible(true);
			break;
		}
	}
}
