package com.dvnchina.advertDelivery.action;

import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.model.AssetInfo;
import com.dvnchina.advertDelivery.model.ProductInfo;
import com.dvnchina.advertDelivery.service.ParseVodService;
import com.dvnchina.advertDelivery.service.VodIncrSyncService;
import com.dvnchina.advertDelivery.service.VodInfoService;
import com.dvnchina.advertDelivery.service.VodSyncService;
import com.dvnchina.advertDelivery.utils.Transform;

public class VodSyncAction extends BaseActionSupport<Object> {
	private static final long serialVersionUID = 1L;

	private static final String OK = "ok";

	private static final String ERROR = "error";

	private static final Logger logger = Logger.getLogger(VodSyncAction.class);

	private VodIncrSyncService vodIncrSyncService;

	private VodSyncService vodSyncService;

	private ParseVodService parseVodService;

	private VodInfoService vodInfoService;

	private String selAId;
	private String selAName;
	private String selPId;
	private String selPName;

	public void setVodIncrSyncService(VodIncrSyncService vodIncrSyncService) {
		this.vodIncrSyncService = vodIncrSyncService;
	}

	public void setVodSyncService(VodSyncService vodSyncService) {
		this.vodSyncService = vodSyncService;
	}

	public void setParseVodService(ParseVodService parseVodService) {
		this.parseVodService = parseVodService;
	}

	public void setVodInfoService(VodInfoService vodInfoService) {
		this.vodInfoService = vodInfoService;
	}


	public String getSelAId() {
		return selAId;
	}

	public void setSelAId(String selAId) {
		this.selAId = selAId;
	}

	public String getSelAName() {
		return selAName;
	}

	public void setSelAName(String selAName) {
		this.selAName = selAName;
	}

	public String getSelPId() {
		return selPId;
	}

	public void setSelPId(String selPId) {
		this.selPId = selPId;
	}

	public String getSelPName() {
		return selPName;
	}

	public void setSelPName(String selPName) {
		this.selPName = selPName;
	}

	public String vodSync() {
		try {
			vodSyncService.syncVodInfo();
			renderText("0");
		} catch (Exception e) {
			logger.error("同步vod数据出错", e);
			renderText("-1");
		}
		return null;
	}

	public String vodIncrSync() {
		logger.info("###调用cps增量同步数据,from ip=" + getRequest().getRemoteAddr());
		InputStream in = null;
		try {
			in = getRequest().getInputStream();
			logger.debug("性能优化记录：同步BSMP数据开始于===>"
					+ Transform.CalendartoString(Calendar.getInstance()));
			boolean flag = convertStreamToObject(in, getResponse());
			if (flag) {
				outMessage(getResponse(), OK);
			} else {
				outMessage(getResponse(), ERROR);

			}
			logger.info("###调用cps增量同步VOD数据结束");

		} catch (Exception e) {
			logger.error("######调用cps增量同步VOD数据错误: ", e);
			outMessage(getResponse(), ERROR);
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					logger.error("关闭io出错 ", e);
				}
			}
		}
		return null;
	}

	private boolean convertStreamToObject(InputStream intpuStream,
			HttpServletResponse httpServletResponse) throws Exception {

		logger.debug("性能优化记录：开始解析同步数据，形成hashMap放入内存中===>"
				+ Transform.CalendartoString(Calendar.getInstance()));
		HashMap<String, Object> infos = parseVodService
				.convertStreamToObject(intpuStream);
		logger.debug("性能优化记录：解析同步数据完成于===>"
				+ Transform.CalendartoString(Calendar.getInstance()));
		return vodIncrSyncService.updateDataIncrement(infos);
	}

	/**
	 * 输出信息
	 */
	private void outMessage(HttpServletResponse response, String msg) {
		ServletOutputStream out = null;
		try {
			out = response.getOutputStream();
			out.write(msg.getBytes());
			out.flush();
		} catch (Exception e) {
			logger.error("###增量同步VOD数据返回信息出错", e);
		} finally {
			if (out != null) {
				try {
					out.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public String listProduct() {
		int count = vodInfoService.getProductCount(selPId,selPName);
		PageBeanDB page = new PageBeanDB(count, pageNo);
		List<ProductInfo> products = vodInfoService.getProducts(page.getBegin(), page.getEnd(), selPId,selPName);
		getRequest().setAttribute("page", page);
		getRequest().setAttribute("products", products);
		return SUCCESS;
	}

	public String listAsset() {	
		int count = vodInfoService.getAssetCount(selAId,selAName);
		PageBeanDB page = new PageBeanDB(count, pageNo);
		List<AssetInfo> assets = vodInfoService.getAssets(page.getBegin(), page.getEnd(), selAId,selAName);
		getRequest().setAttribute("page", page);
		getRequest().setAttribute("assets", assets);
		return SUCCESS;
	}
}
