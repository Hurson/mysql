/**
 * Copyright (c) AVIT LTD (2012). All Rights Reserved.
 * Welcome to <a href="www.avit.com.cn">www.avit.com.cn</a>
 */
package com.avit.ads.xml;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * @Description:
 * @author lizhiwei
 * @Date: 2012-5-22
 * @Version: 1.0
 */
public class JaxbXmlObjectConvertor {
	public final static String ENCODING = "UTF-8";
	private static JaxbXmlObjectConvertor instance = null;
	private JAXBContext jaxbContext = null;
	private Marshaller marshaller = null;
	private Unmarshaller unmarshaller = null;

	private JaxbXmlObjectConvertor() {
	}

	public static synchronized JaxbXmlObjectConvertor getInstance() {
		if (instance == null) {
			instance = new JaxbXmlObjectConvertor();
			instance.prepareJaxbContext();
		}
		return instance;
	}

	public synchronized void prepareJaxbContext() {
		try {
			jaxbContext = JAXBContext.newInstance(JaxbXmlObjectConvertor.class.getPackage().getName());
			// Class Marshaller controls the process of marshalling i.e: Java
			// Object --> XML
			marshaller = jaxbContext.createMarshaller();
			// Class UnMarshaller controls the process of unmarshalling i.e: XML
			// --> Java Object
			unmarshaller = jaxbContext.createUnmarshaller();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String toXML(Object obj) {
		String docu = null;
		StringWriter sw = null;
		synchronized (marshaller) {
			try {
				sw = new StringWriter();
				marshaller.marshal(obj, sw);
				docu = sw.toString();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (sw != null) {
					try {
						sw.close();
					} catch (IOException e) {
						sw = null;
					}
				}
			}
		}
		return docu;
	}

	public Object fromXML(String xml) throws Exception {
		if (xml == null) {
			return null;
		}
		ByteArrayInputStream bais = null;
		Object obj = null;

		synchronized (unmarshaller) {
			try {
				bais = new ByteArrayInputStream(xml.getBytes(ENCODING));
				obj = unmarshaller.unmarshal(bais);
			} catch (Exception e) {
				throw new Exception(e);
			} finally {
				if (bais != null) {
					try {
						bais.close();
					} catch (IOException e) {
						bais = null;
					}
				}
			}
		}
		return obj;
	}	
	
}
