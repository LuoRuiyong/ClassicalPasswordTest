package com.luoruiyong.myview;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.naming.InitialContext;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.border.EmptyBorder;

public class ArithmeticPanel extends JPanel {
	
	public static final int CAESAR = 1;
	public static final int PLAYFAIR = 2;
	public static final int HILL = 3;
	
	private JRadioButton caesarButton;
	private JRadioButton playfairButton;
	private JRadioButton hillButton;
	private ButtonGroup buttonGroup;
	private OnArithmeticChangedListener listener;
	
	public ArithmeticPanel() {
		caesarButton = new JRadioButton("Caeser",true);
		playfairButton = new JRadioButton("Playfair");
		hillButton = new JRadioButton("Hill");
		buttonGroup = new ButtonGroup();
		buttonGroup.add(caesarButton);
		buttonGroup.add(playfairButton);
		buttonGroup.add(hillButton);
		add(caesarButton);
		add(playfairButton);
		add(hillButton);
		setLayout(new FlowLayout(FlowLayout.CENTER,40,10));
		setBorder(BorderFactory.createLineBorder(Color.ORANGE,1));
		
		initActionListener();
	}
	
	
	public void initActionListener() {
		ActionListener actionListener = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(listener == null) {
					return;
				}
				if(e.getSource().equals(caesarButton)) {
					listener.onArithmeticChangedListener(CAESAR);
				}else if(e.getSource().equals(playfairButton)) {
					listener.onArithmeticChangedListener(PLAYFAIR);
				}else if(e.getSource().equals(hillButton)) {
					listener.onArithmeticChangedListener(HILL);
				}
			}
		};
		caesarButton.addActionListener(actionListener);
		playfairButton.addActionListener(actionListener);
		hillButton.addActionListener(actionListener);
	}
	
	public void setOnArithmeticChangedListener(OnArithmeticChangedListener listener) {
		this.listener = listener;
	}
	
	public interface OnArithmeticChangedListener{
		void onArithmeticChangedListener(int arithmeticType);
	}
}
