package com.dvnchina.advertDelivery.task;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;
import jxl.write.WriteException;
import jxl.write.biff.RowsExceededException;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.accounts.bean.ContractAccounts;
import com.dvnchina.advertDelivery.common.ExcelUtils;
import com.dvnchina.advertDelivery.common.cache.LocationCache;
import com.dvnchina.advertDelivery.common.cache.LocationCacheBean;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.contract.bean.PositionAD;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.order.bean.QuestionReportBean;
import com.dvnchina.advertDelivery.order.service.GenerateQuestionReportService;
import com.dvnchina.advertDelivery.order.service.OrderService;
import com.dvnchina.advertDelivery.order.service.PlayList4OrderService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;

public class GenerateReportTask {
	private static Logger logger = Logger.getLogger(GenerateReportTask.class);
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	
	private GenerateQuestionReportService generateQuestionReportService;


    public GenerateQuestionReportService getGenerateQuestionReportService() {
        return generateQuestionReportService;
    }

    public void setGenerateQuestionReportService(GenerateQuestionReportService generateQuestionReportService) {
        this.generateQuestionReportService = generateQuestionReportService;
    }







    /**
	 * 定时根据订单生成问卷报表
	 * */
	public void generateReport() {
		logger.info("定时生成问卷报表");
		try {
			List<QuestionReportBean> questionReportTitleList = generateQuestionReportService.getQuestionReportTitle();
			if(questionReportTitleList!=null&&questionReportTitleList.size()>0){
			    for(QuestionReportBean questionReportTitle:questionReportTitleList){
			        exportReport(questionReportTitle);
			        
			    }
			}
    
			logger.info("定时生成问卷报表结束,共有"+questionReportTitleList.size()+"个报表。");
		} catch (Exception e) {
			logger.error("定时生成问卷报表时出错", e);
		}
	}
	
	
	
	public void exportReport(QuestionReportBean questionReportTitle) {   
        Date dateTemp = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String filePath = ".././" + questionReportTitle.getCustomerName() + "_"
                + questionReportTitle.getQuestionName() + "_" + questionReportTitle.getOrderCode() + "_"
                + df.format(dateTemp) + ".xls";
        //获取问题列表
        int columns = 5;
        int tempColumns;
        List<QuestionReportBean> questionList = generateQuestionReportService.getQuestionList(questionReportTitle.getQuestionId().intValue());
        //循环取出每个问题的答案和数目,并计算出总的列数
        for(int i = 0; i < questionList.size(); i++){
            tempColumns = generateQuestionReportService.getAnswerCount(questionReportTitle.getQuestionId().intValue(), questionList.get(i).getQuestion());
            List<Map<String, Object>> answerList = generateQuestionReportService.getAnswerList(questionReportTitle.getQuestionId().intValue(), questionList.get(i).getQuestion());
            Map<String, String> answerMap =new HashMap<String, String>();
            for (int j = 0; j < answerList.size(); j++){
                answerMap.put((String.valueOf(j+1)), (String)answerList.get(j).get("answer"));
            }
            questionList.get(i).setAnswerMap(answerMap);
            questionList.get(i).setAnswerCount(tempColumns);
            columns = columns+tempColumns;
        }
        
        //获取问卷提交次数
        List<QuestionReportBean> questionSubmitList = generateQuestionReportService.getSubmitCount(questionReportTitle.getOrderId().intValue(), questionReportTitle.getQuestionId().intValue());
        WritableWorkbook wwb = null;
        int rowNumber = 5+questionSubmitList.size()+2;
        try {         
            // 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象         
            wwb = Workbook.createWorkbook(new File(filePath));
        } catch (IOException e) {
            e.printStackTrace();
        } 


        
        if (wwb != null) {
            // 创建一个可写入的工作表
            // Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
            WritableSheet ws = wwb.createSheet("sheet1", 0);
            try {
                ws.mergeCells(0, 3, 6, 3);//合并单元格
                //设置列宽
                ws.setColumnView(0, 9);
                ws.setColumnView(1, 9);
                ws.setColumnView(3, 15);
                ws.setColumnView(4, 12);
                ws.setColumnView(5, 12);
                ws.setColumnView(columns-3, 17);
            } catch (RowsExceededException e1) {

                e1.printStackTrace();
            } catch (WriteException e1) {

                e1.printStackTrace();
            }

            int questionIndex=0;
            int columnIndex=1;
            int k=0;
            List<QuestionReportBean> anserContentList = null;
            // 下面开始添加单元格
            for (int i = 0; i < rowNumber; i++) {
                if(i>5 && i<(rowNumber-2) ){
                    k=k+1;
                }
                
                for (int j = 0; j < columns; j++) {
                    // 这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
                    // Label labelC = new Label(j, i, "这是第"+(i+1)+"行，第"+(j+1)+"列");
                    Label labelC = null;
                    if (i == 0 && j == 3) {
                        labelC = new Label(j, i, "问卷报表");
                        WritableCellFormat cellFormat=new WritableCellFormat();
                        try {
                            cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                        } catch (WriteException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        labelC.setCellFormat(cellFormat);

                    }
                    if (i == 1) {
                        switch (j) {
                        case 1:
                            labelC = new Label(j, i, "问卷名称");
                            break;
                        case 2:
                            labelC = new Label(j, i, "广告商");
                            break;
                        case 3:
                            labelC = new Label(j, i, "订单编号");
                            break;
                        case 4:
                            labelC = new Label(j, i, "投放开始日期");
                            break;
                        case 5:
                            labelC = new Label(j, i, "投放结束日期");
                            break;

                        default:
                            break;
                        }
                    }
                    if (i == 2) {
                        switch (j) {
                        case 1:
                            labelC = new Label(j, i, questionReportTitle.getQuestionName());
                            break;
                        case 2:
                            labelC = new Label(j, i, questionReportTitle.getCustomerName());
                            break;
                        case 3:
                            labelC = new Label(j, i, questionReportTitle.getOrderCode());
                            break;
                        case 4:
                            labelC = new Label(j, i, questionReportTitle.getOrderStartTime().toString().substring(0, 10));
                            break;
                        case 5:
                            labelC = new Label(j, i, questionReportTitle.getOrderEndTime().toString().substring(0, 10));
                            break;

                        default:
                            break;
                        }
                    }
                    if (i == 4) {
                        
                        if(j==0){
                            labelC = new Label(j, i, "TVN号");
                        }else if(j==1){
                            labelC = new Label(j, i, "问卷积分");
                        }else if(j>=2&&j<(columns-3)){
//                                try {
//                                    ws.mergeCells(0, 6, 6, 6);
//                                } catch (RowsExceededException e) {
//                                    e.printStackTrace();
//                                } catch (WriteException e) {
//                                    e.printStackTrace();
//                                }
                            
                                QuestionReportBean questionTemp = questionList.get(questionIndex);
                                labelC = new Label(j, i, questionTemp.getQuestion());                               
                                if(columnIndex==questionTemp.getAnswerCount()){
                                    questionIndex++;
                                    columnIndex=1;
                                }else{
                                    columnIndex++;
                                }
                                
                                                         
                        }else if(j==(columns-3)){
                            labelC = new Label(j, i, "提交时间");
                        }else if(j==(columns-2)){
                            labelC = new Label(j, i, "所属城市");
                        }else if(j==columns-1){
                            labelC = new Label(j, i, "所属区域"); 
                            questionIndex=0;
                            columnIndex=1;
                        }
                        
                        
                    }
                    
                    
                    if(questionSubmitList!=null&&questionSubmitList.size()>0 && i>4 && i<(rowNumber-2)){                                                  
                        
                                String city="";
                                String street="";
                                LocationCacheBean location = LocationCache.getChiledLocation(Long.valueOf(questionSubmitList.get(k).getCity()));                             
                                if(location!=null){
                                    //获取市 
                                    city = location.getLocationName1();                              
                                    //获取区
                                    if(location.getLocationcode2()!=0){
                                        street = location.getLocationName2();
                                    }else{
                                        street = location.getLocationName3();
                                    }
                                }
                                
                                
                                if(j==0){
                                    labelC = new Label(j, i, questionSubmitList.get(k).getTvn());
                                }else if(j==1){
                                    labelC = new Label(j, i, questionReportTitle.getIntegral().toString());
                                }else if(j>=2&&j<(columns-3)){
                                    QuestionReportBean submitContent = questionSubmitList.get(k);                     
                                    List<QuestionReportBean> temp = generateQuestionReportService.getAnswerContent(submitContent.getSubmitId(), submitContent.getTvn());
                                    if(anserContentList==null){
                                        anserContentList = temp;
                                    }else{
                                        if(anserContentList.get(0).getSubmitId()==submitContent.getSubmitId()&&anserContentList.get(0).getTvn().equals(submitContent.getTvn())){
                                            
                                        }else{
                                            anserContentList = temp;
                                        }
                                    }
                                    
                                    
                                    QuestionReportBean questionTemp = questionList.get(questionIndex);
                                    Map<String, String> answerMap = questionTemp.getAnswerMap();
                                    String answer = answerMap.get(String.valueOf(columnIndex));
                                    String question = questionTemp.getQuestion();                                     
                                    if(columnIndex==questionTemp.getAnswerCount()){
                                        questionIndex++;
                                        columnIndex=1;
                                    }else{
                                        columnIndex++;
                                    }
                                    
                                    if(j==(columns-4)){
                                        questionIndex=0;
                                        columnIndex=1;
                                    }
                                    for(int k1=0;k1<anserContentList.size();k1++){
                                        if(question.equals(anserContentList.get(k1).getQuestion()) 
                                                && anserContentList.get(k1).getAnswer().equals(answer) 
                                                && !anserContentList.get(k1).isFill()){
                                            labelC = new Label(j, i, anserContentList.get(k1).getAnswer());
                                            anserContentList.get(k1).setFill(true);  
                                            break;
                                        }else{
                                            labelC = new Label(j, i, "");
                                        }

                                        
                                    }
                                     

                                    
                                      
                                }else if(j==(columns-3)){
                                    labelC = new Label(j, i, questionSubmitList.get(k).getSubmitTime().toString());
                                }else if(j==(columns-2)){
                                    labelC = new Label(j, i, city);
                                }else if(j==(columns-1)){
                                    labelC = new Label(j, i, street);
                                }
                                
                                
                                
                          
                 
                        
                    }else{
                        if (i == 6 && j == 0) {
                            try {
                              //合并单元格
                                ws.mergeCells(0, 6, 6, 6);
                            } catch (RowsExceededException e) {
                                e.printStackTrace();
                            } catch (WriteException e) {
                                e.printStackTrace();
                            }
                            labelC = new Label(j, i, "备注:该问卷没有用户进行提交");
                        }
                        
                    }
                    

                    try {
                        // 将生成的单元格添加到工作表中
                        if (labelC == null) {
                            labelC = new Label(j, i, "");
                        }
                        ws.addCell(labelC);
                    } catch (RowsExceededException e) {
                        e.printStackTrace();
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }

                }
            }

            try {
                // 从内存中写入文件中
                wwb.write();
                // 关闭资源，释放内存
                wwb.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
        
        //FTP文件复制转移
        FtpUtils ftp =new FtpUtils();
        ftp.connectionFtp();
        String remoteDirectoryReal=config.getValueByKey("questionReport.ftp");
        String fileName =questionReportTitle.getCustomerName() + "_"
        + questionReportTitle.getQuestionName() + "_" + questionReportTitle.getOrderCode() + "_"
        + df.format(dateTemp) + ".xls";
        ftp.uploadFileToRemote(filePath, remoteDirectoryReal, fileName);
        //删除本地文件
        File localFile =new File(filePath);
        if(localFile.exists()&&localFile.isFile()){
            localFile.delete();
        }
        
    }
	
	
	
	
	public void exportReport2(QuestionReportBean questionReportTitle) {   
	    Date dateTemp = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        String filePath = ".././" + questionReportTitle.getCustomerName() + "_"
                + questionReportTitle.getQuestionName() + "_" + questionReportTitle.getOrderCode() + "_"
                + df.format(dateTemp) + ".xls";
	    List<QuestionReportBean> questionReportContentList = generateQuestionReportService.getQuestionReportContent(questionReportTitle.getOrderId().intValue(), questionReportTitle.getQuestionId().intValue());
        WritableWorkbook wwb = null;
        int rowNumber = 5+questionReportContentList.size()+2;
        try {         
            // 首先要使用Workbook类的工厂方法创建一个可写入的工作薄(Workbook)对象         
            wwb = Workbook.createWorkbook(new File(filePath));
//            WritableSheet dataSheet = wwb.createSheet("加入页眉", 0);
//            ExcelUtils.setHeader(dataSheet, "chb", "2007-03-06", "第1页,共3页");
//            
//            WritableFont   wf   =   new   WritableFont(WritableFont.TIMES,18,WritableFont.BOLD,true); 
//            WritableCellFormat   wcf   =   new   WritableCellFormat(wf); 
//            wcf.setBorder(jxl.format.Border.ALL,jxl.format.BorderLineStyle.THIN);

        } catch (IOException e) {
            e.printStackTrace();
        } 
//        catch (WriteException ex) {
//            ex.printStackTrace();
//        }

        
        if (wwb != null) {
            // 创建一个可写入的工作表
            // Workbook的createSheet方法有两个参数，第一个是工作表的名称，第二个是工作表在工作薄中的位置
            WritableSheet ws = wwb.createSheet("sheet1", 0);
            try {
                ws.mergeCells(0, 3, 6, 3);//合并单元格
                //设置列宽
                ws.setColumnView(0, 9);
                ws.setColumnView(1, 9);
                ws.setColumnView(2, 14);
                ws.setColumnView(3, 15);
                ws.setColumnView(4, 17);
                ws.setColumnView(5, 12);
                ws.setColumnView(6, 14);
            } catch (RowsExceededException e1) {

                e1.printStackTrace();
            } catch (WriteException e1) {

                e1.printStackTrace();
            }

            // 下面开始添加单元格
            for (int i = 0; i < rowNumber; i++) {
                for (int j = 0; j < 7; j++) {
                    // 这里需要注意的是，在Excel中，第一个参数表示列，第二个表示行
                    // Label labelC = new Label(j, i, "这是第"+(i+1)+"行，第"+(j+1)+"列");
                    Label labelC = null;
                    if (i == 0 && j == 3) {
                        labelC = new Label(j, i, "问卷报表");
                        WritableCellFormat cellFormat=new WritableCellFormat();
                        try {
                            cellFormat.setAlignment(jxl.format.Alignment.CENTRE);
                        } catch (WriteException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        labelC.setCellFormat(cellFormat);

                    }
                    if (i == 1) {
                        switch (j) {
                        case 1:
                            labelC = new Label(j, i, "问卷名称");
                            break;
                        case 2:
                            labelC = new Label(j, i, "广告商");
                            break;
                        case 3:
                            labelC = new Label(j, i, "订单编号");
                            break;
                        case 4:
                            labelC = new Label(j, i, "投放开始日期");
                            break;
                        case 5:
                            labelC = new Label(j, i, "投放结束日期");
                            break;

                        default:
                            break;
                        }
                    }
                    if (i == 2) {
                        switch (j) {
                        case 1:
                            labelC = new Label(j, i, questionReportTitle.getQuestionName());
                            break;
                        case 2:
                            labelC = new Label(j, i, questionReportTitle.getCustomerName());
                            break;
                        case 3:
                            labelC = new Label(j, i, questionReportTitle.getOrderCode());
                            break;
                        case 4:
                            labelC = new Label(j, i, questionReportTitle.getOrderStartTime().toString().substring(0, 10));
                            break;
                        case 5:
                            labelC = new Label(j, i, questionReportTitle.getOrderEndTime().toString().substring(0, 10));
                            break;

                        default:
                            break;
                        }
                    }
                    if (i == 4) {
                        switch (j) {
                        case 0:
                            labelC = new Label(j, i, "TVN号");
                            break;
                        case 1:
                            labelC = new Label(j, i, "问卷积分");
                            break;
                        case 2:
                            labelC = new Label(j, i, "问题");
                            break;
                        case 3:
                            labelC = new Label(j, i, "答案");
                            break;
                        case 4:
                            labelC = new Label(j, i, "提交时间");
                            break;
                        case 5:
                            labelC = new Label(j, i, "所属城市");
                            break;
                        case 6:
                            labelC = new Label(j, i, "所属区域");
                            break;

                        default:
                            break;
                        }
                    }
                    if(questionReportContentList!=null&&questionReportContentList.size()>0){                       
                        for(int k=0;k<questionReportContentList.size();k++){
                            if(i==4+(k+1)){
                                switch(j){ 
                                case 0:
                                    labelC = new Label(j, i, questionReportContentList.get(k).getTvn());
                                    break;                                                      
                                case 1:
                                    labelC = new Label(j, i,questionReportTitle.getIntegral().toString() );
                                    break;
                                case 2:
                                    labelC = new Label(j, i,questionReportContentList.get(k).getQuestion() );
                                    break;              
                                case 3:      
                                    labelC = new Label(j, i,questionReportContentList.get(k).getAnswer() );
                                    break;
                                case 4:                                                
                                    labelC = new Label(j, i,questionReportContentList.get(k).getSubmitTime().toString() );
                                    break;
                                case 5:             
                                    labelC = new Label(j, i, questionReportContentList.get(k).getCity());
                                    break;
                                case 6:             
                                    labelC = new Label(j, i, questionReportContentList.get(k).getStreet());
                                    break;
                                
                                
                                default:
                                    break;
                                }
                            }
                        }
                        
                    }else{
                        if (i == 6 && j == 0) {
                            try {
                              //合并单元格
                                ws.mergeCells(0, 6, 6, 6);
                            } catch (RowsExceededException e) {
                                e.printStackTrace();
                            } catch (WriteException e) {
                                e.printStackTrace();
                            }
                            labelC = new Label(j, i, "备注:该问卷没有用户进行提交");
                        }
                        
                    }
                    

                    try {
                        // 将生成的单元格添加到工作表中
                        if (labelC == null) {
                            labelC = new Label(j, i, "");
                        }
                        ws.addCell(labelC);
                    } catch (RowsExceededException e) {
                        e.printStackTrace();
                    } catch (WriteException e) {
                        e.printStackTrace();
                    }

                }
            }

            try {
                // 从内存中写入文件中
                wwb.write();
                // 关闭资源，释放内存
                wwb.close();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (WriteException e) {
                e.printStackTrace();
            }
        }
        
        //FTP文件复制转移
        FtpUtils ftp =new FtpUtils();
        ftp.connectionFtp();
        String remoteDirectoryReal=config.getValueByKey("questionReport.ftp");
        String fileName =questionReportTitle.getCustomerName() + "_"
        + questionReportTitle.getQuestionName() + "_" + questionReportTitle.getOrderCode() + "_"
        + df.format(dateTemp) + ".xls";
        ftp.uploadFileToRemote(filePath, remoteDirectoryReal, fileName);
        //删除本地文件
        File localFile =new File(filePath);
        if(localFile.exists()&&localFile.isFile()){
            localFile.delete();
        }
        
    }

}
