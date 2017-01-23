package com.unplag.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UPdfReport {

	private String lang;
	private String status; // generate or complete
	@JsonProperty("download_url")
	private String downloadUrl; // not null only when status is complete
	private Long lifetime;

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDownloadUrl() {
		return downloadUrl;
	}

	public void setDownloadUrl(String downloadUrl) {
		this.downloadUrl = downloadUrl;
	}

	public Long getLifetime() {
		return lifetime;
	}

	public void setLifetime(Long lifetime) {
		this.lifetime = lifetime;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UPdfReport{");
		sb.append("lang='").append(lang).append('\'');
		sb.append(", status='").append(status).append('\'');
		sb.append(", downloadUrl='").append(downloadUrl).append('\'');
		sb.append(", lifetime=").append(lifetime);
		sb.append('}');
		return sb.toString();
	}
}
