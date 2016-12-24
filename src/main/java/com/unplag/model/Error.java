package com.unplag.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Error {

	private String message;
	@JsonProperty(value = "error_code")
	private String code;

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	@Override
	public String toString() {
		return code + " " + message;
	}
}
