package com.dvnchina.advertDelivery.bean.config;

import com.dvnchina.advertDelivery.model.AdvertPosition;

/**
 * 广告位默认素材配置  实体Bean
 * 
 * @author chennaidong
 *
 */
public class AdvertPositionBean extends AdvertPosition{

	private static final long serialVersionUID = 1L;
	
	/**
	 * 子节点的ID
	 */
	private String child_id; 
	
	/**
	 * 素材类型
	 */
	private String m_type;
	
	/**
	 * 素材默认的路径
	 */
	private String m_path;

	
	public String getChild_id() {
		return child_id;
	}

	public void setChild_id(String childId) {
		child_id = childId;
	}

	public String getM_type() {
		return m_type;
	}

	public void setM_type(String mType) {
		m_type = mType;
	}

	public String getM_path() {
		return m_path;
	}

	public void setM_path(String mPath) {
		m_path = mPath;
	}

	@Override
	public String toString() {
		return "AdvertPositionBean [child_id=" + child_id + ", m_path=" + m_path + ", m_type=" + m_type + "]";
	}
	
}	