package com.dvnchina.advertDelivery.order.dao;

import java.util.List;
import java.util.Map;




public interface GenerateQuestionReportDao {
    
    /**
     * 
     * @description: 获取结束的问卷订单报表表头信息(包括广告商,问卷名称,问卷积分,订单起始日期) 
     * @return 
     * List<Map<String,Object>>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-3 上午10:57:24
     */
    public List<Map<String, Object>> getQuestionReportTitle();
    
    /**
     * 
     * @description: 获取结束的问卷订单报表表细节信息(包括tvn号码,问题,答案,用户所属城市,用户所属街道以及问卷提交时间)  
     * @param orderId
     * @param questionId
     * @return 
     * List<Map<String,Object>>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-3 下午03:50:29
     */
    public List<Map<String, Object>> getQuestionReportContent(Integer orderId,Integer questionId);
    
    /**
     * 
     * @description: 获取问题列表
     * @param id
     * @return 
     * List<Map<String,Object>>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-9 下午05:56:33
     */
    public List<Map<String, Object>> getQuestionList(Integer id);
    
    
    /**
     * 
     * @description: 获取问卷提交次数
     * @param orderId
     * @param questionId
     * @return 
     * List<Map<String,Object>>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-9 下午06:20:17
     */
    public List<Map<String, Object>> getSubmitCount(Integer orderId,Integer questionId);
    
    
    /**
     * 
     * @description: 获取每一个问题的答案数目 
     * @param id
     * @param question
     * @return 
     * List<Map<String,Object>>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-10 上午09:26:55
     */
    public int getAnswerCount(Integer id,String question);
    
    /**
     * 
     * @description: 获取每次提交问卷的内容 
     * @param submitId
     * @param tvn
     * @return 
     * List<Map<String,Object>>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-10 上午10:56:04
     */
    public List<Map<String, Object>> getAnswerContent(Integer submitId,String tvn);
    
    /**
     * 
     * @description: 获取每一个问题的详细答案
     * @param id
     * @param question
     * @return 
     * int
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-10 下午07:12:32
     */
    public List<Map<String, Object>> getAnswerList(Integer id,String question);
    
}
