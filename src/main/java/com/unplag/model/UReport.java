package com.unplag.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UReport {

	@JsonProperty("create_date")
	private long createDate;
	private float similarity;
	@JsonProperty("sources_count")
	private long sourcesCount;
	@JsonProperty("references_part")
	private float referencesPart;
	@JsonProperty("citations_part")
	private float citationsPart;
	@JsonProperty("citations_count")
	private int citationsCount;
	@JsonProperty("excluded_part")
	private float excludedPart;
	@JsonProperty("view_url")
	private String viewUrl;
	@JsonProperty("view_edit_url")
	private String viewEditUrl;

	public long getCreateDate() {
		return createDate;
	}

	public void setCreateDate(long createDate) {
		this.createDate = createDate;
	}

	public float getSimilarity() {
		return similarity;
	}

	public void setSimilarity(float similarity) {
		this.similarity = similarity;
	}

	public long getSourcesCount() {
		return sourcesCount;
	}

	public void setSourcesCount(long sourcesCount) {
		this.sourcesCount = sourcesCount;
	}

	public float getReferencesPart() {
		return referencesPart;
	}

	public void setReferencesPart(float referencesPart) {
		this.referencesPart = referencesPart;
	}

	public float getCitationsPart() {
		return citationsPart;
	}

	public void setCitationsPart(float citationsPart) {
		this.citationsPart = citationsPart;
	}

	public int getCitationsCount() {
		return citationsCount;
	}

	public void setCitationsCount(int citationsCount) {
		this.citationsCount = citationsCount;
	}

	public float getExcludedPart() {
		return excludedPart;
	}

	public void setExcludedPart(float excludedPart) {
		this.excludedPart = excludedPart;
	}

	public String getViewUrl() {
		return viewUrl;
	}

	public void setViewUrl(String viewUrl) {
		this.viewUrl = viewUrl;
	}

	public String getViewEditUrl() {
		return viewEditUrl;
	}

	public void setViewEditUrl(String viewEditUrl) {
		this.viewEditUrl = viewEditUrl;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("Report{");
		sb.append("createDate=").append(createDate);
		sb.append(", similarity=").append(similarity);
		sb.append(", sourcesCount=").append(sourcesCount);
		sb.append(", referencesPart=").append(referencesPart);
		sb.append(", citationsPart=").append(citationsPart);
		sb.append(", citationsCount=").append(citationsCount);
		sb.append(", excludedPart=").append(excludedPart);
		sb.append(", viewUrl='").append(viewUrl).append('\'');
		sb.append(", viewEditUrl='").append(viewEditUrl).append('\'');
		sb.append('}');
		return sb.toString();
	}
}
