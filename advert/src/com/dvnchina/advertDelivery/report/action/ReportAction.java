package com.dvnchina.advertDelivery.report.action;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.tools.zip.ZipEntry;
import org.apache.tools.zip.ZipOutputStream;

import com.dvnchina.advertDelivery.action.BaseActionSupport;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.report.bean.AdvertReport;
import com.dvnchina.advertDelivery.report.bean.QuestionReport;
import com.dvnchina.advertDelivery.report.service.ReportService;
import com.dvnchina.advertDelivery.utils.DateUtil;

/**
 * @ClassName ReportAction
 * @Description 报表处理Action
 * @author panxincheng
 * @date 2013-10-28 
 */
public class ReportAction extends BaseActionSupport<Object> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2448587577434390352L;
	private Logger logger = Logger.getLogger(ReportAction.class);
	
	private ReportService reportService;
	private PageBeanDB page = null;
	private AdvertReport report; 
	private QuestionReport qreport;
	private Integer reportType;
	private String reportFileName;
	
	/**
	 * 分页查询日报表信息
	 */
	public String queryDayReportList(){
		try{
			if(page == null){
				page = new PageBeanDB();
			}
//			if (report.getPushDateStart()!=null && report.getPushDateStart().length()==10)
//			{
//				report.setPushDateStart(report.getPushDateStart()+" 00:00:00");
//			}
//			if (report.getPushDateEnd()!=null && report.getPushDateEnd().length()<=10)
//			{
//				report.setPushDateEnd(report.getPushDateEnd()+" 23:59:59");
//			}
			if (report.getReportType()==0)
			{
				if (report.getPushDateStart()==null)
				{
					report.setPushDateStart(DateUtil.getCurrentDateStr1());
				}
			}
			page = reportService.queryDayReportList(report, page.getPageNo(), page.getPageSize());
			
		}catch(Exception e){
			logger.error("查询报表信息出现异常",e);
		}
		return SUCCESS;
	}

	public String exportReport() {
		try {
            File f = new File(".././DayReport.zip");
            f.createNewFile();
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
            //生成临时文件名
            String tmpFileName = ".././report.xls";
            if (report == null || report.getReportType() == 0) {
            	reportFileName = "timeReport"  + exportDayReport(tmpFileName);
    		} else if (report.getReportType() == 1) {
    			reportFileName = "dayReport"  + exportWeekReport(tmpFileName);
			} else {
				reportFileName = "monthReport"  + exportMonthReport(tmpFileName);
			}
            reportFileName = reportFileName.replace("-", "");
            out.putNextEntry(new ZipEntry(reportFileName+".xls")); 
            out.setEncoding("GBK");
            //InputStream in =new ByteArrayInputStream(xml.getBytes("UTF-8"));
            InputStream in = new FileInputStream(tmpFileName);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
            out.flush();
            out.close();
		} catch (Exception e) {
			logger.error("导出报表信息出现异常",e);
		}

        return SUCCESS;
	}
	
	@SuppressWarnings("unchecked")
	private String exportDayReport(String fileName) {
		String retDate = "";
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle cellStyle2 = workbook.createCellStyle();
		cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFSheet sheet = null;
        try{
        	page = reportService.queryDayReportList(report, -1, -1);
        	List<AdvertReport> reportList = page.getDataList();
        	if (reportList == null || reportList.size() <= 0) {
				return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			} else {
				retDate = reportList.get(0).getPushDate().split(" ")[0];
			}
        	//广告位工作表名称，每个广告位一个
        	String sheetName = "";
        	//正式记录起始下标
        	int startIndex = 5;
        	//表中行下标
        	int sheetRowNum = 0;
        	String lastPushDate = "";
//        	int tmpSheetRowNum = 0;//记录相同投放时间的第一个记录的下标
        	for (AdvertReport aReport : reportList) {
        		if (!sheetName.equals(aReport.getPositionCode())) {
        			sheetName = aReport.getPositionCode();
        			if (sheet != null) {
        				//投放结束时间
        				sheet.getRow(1).getCell(1).setCellValue(
        						new HSSFRichTextString(sheet.getRow(1).getCell(1).getRichStringCellValue().getString()
        								+ " -- " + lastPushDate));
        				//合并投放时间相同的行（n,m,1,1）
        				int rowNum = sheet.getPhysicalNumberOfRows();
        				int lastIndex = startIndex;
        				String lastDate = "";
        				for (int i = startIndex; i < rowNum; i++) {
        					String curDate = sheet.getRow(i).getCell(0).getRichStringCellValue().toString();
        					if (!lastDate.equals(curDate)) {
		   						if (i-1 > lastIndex) {
        							sheet.addMergedRegion(new CellRangeAddress(lastIndex, i-1, 0, 0));
								}
		   						lastIndex = i;
							} else if (i == rowNum-1) {
        						//最后一个
        						if (i > lastIndex) {
        							sheet.addMergedRegion(new CellRangeAddress(lastIndex, i, 0, 0));
								}
							}
        					lastDate = curDate;
						}
					}
        			sheet = workbook.createSheet(sheetName);
        			createTitle(sheet, cellStyle, aReport);
        			//投放开始时间
        			sheet.getRow(1).getCell(1).setCellValue(new HSSFRichTextString(aReport.getPushDate()));
        			sheetRowNum = startIndex;
//        			tmpSheetRowNum = sheetRowNum;
				}
        		//填充报表数据
    			createRow(sheet.createRow(sheetRowNum), cellStyle2, aReport);
    			//合并投放时间相同的行（n,m,1,1）
//    			if (lastPushDate.equals(aReport.getPushDate())) {
//    				if (sheetRowNum > tmpSheetRowNum) {
//    					sheet.addMergedRegion(new CellRangeAddress(tmpSheetRowNum, sheetRowNum, 0, 0));
//    					System.out.println(tmpSheetRowNum+" "+sheetRowNum);
//					}
//				}
//    			tmpSheetRowNum = sheetRowNum;
    			lastPushDate = aReport.getPushDate();
    			sheetRowNum++;
			}
			if (sheet != null) {
				//投放结束时间
				sheet.getRow(1).getCell(1).setCellValue(
						new HSSFRichTextString(sheet.getRow(1).getCell(1).getRichStringCellValue().getString()
								+ " -- " + lastPushDate));
				//合并投放时间相同的行（n,m,1,1）
				int rowNum = sheet.getPhysicalNumberOfRows();
				int lastIndex = startIndex;
				String lastDate = "";
				System.out.println(rowNum);
				for (int i = startIndex; i < rowNum; i++) {
					String curDate = sheet.getRow(i).getCell(0).getRichStringCellValue().toString();
					if (!lastDate.equals(curDate)) {
   						if (i-1 > lastIndex) {
							sheet.addMergedRegion(new CellRangeAddress(lastIndex, i-1, 0, 0));
							lastIndex = i;
						}
					} else if (i == rowNum-1) {
						//最后一个
						if (i > lastIndex) {
							sheet.addMergedRegion(new CellRangeAddress(lastIndex, i, 0, 0));
						}
					}
					lastDate = curDate;
				}
			}
        	
    		//生成文件
        	logger.info("生成文件 " +fileName);
    		FileOutputStream fOut;
			fOut = new FileOutputStream(fileName);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();

        } catch (Exception e) {
        	logger.error("生成日报表文件出现异常",e);
        }
        return retDate;
	}
	
	private String exportWeekReport(String fileName) {
	//	return exportDayReport(fileName);
		
		String retDate = "";
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle cellStyle2 = workbook.createCellStyle();
		cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFSheet sheet = null;
        try{
        	page = reportService.queryDayReportList(report, -1, -1);
        	List<AdvertReport> reportList = page.getDataList();
        	if (reportList == null || reportList.size() <= 0) {
				return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			} else {
				retDate = reportList.get(0).getPushDate().split(" ")[0];
			}
        	//广告位工作表名称，每个广告位一个
        	String sheetName = "";
        	//正式记录起始下标
        	int startIndex = 5;
        	//表中行下标
        	int sheetRowNum = 0;
        	String lastPushDate = "";
//        	int tmpSheetRowNum = 0;//记录相同投放时间的第一个记录的下标
        	for (AdvertReport aReport : reportList) {
        		if (!sheetName.equals(aReport.getPositionCode())) {
        			sheetName = aReport.getPositionCode();
        			if (sheet != null) {
        				//投放结束时间
        				sheet.getRow(1).getCell(1).setCellValue(
        						new HSSFRichTextString(sheet.getRow(1).getCell(1).getRichStringCellValue().getString()
        								+ " -- " + lastPushDate));
        				//合并投放时间相同的行（n,m,1,1）
        				int rowNum = sheet.getPhysicalNumberOfRows();
        				int lastIndex = startIndex;
        				String lastDate = "";
        				for (int i = startIndex; i < rowNum; i++) {
        					String curDate = sheet.getRow(i).getCell(0).getRichStringCellValue().toString();
        					if (!lastDate.equals(curDate)) {
		   						if (i-1 > lastIndex) {
        							sheet.addMergedRegion(new CellRangeAddress(lastIndex, i-1, 0, 0));
        							lastIndex = i;
								}
							} else if (i == rowNum-1) {
        						//最后一个
        						if (i > lastIndex) {
        							sheet.addMergedRegion(new CellRangeAddress(lastIndex, i, 0, 0));
								}
							}
        					lastDate = curDate;
						}
					}
        			sheet = workbook.createSheet(sheetName);
        			createTitle(sheet, cellStyle, aReport);
        			//投放开始时间
        			sheet.getRow(1).getCell(1).setCellValue(new HSSFRichTextString(aReport.getPushDate()));
        			sheetRowNum = startIndex;
//        			tmpSheetRowNum = sheetRowNum;
				}
        		//填充报表数据
    			createRow(sheet.createRow(sheetRowNum), cellStyle2, aReport);
    			//合并投放时间相同的行（n,m,1,1）
//    			if (lastPushDate.equals(aReport.getPushDate())) {
//    				if (sheetRowNum > tmpSheetRowNum) {
//    					sheet.addMergedRegion(new CellRangeAddress(tmpSheetRowNum, sheetRowNum, 0, 0));
//    					System.out.println(tmpSheetRowNum+" "+sheetRowNum);
//					}
//				}
//    			tmpSheetRowNum = sheetRowNum;
    			lastPushDate = aReport.getPushDate();
    			sheetRowNum++;
			}
			if (sheet != null) {
				//投放结束时间
				sheet.getRow(1).getCell(1).setCellValue(
						new HSSFRichTextString(sheet.getRow(1).getCell(1).getRichStringCellValue().getString()
								+ " -- " + lastPushDate));
				//合并投放时间相同的行（n,m,1,1）
				int rowNum = sheet.getPhysicalNumberOfRows();
				int lastIndex = startIndex;
				String lastDate = "";
				System.out.println(rowNum);
				for (int i = startIndex; i < rowNum; i++) {
					String curDate = sheet.getRow(i).getCell(0).getRichStringCellValue().toString();
					if (!lastDate.equals(curDate)) {
   						if (i-1 > lastIndex) {
							sheet.addMergedRegion(new CellRangeAddress(lastIndex, i-1, 0, 0));
							lastIndex = i;
						}
					} else if (i == rowNum-1) {
						//最后一个
						if (i > lastIndex) {
							sheet.addMergedRegion(new CellRangeAddress(lastIndex, i, 0, 0));
						}
					}
					lastDate = curDate;
				}
			}
        	
    		//生成文件
        	logger.info("生成文件 " +fileName);
    		FileOutputStream fOut;
			fOut = new FileOutputStream(fileName);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();

        } catch (Exception e) {
        	logger.error("生成日报表文件出现异常",e);
        }
        return retDate;
	}
	private String exportMonthReport(String fileName) {
		//return exportDayReport(fileName);
		String retDate = "";
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle cellStyle2 = workbook.createCellStyle();
		cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFSheet sheet = null;
        try{
        	page = reportService.queryDayReportList(report, -1, -1);
        	List<AdvertReport> reportList = page.getDataList();
        	if (reportList == null || reportList.size() <= 0) {
				return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
			} else {
				retDate = reportList.get(0).getPushDate().split(" ")[0];
			}
        	//广告位工作表名称，每个广告位一个
        	String sheetName = "";
        	//正式记录起始下标
        	int startIndex = 5;
        	//表中行下标
        	int sheetRowNum = 0;
        	String lastPushDate = "";
//        	int tmpSheetRowNum = 0;//记录相同投放时间的第一个记录的下标
        	for (AdvertReport aReport : reportList) {
        		if (!sheetName.equals(aReport.getPositionCode())) {
        			sheetName = aReport.getPositionCode();
        			if (sheet != null) {
        				//投放结束时间
        				sheet.getRow(1).getCell(1).setCellValue(
        						new HSSFRichTextString(sheet.getRow(1).getCell(1).getRichStringCellValue().getString()
        								+ " -- " + lastPushDate));
        				//合并投放时间相同的行（n,m,1,1）
        				int rowNum = sheet.getPhysicalNumberOfRows();
        				int lastIndex = startIndex;
        				String lastDate = "";
        				for (int i = startIndex; i < rowNum; i++) {
        					String curDate = sheet.getRow(i).getCell(0).getRichStringCellValue().toString();
        					if (!lastDate.equals(curDate)) {
		   						if (i-1 > lastIndex) {
        							sheet.addMergedRegion(new CellRangeAddress(lastIndex, i-1, 0, 0));
        							lastIndex = i;
								}
							} else if (i == rowNum-1) {
        						//最后一个
        						if (i > lastIndex) {
        							sheet.addMergedRegion(new CellRangeAddress(lastIndex, i, 0, 0));
								}
							}
        					lastDate = curDate;
						}
					}
        			sheet = workbook.createSheet(sheetName);
        			createTitle(sheet, cellStyle, aReport);
        			//投放开始时间
        			sheet.getRow(1).getCell(1).setCellValue(new HSSFRichTextString(aReport.getPushDate()));
        			sheetRowNum = startIndex;
//        			tmpSheetRowNum = sheetRowNum;
				}
        		//填充报表数据
    			createRow(sheet.createRow(sheetRowNum), cellStyle2, aReport);
    			//合并投放时间相同的行（n,m,1,1）
//    			if (lastPushDate.equals(aReport.getPushDate())) {
//    				if (sheetRowNum > tmpSheetRowNum) {
//    					sheet.addMergedRegion(new CellRangeAddress(tmpSheetRowNum, sheetRowNum, 0, 0));
//    					System.out.println(tmpSheetRowNum+" "+sheetRowNum);
//					}
//				}
//    			tmpSheetRowNum = sheetRowNum;
    			lastPushDate = aReport.getPushDate();
    			sheetRowNum++;
			}
			if (sheet != null) {
				//投放结束时间
				sheet.getRow(1).getCell(1).setCellValue(
						new HSSFRichTextString(sheet.getRow(1).getCell(1).getRichStringCellValue().getString()
								+ " -- " + lastPushDate));
				//合并投放时间相同的行（n,m,1,1）
				int rowNum = sheet.getPhysicalNumberOfRows();
				int lastIndex = startIndex;
				String lastDate = "";
				System.out.println(rowNum);
				for (int i = startIndex; i < rowNum; i++) {
					String curDate = sheet.getRow(i).getCell(0).getRichStringCellValue().toString();
					if (!lastDate.equals(curDate)) {
   						if (i-1 > lastIndex) {
							sheet.addMergedRegion(new CellRangeAddress(lastIndex, i-1, 0, 0));
							lastIndex = i;
						}
					} else if (i == rowNum-1) {
						//最后一个
						if (i > lastIndex) {
							sheet.addMergedRegion(new CellRangeAddress(lastIndex, i, 0, 0));
						}
					}
					lastDate = curDate;
				}
			}
        	
    		//生成文件
        	logger.info("生成文件 " +fileName);
    		FileOutputStream fOut;
			fOut = new FileOutputStream(fileName);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();

        } catch (Exception e) {
        	logger.error("生成日报表文件出现异常",e);
        }
        return retDate;
	}
	
	public InputStream getInputStreamForBatch() {
        InputStream is = null;
        File tempfile =null;
        
            try {
                tempfile = new File(".././DayReport.zip");     
                is = new FileInputStream(tempfile);
            } catch (Exception e) {
                log.error("", e);
            } 
//            finally{                    
//                if(tempfile.exists()){
//                    tempfile.delete();
//                    if(tempfile.exists()){
//                        System.out.println("------删除临时文件失败-------");
//                    }else{
//                        System.out.println("------删除打包产生的临时文件------");
//                    }
//                }
//            }
         
        if(is == null){
            is = new ByteArrayInputStream("".getBytes());
        }

        return is;
    }
    public String getFileNameForBatch() {
        Date dateTemp = new Date();
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
//        String str = "dayReport"+df.format(dateTemp);
        String str = reportFileName;
        try {
            str = URLEncoder.encode(str, "UTF-8");
        } catch (Exception e) {
           // log.error("", e);
            str = "dayReport"+df.format(dateTemp);
        }

        return str + ".zip";
    }
	
	//创建表头公共部分
	private void createTitle(HSSFSheet sheet, HSSFCellStyle cellStyle, AdvertReport advertReport) {
		String title = "广告投放数据表";
        if (report == null || report.getReportType() == 0) {
        	title += "（分时报表）";
		} else if (report.getReportType() == 1) {
			title += "（日报）";
		} else {
			title += "（月报）";
		}
		for (int i = 0; i < 5; i++) {
			HSSFRow row = sheet.createRow(i);
			for (int j = 0; j < 7; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new HSSFRichTextString(""));
			}
		}
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 3200);
		sheet.setColumnWidth(6, 2800);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 6));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 6));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 6));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 6));
		sheet.getRow(0).getCell(0).setCellStyle(cellStyle);
		sheet.getRow(0).getCell(0).setCellValue(new HSSFRichTextString(title));
		sheet.getRow(1).getCell(0).setCellValue(new HSSFRichTextString("投放起始时间"));
		sheet.getRow(2).getCell(0).setCellValue(new HSSFRichTextString("广告位名称"));
		sheet.getRow(2).getCell(1).setCellValue(new HSSFRichTextString(advertReport.getPositionName()));
		sheet.getRow(3).getCell(0).setCellValue(new HSSFRichTextString("广告位ID"));
		sheet.getRow(3).getCell(1).setCellValue(new HSSFRichTextString(advertReport.getPositionCode()));
		sheet.getRow(4).getCell(0).setCellValue(new HSSFRichTextString("投放时间"));
		sheet.getRow(4).getCell(1).setCellValue(new HSSFRichTextString("素材名称"));
		//sheet.getRow(4).getCell(2).setCellValue(new HSSFRichTextString("投放次数"));
		sheet.getRow(4).getCell(2).setCellValue(new HSSFRichTextString("曝光次数"));
		sheet.getRow(4).getCell(3).setCellValue(new HSSFRichTextString("到达次数"));
		sheet.getRow(4).getCell(4).setCellValue(new HSSFRichTextString("有效次数"));
		sheet.getRow(4).getCell(5).setCellValue(new HSSFRichTextString("素材分类"));
		sheet.getRow(4).getCell(6).setCellValue(new HSSFRichTextString("广告商名称"));
	}
	//填充表格主体数据
	private void createRow(HSSFRow row, HSSFCellStyle cellStyle, AdvertReport advertReport){
		HSSFCell cell = null;
		for (int i = 0; i < 7; i++) {
			cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			switch (i) {
			case 0:
				cell.setCellValue(new HSSFRichTextString(advertReport.getPushDate()));
				break;
			case 1:
				cell.setCellValue(new HSSFRichTextString(advertReport.getResourceName()));
				break;
			case 2:
				//cell.setCellValue(new HSSFRichTextString(advertReport.getReceiveCount() + ""));
				if(advertReport.getReceiveCount()!=null){
					cell.setCellValue(advertReport.getReceiveCount());
				}else{
					cell.setCellValue(0);
				}
				
				break;
			case 3:
				//cell.setCellValue(new HSSFRichTextString(advertReport.getReacheCount() + ""));
				if(advertReport.getReacheCount()!=null){
					cell.setCellValue(advertReport.getReacheCount());
				}else{
					cell.setCellValue(0);
				}
				
				break;
			case 4:
				//cell.setCellValue(new HSSFRichTextString(advertReport.getEffCount() + ""));
				if(advertReport.getEffCount() != null){
					cell.setCellValue(advertReport.getEffCount());
				}else{
					cell.setCellValue(0);
				}
				
				break;	
				
			case 5:
				cell.setCellValue(new HSSFRichTextString(advertReport.getCategoryName()));
				break;
			case 6:
				cell.setCellValue(new HSSFRichTextString(advertReport.getAdvertisersName()));
				break;
			}
		}
	}
	
	
	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public PageBeanDB getPage() {
		return page;
	}

	public void setPage(PageBeanDB page) {
		this.page = page;
	}

	public AdvertReport getReport() {
		return report;
	}

	public void setReport(AdvertReport report) {
		this.report = report;
	}

	public Integer getReportType() {
		return reportType;
	}

	public void setReportType(Integer reportType) {
		this.reportType = reportType;
	}

	public static void main(String[] args) {
		//产生的Excel文件的名称
		String xlsFile="test.xls";
		//产生工作簿对象
		HSSFWorkbook workbook = new HSSFWorkbook();
		//产生工作表对象
		HSSFSheet sheet = workbook.createSheet();
		//产生单元格样式
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		//设置水平居中
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		//设置第一个工作表的名称为firstSheet
		workbook.setSheetName(0,"广告位1");
		//产生5行6列的表格
		for (int i = 0; i < 5; i++) {
			HSSFRow row = sheet.createRow(i);
			for (int j = 0; j < 6; j++) {
				//产生一个单元格
				HSSFCell cell = row.createCell(j);
				//设置单元格内容为字符串型
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				//往第一个单元格中写入信息
				cell.setCellValue(new HSSFRichTextString(""));
				
			}
		}
		//设置列宽
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(5, 2800);
		//合并单元格，合并（起始行, 终止行, 起始列, 终止列）
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 5));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 5));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 5));
		//表头
		sheet.getRow(0).getCell(0).setCellStyle(cellStyle);
		sheet.getRow(0).getCell(0).setCellValue(new HSSFRichTextString("广告投放数据表（日报）"));
		
		sheet.getRow(1).getCell(0).setCellValue(new HSSFRichTextString("投放起始时间（日期）"));
		sheet.getRow(2).getCell(0).setCellValue(new HSSFRichTextString("广告位名称"));
		sheet.getRow(3).getCell(0).setCellValue(new HSSFRichTextString("广告位ID"));
		sheet.getRow(4).getCell(0).setCellValue(new HSSFRichTextString("投放时间"));
		sheet.getRow(4).getCell(1).setCellValue(new HSSFRichTextString("素材名称"));
		sheet.getRow(4).getCell(2).setCellValue(new HSSFRichTextString("投放次数"));
		sheet.getRow(4).getCell(3).setCellValue(new HSSFRichTextString("展示次数"));
		sheet.getRow(4).getCell(4).setCellValue(new HSSFRichTextString("素材分类"));
		sheet.getRow(4).getCell(5).setCellValue(new HSSFRichTextString("广告商名称"));
		
		
		//生成文件
		FileOutputStream fOut;
		try {
			fOut = new FileOutputStream(xlsFile);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();
			System.out.println("文件生成...");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//读取Excel文件例子（无法读取后缀为xlsx的Excel文件）
	public static void readExcel(String[] args) {
		//Excel文件名称
		String xlsFile="D:/广告投放数据表.xls";
		try {
			HSSFWorkbook readWorkBook = new HSSFWorkbook(new FileInputStream(new File(xlsFile))); //产生工作簿对象
			HSSFSheet readSheet= readWorkBook.getSheet("日报");
			HSSFRow readRow =readSheet.getRow(0);
			System.out.println(readRow.getPhysicalNumberOfCells());
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public QuestionReport getQreport() {
		return qreport;
	}

	public void setQreport(QuestionReport qreport) {
		this.qreport = qreport;
	}

	/**
	 * 分页查询问卷报表信息
	 */
	public String queryQuestionReportList(){
		try{
			if(page == null){
				page = new PageBeanDB();
			}
			page = reportService.queryQuestionReportList(qreport, page.getPageNo(), page.getPageSize());
		}catch(Exception e){
			logger.error("查询报表信息出现异常",e);
		}
		return SUCCESS;
	}
	public String exportQuestionReport() {
		try {
            File f = new File(".././DayReport.zip");
            f.createNewFile();
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(f));
            //生成临时文件名
            String tmpFileName = ".././report.xls";
           	exporQuestionReport(tmpFileName);    		
            out.putNextEntry(new ZipEntry("report.xls")); 
            out.setEncoding("GBK");
            //InputStream in =new ByteArrayInputStream(xml.getBytes("UTF-8"));
            InputStream in = new FileInputStream(tmpFileName);
            int b;
            while ((b = in.read()) != -1) {
                out.write(b);
            }
            in.close();
            out.flush();
            out.close();
		} catch (Exception e) {
			logger.error("导出报表信息出现异常",e);
		}

        return SUCCESS;
	}
	@SuppressWarnings("unchecked")
	private void exporQuestionReport(String fileName) {
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		cellStyle.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		cellStyle.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cellStyle.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cellStyle.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		HSSFCellStyle cellStyle2 = workbook.createCellStyle();
		cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		cellStyle2.setBorderBottom(HSSFCellStyle.BORDER_THIN); //下边框
		cellStyle2.setBorderLeft(HSSFCellStyle.BORDER_THIN);//左边框
		cellStyle2.setBorderTop(HSSFCellStyle.BORDER_THIN);//上边框
		cellStyle2.setBorderRight(HSSFCellStyle.BORDER_THIN);//右边框
		HSSFSheet sheet = null;
        try{
        	page = reportService.queryQuestionReportList(qreport, -1, -1);
        	List<QuestionReport> reportList = page.getDataList();
        	if (reportList == null || reportList.size() <= 0) {
				return;
			}
        	//广告位工作表名称，每个广告位一个
        	String sheetName = "";
        	//正式记录起始下标
        	int startIndex = 5;
        	//表中行下标
        	int sheetRowNum = 0;
        	String lastPushDate = "";
//        	int tmpSheetRowNum = 0;//记录相同投放时间的第一个记录的下标
        	for (QuestionReport aReport : reportList) {
        		if (sheetName.equals("")) {
        			sheetName = "02132";        			
        			sheet = workbook.createSheet(sheetName);
        			createQuestionTitle(sheet, cellStyle, aReport);
        			//投放开始时间
        			//sheet.getRow(1).getCell(1).setCellValue(new HSSFRichTextString(aReport.getPushDate()));
        			sheetRowNum = 2;
//        			tmpSheetRowNum = sheetRowNum;
				}
        		//填充报表数据
    			createQuestionRow(sheet.createRow(sheetRowNum), cellStyle2, aReport);
    			//合并投放时间相同的行（n,m,1,1）
//    			if (lastPushDate.equals(aReport.getPushDate())) {
//    				if (sheetRowNum > tmpSheetRowNum) {
//    					sheet.addMergedRegion(new CellRangeAddress(tmpSheetRowNum, sheetRowNum, 0, 0));
//    					System.out.println(tmpSheetRowNum+" "+sheetRowNum);
//					}
//				}
//    			tmpSheetRowNum = sheetRowNum;
    			lastPushDate = aReport.getPushDate();
    			sheetRowNum++;
			}
    		//生成文件
        	logger.info("生成文件 " +fileName);
    		FileOutputStream fOut;
			fOut = new FileOutputStream(fileName);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();

        } catch (Exception e) {
        	logger.error("生成日报表文件出现异常",e);
        }
	}
	//创建表头公共部分
	private void createQuestionTitle(HSSFSheet sheet, HSSFCellStyle cellStyle, QuestionReport advertReport) {
		String title = "调查问卷投放数据表";
       
		for (int i = 0; i < 2; i++) {
			HSSFRow row = sheet.createRow(i);
			for (int j = 0; j < 5; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new HSSFRichTextString(""));
				cell.setCellStyle(cellStyle);
			}
		}
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 3200);
		sheet.setColumnWidth(2, 3200);
		sheet.setColumnWidth(3, 3200);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		//sheet.getRow(0).getCell(0).setCellStyle(cellStyle);
		sheet.getRow(0).getCell(0).setCellValue(new HSSFRichTextString(title));
		sheet.getRow(1).getCell(0).setCellValue(new HSSFRichTextString("广告商名称"));
		sheet.getRow(1).getCell(1).setCellValue(new HSSFRichTextString("问卷名称"));
		sheet.getRow(1).getCell(2).setCellValue(new HSSFRichTextString("投放开始时间"));
		sheet.getRow(1).getCell(3).setCellValue(new HSSFRichTextString("投放结束时间"));
		sheet.getRow(1).getCell(4).setCellValue(new HSSFRichTextString("投放次数"));
		
//		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 5));
//		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 5));
//		sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 5));
//		
//		sheet.getRow(1).getCell(0).setCellValue(new HSSFRichTextString("投放起始时间"));
//		sheet.getRow(2).getCell(0).setCellValue(new HSSFRichTextString("广告位名称"));
//		sheet.getRow(2).getCell(1).setCellValue(new HSSFRichTextString(advertReport.getPositionName()));
//		sheet.getRow(3).getCell(0).setCellValue(new HSSFRichTextString("广告位ID"));
//		sheet.getRow(3).getCell(1).setCellValue(new HSSFRichTextString(advertReport.getPositionCode()));
//		sheet.getRow(4).getCell(0).setCellValue(new HSSFRichTextString("投放时间"));
//		sheet.getRow(4).getCell(1).setCellValue(new HSSFRichTextString("素材名称"));
//		sheet.getRow(4).getCell(2).setCellValue(new HSSFRichTextString("投放次数"));
//		sheet.getRow(4).getCell(3).setCellValue(new HSSFRichTextString("展示次数"));
//		sheet.getRow(4).getCell(4).setCellValue(new HSSFRichTextString("素材分类"));
		
	}
	//填充表格主体数据
	private void createQuestionRow(HSSFRow row, HSSFCellStyle cellStyle, QuestionReport advertReport){
		HSSFCell cell = null;
		for (int i = 0; i < 5; i++) {
			cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			switch (i) {
			case 0:
				cell.setCellValue(new HSSFRichTextString(advertReport.getAdvertisersName()));
				break;
			case 1:
				cell.setCellValue(new HSSFRichTextString(advertReport.getResourceName()));
				break;
			case 2:
				cell.setCellValue(new HSSFRichTextString(advertReport.getPushDateStart()));
				break;
			case 3:
				cell.setCellValue(new HSSFRichTextString(advertReport.getPushDateEnd()));
				
				break;
			case 4:
				cell.setCellValue(new HSSFRichTextString(advertReport.getReceiveCount() + ""));
				break;
			case 5:
//				cell.setCellValue(new HSSFRichTextString(advertReport.getPushDate()));
				break;
			}
		}
	}
	
}
