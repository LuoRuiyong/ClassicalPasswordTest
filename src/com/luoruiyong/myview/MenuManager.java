package com.luoruiyong.myview;

import java.awt.Component;
import java.awt.Font;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileFilter;

import com.luoruiyong.OnMessageChangedListener;
import com.luoruiyong.bean.Message;
import com.luoruiyong.constant.Status;
import com.luoruiyong.ui.MainFrame;
import com.luoruiyong.util.DocumentUtil;

public class MenuManager extends MenuBar {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String[] mainMenuLabel = {"�ļ�","�༭","����"};
	private String childMenuItemLabel [][] = {{"�������ļ�","-","�������ļ�","-","��������Ϊ...","-","��������Ϊ...","-","�˳�"},
											  {"�������","-","�������","-","�������"},
											  {"ʹ��˵��","-","��ϵ����"}};
	private int[][] menuShotcuts = {{KeyEvent.VK_O,0,KeyEvent.VK_O,0,KeyEvent.VK_S,0,KeyEvent.VK_S,0,KeyEvent.VK_Q},
								   {KeyEvent.VK_D,0,KeyEvent.VK_D,0,KeyEvent.VK_T},
								   {KeyEvent.VK_H,0,KeyEvent.VK_U}};
	private boolean[][] isShihts = {{false,false,true,false,false,false,true,false,false},
									{false,false,true,false,false},
									{false,false,false}};
	private final String TYPE_PLAINTEXT = "����";
	private final String TYPE_CIPHERTEXT = "����";
	
	private Menu[] mainMenu;
	private MenuItem[] childMenuItem;
	private OnClearRecordListener clearRecordListener;
	private OnMessageChangedListener messageChangedListener;
	
	public MenuManager() {
		MyActionListener listener = new MyActionListener();
		int mainCount = mainMenuLabel.length;
		int childCount = 0;
		for(int i = 0;i < childMenuItemLabel.length;i++) {
			for(int j= 0;j< childMenuItemLabel[i].length;j++) {
				childCount+=1;
			}
		}
		mainMenu = new Menu[mainCount];
		childMenuItem = new MenuItem[childCount];
		for(int i = 0;i<mainCount;i++) {
			mainMenu[i] = new Menu(mainMenuLabel[i]);
			for(int j = 0;j<menuShotcuts[i].length;j++) {
				MenuShortcut shortcut = null;
				if(menuShotcuts[i][j] != 0){
					shortcut = new MenuShortcut(menuShotcuts[i][j],isShihts[i][j]);
				}
				int location = 0;
				if(i>0) {
					location = i*childMenuItemLabel[i-1].length+j;
				}else {
					location = j;
				}
				childMenuItem[location] = new MenuItem(childMenuItemLabel[i][j],shortcut);
				childMenuItem[location].addActionListener(listener);
				mainMenu[i].add(childMenuItem[location]);
			}
			add(mainMenu[i]);
		}
		setFont(new Font("����", Font.PLAIN, 14));
	}
	
	private class MyActionListener implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			String cmd = e.getActionCommand();
			if(cmd.equals(childMenuItemLabel[0][0])) {
				openFile(TYPE_PLAINTEXT);
			}else if(cmd.equals(childMenuItemLabel[0][2])) {
				openFile(TYPE_CIPHERTEXT);
			}else if(cmd.equals(childMenuItemLabel[0][4])) {
				saveAsFile(TYPE_PLAINTEXT);
			}else if(cmd.equals(childMenuItemLabel[0][6])) {
				saveAsFile(TYPE_CIPHERTEXT);
			}else if(cmd.equals(childMenuItemLabel[0][8])) {
				System.exit(0);
			}else if(cmd.equals(childMenuItemLabel[1][0])) {
				if(clearRecordListener != null) {
					clearRecordListener.onClearPlaintextArea();
				}
			}else if(cmd.equals(childMenuItemLabel[1][2])) {
				if(clearRecordListener != null) {
					clearRecordListener.onClearCiphertextArea();
				}
			}else if(cmd.equals(childMenuItemLabel[1][4])) {
				if(clearRecordListener != null) {
					clearRecordListener.onClearAll();
				}
			}else if(cmd.equals(childMenuItemLabel[2][0])) {
				JOptionPane.showMessageDialog((Component) getParent(),
						"������֧�ֵĹ��ܣ�\n1.ʹ��Caesar�����㷨���ܡ�����\n2.ʹ��PlayPair�����㷨���ܡ�����\n3.ʹ��Hill3�����㷨���ܡ�����\n4.�ƽ�ʹ��Caesar�����㷨���ܵ�����\n"
						+ "����˵��:\n1.ʹ��Caesar�����㷨���ɼӽ��������ı�\n2.ʹ��Playfair��Hill3�����㷨ֻ�ܼӽ���Ӣ���ĵ��������ִ�Сд����Ӣ����ĸ�ַ��Զ�����\n3.Hill3��Կ��Ӧ����Կ��������ǿ������(mod 26)����������п��ܻ��������ʧ�ܣ�����������\n4.�������ļ��������ļ���С������20KB֮��\n"
						+ "����˵����\n1.ʹ�üӽ��ܲ���ǰ����Ҫ�����Կ�ĺϷ���\n2.��������(����/����)����Ϊ�ջ���ȫ������Ӣ����ĸ\n3.Caesar�����㷨����Կ����Ϊ0~25������\n4.Playfair�����㷨����Կ�������Ӣ����ĸ(�����ִ�Сд)����Ӣ����ĸ�Զ�����\n5.Hill3�����㷨����Կ�������9����ĸ���ϻ�Ϊ9��0~25����������ֵʹ���ÿո����\n6.��������ֻ�������ġ����ĺ���Կһ�µ�����²ſ���ʹ��,�����ܡ����ܻ��ƽ���ʹ��\n","ʹ��˵��",JOptionPane.PLAIN_MESSAGE);
			}else if(cmd.equals(childMenuItemLabel[2][2])) {
				JOptionPane.showMessageDialog((Component) getParent(),
						"�㹤ѧ��\n��ϵ��ʽ��1217151349@qq.com\n","��������",JOptionPane.PLAIN_MESSAGE);
			}
		}
	}
	
	private class MyFileFilter extends FileFilter{
		@Override
		public boolean accept(File f) {
			return f.isFile();
		}
		@Override
		public String getDescription() {
			return "*.*";
		}
	}
	
	public void openFile(String typeName) {
		JFileChooser chooser = new JFileChooser();
		chooser.setFileFilter(new MyFileFilter());
		chooser.setDialogTitle("��"+typeName+"�ļ�");
		int flag = chooser.showOpenDialog((Component) getParent());
		if(flag == JFileChooser.APPROVE_OPTION){
			String content = DocumentUtil.read(chooser.getSelectedFile().getPath());
			Message message = new Message();
			if(content == null) {
				if(messageChangedListener != null) {
					messageChangedListener.onOnlyStatusChanged(Status.OPEN_FILE_FAILED);
				}
				JOptionPane.showMessageDialog((Component) getParent(),"�ļ������޷��򿪵�ǰ�ļ�","��ʾ",JOptionPane.WARNING_MESSAGE);
			}else if(messageChangedListener != null) {
				if(typeName.equals(TYPE_PLAINTEXT)) {
					message.setPlaintext(content);
					message.setStatus(Status.OPEN_FILE_SUCCEED);
					messageChangedListener.onPlaintextChanged(message);	
				}else {
					message.setCiphertext(content);
					message.setStatus(Status.OPEN_FILE_SUCCEED);
					messageChangedListener.onCiphertextChanged(message);
				}
			}
		}else if(flag == JFileChooser.ERROR_OPTION) {
			if(messageChangedListener != null) {
				messageChangedListener.onOnlyStatusChanged(Status.OPEN_FILE_FAILED);
			}
			JOptionPane.showMessageDialog((Component) getParent(),"δ֪�����޷��򿪵�ǰ�ļ�","��ʾ",JOptionPane.WARNING_MESSAGE);
		}
	}
	
	private void saveAsFile(String typeName) {
		String content = null;
		if(typeName.equals(TYPE_CIPHERTEXT)){
			content = MainFrame.getMessage().getCiphertext();
		}else {
			content = MainFrame.getMessage().getPlaintext();
		}
		if(content == null || content.trim().equals("")) {
			if(messageChangedListener != null) {
				messageChangedListener.onOnlyStatusChanged(Status.SAVE_FILE_FAILED);
			}
			JOptionPane.showMessageDialog((Component) getParent(),"��"+typeName+"���ݣ��޷����浽�ļ�","��ʾ",JOptionPane.WARNING_MESSAGE);
			return;
		}
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("����"+typeName+"Ϊ");
		 int flag = 0;
		 flag = chooser.showSaveDialog((Component) getParent());
		 if(flag == JFileChooser.APPROVE_OPTION) {
			 String filePath = chooser.getSelectedFile().getAbsolutePath();
			 if(!filePath.contains(".")) {
				 filePath += ".txt";
			 }
            PrintWriter out = null;
            try {
	            out = new PrintWriter(new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath),"UTF-8")));
	            out.write(content);
        		out.flush();
			} catch (Exception e) {
				if(messageChangedListener != null) {
					messageChangedListener.onOnlyStatusChanged(Status.SAVE_FILE_FAILED);
				}
				JOptionPane.showMessageDialog((Component) getParent(),"δ֪�����޷����浽�ļ�","��ʾ",JOptionPane.WARNING_MESSAGE);
			}finally {
				if(out != null) {
					out.close();
				}
				if(messageChangedListener != null) {
					messageChangedListener.onOnlyStatusChanged(Status.SAVE_FILE_SUCCEED);
				}
			}   
		 }else if(flag == JFileChooser.ERROR_OPTION) {
		 	JOptionPane.showMessageDialog((Component) getParent(),"δ֪�����޷����浽�ļ�","��ʾ",JOptionPane.WARNING_MESSAGE);
		 } 
	}
	
	public void setOnClearRecordListener(OnClearRecordListener listener) {
		this.clearRecordListener = listener;
	}
	
	public void setOnMessageChangedListener(OnMessageChangedListener listener) {
		this.messageChangedListener = listener;
	}
	
	public interface OnClearRecordListener{
		void onClearPlaintextArea();
		void onClearCiphertextArea();
		void onClearAll();
	}
}
