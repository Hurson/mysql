package com.avit.ads.pushads.task.bean;

import java.util.Comparator;
/**
 * 按广告位包-区域-参数排序类.
 */
public class ComparatorElementType implements Comparator {
		 public int compare(Object arg0, Object arg1) {
			 AdsElement element0=(AdsElement)arg0;
			 AdsElement element1=(AdsElement)arg1;
		     //现有js格式 排序		 
			 /*int flag=element0.getAdsTypeCode().compareTo(element1.getAdsTypeCode());
			  if(flag==0){
				  flag=element0.getAreaCode().compareTo(element1.getAreaCode());
				  if (flag==0){
					  return element0.getChannelCode().compareTo(element1.getChannelCode());
				  }
				  return flag;
			  }
			  else
			  {
			   return flag;
			  }  
			  */
			  //旧js格式 排序		 
			  int flag=element0.getAdsTypeCode().substring(0,4).compareTo(element1.getAdsTypeCode().substring(0,4));
			  //如果广告位相同,比较区域
			  if(flag==0){
				  flag=element0.getAreaCode().compareTo(element1.getAreaCode());
				  //如果区域相同，比较广告作目标
				  if (flag==0){
					  return element0.getParamCode().compareTo(element1.getParamCode());
				  }
			  }
			   return flag;
			 
		 }
	

}
