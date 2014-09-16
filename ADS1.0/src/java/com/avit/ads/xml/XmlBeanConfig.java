package com.avit.ads.xml;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import com.avit.ads.requestads.bean.request.AdInsertRequestXmlBean;
import com.avit.ads.requestads.bean.request.AdStatusReportReqXmlBean;
import com.avit.ads.requestads.bean.response.AdInsertResponsetPlaylistXmlBean;

public class XmlBeanConfig {
	@XmlElement(name="AdInsertRequestXmlBean")
	public List<AdInsertRequestXmlBean> AdInsertRequestXmlBean;
	@XmlElement(name="AdInsertResponsetPlaylistXmlBean")
	public List<AdInsertResponsetPlaylistXmlBean> AdInsertResponsetPlaylistXmlBean;
	@XmlElement(name="AdStatusReportReqXmlBean")
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
	public List<AdInsertResponsetPlaylistXmlBean> getAdInsertResponsetPlaylistXmlBean() {
		return this.AdInsertResponsetPlaylistXmlBean;
	}
	public void setAdInsertResponsetPlaylistXmlBean(
			List<AdInsertResponsetPlaylistXmlBean> adInsertResponsetPlaylistXmlBean) {
		this.AdInsertResponsetPlaylistXmlBean = adInsertResponsetPlaylistXmlBean;
	}
	public List<AdInsertRequestXmlBean> getAdInsertRequestXmlBean() {
		return this.AdInsertRequestXmlBean;
	}
	public void setAdInsertRequestXmlBean(
			List<AdInsertRequestXmlBean> adInsertRequestXmlBean) {
		this.AdInsertRequestXmlBean = adInsertRequestXmlBean;
	}
}