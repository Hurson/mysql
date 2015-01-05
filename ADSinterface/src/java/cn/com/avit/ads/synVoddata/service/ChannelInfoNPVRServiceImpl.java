package cn.com.avit.ads.synVoddata.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.avit.ads.synVoddata.bean.ChannelInfoNPVR;
import cn.com.avit.ads.synVoddata.bean.Productinfo;
import cn.com.avit.ads.synVoddata.dao.ChannelInfoNPVRDao;
@Service("ChannelInfoNPVRService")
public class ChannelInfoNPVRServiceImpl implements ChannelInfoNPVRService{
	
	@Inject
	ChannelInfoNPVRDao channelInfoNPVRDao;
	private Logger logger = Logger.getLogger(ChannelInfoNPVRServiceImpl.class);
	public void deleteChannelInfoNPVRList(String netWorkID) {
		channelInfoNPVRDao.deleteChannelInfoNPVRList(netWorkID);
		
	}
	public void insertChannelInfoNPVRs(List<ChannelInfoNPVR> channelInfoNPVRs) {
		channelInfoNPVRDao.insertChannelInfoNPVRs(channelInfoNPVRs);
		
	}
	public void insertChannelInfoNPVR(ChannelInfoNPVR channelInfoNPVR) {
		channelInfoNPVRDao.insertChannelInfoNPVR(channelInfoNPVR);
		
	}
	public void execProc() {
		channelInfoNPVRDao.execProc();
		
	}


}
