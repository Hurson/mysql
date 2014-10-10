package com.avit.ads.requestads.cache;

import java.util.Comparator;
import java.util.Map;

import com.avit.ads.requestads.bean.PrecisionCacheModel;
import com.avit.common.util.StringUtil;

public class PresisionComparator implements Comparator {
	 public int compare(Object o1, Object o2) {  
		 PrecisionCacheModel obj1 = (PrecisionCacheModel)o1;  
		 PrecisionCacheModel obj2 = (PrecisionCacheModel)o2;           
         return StringUtil.toNotNullStr(obj2.getUseLevel()).compareTo((StringUtil.toNotNullStr(obj1.getUseLevel())));  
     }  
}
