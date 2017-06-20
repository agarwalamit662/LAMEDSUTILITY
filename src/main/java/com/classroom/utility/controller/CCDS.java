package com.classroom.utility.controller;

public class CCDS {

	private String tableName;
	private String dburl="usmumpurchase1:44033";
	
	
	

	public String getDburl() {
		return dburl;
	}

	public void setDburl(String dburl) {
		this.dburl = dburl;
	}

	public CCDS() {
		super();
		// TODO Auto-generated constructor stub
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public CCDS(String tableName) {
		super();
		this.tableName = tableName;
	}
	
	

}
