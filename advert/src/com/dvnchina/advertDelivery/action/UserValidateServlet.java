package com.dvnchina.advertDelivery.action;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentFactory;
import org.dom4j.Element;

import com.dvnchina.advertDelivery.model.CpsCategory;
import com.dvnchina.advertDelivery.model.CpsPosition;
import com.dvnchina.advertDelivery.model.CpsTemplate;


public class UserValidateServlet extends HttpServlet {

	     protected void service(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String uuid = null;
		String businessInfoId = null;
		PrintWriter out = null;
		String xmlInfo = null;
		
		
		uuid = request.getParameter("stbId");
		businessInfoId = request.getParameter("businessInfoId");
		
		if ((StringUtils.isNotBlank(uuid))&&(StringUtils.isNotBlank(businessInfoId))) {
			//返回ispc 鉴权时的 xml 格式
			xmlInfo = generateXMLForUserValidate(uuid, businessInfoId);
		}else{
			//模拟生成Template和category信息
			Map map = generateTemplateAndCategoryInfo(); 
			xmlInfo = generateCategoryInfoFromCps((List)(map.get("template")),(List)(map.get("category")));
		}
		response.setCharacterEncoding("UTF-8");
		out = response.getWriter();
		out.write(xmlInfo);
		out.close();
	}
	/**
	 * 模拟iscp鉴权时使用
	 * @param uuid
	 * @param businessInfoId
	 * @return
	 */
	public static String generateXMLForUserValidate(String uuid,String businessInfoId){
		DocumentFactory factory = null;
		Document doc = null;
		Element infoElement = null;
		Element businessNameElement = null;
		Element businessDescriptionElement = null;
		Element billingModelNameElement = null;
		Element billingModelTypeElement = null;
		Element billingModelPriceElement = null;
		Element billingModelDurationElement = null;
		Element businessUrlElement = null;
		Element typeElement = null;
		Element statusElement = null;
		
		if(StringUtils.isNotBlank(uuid)&&StringUtils.isNotBlank(businessInfoId)){
			factory = new DocumentFactory();
			doc = factory.createDocument();
			infoElement = doc.addElement("info");
			businessNameElement = infoElement.addElement("businessName");
			businessNameElement.addText("包月20元Ԫ");
			businessDescriptionElement = infoElement.addElement("businessDescription");
			businessDescriptionElement.addText("");
			billingModelNameElement = infoElement.addElement("billingModelName");
			billingModelNameElement.addText("包月20元无折扣");
			billingModelTypeElement = infoElement.addElement("billingModelType");
			billingModelTypeElement.addText("2");
			billingModelPriceElement = infoElement.addElement("billingModelPrice");
			billingModelPriceElement.addText("2000");
			billingModelDurationElement = infoElement.addElement("billingModelDuration");
			billingModelDurationElement.addText("");
			businessUrlElement = infoElement.addElement("businessUrl");
			businessUrlElement.addText("");
			typeElement = infoElement.addElement("type");
			typeElement.addText("vod");
			statusElement = infoElement.addElement("status");
			statusElement.addText("3");
		}else{
			System.out.println("参数不足：uuid="+uuid+"\nbusinessInfoId="+businessInfoId);
		}
		return doc.asXML();
	}
	
	/**
	 * 模拟cps生成节点信息
	 * @return
	 */
	public static String generateCategoryInfoFromCps(List<CpsTemplate> templateList,List<CpsCategory> categoryList){
		DocumentFactory factory = null;
		Document doc = null;
		factory = new DocumentFactory();
		doc = factory.createDocument();
		Element advertsElement = null;
		Element templatesElement = null;
		Element categoriesElement = null;
		Element templateElement = null;
		Element positionElement = null;
		advertsElement = doc.addElement("adverts");
		templatesElement = advertsElement.addElement("templates");
		
		categoriesElement = advertsElement.addElement("categories");

		//在 xml 中分别生成根节点<advert>下的 <Template> 和 <position> 属性
		// 这里需要注意的是 在 <Template>下，有多个<position>子节点
		if(templateList!=null && templateList.size()>0){
			for (CpsTemplate template : templateList) {
				templateElement = templatesElement.addElement("template").addAttribute("id",template.getId()+"").addAttribute("templateName",template.getTemplateName()).addAttribute("effectPicture", template.getEffectPicture()).addAttribute("type", template.getType()+"");

				List<CpsPosition> positionList = template.getPositionList();
				if (positionList!=null&&positionList.size()>0) {
					
					for (CpsPosition position : positionList) {
						positionElement = templateElement.addElement("position");
						positionElement.addAttribute("id",
								position.getId() + "").addAttribute("cname",
								position.getCname()).addAttribute("width",
								position.getWidth() + "").addAttribute(
								"height", position.getHeight() + "")
								.addAttribute("top", position.getTop() + "")
								.addAttribute("left", position.getLeft() + "").addAttribute("pictureMaterialSpeciId", position.getPictureMaterialSpeciId() +"")
								.addAttribute("videoMaterialSpeciId", position.getVideoMaterialSpeciId()+"").addAttribute("contentMaterialSpeciId", position.getContentMaterialSpeciId()+"")
								.addAttribute("questionMaterialSpeciId",position.getQuestionMaterialSpeciId()+"")
								.addAttribute("isOverlying",position.getIsOverlying()+"").addAttribute("desc", position.getDescribe()+"")
								.addAttribute("eigenValue", position.getEigenValue()).addAttribute("modifyTime",position.getModifyTime()+"").addAttribute("positionTypeCode",position.getPositionTypeCode());
					}
				}
			}
		}
		
		if(categoryList!=null && categoryList.size()>0){
			for (CpsCategory category : categoryList) {
				//categoriesElement.addElement("category").addAttribute("id", category.getId()+"").addAttribute("categoryId",category.getCategoryId()).addAttribute("categoryName",category.getCategoryName()).addAttribute("treeId",category.getTreeId()+"").addAttribute("treeName",category.getTreeName()).addAttribute("templateId", category.getTemplateId()+"").addAttribute("networkId", category.getNetworkId()+"").addAttribute("userLevel", category.getUserLevel()+"").addAttribute("industryType", category.getIndustryType()+"").addAttribute("type", category.getType()+"").addAttribute("modifyTime", category.getModifyTime()+"");
				categoriesElement.addElement("category").addAttribute("id", category.getId()+"").addAttribute("categoryId",category.getCategoryId()).addAttribute("categoryName",category.getCategoryName()).addAttribute("treeId",category.getTreeId()+"").addAttribute("treeName",category.getTreeName()).addAttribute("templateId", category.getTemplateId()+"").addAttribute("type", category.getType()+"").addAttribute("modifyTime", category.getModifyTime()+"");
			}
		}
		
		System.out.println(doc.asXML());
		return doc.asXML();
	}
	
	/**
	 * 模拟生成Template和category信息
	 */
	public static Map generateTemplateAndCategoryInfo(){
		List<CpsTemplate> templateList = new ArrayList<CpsTemplate>();
		List<CpsCategory> categoryList = new ArrayList<CpsCategory>();
		List<CpsPosition> positionList1 = new ArrayList<CpsPosition>();
		List<CpsPosition> positionList2 = new ArrayList<CpsPosition>();
		List<CpsPosition> positionList3 = new ArrayList<CpsPosition>();
		List<CpsPosition> positionList4 = new ArrayList<CpsPosition>();
		List<CpsPosition> positionList5 = new ArrayList<CpsPosition>();
		
		Map map = new HashMap();
		
		CpsPosition position1 = new CpsPosition();
		position1.setId(1);
		position1.setCname("第一模板-广告位一");
		position1.setWidth(1);
		position1.setHeight(1);
		position1.setTop(1);
		position1.setLeft(1);
//------------------------------------------------------		
		position1.setDescribe("第一模板-广告位一");
		position1.setPictureMaterialSpeciId(11);
		position1.setVideoMaterialSpeciId(11);
		position1.setContentMaterialSpeciId(11);
		position1.setQuestionMaterialSpeciId(11);
		position1.setIsOverlying(0);
		position1.setCCode("1111");
		position1.setEigenValue("###1111");
	//	position1.setModifyTime("2013-02-26");
		position1.setModifyTime("2013-02-11 10:10:10");
		position1.setPositionTypeCode("1111###");
		
		positionList1.add(position1);
		
		CpsPosition position2 = new CpsPosition();
		position2.setId(2);
		position2.setCname("第一模板-广告位二");
		position2.setWidth(11);
		position2.setHeight(11);
		position2.setTop(11);
		position2.setLeft(11);
//----------------------------------------------------
		position2.setDescribe("第一模板-广告位二");
		position2.setPictureMaterialSpeciId(12);
		position2.setVideoMaterialSpeciId(12);
		position2.setContentMaterialSpeciId(12);
		position2.setQuestionMaterialSpeciId(12);
		position2.setIsOverlying(0);
		position2.setCCode("1122");
		position2.setEigenValue("###1122");
		position2.setModifyTime("2013-02-22 10:10:10");
		position2.setPositionTypeCode("1122###");
		
		positionList1.add(position2);
		
		CpsPosition position3 = new CpsPosition();
		position3.setId(3);
		position3.setCname("第一模板-广告位三");
		position3.setWidth(33);
		position3.setHeight(33);
		position3.setTop(33);
		position3.setLeft(33);
		positionList3.add(position3);
		
		CpsPosition position4 = new CpsPosition();
		position4.setId(4);
		position4.setCname("第四模板-广告位一");
		position4.setWidth(44);
		position4.setHeight(44);
		position4.setTop(44);
		position4.setLeft(44);
//----------------------------------------------------
		position4.setDescribe("第四模板-广告位一");
		position4.setPictureMaterialSpeciId(41);
		position4.setVideoMaterialSpeciId(41);
		position4.setContentMaterialSpeciId(41);
		position4.setQuestionMaterialSpeciId(41);
		position4.setIsOverlying(1);
		position4.setCCode("4411");
		position4.setEigenValue("###4411");
		position4.setModifyTime("2013-04-11 10:10:10");
		position4.setPositionTypeCode("4411###");
		
		positionList4.add(position4);

		
		CpsTemplate template1 = new CpsTemplate();
		template1.setId(1);
		template1.setEffectPicture("/images/template/effectDrawing/list/Hndvd_Complex_List_Index_main/Hndvd_Complex_List_Index1.jpg");
		template1.setType(0);
		template1.setTemplateName("第一模板");
		template1.setPositionList(positionList1);
		
		CpsPosition position5 = new CpsPosition();
		position5.setId(1);
		position5.setCname("第二模板-广告位一");
		position5.setWidth(2);
		position5.setHeight(2);
		position5.setTop(2);
		position5.setLeft(2);
//------------------------------------------------
		position5.setDescribe("第二模板-广告位一");
		position5.setPictureMaterialSpeciId(21);
		position5.setVideoMaterialSpeciId(21);
		position5.setContentMaterialSpeciId(21);
		position5.setQuestionMaterialSpeciId(21);
		position5.setIsOverlying(1);
		position5.setCCode("2211");
		position5.setEigenValue("###2211");
		position5.setModifyTime("2013-02-11 10:10:10");
		position5.setPositionTypeCode("2211###");
		
		positionList2.add(position5);
		
		CpsPosition position6 = new CpsPosition();
		position6.setId(2);
		position6.setCname("第二模板-广告位二");
		position6.setWidth(22);
		position6.setHeight(22);
		position6.setTop(22);
		position6.setLeft(22);
		
//-----------------------------------------------------------
		position6.setDescribe("第二模板-广告位二");
		position6.setPictureMaterialSpeciId(22);
		position6.setVideoMaterialSpeciId(22);
		position6.setContentMaterialSpeciId(22);
		position6.setQuestionMaterialSpeciId(22);
		position6.setIsOverlying(1);
		position6.setCCode("2222");
		position6.setEigenValue("###2222");
		position6.setModifyTime("2013-02-22 10:10:10");
		position6.setPositionTypeCode("2222###");
		
		positionList2.add(position6);
		
		/*Position position5 = new Position();
		position5.setId(1);
		position5.setCname("中文名称三三");
		position5.setWidth(200);
		position5.setHeight(200);
		position5.setTop(100);
		position5.setLeft(300);
		positionList3.add(position5);
		
		Position position6 = new Position();
		position6.setId(1);
		position6.setCname("中文名称44444");
		position6.setWidth(200);
		position6.setHeight(200);
		position6.setTop(100);
		position6.setLeft(300);
		positionList4.add(position6);
		
		Position position7 = new Position();
		position7.setId(1);
		position7.setCname("中文名称55555");
		position7.setWidth(200);
		position7.setHeight(200);
		position7.setTop(100);
		position7.setLeft(300);
		positionList5.add(position7);*/
		
		CpsTemplate template2 = new CpsTemplate();
		template2.setId(2);
		template2.setEffectPicture("/images/template/effectDrawing/list/Hndvd_Complex_List_Index_main/Hndvd_Complex_List_Index2.jpg");
		template2.setType(1);
		template2.setTemplateName("第二模板");
		template2.setPositionList(positionList2);
		
		CpsTemplate template3 = new CpsTemplate();
		template3.setId(3);
		template3.setEffectPicture("/images/template/effectDrawing/list/Hndvd_Complex_List_Index_main/Hndvd_Complex_List_Index3.jpg");
		template3.setType(1);
		template3.setTemplateName("第三模板");
		//template3.setPositionList(positionList2);
		
		
		CpsTemplate template4 = new CpsTemplate();
		template4.setId(4);
		template4.setEffectPicture("http://www.google.com.hk/4.jpg");
		template4.setType(1);
		template4.setTemplateName("第四模板");
		template4.setPositionList(positionList4);
		
		/*Template template5 = new Template();
		template5.setId(5);
		template5.setEffectPicture("http://www.google.com.hk/5.jpg");
		template5.setType(1);
		template5.setTemplateName("5555555名称");
		template5.setPositionList(positionList5);*/
		
		templateList.add(template1);
		templateList.add(template2);
		templateList.add(template3);
		//templateList.add(template3);
		templateList.add(template4);
		//templateList.add(template5);
		
		
		CpsCategory category1 = new CpsCategory();
		category1.setId(1);
		category1.setCategoryId("c1");
		category1.setCategoryName("分类一");
		category1.setTemplateId(1);
		category1.setNetworkId(0);
		category1.setUserLevel(0);
		category1.setIndustryType(0);
		category1.setType(8);
		category1.setTreeId(5251992);
		category1.setTreeName("河南有线1次复制[高清]");
		
//----------------------------------------------------------------------
		category1.setModifyTime("2013-02-26 10:10:10");
		
		categoryList.add(category1);
		
		CpsCategory category2 = new CpsCategory();
		category2.setId(2);
		category2.setCategoryId("c2");
		category2.setCategoryName("分类二");
		category2.setTemplateId(2);
		category2.setNetworkId(0);
		category2.setUserLevel(0);
		category2.setIndustryType(0);
		category2.setType(8);
		category2.setTreeId(5251993);
		category2.setTreeName("河南有线2次复制[高清]");
//-------------------------------------------
		category2.setModifyTime("2013-02-26 10:10:10");
		
		categoryList.add(category2);
		

		
		/*Template template3 = new Template();
		template3.setId(3);
		template3.setEffectPicture("33http://www.google.com.hk/1.jpg");
		template3.setType(3);
		template3.setTemplateName("333模板名称 标清");
		template3.setPositionList(positionList3);
		templateList.add(template3);*/
		
		CpsCategory category3 = new CpsCategory();
		category3.setId(789);
		category3.setCategoryId("c789");
		category3.setCategoryName("节点名称3");
		category3.setTemplateId(4);
		category3.setNetworkId(0);
		category3.setUserLevel(0);
		category3.setIndustryType(0);
		category3.setType(8);
		category3.setTreeId(5251994);
		category3.setTreeName("节点名称3");
//--------------------------------------------------
		category3.setModifyTime("2013-02-26 10:10:10");
		
		categoryList.add(category3);
		
		CpsCategory category4 = new CpsCategory();
		category4.setId(790);
		category4.setCategoryId("c790");
		category4.setCategoryName("河南有线3次复制[高清]");
		category4.setTemplateId(3);
		category4.setNetworkId(0);
		category4.setUserLevel(0);
		category4.setIndustryType(0);
		category4.setType(8);
		category4.setTreeId(5251995);
		category4.setTreeName("节点名称4");
//---------------------------------------------------------------
		category4.setModifyTime("2013-02-26 10:10:10");
		
		categoryList.add(category4);
		
		/*Category category5 = new Category();
		category5.setId(5);
		category5.setCategoryId("55555");
		category5.setCategoryName("节点名称5");
		category5.setTemplateId(5);
		category5.setNetworkId(0);
		category5.setUserLevel(0);
		category5.setIndustryType(0);
		category5.setType(8);
		categoryList.add(category5);*/
		
		map.put("template",templateList);
		map.put("category",categoryList);
		return map;
	}
	
	public static void main(String[] args) {
		System.out.println(generateXMLForUserValidate("123","abcd"));
	}
	 
	
	
	
	
 }