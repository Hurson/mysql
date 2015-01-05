package cn.com.avit.ads.synVoddata.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.avit.ads.synVoddata.bean.UserRankBean;
import cn.com.avit.ads.synVoddata.dao.SynUserRankDao;
@Service("UserRankService")
public class UserRankServiceImpl implements UserRankService{
	
	@Inject
	SynUserRankDao synUserRankDao;
	private Logger logger = Logger.getLogger(UserRankServiceImpl.class);


	public void deleteUserRankList(String netWorkID) {
		synUserRankDao.deleteUserRankList(netWorkID);
		
	}

	public void insertUserRanks(List<UserRankBean> userRankBean) {
		synUserRankDao.insertUserRanks(userRankBean);
		
	}

	public void insertUserRank(UserRankBean userRankBean) {
		synUserRankDao.insertUserRank(userRankBean);
		
	}

	public void execProc() {
		synUserRankDao.execProc();
		
	}

}
