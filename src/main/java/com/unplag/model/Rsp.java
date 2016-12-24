package com.unplag.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Rsp<V> {

	@JsonProperty(value = "result")
	private boolean successful;
	private Error[] errors;

	private V v;

	public boolean isSuccessful() {
		return successful;
	}

	public void setSuccessful(boolean successful) {
		this.successful = successful;
	}

	public Error[] getErrors() {
		return errors;
	}

	public void setErrors(Error[] errors) {
		this.errors = errors;
	}

	public V getValue() {
		return v;
	}

	public void setValue(V v) {
		this.v = v;
	}

	public static class FileResponse extends Rsp<UFile> {

		@JsonProperty("file")
		@Override
		public UFile getValue() {
			return super.getValue();
		}
	}

	public static class CheckResponse extends Rsp<UCheck> {

		@JsonProperty("check")
		@Override
		public UCheck getValue() {
			return super.getValue();
		}
	}

	public static class PdfReportResponse extends Rsp<UPdfReport> {

		@JsonProperty("pdf_report")
		@Override
		public UPdfReport getValue() {
			return super.getValue();
		}
	}

	public static class ToggleCitationsResponse extends CheckResponse {

		@JsonProperty("exclude_citations")
		private boolean excludeCitations;
		@JsonProperty("exclude_references")
		private boolean excludeReferences;

		public boolean isExcludeCitations() {
			return excludeCitations;
		}

		public void setExcludeCitations(boolean excludeCitations) {
			this.excludeCitations = excludeCitations;
		}

		public boolean isExcludeReferences() {
			return excludeReferences;
		}

		public void setExcludeReferences(boolean excludeReferences) {
			this.excludeReferences = excludeReferences;
		}
	}

}
