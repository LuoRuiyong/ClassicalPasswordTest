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
	private String[] mainMenuLabel = {"文件","编辑","帮助"};
	private String childMenuItemLabel [][] = {{"打开明文文件","-","打开密文文件","-","保存明文为...","-","保存密文为...","-","退出"},
											  {"清除明文","-","清除密文","-","清除所有"},
											  {"使用说明","-","联系我们"}};
	private int[][] menuShotcuts = {{KeyEvent.VK_O,0,KeyEvent.VK_O,0,KeyEvent.VK_S,0,KeyEvent.VK_S,0,KeyEvent.VK_Q},
								   {KeyEvent.VK_D,0,KeyEvent.VK_D,0,KeyEvent.VK_T},
								   {KeyEvent.VK_H,0,KeyEvent.VK_U}};
	private boolean[][] isShihts = {{false,false,true,false,false,false,true,false,false},
									{false,false,true,false,false},
									{false,false,false}};
	private final String TYPE_PLAINTEXT = "明文";
	private final String TYPE_CIPHERTEXT = "密文";
	
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
		setFont(new Font("楷书", Font.PLAIN, 14));
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
						"本程序支持的功能：\n1.使用Caesar密码算法加密、解密\n2.使用PlayPair密码算法加密、解密\n3.使用Hill3密码算法加密、解密\n4.破解使用Caesar密码算法加密的密文\n"
						+ "功能说明:\n1.使用Caesar密码算法，可加解密任意文本\n2.使用Playfair或Hill3密码算法只能加解密英文文档，不区分大小写，非英文字母字符自动过滤\n3.Hill3密钥对应的密钥矩阵必须是可逆矩阵(mod 26)，输入过程中可能会遇到多次失败，请耐心输入\n4.打开明文文件或密文文件大小限制在20KB之内\n"
						+ "操作说明：\n1.使用加解密操作前，需要检测密钥的合法性\n2.操作对象(明文/密文)不能为空或完全不包含英文字母\n3.Caesar密码算法的密钥必须为0~25的整数\n4.Playfair密码算法的密钥必须包含英文字母(不区分大小写)，非英文字母自动过滤\n5.Hill3密码算法的密钥必须包含9个字母以上或为9个0~25的整数，数值使用用空格隔开\n6.分析操作只有在明文、密文和密钥一致的情况下才可以使用,即加密、解密或破解后可使用\n","使用说明",JOptionPane.PLAIN_MESSAGE);
			}else if(cmd.equals(childMenuItemLabel[2][2])) {
				JOptionPane.showMessageDialog((Component) getParent(),
						"广工学生\n联系方式：1217151349@qq.com\n","关于我们",JOptionPane.PLAIN_MESSAGE);
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
		chooser.setDialogTitle("打开"+typeName+"文件");
		int flag = chooser.showOpenDialog((Component) getParent());
		if(flag == JFileChooser.APPROVE_OPTION){
			String content = DocumentUtil.read(chooser.getSelectedFile().getPath());
			Message message = new Message();
			if(content == null) {
				if(messageChangedListener != null) {
					messageChangedListener.onOnlyStatusChanged(Status.OPEN_FILE_FAILED);
				}
				JOptionPane.showMessageDialog((Component) getParent(),"文件过大，无法打开当前文件","提示",JOptionPane.WARNING_MESSAGE);
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
			JOptionPane.showMessageDialog((Component) getParent(),"未知错误，无法打开当前文件","提示",JOptionPane.WARNING_MESSAGE);
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
			JOptionPane.showMessageDialog((Component) getParent(),"无"+typeName+"数据，无法保存到文件","提示",JOptionPane.WARNING_MESSAGE);
			return;
		}
		JFileChooser chooser = new JFileChooser();
		chooser.setDialogTitle("保存"+typeName+"为");
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
				JOptionPane.showMessageDialog((Component) getParent(),"未知错误，无法保存到文件","提示",JOptionPane.WARNING_MESSAGE);
			}finally {
				if(out != null) {
					out.close();
				}
				if(messageChangedListener != null) {
					messageChangedListener.onOnlyStatusChanged(Status.SAVE_FILE_SUCCEED);
				}
			}   
		 }else if(flag == JFileChooser.ERROR_OPTION) {
		 	JOptionPane.showMessageDialog((Component) getParent(),"未知错误，无法保存到文件","提示",JOptionPane.WARNING_MESSAGE);
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
