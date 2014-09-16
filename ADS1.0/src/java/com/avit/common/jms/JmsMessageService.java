package com.avit.common.jms;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.apache.log4j.Logger;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;

/**
 * jms消息服务
 */
public class JmsMessageService{

	private static final Logger logger = Logger.getLogger(JmsMessageService.class);

	private JmsTemplate jmsTemplate;
	
	public JmsTemplate getJmsTemplate() {
		return jmsTemplate;
	}

	public void setJmsTemplate(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	/**
	 * 同步资源
	 */
	public void synResources() {
		try {
			jmsTemplate.send(new MessageCreator() {
				public Message createMessage(Session session)throws JMSException {
					Message message = session.createMessage();
					return message;
				}
			});
		} catch (Exception e) {
			logger.error("jms同步资源错误", e);
		}
	}
}
