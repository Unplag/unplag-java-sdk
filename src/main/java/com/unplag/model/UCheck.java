package com.unplag.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

@JsonIgnoreProperties(ignoreUnknown = true)
public class UCheck {

	private long id;
	private double cost;
	@JsonProperty("cost_type")
	private String costType;
	private UType type;
	private String name;
	@JsonProperty("file_id")
	private long fileId;
	@JsonProperty("versus_files")
	private long[] versusFiles;
	@JsonProperty("create_date")
	private double createDate;
	private double progress;
	private UReport report;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public double getCost() {
		return cost;
	}

	public void setCost(double cost) {
		this.cost = cost;
	}

	public String getCostType() {
		return costType;
	}

	public void setCostType(String costType) {
		this.costType = costType;
	}

	public UType getType() {
		return type;
	}

	public void setType(UType type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getFileId() {
		return fileId;
	}

	public void setFileId(long fileId) {
		this.fileId = fileId;
	}

	public long[] getVersusFiles() {
		return versusFiles;
	}

	public void setVersusFiles(long[] versusFiles) {
		this.versusFiles = versusFiles;
	}

	public double getCreateDate() {
		return createDate;
	}

	public void setCreateDate(double createDate) {
		this.createDate = createDate;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double progress) {
		this.progress = progress;
	}

	public UReport getReport() {
		return report;
	}

	public void setReport(UReport report) {
		this.report = report;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UCheck{");
		sb.append("id=").append(id);
		sb.append(", cost=").append(cost);
		sb.append(", costType='").append(costType).append('\'');
		sb.append(", type=").append(type);
		sb.append(", name='").append(name).append('\'');
		sb.append(", fileId=").append(fileId);
		sb.append(", versusFiles=").append(Arrays.toString(versusFiles));
		sb.append(", createDate=").append(createDate);
		sb.append(", progress=").append(progress);
		sb.append(", report=").append(report);
		sb.append('}');
		return sb.toString();
	}
}
