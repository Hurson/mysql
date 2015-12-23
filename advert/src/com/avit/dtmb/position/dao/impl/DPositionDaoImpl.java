package com.avit.dtmb.position.dao.impl;

import java.sql.SQLException;
import java.util.Collection;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

import com.avit.dtmb.position.bean.DAdPosition;
import com.avit.dtmb.position.dao.DPositionDao;
import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.TextSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
@Repository("DPositionDao")
public class DPositionDaoImpl extends BaseDaoImpl implements DPositionDao {

	@Override
	public PageBeanDB queryDPositionList(int pageNo,
			int pageSize) {
		String hql = "from DAdPosition";
		int rowcount = this.getTotalCountHQL(hql, null);
		PageBeanDB page = new PageBeanDB();
		if(pageNo==0){
			pageNo=1;
		}
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1)/pageSize + 1;
		if(rowcount==0){
			pageNo = 1;
			totalPage = 0;
		}
		if (pageNo <= 0) {
			pageNo = 1;
		} else if (pageNo > totalPage) {
			pageNo = totalPage;
		}
		page.setTotalPage(totalPage);
		page.setPageNo(pageNo);
		List list = this.getListForPage(hql, null, pageNo, pageSize);
		page.setDataList(list);
		return page;
	}
	
	@Override
	public DAdPosition GetDPositionById(Integer id) {
		return this.getHibernateTemplate().get(DAdPosition.class, id);
	}
	
	@Override
	public void updateAdvertPosition(DAdPosition ad) {
		if(ad == null){
			return;
		}
		String sql = "update d_advertposition set bg_image_path=?,coordinate=?,domain=?,description=? where id=?";
		this.executeBySQL(sql, new Object[]{ad.getBackgroundPath(),ad.getCoordinate(),ad.getDomain(),ad.getDescription(),ad.getId()});		
	}
	@Override
	public ImageSpecification getImageSpeById(Integer id) {
		return this.getHibernateTemplate().get(ImageSpecification.class, id);
	}

	@Override
	public TextSpecification getTextSpeById(Integer id) {
		return this.getHibernateTemplate().get(TextSpecification.class, id);
	}

	@Override
	public VideoSpecification getVideoSpeById(Integer id) {
		return this.getHibernateTemplate().get(VideoSpecification.class, id);
	}

	public DAdPosition getAdvertPosition(String advertPositionId) {
		return this.getHibernateTemplate().get(DAdPosition.class, advertPositionId);
	}


	




	

	
}
