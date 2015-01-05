package cn.com.avit.ads.synVoddata.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import cn.com.avit.ads.synVoddata.bean.NetWorkinfo;
import cn.com.avit.ads.synVoddata.dao.NetWorkinfoDao;
@Service("NetWorkinfoService")
public class NetWorkinfoServiceImpl implements NetWorkinfoService{
	
	@Inject
	NetWorkinfoDao netWorkinfoDao;
	private Logger logger = Logger.getLogger(NetWorkinfoServiceImpl.class);
	public List<NetWorkinfo> getAllNetWorkinfos(){
		return netWorkinfoDao.getAllNetWorkinfos();
	}
}
