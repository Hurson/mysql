package cn.com.avit.ads.synVoddata.dao;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.avit.common.page.dao.impl.CommonDaoImpl;
@Repository
public class NetWorkinfoDaoImpl extends CommonDaoImpl implements NetWorkinfoDao{

	public List getAllNetWorkinfos() {
		String hql = "from NetWorkinfo";
		
		return this.getListForAll(hql,new ArrayList(1));
	}

	

}
