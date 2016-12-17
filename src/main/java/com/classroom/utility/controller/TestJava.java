package com.classroom.utility.controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class TestJava {

	public TestJava() {
		// TODO Auto-generated constructor stub
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {

            // Change this DB Url When Needed URL AND PORT ONLY
        	//String dbURL = "jdbc:sqlserver://10.12.88.113:44033";
        	//String dbURL = "jdbc:sqlserver://usmumpurchase3:9080";
        	
          String dbURL = "jdbc:sqlserver://localhost:53344";
            // DBNAME
        	String user = "ssp_ee_dev";
            //DBPASSWORD
        	String pass = "ssp_ee_dev";
        	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        	Class.forName(driver).newInstance();
            conn = DriverManager.getConnection(dbURL, user, pass);
            
            stmt = conn.createStatement();
            
            String sql6 = "select * from DPLY_TXT where TXT_ID in ('50506','50507');";
            
            rs = stmt.executeQuery(sql6);
            while(rs.next()){
            	
            	StringBuffer innerLoop = new StringBuffer();
            	String tempString = "INSERT INTO DPLY_TXT VALUES("; 
            	innerLoop.append(tempString);
            	innerLoop.append(String.valueOf(rs.getInt("TXT_ID")));
            	innerLoop.append(",");
            	innerLoop.append("'"+String.valueOf(rs.getString("LANG_CD").replaceAll("'", "''"))+"'");
            	innerLoop.append(",");
            	innerLoop.append("'"+String.valueOf(rs.getString("CMT_TXT").replaceAll("'", "''"))+"'");
            	innerLoop.append(",");
            	System.out.println(rs.getString("TXT_DSC"));
            	innerLoop.append("N'"+String.valueOf(rs.getString("TXT_DSC").replaceAll("'", "''"))+"'");
            	innerLoop.append(");");
            	System.out.println(innerLoop.toString());
            	//sb1.append(innerLoop.toString()+"\n");
            }
            
        }
        catch(Exception e){
        	System.out.println(e.toString());
        }
		
		
	}

}
