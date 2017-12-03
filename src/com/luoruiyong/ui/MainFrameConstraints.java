package com.luoruiyong.ui;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Robot;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.PortableServer.ServantActivator;

public class MainFrameConstraints extends GridBagConstraints {
	
	// ��ʼ�������Ͻ�λ��
	public MainFrameConstraints(int gridx,int gridy) {
		this.gridx = gridx;
		this.gridy = gridy;
	}
	
	// ��ʼ�����Ͻ�λ�ã��趨��ռ������������
	public MainFrameConstraints(int gridx,int gridy,int gridwidth,int gridheight) {
		this.gridx = gridx;
		this.gridy = gridy;
		this.gridwidth = gridwidth;
		this.gridheight = gridheight;
	}
	
	//���뷽ʽ  
	public MainFrameConstraints setAnchor(int anchor) {  
	    this.anchor = anchor;  
	    return this;  
	}  
	  
	//�Ƿ����켰���췽��  
	public MainFrameConstraints setFill(int fill) {  
	    this.fill = fill;  
	    return this;  
	}  
	  
	//x��y�����ϵ�����  
	public MainFrameConstraints setWeight(double weightx, double weighty) {  
	    this.weightx = weightx;  
	    this.weighty = weighty;  
	    return this;  
	}  
	  
	//�ⲿ���  
	public MainFrameConstraints setInsets(int distance) {  
	    this.insets = new Insets(distance, distance, distance, distance);  
	    return this;  
	}  
	  
	//�����  
	public MainFrameConstraints setInsets(int top, int left, int bottom, int right)  {  
	    this.insets = new Insets(top, left, bottom, right);  
	    return this;  
	}  
	  
	//�����  
	public MainFrameConstraints setIpad(int ipadx, int ipady) {  
	    this.ipadx = ipadx;  
	    this.ipady = ipady;  
	    return this;  
	}   

}
