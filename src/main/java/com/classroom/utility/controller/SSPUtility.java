package com.classroom.utility.controller;

public class SSPUtility {

	private String pageIds;
	private String errorMessages;
	private String displayTexts;
	private String refTables;
	
	public SSPUtility() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getPageIds() {
		return pageIds;
	}
	public void setPageIds(String pageIds) {
		this.pageIds = pageIds;
	}
	public String getErrorMessages() {
		return errorMessages;
	}
	public void setErrorMessages(String errorMessages) {
		this.errorMessages = errorMessages;
	}
	public String getDisplayTexts() {
		return displayTexts;
	}
	public void setDisplayTexts(String displayTexts) {
		this.displayTexts = displayTexts;
	}
	public String getRefTables() {
		return refTables;
	}
	public void setRefTables(String refTables) {
		this.refTables = refTables;
	}
	public SSPUtility(String pageIds, String errorMessages, String displayTexts, String refTables) {
		super();
		this.pageIds = pageIds;
		this.errorMessages = errorMessages;
		this.displayTexts = displayTexts;
		this.refTables = refTables;
	}
	
	
	
	
}
