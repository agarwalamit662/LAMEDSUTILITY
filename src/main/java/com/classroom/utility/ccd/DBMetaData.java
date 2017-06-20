package com.classroom.utility.ccd;



import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * This classes is used for to get the Database metadata information
 * @author Anand Balasubramanian
 * Created on Apr 8, 2006
 */

public class DBMetaData {

	public static List getAllColumnMetaData(String aSchema, String aTableName, String aUserID, String aPwd,String url) throws Exception {
		
			
	//			return getColumnData(aUserID, aPwd, "SELECT TABLE_NAME as TBNAME,ORDINAL_POSITION as COLNO,COLUMN_NAME as COLNAME,DATA_TYPE as COLTYPE,coalesce(CHARACTER_MAXIMUM_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE) as LENGTH,NUMERIC_SCALE as SCALE FROM INFORMATION_SCHEMA.COLUMNS	WHERE TABLE_SCHEMA = '"+aSchema+"' AND TABLE_NAME = '"+aTableName+"' ORDER BY ORDINAL_POSITION");
		      // return getColumnData(aUserID, aPwd, "SELECT TABLE_NAME as TBNAME,ORDINAL_POSITION as COLNO,COLUMN_NAME as COLNAME,DATA_TYPE as COLTYPE,coalesce(CHARACTER_MAXIMUM_LENGTH,NUMERIC_PRECISION,NUMERIC_SCALE) as LENGTH,NUMERIC_SCALE as SCALE FROM INFORMATION_SCHEMA.COLUMNS	WHERE TABLE_NAME = '"+aTableName+"' ORDER BY ORDINAL_POSITION");
		      return getColumnData(aUserID, aPwd, "SELECT tbls.name as TBNAME,cols.column_id as COLNO,cols.name as COLNAME,typs.name as COLTYPE,case when cols.precision =  0 then coalesce(cols.max_length,cols.scale)  else cols.precision end as LENGTH,cols.scale as SCALE FROM sys.columns cols INNER JOIN sys.tables tbls ON tbls.object_id = cols.object_id INNER JOIN sys.types typs ON cols.system_type_id = typs.system_type_id  INNER JOIN sys.schemas schms ON tbls.schema_id = schms.schema_id WHERE tbls.name ='"+aTableName+"' AND schms.name = '"+aSchema+"' ORDER BY cols.column_id",url);

//				return getColumnData(aUserID, aPwd, "SELECT TBNAME,COLNO,NAME as COLNAME,COLTYPE,LENGTH,SCALE FROM SYSIBM.SYSCOLUMNS WHERE TBCREATOR = '"+aSchema+"' AND TBNAME = '"+aTableName+"' ORDER BY COLNO");
//		return getColumnData(aUserID, aPwd, 
//				"select table_name, column_id, column_name COLNAME, " +
//				"data_type COLTYPE, " +
//				"data_precision PRECISION, data_length LENGTH, data_scale SCALE " +
//				"from all_tab_columns where table_name= '" + 
//				aTableName+"' and owner = '"+aSchema+"' ORDER BY COLUMN_ID");
	}
	
	public static List getPKColumnMetaData(String aSchema, String aTableName, String aUserID, String aPwd,String url) throws Exception {
		
		//return getColumnData(aUserID, aPwd, "select A.TABLE_NAME as TBNAME,	C.ORDINAL_POSITION as COLSEQ, C.COLUMN_NAME as COLNAME,	C.DATA_TYPE as COLTYPE, coalesce(C.CHARACTER_MAXIMUM_LENGTH,C.NUMERIC_PRECISION,C.NUMERIC_SCALE) as LENGTH, C.NUMERIC_SCALE as SCALE from INFORMATION_SCHEMA.TABLE_CONSTRAINTS A INNER JOIN	INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE B ON A.CONSTRAINT_NAME = B.CONSTRAINT_NAME and A.TABLE_NAME = B.TABLE_NAME INNER JOIN INFORMATION_SCHEMA.COLUMNS C ON B.COLUMN_NAME = C.COLUMN_NAME and B.TABLE_NAME = C.TABLE_NAME where A.TABLE_SCHEMA = '"+aSchema+"' AND	C.TABLE_NAME = '"+aTableName+"' AND A.CONSTRAINT_TYPE ='PRIMARY KEY' ORDER  BY C.ORDINAL_POSITION");
		 return getColumnData(aUserID, aPwd, "select A.TABLE_NAME as TBNAME,	C.ORDINAL_POSITION as COLSEQ, C.COLUMN_NAME as COLNAME,	C.DATA_TYPE as COLTYPE, coalesce(C.CHARACTER_MAXIMUM_LENGTH,C.NUMERIC_PRECISION,C.NUMERIC_SCALE) as LENGTH, C.NUMERIC_SCALE as SCALE from INFORMATION_SCHEMA.TABLE_CONSTRAINTS A INNER JOIN	INFORMATION_SCHEMA.CONSTRAINT_COLUMN_USAGE B ON A.CONSTRAINT_NAME = B.CONSTRAINT_NAME and A.TABLE_NAME = B.TABLE_NAME INNER JOIN INFORMATION_SCHEMA.COLUMNS C ON B.COLUMN_NAME = C.COLUMN_NAME and B.TABLE_NAME = C.TABLE_NAME where C.TABLE_NAME = '"+aTableName+"' AND A.CONSTRAINT_TYPE ='PRIMARY KEY' ORDER  BY C.ORDINAL_POSITION",url);

//		return getColumnData(aUserID, aPwd, 
//				"SELECT distinct cols.table_name,tcol.data_precision PRECISION,  cols.column_name COLNAME, tcol.data_scale SCALE, tcol.data_type COLTYPE, tcol.data_length LENGTH, cols.position, cons.status, cons.owner "+
//				"FROM all_constraints cons, all_cons_columns cols, all_tab_columns tcol " +
//				"WHERE tcol.table_name = '"+aTableName +"'"+			
//				" AND cols.owner = '"+aSchema+"'"+
//				" AND cons.constraint_name = cols.constraint_name " +
//				" AND cons.owner = cols.owner " +
//				" AND cons.constraint_type = 'P'" +
//				" AND tcol.table_name=cols.table_name " +
//				" AND tcol.column_name=cols.column_name " +
//				" ORDER BY cols.table_name, cols.position");
	}
	
	private static List getColumnData(String aUserID, String aPwd, String aSql,String url) throws Exception{

		try {
			
			List columnMetaData = new ArrayList(); 
			Connection conn = getConnection(aUserID, aPwd,url);
			Statement stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(aSql);
			while(rs.next()) {
				Map columnData = new HashMap();
				System.out.println(rs.getString("COLNAME"));
				columnData.put(GeneratorConstants.COLUMN_NAME, rs.getString("COLNAME").trim());
				columnData.put(GeneratorConstants.DATA_TYPE, rs.getString("COLTYPE").trim());
				columnData.put(GeneratorConstants.DAO_DATA_TYPE, rs.getString("COLTYPE").trim().toUpperCase());
				columnData.put(GeneratorConstants.LENGTH, rs.getString("LENGTH").trim());
				
//				if(rs.getString("PRECISION") == null)
//					columnData.put(GeneratorConstants.PRECISION,"0");
//				else
//					columnData.put(GeneratorConstants.PRECISION,rs.getString("PRECISION").trim());
				
				if(rs.getString("SCALE")==null)
				columnData.put(GeneratorConstants.SCALE, "0");
				else
					columnData.put(GeneratorConstants.SCALE,rs.getString("SCALE").trim());
				columnMetaData.add(columnData);
			}
			return columnMetaData;
		} catch (SQLException sqle){
			throw sqle;
		} catch (Exception e){
			throw e;
		}
	}
	
	private static Connection getConnection(String aUserID, String aPwd,String baseurl) throws Exception {

		try {		
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerConnectionPoolDataSource");
			
			Properties properties = new Properties(); // Create Properties object
			properties.put("user", "ssp_ee_dev");         // Set user ID for connection
			properties.put("password", "ssp_ee_dev");     // Set password for connection
			//String url = "jdbc:sqlserver://localhost:53344";
			String url = "jdbc:sqlserver://"+baseurl.trim();
			//String url = "jdbc:sqlserver://usmumpurchase1:44033";
				//	+ "10.12.88.113:44033";
			//10.12.88.136,44033
//			String url = "jdbc:db2://167.219.240.161:5951/CTDA";
			
			String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
        	Class.forName(driver).newInstance();
        	Connection con = DriverManager.getConnection(url, "ssp_ee_dev", "ssp_ee_dev");
			
			                                          // Set URL for data source
			//Connection con = DriverManager.getConnection(url, properties); 

			return con;
			//return DriverManager.getConnection("jdbc:oracle:thin:@10.233.8.180:1521:GANYORCL", aUserID, aPwd);
			//	return DriverManager.getConnection("jdbc:db2://167.219.240.161:5951:CTDA", aUserID, aPwd);
//			changes to read from properties file
			/*
			String db = "";
			BufferedReader in;
			
			in = new BufferedReader(new FileReader("C:\\generatorProperties\\db.location"));
			db = in.readLine();
			in.close();
			
			return DriverManager.getConnection(db, aUserID, aPwd);*/
		} catch (ClassNotFoundException cnfe){ 
			throw cnfe;
		} catch (SQLException sqle){
			throw sqle;
		}
	}
}