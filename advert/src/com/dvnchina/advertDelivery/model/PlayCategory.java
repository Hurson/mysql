package com.dvnchina.advertDelivery.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.net.ntp.TimeStamp;
import org.springframework.jdbc.core.RowMapper;

/**
 * 广告投放节点信息  -------------->T_PLAT_CATEGORY
 * 
 */

public class PlayCategory implements Serializable,RowMapper<PlayCategory>{
	
	private static final long serialVersionUID = 4895863265842484772L;
	
	private Integer id;
	/**
	 * 节点ID
	 */
	private String categoryId;
	/**
	 * 节点名称
	 */
	private String categoryName;
	
	/**
	 * 节点类型
	 */
	private String categoryType;
	/**
	 * 模板ID
	 */
	private String templateId;
	/**
	 * 模板名
	 */
	private String templateName;
	
	/**
	 * 创建时间
	 */
//	private Timestamp createTime;
	private Date createTime;
	
	/**
	 * 修改时间
	 */
//	private Timestamp modifyTime;
	private Date modifyTime;

	@Override
	public PlayCategory mapRow(ResultSet rs, int rowNumber) throws SQLException {
		PlayCategory playColumn = new PlayCategory();
		
		playColumn.setId(rs.getInt("ID"));
		playColumn.setCategoryId(rs.getString("CATEGORY_ID"));
		playColumn.setCategoryName(rs.getString("CATEGORY_NAME"));
		playColumn.setCategoryType(rs.getString("CATEGORY_TYPE"));
		playColumn.setTemplateId(rs.getString("TEMPLATE_ID"));
		playColumn.setTemplateName(rs.getString("TEMPLATE_NAME"));
//		playColumn.setCreateTime(rs.getTimestamp("CREATE_TIME"));
//		playColumn.setModifyTime(rs.getTimestamp("MODIFY_TIME"));
		playColumn.setCreateTime(rs.getTime("CREATE_TIME"));
		playColumn.setModifyTime(rs.getTime("MODIFY_TIME"));
		
		return playColumn;
	}


	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getTemplateId() {
		return templateId;
	}
	public void setTemplateId(String templateId) {
		this.templateId = templateId;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(Date modifyTime) {
		this.modifyTime = modifyTime;
	}

	public int compareValue() {
		
		Long id = this.id.longValue();
	//	Integer id = this.id;
		if(this.modifyTime != null){
			id += new TimeStamp(this.modifyTime).getTime();
		}
		return id.hashCode();
	}
	
}
