package com.errorreader.sushant.demo;

public class ErrorLogDTO {

	private String cuId;
	private String userId;
	private String errorText;
	private String stackTrace;
	private String source;
	private String severity;
	private String createdTS;

	public String getCuId() {
		return cuId;
	}

	public void setCuId(String cuId) {
		this.cuId = cuId;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getErrorText() {
		return errorText;
	}

	public void setErrorText(String errorText) {
		this.errorText = errorText;
	}

	public String getStackTrace() {
		return stackTrace;
	}

	public void setStackTrace(String stackTrace) {
		this.stackTrace = stackTrace;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getSeverity() {
		return severity;
	}

	public void setSeverity(String severity) {
		this.severity = severity;
	}

	public String getCreatedTS() {
		return createdTS;
	}

	public void setCreatedTS(String createdTS) {
		this.createdTS = createdTS;
	}

}
