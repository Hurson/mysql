package com.dvnchina.advertDelivery.utils;

import java.io.ByteArrayInputStream;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

/**
 * 字符串数据帮助类
 * 
 * @author chennaidong
 *
 */
public class StringUtil {
    
    public static int toInt(Object objValue) {
        return toInt(objValue, 0);
    }
    
    public static int toInt(Object objValue, int defaultValue) {
        if (objValue == null) {
            return defaultValue;
        } else {
            try {
                return Integer.parseInt(objValue.toString());
            } catch (Exception ex) {
                return defaultValue;
            }

        }
    }
    public static Long toLong(Object objValue) {
        return toLong(objValue, 0L);
    }
    
    public static Long toLong(Object objValue, Long defaultValue) {
        if (objValue == null) {
            return defaultValue;
        } else {
            try {
                return Long.parseLong(objValue.toString());
            } catch (Exception ex) {
                return defaultValue;
            }

        }
    }
	
	/**
	 * 把数字型字符串转换为整数类型
	 * 
	 * @param srcNum "21;22;23;24"
	 * @param sign   ";"
	 * @return
	 */
	public static List<Integer> getIntegerList(String srcNum,String sign){
		
		List<Integer> intList =  new ArrayList<Integer>();
		if(StringUtils.isNotBlank(srcNum)){
			String[] srcNums = srcNum.split(sign);
			for(String s : srcNums){
				if(StringUtils.isNotBlank(s)){
					intList.add(Integer.valueOf(s));
				}
			}
		}
		return intList;
	}
	
	/**
	 * 判断参数列表中是否有空值
	 * 
	 * @param strArray  字符数组
	 * 					java.lang.Sting
	 * @return true:有空值  false:没有
	 */
	public static boolean isBlankArray(String[] strArray){
		for (String param : strArray) {
			if (StringUtils.isBlank(param)){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 把数据库中blob类型转换成String类型
	 * 
	 * @param blob
	 * 			java.sql.Blob;
	 * @return 字符串
	 * 			java.lang.String;
	 */
	public static String convertBlobToString(Blob blob) {

		String result = "";
		try {
			ByteArrayInputStream msgContent = (ByteArrayInputStream) blob.getBinaryStream();
			byte[] byte_data = new byte[msgContent.available()];
			msgContent.read(byte_data, 0, byte_data.length);
			result = new String(byte_data);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String toNotNullStr(Object canNullStr, String defaultStr) {
        if (canNullStr == null) {
            if (defaultStr != null) {
                return defaultStr;
            } else {
                return "";
            }
        } else {
            return canNullStr.toString();
        }
    }

    public static String toNotNullStr(Object canNullStr) {
        return toNotNullStr(canNullStr, "");
    }
	
	public static String ellipsis(String str, int len) {
    	if (str != null && str.length() > len) {
    		str = str.substring(0, len-3) + "...";
		}
    	return str;
    }
	public static String objListToString(List<?> list, String separator, String blankFillStr){
		if(null == list || list.size() == 0){
			return blankFillStr;
		}
		if(list.size() == 1){
			return "" + list.get(0);
		}
		String str = "";
		for(Object obj : list){
			str += separator + obj;
		}
		return str.substring(1);
	}
}
