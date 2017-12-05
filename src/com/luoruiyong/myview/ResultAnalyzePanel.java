package com.luoruiyong.myview;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.lang.reflect.Array;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.general.DefaultPieDataset;
import org.omg.CORBA.PUBLIC_MEMBER;

import com.luoruiyong.constant.ArithmeticType;

public class ResultAnalyzePanel extends JPanel {
	
	private JPanel groupAnalyzePanel;
	private JPanel frequencyAnalyzePanel;
	private JPanel objectAnaylyzePanel;
	private JTextArea taGroupData;
	
	public ResultAnalyzePanel() {
		frequencyAnalyzePanel = new JPanel();
		objectAnaylyzePanel = new JPanel();
		
		groupAnalyzePanel = new JPanel();
		groupAnalyzePanel.setLayout(new BorderLayout());
		taGroupData = new JTextArea();
		taGroupData.setEditable(false);
		groupAnalyzePanel.add(taGroupData,BorderLayout.CENTER);
		
		setLayout(new GridLayout(1, 3));
		add(groupAnalyzePanel);
		add(frequencyAnalyzePanel);
		add(objectAnaylyzePanel);
		
		frequencyAnalyzePanel.setLayout(new CardLayout());
		objectAnaylyzePanel.setLayout(new CardLayout());
		//createPicture();
	}
	
	// ���������㷨��Ӧ�Ľ���
	public void setArithmeticType(ArithmeticType arithmeticType) {
		switch (arithmeticType) {
		case CAESAR:
			// �������������������
			break;
		case PLAYFAIR:
			frequencyAnalyzePanel.setVisible(false);
			objectAnaylyzePanel.setVisible(false);
			// �������������������
			break;
			
		case HILL:
			frequencyAnalyzePanel.setVisible(false);
			objectAnaylyzePanel.setVisible(false);
			// �������������������
			break;
		}
	}
	
	public void setGroupAnalyzeData(String data) {
		taGroupData.setText(data);
	}
	
	public void createPicture() {
		DefaultPieDataset dpd=new DefaultPieDataset(); //����һ��Ĭ�ϵı�ͼ
        dpd.setValue("������Ա", 25);  //��������
        dpd.setValue("�г���Ա", 25);
        dpd.setValue("������Ա", 45);
        dpd.setValue("������Ա", 10);
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        standardChartTheme.setExtraLargeFont(new Font("����", Font.BOLD, 10));
        standardChartTheme.setRegularFont(new Font("����", Font.PLAIN, 8));
        standardChartTheme.setLargeFont(new Font("����", Font.PLAIN, 8));
        ChartFactory.setChartTheme(standardChartTheme);
        JFreeChart chart=ChartFactory.createPieChart("ĳ��˾��Ա��֯����ͼ",dpd,true,true,false); 
        //���Բ�����API�ĵ�,��һ�������Ǳ��⣬�ڶ���������һ�����ݼ���������������ʾ�Ƿ���ʾLegend�����ĸ�������ʾ�Ƿ���ʾ��ʾ�������������ʾͼ���Ƿ����URL
        ChartPanel panel=new ChartPanel(chart); 
        ChartPanel panel2 = new ChartPanel(chart);
        panel.setPreferredSize(frequencyAnalyzePanel.getPreferredSize());
        panel2.setPreferredSize(objectAnaylyzePanel.getPreferredSize());
        //chartҪ����Java��������У�ChartFrame�̳���java��Jframe�ࡣ�õ�һ�������������Ƿ��ڴ������Ͻǵģ��������м�ı��⡣
        frequencyAnalyzePanel.add(panel);
        objectAnaylyzePanel.add(panel2);
        //objectAnaylyzePanel.add(panel);
	}
	
}
