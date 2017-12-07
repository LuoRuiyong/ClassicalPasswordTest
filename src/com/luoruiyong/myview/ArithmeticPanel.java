package com.luoruiyong.myview;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JPanel;
import javax.swing.JRadioButton;

import com.luoruiyong.OnMessageChangedListener;
import com.luoruiyong.bean.Message;
import com.luoruiyong.constant.ArithmeticType;
import com.luoruiyong.ui.MainFrame;
import com.luoruiyong.util.DocumentUtil;

public class ArithmeticPanel extends JPanel {

	private static final long serialVersionUID = 1L;
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
		setBorder(BorderFactory.createLineBorder(Color.ORANGE,2));
		
		Font font = new Font("ËÎÌו", Font.BOLD, 16);
		caesarButton.setFont(font);
		playfairButton.setFont(font);
		hillButton.setFont(font);
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
