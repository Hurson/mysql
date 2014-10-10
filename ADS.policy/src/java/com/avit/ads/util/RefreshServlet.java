package com.avit.ads.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Timer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.avit.ads.requestads.service.AdrequestProcessService;
import com.avit.ads.requestads.service.RefreshCacheService;
import com.avit.ads.requestads.service.RefreshTimerTask;
import com.avit.ads.xml.HttpClientUtil;
import com.avit.ads.xml.JaxbXmlObjectConvertor;
import com.avit.ads.xml.beans.ServerResponse;

public class RefreshServlet extends HttpServlet {
	 private static final Logger log = LoggerFactory.getLogger(RefreshServlet.class);
	protected void doGet(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
	{
		doPost(req,resp);
	}
	protected void doPost(HttpServletRequest req,HttpServletResponse resp) throws ServletException,IOException
	{
	    String responseXml = "";

        try {
        	RefreshTimerTask timertask= new RefreshTimerTask();
        	Timer timer = new Timer();
			timer.schedule(timertask, new Date());
        } 
        catch (Exception e) {
//            log.error("", e);
//           JsonErrorResponse errorResponse = JsonErrorResponse.buildServerErrorResponse();
//        	responseXml="";
//            result = new JsonUtil().returnErrorResponse(errorResponse);
        }

        try {
            //resp.setContentType("text/html");
            //resp.setCharacterEncoding(Constants.CHAR_ENCODING);
            ServerResponse response = new ServerResponse();
			response.setCode(0);
			response.setMessage("");
			responseXml = JaxbXmlObjectConvertor.getInstance().toXML(response);
            PrintWriter out = resp.getWriter();
            out.print(responseXml);
            out.flush();
            out.close();
        } catch (Exception e) {
            log.error("", e);
        }
	}
}
