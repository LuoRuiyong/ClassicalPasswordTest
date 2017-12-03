package com.luoruiyong.ui;

import java.awt.GridBagConstraints;
import java.awt.Insets;
import java.awt.Robot;

import org.omg.CORBA.PUBLIC_MEMBER;
import org.omg.PortableServer.ServantActivator;

public class MainFrameConstraints extends GridBagConstraints {
	
	// 初始化话左上角位置
	public MainFrameConstraints(int gridx,int gridy) {
		this.gridx = gridx;
		this.gridy = gridy;
	}
	
	// 初始化左上角位置，设定所占的行数和列数
	public MainFrameConstraints(int gridx,int gridy,int gridwidth,int gridheight) {
		this.gridx = gridx;
		this.gridy = gridy;
		this.gridwidth = gridwidth;
		this.gridheight = gridheight;
	}
	
	//对齐方式  
	public MainFrameConstraints setAnchor(int anchor) {  
	    this.anchor = anchor;  
	    return this;  
	}  
	  
	//是否拉伸及拉伸方向  
	public MainFrameConstraints setFill(int fill) {  
	    this.fill = fill;  
	    return this;  
	}  
	  
	//x和y方向上的增量  
	public MainFrameConstraints setWeight(double weightx, double weighty) {  
	    this.weightx = weightx;  
	    this.weighty = weighty;  
	    return this;  
	}  
	  
	//外部填充  
	public MainFrameConstraints setInsets(int distance) {  
	    this.insets = new Insets(distance, distance, distance, distance);  
	    return this;  
	}  
	  
	//外填充  
	public MainFrameConstraints setInsets(int top, int left, int bottom, int right)  {  
	    this.insets = new Insets(top, left, bottom, right);  
	    return this;  
	}  
	  
	//内填充  
	public MainFrameConstraints setIpad(int ipadx, int ipady) {  
	    this.ipadx = ipadx;  
	    this.ipady = ipady;  
	    return this;  
	}   

}
