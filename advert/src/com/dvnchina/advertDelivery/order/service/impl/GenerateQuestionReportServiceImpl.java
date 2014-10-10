package com.dvnchina.advertDelivery.order.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.dvnchina.advertDelivery.order.bean.QuestionReportBean;
import com.dvnchina.advertDelivery.order.dao.GenerateQuestionReportDao;
import com.dvnchina.advertDelivery.order.dao.PlayList4OrderDao;
import com.dvnchina.advertDelivery.order.service.GenerateQuestionReportService;




public class GenerateQuestionReportServiceImpl implements GenerateQuestionReportService {
    private GenerateQuestionReportDao generateQuestionReportDao;
    
    /**
     * 
     * @description: 获取结束的问卷订单报表表头信息(包括广告商,问卷名称,问卷积分,订单起始日期) 
     * @return 
     * List<QuestionReportBean>
     *
     * @author: wangfei@avit.com.cn
     * @date: 2013-12-3 上午11:34:31
     */
    public List<QuestionReportBean> getQuestionReportTitle(){
        List<QuestionReportBean> questionReportList = new ArrayList<QuestionReportBean>();
        List<Map<String, Object>> questionReports = generateQuestionReportDao.getQuestionReportTitle();
        if(questionReports != null && questionReports.size()>0){         
            for (Map questionReport : questionReports) {
                QuestionReportBean temp = new QuestionReportBean();
                temp.setCustomerId(((BigDecimal)questionReport.get("customerId")).intValue());
                temp.setCustomerName((String)questionReport.get("customerName"));
                temp.setIntegral((Integer)questionReport.get("integral"));
                temp.setOrderCode((String)questionReport.get("orderCode"));
                temp.setOrderEndTime((Date)questionReport.get("orderEndTime"));
                temp.setOrderId((Long)questionReport.get("orderId"));
                temp.setOrderStartTime((Date)questionReport.get("orderStartTime"));
                temp.setQuestionId((Long)questionReport.get("questionId"));
                temp.setQuestionName((String)questionReport.get("questionName"));
                
                questionReportList.add(temp);
            }
        }
        return questionReportList;
    }
    
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
    public List<QuestionReportBean> getQuestionReportContent(Integer orderId,Integer questionId){
        List<QuestionReportBean> questionReportList = new ArrayList<QuestionReportBean>();
        List<Map<String, Object>> questionReports = generateQuestionReportDao.getQuestionReportContent(orderId,questionId);
        if(questionReports != null && questionReports.size()>0){
            
            for (Map questionReport : questionReports) {
                QuestionReportBean temp = new QuestionReportBean();
                temp.setTvn(((String)questionReport.get("tvn")));
                temp.setQuestion((String)questionReport.get("question"));                
                temp.setAnswer((String)questionReport.get("answer"));
                temp.setSubmitTime((Date)questionReport.get("submitTime"));
                temp.setCity((String)questionReport.get("city"));
                temp.setStreet((String)questionReport.get("street"));              
                
                questionReportList.add(temp);
            }
        }
        return questionReportList;
    }
    
    
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
    public List<QuestionReportBean> getQuestionList(Integer id){
        List<QuestionReportBean> questionList = new ArrayList<QuestionReportBean>();
        List<Map<String, Object>> questions = generateQuestionReportDao.getQuestionList(id);
        if(questions != null && questions.size()>0){
            
            for (Map question : questions) {
                QuestionReportBean temp = new QuestionReportBean();
                temp.setQuestion((String)question.get("question"));  
                questionList.add(temp);
            }
        }
        return questionList;
    }
    
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
    public List<QuestionReportBean> getSubmitCount(Integer orderId,Integer questionId){
        List<QuestionReportBean> questionReportList = new ArrayList<QuestionReportBean>();
        List<Map<String, Object>> questionReports = generateQuestionReportDao.getSubmitCount(orderId,questionId);
        if(questionReports != null && questionReports.size()>0){
            
            for (Map questionReport : questionReports) {
                QuestionReportBean temp = new QuestionReportBean();
                temp.setSubmitId((Integer)questionReport.get("submitId"));
                temp.setTvn(((String)questionReport.get("tvn")));            
                temp.setSubmitTime((Date)questionReport.get("submitTime"));
                temp.setCity(((BigDecimal)questionReport.get("locationCode")).toString());            
                
                questionReportList.add(temp);
            }
        }
        return questionReportList;
    }
    
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
    public int getAnswerCount(Integer id,String question){
        return generateQuestionReportDao.getAnswerCount(id,question);
    }
    
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
    public List<Map<String, Object>> getAnswerList(Integer id,String question){
        return generateQuestionReportDao.getAnswerList(id, question);
    }
    
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
    public List<QuestionReportBean> getAnswerContent(Integer submitId,String tvn){
        List<QuestionReportBean> answerContentList = new ArrayList<QuestionReportBean>();
        List<Map<String, Object>> answerContents = generateQuestionReportDao.getAnswerContent(submitId, tvn);
        if(answerContents != null && answerContents.size()>0){
            
            for (Map answerContent : answerContents) {
                QuestionReportBean temp = new QuestionReportBean();
                temp.setQuestion((String)answerContent.get("question"));
                temp.setAnswer(((String)answerContent.get("answer")));    
                temp.setFill(false);
                temp.setSubmitId((Integer)answerContent.get("submitId"));
                temp.setTvn((String)answerContent.get("tvn"));
                
                answerContentList.add(temp);
            }
        }
        return answerContentList;
    }
    

    public GenerateQuestionReportDao getGenerateQuestionReportDao() {
        return generateQuestionReportDao;
    }

    public void setGenerateQuestionReportDao(GenerateQuestionReportDao generateQuestionReportDao) {
        this.generateQuestionReportDao = generateQuestionReportDao;
    }

    
    
    
    
    
    
}
