package com.avit.ads.syncreport.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;

import com.avit.ads.syncreport.bean.AdPlaylistGis;
import com.avit.ads.syncreport.bean.ContentPush;
import com.avit.ads.syncreport.bean.LuceneBean;
import com.avit.ads.syncreport.bean.TReportData;
import com.avit.ads.syncreport.service.impl.ReportServiceImpl;
import com.avit.ads.util.ConstantsAdsCode;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.util.DateUtil;
import com.avit.ads.util.InitConfig;
import com.avit.common.util.StringUtil;
import com.avit.lucene.FileIndex;
import com.avit.lucene.PageBean;


public class CreateIndexThread extends Thread { //implements Runable
	String convertday;
    String path;
	FileIndex fi;
	private RAMDirectory ramdir;
	private IndexWriter ramwriter;
	int      ramSize = 0;
	public static Long ffcount=0L;
	public Long ffcountl=0L;
	Map <String,List<AdPlaylistGis>> phourAdPlayListGisList=null;
	public CreateIndexThread(String convertday,String path,FileIndex fi)
	{
		this.convertday=convertday;
		this.path=path;
		this.fi=fi;
		try
		{
		ramdir = new RAMDirectory();
		Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
		IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44, analyzer);
		ramwriter = new IndexWriter(ramdir,iwc);
		ramSize= StringUtil.toInt(InitConfig.getConfigMap().get(ConstantsHelper.CMA_LUCENE_SIZE));
		}
		catch(Exception e)
		{
			
		}
	}
	public void run(){
		List listAll= new ArrayList();
		phourAdPlayListGisList=ReportServiceImpl.hourAdPlayListGisList;
		try 
		  {
			  String cmaPath = path;
			  File a=new File(cmaPath);
			  String[] file=a.list();
			  File temp=null;
			  for (int i = 0; i < file.length; i++) 
			  {
				  
				  if(cmaPath.endsWith(File.separator))
				  {
					  temp=new File(cmaPath+file[i]);
				  }
				  else
				  { 
					  temp=new File(cmaPath+File.separator+file[i]);
				  }
				  if(temp.isDirectory())
				  { 
					  continue;
				  }
				  if(temp.isFile())
				  {   
					  //如果是广告配置文件，不复制
//					  if (!temp.getName().toString().startsWith(action_flag+convertday) && !temp.getName().toString().startsWith(boot_flag+convertday))
//					  {
//						  continue;
//					  }
					  FileInputStream input = new FileInputStream(temp);
					  List listFile =null;
					  if (temp.getName().toString().startsWith(ConstantsHelper.ACTION_FLAG+convertday))
					  {
						  listFile= convertBeanFromInput(input);
						  if (listFile!=null && listFile.size()>0)
						  {
							  //listAll.addAll(listFile);
							  fi.createCacheIndex(listFile, LuceneBean.class,ramwriter);
						  }
					  }
					  
					  if (temp.getName().toString().startsWith(ConstantsHelper.BOOT_FLAG+convertday))
					  {
						  //System.out.println();
						  listFile= convertBootBeanFromInput(input);
						  if (listFile!=null && listFile.size()>0)
						  {
							 // listAll.addAll(listFile);
							  fi.createCacheIndex(listFile, LuceneBean.class,ramwriter);
						  }
					  }
					  input.close();
				  }
				  if (ramdir.sizeInBytes()>ramSize)
				  {
					  ramwriter.close();
					  fi.addDirIndex(ramdir);					  
					  ramdir = new RAMDirectory();
					  Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
					  IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44, analyzer);
					  ramwriter = new IndexWriter(ramdir,iwc);
				  }
					
			  }
			 
			  {
				  ramwriter.close();
				  fi.addDirIndex(ramdir);					  
//				  ramdir = new RAMDirectory();
//				  Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_44);
//				  IndexWriterConfig iwc = new IndexWriterConfig(Version.LUCENE_44, analyzer);
//				  ramwriter = new IndexWriter(ramdir,iwc);
			  }
			  System.out.println(path+" thread ffcountl="+ffcountl);			 
		  }
		  catch (Exception e)
		  {   
			  e.printStackTrace();
		  }
		
	}
	
	public static void main(String[] args) {
		String line = "15000205D70000137348,20140709235957:GUIDE: ,28610744";
		String[] datas = line.split(ConstantsHelper.COMMA);
		String[] ld = datas[1].trim().split(":");
		System.out.println(ld.length);
	}
	
	
	public List convertBeanFromInput(FileInputStream input)
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(input)); 
		String result="";
		String line = null;			
		List list = new ArrayList();
		List<AdPlaylistGis> listPlay ;
		String cmatag="";
		String predata="";
		String nextdata="";
		int testcount=0;
		SimpleDateFormat fmt = new SimpleDateFormat();
		fmt.applyPattern("yyyyMMddHHmmss");
		try
		{
	        while ((line = br.readLine()) != null) {
	            line = line.trim();
	           
	            //ConstantsHelper.COMMA
	            try
	            {
	            	predata = line.substring(0, line.indexOf(ConstantsHelper.COLON));
	 	            nextdata= line.substring(line.lastIndexOf(ConstantsHelper.COLON)+1);
	 	            //主菜单 外框
	            	if (line.indexOf(ConstantsAdsCode.CMA_MAINMENU)>0)
			            {
	            			//System.out.println(line);
	            			String [] datas= line.substring(0,line.indexOf(ConstantsHelper.COLON)).split(ConstantsHelper.COMMA);
			            	LuceneBean luceneBean= new LuceneBean();
			            	
			            	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_MAINMENU);
			            	luceneBean.setPosition_code(ConstantsAdsCode.CMA_MAINMENU_POSITION_CODE);
			            	luceneBean.setTvn(datas[0]);
			            	luceneBean.setOperator_time(datas[1]);
			            	//add orderid resourceid
			            //	testcount++;
			            	listPlay=null;
			            	
			            	 try
			            	{
			            		//listPlay = ReportServiceImpl.hourAdPlayListGisList.get(datas[1].substring(0,10));
			            		listPlay = phourAdPlayListGisList.get(datas[1].substring(0,10));
			            	}
			            	catch(Exception e)
			            	{
			            		//System.out.println();
			            	}
			            	
			            	if (listPlay!=null)
			            	{
				            	for(AdPlaylistGis adPlay:listPlay)
				            	{
				            		if(ConstantsAdsCode.CMA_MAINMENU_POSITION_CODE.equals(adPlay.getAdSiteCode()))
				            		{
				            			Date optime = new Date();//DateUtil.toDate(datas[1],"yyyyMMddHHmmss");					            		
					            		optime = fmt.parse(datas[1]);
					            		if (optime.after(adPlay.getStartTime()) && optime.before(adPlay.getEndTime()))
										{
					            			luceneBean.setContentid(adPlay.getContentId());
						            		luceneBean.setOrder_id(adPlay.getOrderId().toString());
						            		luceneBean.setService_id(" ");
							            	list.add(luceneBean);
							            	ContentPush  contentPush = new ContentPush();
							            	contentPush.setContentid(adPlay.getContentId());
							            	ReportServiceImpl.contentPush.put(adPlay.getContentId(), contentPush);
							            	break;
										}					            		
				            		}
				            	}
			            	}
			            	
			            	
			            	//end
//			            	luceneBean.setService_id(" ");
//			            	list.add(luceneBean);
			            	luceneBean= new LuceneBean();
	
			            	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_MAINMENU);
			            	luceneBean.setPosition_code(ConstantsAdsCode.CMA_MAINMENU_FRAME_POSITION_CODE);
			            	luceneBean.setTvn(datas[0]);
			            	luceneBean.setOperator_time(datas[1]);
			            	luceneBean.setService_id(" ");
			            	
			            	if (listPlay!=null)
			            	{
			            		
			            		for(AdPlaylistGis adPlay:listPlay)
				            	{
			            			
				            		if(ConstantsAdsCode.CMA_MAINMENU_FRAME_POSITION_CODE.equals(adPlay.getAdSiteCode()))
				            		{
				            			//String stroptime = datas[1];				            			
					            		Date optime = new Date();//DateUtil.toDate(datas[1],"yyyyMMddHHmmss");					            		
					            		optime = fmt.parse(datas[1]);
					            		ffcountl++;	
				            			if (optime.after(adPlay.getStartTime()) && optime.before(adPlay.getEndTime()))
										{
					            			luceneBean.setContentid(adPlay.getContentId());
						            		luceneBean.setOrder_id(adPlay.getOrderId().toString());
						            		luceneBean.setService_id(" ");
							            	list.add(luceneBean);
							            	ContentPush  contentPush = new ContentPush();
							            	contentPush.setContentid(adPlay.getContentId());
							            	ReportServiceImpl.contentPush.put(adPlay.getContentId(), contentPush);
							            	
					            			
							            	break;
										}
					            		else
					            		{
					            			//System.out.println();
					            		}
					            		
				            		}
				            	}
			            	}
			            	else
			            	{
			            		//System.out.println("listPlay is null");
			            		//testcount++;
			            	}
			            	
//			            	list.add(luceneBean);
			            	continue;
			           }
	            	cmatag = line.substring(line.indexOf(ConstantsHelper.COLON)+1, line.lastIndexOf(ConstantsHelper.COLON));
		            //为明确serviceid唯一 需要增加标识
	            	//节目预告
	            	if (cmatag.equals(ConstantsAdsCode.CMA_GUIDE))
		            {
		            	String [] predatas= predata.split(ConstantsHelper.COMMA);
		            	String [] nextdatas= nextdata.split(ConstantsHelper.COMMA);
		            	LuceneBean luceneBean= new LuceneBean();
		            	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_GUIDE);
		            	luceneBean.setPosition_code(ConstantsAdsCode.CMA_GUIDE_POSITION_CODE);
		            	luceneBean.setTvn(predatas[0]);
		            	luceneBean.setOperator_time(predatas[1]);
		            	luceneBean.setService_id("ID"+nextdatas[0]);
		            	listPlay = phourAdPlayListGisList.get(predatas[1].substring(0,10));
		            	if (listPlay!=null)
		            	{
			            	for(AdPlaylistGis adPlay:listPlay)
			            	{
			            		if(ConstantsAdsCode.CMA_GUIDE_POSITION_CODE.equals(adPlay.getAdSiteCode()) && adPlay.getChannelId().indexOf(nextdatas[0])>0)
			            		{
			            			Date optime = new Date();//DateUtil.toDate(datas[1],"yyyyMMddHHmmss");					            		
				            		optime = fmt.parse(predatas[1]);
				            		if (optime.after(adPlay.getStartTime()) && optime.before(adPlay.getEndTime()) )
									{
				            			luceneBean.setContentid(adPlay.getContentId());
					            		luceneBean.setOrder_id(adPlay.getOrderId().toString());
					            		list.add(luceneBean);
						            	ContentPush  contentPush = new ContentPush();
						            	contentPush.setContentid(adPlay.getContentId());
						            	ReportServiceImpl.contentPush.put(adPlay.getContentId(), contentPush);
						            	break;
									}					            		
			            		}
			            	}
		            	}
		            	//list.add(luceneBean);
		            	continue;
		            }
	            	//音量条
		            if (cmatag.equals(ConstantsAdsCode.CMA_VOLUME))
		            {
		            	String [] predatas= predata.split(ConstantsHelper.COMMA);
		            	String [] nextdatas= nextdata.split(ConstantsHelper.COMMA);
		            	LuceneBean luceneBean= new LuceneBean();
		            	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_VOLUME);
		            	luceneBean.setPosition_code(ConstantsAdsCode.CMA_VOLUME_POSITION_CODE);
		            	luceneBean.setTvn(predatas[0]);
		            	luceneBean.setOperator_time(predatas[1]);
		            	luceneBean.setService_id("ID"+nextdatas[0]);
		            	listPlay = phourAdPlayListGisList.get(predatas[1].substring(0,10));
		            	if (listPlay!=null)
		            	{
			            	for(AdPlaylistGis adPlay:listPlay)
			            	{
			            		if(ConstantsAdsCode.CMA_VOLUME_POSITION_CODE.equals(adPlay.getAdSiteCode()) && adPlay.getChannelId().indexOf(nextdatas[0])>0)
			            		{
			            			Date optime = new Date();//DateUtil.toDate(datas[1],"yyyyMMddHHmmss");					            		
				            		optime = fmt.parse(predatas[1]);
				            		if (optime.after(adPlay.getStartTime()) && optime.before(adPlay.getEndTime()))
									{
				            			luceneBean.setContentid(adPlay.getContentId());
					            		luceneBean.setOrder_id(adPlay.getOrderId().toString());
					            		list.add(luceneBean);
						            	ContentPush  contentPush = new ContentPush();
						            	contentPush.setContentid(adPlay.getContentId());
						            	ReportServiceImpl.contentPush.put(adPlay.getContentId(), contentPush);
						            	break;
									}					            		
			            		}
			            	}
		            	}
		            	//list.add(luceneBean);
		            	continue;
		            }
		            //导航条
		            if (cmatag.equals(ConstantsAdsCode.CMA_MINIEPG))
		            {
		            	String [] predatas= predata.split(ConstantsHelper.COMMA);
		            	String [] nextdatas= nextdata.split(ConstantsHelper.COMMA);
		            	LuceneBean luceneBean= new LuceneBean();
		            	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_MINIEPG);
		            	luceneBean.setPosition_code(ConstantsAdsCode.CMA_MINIEPG_POSITION_CODE);
		            	luceneBean.setTvn(predatas[0]);
		            	luceneBean.setOperator_time(predatas[1]);
		            	luceneBean.setService_id("ID"+nextdatas[0]);
		            	listPlay = phourAdPlayListGisList.get(predatas[1].substring(0,10));
		            	if (listPlay!=null)
		            	{
			            	for(AdPlaylistGis adPlay:listPlay)
			            	{
			            		if(ConstantsAdsCode.CMA_MINIEPG_POSITION_CODE.equals(adPlay.getAdSiteCode()) && adPlay.getChannelId().indexOf(nextdatas[0])>0)
			            		{
			            			Date optime = new Date();//DateUtil.toDate(datas[1],"yyyyMMddHHmmss");					            		
				            		optime = fmt.parse(predatas[1]);
				            		if (optime.after(adPlay.getStartTime()) && optime.before(adPlay.getEndTime()))
									{
				            			luceneBean.setContentid(adPlay.getContentId());
					            		luceneBean.setOrder_id(adPlay.getOrderId().toString());
					            		list.add(luceneBean);
						            	ContentPush  contentPush = new ContentPush();
						            	contentPush.setContentid(adPlay.getContentId());
						            	ReportServiceImpl.contentPush.put(adPlay.getContentId(), contentPush);
						            	break;
									}					            		
			            		}
			            	}
		            	}
		            	//list.add(luceneBean);
		            	continue;
		            }
		            //导航条
		            if (cmatag.equals(ConstantsAdsCode.CMA_DVB))
		            {
		            	String [] predatas= predata.split(ConstantsHelper.COMMA);
		            	String [] nextdatas= nextdata.split(ConstantsHelper.COMMA);
		            	LuceneBean luceneBean= new LuceneBean();
		            	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_DVB);
		            	luceneBean.setPosition_code(ConstantsAdsCode.CMA_DVB_POSITION_CODE);
		            	luceneBean.setTvn(predatas[0]);
		            	luceneBean.setOperator_time(predatas[1]);
		            	luceneBean.setService_id("ID"+nextdatas[0]);
		            	listPlay = phourAdPlayListGisList.get(predatas[1].substring(0,10));
		            	if (listPlay!=null)
		            	{
			            	for(AdPlaylistGis adPlay:listPlay)
			            	{
			            		if(ConstantsAdsCode.CMA_DVB_POSITION_CODE.equals(adPlay.getAdSiteCode()) && adPlay.getChannelId().indexOf(nextdatas[0])>0)
			            		{
			            			Date optime = new Date();//DateUtil.toDate(datas[1],"yyyyMMddHHmmss");					            		
				            		optime = fmt.parse(predatas[1]);
				            		if (optime.after(adPlay.getStartTime()) && optime.before(adPlay.getEndTime()))
									{
				            			luceneBean.setContentid(adPlay.getContentId());
					            		luceneBean.setOrder_id(adPlay.getOrderId().toString());
						            	list.add(luceneBean);
						            	ContentPush  contentPush = new ContentPush();
						            	contentPush.setContentid(adPlay.getContentId());
						            	ReportServiceImpl.contentPush.put(adPlay.getContentId(), contentPush);
						            	break;
									}					            		
			            		}
			            	}
		            	}
		            	//list.add(luceneBean);
		            	continue;
		            }
		            //列表
		            if (cmatag.equals(ConstantsAdsCode.CMA_LIST))
		            {
		            	String [] predatas= predata.split(ConstantsHelper.COMMA);
		            	String [] nextdatas= nextdata.split(ConstantsHelper.COMMA);
		            	LuceneBean luceneBean= new LuceneBean();
		            	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_LIST);
		            	luceneBean.setPosition_code(ConstantsAdsCode.CMA_LIST_POSITION_CODE);
		            	luceneBean.setTvn(predatas[0]);
		            	luceneBean.setOperator_time(predatas[1]);
		            	luceneBean.setService_id("ID"+nextdatas[0]);
		            	listPlay = phourAdPlayListGisList.get(predatas[1].substring(0,10));
		            	if (listPlay!=null)
		            	{
			            	for(AdPlaylistGis adPlay:listPlay)
			            	{
			            		if(ConstantsAdsCode.CMA_LIST_POSITION_CODE.equals(adPlay.getAdSiteCode()) && adPlay.getChannelId().indexOf(nextdatas[0])>0)
			            		{
			            			Date optime = new Date();//DateUtil.toDate(datas[1],"yyyyMMddHHmmss");					            		
				            		optime = fmt.parse(predatas[1]);
				            		if (optime.after(adPlay.getStartTime()) && optime.before(adPlay.getEndTime()))
									{
				            			luceneBean.setContentid(adPlay.getContentId());
					            		luceneBean.setOrder_id(adPlay.getOrderId().toString());
					            		list.add(luceneBean);
						            	ContentPush  contentPush = new ContentPush();
						            	contentPush.setContentid(adPlay.getContentId());
						            	ReportServiceImpl.contentPush.put(adPlay.getContentId(), contentPush);
						            	break;
									}					            		
			            		}
			            	}
		            	}
		            	//list.add(luceneBean);
		            	continue;
		            }
		            //收藏列表
		            if (cmatag.equals(ConstantsAdsCode.CMA_FAVORITEB))
		            {
		            	String [] predatas= predata.split(ConstantsHelper.COMMA);
		            	String [] nextdatas= nextdata.split(ConstantsHelper.COMMA);
		            	LuceneBean luceneBean= new LuceneBean();
		            	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_FAVORITEB);
		            	luceneBean.setPosition_code(ConstantsAdsCode.CMA_FAVORITEB_POSITION_CODE);
		            	luceneBean.setTvn(predatas[0]);
		            	luceneBean.setOperator_time(predatas[1]);
		            	luceneBean.setService_id("ID"+nextdatas[0]);
		            	listPlay = phourAdPlayListGisList.get(predatas[1].substring(0,10));
		            	if (listPlay!=null)
		            	{
			            	for(AdPlaylistGis adPlay:listPlay)
			            	{
			            		if(ConstantsAdsCode.CMA_FAVORITEB_POSITION_CODE.equals(adPlay.getAdSiteCode()) && adPlay.getChannelId().indexOf(nextdatas[0])>0)
			            		{
			            			Date optime = new Date();//DateUtil.toDate(datas[1],"yyyyMMddHHmmss");					            		
				            		optime = fmt.parse(predatas[1]);
				            		if (optime.after(adPlay.getStartTime()) && optime.before(adPlay.getEndTime()))
									{
				            			luceneBean.setContentid(adPlay.getContentId());
					            		luceneBean.setOrder_id(adPlay.getOrderId().toString());
					            		list.add(luceneBean);
						            	ContentPush  contentPush = new ContentPush();
						            	contentPush.setContentid(adPlay.getContentId());
						            	ReportServiceImpl.contentPush.put(adPlay.getContentId(), contentPush);
						            	break;
									}					            		
			            		}
			            	}
		            	}
		            	//list.add(luceneBean);
		            	continue;
		            }
		            //广播
		            if (cmatag.equals(ConstantsAdsCode.CMA_RADIO))
		            {
		            	String [] predatas= predata.split(ConstantsHelper.COMMA);
		            	//String [] nextdatas= nextdata.split(ConstantsHelper.COMMA);
		            	LuceneBean luceneBean= new LuceneBean();
		            	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_RADIO);
		            	luceneBean.setPosition_code(ConstantsAdsCode.CMA_RADIO_POSITION_CODE);
		            	luceneBean.setTvn(predatas[0]);
		            	luceneBean.setOperator_time(predatas[1]);
		            	luceneBean.setService_id("ID0");
		            	listPlay = phourAdPlayListGisList.get(predatas[1].substring(0,10));
		            	if (listPlay!=null)
		            	{
			            	for(AdPlaylistGis adPlay:listPlay)
			            	{
			            		if(ConstantsAdsCode.CMA_RADIO_POSITION_CODE.equals(adPlay.getAdSiteCode()))
			            		{
			            			Date optime = new Date();//DateUtil.toDate(datas[1],"yyyyMMddHHmmss");					            		
				            		optime = fmt.parse(predatas[1]);
				            		if (optime.after(adPlay.getStartTime()) && optime.before(adPlay.getEndTime()))
									{
//				            			luceneBean= new LuceneBean();
//						            	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_RADIO);
//						            	luceneBean.setPosition_code(ConstantsAdsCode.CMA_RADIO_POSITION_CODE);
//						            	luceneBean.setTvn(predatas[0]);
//						            	luceneBean.setOperator_time(predatas[1]);
//						            	luceneBean.setService_id("ID0");
				            			
				            			luceneBean.setContentid(adPlay.getContentId());
					            		luceneBean.setOrder_id(adPlay.getOrderId().toString());
					            		list.add(luceneBean);
						            	ContentPush  contentPush = new ContentPush();
						            	contentPush.setContentid(adPlay.getContentId());
						            	ReportServiceImpl.contentPush.put(adPlay.getContentId(), contentPush);
						            	break;
									}					            		
			            		}
			            	}
		            	}
		            	//list.add(luceneBean);
		            	continue;
		            }
	            }
	            catch(Exception e)
	            {
	            	//e.printStackTrace();
	    			//logger.error(line);
	            }
	        }
	        br.close();
	        br=null;
		}
	    catch (Exception e)
	    {
	    	//e.printStackTrace();
	    }
        finally
        {
        	if (br!=null)
        	{
        		try
        		{
        			br.close();
        		}
        		catch (Exception e)
        	    {
    	        	
        	    }
        	}
        }
        testcount=0;
		return list;
	}
	public List convertBootBeanFromInput(FileInputStream input)
	{
		BufferedReader br = new BufferedReader(new InputStreamReader(input)); 
		String result="";
		String line = null;			
		List list = new ArrayList();
		List<AdPlaylistGis> listPlay ;
		String cmatag="";
		String predata="";
		String nextdata="";
		SimpleDateFormat fmt = new SimpleDateFormat();
		fmt.applyPattern("yyyyMMddHHmmss");
		try
		{
	        while ((line = br.readLine()) != null) {
	            line = line.trim();
	           
	            //ConstantsHelper.COMMA
	            try
	            {
	            	
	 	            String [] datas= line.split(ConstantsHelper.COMMA);
	 	            if(datas.length < 2){
	 	            	continue;
	 	            }
	            	LuceneBean luceneBean= new LuceneBean();
	            	LuceneBean luceneBeantemp= new LuceneBean();
	            	luceneBean.setPosition_flag(ConstantsAdsCode.CMA_START);
	            	luceneBean.setPosition_code(ConstantsAdsCode.CMA_START_POSITION_CODE);
	            	luceneBean.setTvn(datas[0]);
	            	luceneBean.setOperator_time(datas[1].replace(ConstantsHelper.COLON, "").replace(" ", ""));
	            	luceneBean.setService_id(" ");
	            	
	            	luceneBeantemp.setPosition_flag(ConstantsAdsCode.CMA_START);
	            	luceneBeantemp.setPosition_code(ConstantsAdsCode.CMA_START_POSITION_CODE);
	            	luceneBeantemp.setTvn(datas[0]);
	            	luceneBeantemp.setOperator_time(datas[1].replace(ConstantsHelper.COLON, "").replace(" ", ""));
	            	luceneBeantemp.setService_id(" ");
	            	
	            	
	            	
	            	listPlay = phourAdPlayListGisList.get(luceneBean.getOperator_time().substring(0,10));
	            	if (listPlay!=null)
	            	{
		            	for(AdPlaylistGis adPlay:listPlay)
		            	{
		            		//Date optime = DateUtil.toDate(luceneBean.getOperator_time(),"yyyyMMddHHmmss");
		            		Date optime = new Date();//DateUtil.toDate(datas[1],"yyyyMMddHHmmss");					            		
		            		optime = fmt.parse(luceneBean.getOperator_time());
		            		if(ConstantsAdsCode.CMA_START_POSITION_CODE.equals(adPlay.getAdSiteCode()))
		            		{
			            		if (optime.after(adPlay.getStartTime()) && optime.before(adPlay.getEndTime()))
								{
			            			
				            		String [] contentids=adPlay.getContentId().trim().split(ConstantsHelper.COMMA);
									int len = contentids.length;
									int hour  =optime.getHours();
									ContentPush contentPush = null;
									switch (len) {
									case 1:   //一张默认图片
					            		luceneBean.setOrder_id(adPlay.getOrderId().toString());
					            		luceneBean.setContentid(StringUtil.toNotNullStr(contentids[0]));	
										list.add(luceneBean);
										
										contentPush = new ContentPush();
										contentPush.setContentid(contentids[0]);
										ReportServiceImpl.contentPush.put(contentids[0], contentPush);
										break;
									case 2: // 一个视频，一张默认图片
										luceneBeantemp.setOrder_id(adPlay.getOrderId().toString());
										luceneBeantemp.setContentid(StringUtil.toNotNullStr(contentids[0]));
										list.add(luceneBeantemp);
										
										contentPush = new ContentPush();
										contentPush.setContentid(contentids[0]);
										ReportServiceImpl.contentPush.put(contentids[0], contentPush);
										
										luceneBean.setOrder_id(adPlay.getOrderId().toString());
					            		luceneBean.setContentid(StringUtil.toNotNullStr(contentids[1]));	
										list.add(luceneBean);
										
										contentPush = new ContentPush();
										contentPush.setContentid(contentids[1]);
										ReportServiceImpl.contentPush.put(contentids[1], contentPush);
										break;
									case 24:  //24张图片
										luceneBean.setOrder_id(adPlay.getOrderId().toString());
					            		luceneBean.setContentid(StringUtil.toNotNullStr(contentids[hour]));	
										list.add(luceneBean);
										
										contentPush = new ContentPush();
										contentPush.setContentid(contentids[hour]);
										ReportServiceImpl.contentPush.put(contentids[hour], contentPush);
										break;
									case 25:   //一个视频，24张图片
										luceneBeantemp.setOrder_id(adPlay.getOrderId().toString());
										luceneBeantemp.setContentid(StringUtil.toNotNullStr(contentids[0]));
										list.add(luceneBeantemp);
										
										contentPush = new ContentPush();
										contentPush.setContentid(contentids[0]);
										ReportServiceImpl.contentPush.put(contentids[0], contentPush);
										
										luceneBean.setOrder_id(adPlay.getOrderId().toString());
					            		luceneBean.setContentid(StringUtil.toNotNullStr(contentids[hour+1]));	
										list.add(luceneBean);
										
										contentPush = new ContentPush();
										contentPush.setContentid(contentids[hour+1]);
										ReportServiceImpl.contentPush.put(contentids[hour+1], contentPush);
										break;

									default:
										break;
									}
									break;
								}					            		
		            		}
		            	}
	            	}	            	
	            	//list.add(luceneBean);
	            	
	            }
	            catch(Exception e)
	            {
	            	e.printStackTrace();
	    			//logger.error(line);
	            }
	        }
	        br.close();
	        br=null;
		}
	    catch (Exception e)
	    {
	        	
	    }
        finally
        {
        	if (br!=null)
        	{
        		try
        		{
        			br.close();
        		}
        		catch (Exception e)
        	    {
    	        	
        	    }
        	}
        }
		return list;
	}
}
