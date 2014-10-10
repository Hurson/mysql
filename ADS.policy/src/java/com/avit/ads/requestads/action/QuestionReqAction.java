package com.avit.ads.requestads.action;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.apache.struts2.convention.annotation.ParentPackage;
import org.apache.struts2.convention.annotation.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

import com.avit.ads.requestads.bean.AdQuestionnaireData;
import com.avit.ads.requestads.bean.Question;
import com.avit.ads.requestads.bean.TQuestionPlaylistv;
import com.avit.ads.requestads.bean.TQuestionnaireReal;
import com.avit.ads.requestads.bean.TUserQuestion;
import com.avit.ads.requestads.bean.TUserQuestionnaire;
import com.avit.ads.requestads.bean.request.AdReportList;
import com.avit.ads.requestads.bean.request.AdStatus;
import com.avit.ads.requestads.bean.request.AdStatusReportReqXmlBean;
import com.avit.ads.requestads.dao.impl.QuestionDaoImpl;
import com.avit.ads.requestads.service.ADPlayListService;
import com.avit.ads.requestads.service.AdrequestProcessService;
import com.avit.ads.requestads.service.QuestionService;
import com.avit.ads.util.ConstantsHelper;
import com.avit.ads.xml.HttpClientUtil;
import com.avit.ads.xml.JaxbXmlObjectConvertor;
import com.avit.ads.xml.beans.ServerResponse;
import com.avit.common.util.CookieUtils;
import com.avit.common.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

@ParentPackage("BasePackage")
@Namespace("/interface")
@Controller
public class QuestionReqAction extends ActionSupport {
	private static final long serialVersionUID = UUID.randomUUID()
			.getMostSignificantBits();
	static Logger logger = LoggerFactory.getLogger(QuestionReqAction.class);
	@Inject
	QuestionService questionService; 
	/**
	 * 保存问卷的信息
	 * 	@return success 成功后重定向到播控系统
	 */

	@Action(value = "handleSurveySubmit")
	public void handleSurveySubmit(){
		HttpServletRequest request = ServletActionContext.getRequest();
		// 填充问卷保存的bean
		int score=0;
		try
		{
			String answer = request.getParameter("answerJson");
			String tvn = request.getParameter("tvn");
			score = questionService.getScoreByTVN(tvn).intValue();
		    String backurl = request.getParameter("backurl");
		    String questionid = request.getParameter("questionid");
		    String orderId =  request.getParameter("orderid");
		    String idnumber = request.getParameter("idNmmber");
		    String tel =  request.getParameter("phone");
		    
			JSONArray json = JSONArray.fromObject(answer);
			JSONObject jsonObject = null;
			List<Question> list = new ArrayList<Question>();
			List<TUserQuestion> listUserquestion =new ArrayList<TUserQuestion>();
			for (int i = 0, j = json.size(); i < j; i++) {
				jsonObject = json.getJSONObject(i);
				Question question = (Question) JSONObject.toBean(jsonObject, Question.class);
				if(question.getId().toString().equals("1")){
				    list.add(question);
				    TUserQuestion temp = new TUserQuestion();
				    temp.setQuestionname(question.getQuestion());
				    temp.setQuestionnaireId(StringUtil.toLong(question.getQuestionnaireId()));
				    temp.setOptionsName(question.getOptions());
				   // short flag =1;
				    temp.setFlag(question.getId().shortValue());
				    temp.setUsersn(tvn);
				    listUserquestion.add(temp);
				}
			}
			if (tvn!=null && orderId!=null && questionid!=null)
			{
				if (listUserquestion.size()>0)
				{
					TUserQuestionnaire userQuestionnaire= new TUserQuestionnaire();
					userQuestionnaire.setOrderId(StringUtil.toInteger(orderId));
					userQuestionnaire.setUsersn(tvn);
					userQuestionnaire.setQuestionnaireId(StringUtil.toInteger(questionid));
					userQuestionnaire.setIdnumber(idnumber);
					userQuestionnaire.setTel(tel);
					
					questionService.save(listUserquestion,userQuestionnaire);
				}
			}
			score = questionService.getScoreByTVN(tvn).intValue();
		}
		catch(Exception e)
		{
			logger.error(e.getMessage());
		}
		this.print(StringUtil.toNotNullStr(score));
		// 拉起播控
		//return "success";
	}

	
	//@Action(value = "questionAction", results = { @Result(name = "success", location = "/WEB-INF/page/question/questionConfrim.jsp") })
    @Action(value = "questionAction")
   public void validateQusetionTimes(){
        HttpServletRequest request = ServletActionContext.getRequest();
        // 填充问卷保存的bean
        String tvn = request.getParameter("tvn");
        String backurl = request.getParameter("backurl");
        String questionid = request.getParameter("questionid");
        HttpServletResponse response=ServletActionContext.getResponse();
        response.setCharacterEncoding("UTF-8");
       // response.setParameter("tvn",tvn);
        try {
        	TQuestionPlaylistv retuestionPlaylistv =questionService.validateQusetionTimes(tvn,questionid);
        	int retvalue = retuestionPlaylistv.getRetvalue();
        	if (retvalue==QuestionDaoImpl.SUCCESS)
        	{
        		TQuestionnaireReal questionnaireReal=questionService.getQuestionnaireReal(questionid);
        		if (questionnaireReal!=null)
        		{
        			response.sendRedirect(questionnaireReal.getFilePath()+"?backurl="+backurl+"&tvn="+tvn+"&questionid="+questionid+"&orderid="+retuestionPlaylistv.getOrderId());
        		}
        		else
        		{
        			//response.sendRedirect(backurl);
        			response.sendRedirect(request.getContextPath()+"/question/questionConfrim.jsp?backurl="+backurl);
        		}
        	}
        	else
        	{
        		response.sendRedirect(request.getContextPath()+"/question/questionConfrim.jsp?backurl="+backurl+"&retvalue="+retvalue);
        	}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
    }
	/**
	 * 插播的广告列表转换成json
	 * @param list 插播的广告列表
	 * @return json json格式的广告列表
	 */
//	private String adListToJson(List<InsertedAd> list) {
//		if(list != null && list.size() > 0){
//			StringBuffer sb = new StringBuffer();
//			sb.append("[");
//			for (int i = 0, j = list.size(); i < j; i++) {
//				
//			 
//				InsertedAd insertedAd = list.get(i);
//				sb.append("{").append("\"adSeq\":").append(insertedAd.getAdSeq()).append(",");
//				sb.append("\"adUrl\":\"").append(insertedAd.getAdUrl()).append("\",");
//				sb.append("\"insertedTime\":").append(insertedAd.getAdUrl()).append("}");
//				if(j > i+1){
//					sb.append(",");
//				}
//			}
//			sb.append("]");
//			return sb.toString();
//		}
//		return "";
//	}
	
	/**
	 * 将内容写入对应的response中
	 * @param str 存有播出单列表的字符串
	 */
	private void print(String str) {
		try {
			ServletActionContext.getResponse().setCharacterEncoding("utf-8");
			ServletActionContext.getResponse().setContentType(
					"text/xml;charset=utf-8");
			ServletActionContext.getResponse().setHeader("Cache-Control",
					"no-cache");
			ServletActionContext.getResponse().getOutputStream()
					.write(str.getBytes("utf-8"));
		} catch (Exception e) {
			System.out.println("send response error");
		}
	}
}