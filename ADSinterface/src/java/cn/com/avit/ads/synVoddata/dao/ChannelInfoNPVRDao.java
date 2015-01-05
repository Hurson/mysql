package cn.com.avit.ads.synVoddata.dao;

import java.util.List;

import cn.com.avit.ads.synVoddata.bean.ChannelInfoNPVR;


public interface ChannelInfoNPVRDao {
	public void deleteChannelInfoNPVRList(String netWorkID);
	public void insertChannelInfoNPVRs(List<ChannelInfoNPVR> channelInfoNPVRs);
	public void insertChannelInfoNPVR(ChannelInfoNPVR channelInfoNPVR);
	public void execProc();
}
