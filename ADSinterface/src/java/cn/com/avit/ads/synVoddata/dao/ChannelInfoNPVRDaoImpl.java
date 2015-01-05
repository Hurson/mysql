package cn.com.avit.ads.synVoddata.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.avit.ads.synVoddata.bean.ChannelInfoNPVR;

import com.avit.common.page.dao.impl.CommonDaoImpl;
@Repository
public class ChannelInfoNPVRDaoImpl extends CommonDaoImpl implements ChannelInfoNPVRDao{

	
	public void execProc() {
		String hql = "Call synChannelNPVR()";
	   this.excuteBySql(hql, null);
		
	}

	public void deleteChannelInfoNPVRList(String netWorkID) {
		String hql = "delete ChannelInfoNPVR where netWorkID = "+netWorkID;
		this.delete(hql);
		
	}

	public void insertChannelInfoNPVRs(List<ChannelInfoNPVR> channelInfoNPVRs) {
		this.saveAll(channelInfoNPVRs);
		
	}

	public void insertChannelInfoNPVR(ChannelInfoNPVR channelInfoNPVR) {
		this.save(channelInfoNPVR);
		
	}
	
	


}
