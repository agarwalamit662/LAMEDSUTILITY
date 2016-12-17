package com.classroom.utility.ccd;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;

/**
 * Code Generates Cargo, Collection, PrimaryKey and DAO objects for the Database table
 * @author Anand Balasubramanian
 * Created on Apr 8, 2006
 */

public class CCDGenerator {

	public static void main(String[] args) {
		
		CCDGenerator cg = new CCDGenerator();
	}
	
	public void generate(String aSubSystem, String aTableName, String aUserId, String aPwd) throws Exception {

		try {
			String schema = null;
			if(aSubSystem.equals("cares")){
				schema = GeneratorConstants.DB_SCHEMA_CARES;
			} else if (aSubSystem.equals("access")){
				schema = GeneratorConstants.DB_SCHEMA_ACCESS;	
			}  else if (aSubSystem.equals("pps")){
				schema = GeneratorConstants.DB_SCHEMA_PPS;	
			} else if (aSubSystem.equals("wits")){
				schema = GeneratorConstants.DB_SCHEMA_WITS;	
			} else if (aSubSystem.equals("studio-cares")){
				schema = GeneratorConstants.DB_SCHEMA_CARES;
				aSubSystem="studio";
			} else if (aSubSystem.equals("studio-access")){
				schema = GeneratorConstants.DB_SCHEMA_ACCESS;
				aSubSystem="studio";								
			} else {
				throw new Exception("invalid Application");
			}

			File dir = new File("C:/GeneratedCode");
			if(!dir.exists()){
				dir.mkdir();
			}
			dir = new File("C:/GeneratedCode/ccd");
			if(!dir.exists()){
				dir.mkdir();
			}				
				
			List allColumnInfo = DBMetaData.getAllColumnMetaData(schema, aTableName, aUserId, aPwd);
			List pkColumnInfo = DBMetaData.getPKColumnMetaData(schema, aTableName, aUserId, aPwd);
		
			// generate cargo
			CargoGenerator.generate(aSubSystem, aTableName, allColumnInfo, pkColumnInfo);

			// generate collection
			CollectionGenerator.generate(aSubSystem, aTableName, allColumnInfo);

			// generate PK
			PKGenerator.generate(aSubSystem, aTableName, pkColumnInfo);

			// generate DAO
			boolean simulation = getSimulation(aSubSystem);
			DAOGenerator.generate(aSubSystem, aTableName, allColumnInfo, pkColumnInfo, simulation, schema);

		} catch(Exception e){
			try {
				BufferedWriter bw =	new BufferedWriter(new FileWriter("C:/GeneratedCode/error.log"));
				bw.write(e.toString());
				bw.flush();
				bw.close();
				throw e;
			} catch(Exception te){
				throw te;
			}
		}
	}
	
	private boolean getSimulation(String aSubSystem) {
		return (aSubSystem.equals("cares"));
	}
}
