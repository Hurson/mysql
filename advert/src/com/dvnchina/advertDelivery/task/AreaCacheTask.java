package com.dvnchina.advertDelivery.task;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.dvnchina.advertDelivery.common.cache.RefreshCacheService;
import com.dvnchina.advertDelivery.constant.Constant;
import com.dvnchina.advertDelivery.contract.bean.PositionAD;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.order.bean.QuestionReportBean;
import com.dvnchina.advertDelivery.order.service.GenerateQuestionReportService;
import com.dvnchina.advertDelivery.order.service.OrderService;
import com.dvnchina.advertDelivery.order.service.PlayList4OrderService;
import com.dvnchina.advertDelivery.utils.ConfigureProperties;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;

public class AreaCacheTask {
	private static Logger logger = Logger.getLogger(AreaCacheTask.class);
	private static ConfigureProperties config = ConfigureProperties.getInstance();
	
	private RefreshCacheService refreshCacheService;




    public RefreshCacheService getRefreshCacheService() {
        return refreshCacheService;
    }



    public void setRefreshCacheService(RefreshCacheService refreshCacheService) {
        this.refreshCacheService = refreshCacheService;
    }



    /**
	 * 定时刷新区域缓存
	 * */
	public void refreshAreaCache() {
	    refreshCacheService.refreshLocation();
//	    Map<Long,LocationCacheBean>  dd2 = LocationCache.getLocationMap();
//        LocationCacheBean s = LocationCache.getChiledLocation(152010000007l);
//        LocationCacheBean s1 = LocationCache.getChiledLocation(152010000005l);
//        LocationCacheBean s2 = LocationCache.getChiledLocation(152010000000l);
//        LocationCacheBean s3 = LocationCache.getChiledLocation(152010000008L);

	}
	
	


}
