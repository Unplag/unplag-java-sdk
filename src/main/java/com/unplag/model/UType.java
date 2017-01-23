package com.unplag.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonValue;

@JsonIgnoreProperties(ignoreUnknown = true)
public enum UType {

	MY_LIBRARY("my_library"),
	WEB("web"),
	EXTERNAL_DATABASE("external_database"),
	DOC_VS_DOCS("doc_vs_docs"),
	WEB_AND_MY_LIBRARY("web_and_my_library");

	private String value;

	UType(String value) {
		this.value = value;
	}

	@JsonValue
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
