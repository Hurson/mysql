package com.dvnchina.advertDelivery.bean.auditMetas;

import java.sql.Blob;

/**
 * @author Weicl
 *
 */
public class AuditMetasFormBean {
	
		/**
		 * 资源表单主键Id
		 */
		private Integer id;
		
		/**
		 * 资源名称
		 */
		private String  resourceName;
		
		/**
		 * 资源类型
		 */
		private Integer resourceType;
		
		/**
		 * 资源类型ID
		 */
		private Integer resourceId;
		
		/**
		 * 资源内容分类
		 */
		private Integer category;
		
		/**
		 * 资源类型名称
		 */
		private String resourceTypeName;
		
		/**
		 * 资源类型分类名称
		 */
		private String categoryName;
		
		/**
		 * 临时存储路径
		 */
		private String temporaryFilePath;
		
		/**
		 * 文字素材文字内容
		 */
		private String content;
		
		/**
		 * 
		 * 广告商客户表单中客户代码
		 * 
		 */
		private String clientCode;
		
		/**
		 * 合同表单中合同号
		 */
		private String contractNumber;
		
		
		public AuditMetasFormBean(){}
		
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getResourceName() {
			return resourceName;
		}
		public void setResourceName(String resourceName) {
			this.resourceName = resourceName;
		}
		public Integer getResourceType() {
			return resourceType;
		}
		public void setResourceType(Integer resourceType) {
			this.resourceType = resourceType;
		}
		public Integer getResourceId() {
			return resourceId;
		}
		public void setResourceId(Integer resourceId) {
			this.resourceId = resourceId;
		}
		public Integer getCategory() {
			return category;
		}
		public void setCategory(Integer category) {
			this.category = category;
		}
		public String getResourceTypeName() {
			return resourceTypeName;
		}
		public void setResourceTypeName(String resourceTypeName) {
			this.resourceTypeName = resourceTypeName;
		}
		public String getCategoryName() {
			return categoryName;
		}
		public void setCategoryName(String categoryName) {
			this.categoryName = categoryName;
		}

		public String getTemporaryFilePath() {
			return temporaryFilePath;
		}

		public void setTemporaryFilePath(String temporaryFilePath) {
			this.temporaryFilePath = temporaryFilePath;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String getClientCode() {
			return clientCode;
		}

		public void setClientCode(String clientCode) {
			this.clientCode = clientCode;
		}

		public String getContractNumber() {
			return contractNumber;
		}

		public void setContractNumber(String contractNumber) {
			this.contractNumber = contractNumber;
		}
		
}
