package com.luoruiyong.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.JFrame;

import com.luoruiyong.myview.ArithmeticPanel;
import com.luoruiyong.myview.ArithmeticPanel.OnArithmeticChangedListener;
import com.luoruiyong.myview.MenuManager;
import com.luoruiyong.myview.ProcessPanel;
import com.luoruiyong.myview.ResultAnalyzePanel;


public class MainFrame extends JFrame implements OnArithmeticChangedListener{
	
	private MenuManager menuManager;
	private ArithmeticPanel arithmeticPanel;
	private ProcessPanel processPanel ;
	private ResultAnalyzePanel resultAnalyzePanel;

	public MainFrame(){
		super();
		initSetting();
		loadPanel();
	}
	
	public MainFrame(String title){
		super(title);
		initSetting();
		loadPanel();
	}
	
	public void initSetting() {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Dimension dimension = toolkit.getScreenSize();
		int width = dimension.width - dimension.width / 3;
		int height = dimension.height - dimension.height / 6;
		setBounds(width / 4,height / 10,width,height);
		setMinimumSize(new Dimension(600, 500));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
	}
	
	public void loadPanel() {
		menuManager = new MenuManager();
		arithmeticPanel = new ArithmeticPanel();
		processPanel = new ProcessPanel();
		resultAnalyzePanel = new ResultAnalyzePanel();
		setMenuBar(menuManager);
		setLayout(new GridBagLayout());
		add(arithmeticPanel, new MainFrameConstraints(0,0).
				setFill(MainFrameConstraints.BOTH).setWeight(1, 0)); 
		add(processPanel,new MainFrameConstraints(0,1).  
                setFill(MainFrameConstraints.BOTH).setWeight(1, 2));
		add(resultAnalyzePanel,new MainFrameConstraints(0,2).  
                setFill(MainFrameConstraints.BOTH).setWeight(1,3));
		initActionListener();
	}
	
	public void initActionListener() {
		arithmeticPanel.setOnArithmeticChangedListener(this);
	}

	// ÃÜÂëËã·¨¸Ä±ä
	@Override
	public void onArithmeticChangedListener(int arithmeticType) {
		processPanel.setArithmeticType(arithmeticType);
		resultAnalyzePanel.setArithmeticType(arithmeticType);
		
	}
	
	
	
}
