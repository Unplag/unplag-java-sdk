package com.unplag.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Arrays;

public class UFile {

	private long id;
	@JsonProperty("words_count")
	private long wordsCount;
	private long size;
	private String name;
	private String format;
	@JsonProperty("pages_count")
	private long pages;
	@JsonProperty("print_pages_count")
	private long printPages;
	@JsonProperty("checks")
	private UCheck[] uChecks;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getWordsCount() {
		return wordsCount;
	}

	public void setWordsCount(long wordsCount) {
		this.wordsCount = wordsCount;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public long getPages() {
		return pages;
	}

	public void setPages(long pages) {
		this.pages = pages;
	}

	public long getPrintPages() {
		return printPages;
	}

	public void setPrintPages(long printPages) {
		this.printPages = printPages;
	}

	public UCheck[] getUChecks() {
		return uChecks;
	}

	public void setUChecks(UCheck[] uChecks) {
		this.uChecks = uChecks;
	}

	@Override
	public String toString() {
		final StringBuilder sb = new StringBuilder("UFile{");
		sb.append("id=").append(id);
		sb.append(", wordsCount=").append(wordsCount);
		sb.append(", size=").append(size);
		sb.append(", name='").append(name).append('\'');
		sb.append(", format='").append(format).append('\'');
		sb.append(", pages=").append(pages);
		sb.append(", printPages=").append(printPages);
		sb.append(", uChecks=").append(Arrays.toString(uChecks));
		sb.append('}');
		return sb.toString();
	}
}
