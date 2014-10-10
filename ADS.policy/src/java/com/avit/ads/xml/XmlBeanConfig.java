package com.avit.ads.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.avit.ads.requestads.bean.request.AdInsertRequestXmlBean;
import com.avit.ads.requestads.bean.request.AdStatusReportReqXmlBean;
import com.avit.ads.requestads.bean.response.AdInsertResponseBean;

public class XmlBeanConfig {
	@XmlElement(name="AdInsertRequestXmlBean")
	public List<AdInsertRequestXmlBean> AdInsertRequestXmlBean;
	@XmlElement(name="AdInsertResponseBean")
	public List<AdInsertResponseBean> AdInsertResponseBean;
	@XmlElement(name="AdStatusReportReq")
	public List<AdStatusReportReqXmlBean> AdStatusReportReqXmlBean;

	public XmlBeanConfig() {
	}
	public List<AdStatusReportReqXmlBean> getAdStatusReportReqXmlBean() {
		return this.AdStatusReportReqXmlBean;
	}
	public void setAdStatusReportReqXmlBean(
			List<AdStatusReportReqXmlBean> adStatusReportReqXmlBean) {
		this.AdStatusReportReqXmlBean = adStatusReportReqXmlBean;
	}
	
	public List<AdInsertResponseBean> getAdInsertResponseBean() {
		return AdInsertResponseBean;
	}
	public void setAdInsertResponseBean(
			List<AdInsertResponseBean> adInsertResponseBean) {
		AdInsertResponseBean = adInsertResponseBean;
	}
	public List<AdInsertRequestXmlBean> getAdInsertRequestXmlBean() {
		return this.AdInsertRequestXmlBean;
	}
	public void setAdInsertRequestXmlBean(
			List<AdInsertRequestXmlBean> adInsertRequestXmlBean) {
		this.AdInsertRequestXmlBean = adInsertRequestXmlBean;
	}
}