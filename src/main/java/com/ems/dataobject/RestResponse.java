package com.ems.dataobject;

import org.springframework.http.HttpStatus;

public class RestResponse<T> {
	HttpStatus statusCode;
	T data;
	String responseMessage;
	
	public RestResponse(){}
	
	public RestResponse(HttpStatus statuscode,String responseMessage){
		this.statusCode = statuscode;
		this.responseMessage = responseMessage;
		
	}
	
	public HttpStatus getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(HttpStatus ok) {
		this.statusCode = ok;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	public String getResponseMessage() {
		return responseMessage;
	}
	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}
	
	

}
