package com.dvnchina.advertDelivery.model;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

/**
 * Portal 投放节点信息关系表  ----->T_PORTAL_PLAT_CATEGORY
 * @author Weicl
 *
 */

public class PortalPlayCategory implements Serializable,RowMapper<PortalPlayCategory>{
	
	private static final long serialVersionUID = -3558183549123617033L;
	
	private Integer id;
	
	/**
	 * 节点ID
	 */
	private Integer categoryId;
	
	/**
	 * 广告位ID
	 */
	private Integer positionId;

	@Override
	public PortalPlayCategory mapRow(ResultSet rs, int rowNumber)throws SQLException {
		PortalPlayCategory portalPlayCategory = new PortalPlayCategory();
		portalPlayCategory.setId(rs.getInt("ID"));
		portalPlayCategory.setCategoryId(rs.getInt("CATEGORY_ID"));
		portalPlayCategory.setPositionId(rs.getInt("POSITION_ID"));
		return portalPlayCategory;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}
	public Integer getPositionId() {
		return positionId;
	}
	public void setPositionId(Integer positionId) {
		this.positionId = positionId;
	}

}











