package cn.com.avit.ads.synVoddata.service;

import java.util.List;

import cn.com.avit.ads.synVoddata.bean.UserRankBean;

public interface UserRankService {
	public void deleteUserRankList(String netWorkID);
	public void insertUserRanks(List<UserRankBean> userRankBean);
	public void insertUserRank(UserRankBean userRankBean);
	public void execProc();
}
