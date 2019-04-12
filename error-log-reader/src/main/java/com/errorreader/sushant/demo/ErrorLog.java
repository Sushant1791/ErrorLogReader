package com.errorreader.sushant.demo;

import java.io.Serializable;
import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ErrorLog implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	private String id;

	@Column(name = "CUID")
	private Integer cuId;

	@Column(name = "USERID")
	private String userId;

	@Column(name = "ERRTXT")
	private String errorText;

	@Column(name = "STACK_TRACE")
	private String stackTrace;

	@Column(name = "SOURCE")
	private String source;

	@Column(name = "SEVERITY")
	private String severity;

	@Column(name = "CREATED_TS")
	private Timestamp createdTS;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getCuId() {
		return cuId;
	}

	public void setCuId(Integer cuId) {
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

	public Timestamp getCreatedTS() {
		return createdTS;
	}

	public void setCreatedTS(Timestamp createdTS) {
		this.createdTS = createdTS;
	}

	@Override
	public String toString() {
		return "ErrorLog [id=" + id + ", cuId=" + cuId + ", userId=" + userId + ", errorText=" + errorText
				+ ", stackTrace=" + stackTrace + ", source=" + source + ", severity=" + severity + ", createdTS="
				+ createdTS + "]";
	}

}
