package com.avit.ads.requestads.bean.response;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "")
@XmlRootElement(name = "AdInsertRequestPlaylists")
public class AdInsertResponsetPlaylistXmlBean {

	public AdInsertResponsetPlaylistXmlBean() {
		
	}
	
	public AdInsertResponsetPlaylistXmlBean(int adInsertRespCode, AdInsertRespContent adInsertRespContent) {
		this.adInsertRespCode = adInsertRespCode;
		this.adInsertRespContent = adInsertRespContent;
	}
	
	/** 广告插入决策响应代码，0 ：成功；1 ：用户未找到；其他待定 */
	@XmlElement(name="adInsertRespCode")
	private int adInsertRespCode; 
	
	/** 如果返回码为0，该部分包含广告插入的内容*/
	@XmlElement(name="AdInsertRespContent")
	private AdInsertRespContent adInsertRespContent; 
	
	
	public int getAdInsertRespCode() {
		return adInsertRespCode;
	}

	public void setAdInsertRespCode(int adInsertRespCode) {
		this.adInsertRespCode = adInsertRespCode;
	}

	public AdInsertRespContent getAdInsertRespContent() {
		return adInsertRespContent;
	}

	public void setAdInsertRespContent(AdInsertRespContent adInsertRespContent) {
		this.adInsertRespContent = adInsertRespContent;
	}

}
