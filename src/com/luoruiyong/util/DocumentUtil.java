package com.luoruiyong.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;

import com.luoruiyong.bean.Message;
import com.luoruiyong.constant.ArithmeticType;
import com.luoruiyong.constant.Status;

public class DocumentUtil {
	public static final String FILE_PATH = "D:/setting.xml";
	
	public class SettingElement{
		public static final String ROOT = "Setting";
		public static final String CAESAR = "Caesar";
		public static final String PLAYFAIR = "Playfair";
		public static final String HILL = "Hill";
		public static final String KEY = "Key";
		public static final String PLAINTEXT = "Plaintext";
		public static final String CIPHERTEXT = "Ciphertext";
		public static final String STATUS = "Status";
	}
	
	// 创建文件
	public static boolean createXmlSettingFile() {
		Document document = null;
		document = DocumentHelper.createDocument();
        Element root = DocumentHelper.createElement(SettingElement.ROOT);

        Element caesarElement = DocumentHelper.createElement(SettingElement.CAESAR);
        Element key = caesarElement.addElement(SettingElement.KEY);
        key.setText("3");
        Element plaintext = caesarElement.addElement(SettingElement.PLAINTEXT);
        plaintext.setText("we are good childen!");
        Element ciphertext = caesarElement.addElement(SettingElement.CIPHERTEXT);
        ciphertext.setText("zh duh jrrg fkloghq!");
        Element status = caesarElement.addElement(SettingElement.STATUS);
        status.setText(Status.NO_SECRET_KEY);
        
        Element playfairElement = DocumentHelper.createElement(SettingElement.PLAYFAIR);
        key = playfairElement.addElement(SettingElement.KEY);
        key.setText("monarchy");
        plaintext = playfairElement.addElement(SettingElement.PLAINTEXT);
        plaintext.setText("we are discovered save yourself");
        ciphertext = playfairElement.addElement(SettingElement.CIPHERTEXT);
        ciphertext.setText("UGRMKCSXHMUFMKBTOXGCMVATLUGE");
        status = playfairElement.addElement(SettingElement.STATUS);
        status.setText(Status.NO_SECRET_KEY);
        
        Element hillElement = DocumentHelper.createElement(SettingElement.HILL);
        key = hillElement.addElement(SettingElement.KEY);
        key.setText("17 17 5 21 18 21 2 2 19");
        plaintext = hillElement.addElement(SettingElement.PLAINTEXT);
        plaintext.setText("pay more money");
        ciphertext = hillElement.addElement(SettingElement.CIPHERTEXT);
        ciphertext.setText("LNSHDLEWMTRW");
        status = hillElement.addElement(SettingElement.STATUS);
        status.setText(Status.NO_SECRET_KEY);
        
        root.add(caesarElement);
        root.add(playfairElement);
        root.add(hillElement);
        
        document.add(root);
        return writeToFile(document);
	}
	
	// 修改节点信息
	public static boolean saveMessage(Message message) {
		Document document = getDocument();
		String node = selectElement(message.getArithmeticType());
		if(document == null || node == null) {
			return false;
		}
        Element element = document.getRootElement().element(node).element(SettingElement.KEY) ;
        element.setText(message.getKey());
        element = document.getRootElement().element(node).element(SettingElement.PLAINTEXT) ;
        element.setText(message.getPlaintext());
        element = document.getRootElement().element(node).element(SettingElement.CIPHERTEXT) ;
        element.setText(message.getCiphertext());
        element = document.getRootElement().element(node).element(SettingElement.STATUS) ;
        element.setText(message.getStatus());
		return writeToFile(document);
	}
	
	// 筛选节点
	private static String selectElement(ArithmeticType type) {
		if(type == null) {
			return null;
		}
		String elememtName = null;
		switch (type) {
		case CAESAR:
			elememtName = SettingElement.CAESAR;
			break;
		case PLAYFAIR:
			elememtName = SettingElement.PLAYFAIR;
			break;
		default:
			elememtName = SettingElement.HILL;
			break;
		}
		return elememtName;
	}
	
	// 筛选节点
	private static ArithmeticType selectArithmeticType(String type) {
		if(type == null) {
			return null;
		}
		if(type.equals(SettingElement.CAESAR)){
			return ArithmeticType.CAESAR;
		}else if(type.equals(SettingElement.PLAYFAIR)){
			return ArithmeticType.PLAYFAIR;
		}else {
			return ArithmeticType.HILL;
		}
	}
	
	// 保存修改到文件
	private static boolean writeToFile(Document document) {
		OutputStream outputStream = null;
		XMLWriter xmlWriter = null;
		try {
			OutputFormat outputFormat = new OutputFormat();
			outputFormat.setEncoding("UTF-8");
			outputStream = new FileOutputStream(FILE_PATH);
			xmlWriter = new XMLWriter(outputStream,outputFormat);
			xmlWriter.write(document);
		} catch (IOException e){
			return false;
		} catch (Exception e){
			return false;
		} finally {
			try {
				outputStream.close();
				xmlWriter.close();
			} catch (IOException e) {
				e.printStackTrace();
			} 
		}
		return true;
	}
	
	// 打开文件
	private static Document getDocument() {
        Document document = null;
		try {
			// 先尝试从文件获取
			document = new SAXReader().read(new File(FILE_PATH));
		} catch (DocumentException e) {
			// 从文件中获取失败，尝试创建文件
			if(!createXmlSettingFile()) {
				// 文件创建失败
				return null;
			}
			try {
				document = new SAXReader().read(new File(FILE_PATH));
			}catch (DocumentException e2) {
				return null;
			}
		} 
		return document;
	}
	
	public static Message getMessage(ArithmeticType type) {
		String node = selectElement(type);
		Document document = getDocument();
		if(document == null || node == null) {
			return null;
		}
		Message message = new Message();
		Element targetElement = document.getRootElement().element(node);
		message.setArithmeticType(selectArithmeticType(targetElement.getName()));
		message.setKey(targetElement.element(SettingElement.KEY).getText());
		message.setPlaintext(targetElement.element(SettingElement.PLAINTEXT).getText());
		message.setCiphertext(targetElement.element(SettingElement.CIPHERTEXT).getText());
		message.setStatus(targetElement.element(SettingElement.STATUS).getText());
		return message;
	}
	
	public static String read(String fileName) { 
		File file=new File(fileName);
		if(file.length() > 1024 * 20) {
			return null;
		}
		BufferedReader br= null;
		char[] buffer = new char[1024];
		int len = 0;
		StringBuilder builder = new StringBuilder();
		try {
	    	br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"utf-8"));
			while((len = br.read(buffer, 0, buffer.length))!= -1){
			    builder.append(buffer,0,len);
			    System.out.println();
			}
		} catch (IOException e) {
			return null;
		}finally {
			if(br != null) {
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	    return builder.toString();    
	}
}
