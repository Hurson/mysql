package com.dvnchina.advertDelivery.order.service;

import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.order.bean.QuestionReportBean;





public interface GenerateQuestionReportService {
	
    /**
     * 
     * @description: 获取结束的问卷订单报表表头信息(包括广告商,问卷名称,问卷积分,订单起始日期) 
     * @return 
     * List<QuestionReportBean>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-3 上午11:34:31
     */
    public List<QuestionReportBean> getQuestionReportTitle();
    
    
    /**
     * 
     * @description: 获取结束的问卷订单报表表细节信息(包括tvn号码,问题,答案,用户所属城市,用户所属街道以及问卷提交时间)  
     * @param orderId
     * @param questionId
     * @return 
     * List<QuestionReportBean>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-3 下午03:57:44
     */
    public List<QuestionReportBean> getQuestionReportContent(Integer orderId,Integer questionId);
    
    /**
     * 
     * @description: 获取问题列表
     * @param id
     * @return 
     * List<QuestionReportBean>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-9 下午06:01:06
     */
    public List<QuestionReportBean> getQuestionList(Integer id);
    
    /**
     * 
     * @description: 获取问卷提交次数
     * @param orderId
     * @param questionId
     * @return 
     * List<QuestionReportBean>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-9 下午06:27:49
     */
    public List<QuestionReportBean> getSubmitCount(Integer orderId,Integer questionId);
    
    /**
     * 
     * @description: 获取每个问题的答案数目
     * @param id
     * @param question
     * @return 
     * int
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-10 上午09:31:34
     */
    public int getAnswerCount(Integer id,String question);
    
    /**
     * 
     * @description: 获取每一个问题的详细答案
     * @param id
     * @param question
     * @return 
     * List<Map<String,Object>>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-11 上午09:16:17
     */
    public List<Map<String, Object>> getAnswerList(Integer id,String question);
    
    /**
     * 
     * @description: 获取每次提交问卷的内容
     * @param submitId
     * @param tvn
     * @return 
     * List<QuestionReportBean>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-10 上午11:01:48
     */
    public List<QuestionReportBean> getAnswerContent(Integer submitId,String tvn);
    
	
}
