package com.classroom.utility.controller;

public class SSPUtilityResponse {

	private String filePath;
	private String utilSuccessfull;
	private String exceptions;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getUtilSuccessfull() {
		return utilSuccessfull;
	}
	public void setUtilSuccessfull(String utilSuccessfull) {
		this.utilSuccessfull = utilSuccessfull;
	}
	public String getExceptions() {
		return exceptions;
	}
	public void setExceptions(String exceptions) {
		this.exceptions = exceptions;
	}
	public SSPUtilityResponse(String filePath, String utilSuccessfull, String exceptions) {
		super();
		this.filePath = filePath;
		this.utilSuccessfull = utilSuccessfull;
		this.exceptions = exceptions;
	}
	public SSPUtilityResponse() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
}
