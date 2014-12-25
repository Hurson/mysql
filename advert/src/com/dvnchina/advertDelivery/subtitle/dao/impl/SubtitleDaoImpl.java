package com.dvnchina.advertDelivery.subtitle.dao.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.ReleaseArea;
import com.dvnchina.advertDelivery.subtitle.bean.SubtitleBean;
import com.dvnchina.advertDelivery.subtitle.dao.SubtitleDao;

@SuppressWarnings("rawtypes")
public class SubtitleDaoImpl extends BaseDaoImpl implements SubtitleDao {

	@Override
	public PageBeanDB findSubtitleList(SubtitleBean subtitle, int pageNo, int pageSize) {
		StringBuffer sqlBuf = new StringBuffer("SELECT t.id, t.word, t.create_time, t.push_time, t.state, t.area_codes FROM t_text_order t WHERE 1 = 1");
		if(null != subtitle){
			if(StringUtils.isNotBlank(subtitle.getWord())){
				sqlBuf.append(" AND t.word LIKE '%" + subtitle.getWord() + "%'");
			}
			if(StringUtils.isNotBlank(subtitle.getCreateDateStr())){
				sqlBuf.append(" AND TO_DAYS(t.create_time) = TO_DAYS('" + subtitle.getCreateDateStr() + "')");
			}
			if(StringUtils.isNotBlank(subtitle.getPushDateStr())){
				sqlBuf.append(" AND TO_DAYS(t.push_time) = TO_DAYS('" + subtitle.getPushDateStr() + "')");
			}
			Integer state = subtitle.getState();
			if(null != state && state > 0){
				sqlBuf.append(" AND t.state = " + state.intValue());
			}
		}
		sqlBuf.append(" ORDER BY t.state, t.create_time");
		String sql = sqlBuf.toString();
		int totalCount = this.getTotalCountSQL(sql, null);
		PageBeanDB page = new PageBeanDB(totalCount, pageNo, pageSize);
		List list = this.getListBySql(sql, null, page.getPageNo(), page.getPageSize());
		
		List<SubtitleBean> resultList = new ArrayList<SubtitleBean>();
		for(Object obj: list){
			Object[] objs = (Object[])obj;
			SubtitleBean subtitleEntity = new SubtitleBean();
			subtitleEntity.setId((Integer)objs[0]);
			subtitleEntity.setWord(limitWordLength((String)objs[1], null) );
			subtitleEntity.setCreateTime((Date)objs[2]);
			subtitleEntity.setPushTime((Date)objs[3]);
			subtitleEntity.setState((Integer)objs[4]);
			String areaCodes = (String)objs[5];
			String areaNames = getAreaNamesByCodes(areaCodes);
			subtitleEntity.setAreaCodes(areaCodes);
			subtitleEntity.setAreaNames(limitWordLength(areaNames, null));
			resultList.add(subtitleEntity);
		}
		page.setDataList(resultList);
		return page;
	}

	@Override
	public void delSubtitles(String ids) {
		String sql = "DELETE FROM t_text_order WHERE id IN(" + ids + ")";
		this.executeBySQL(sql, null);
	}

	@Override
	public void saveSubtitle(SubtitleBean entity) {
		this.saveOrUpdate(entity);
	}
	
	private String limitWordLength(String word, Integer length){
		if(null == length){
			length = 15;
		}
		if(word.length() > length){
			word = word.substring(0, length) + "...";
		}
		return word;
	}
	
	private String getAreaNamesByCodes(String areaCodes){
		List list = this.getAreaList();
		if("0".equals(areaCodes)){
			return "全部";
		}
		String areaNames = areaCodes;
		for(Object obj : list){
			ReleaseArea area = (ReleaseArea)obj;
			areaNames = areaNames.replace(area.getAreaCode(), area.getAreaName());
		}
		return areaNames;
	}

	@Override
	public SubtitleBean getSubtitleById(Integer id) {
		return this.getHibernateTemplate().get(SubtitleBean.class, id);
	}

	@Override
	public List getAreaList() {
		String hql = "from ReleaseArea ra where ra.areaCode != '152000000000'";
		return getListForAll(hql, null);
	}

}
