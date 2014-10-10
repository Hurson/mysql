package com.dvnchina.advertDelivery.service.common.tools.support;

import java.util.List;

/**
 * 图片类型素材验证
 * @author lester
 *
 */
public interface PictureMaterialSpeciValidateService extends ValidateService {
	/**
	 * 验证文件大小
	 * @param filepath 待验证文件路径
	 * @param maxRequireSourceFileSize
	 * @return
	 */
	public boolean validateFileSize(String filepath,Integer maxRequireSourceFileSize);
	/**
	 * 验证文件类型
	 * @param filepath 待验证文件路径
	 * @param fileType 待验证文件类型
	 * @return
	 */
	public boolean validateFileType(String filepath,List<String> fileType);
}
