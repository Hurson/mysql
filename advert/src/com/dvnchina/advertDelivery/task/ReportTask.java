package com.dvnchina.advertDelivery.task;

import java.io.File;
import java.io.FileOutputStream;
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

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.report.bean.AdvertOrderReport;
import com.dvnchina.advertDelivery.report.bean.OrderBean;
import com.dvnchina.advertDelivery.report.service.AdvertReportService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;

public class ReportTask {

	private static Logger logger = Logger.getLogger(ReportTask.class);

	private static ConfigureProperties config = ConfigureProperties
			.getInstance();
	private AdvertReportService advertReportService;

	public void exportReport() {
		List<OrderBean> orderList = advertReportService.queryOrderList();
		if(orderList!=null && orderList.size()>0){
			for(OrderBean order : orderList){
				// 统计单向广告
				exportAdvert(order);
				// 统计双向广告
				exportReqAdvert(order);
			}
		}
		
	}

	private void exportAdvert(OrderBean order) {
		String retDate = "";
		String filePath = ".././";
		String fileName = "";
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle cellStyle2 = workbook.createCellStyle();
		cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFSheet sheet = null;
		try {
			PageBeanDB page = advertReportService.queryAdvertPublishCountList(
					order, -1, -1);
			List<AdvertOrderReport> reportList = page.getDataList();
			if (reportList == null || reportList.size() <= 0) {
				return;
			} else {
				retDate = reportList.get(0).getPushDate().split(" ")[0];
				AdvertOrderReport aReport = reportList.get(0);
				fileName = aReport.getAdvertisersName() + "_"
						+ aReport.getPositionName() + "_" + order.getOrderCode() + "_"
						+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())
						+ ".xls";
			}
			// 广告位工作表名称，每个广告位一个
			String sheetName = "";
			// 正式记录起始下标
			int startIndex = 5;
			// 表中行下标
			int sheetRowNum = 0;
			String lastPushDate = "";
			// int tmpSheetRowNum = 0;//记录相同投放时间的第一个记录的下标
			for (AdvertOrderReport aReport : reportList) {
				if (!sheetName.equals(aReport.getPositionCode())) {
					sheetName = aReport.getPositionCode();
					if (sheet != null) {
						// 投放结束时间
						sheet.getRow(1)
								.getCell(1)
								.setCellValue(
										new HSSFRichTextString(sheet.getRow(1)
												.getCell(1)
												.getRichStringCellValue()
												.getString()
												+ " -- " + lastPushDate));
						// 合并投放时间相同的行（n,m,1,1）
						int rowNum = sheet.getPhysicalNumberOfRows();
						int lastIndex = startIndex;
						String lastDate = "";
						for (int i = startIndex; i < rowNum; i++) {
							String curDate = sheet.getRow(i).getCell(0)
									.getRichStringCellValue().toString();
							if (!lastDate.equals(curDate)) {
								if (i - 1 > lastIndex) {
									sheet.addMergedRegion(new CellRangeAddress(
											lastIndex, i - 1, 0, 0));
									lastIndex = i;
								}
							} else if (i == rowNum - 1) {
								// 最后一个
								if (i > lastIndex) {
									sheet.addMergedRegion(new CellRangeAddress(
											lastIndex, i, 0, 0));
								}
							}
							lastDate = curDate;
						}
					}
					sheet = workbook.createSheet(sheetName);
					createTitle(sheet, cellStyle, aReport);
					// 投放开始时间
					sheet.getRow(1)
							.getCell(1)
							.setCellValue(
									new HSSFRichTextString(aReport
											.getPushDate()));
					sheetRowNum = startIndex;
					// tmpSheetRowNum = sheetRowNum;
				}
				// 填充报表数据
				createRow(sheet.createRow(sheetRowNum), cellStyle2, aReport);
				// 合并投放时间相同的行（n,m,1,1）
				// if (lastPushDate.equals(aReport.getPushDate())) {
				// if (sheetRowNum > tmpSheetRowNum) {
				// sheet.addMergedRegion(new CellRangeAddress(tmpSheetRowNum,
				// sheetRowNum, 0, 0));
				// System.out.println(tmpSheetRowNum+" "+sheetRowNum);
				// }
				// }
				// tmpSheetRowNum = sheetRowNum;
				lastPushDate = aReport.getPushDate();
				sheetRowNum++;
			}
			if (sheet != null) {
				// 投放结束时间
				sheet.getRow(1)
						.getCell(1)
						.setCellValue(
								new HSSFRichTextString(sheet.getRow(1)
										.getCell(1).getRichStringCellValue()
										.getString()
										+ " -- " + lastPushDate));
				// 合并投放时间相同的行（n,m,1,1）
				int rowNum = sheet.getPhysicalNumberOfRows();
				int lastIndex = startIndex;
				String lastDate = "";
				System.out.println(rowNum);
				for (int i = startIndex; i < rowNum; i++) {
					String curDate = sheet.getRow(i).getCell(0)
							.getRichStringCellValue().toString();
					if (!lastDate.equals(curDate)) {
						if (i - 1 > lastIndex) {
							sheet.addMergedRegion(new CellRangeAddress(
									lastIndex, i - 1, 0, 0));
							lastIndex = i;
						}
					} else if (i == rowNum - 1) {
						// 最后一个
						if (i > lastIndex) {
							sheet.addMergedRegion(new CellRangeAddress(
									lastIndex, i, 0, 0));
						}
					}
					lastDate = curDate;
				}
			}

			// 生成文件
			logger.info("生成文件 " + fileName);
			FileOutputStream fOut;
			fOut = new FileOutputStream(filePath + fileName);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();

			// FTP文件复制转移
			FtpUtils ftp = new FtpUtils();
			ftp.connectionFtp();
			String remoteDirectoryReal = config.getValueByKey("advert.ftp");
			ftp.uploadFileToRemote(filePath + fileName, remoteDirectoryReal,
					fileName);
			// 删除本地文件
			File localFile = new File(filePath + fileName);
			if (localFile.exists() && localFile.isFile()) {
				localFile.delete();
			}
			ftp.closeFTP();
		} catch (Exception e) {
			logger.error("生成日报表文件出现异常", e);
		}
	}

	private void exportReqAdvert(OrderBean order) {
		String retDate = "";
		String filePath = ".././";
		String fileName = "";
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFCellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
		HSSFCellStyle cellStyle2 = workbook.createCellStyle();
		cellStyle2.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
		HSSFSheet sheet = null;
		try {
			PageBeanDB page = advertReportService
					.queryReqAdvertPublishCountList(order, -1, -1);
			List<AdvertOrderReport> reportList = page.getDataList();
			if (reportList == null || reportList.size() <= 0) {
				logger.info("reqhistory is null");
				return;
			} else {
				retDate = reportList.get(0).getPushDate().split(" ")[0];
				AdvertOrderReport aReport = reportList.get(0);
				fileName = aReport.getAdvertisersName() + "_"
						+ aReport.getPositionName() + "_" + order.getOrderCode() + "_"
						+ new SimpleDateFormat("yyyy-MM-dd").format(new Date())
						+ ".xls";
			}
			// 广告位工作表名称，每个广告位一个
			String sheetName = "";
			// 正式记录起始下标
			int startIndex = 5;
			// 表中行下标
			int sheetRowNum = 0;
			String lastPushDate = "";
			// int tmpSheetRowNum = 0;//记录相同投放时间的第一个记录的下标
			for (AdvertOrderReport aReport : reportList) {
				if (!sheetName.equals(aReport.getPositionCode())) {
					sheetName = aReport.getPositionCode();
					if (sheet != null) {
						// 投放结束时间
						sheet.getRow(1)
								.getCell(1)
								.setCellValue(
										new HSSFRichTextString(sheet.getRow(1)
												.getCell(1)
												.getRichStringCellValue()
												.getString()
												+ " -- " + lastPushDate));
						// 合并投放时间相同的行（n,m,1,1）
						int rowNum = sheet.getPhysicalNumberOfRows();
						int lastIndex = startIndex;
						String lastDate = "";
						for (int i = startIndex; i < rowNum; i++) {
							String curDate = sheet.getRow(i).getCell(0)
									.getRichStringCellValue().toString();
							if (!lastDate.equals(curDate)) {
								if (i - 1 > lastIndex) {
									sheet.addMergedRegion(new CellRangeAddress(
											lastIndex, i - 1, 0, 0));
									lastIndex = i;
								}
							} else if (i == rowNum - 1) {
								// 最后一个
								if (i > lastIndex) {
									sheet.addMergedRegion(new CellRangeAddress(
											lastIndex, i, 0, 0));
								}
							}
							lastDate = curDate;
						}
					}
					sheet = workbook.createSheet(sheetName);
					createReqTitle(sheet, cellStyle, aReport);
					// 投放开始时间
					sheet.getRow(1)
							.getCell(1)
							.setCellValue(
									new HSSFRichTextString(aReport
											.getPushDate()));
					sheetRowNum = startIndex;
					// tmpSheetRowNum = sheetRowNum;
				}
				// 填充报表数据
				createReqRow(sheet.createRow(sheetRowNum), cellStyle2, aReport);
				// 合并投放时间相同的行（n,m,1,1）
				// if (lastPushDate.equals(aReport.getPushDate())) {
				// if (sheetRowNum > tmpSheetRowNum) {
				// sheet.addMergedRegion(new CellRangeAddress(tmpSheetRowNum,
				// sheetRowNum, 0, 0));
				// System.out.println(tmpSheetRowNum+" "+sheetRowNum);
				// }
				// }
				// tmpSheetRowNum = sheetRowNum;
				lastPushDate = aReport.getPushDate();
				sheetRowNum++;
			}
			if (sheet != null) {
				// 投放结束时间
				sheet.getRow(1)
						.getCell(1)
						.setCellValue(
								new HSSFRichTextString(sheet.getRow(1)
										.getCell(1).getRichStringCellValue()
										.getString()
										+ " -- " + lastPushDate));
				// 合并投放时间相同的行（n,m,1,1）
				int rowNum = sheet.getPhysicalNumberOfRows();
				int lastIndex = startIndex;
				String lastDate = "";
				System.out.println(rowNum);
				for (int i = startIndex; i < rowNum; i++) {
					String curDate = sheet.getRow(i).getCell(0)
							.getRichStringCellValue().toString();
					if (!lastDate.equals(curDate)) {
						if (i - 1 > lastIndex) {
							sheet.addMergedRegion(new CellRangeAddress(
									lastIndex, i - 1, 0, 0));
							lastIndex = i;
						}
					} else if (i == rowNum - 1) {
						// 最后一个
						if (i > lastIndex) {
							sheet.addMergedRegion(new CellRangeAddress(
									lastIndex, i, 0, 0));
						}
					}
					lastDate = curDate;
				}
			}

			// 生成文件
			logger.info("生成文件 " + fileName);
			FileOutputStream fOut;
			fOut = new FileOutputStream(filePath + fileName);
			workbook.write(fOut);
			fOut.flush();
			fOut.close();

			// FTP文件复制转移
			FtpUtils ftp = new FtpUtils();
			ftp.connectionFtp();
			String remoteDirectoryReal = config.getValueByKey("advert.req.ftp");
			ftp.uploadFileToRemote(filePath + fileName, remoteDirectoryReal,
					fileName);
			// 删除本地文件
			File localFile = new File(filePath + fileName);
			if (localFile.exists() && localFile.isFile()) {
				localFile.delete();
			}
			ftp.closeFTP();

		} catch (Exception e) {
			logger.error("生成日报表文件出现异常", e);
		}
	}

	// 创建表头公共部分
	private void createTitle(HSSFSheet sheet, HSSFCellStyle cellStyle,
			AdvertOrderReport advertReport) {
		String title = "广告投放数据表";

		for (int i = 0; i < 5; i++) {
			HSSFRow row = sheet.createRow(i);
			for (int j = 0; j < 6; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new HSSFRichTextString(""));
			}
		}
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 3200);
		sheet.setColumnWidth(5, 2800);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 4));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 4));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 4));
		sheet.getRow(0).getCell(0).setCellStyle(cellStyle);
		sheet.getRow(0).getCell(0).setCellValue(new HSSFRichTextString(title));
		sheet.getRow(1).getCell(0)
				.setCellValue(new HSSFRichTextString("投放起始时间（日期）"));
		// sheet.getRow(1).getCell(1).setCellValue(new
		// HSSFRichTextString(advertReport.getPushDateStart() + "——" +
		// advertReport.getPushDateEnd()));
		sheet.getRow(2).getCell(0)
				.setCellValue(new HSSFRichTextString("广告位名称"));
		sheet.getRow(2)
				.getCell(1)
				.setCellValue(
						new HSSFRichTextString(advertReport.getPositionName()));
		sheet.getRow(3).getCell(0)
				.setCellValue(new HSSFRichTextString("广告位ID"));
		sheet.getRow(3)
				.getCell(1)
				.setCellValue(
						new HSSFRichTextString(advertReport.getPositionCode()));
		sheet.getRow(4).getCell(0).setCellValue(new HSSFRichTextString("投放时间"));
		sheet.getRow(4).getCell(1).setCellValue(new HSSFRichTextString("素材名称"));
//		sheet.getRow(4).getCell(2).setCellValue(new HSSFRichTextString("投放次数"));
		sheet.getRow(4).getCell(2).setCellValue(new HSSFRichTextString("展示次数"));
		sheet.getRow(4).getCell(3).setCellValue(new HSSFRichTextString("素材分类"));
		sheet.getRow(4).getCell(4)
				.setCellValue(new HSSFRichTextString("广告商名称"));
	}

	// 填充表格主体数据
	private void createRow(HSSFRow row, HSSFCellStyle cellStyle,
			AdvertOrderReport advertReport) {
		HSSFCell cell = null;
		for (int i = 0; i < 6; i++) {
			cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			switch (i) {
			case 0:
				cell.setCellValue(new HSSFRichTextString(advertReport
						.getPushDate()));
				break;
			case 1:
				cell.setCellValue(new HSSFRichTextString(advertReport
						.getResourceName()));
				break;
//			case 2:
//				cell.setCellValue(new HSSFRichTextString(advertReport
//						.getPushCount() + ""));
//				break;
			case 2:
				cell.setCellValue(new HSSFRichTextString(advertReport
						.getReceiveCount() + ""));
				break;
			case 3:
				cell.setCellValue(new HSSFRichTextString(advertReport
						.getCategoryName()));
				break;
			case 4:
				cell.setCellValue(new HSSFRichTextString(advertReport
						.getAdvertisersName()));
				break;
			}
		}
	}

	// 创建表头公共部分
	private void createReqTitle(HSSFSheet sheet, HSSFCellStyle cellStyle,
			AdvertOrderReport advertReport) {
		String title = "广告投放数据表";

		for (int i = 0; i < 5; i++) {
			HSSFRow row = sheet.createRow(i);
			for (int j = 0; j < 6; j++) {
				HSSFCell cell = row.createCell(j);
				cell.setCellType(HSSFCell.CELL_TYPE_STRING);
				cell.setCellValue(new HSSFRichTextString(""));
			}
		}
		sheet.setColumnWidth(0, 5000);
		sheet.setColumnWidth(1, 3200);
		sheet.setColumnWidth(5, 2800);
		sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));
		sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 4));
		sheet.addMergedRegion(new CellRangeAddress(2, 2, 1, 4));
		sheet.addMergedRegion(new CellRangeAddress(3, 3, 1, 4));
		sheet.getRow(0).getCell(0).setCellStyle(cellStyle);
		sheet.getRow(0).getCell(0).setCellValue(new HSSFRichTextString(title));
		sheet.getRow(1).getCell(0)
				.setCellValue(new HSSFRichTextString("投放起始时间（日期）"));
		// sheet.getRow(1).getCell(1).setCellValue(new
		// HSSFRichTextString(advertReport.getPushDateStart() + "——" +
		// advertReport.getPushDateEnd()));
		sheet.getRow(2).getCell(0)
				.setCellValue(new HSSFRichTextString("广告位名称"));
		sheet.getRow(2)
				.getCell(1)
				.setCellValue(
						new HSSFRichTextString(advertReport.getPositionName()));
		sheet.getRow(3).getCell(0)
				.setCellValue(new HSSFRichTextString("广告位ID"));
		sheet.getRow(3)
				.getCell(1)
				.setCellValue(
						new HSSFRichTextString(advertReport.getPositionCode()));
		sheet.getRow(4).getCell(0).setCellValue(new HSSFRichTextString("投放时间"));
		sheet.getRow(4).getCell(1).setCellValue(new HSSFRichTextString("素材名称"));
//		sheet.getRow(4).getCell(2).setCellValue(new HSSFRichTextString("投放次数"));
		sheet.getRow(4).getCell(2).setCellValue(new HSSFRichTextString("展示次数"));
		sheet.getRow(4).getCell(3).setCellValue(new HSSFRichTextString("素材分类"));
		sheet.getRow(4).getCell(4)
				.setCellValue(new HSSFRichTextString("广告商名称"));
	}

	// 填充表格主体数据
	private void createReqRow(HSSFRow row, HSSFCellStyle cellStyle,
			AdvertOrderReport advertReport) {
		HSSFCell cell = null;
		for (int i = 0; i < 6; i++) {
			cell = row.createCell(i);
			cell.setCellType(HSSFCell.CELL_TYPE_STRING);
			cell.setCellStyle(cellStyle);
			switch (i) {
			case 0:
				cell.setCellValue(new HSSFRichTextString(advertReport
						.getPushDate()));
				break;
			case 1:
				cell.setCellValue(new HSSFRichTextString(advertReport
						.getResourceName()));
				break;
//			case 2:
//				cell.setCellValue(new HSSFRichTextString(advertReport
//						.getPushCount() + ""));
//				break;
			case 2:
				cell.setCellValue(new HSSFRichTextString(advertReport
						.getReceiveCount() + ""));
				break;
			case 3:
				cell.setCellValue(new HSSFRichTextString(advertReport
						.getCategoryName()));
				break;
			case 4:
				cell.setCellValue(new HSSFRichTextString(advertReport
						.getAdvertisersName()));
				break;
			}
		}
	}

	public AdvertReportService getAdvertReportService() {
		return advertReportService;
	}

	public void setAdvertReportService(AdvertReportService advertReportService) {
		this.advertReportService = advertReportService;
	}

}
