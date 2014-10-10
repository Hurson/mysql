package com.dvnchina.advertDelivery.position.dao.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.bean.PageBeanDB;
import com.dvnchina.advertDelivery.dao.impl.BaseDaoImpl;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractADBackup;
import com.dvnchina.advertDelivery.position.bean.AdvertPosition;
import com.dvnchina.advertDelivery.position.bean.ImageSpecification;
import com.dvnchina.advertDelivery.position.bean.PositionOccupy;
import com.dvnchina.advertDelivery.position.bean.PositionPackage;
import com.dvnchina.advertDelivery.position.bean.QuestionnaireSpecification;
import com.dvnchina.advertDelivery.position.bean.TextSpecification;
import com.dvnchina.advertDelivery.position.bean.VideoSpecification;
import com.dvnchina.advertDelivery.position.dao.PositionDao;
import com.dvnchina.advertDelivery.utils.Transform;

public class PositionDaoImpl extends BaseDaoImpl implements PositionDao {
	
	/**
	 * 分页获取广告包信息
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB queryPositionPackageList(int pageNo, int pageSize){
		String hql = "from PositionPackage ";
		
		int rowcount = this.getTotalCountHQL(hql, null);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
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
	
	/**
	 * 根据ID获取广告位包信息
	 * @param id
	 * @return
	 */
	public PositionPackage getPositionPackageById(Integer id){
		return this.getHibernateTemplate().get(PositionPackage.class, id);
	}
	
	/**
	 * 根据子广告位ID获取广告位包信息
	 * @param adId
	 * @return
	 */
	public Integer getPackageTypeByAdId(Integer adId){
		String hql = "select pp.positionPackageType from AdvertPosition ap,PositionPackage pp ";
		hql += "where ap.positionPackageId=pp.id and ap.id=? ";
		List list = this.getHibernateTemplate().find(hql,new Object[]{adId});
		if(list != null && list.size()>0 ){
			return toInteger(list.get(0));
		}
		return null;
	}
	
	/**
	 * 获取子广告位详情
	 * @param id
	 * @return
	 */
	public AdvertPosition getAdvertPosition(Integer id){
		return this.getHibernateTemplate().get(AdvertPosition.class, id);
	}
	
	/**
	 * 根据ID获取图片规格
	 * @param id
	 * @return
	 */
	public ImageSpecification getImageSpeById(Integer id){
		return this.getHibernateTemplate().get(ImageSpecification.class, id);
	}
	
	/**
	 * 修改子广告位信息（坐标、宽高、描述、背景图片）
	 * @param ad
	 */
	public void updateAdvertPosition(AdvertPosition ad){
		if(ad == null){
			return;
		}
		String sql = "update t_advertposition set background_path=?,coordinate=?,width_height=?,description=? where id=?";
		this.executeBySQL(sql, new Object[]{ad.getBackgroundPath(),ad.getCoordinate(),ad.getWidthHeight(),ad.getDescription(),ad.getId()});
	}
	
	/**
	 * 根据ID获取视频规格
	 * @param id
	 * @return
	 */
	public VideoSpecification getVideoSpeById(Integer id){
		return this.getHibernateTemplate().get(VideoSpecification.class, id);
	}
	
	/**
	 * 根据ID获取字幕规格
	 * @param id
	 * @return
	 */
	public TextSpecification getTextSpeById(Integer id){
		return this.getHibernateTemplate().get(TextSpecification.class, id);
	}
	
	/**
	 * 根据ID获取问卷规格
	 * @param id
	 * @return
	 */
	public QuestionnaireSpecification getQuestionnaireSpeById(Integer id){
		return this.getHibernateTemplate().get(QuestionnaireSpecification.class, id);
	}
	
	/**
	 * 根据广告位包ID获取子广告位列表
	 * @param packageId
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public List<AdvertPosition> findADPositionByPackage(Integer packageId){
		String hql  = " from AdvertPosition where positionPackageId = ?";
		return this.getHibernateTemplate().find(hql,new Object[]{packageId});
	}
	
	/**
	 * 查询广告位包对应的合同占用情况
	 * @param ca
	 * @param pageNo
	 * @param pageSize
	 * @return
	 */
	public PageBeanDB viewPositionOccupy(ContractAD ca, int pageNo, int pageSize){
		String sql = "select cust.advertisers_name,con.contract_name,con.contract_code," +
				"date_format(ca.contract_starttime,'%Y-%m-%d') as startTime,date_format(ca.contract_endtime,'%Y-%m-%d') as endTime " +
				" from t_contract_ad ca, t_position_package pp , t_contract con, t_customer cust " +
				"where ca.ad_id = pp.id  and ca.contract_id = con.id and con.custom_id = cust.id and ca.contract_endtime > ? ";
		List params = new ArrayList();
        Date nowDate= new Date();
        Calendar c1 =Calendar.getInstance();
        c1.setTime(nowDate);
        c1.add(Calendar.DATE,-1);
        nowDate=c1.getTime();         
        params.add(nowDate);
		if(ca != null){
			if(ca.getPositionId() != null){
				sql = sql+" and pp.id = "+ca.getPositionId();
			}
			try{
			if(!StringUtils.isEmpty(ca.getStartDateStr())){
//				sql = sql+" and ( ca.contract_starttime <= str_to_date( '"+ca.getStartDateStr()+"','%Y-%m-%d') ";
//				sql = sql+" and ca.contract_endtime >= str_to_date( '"+ca.getStartDateStr()+"','%Y-%m-%d') )";
				sql = sql+" and ( ca.contract_starttime <= ? ";
				sql = sql+" and ca.contract_endtime >= ? )";
				params.add(Transform.string2Date(ca.getStartDateStr(), "yyyy-MM-dd"));
				params.add(Transform.string2Date(ca.getStartDateStr(), "yyyy-MM-dd"));
			}
			if(!StringUtils.isEmpty(ca.getEndDateStr())){
//				sql = sql+" and ( ca.contract_endtime >= str_to_date( '"+ca.getEndDateStr()+"','%Y-%m-%d') ";
//				sql = sql+" and ca.contract_starttime <= str_to_date( '"+ca.getEndDateStr()+"','%Y-%m-%d') ) ";
				sql = sql+" and ( ca.contract_endtime >= ? ";
				sql = sql+" and ca.contract_starttime <= ? ) ";
				params.add(Transform.string2Date(ca.getEndDateStr(), "yyyy-MM-dd"));
				params.add(Transform.string2Date(ca.getEndDateStr(), "yyyy-MM-dd"));
			}
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		int rowcount = this.getTotalCountSQL2(sql, params);
		PageBeanDB page = new PageBeanDB();
		if (pageNo==0){
			pageNo =1;
		}		
		page.setPageSize(pageSize);
		page.setCount(rowcount);
		int totalPage = (rowcount - 1) / pageSize + 1;
		
		
		if (rowcount == 0) {
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
		List<PositionOccupy> resultList = getPositionOccupyList(this.getListBySql2(sql, params, pageNo, pageSize));
		page.setDataList(resultList);
		return page;
	}
	
	/**
     * 查询广告位包对应的合同占用情况(查询临时表数据)
     * @param ca
     * @param pageNo
     * @param pageSize
     * @return
     */
    public PageBeanDB viewPositionOccupyForContractManager(ContractADBackup ca, int pageNo, int pageSize){
        String sql = "select cust.advertisers_name,con.contract_name,con.contract_code," +
                "date_format(ca.valid_start,'%Y-%m-%d') as startTime,date_format(ca.valid_end,'%Y-%m-%d') as endTime " +
                " from t_contract_ad_backup ca, t_position_package pp , t_contract_backup con, t_customer cust " +
                "where ca.ad_id = pp.id  and ca.contract_id = con.id and con.customer_id = cust.id ";
        if(ca.getId()!=null){
            sql=sql+" and con.id !="+ca.getId();
        }
        List params = new ArrayList();
        //Date nowDate= new Date();
        Calendar c1 =Calendar.getInstance();
        //c1.setTime(nowDate);
        //c1.add(Calendar.DATE,-1);
        //nowDate=c1.getTime();         
        //params.add(nowDate);
        if(ca != null){
            if(ca.getPositionId() != null){
                sql = sql+" and pp.id = "+ca.getPositionId();
            }
            try{
            if(!StringUtils.isEmpty(ca.getStartDateStr())){
//              sql = sql+" and ( ca.contract_starttime <= str_to_date( '"+ca.getStartDateStr()+"','%Y-%m-%d') ";
//              sql = sql+" and ca.contract_endtime >= str_to_date( '"+ca.getStartDateStr()+"','%Y-%m-%d') )";
                sql = sql+" and ( ca.valid_start <= ? ";
                sql = sql+" and ca.valid_end >= ? )";
                params.add(Transform.string2Date(ca.getStartDateStr(), "yyyy-MM-dd"));
                params.add(Transform.string2Date(ca.getStartDateStr(), "yyyy-MM-dd"));
            }
            if(!StringUtils.isEmpty(ca.getEndDateStr())){
//              sql = sql+" and ( ca.contract_endtime >= str_to_date( '"+ca.getEndDateStr()+"','%Y-%m-%d') ";
//              sql = sql+" and ca.contract_starttime <= str_to_date( '"+ca.getEndDateStr()+"','%Y-%m-%d') ) ";
                sql = sql+" and ( ca.valid_end >= ? ";
                sql = sql+" and ca.valid_start <= ? ) ";
                params.add(Transform.string2Date(ca.getEndDateStr(), "yyyy-MM-dd"));
                params.add(Transform.string2Date(ca.getEndDateStr(), "yyyy-MM-dd"));
            }
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        int rowcount = this.getTotalCountSQL2(sql, params);
        PageBeanDB page = new PageBeanDB();
        if (pageNo==0){
            pageNo =1;
        }       
        page.setPageSize(pageSize);
        page.setCount(rowcount);
        int totalPage = (rowcount - 1) / pageSize + 1;
        
        
        if (rowcount == 0) {
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
        List<PositionOccupy> resultList = getPositionOccupyList(this.getListBySql2(sql, params, pageNo, pageSize));
        page.setDataList(resultList);
        return page;
    }
	
	private List<PositionOccupy> getPositionOccupyList(List<?> resultList) {
		List<PositionOccupy> list = new ArrayList<PositionOccupy>();
		for (int i=0; i<resultList.size(); i++) {
			Object[] obj = (Object[]) resultList.get(i);
			PositionOccupy tmp = new PositionOccupy();
			tmp.setAdvertisersName((String)obj[0]);
			tmp.setContractName((String)obj[1]);
			tmp.setContractCode((String)obj[2]);
			tmp.setStartTime((String)obj[3]);
			tmp.setEndTime((String)obj[4]);
			list.add(tmp);
		}
		return list;
	}
	
	/**
	 * 根据传入的广告位包查找所有广告位包包含的广告位
	 * @param packageIds
	 * @return
	 */
	public List<AdvertPosition> findADPositionsByPackages(String packageIds){
		String hql  = " from AdvertPosition where positionPackageId in ("+packageIds+")";
		return this.getHibernateTemplate().find(hql);
	}
	
}
