package com.avit.ads.util;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.avit.ads.requestads.service.AdrequestProcessService;
import com.avit.ads.xml.HttpClientUtil;
import com.avit.ads.xml.JaxbXmlObjectConvertor;
import com.avit.ads.xml.beans.ServerResponse;

public class ReqServlet extends HttpServlet {
	private static AdrequestProcessService adrequestProcessService ;
	 private static final Logger log = LoggerFactory.getLogger(ReqServlet.class);
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
	{
		doPost(req,resp);
	}
	protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
	{
	    String responseXml = "";

        try {
           if (adrequestProcessService == null) {
        	 	 adrequestProcessService = (AdrequestProcessService) ContextHolder.getApplicationContext().getBean("AdrequestProcessService");
             
           } 
           req.setCharacterEncoding("UTF-8");
      	   String requestXml = HttpClientUtil.getRequestContent(req);
           responseXml = adrequestProcessService.GenerateADPlayList(requestXml);
      	  if(responseXml.isEmpty() || responseXml == null){
      				ServerResponse response = new ServerResponse();
      				response.setCode(1001);
      				response.setMessage("");
      				responseXml = JaxbXmlObjectConvertor.getInstance().toXML(response);
      	 }
                    //log.debug("\n\nParameter {}  \nresult {}", parameterMap, result);
         
        } 
        catch (Exception e) {
//            log.error("", e);
//            JsonErrorResponse errorResponse = JsonErrorResponse.buildServerErrorResponse();
        	responseXml="";
//            result = new JsonUtil().returnErrorResponse(errorResponse);
        }

        try {
            resp.setContentType("text/html");
            //resp.setCharacterEncoding(Constants.CHAR_ENCODING);
            PrintWriter out = resp.getWriter();
            out.print(responseXml);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("", e);
        }
	}
}
