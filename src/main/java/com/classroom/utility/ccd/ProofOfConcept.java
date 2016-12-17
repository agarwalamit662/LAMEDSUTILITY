/*
 * Created on Jan 29, 2008
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.classroom.utility.ccd;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * @author rhoadza
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class ProofOfConcept {

	public static void main(String[] args) {
		try{
			Connection conn = getConnection("PPSDeveloper", "Deep1Kajol_8");
			Statement stmt = conn.createStatement();
			//String query = "SELECT * from AGCY";
			//ResultSet rs = stmt.executeQuery(query);
			//long res = 0;
			//while (rs.next()){
			//	res = rs.getLong("AGCY_ID_SK");
			//	int inspector = 0;
			//}
			
		}
		catch (SQLException sqle){
			sqle.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static Connection getConnection(String aUserID, String aPwd) throws Exception {

		try {		
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection("jdbc:oracle:thin:@10.233.8.180:1521:GANYORCL", "suren", "suren");

		} catch (ClassNotFoundException cnfe){ 
			throw cnfe;
		} catch (SQLException sqle){
			throw sqle;
		}
	}
}
