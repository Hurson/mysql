package com.dvnchina.advertDelivery.order.bean;

import java.util.List;

public class CommonParentBean extends CommonBean {
	private List<CommonBean> child;

	public List<CommonBean> getChild() {
		return child;
	}

	public void setChild(List<CommonBean> child) {
		this.child = child;
	}

}
