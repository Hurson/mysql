package com.avit.ads.requestads.cache;

import java.util.Comparator;
import java.util.Map;

public class LocationComparator implements Comparator {
	 public int compare(Object o1, Object o2) {  
         Map.Entry obj1 = (Map.Entry)o1;  
         Map.Entry obj2 = (Map.Entry)o2;           
         return obj1.getKey().toString().compareTo((obj2.getKey().toString()));  
     }  
}
