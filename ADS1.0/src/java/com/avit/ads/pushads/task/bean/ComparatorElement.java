package com.avit.ads.pushads.task.bean;

import java.util.Comparator;
// TODO: Auto-generated Javadoc
//
/**
 * 按频道——区域--广告位排序类.
 */
public class ComparatorElement implements Comparator {
		 
 		/* (non-Javadoc)
 		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
 		 */
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
				 int flag=element0.getParamCode().compareTo(element1.getParamCode());
				  if(flag==0){
					  flag=element0.getAreaCode().compareTo(element1.getAreaCode());
					  if (flag==0){
						  return element0.getAdsTypeCode().compareTo(element1.getAdsTypeCode());
					  }
					  return flag;
				  }
				  else
				  {
				   return flag;
				  } 
		 }
	

}
