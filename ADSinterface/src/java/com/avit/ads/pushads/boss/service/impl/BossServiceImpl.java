package com.avit.ads.pushads.boss.service.impl;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.avit.ads.pushads.boss.bean.TSyncBoss;
import com.avit.ads.pushads.boss.bean.TvnScore;
import com.avit.ads.pushads.boss.dao.BossDao;
import com.avit.ads.pushads.boss.service.BossService;
import com.avit.ads.syncreport.service.impl.ReportServiceImpl;
import com.avit.ads.util.DateUtil;
import com.avit.ads.util.FileDigestUtil;
import com.avit.ads.util.InitConfig;
import com.avit.ads.util.Md5Util;
import com.avit.common.ftp.service.FtpService;
import com.avit.common.util.StringUtil;
@Service("BossService")
public class BossServiceImpl implements BossService {
	@Inject
	BossDao bossDao;
	private Logger logger = Logger.getLogger(BossServiceImpl.class);
	private static String unitFlag="D05001";
//	private static String boss_server_ip = "boss_server_ip";
//	private static String boss_server_port = "boss_server_port";
//	private static String boss_user = "boss_user";
//	private static String boss_password = "boss_password";
//	private static String boss_server_path = "boss_server_path";
	private static String boss_data_temppath = "boss_data_temppath";
	private static String boss_data_retain_month = "boss_data_retain_month";
	
	private static String receive = "receive";
	private static String backup = "backup";
	
	
	public String syncDataByMonth(Date startDate,Date endDate) {
		// TODO Auto-generated method stub
		String dataline="" ;
		String month;
		String number= "0001";
		String filename = "";
		String path="";
		//删除N 月前的记录
		int retain_month = 0- StringUtil.toInt(InitConfig.getConfigMap().get(boss_data_retain_month));
		DateUtil.formatDateTime(DateUtil.addMonth(startDate, retain_month),"yyyyMMdd");
		try
		{
			List<TSyncBoss> list= bossDao.querySyncBossListBeforeTime(DateUtil.addMonth(startDate, retain_month));
			if (list!=null && list.size()>0)
			{
				for (int i=0;i<list.size();i++)
				{
					File myDelFile = new File(list.get(i).getFilename()+".DAT");
					myDelFile.delete();
					myDelFile = new File(list.get(i).getFilename()+".MD5");
					myDelFile.delete();
					//myDelFile = new File(list.get(i).getFilepath());
					//myDelFile.delete();
				}
			}
		}
		catch(Exception e)
		{
			logger.error("删除数据失败"+e.getMessage());
		}
			
		//end 删除
		//查询当月积分记录
		//如果已有同步数据则不重新生成
		if (null!=bossDao.querySyncBossListbyTime(startDate))
		{
			return null;	
		}
		List<TvnScore> listScore = bossDao.queryTvnScoreList(startDate, endDate);
		if (listScore!=null && listScore.size()>0)
		{
			//创建文件
			dataline="" ;
			month=DateUtil.formatDateTime(startDate,"yyyyMMdd");
			number= "0001";
			filename = unitFlag + month+number;
			try
			{
				//创建目录
				path = InitConfig.getConfigMap().get(boss_data_temppath)+File.separator+receive+File.separator+month;
				(new File(path)).mkdirs();
				
				BufferedWriter osw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+File.separator+filename+".DAT",false)));//Files.newBufferedWriter(InitConfig.getAdsConfigByCode(adsTypeCode).getResourcPath()+filename, "UTF-8",false);
				//写文件
				for (int i=0;i<listScore.size();i++)
				{
					dataline= listScore.get(i).getTvn()+"|"+listScore.get(i).getScore()+"|"+listScore.get(i).getMonth();
					osw.write(dataline);
					osw.newLine();
				}
				osw.close();
				//String oldmd5=FileDigestUtil.getFileNameMD5(InitConfig.getConfigMap().get("boss_data_temppath")+File.separator+filename);
				//计算md5
				File file = new File(path+File.separator+filename+".DAT");
		        String fileName = file.getName();
		        String validKey = fileName.substring(0, fileName.toUpperCase().indexOf(".DAT")) + file.length() + "\n";
		        String md5=Md5Util.byte2hex(Md5Util.encryptMD5(validKey.getBytes()));
		        osw= new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path+File.separator+filename+".MD5",false)));//Files.newBufferedWriter(InitConfig.getAdsConfigByCode(adsTypeCode).getResourcPath()+filename, "UTF-8",false);
		        dataline= md5;
		        osw.write(dataline);
				osw.newLine();
				osw.close();
				//上传文件 
				TSyncBoss syncBoss = new TSyncBoss();
				syncBoss.setCreateDate(new Date());
				syncBoss.setDataTime(startDate);
				path = InitConfig.getConfigMap().get(boss_data_temppath)+File.separator+backup+File.separator+month;
				syncBoss.setFilepath(path);
				syncBoss.setFilename(filename);
				syncBoss.setFlag(0);
				bossDao.saveSyncBoss(syncBoss);
			}
			catch(Exception e)
			{
				logger.error("生成同步数据失败"+e.getMessage());
			}			 
		}
		return null;
	}

}
