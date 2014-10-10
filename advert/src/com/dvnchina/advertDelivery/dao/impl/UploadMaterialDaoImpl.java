package com.dvnchina.advertDelivery.dao.impl;

import java.io.BufferedInputStream;
import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

import org.hibernate.Hibernate;
import org.hibernate.Query;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate3.support.HibernateDaoSupport;

import com.dvnchina.advertDelivery.model.MessageMeta;
import com.dvnchina.advertDelivery.dao.UploadMaterialDao;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.ImageMeta;
import com.dvnchina.advertDelivery.model.Resource;
import com.dvnchina.advertDelivery.model.VideoMeta;

public class UploadMaterialDaoImpl extends HibernateDaoSupport implements UploadMaterialDao {

	@Override
	public void addImageMaterial(ImageMeta material) {
		getHibernateTemplate().save(material);
	}

	@Override
	public void addMessageMaterial(MessageMeta material) {
		getHibernateTemplate().save(material);
	}

	@Override
	public void addVideoMaterial(VideoMeta material) {
		getHibernateTemplate().save(material);
	}

	@SuppressWarnings("unchecked")
	@Override
	public Integer getResourceId(Integer type,String materialName,String uploadPath) {
		Integer resourceId = null;
		List<Integer> maxId = new ArrayList<Integer>();
		String hql = "";
		switch (type) {
		case 0:
			hql = "select vm.id from VideoMeta as vm where vm.name='"+materialName+"'" +" and vm.temporaryFilePath='"+uploadPath + "'";
			break;
		case 1:
			hql = "select im.id from ImageMeta as im where im.name='"+materialName+"'" + " and im.temporaryFilePath='" +uploadPath + "'";
			break;
		default:
			break;
		}
		maxId = getHibernateTemplate().find(hql);
		if(maxId != null && maxId.size() > 0){
			resourceId = maxId.get(0);
		}
		return resourceId;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public MessageMeta getMessageMeta(String messageName) {
		String hql = "select * from t_text_meta_backup t where t.name='"+messageName+"'";
		Query query = this.getSession().createSQLQuery(hql);
		if(query.list() == null || query.list().size()==0){
			return null;
		}else{
			MessageMeta messageMeta = new MessageMeta();
			for(int i = 0 ; i < query.list().size();i++){
				Object[] obj = (Object[]) query.list().get(i);
				messageMeta.setId(Integer.valueOf(obj[0]+""));
				messageMeta.setName(obj[1]+"");
				Blob contentBlob = (Blob) obj[2];
				byte[] content = null;
				BufferedInputStream bis = null;
				try {
					bis = new BufferedInputStream(contentBlob.getBinaryStream());
					content = new byte[(int)contentBlob.length()];
					int len = content.length;
					int offest = 0;
					int read = 0;
					while(offest<len&&(read=bis.read(content, offest,len-offest))>0){
						offest+=read;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				messageMeta.setContent(content);
				messageMeta.setURL(obj[3]+"");
			}
			return messageMeta;
		}

	}

	@Override
	public void addResource(Resource resource) {
		getHibernateTemplate().save(resource);
	}

	@Override
	public Resource getResource(String messageName,Integer resourceId) {
		Resource resource = null;
		String hql = "from Resource as t where t.resourceName='"+ messageName +"'" + " and t.resourceId='" + resourceId+"'";
		if(getHibernateTemplate().find(hql).size() > 0){
			resource = (Resource) getHibernateTemplate().find(hql).get(0);
		}
		return resource;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public List<ContractBackup> getAllContract() {
		List<ContractBackup> contractList = null;
		String sql = "select t.id,t.contract_number as contractNumber from t_contract_backup t";
		contractList = this.getSession().createSQLQuery(sql)
			.addScalar("id", Hibernate.INTEGER)
			.addScalar("contractNumber", Hibernate.STRING)
			.setResultTransformer(Transformers.aliasToBean(ContractBackup.class)).list();
		return contractList;
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public ContractBackup getContract(String contractCode) {
		ContractBackup contract = null;
		String sql = "select t.effective_start_date as effectiveStartDate,t.effective_end_date as effectiveEndDate " +
				"from t_contract t where t.contract_number like '%"+ contractCode +"%'";
		List<ContractBackup> contractList = this.getSession().createSQLQuery(sql)
		.addScalar("effectiveStartDate", Hibernate.DATE)
		.addScalar("effectiveEndDate", Hibernate.DATE)
		.setResultTransformer(Transformers.aliasToBean(ContractBackup.class)).list();
		if(contractList.size()>0){
			contract = contractList.get(0);
		}
		return contract;
	}

	@SuppressWarnings("unchecked")
	@Override
	public String getMetarialPath(String contractId) {
		String sql = "select t.metarial_path from t_contract t where t.id="+Integer.valueOf(contractId);
		List<String> pathList = this.getSession().createSQLQuery(sql).list();
		String matarialPath = "";
		if(pathList.size()>0){
			matarialPath = pathList.get(0);
		}
		return matarialPath;
	}

}
