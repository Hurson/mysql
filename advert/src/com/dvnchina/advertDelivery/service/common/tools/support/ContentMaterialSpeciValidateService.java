package com.dvnchina.advertDelivery.service.common.tools.support;
/**
 * 文本字体验证接口
 * @author lester
 *
 */
public interface ContentMaterialSpeciValidateService extends ValidateService {
	/**
	 * 文字长度限制
	 * @param content 原始文本内容
	 * @return
	 */
	public boolean contentLengthValidate(String content);
	/**
	 * 是否有连接
	 * @param content 原始文本内容
	 * @return
	 */
	public boolean hasHyperLink(String content);
	/**
	 * 链接地址
	 * @param content 原始文本内容
	 * @return
	 */
	public boolean hyperLinkUrl(String content);
}
