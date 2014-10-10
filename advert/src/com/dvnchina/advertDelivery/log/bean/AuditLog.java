package com.dvnchina.advertDelivery.log.bean;

import java.io.Serializable;
import java.util.Date;

public class AuditLog implements Serializable{
	
	private static final long serialVersionUID = 1L;
	/** 主键 */
	private Integer id;
	/** 关联类型 */
	private Integer relationType;
	/** 关联审核ID */
	private Integer relationId;
	/** 关联审核名称 */
	private String relationName;
	/** 状态 */
	private Integer state;
	/** 操作员ID */
	private Integer operatorId;
	/** 操作员名称 */
	private String operatorName;
	/** 审核时间 */
	private String auditTime;
	/** 审核意见 */
	private String auditOpinion;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getRelationType() {
		return relationType;
	}
	public void setRelationType(Integer relationType) {
		this.relationType = relationType;
	}
	public Integer getRelationId() {
		return relationId;
	}
	public void setRelationId(Integer relationId) {
		this.relationId = relationId;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Integer getOperatorId() {
		return operatorId;
	}
	public void setOperatorId(Integer operatorId) {
		this.operatorId = operatorId;
	}
	public String getAuditTime() {
		return auditTime;
	}
	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}
	public String getAuditOpinion() {
		return auditOpinion;
	}
	public void setAuditOpinion(String auditOpinion) {
		this.auditOpinion = auditOpinion;
	}
	public String getRelationName() {
		return relationName;
	}
	public void setRelationName(String relationName) {
		this.relationName = relationName;
	}
	public String getOperatorName() {
		return operatorName;
	}
	public void setOperatorName(String operatorName) {
		this.operatorName = operatorName;
	}
	
}
