package com.dvnchina.advertDelivery.action;

import java.awt.Font;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.title.LegendTitle;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.DefaultPieDataset;

import com.opensymphony.xwork2.ActionSupport;

public class ChartAction extends ActionSupport 
{ 
	 private JFreeChart chart; 
	 // 必须提供 getChart() 方法，且由该方法返回 JFreeChart 对象
	 public JFreeChart getChart() 
	 { 
		 chart = ChartFactory.createPieChart3D( 
			"图书销量统计图",  // 图表标题
			 getDataSet(), // 数据
			 true, // 是否显示图例
			 false, // 是否显示工具提示
			 false // 是否生成 URL 
		 ); 
		 // 重新设置图表标题，改变字体
		 chart.setTitle(new TextTitle("图书销量统计图"
			 , new Font("黑体", Font.ITALIC , 22))); 
		 // 取得统计图表的第一个图例
		 LegendTitle legend = chart.getLegend(0); 
		 // 修改图例的字体
		 legend.setItemFont(new Font("宋体", Font.BOLD, 14)); 
		 // 获得饼图的 Plot 对象
		 PiePlot plot = (PiePlot)chart.getPlot(); 
		 // 设置饼图各部分的标签字体
		 plot.setLabelFont(new Font("隶书", Font.BOLD, 18)); 
		 // 设定背景透明度（0-1.0 之间）
       	 plot.setBackgroundAlpha(0.9f); 
		 // 设定前景透明度（0-1.0 之间）
      	 plot.setForegroundAlpha(0.50f); 
		 return chart; 
	 } 
	 // 获取生成统计图的 Dataset 
	 private DefaultPieDataset getDataSet() 
	 { 
		 DefaultPieDataset dataset = new DefaultPieDataset(); 
		 dataset.setValue("疯狂 Java 讲义",47000); 
		 dataset.setValue("轻量级 Java EE 企业实战",38000); 
		 dataset.setValue("疯狂 Ajax 讲义",31000); 
		 dataset.setValue("Struts 2 权威指南",29000); 
		 dataset.setValue("疯狂 XML 讲义",25000); 
		 return dataset; 
	 } 
} 




