package com.avit.lucene;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.*;

import org.apache.lucene.queryparser.classic.ParseException;

import com.avit.ads.syncreport.bean.LuceneBean;
import com.avit.ads.util.ConstantsAdsCode;

public class Test {
	
	@SuppressWarnings({ "unchecked", "rawtypes", "static-access" })
	public static void main(String[] args) throws IllegalArgumentException, IOException, IllegalAccessException, InvocationTargetException, ParseException{
		List list = new ArrayList();
//		LuceneBean luceneBean= new LuceneBean();
//
//		//test();
//		
//		if (1==1)return;
//    	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_MAINMENU);
//    	luceneBean.setPosition_code(ConstantsAdsCode.CMA_GUIDE_POSITION_CODE);
//    	luceneBean.setTvn("111");
//    	luceneBean.setOperator_time("20130910215");
//    	luceneBean.setService_id(" ");
//    	list.add(luceneBean);
//    	luceneBean= new LuceneBean();
//		FileIndex fii = new FileIndex("");
//		fii.createIndex(list, LuceneBean.class);
//		fii.updateIndex(LuceneBean.class);
//		Search searchi = new Search(fii.getDir());
//		//printPageBean(search.Query("sep","cs1"));
//		String keyi[]={"position_code","operator_time"};
//		String valuei[]={ConstantsAdsCode.CMA_GUIDE_POSITION_CODE,"20130910215"};
//		PageBean tt = searchi.MultiQuery(keyi,valuei);
		//printPageBean(searchi.MultiQuery(keyi,valuei));
		
		
		VO v1= new VO();
		v1.setName("测试1");
		v1.setAge(100);
		v1.setSep("cs1");
		list.add(v1);
		VO v2= new VO();
		v2.setName("测试11");
		v2.setAge(80);
		v2.setSep("cs11");
		list.add(v2);
		VO v3= new VO();
		v3.setName("测试2");
		v3.setAge(60);
		v3.setSep("cs2");
		list.add(v3);
		VO v4= new VO();
		v4.setName("测试22");
		v4.setAge(40);
		v4.setSep("cs22");
		list.add(v4);
		VO v5= new VO();
		v5.setName("测试3");
		v5.setAge(20);
		v5.setSep("cs3");
		list.add(v5);
		FileIndex fi = new FileIndex("");
		
		
		
		
		
		
		fi.createIndex(list, VO.class);
		fi.updateIndex(VO.class);
		Search search = new Search(fi.getDir());
		printPageBean(search.Query("sep","cs1"));
		System.out.println("===========================2");
		printPageBean(search.TermQuery("sep","s"));
		System.out.println("===========================3");		
		printPageBean(search.TermQueryRange("sep","cs","ct"));
		System.out.println("===========================4");
		printPageBean(search.TermQueryNumber("age",100,100));
		System.out.println("===========================5");
		printPageBean(search.Query("sep","cs1"));
		System.out.println("===========================6");
		printPageBean(search.WildcardQuery("name","*1*"));
		System.out.println("===========================7");
		printPageBean(search.WildcardQuery("sep","*s*"));	
		System.out.println("===========================8");
		String key[]={"sep","name"};
		String value[]={"sc","1"};
		printPageBean(search.MultiQuery(key,value));	
		System.out.println("===========================9");
		printPageBean(search.MultiQueryEx(key,value,"name","测试"));
		
		printPageBean(search.MultiQueryEx(key,value,"sep","*"));
		
		System.out.println("===========================10");
		//空搜索需使用通配符 *
		printPageBean(search.WildcardQuery("name","*","age"));
		System.out.println("===========================11");
		printPageBean(search.MultiQuerySHOULD(null,null));
		
		search.close();
		
		//测试缓存更新
		/*list = init2();
		//创建基于VO的索引
		fi.createIndex(list, VO.class);
		//更新基于VO的索引，批量更新
		fi.updateIndex(VO.class);
		search = new Search(fi.getDir());
		System.out.println("===========================1");
		search.Search("sep","cs1");
		System.out.println("===========================2");
		search.TermQuery("sep","s");
		System.out.println("===========================3");		
		search.TermQueryRange("sep","cs","ct");
		System.out.println("===========================4");
		search.TermQueryNumber("age",100,100);
		System.out.println("===========================5");
		search.Search("sep","cs1");		
		search.close();
		
		list = init3();
		//创建基于VO的索引
		fi.createIndex(list, VO2.class);
		//更新基于VO的索引，批量更新
		fi.updateIndex(VO2.class);
		search = new Search(fi.getDir());
		System.out.println("===========================1");
		search.Search("sep","cs1");
		System.out.println("===========================2");
		search.TermQuery("sep","s");
		System.out.println("===========================3");		
		search.TermQueryRange("sep","cs","ct");
		System.out.println("===========================4");
		search.TermQueryNumber("age",100,100);
		System.out.println("===========================5");
		search.Search("sep","cs1");	
		System.out.println("===========================6");
		search.Search("test","cs1");	*/		
	}

	public static void printPageBean(PageBean page){
		for(int i = 0;i<page.getData().size();i++){
			System.out.println("name:"+((VO)page.getData().get(i)).getName());
			System.out.println("age:"+((VO)page.getData().get(i)).getAge());
			System.out.println("sep:"+((VO)page.getData().get(i)).getSep());
		}
	}
	
	
	public static List init2(){
		List result = new ArrayList();
		VO v1= new VO();
		v1.setName("XX1");
		v1.setAge(100);
		v1.setSep("cs1");
		result.add(v1);
		VO v2= new VO();
		v2.setName("XXX11");
		v2.setAge(80);
		v2.setSep("cs11");
		result.add(v2);
		VO v3= new VO();
		v3.setName("XXX2");
		v3.setAge(60);
		v3.setSep("cs2");
		result.add(v3);
		VO v4= new VO();
		v4.setName("XXX22");
		v4.setAge(40);
		v4.setSep("cs22");
		result.add(v4);
		VO v5= new VO();
		v5.setName("XXX3");
		v5.setAge(20);
		v5.setSep("cs3");
		result.add(v5);		
		return result;
	}
	public static List init3(){
		List result = new ArrayList();
		VO2 v1= new VO2();
		v1.setName("XX21");
		v1.setAge(100);
		v1.setSep("cs1");
		result.add(v1);
		VO2 v2= new VO2();
		v2.setName("XXX11");
		v2.setAge(80);
		v2.setSep("cs11");
		result.add(v2);
		VO2 v3= new VO2();
		v3.setName("XXX2");
		v3.setAge(60);
		v3.setSep("cs2");
		result.add(v3);
		VO2 v4= new VO2();
		v4.setName("XXX22");
		v4.setAge(40);
		v4.setSep("cs22");
		result.add(v4);
		VO2 v5= new VO2();
		v5.setName("XXX3");
		v5.setAge(20);
		v5.setSep("cs3");
		result.add(v5);		
		return result;
	}
	  public static void test()
	   {
		   List listAll= new ArrayList();
		   try
		   {
//		    LuceneBean luceneBean= new LuceneBean();
//		    for (int i=0;i<5;i++)
//		    {
//		    	luceneBean= new LuceneBean();
//		    	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_START);
//	        	luceneBean.setPosition_code(ConstantsAdsCode.CMA_START_POSITION_CODE1+i);
//	        	luceneBean.setTvn(""+i);
//	        	luceneBean.setOperator_time("20130910173534");
//	        	String str  ="ID"+i;
//	        	luceneBean.setService_id(str);
//	        	listAll.add(luceneBean);
//		    }
//			FileIndex fi = new FileIndex("","");
//			fi.createCacheIndex(listAll, LuceneBean.class);
//			//fi.updateIndex(LuceneBean.class);
//			System.out.println("end index"+new Date().getTime());
//			Search search = new Search(fi.getDir());
//			search.setPageInfo(1, 10000000);
//			
//			search.TermQuery("service_id","*4");
//			search.TermQuery("position_code",ConstantsAdsCode.CMA_START_POSITION_CODE1+4);
//			search.Query("service_id","ID4");
//			search.Query("position_code",ConstantsAdsCode.CMA_START_POSITION_CODE1+4);
//			String keys[]={"position_code","service_id"};
//			String values[]={ConstantsAdsCode.CMA_START_POSITION_CODE1+1,"ID2"};
//			PageBean channelpageBean=search.MultiQueryExRange(keys,values,"operator_time","20130910173501","20130910180000");
//			//PageBean channelpageBean=search.Query("service_id","ID"+channels[channelindex]);
//			if (channelpageBean!=null && channelpageBean.getRowCount()>0)
//			{
//				channelpageBean.getRowCount();
//			}
//			
//			//search.setPageInfo(1, 100000);
//			//printPageBean(search.Query("sep","cs1"));
//			String key[]={"position_code","operator_time"};
//			String value[]={ConstantsAdsCode.CMA_GUIDE_POSITION_CODE,"2013091021"};
//			//PageBean tt = search.MultiQuery(keyi,valuei);
//			System.out.println("start search"+new Date().getTime());
//			printPageBean(search.MultiQuery(key,value));
			System.out.println("start search"+new Date().getTime());
//			String keys[]=null;
		   }
		   catch(Exception e)
		   {
			   e.printStackTrace();
		   }
	   }
}
