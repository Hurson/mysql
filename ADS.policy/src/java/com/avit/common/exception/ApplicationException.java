package com.avit.common.exception;

import java.util.List;

/**
 * @author Weimmy

 * @date:2011-8-1
 * @version :1.0
 * 
 */
public class ApplicationException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 3184998542145188928L;
	private int code;
	private String codeMessage;
	private List<?> params;
	
	public ApplicationException(){
		super();
	}
	
	public ApplicationException(String message){
		super(message);
		this.codeMessage = message;
	}
	
	public ApplicationException(Throwable cause){
		super(cause);
	}
	
	public ApplicationException(String message,Throwable cause){
		super(message,cause);
		this.codeMessage = message;
	}
	
	public ApplicationException(int code, String codeMessage){
		super(codeMessage);
		this.code = code;
		this.codeMessage = codeMessage;
	}
	
	public ApplicationException(int code, String codeMessage, List<?> params){
		super(codeMessage);
		this.code = code;
		this.codeMessage = codeMessage;
		this.params = params;
	}
	
	public ApplicationException(int code, String codeMessage, Throwable cause){
		super(codeMessage,cause);
		this.code = code;
		this.codeMessage = codeMessage;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getCodeMessage() {
		return codeMessage;
	}

	public void setCodeMessage(String codeMessage) {
		this.codeMessage = codeMessage;
	}

	public List<?> getParams() {
		return params;
	}

	public void setParams(List<?> params) {
		this.params = params;
	}
	
}
