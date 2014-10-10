package com.dvnchina.advertDelivery.npvrChannelGroup.bean;

import com.dvnchina.advertDelivery.utils.StringUtil;
import com.dvnchina.advertDelivery.utils.Transform;

/**
 * TChannelGroup entity. @author MyEclipse Persistence Tools
 */

public class TNpvrChannelGroup implements java.io.Serializable {

	// Fields

	private Long id;
	private String code;
	private String name;
	private String channelDesc;

	// Constructors

	/** default constructor */
	public TNpvrChannelGroup() {
	}

	/** minimal constructor */
	public TNpvrChannelGroup(String name) {
		this.name = name;
	}

	/** full constructor */
	public TNpvrChannelGroup(String code, String name, String channelDesc) {
		this.code = code;
		this.name = name;
		this.channelDesc = channelDesc;
	}

	// Property accessors

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannelDesc() {
		return this.channelDesc;
	}

	public void setChannelDesc(String channelDesc) {
		this.channelDesc = channelDesc;
	}
	
	/**
	 * 操作日志详情描述
	 */
	public String toString() {
        StringBuffer info = new StringBuffer();
        info.append(!"".equals(StringUtil.toNotNullStr(id)) ? "ID:" + id + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(name)) ? "频道组名:" + name + "," : "");
        info.append(!"".equals(StringUtil.toNotNullStr(code)) ? "频道组编码:" + code + "," : "");
        if (info.length() > 0)
            return info.toString().substring(0, info.length() - 1);
        else
            return "";
    }

}