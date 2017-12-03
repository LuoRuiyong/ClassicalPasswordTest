package com.luoruiyong.myview;

import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.StandardChartTheme;
import org.jfree.data.general.DefaultPieDataset;
import org.omg.CORBA.PUBLIC_MEMBER;

public class ResultAnalyzePanel extends JPanel {
	
	private JPanel frequencyAnalyzePanel;
	private JPanel objectAnaylyzePanel;
	private JPanel groupAnalyzePanel;
	
	public ResultAnalyzePanel() {
		frequencyAnalyzePanel = new JPanel();
		objectAnaylyzePanel = new JPanel();
		groupAnalyzePanel = new JPanel();
		
		setLayout(new GridLayout(1, 3));
		add(groupAnalyzePanel);
		add(frequencyAnalyzePanel);
		add(objectAnaylyzePanel);
		
		frequencyAnalyzePanel.setLayout(new CardLayout());
		objectAnaylyzePanel.setLayout(new CardLayout());
		createPicture();
	}
	
	// 设置密码算法对应的界面
	public void setArithmeticType(int arithmeticType) {
		switch (arithmeticType) {
		case ArithmeticPanel.CAESAR:
			// 清除分组分析里面的内容
			break;
		case ArithmeticPanel.PLAYFAIR:
			frequencyAnalyzePanel.setVisible(false);
			objectAnaylyzePanel.setVisible(false);
			// 清除分组分析里面的内容
			break;
			
		case ArithmeticPanel.HILL:
			frequencyAnalyzePanel.setVisible(false);
			objectAnaylyzePanel.setVisible(false);
			// 清除分组分析里面的内容
			break;
		}
	}
	
	public void createPicture() {
		DefaultPieDataset dpd=new DefaultPieDataset(); //建立一个默认的饼图
        dpd.setValue("管理人员", 25);  //输入数据
        dpd.setValue("市场人员", 25);
        dpd.setValue("开发人员", 45);
        dpd.setValue("其他人员", 10);
        StandardChartTheme standardChartTheme = new StandardChartTheme("CN");
        standardChartTheme.setExtraLargeFont(new Font("隶书", Font.BOLD, 10));
        standardChartTheme.setRegularFont(new Font("宋书", Font.PLAIN, 8));
        standardChartTheme.setLargeFont(new Font("宋书", Font.PLAIN, 8));
        ChartFactory.setChartTheme(standardChartTheme);
        JFreeChart chart=ChartFactory.createPieChart("某公司人员组织数据图",dpd,true,true,false); 
        //可以查具体的API文档,第一个参数是标题，第二个参数是一个数据集，第三个参数表示是否显示Legend，第四个参数表示是否显示提示，第五个参数表示图中是否存在URL
        ChartPanel panel=new ChartPanel(chart); 
        ChartPanel panel2 = new ChartPanel(chart);
        panel.setPreferredSize(frequencyAnalyzePanel.getPreferredSize());
        panel2.setPreferredSize(objectAnaylyzePanel.getPreferredSize());
        //chart要放在Java容器组件中，ChartFrame继承自java的Jframe类。该第一个参数的数据是放在窗口左上角的，不是正中间的标题。
        frequencyAnalyzePanel.add(panel);
        objectAnaylyzePanel.add(panel2);
        //objectAnaylyzePanel.add(panel);
	}
}
