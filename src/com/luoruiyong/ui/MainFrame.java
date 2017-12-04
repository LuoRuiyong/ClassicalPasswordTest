package com.luoruiyong.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import com.luoruiyong.myview.ArithmeticPanel;
import com.luoruiyong.myview.ArithmeticPanel.OnArithmeticChangedListener;
import com.luoruiyong.myview.FunctionPanel.OnStatusChangedListener;
import com.luoruiyong.myview.MenuManager;
import com.luoruiyong.myview.ProcessPanel;
import com.luoruiyong.myview.ResultAnalyzePanel;
import com.luoruiyong.myview.TipPanel.OnCaesarSettingChangedListener;
import com.luoruiyong.myview.TipPanel.OnSecretKeyChangedListener;


public class MainFrame extends JFrame implements OnArithmeticChangedListener,
				OnSecretKeyChangedListener,OnCaesarSettingChangedListener,
				OnStatusChangedListener{
	
	private MenuManager menuManager;
	private ArithmeticPanel arithmeticPanel;
	private ProcessPanel processPanel ;
	private ResultAnalyzePanel resultAnalyzePanel;
	private JLabel statusLabel;

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
		statusLabel = new JLabel("����");
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(new JLabel("״̬��"));
		panel.add(statusLabel);
		panel.setBackground(Color.white);;
		
		setMenuBar(menuManager);
		setLayout(new GridBagLayout());
		add(arithmeticPanel, new MainFrameConstraints(0,0).
				setFill(MainFrameConstraints.BOTH).setWeight(1, 0)); 
		add(processPanel,new MainFrameConstraints(0,1).  
                setFill(MainFrameConstraints.BOTH).setWeight(1, 2));
		add(resultAnalyzePanel,new MainFrameConstraints(0,2).  
                setFill(MainFrameConstraints.BOTH).setWeight(1,3));
		add(panel,new MainFrameConstraints(0,3).  
                setFill(MainFrameConstraints.BOTH).setWeight(1,0));
		initActionListener();
	}
	
	public void initActionListener() {
		arithmeticPanel.setOnArithmeticChangedListener(this);
		processPanel.getTipPanel().setOnSecretKeyChangedListener(this);
		processPanel.getTipPanel().setOnCaesarSettingChangedListener(this);
	}

	// �����㷨�ı�ص�
	@Override
	public void onArithmeticChangedListener(int arithmeticType) {
		processPanel.getTipPanel().setArithmeticType(arithmeticType);
		processPanel.getFunctionPanel().setArithmeticType(arithmeticType);
		resultAnalyzePanel.setArithmeticType(arithmeticType);
	}

	// �����������÷����ı�ص�
	@Override
	public void onCaserSettingChanged(boolean isReserveNotLetter, boolean isIgnoreCase) {
		processPanel.getFunctionPanel().setCaesarSetting(isReserveNotLetter, isIgnoreCase);
	}

	// �������뷢���ı�ص�
	@Override
	public void onCaesarSecretKeyChanged(int key) {
		processPanel.getFunctionPanel().setCaesarSecretKey(key);
	}
	
	// playfair���뷢���ı�ص�
	@Override
	public void onPlayfairSecretKeyChanged(char[][] keyMatrix) {
		processPanel.getFunctionPanel().setPlayfairSecretKey(keyMatrix);
	}

	// hill���뷢���ı�ص�
	@Override
	public void onHillSecretKeyChanged(int[][] matrixData) {
		processPanel.getFunctionPanel().setHillSecretKey(matrixData);
	}

	// ϵͳ״̬�����ı�ص�
	@Override
	public void onStatusChanged() {
		
	}
	
	
}
