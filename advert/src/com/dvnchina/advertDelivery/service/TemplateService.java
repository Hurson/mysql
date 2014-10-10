package com.dvnchina.advertDelivery.service;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipException;

import org.htmlparser.util.ParserException;

import com.dvnchina.advertDelivery.model.QuestionnaireTemplate;

public interface TemplateService {
	public Map checkZip(File file) throws ZipException, ParserException, IOException;

	public QuestionnaireTemplate getTemplateByName(String fileName) throws Exception;
	
	public void insertTemplate(QuestionnaireTemplate template);

	public Map<String, String> saveTemplateFile(File file, String targetPath) throws Exception;

	public void updateTemplate(QuestionnaireTemplate template);

	public int getTemplatesCount();

	public List<QuestionnaireTemplate> listTemplatesByPage(int begin, int pageSize);

	public void deleteTemplate(Integer templateId);

	public QuestionnaireTemplate getTemplateById(Integer templateId);
}
