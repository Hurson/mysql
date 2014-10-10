/**
 * Copyright (c) AVIT LTD (2012). All Rights Reserved.
 * Welcome to <a href="www.avit.com.cn">www.avit.com.cn</a>
 */
package com.avit.ads.xml.beans;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 * @Description:
 * @author lizhiwei
 * @Date: 2012-5-28
 * @Version: 1.0
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "ResServiceCode")
public class NodeResServiceMaping {
	@XmlTransient
	private Long id;
    @XmlAttribute(name = "serviceCode")
	private Long resourceServiceId;
    @XmlTransient
	private Long storageId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getResourceServiceId() {
		return resourceServiceId;
	}
	public void setResourceServiceId(Long resourceServiceId) {
		this.resourceServiceId = resourceServiceId;
	}
	public Long getStorageId() {
		return storageId;
	}
	public void setStorageId(Long storageId) {
		this.storageId = storageId;
	}
}
