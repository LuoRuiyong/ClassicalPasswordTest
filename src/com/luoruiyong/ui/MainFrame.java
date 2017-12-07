package com.luoruiyong.ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.luoruiyong.bean.Message;
import com.luoruiyong.constant.ArithmeticType;
import com.luoruiyong.constant.Status;
import com.luoruiyong.myview.ArithmeticPanel;
import com.luoruiyong.myview.MenuManager;
import com.luoruiyong.myview.ProcessPanel;
import com.luoruiyong.myview.ResultAnalyzePanel;
import com.luoruiyong.myview.MenuManager.OnClearRecordListener;
import com.luoruiyong.myview.TipPanel.OnCaesarSettingChangedListener;
import com.luoruiyong.util.DocumentUtil;
import com.luoruiyong.OnMessageChangedListener;

public class MainFrame extends JFrame implements OnCaesarSettingChangedListener,
				OnMessageChangedListener,OnClearRecordListener{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Message message = new Message();
	
	private MenuManager menuManager;
	private ArithmeticPanel arithmeticPanel;
	private ProcessPanel processPanel ;
	private ResultAnalyzePanel resultAnalyzePanel;
	private JLabel statusLabel;

	public MainFrame(){
		super();
		initSetting();
		loadPanel();
		initData();
		initActionListener();
	}
	
	public MainFrame(String title){
		super(title);
		initSetting();
		loadPanel();
		initData();
		initActionListener();
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
		statusLabel = new JLabel();
		JPanel panel = new JPanel();
		panel.setLayout(new FlowLayout(FlowLayout.LEFT));
		panel.add(new JLabel("״̬��"));
		panel.add(statusLabel);
		panel.setBorder(BorderFactory.createLineBorder(Color.orange,1));
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
		
	}
	
	public void initActionListener() {
		arithmeticPanel.setOnMessageChangedListener(this); // �㷨�ı�
		menuManager.setOnClearRecordListener(this);
		menuManager.setOnMessageChangedListener(this);
		processPanel.setOnMessageChangedListener(this);   // ����������Ϊ���ĸı�
		processPanel.getTipPanel().setOnCaesarSettingChangedListener(this); // �����ӽ��ܹ���ı�
		processPanel.getTipPanel().setOnMessageChangedListener(this);	// ��Կ�ı�
		processPanel.getFunctionPanel().setOnMessageChangedListener(this); // �ӽ��ܷ����Ȳ���
	}
	
	public void initData() {
		DocumentUtil.createXmlSettingFile();
		message = DocumentUtil.getMessage(ArithmeticType.CAESAR);
		message.setStatus(Status.NO_SECRET_KEY);
		statusLabel.setText(message.getStatus());
		processPanel.updateInterface(message);
		
	}
	
	// �����ӽ��ܶ���������÷����ı�ص�
	@Override
	public void onCaserSettingChanged(boolean isReserveNotLetter, boolean isIgnoreCase) {
		processPanel.getFunctionPanel().setCaesarSetting(isReserveNotLetter, isIgnoreCase);
	}


	// ��Կ״̬�����ı�ص�������ʾ��崥��
	@Override
	public void onSecretKeyChanged(Message msg) {
		// NO_SECRET_KEY��SECRET_KEY_INVALID��SECRET_KEY_AVAILABLE
		statusLabel.setText(msg.getStatus());
		message.setStatus(msg.getStatus());
		message.setKey(msg.getKey());
		processPanel.getFunctionPanel().updateInterface(message);
	}

	// �����㷨�����ı�ʱ�ص�
	@Override
	public void onArithmeticTypeChanged(Message msg) {
		message = msg;
		processPanel.updateInterface(message);
		resultAnalyzePanel.setArithmeticType(message.getArithmeticType());
	}
	
	@Override
	public void onOnlyStatusChanged(String status) {
		// ENCRYPT_FAILED��DECRYPT_FAILED��ENCRYPT_ANALYSIS��
		// DECRYPT_ANALYSIS��CRACK_ANALYSIS��NO_ANAYLYSIS
		message.setStatus(status);
		statusLabel.setText(status);
		if(status.equals(Status.ENCRYPT_FAILED) 
				|| status.equals(Status.DECRYPT_FAILED)
						|| status.equals(Status.CRACK_FAILED)) {
			processPanel.getFunctionPanel().setAnaylzable(false);
		}else if(status.equals(Status.ENCRYPT_ANALYSIS) 
				||status.equals(Status.DECRYPT_ANALYSIS)
					||status.equals(Status.EXHAUST_CRACK_ANAYLYZE)
						||status.equals(Status.PROBABILITY_CRACK_ANALYZE)){
			resultAnalyzePanel.showAnalyzeResult(message);
		}
	}
	
	// �������ݷ����ı�ص����������������������������������ݴ���
	@Override
	public void onPlaintextChanged(Message msg) {
		// DECRYPT_SUCCEED��NO_SECRET_KEY��EXHAUST_CRACK��PROBABILITY_CRACK
		if(msg.getStatus().equals(Status.DECRYPT_SUCCEED) 
				|| msg.getStatus().equals(Status.EXHAUST_CRACK)
					|| msg.getStatus().equals(Status.PROBABILITY_CRACK)
						|| msg.getStatus().equals(Status.OPEN_FILE_SUCCEED)){
			// ���ܳɹ�
			statusLabel.setText(msg.getStatus());
			processPanel.showPlaintext(msg.getPlaintext());
			if(msg.getStatus().equals(Status.OPEN_FILE_SUCCEED)) {
				processPanel.getFunctionPanel().setAnaylzable(false);
			}
		}else if(msg.getStatus().equals(Status.NO_ANAYLYSIS)) {
			processPanel.getFunctionPanel().setAnaylzable(false);
		}
		message.setStatus(msg.getStatus());
		message.setPlaintext(msg.getPlaintext());
	}

	// �������ݷ�����Ϊ�ı�ص����������������������������������ݴ���
	@Override
	public void onCiphertextChanged(Message msg) {
		
		if(msg.getStatus().equals(Status.ENCRYPT_SUCCEED)
				|| msg.getStatus().equals(Status.OPEN_FILE_SUCCEED)){
			// ���ܳɹ�
			statusLabel.setText(msg.getStatus());
			processPanel.showCiphertext(msg.getCiphertext());
			if(msg.getStatus().equals(Status.OPEN_FILE_SUCCEED)) {
				processPanel.getFunctionPanel().setAnaylzable(false);
			}
		}else if(msg.getStatus().equals(Status.NO_ANAYLYSIS)) {
			processPanel.getFunctionPanel().setAnaylzable(false);
		}
		message.setStatus(msg.getStatus());
		message.setCiphertext(msg.getCiphertext());
	}
	
	public static Message getMessage() {
		return message;
	}

	@Override
	public void onClearPlaintextArea() {
		message.setPlaintext("");
		statusLabel.setText(Status.CLEAR_PLAINTEXT_SUCCEED);
		message.setStatus(Status.CLEAR_PLAINTEXT_SUCCEED);
		processPanel.showPlaintext(message.getPlaintext());
	}

	@Override
	public void onClearCiphertextArea() {
		message.setCiphertext("");
		statusLabel.setText(Status.CLEAR_CIPHERTEXT_SUCCEED);
		message.setStatus(Status.CLEAR_CIPHERTEXT_SUCCEED);
		processPanel.showCiphertext(message.getCiphertext());
	}

	@Override
	public void onClearAll() {
		message.setPlaintext("");
		message.setCiphertext("");
		message.setKey("");
		message.setStatus(Status.NO_SECRET_KEY);
		processPanel.updateInterface(message);
		resultAnalyzePanel.updateInterface();
	}
}
