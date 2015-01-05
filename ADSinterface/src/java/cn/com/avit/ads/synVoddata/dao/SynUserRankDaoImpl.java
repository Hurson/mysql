package cn.com.avit.ads.synVoddata.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.com.avit.ads.synVoddata.bean.UserRankBean;

import com.avit.common.page.dao.impl.CommonDaoImpl;
@Repository
public class SynUserRankDaoImpl extends CommonDaoImpl implements SynUserRankDao{




	public void execProc() {
		String hql = "Call synUserRank()";
	   this.excuteBySql(hql, null);
		
	}

	public void deleteUserRankList(String netWorkID) {
		String hql = "delete UserRankBean where areacode = "+netWorkID;
		this.delete(hql);
		
	}

	public void insertUserRanks(List<UserRankBean> UserRankBeans) {
		this.saveAll(UserRankBeans);
		
	}

	public void insertUserRank(UserRankBean UserRankBean) {
		this.save(UserRankBean);
		
	}

}
	
	



