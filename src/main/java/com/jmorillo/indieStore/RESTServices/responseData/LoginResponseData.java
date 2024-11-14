package com.jmorillo.indieStore.RESTServices.responseData;

public class LoginResponseData {
	
	private ResponseData response;
	private String message;
	private String username;
	
	
	public LoginResponseData(ResponseData response, String message, String username) {
		this.response = response;
		this.message = message;
		this.username = username;
	}

	public ResponseData getResponse() {
		return response;
	}

	public void setResponse(ResponseData response) {
		this.response = response;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
	
	
}
