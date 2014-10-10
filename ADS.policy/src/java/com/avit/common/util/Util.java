package com.avit.common.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import javax.servlet.http.HttpServletRequest;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 *@author:weimmy
 *@date:2010-12-07
 */
public class Util {
	
		/**
		 * Format:	yyyy-MM-ddThh:mm:ssZ
		 * @param date
		 * @return
		 */
	   public static XMLGregorianCalendar getXMLGregorianCalendar(Calendar date){
	    	XMLGregorianCalendar xmlDate = null;
	    	DatatypeFactory df = null;
	    	TimeZone timeZone =null;
	    	
	    	try {
	    		if (date != null){
	    			df = DatatypeFactory.newInstance();
		    		xmlDate = df.newXMLGregorianCalendar();
		    		
		    		xmlDate.setYear(date.get(Calendar.YEAR));
		    		xmlDate.setMonth(date.get(Calendar.MONTH) + 1);
		    		xmlDate.setDay(date.get(Calendar.DAY_OF_MONTH));
		    		xmlDate.setHour(date.get(Calendar.HOUR_OF_DAY));
		    		xmlDate.setMinute(date.get(Calendar.MINUTE));
		    		xmlDate.setSecond(date.get(Calendar.SECOND));
		    		
		    		timeZone = date.getTimeZone();
		    		xmlDate.setTimezone(timeZone.getRawOffset()/(1000*60));
		    		
	    		}
	    	} catch (Exception e){
	    		e.printStackTrace();
	    	}
	    	return xmlDate.normalize();
	    }
	   
	   public static XMLGregorianCalendar getXMLGregorianCalendar(Date date){
		   Calendar cal=Calendar.getInstance();
		   cal.setTime(date);
		   return getXMLGregorianCalendar(cal);
	    }
	   
	   /**
	    * Format:	yyyy-MM-dd
	    * @param date
	    * @return
	    */
	   public static XMLGregorianCalendar getXMLGregorianDate(Calendar date){
		   XMLGregorianCalendar xmlDate = null;
	    	DatatypeFactory df = null;

	    	try {
	    		if (date != null){
	    			df = DatatypeFactory.newInstance();
		    		xmlDate = df.newXMLGregorianCalendar();
		    		
		    		xmlDate.setYear(date.get(Calendar.YEAR));
		    		xmlDate.setMonth(date.get(Calendar.MONTH) + 1);
		    		xmlDate.setDay(date.get(Calendar.DAY_OF_MONTH));		    		
	    		}
	    	} catch (Exception e){
	    		e.printStackTrace();
	    	}
	    	return xmlDate.normalize();
	   }
	   
	   public static XMLGregorianCalendar getXMLGregorianDate(Date date){
		   Calendar cal=Calendar.getInstance();
		   cal.setTime(date);
		   return getXMLGregorianDate(cal);
	    }
	   
	   /**
	    * Format:	hh:mm:ss
	    * @param date
	    * @return
	    */
	   public static XMLGregorianCalendar getXMLGregorianTime(Calendar date){
		   XMLGregorianCalendar xmlDate = null;
	    	DatatypeFactory df = null;

	    	try {
	    		if (date != null){
	    			df = DatatypeFactory.newInstance();
		    		xmlDate = df.newXMLGregorianCalendar();
		    		
		    		xmlDate.setHour(date.get(Calendar.HOUR_OF_DAY));
		    		xmlDate.setMinute(date.get(Calendar.MINUTE));
		    		xmlDate.setSecond(date.get(Calendar.SECOND));    		
	    		}
	    	} catch (Exception e){
	    		e.printStackTrace();
	    	}
	    	return xmlDate.normalize();
	   }
	   
	   public static XMLGregorianCalendar getXMLGregorianTime(Date date){
		   Calendar cal=Calendar.getInstance();
		   cal.setTime(date);
		   return getXMLGregorianTime(cal);
	   }
	   
	   /**
	    * 从Request中获取内容
	    * @param request
	    * @return
	    * @throws IOException
	    */
	   public static String getRequestContent(HttpServletRequest request) throws IOException {
		   
		   StringBuffer sb = new StringBuffer();   	
		   BufferedReader br = null;  
		   try {
			   br = new BufferedReader(new InputStreamReader(request.getInputStream()));
			   String tmp = "";
			   while((tmp=br.readLine())!= null) {
					   sb.append(tmp);
			   }
			} catch (IOException e) {
				throw e;
			} finally {
				if (br != null) {			
					br.close();
				}
			}

			return sb.toString();
	   }
	   
	   /**
	    * 从文件中获取内容
	    * @param file
	    * @return
	    * @throws IOException
	    */
	   public static String getFileContent(String file) throws IOException {
		   StringBuffer sb = new StringBuffer();   	
		   BufferedReader br = null;  
		   try {
			   br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			   String tmp = "";
			   while((tmp=br.readLine())!= null) {
				   sb.append(tmp);
			   }
			} catch (IOException e) {
				throw e;
			} finally {
				if (br != null) {			
					br.close();
				}
			}

			return sb.toString();
	   }

		 public static void main(String[] args) throws DatatypeConfigurationException {
//			 	XMLGregorianCalendar xmlDate = null;
//		    	DatatypeFactory df = null;
//		    	df = DatatypeFactory.newInstance();
//	    		xmlDate = df.newXMLGregorianCalendar("2011-10-19T05:45:58Z");
//	    		 Date date = xmlDate.toGregorianCalendar().getTime();
	    		
	    	/*	 System.out.println(xmlDate.normalize());
			   System.out.println(getXMLGregorianCalendar(new Date()).toGregorianCalendar().getTime());
			   System.out.println(getXMLGregorianCalendar(new Date()));*/

		}
		
}
