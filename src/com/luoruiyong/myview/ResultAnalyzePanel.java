package com.luoruiyong.myview;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.luoruiyong.bean.CaesarMessage;
import com.luoruiyong.bean.Message;
import com.luoruiyong.constant.ArithmeticType;
import com.luoruiyong.constant.Status;
import com.luoruiyong.password.Caesar;
import com.luoruiyong.password.Hill;
import com.luoruiyong.ui.MainFrameConstraints;
import com.luoruiyong.util.CompareUtil;
import com.luoruiyong.util.MatrixUtil;

public class ResultAnalyzePanel extends JPanel {
	
	private static final long serialVersionUID = 1L;
	// 分析板块1，分组对比
	private JPanel analyzePanel1;
	private JTextArea taGroupData;
	
	// 分析板块2，加密矩阵，频率扇形图表
	private JPanel analyzePanel2;
	private JPanel hillInverseMatrix;
	private final int TYPE_ENCRYPT = 1;
	private final int TYPE_DECRYPT = 2;
	private final int TYPE_EXHAUST_CRACK = 3;
	private final int TYPE_PROBABILITY_CRACK = 4;
	
	public ResultAnalyzePanel() {
		analyzePanel1 = new JPanel();
		analyzePanel1.setLayout(new BorderLayout());
		taGroupData = new JTextArea();
		taGroupData.setLineWrap(true);  // 自动换行
		taGroupData.setWrapStyleWord(true); // 断行不断字
		JScrollPane scrollPane = new JScrollPane(taGroupData);
		taGroupData.setEditable(false);
		taGroupData.setFont(new Font("宋体", Font.PLAIN, 14));
		analyzePanel1.add(scrollPane,BorderLayout.CENTER);
		
		analyzePanel2 = new JPanel();
		hillInverseMatrix = new JPanel();
		hillInverseMatrix.setLayout(new GridLayout(3, 3));
		
		setLayout(new GridLayout(1,2,1,0));
		add(analyzePanel1);
		add(analyzePanel2);
		//add(analyzePanel3);
		analyzePanel1.setBorder(BorderFactory.createLineBorder(Color.orange,2));
		analyzePanel2.setBorder(BorderFactory.createLineBorder(Color.orange,2));
		analyzePanel2.setBackground(Color.white);
		analyzePanel2.setLayout(new GridBagLayout());
	}
	
	// 设置密码算法对应的界面
	public void setArithmeticType(ArithmeticType arithmeticType) {
		taGroupData.setText("");
		analyzePanel2.removeAll();
	}
	
	public void updateInterface() {
		taGroupData.setText("");
		analyzePanel2.removeAll();
	}
	
	public void showAnalyzeResult(Message message) {
		int analyzeType = getAnalyzeType(message.getStatus());
		groupCompareAnalyze(message,analyzeType);
	}
	
	private void groupCompareAnalyze(Message message, int analyzeType) {
		String plaintext = message.getPlaintext();
		String ciphertext = message.getCiphertext();
		String key = message.getKey();
		int groupSize = getGroupSize(message.getArithmeticType());
		ArrayList<Message> messages;
		switch (analyzeType) {
		case TYPE_ENCRYPT:
			messages = CompareUtil.getDetailMessage(plaintext, ciphertext, groupSize);
			if(messages == null) {
				// 通知
				taGroupData.setText("无分析数据");
			}else {
				StringBuilder textBuilder = new StringBuilder();
				textBuilder.append(">  分组对比分析：\n>  密钥："+key+"\n>  明文："+plaintext+"\n>  密文："+ciphertext+"\n>  明文组 密文组\n");
				for(Message msg : messages) {
					textBuilder.append(">  "+msg.getPlaintext()+" --> "+msg.getCiphertext()+"\n");
				}
				taGroupData.setText(textBuilder.toString());
			}
			break;
		case TYPE_DECRYPT:
			messages = CompareUtil.getDetailMessage(plaintext, ciphertext, groupSize);
			if(messages == null) {
				// 通知
				taGroupData.setText("无分析数据");
			}else {
				StringBuilder textBuilder = new StringBuilder();
				textBuilder.append(">  分组对比分析\n>  密钥："+key+"\n>  密文："+ciphertext+"\n>  明文："+plaintext+"\n>  密文组 明文组\n");
				for(Message msg : messages) {
					textBuilder.append(">  "+msg.getCiphertext()+" --> "+msg.getPlaintext()+"\n");
				}
				taGroupData.setText(textBuilder.toString());
			}
			if(message.getArithmeticType() == ArithmeticType.HILL) {
				showInverseMatrix(key);
			}
			break;
		case TYPE_EXHAUST_CRACK:
			ArrayList<Message> messages2 = Caesar.exhaustCrack(ciphertext);
			ArrayList<ArrayList<Message>> list = CompareUtil.getCaesarExhaustCrackDetailMessage(messages2);
			if(list == null) {
				// 通知
				taGroupData.setText("无分析数据");
			}else {
				StringBuilder textBuilder = new StringBuilder();
				textBuilder.append(">  穷举法破解分组对比\n");
				for(int i = 0;i < list.size();i++) {
					textBuilder.append(">>>- - - - - - - 第 "+(i+1)+" 组 - - - - - - - \n");
					textBuilder.append(">  可能密钥："+i+"\n>  原密文:"+ciphertext+"\n>  可能明文："+messages2.get(i).getPlaintext()+"\n>  密文组 明文组\n");
					ArrayList<Message> messages3 = list.get(i);
					for(Message msg : messages3) {
						textBuilder.append(">  "+msg.getCiphertext()+" --> "+msg.getPlaintext()+"\n");
					}
				}
				textBuilder.append(">  共有"+list.size()+"组可能密钥");
				taGroupData.setText(textBuilder.toString());
			}
			break;
			
		default:
			CaesarMessage message2 = Caesar.autoCrack(ciphertext, true, false);
			plaintext = message2.getPlaintext();
			messages = CompareUtil.getDetailMessage(plaintext, ciphertext, groupSize);
			if(messages == null) {
				// 通知
				taGroupData.setText("无分析数据");
			}else {
				StringBuilder textBuilder = new StringBuilder();
				textBuilder.append(">  概率破解分组对比分析：\n>  密文："+ciphertext+"\n>  破解得到的密钥："+message2.getKey()+"\n>  破解得到的明文："+plaintext+"\n>  密文组 明文组\n");
				for(Message msg : messages) {
					textBuilder.append(">  "+msg.getCiphertext()+" --> "+msg.getPlaintext()+"\n");
				}
				textBuilder.append(">  可信度："+message2.getSimilarity()+"%");
				taGroupData.setText(textBuilder.toString());
			}
			showLineChart(ciphertext);
			break;
		}
	}
	
	public void showLineChart(String ciphertext) {
		double[] frequency = Caesar.getLetterFrequency(ciphertext);
		String series1 = "标准字母频率";
		String series2 = "密文字母频率";
		DefaultCategoryDataset dataset = new DefaultCategoryDataset();
		for(int i = 0;i< 26;i++) {
			char ch = (char) (i+65);
			dataset.setValue(Caesar.STANDARD_FREQUENCY[i]*100,series1,ch+"");
			dataset.setValue(frequency[i]*100,series2,ch+"");
		}
		JFreeChart chart = ChartFactory.createLineChart("英文字母频率曲线图",// 主标题的名称
					null,// X轴的标签
					"频率/%",// Y轴的标签 
					dataset, // 图标显示的数据集合
					PlotOrientation.VERTICAL, // 图像的显示形式（水平或者垂直） 
					true,// 是否显示子标题 
					true,// 是否生成提示的标签 
					false); // 是否生成URL链接 
		// 处理主标题的乱码 
		 chart.getTitle().setFont(new Font("宋体", Font.BOLD,10)); // 处理子标题乱码 
		 chart.getLegend().setItemFont(new Font("宋体", Font.BOLD,10)); // 获取图表区域对象 
		 CategoryPlot categoryPlot = (CategoryPlot) chart.getPlot(); // 获取X轴的对象
		 CategoryAxis categoryAxis = (CategoryAxis) categoryPlot.getDomainAxis(); // 获取Y轴的对象
		 NumberAxis numberAxis = (NumberAxis) categoryPlot.getRangeAxis(); // 处理X轴上的乱码 
		 categoryAxis.setTickLabelFont(new Font("宋体", Font.BOLD, 10)); // 处理X轴外的乱码 
		 categoryAxis.setLabelFont(new Font("宋体", Font.PLAIN, 10)); // 处理Y轴上的乱码
		 numberAxis.setTickLabelFont(new Font("宋体", Font.BOLD,10)); // 处理Y轴外的乱码
		 numberAxis.setLabelFont(new Font("宋体", Font.BOLD, 10)); // 处理Y轴上显示的刻度，以10作为1格
		 numberAxis.setAutoTickUnitSelection(false);
		 NumberTickUnit unit = new NumberTickUnit(2); 
		 numberAxis.setTickUnit(unit); 
		 ChartPanel panel = new ChartPanel(chart);
		 panel.setPreferredSize(analyzePanel2.getPreferredSize());
		 analyzePanel2.removeAll();
		 analyzePanel2.add(panel,new MainFrameConstraints(0, 0).setFill(GridBagConstraints.BOTH).setWeight(100, 100));
	}
	
	private void showInverseMatrix(String key) {
		int[][] matrixData = Hill.isKeyAvailable(key);
		matrixData = MatrixUtil.getForceInverseMatrix(matrixData);
		hillInverseMatrix = new JPanel();
		hillInverseMatrix.setLayout(new GridLayout(3, 3));
		hillInverseMatrix.setBorder(BorderFactory.createLineBorder(Color.black,1));
		hillInverseMatrix.removeAll();
		for(int i = 0;i < 9;i++) {
			JLabel label = new JLabel(" "+matrixData[i/3][i%3]+" ",JLabel.CENTER);
			label.setBorder(BorderFactory.createLineBorder(Color.black,1));
			label.setMinimumSize(new Dimension(30, 30));
			hillInverseMatrix.add(label);
		}
		analyzePanel2.removeAll();
		analyzePanel2.add(new JLabel("密钥逆矩阵",JLabel.CENTER));
		analyzePanel2.add(hillInverseMatrix,new MainFrameConstraints(0, 1).setWeight(1, 0));
	}
	
	// 获取分析类型：加密、解密、穷举破解、概率破解
	private int getAnalyzeType(String status) {
		int type;
		if(status.equals(Status.ENCRYPT_ANALYSIS)) {
			type = TYPE_ENCRYPT;
		}else if(status.equals(Status.DECRYPT_ANALYSIS)){
			type = TYPE_DECRYPT;
		}else if(status.equals(Status.EXHAUST_CRACK_ANAYLYZE)){
			type = TYPE_EXHAUST_CRACK;
		}else{
			type = TYPE_PROBABILITY_CRACK;
		}
		return type;
	}
	
	// 获取一个分组大小：Caesar为1，Playfair为2，Hill为3
	private int getGroupSize(ArithmeticType type) {
		int size;
		switch (type) {
		case CAESAR:
			size = 1;
			break;
		case PLAYFAIR:
			size = 2;
			break;
		default:
			size = 3;
			break;
		}
		return size;
	}
}
