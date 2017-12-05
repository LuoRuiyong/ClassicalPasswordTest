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

import com.luoruiyong.OnMessageChangedListener;
import com.luoruiyong.bean.Message;
import com.luoruiyong.constant.ArithmeticType;
import com.luoruiyong.ui.MainFrame;
import com.luoruiyong.util.DocumentUtil;

public class ArithmeticPanel extends JPanel {

	private JRadioButton caesarButton;
	private JRadioButton playfairButton;
	private JRadioButton hillButton;
	private ButtonGroup buttonGroup;
	private OnMessageChangedListener listener;
	
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
				DocumentUtil.saveMessage(MainFrame.getMessage());
				if(listener == null) {
					return;
				}
				Message message = null;
				if(e.getSource().equals(caesarButton)) {
					 message = DocumentUtil.getMessage(ArithmeticType.CAESAR);
				}else if(e.getSource().equals(playfairButton)) {
					 message = DocumentUtil.getMessage(ArithmeticType.PLAYFAIR);
				}else if(e.getSource().equals(hillButton)) {
					 message = DocumentUtil.getMessage(ArithmeticType.HILL);
				}
				listener.onArithmeticTypeChanged(message);
			}
		};
		caesarButton.addActionListener(actionListener);
		playfairButton.addActionListener(actionListener);
		hillButton.addActionListener(actionListener);
	}
	
	public void setOnMessageChangedListener(OnMessageChangedListener listener) {
		this.listener = listener;
	}
}
