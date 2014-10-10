package com.dvnchina.advertDelivery.dao;

import java.util.List;

import com.dvnchina.advertDelivery.bean.contract.ContractContractADRelation;
import com.dvnchina.advertDelivery.model.ContractAD;
import com.dvnchina.advertDelivery.model.ContractBackup;
import com.dvnchina.advertDelivery.model.TempContract;

/**
 * 合同备份表
 * @author lester
 *
 */
public interface ContractBackupDao {
	/**
	 * 新增
	 * @param contractBackup
	 * @return
	 */
	public int saveContractBackup(final ContractBackup contractBackup);
	/**
	 * 删除
	 * @param contractBackupId
	 * @return
	 */
	public int removeContractBackup(final int contractBackupId);
	/**
	 * 更新
	 * @param contractBackup
	 * @return
	 */
	public int updateContractBackup(final ContractBackup contractBackup);
	/**
	 * 批量保存 contractAdList
	 * @param contractAdList
	 * @return
	 */
	public int[] updateContractADBackupBatch(final List<ContractAD> contractAdList);
	/**
	 * 更新广告位基本信息同时，需要更新关系表中指定字段
	 * @param contractAD
	 * @return
	 */
	public int updateContractADBackup4ModifyContractBackup(ContractAD contractAD);
	/**
	 * 分页查询
	 * @param sql
	 * @param start
	 * @param end
	 * @return
	 */
	public List<ContractBackup> page(String sql, int start, int end);
	
	public List<ContractBackup> page(String sql,List param, int start, int end);
	/**
	 * 统计总数
	 * @param sql
	 * @return
	 */
	public int queryTotalCount(String sql);
	public int queryTotalCount(String sql,List param);
	/**
	 * 按条件查询
	 * @param sql
	 * @return
	 */
  	public List<ContractBackup> query(String sql);
	
  	/**
	 * 根据客户id查询
	 * @param sql
	 * @return
	 */
	public List<ContractBackup> queryCustomerBackupById(Integer id);
	/**
	 * 查询当前序列
	 * @return 当前序列的值
	 */
	public Integer queryCurrentSequece();
	/**
	 * 保存数据至 合同-广告位维护表
	 * @param contractAdList
	 * @return
	 */
	public int[] saveBatchContractAD(final List<ContractAD> contractAdList);
	/**
	 * 根据合同编号查询数据
	 * @param contractNumber 合同编号
	 * @return
	 */
	public List<ContractBackup> queryContractByContractNumber(String contractNumber);
	/**
	 * 从合同备份表中查询相关记录
	 * @param contractId 合同id
	 * @param status 状态
	 * @return
	 */
	public List<TempContract> queryContractByContractIdFromBackup(Integer contractId);
	
	/**
	 * 获取合同与合同关系表中对应关系
	 * @param contractId 合同id
	 * @param status 状态
	 * @return
	 */
	public List<ContractContractADRelation> getContractContractADRelation(Integer contractId);
	/**
	 * 编辑合同时，删除广告位
	 * @param ids
	 * @return
	 */
	public int[] removeContractBackupAd(List<Integer> ids);
	/**
	 * 审核合同
	 * @param contractBackup
	 * @return
	 */
	public int approvalContractBackup(final ContractBackup contractBackup);
	/**
	 * 更新备份表合同状态
	 * @param contractBackupId 合同id
	 * @param status 状态
	 * @return
	 */
	public int updateCotractBackupStatus(final Integer contractBackupId,final Integer status);
	
	/**
	 * 通过存储过程方式关联删除合同信息
	 */
	public int removeContractAllInfoByContractId(Integer contractBackupId);
}



