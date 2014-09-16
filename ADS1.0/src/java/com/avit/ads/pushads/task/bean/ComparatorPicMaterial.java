package com.avit.ads.pushads.task.bean;

import java.util.Comparator;
// TODO: Auto-generated Javadoc
//
/**
 * 按频道——区域--广告位排序类.
 */
public class ComparatorPicMaterial implements Comparator {
		 
 		/* (non-Javadoc)
 		 * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
 		 */
 		public int compare(Object arg0, Object arg1) {
 			 PicMaterial element0=(PicMaterial)arg0;
			 PicMaterial element1=(PicMaterial)arg1;
		   	 int flag=element0.getIndex().compareTo(element1.getIndex());
			 return flag;
		 }
	

}
