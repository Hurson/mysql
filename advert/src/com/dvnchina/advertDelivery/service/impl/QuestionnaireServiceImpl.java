package com.dvnchina.advertDelivery.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;

import com.dvnchina.advertDelivery.action.QuestionnaireAction;
import com.dvnchina.advertDelivery.bean.question.OptionForJsBean;
import com.dvnchina.advertDelivery.bean.question.QuestionForJsBean;
import com.dvnchina.advertDelivery.bean.question.TemplateJsBean;
import com.dvnchina.advertDelivery.constant.MessageConstant;
import com.dvnchina.advertDelivery.constant.TemplateConstant;
import com.dvnchina.advertDelivery.dao.QuestionnaireDao;
import com.dvnchina.advertDelivery.model.Question;
import com.dvnchina.advertDelivery.model.Questionnaire;
import com.dvnchina.advertDelivery.service.QuestionnaireService;
import com.dvnchina.advertDelivery.utils.ftp.FtpUtils;

public class QuestionnaireServiceImpl implements QuestionnaireService {
	private QuestionnaireDao questionnaireDao;

	public void setQuestionnaireDao(QuestionnaireDao questionnaireDao) {
		this.questionnaireDao = questionnaireDao;
	}

	@Override
	public TemplateJsBean getQuesitonnaireContainer(String jsPath) {
		String jsContent = this.getFileContent(jsPath);
		TemplateJsBean templateJsBean = this.getJsContent(jsContent.toString());
		return templateJsBean;
	}

	private String getFileContent(String filePath) {
		StringBuffer fileContent = new StringBuffer();
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(
					new FileInputStream(filePath), TemplateConstant.ENCODING));
			while (true) {
				String temp = br.readLine();
				if (temp != null) {
					fileContent.append(temp);
				} else {
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileContent.toString();
	}

	private TemplateJsBean getJsContent(String jsContent) {
		String regex = "^\\s*(v)(a)(r)\\s*(i)(n)(f)(o)\\s*(=)\\s*";
		jsContent = jsContent.replaceFirst(regex, "");

		TemplateJsBean templateJsBean = null;
		try {
			templateJsBean = this.getTemplateJsBean4JsonString(jsContent);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return templateJsBean;
	}

	private TemplateJsBean getTemplateJsBean4JsonString(String jsonString) {
		JSONObject jsonObject = JSONObject.fromObject(jsonString);
		Map classMap = new HashMap();
		classMap.put("question", QuestionForJsBean.class);
		classMap.put("option", OptionForJsBean.class);
		TemplateJsBean templateJsBean = (TemplateJsBean) JSONObject.toBean(
				jsonObject, TemplateJsBean.class, classMap);
		return templateJsBean;
	}

	@Override
	public Integer insertQuestionnaire(Questionnaire questionnaire) {
		return questionnaireDao.save(questionnaire);
	}

	@Override
	public void insertQuestions(List<Question> questions, Integer qId) {
		for (Question question : questions) {
			if (StringUtils.isNotBlank(question.getQuestion())) {
				question.setQuestionnaireId(qId);
				questionnaireDao.save(question);
			}
		}
	}

	@Override
	public Questionnaire getQuestionnaireById(Integer id) {
		return (Questionnaire) questionnaireDao.get(Questionnaire.class, id);
	}

	@Override
	public int getQuestionnairesCount() {
		return questionnaireDao.getQuestionnairesCount();
	}

	@Override
	public List<Questionnaire> listQuestionnaireByPage(int begin, int pageSize) {
		return questionnaireDao.listQuestionnairesByPage(begin, pageSize);
	}

	@Override
	public void generateQuestionnaire(String tHtmlPath,
			TemplateJsBean templateJsBean, Questionnaire questionnaire,
			List<Question> questions) {
		String htmlContent = this.getFileContent(tHtmlPath);

		String regex = "\\s*(=)\\s*";
		htmlContent = htmlContent.replaceAll(regex, "=");

		String summaryId = templateJsBean.getSummaryId();
		String pictureId = templateJsBean.getPictureId();
		String videoId = templateJsBean.getVideoId();
		if (StringUtils.isNotBlank(summaryId)) {
			htmlContent = this.fillContent(htmlContent, summaryId,
					questionnaire.getSummary(), MessageConstant.NOSUMMARY);
		}
		if (StringUtils.isNotBlank(pictureId)) {
			htmlContent = this.fillSrc(htmlContent, pictureId, questionnaire
					.getPicturePath(), MessageConstant.NOPICTURE);
		}
		if (StringUtils.isNotBlank(videoId)) {
			htmlContent = this.fillSrc(htmlContent, videoId, questionnaire
					.getVideoPath(), MessageConstant.NOVIDEO);
		}
		List<QuestionForJsBean> questionBeans = templateJsBean.getQuestion();
		for (int i = 0; i < questions.size(); i++) {
			Question q = questions.get(i);
			if (StringUtils.isNotBlank(q.getQuestion())) {
				int index = this.getIndexLocation(htmlContent, questionBeans
						.get(i).getQuestionId());
				if (index != -1) {
					int begin = htmlContent.indexOf(">", index);
					int end = htmlContent.indexOf("</", begin);
					htmlContent = this.replaceByLocation(htmlContent, begin,
							end, q.getQuestion());
					String optionStr = q.getOptions();
					String[] options = optionStr.split(",");
					List<OptionForJsBean> optionBeans = questionBeans.get(i)
							.getOption();
					int j = 0;
					for (; j < options.length; j++) {
						htmlContent = this.fillContent(htmlContent, optionBeans
								.get(j).getOptionId(), options[j],
								MessageConstant.NOOPTION);
					}
					for (; j < optionBeans.size(); j++) {
						htmlContent = this.fillNull(htmlContent, optionBeans
								.get(j).getOptionDiv());

					}
				} else {
					// 日志模板html文件没有找到id为summaryId的区域
				}
			} else {
				htmlContent = this.fillNull(htmlContent, questionBeans.get(i)
						.getQuestionDiv());
			}
		}
		this.StringToFile(htmlContent, questionnaire.getQuestionnaireId());
	}

	private void StringToFile(String content,String fileName) {
		String filePath = QuestionnaireAction.QUESTIONNAIRE_TEMP_DIR+"/"+fileName+".html";
		File file = new File(filePath);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			FileOutputStream fos = new FileOutputStream(filePath);
			OutputStreamWriter osw = new OutputStreamWriter(fos);
			BufferedWriter bw = new BufferedWriter(osw);
			bw.write(content);
			bw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String fillContent(String htmlContent, String contentId,
			String content, String msg) {
		int index = this.getIndexLocation(htmlContent, contentId);
		if (index != -1) {
			int begin = htmlContent.indexOf(">", index);
			int end = htmlContent.indexOf("</", begin);
			htmlContent = this.replaceByLocation(htmlContent, begin, end,
					content);
		} else {
			// 日志模板html文件没有找到id为summaryId的区域 msg
		}
		return htmlContent;
	}

	private String fillSrc(String htmlContent, String srcId, String src,
			String msg) {
		int index = this.getIndexLocation(htmlContent, srcId);
		if (index != -1) {
			int begin = htmlContent.indexOf("src=", index);
			int end = htmlContent.indexOf(" ", begin);
			int end1 = htmlContent.indexOf("/>", begin);
			int end2 = htmlContent.indexOf(">", begin);
			end1=end1<end2?end1:end2;
			end = end<end1?end:end1;
			String str = "src='" + src + "'";
			htmlContent = this.replaceByLocation(htmlContent, begin - 1, end,
					str);
		} else {
			// 日志模板html文件没有找到id为summaryId的区域 msg
		}
		return htmlContent;
	}

	private String fillNull(String htmlContent, String divId) {
		int index = this.getIndexLocation(htmlContent, divId);
		int begin = 0;
		String newStr = " display:none;";
		int begin1 = htmlContent.indexOf("style=\"", index);
		int begin2 = htmlContent.indexOf("style='", index);
		int end = htmlContent.indexOf(">", index);
		if (begin1 != -1 && begin1 < end) {
			begin = begin1 + 7;
		} else if (begin2 != -1 && begin2 < end) {
			begin = begin2 + 7;
		} else {
			begin = end;
			newStr = " style='display:none' ";
		}
		htmlContent = this.replaceByLocation(htmlContent, begin - 1, begin,
				newStr);
		return htmlContent;
	}

	private int getIndexLocation(String htmlContent, String idStr) {
		int[] indexs = { -1, -1, -1, -1 };
		indexs[0] = htmlContent.indexOf("id='" + idStr + "'");
		indexs[1] = htmlContent.indexOf("ID='" + idStr + "'");
		indexs[2] = htmlContent.indexOf("id=\"" + idStr + "\"");
		indexs[3] = htmlContent.indexOf("ID=\"" + idStr + "\"");
		for (int i = 0; i < indexs.length; i++) {
			if (indexs[i] != -1) {
				return indexs[i];
			}
		}
		return -1;
	}

	private String replaceByLocation(String str, int begin, int end,
			String newStr) {
		StringBuffer buffer = new StringBuffer();
		String s1 = "";
		String s2 = "";
		try {
			s1 = str.substring(0, begin + 1);
			s2 = str.substring(end, str.length());
		} catch (Exception ex) {
			// throw new Throwable("替换的位数大于字符串的位数");
		}
		return buffer.append(s1).append(newStr).append(s2).toString();

	}

	@Override
	public void deleteQuestionnaire(Questionnaire q) {
		questionnaireDao.deleteQuestion(q.getId());
		questionnaireDao.delete(q);
	}

	@Override
	public void updateQuestionnaireState(Integer id,char state) {
		Questionnaire q = (Questionnaire) questionnaireDao.get(Questionnaire.class, id);
		q.setState(state);
		questionnaireDao.update(q);
	}
	
}
