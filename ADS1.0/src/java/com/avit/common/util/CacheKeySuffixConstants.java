package com.avit.common.util;

/**
 * 该类对缓存关键字的后缀进行统一管理，后缀最好可以反映该缓存存储的结构信息
 *     缓存关键字的组成格式：应用路径 + 关键字 + 功能名称（即后缀）
 *     如：将tsid（假设此处值为1）与ErmServer对应关系保存至缓存时，关键字可以为:应用路径+"_"+"1"+"_"+"Tsid_ErmServer"
 *     其中功能名称部分可以反映该缓存的结构信息
 * @author lishiming
 * @date 2011-12-31
 * @version 1.0
 */
public class CacheKeySuffixConstants {
	//功能:根据SOPG获取该SOPG下所有的SOP数据，并封装成list ----用于S3 setup接口
	public static final String SOPG_SOPLIST_CACHESUFFIX = "Sopg_SopList";
    //功能:计算sop的已使用带宽
    public static final String Sop_USED_BANDWIDTH = "Sop_Used_bandwidth";

}
