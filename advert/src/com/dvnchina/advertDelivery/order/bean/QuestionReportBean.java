package com.dvnchina.advertDelivery.order.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;

public class QuestionReportBean {
    //表头
    private Integer customerId;//广告商ID
    private String customerName;//广告商
    private String questionName;//问卷名称
    private Integer integral;//问卷积分
    private Long questionId;//问卷id
    private Long orderId;//订单ID
    private String orderCode;//订单编码
    private Date orderStartTime;//订单开始日期
    private Date orderEndTime;//订单结束日期
    //表细节
    private Date submitTime;//问卷提交时间
    private String tvn;//用户tvn号码
    private String question;//问题
    private String answer;//答案
    private String city;//用户所属城市
    private String street;//用户所属街道
    private Integer answerCount;//问题答案个数
    private Integer submitId;//问卷提交ID
    private boolean isFill;//是否已填充报表
    Map<String, String> answerMap;
    
    public Integer getCustomerId() {
        return customerId;
    }
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }
    public String getCustomerName() {
        return customerName;
    }
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    public String getQuestionName() {
        return questionName;
    }
    public void setQuestionName(String questionName) {
        this.questionName = questionName;
    }
    public Integer getIntegral() {
        return integral;
    }
    public void setIntegral(Integer integral) {
        this.integral = integral;
    }
    
    public Long getQuestionId() {
        return questionId;
    }
    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }
    public Long getOrderId() {
        return orderId;
    }
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }
    public String getOrderCode() {
        return orderCode;
    }
    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
    public Date getOrderStartTime() {
        return orderStartTime;
    }
    public void setOrderStartTime(Date orderStartTime) {
        this.orderStartTime = orderStartTime;
    }
    public Date getOrderEndTime() {
        return orderEndTime;
    }
    public void setOrderEndTime(Date orderEndTime) {
        this.orderEndTime = orderEndTime;
    }
    public Date getSubmitTime() {
        return submitTime;
    }
    public void setSubmitTime(Date submitTime) {
        this.submitTime = submitTime;
    }
    public String getTvn() {
        return tvn;
    }
    public void setTvn(String tvn) {
        this.tvn = tvn;
    }
    public String getQuestion() {
        return question;
    }
    public void setQuestion(String question) {
        this.question = question;
    }
    public String getAnswer() {
        return answer;
    }
    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public String getCity() {
        return city;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public String getStreet() {
        return street;
    }
    public void setStreet(String street) {
        this.street = street;
    }
    public Integer getAnswerCount() {
        return answerCount;
    }
    public void setAnswerCount(Integer answerCount) {
        this.answerCount = answerCount;
    }
    public Integer getSubmitId() {
        return submitId;
    }
    public void setSubmitId(Integer submitId) {
        this.submitId = submitId;
    }
    public boolean isFill() {
        return isFill;
    }
    public void setFill(boolean isFill) {
        this.isFill = isFill;
    }
    public Map<String, String> getAnswerMap() {
        return answerMap;
    }
    public void setAnswerMap(Map<String, String> answerMap) {
        this.answerMap = answerMap;
    }
    
    
    
    
    
    
    
    

}
