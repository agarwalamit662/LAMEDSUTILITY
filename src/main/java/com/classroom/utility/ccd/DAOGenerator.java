package com.classroom.utility.ccd;




import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Generates AbstractDAO and DAO objects for given input values
 * @author Anand Balasubramanian
 * Created on Apr 13, 2006
 */

public class DAOGenerator {
	
	public static void generate(String aSubSystem, String aTableName, List aAllColumnInfo, List aPKColumnInfo, boolean aSimulationRequired, String aSchema) throws Exception {
		
		try {
			StringBuffer cargo = new StringBuffer();

			// get package imports
			cargo.append(getPackageAndImports(aSubSystem,aTableName));

			// get class
			cargo.append(getClassDeclaration(aSubSystem, aTableName));
			
			// get static sqls
			cargo.append(getStaticSQLs(aTableName, aAllColumnInfo, aPKColumnInfo, aSimulationRequired, aSubSystem, aSchema));
			
			//  getRowFromResultSet
			cargo.append(getRowFromResultSet(aTableName, aAllColumnInfo, aSubSystem));
			
			// select
			cargo.append(getSelect(aTableName, aPKColumnInfo, aSimulationRequired, aSubSystem)); 

			// findByPK
			cargo.append(getFindByPK(aTableName, aPKColumnInfo, aSimulationRequired, aSubSystem)); 

			// insert
			cargo.append(getInsert(aTableName, aAllColumnInfo, aSimulationRequired, aSubSystem));			

			// update
			cargo.append(getUpdate(aTableName, aAllColumnInfo, aPKColumnInfo, aSimulationRequired, aSubSystem));			

			// delete
			cargo.append(getDelete(aTableName, aPKColumnInfo, aSimulationRequired, aSubSystem));			

			// getHistoryType
			cargo.append(getHistoryType());

			// close class
			cargo.append("}");
			
			BufferedWriter bw =	new BufferedWriter(new FileWriter("C:/GeneratedCode/ccd/Abstract_"+aTableName+"_DAO.java"));
			bw.write(cargo.toString());
			bw.flush();
			bw.close();
			
			// sub class
			BufferedWriter bw_sub =	new BufferedWriter(new FileWriter("C:/GeneratedCode/ccd/"+aTableName+"_DAO.java"));
			bw_sub.write(getSubClass(aSubSystem, aTableName));
			bw_sub.flush();
			bw_sub.close();

		} catch(Exception e){
			try {
				BufferedWriter bw_err =	new BufferedWriter(new FileWriter("C:/GeneratedCode/error.log"));
				bw_err.write(e.toString());
				bw_err.flush();
				bw_err.close();
				throw e;
			} catch(Exception te){
				throw te;
			}
		}
	}
	
	public static String getPackageAndImports(String aSubSystem, String aTableName){
		
		StringBuffer packageAndImport = new StringBuffer();
		//packageAndImport.append("package gov.wisconsin."+aSubSystem+".data.db2.base;\n\n");
		//packageAndImport.append("package gov.selfservice.data.oracle.base;\n\n");
		packageAndImport.append("package gov.selfservice.data.db2.base;\n\n");
		packageAndImport.append("import gov.selfservice.business.entities."+aTableName+"_Cargo;\n");
		packageAndImport.append("import gov.selfservice.business.entities."+aTableName+"_PrimaryKey;\n");
		packageAndImport.append("import gov.selfservice.framework.business.entities.ICargo;\n");
		packageAndImport.append("import gov.selfservice.framework.business.entities.IPrimaryKey;\n");
		packageAndImport.append("import gov.selfservice.framework.exceptions.FwException;\n");
		packageAndImport.append("import gov.selfservice.framework.exceptions.FwExceptionManager;\n");
		packageAndImport.append("import gov.selfservice.framework.management.constants.FwConstants;\n");
		if(aSubSystem.equals("access")){
			packageAndImport.append("import gov.selfservice.framework.management.util.FwDataCriteria;\n");
		} else {
			packageAndImport.append("import gov.selfservice.framework.management.util.FwDAODataCriteria;\n");			
		}
		packageAndImport.append("import gov.selfservice.framework.management.util.FwDataSortOrder;\n");
		packageAndImport.append("import gov.selfservice.framework.persistence.database.dao.FwAbstractDAO;\n\n");
		
		packageAndImport.append("import java.sql.Connection;\n");
		packageAndImport.append("import java.sql.PreparedStatement;\n");
		packageAndImport.append("import java.sql.ResultSet;\n");
		packageAndImport.append("import java.sql.SQLException;\n");
		packageAndImport.append("import java.util.ArrayList;\n");
		packageAndImport.append("import java.util.List;\n");
		packageAndImport.append("import java.sql.Timestamp;\n");
		packageAndImport.append("import java.sql.Date;\n");
		packageAndImport.append("import java.sql.Time;\n");
		
		packageAndImport.append("\n");
		return packageAndImport.toString();
	}
	
	public static String getClassDeclaration(String aSubSystem, String aTableName){
	
		StringBuffer classDeclaration = new StringBuffer();
		classDeclaration.append("/**\n");
		classDeclaration.append("* Abstract Data Access Object for "+aTableName+"\n");
		classDeclaration.append("* @author CodeGenerator - Architecture Team\n");
		classDeclaration.append("* @Creation Date "+new Date()+"\n");
		classDeclaration.append("*/\n");
		classDeclaration.append("public class Abstract_"+aTableName+"_DAO extends FwAbstractDAO { \n\n");
		classDeclaration.append("\tprivate static final short HISTORY_TYPE=0;\n\n");
		return classDeclaration.toString();
	}
	
	
	public static String getStaticSQLs(String aTableName, List aAllColumnInfo, List aPKColumnInfo, boolean aSimulationRequired, String aSubSys, String aSchema){
		
		StringBuffer selectSQL = new StringBuffer();
		StringBuffer insertSQL = new StringBuffer();
		StringBuffer pkSQL = new StringBuffer();
		StringBuffer updateSQL = new StringBuffer();
		StringBuffer deleteSQL = new StringBuffer();
		StringBuffer sim_selectSQL = new StringBuffer();
		StringBuffer sim_insertSQL = new StringBuffer();
		StringBuffer sim_pkSQL = new StringBuffer();
		StringBuffer sim_updateSQL = new StringBuffer();
		StringBuffer sim_deleteSQL = new StringBuffer();


		String schemaName = "";
		String simulationSchemaName = "";
		if(aSubSys.equals("studio")){
			schemaName = aSchema+".";
		} else {
			schemaName = "\"+getDBSchema()+FwConstants.DOT+\"";
			simulationSchemaName = "\"+getSimulationSchema()+FwConstants.DOT+\"";			
		}

		
		// select SQL		
		selectSQL.append("\tprivate static final String SELECT_SQL=\"SELECT ");
		if(aSimulationRequired){
			sim_selectSQL.append("\tprivate static final String SIMUL_SELECT_SQL=\"SELECT ");	
		}
		
		for(int i=0; i<aAllColumnInfo.size(); i++){
			if(i!=0){
				selectSQL.append(",");	
				if(aSimulationRequired){			
					sim_selectSQL.append(",");
				}
			}
			Map row = (Map)aAllColumnInfo.get(i);
			selectSQL.append(row.get(GeneratorConstants.COLUMN_NAME));
			if(aSimulationRequired){
				sim_selectSQL.append(row.get(GeneratorConstants.COLUMN_NAME));
			}
		}
		
		selectSQL.append(" FROM "+schemaName+aTableName+"\";\n");
		if(aSimulationRequired){
			sim_selectSQL.append(" FROM "+simulationSchemaName+aTableName+"\";\n");
		}


		// insert SQL
		insertSQL.append("\tprivate static final String INSERT_SQL = \"INSERT INTO  "+schemaName+aTableName+"(");
		if(aSimulationRequired){
			sim_insertSQL.append("\tprivate static final String SIMUL_INSERT_SQL = \"INSERT INTO "+simulationSchemaName+aTableName+"(");
		}
		for(int i=0; i<aAllColumnInfo.size(); i++){
			if(i!=0){
				insertSQL.append(",");	
				if(aSimulationRequired){			
					sim_insertSQL.append(",");
				}
			}
			Map row = (Map)aAllColumnInfo.get(i);
			insertSQL.append(row.get(GeneratorConstants.COLUMN_NAME));
			if(aSimulationRequired){
				sim_insertSQL.append(row.get(GeneratorConstants.COLUMN_NAME));
			}
		}
		insertSQL.append(") VALUES(");
		if(aSimulationRequired){
			sim_insertSQL.append(") VALUES(");
		}
		for(int i=0; i<aAllColumnInfo.size(); i++){
			if(i!=0){
				insertSQL.append(",");	
				if(aSimulationRequired){			
					sim_insertSQL.append(",");
				}
			}
			insertSQL.append("?");
			if(aSimulationRequired){
				sim_insertSQL.append("?");
			}
		}	
		insertSQL.append(")\";\n");
		if(aSimulationRequired){
			sim_insertSQL.append(")\";\n");
		}	

		// PK sql
		pkSQL.append("\tprivate static final String PK_SELECT_SQL = \"SELECT 1 FROM "+schemaName+aTableName+" WHERE ");
		if(aSimulationRequired){
			sim_pkSQL.append("\tprivate static final String SIMUL_PK_SELECT_SQL = \"SELECT 1 FROM "+simulationSchemaName+aTableName+" WHERE ");
		}
		for(int i=0; i<aPKColumnInfo.size(); i++){
			if(i!=0){
				pkSQL.append(" AND ");
				if(aSimulationRequired){
					sim_pkSQL.append(" AND ");
				}
			}
			Map row = (Map)aPKColumnInfo.get(i);
			pkSQL.append(row.get(GeneratorConstants.COLUMN_NAME));
			pkSQL.append("=?");
			if(aSimulationRequired){
				sim_pkSQL.append(row.get(GeneratorConstants.COLUMN_NAME));
				sim_pkSQL.append("=?");
			}
		}
		pkSQL.append("\";\n");
		if(aSimulationRequired) {
			sim_pkSQL.append("\";\n");
		}

		// update sql
		updateSQL.append("\tprivate static final String UPDATE_SQL=\"UPDATE "+schemaName+aTableName+" SET ");
		if(aSimulationRequired){
			sim_updateSQL.append("\tprivate static final String SIMUL_UPDATE_SQL=\"UPDATE "+simulationSchemaName+aTableName+" SET ");
		}
		// getting the PK columns
		List pkList = new ArrayList();
		for(int i=0; i<aPKColumnInfo.size(); i++){
			Map row = (Map)aPKColumnInfo.get(i);
			pkList.add(row.get(GeneratorConstants.COLUMN_NAME));
		}
		int count = 0;
		for(int i=0; i<aAllColumnInfo.size(); i++){
			if(count!=0) {
				updateSQL.append(",");
				if(aSimulationRequired){
					sim_updateSQL.append(",");
				}
			}
			Map row = (Map)aAllColumnInfo.get(i);
			if(!pkList.contains(row.get(GeneratorConstants.COLUMN_NAME))){
				count = 1;
				updateSQL.append(row.get(GeneratorConstants.COLUMN_NAME)+"=?"); 
				if(aSimulationRequired){
					sim_updateSQL.append(row.get(GeneratorConstants.COLUMN_NAME)+"=?");
				}
			}
		}
		updateSQL.append(" WHERE ");
		if(aSimulationRequired){
			sim_updateSQL.append(" WHERE ");
		}
		for(int i=0; i<pkList.size(); i++){
			if(i!=0){
				updateSQL.append(" AND ");
				if(aSimulationRequired){
					sim_updateSQL.append(" AND ");
				}
			}
			updateSQL.append(pkList.get(i)+"=?");
			if(aSimulationRequired){
				sim_updateSQL.append(pkList.get(i)+"=?");
			}
		}
		updateSQL.append("\";\n");
		if(aSimulationRequired){
			sim_updateSQL.append("\";\n");
		}
		
		// delete sql
		deleteSQL.append("\tprivate static final String DELETE_SQL=\"DELETE FROM "+schemaName+aTableName+" WHERE  ");
		if(aSimulationRequired){
			sim_deleteSQL.append("\tprivate static final String SIMUL_DELETE_SQL=\"DELETE FROM "+simulationSchemaName+aTableName+" WHERE ");
		}
		for(int i=0; i<pkList.size(); i++){
			if(i!=0){
				deleteSQL.append(" AND ");
				if(aSimulationRequired){
					sim_deleteSQL.append(" AND ");
				}
			}
			deleteSQL.append(pkList.get(i)+"=?");
			if(aSimulationRequired){
				sim_deleteSQL.append(pkList.get(i)+"=?");
			}
		}
		deleteSQL.append("\";\n");
		if(aSimulationRequired){
			sim_deleteSQL.append("\";\n");
		}
		return new StringBuffer().append(selectSQL).append(sim_selectSQL).append(insertSQL).append(sim_insertSQL).append(pkSQL).append(sim_pkSQL).append(updateSQL).append(sim_updateSQL).append(deleteSQL).append(sim_deleteSQL).append("\n").toString();
	}
	
	
	public static String getRowFromResultSet(String aTableName, List aAllColumnInfo, String aSubSystem){
		
		StringBuffer getRowFromResultSet = new StringBuffer();

		getRowFromResultSet.append("\t/**\n");
		getRowFromResultSet.append("\t* Getting the row values from the Result Set\n");
		getRowFromResultSet.append("\t*/\n");
		getRowFromResultSet.append("\tprivate void getRowFromResultSet("+aTableName+"_Cargo row, ResultSet rs) {\n\n");
		getRowFromResultSet.append("\t\ttry{\n");
		
		for(int i=0; i<aAllColumnInfo.size(); i++){
			
			Map columnInfo = (Map)aAllColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			System.out.println(columnName);
			String dataType = (String)columnInfo.get(GeneratorConstants.DATA_TYPE);
			String length = (String)columnInfo.get(GeneratorConstants.LENGTH);
			String scale = (String)columnInfo.get(GeneratorConstants.SCALE);
			String precision = (String)columnInfo.get(GeneratorConstants.PRECISION);
			String javaDataType = GeneraterHelper.getJavaDataType(dataType, length, scale, precision);
			String initCapColumnName = initCap(columnName.toLowerCase());
			String initCapJavaDataType = initCap(javaDataType);
			if(aSubSystem.equals("access")){
				getRowFromResultSet.append("\t\t\trow.set"+initCapColumnName+"(rs.getString(\""+columnName+"\"));\n");
			} else {
				getRowFromResultSet.append("\t\t\trow.set"+initCapColumnName+"(rs.get"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(\""+columnName+"\")"+(initCapJavaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?".charAt(0)":"")+");\n");
			}
		}
		getRowFromResultSet.append("\t\t} catch(SQLException sqle){\n");
		getRowFromResultSet.append("\t\t\tFwException fe = new FwException();\n");
		getRowFromResultSet.append("\t\t\tfe.setMethodID(\"getRowFromResultSet\");\n");
		getRowFromResultSet.append("\t\t\tfe.setStackTraceValue(FwExceptionManager.getStackTraceValue(sqle));\n");
		getRowFromResultSet.append("\t\t\tfe.setExceptionText(String.valueOf(sqle));\n");
		getRowFromResultSet.append("\t\t\tthrow fe;\n");
		getRowFromResultSet.append("\t\t}\n");
		getRowFromResultSet.append("\t}\n\n");
		
		return getRowFromResultSet.toString();
	}
	
	
	public static String getSelect(String aTableName, List aPKColumnInfo, boolean aSimulation, String aSubSystem){
		
		StringBuffer select = new StringBuffer();
		select.append("\t/**\n");
		select.append("\t* select rows from database based on the given primarykey(s)\n");
		select.append("\t*/\n");
		if(aSimulation){
			if(aSubSystem.equals("access")){
				select.append("\tpublic ICargo[] select(FwDataCriteria[] aCriteria, FwDataSortOrder[] aSort, char aSimul, Connection aConn) {\n");				
			} else {
				select.append("\tpublic ICargo[] select(FwDAODataCriteria[] aCriteria, FwDataSortOrder[] aSort, char aSimul, Connection aConn) {\n");							
			}
		} else {
			if(aSubSystem.equals("access")){
				select.append("\tpublic ICargo[] select(FwDataCriteria[] aCriteria, FwDataSortOrder[] aSort, Connection aConn) {\n");				
			}else {
				select.append("\tpublic ICargo[] select(FwDAODataCriteria[] aCriteria, FwDataSortOrder[] aSort, Connection aConn) {\n");				
			}
		}
		select.append("\t\tList values = new ArrayList();\n");
		select.append("\t\tConnection conn = null;\n");
		select.append("\t\tPreparedStatement statement = null;\n");
		select.append("\t\tResultSet rs = null;\n");
		select.append("\t\t"+aTableName+"_Cargo rows[] = null;\n");
		select.append("\t\tStringBuffer whereClause = new StringBuffer();\n");
		select.append("\t\ttry{\n");
		select.append("\t\t\tconn = aConn;\n");
		select.append("\t\t\tif(aCriteria.length == 0) {\n"); 
		select.append("\t\t\t\tFwException fe = new FwException();\n");
		select.append("\t\t\t\tfe.setMethodID(\"select\");\n");
		select.append("\t\t\t\tfe.setServiceMessage(NO_SEARCH_CRITERIA);\n");
		select.append("\t\t\t\tthrow fe;\n");
		select.append("\t\t\t}\n");
		select.append("\t\t\twhereClause.append(FwConstants.WHERE);\n");
		select.append("\t\t\tboolean where = false;\n");
		select.append("\t\t\tboolean and = false;\n");
		select.append("\t\t\tfor(int i=0; i<aCriteria.length; i++){\n");
		for(int i=0; i<aPKColumnInfo.size(); i++){
			Map row =(Map) aPKColumnInfo.get(i);
			if(aSubSystem.equals("access")){
				select.append("\t\t\t\tif(aCriteria[i].getColumn_name().equals(\""+row.get(GeneratorConstants.COLUMN_NAME)+"\")){\n");				
			} else {
				select.append("\t\t\t\tif(aCriteria[i].getColumnName().equals(\""+row.get(GeneratorConstants.COLUMN_NAME)+"\")){\n");				
			}
			select.append("\t\t\t\t\twhere = true;\n");
			select.append("\t\t\t\t\tif(and){\n");
			select.append("\t\t\t\t\t\twhereClause.append(FwConstants.AND);\n");
			select.append("\t\t\t\t\t}\n");
			select.append("\t\t\t\t\twhereClause.append(\" "+row.get(GeneratorConstants.COLUMN_NAME)+"=?\");\n");
			select.append("\t\t\t\t\tand=true;\n");
			select.append("\t\t\t\t}\n");
		}
		select.append("\t\t\t}\n");
		select.append("\t\t\tif(!where) {\n");
		select.append("\t\t\t\tFwException fe = new FwException();\n");
		select.append("\t\t\t\tfe.setMethodID(\"select\");\n");
		select.append("\t\t\t\tfe.setServiceMessage(NO_PK_SEARCH_CRITERIA);\n");
		select.append("\t\t\t\tthrow fe;\n");
		select.append("\t\t\t}\n");
		select.append("\t\t\tif(aSort!=null && aSort.length>0){\n");
		select.append("\t\t\t\twhereClause.append(FwConstants.ORDER_BY);\n");
		select.append("\t\t\t\tfor(int i=0; i<aSort.length; i++){\n");
		select.append("\t\t\t\t\tif(i>0)whereClause.append(FwConstants.COMMA);\n");
		select.append("\t\t\t\t\twhereClause.append(aSort[i].getColumnName());\n");
		select.append("\t\t\t\t\tif(aSort[i].getSortOrder() == FwDataSortOrder.asc) {\n");
		select.append("\t\t\t\t\t\twhereClause.append(\" ASC\");\n");
		select.append("\t\t\t\t\t} else if (aSort[i].getSortOrder() == FwDataSortOrder.desc) {\n");
		select.append("\t\t\t\t\t\twhereClause.append(\" DESC\");\n");
		select.append("\t\t\t\t\t}\n");
		select.append("\t\t\t\t}\n");
		select.append("\t\t\t}\n");
		
		if(aSimulation) {
			select.append("\t\t\tif(aSimul == FwConstants.DB_PRODUCTION_ENV){\n");
			select.append("\t\t\t\tstatement = conn.prepareStatement(SELECT_SQL+whereClause);\n");
			select.append("\t\t\t} else if(aSimul == FwConstants.DB_SIMULATION_ENV){\n");
			select.append("\t\t\t\tstatement = conn.prepareStatement(SIMUL_SELECT_SQL+whereClause);\n");
			select.append("\t\t\t} else {\n");
			select.append("\t\t\t\tFwException fe = new FwException();\n");
			select.append("\t\t\t\tfe.setMethodID(\"select\");\n");
			select.append("\t\t\t\tfe.setServiceMessage(NO_DB_SCHEMA);\n");
			select.append("\t\t\t\tthrow fe;\n");
			select.append("\t\t\t}\n");
		} else {
			select.append("\t\t\tstatement = conn.prepareStatement(SELECT_SQL+whereClause);\n");
		}
		select.append("\t\t\tint pos=0;\n");
		select.append("\t\t\tfor(int j=0; j<aCriteria.length; j++) {\n");
		for(int i=0; i<aPKColumnInfo.size(); i++){
			Map columnInfo =(Map) aPKColumnInfo.get(i);
			if(aSubSystem.equals("access")){
				select.append("\t\t\t\tif(aCriteria[j].getColumn_name().equals(\""+columnInfo.get(GeneratorConstants.COLUMN_NAME)+"\")){\n");						
			}else{
				select.append("\t\t\t\tif(aCriteria[j].getColumnName().equals(\""+columnInfo.get(GeneratorConstants.COLUMN_NAME)+"\")){\n");				
			}
			String dataType = (String)columnInfo.get(GeneratorConstants.DATA_TYPE);
			String length = (String)columnInfo.get(GeneratorConstants.LENGTH);
			String scale = (String)columnInfo.get(GeneratorConstants.SCALE);
			String precision = (String)columnInfo.get(GeneratorConstants.PRECISION);
			String javaDataType = "";
			/*
			if(dataType.equals("NUMBER"))
				javaDataType="BigDecimal";
			else*/
				javaDataType = GeneraterHelper.getJavaDataType(dataType, length, scale,precision);
			String initCapJavaDataType = initCap(javaDataType.toLowerCase());
			if(aSubSystem.equals("access")){
				select.append("\t\t\t\t\tString value =aCriteria[j].getColumn_value();\n");
				String getStr=null;
				javaDataType = GeneraterHelper.getJavaDataType((String)columnInfo.get(GeneratorConstants.DAO_DATA_TYPE), length, scale,precision);
				initCapJavaDataType = initCap(javaDataType.toLowerCase());
				if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_SHORT)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_LONG)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_INT)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_DOUBLE) || javaDataType.equals(GeneratorConstants.JAVA_SQL_INT)){
					getStr = initCapJavaDataType+".parse"+initCapJavaDataType+"(value)";
				} else if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_DATE) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIMESTAMP)|| javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIME)) {
					getStr = initCapJavaDataType+".valueOf(value)";
				} else if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_CHAR) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_STRING)){
					getStr = "value";				
				}
				select.append("\t\t\t\t\tstatement.set"+initCapJavaDataType+"(++pos, "+getStr+");\n");				
			} else {
				select.append("\t\t\t\t\t"+javaDataType+" value =aCriteria[j].get"+initCapJavaDataType+"Value();\n");
				select.append("\t\t\t\t\tstatement.set"+initCapJavaDataType+"(++pos, value);\n");				
			}
			select.append("\t\t\t\t}\n");
		}
		select.append("\t\t\t}\n");
		select.append("\t\t\trs=statement.executeQuery();\n");
		select.append("\t\t\twhile(rs.next()){\n");
		select.append("\t\t\t\t"+aTableName+"_Cargo cargo = new "+aTableName+"_Cargo();\n");
		select.append("\t\t\t\tgetRowFromResultSet(cargo,rs);\n");
		select.append("\t\t\t\tvalues.add(cargo);\n");
		select.append("\t\t\t}\n");
		select.append("\t\t\trows = new "+aTableName+"_Cargo[values.size()];\n");
		select.append("\t\t\tvalues.toArray(rows);\n");
		select.append("\t\t}catch(SQLException sqle){\n");
		select.append("\t\t\tFwException fe = new FwException();\n");
		select.append("\t\t\tFwExceptionManager.setSqlca(sqle, fe);\n");
		select.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		select.append("\t\t\tfe.setMethodID(\"select\");\n");
		select.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		select.append("\t\t\tfe.setStackTraceValue(FwExceptionManager.getStackTraceValue(sqle));\n");
		select.append("\t\t\tfe.setExceptionText(String.valueOf(sqle));\n");
		select.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		select.append("\t\t\tfe.setServiceMethod(\"select\");\n");
		if(aSimulation){
			select.append("\t\t\tif(aSimul == FwConstants.DB_PRODUCTION_ENV){\n");
			select.append("\t\t\t\tfe.setServiceDescription(SELECT_SQL+whereClause);\n");
			select.append("\t\t\t} else {\n");
			select.append("\t\t\t\tfe.setServiceDescription(SIMUL_SELECT_SQL+whereClause);\n");
			select.append("\t\t\t}\n");
		} else {
			select.append("\t\t\t\tfe.setServiceDescription(SELECT_SQL+whereClause);\n");
		}
		select.append("\t\t\tthrow fe;\n");
		select.append("\t\t}catch(FwException fe){\n");
		select.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		select.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		select.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		select.append("\t\t\tfe.setServiceMethod(\"select\");\n");
		select.append("\t\t\tthrow fe;\n");
		select.append("\t\t}catch(Exception e){\n");
		select.append("\t\t\tFwException fe = new FwException();\n");
		select.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		select.append("\t\t\tfe.setMethodID(\"select\");\n");
		select.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		select.append("\t\t\tfe.setStackTraceValue(FwExceptionManager.getStackTraceValue(e));\n");
		select.append("\t\t\tfe.setExceptionText(String.valueOf(e));\n");
		select.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		select.append("\t\t\tfe.setServiceMethod(\"select\");\n");
		select.append("\t\t\tthrow fe;\n");
		select.append("\t\t}\n");
		select.append("\t\tfinally{\n");
		if(aSubSystem.equals("access")){
			select.append("\t\t\tcloseResultSetAndStatement(rs, statement);\n");			
		} else {
			select.append("\t\t\tcloseConnection(rs, statement);\n");			
		}
		select.append("\t\t}\n");
		select.append("\t\treturn rows;\n");
		select.append("\t}\n\n");

		return select.toString();
	}
	
	
	public static String getFindByPK(String aTableName, List aPKColumnInfo, boolean aSimulation, String aSubSystem){
		
		StringBuffer findByPK = new StringBuffer();
		
		findByPK.append("\t/**\n");
		findByPK.append("\t* returns a not null cargo if record exists\n");
		findByPK.append("\t*/\n");
		if(aSimulation){
			findByPK.append("\tpublic ICargo findByPrimaryKey(IPrimaryKey aKey, char aSimul, Connection aConn) {\n");			
		} else {
			findByPK.append("\tpublic ICargo findByPrimaryKey(IPrimaryKey aKey, Connection aConn) {\n");
		}
		//findByPK.append("\t\tArrayList values = new ArrayList();\n");
		findByPK.append("\t\tConnection conn = null;\n");
		findByPK.append("\t\tPreparedStatement statement = null;\n");
		findByPK.append("\t\tResultSet rs = null;\n");
		findByPK.append("\t\t"+aTableName+"_Cargo rescargo = null;\n");
		findByPK.append("\t\t"+aTableName+"_PrimaryKey key = ("+aTableName+"_PrimaryKey) aKey;\n");
		//findByPK.append("\t\tString whereClause=null;\n");
		findByPK.append("\t\ttry{\n");
		findByPK.append("\t\t\tint count=1;\n");
		findByPK.append("\t\t\tconn = aConn;\n");
		if(aSimulation){
			findByPK.append("\t\t\tif(aSimul == FwConstants.DB_PRODUCTION_ENV){\n");
			findByPK.append("\t\t\t\tstatement = conn.prepareStatement(PK_SELECT_SQL);\n");
			findByPK.append("\t\t\t} else if(aSimul == FwConstants.DB_SIMULATION_ENV){\n"); 
			findByPK.append("\t\t\t\tstatement = conn.prepareStatement(SIMUL_PK_SELECT_SQL);\n");
			findByPK.append("\t\t\t} else {\n");
			findByPK.append("\t\t\t\tFwException fe = new FwException();\n");
			findByPK.append("\t\t\t\tfe.setMethodID(\"findByPrimaryKey\");\n");
			findByPK.append("\t\t\t\tfe.setServiceMessage(NO_DB_SCHEMA);\n");
			findByPK.append("\t\t\t\tthrow fe;\n");
			findByPK.append("\t\t\t}\n");			
		} else {
			findByPK.append("\t\t\tstatement = conn.prepareStatement(PK_SELECT_SQL);\n");
		}
		for(int i=0; i<aPKColumnInfo.size(); i++) {
			Map columnInfo = (Map)aPKColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			String dataType = (String)columnInfo.get(GeneratorConstants.DATA_TYPE);
			String length = (String)columnInfo.get(GeneratorConstants.LENGTH);
			String scale = (String)columnInfo.get(GeneratorConstants.SCALE);
			String precision = (String)columnInfo.get(GeneratorConstants.PRECISION);
			String javaDataType = GeneraterHelper.getJavaDataType(dataType, length, scale, precision);
			String initCapJavaDataType = initCap(javaDataType.toLowerCase());
			String initCapColumnName = initCap(columnName.toLowerCase());
			if(aSubSystem.equals("access")){
				javaDataType = GeneraterHelper.getJavaDataType((String)columnInfo.get(GeneratorConstants.DAO_DATA_TYPE), length, scale,precision);
				initCapJavaDataType = initCap(javaDataType.toLowerCase());
				String getStr=null;
				if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_SHORT)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_LONG)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_INT)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_DOUBLE)){
					getStr = initCapJavaDataType+".parse"+initCapJavaDataType+"(key.get"+initCapColumnName+"())";
				} else if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_DATE) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIMESTAMP)|| javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIME)) {
					getStr = initCapJavaDataType+".valueOf(key.get"+initCapColumnName+"())";
				}
				findByPK.append("\t\t\tstatement.set"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,"+((javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)||javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_STRING))?"key.get"+initCapColumnName+"()":getStr)+");\n");
			} else {
				findByPK.append("\t\t\tstatement.set"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?"String.valueOf(key.get"+initCapColumnName+"())":"key.get"+initCapColumnName+"()")+");\n");				
			}
		}
		findByPK.append("\t\t\trs=statement.executeQuery();\n");
		findByPK.append("\t\t\tif(rs.next()){\n");
		findByPK.append("\t\t\t\trescargo = new "+aTableName+"_Cargo();\n");
		findByPK.append("\t\t\t}\n");
		findByPK.append("\t\t}catch(SQLException sqle){\n");
		findByPK.append("\t\t\tFwException fe = new FwException();\n");
		findByPK.append("\t\t\tFwExceptionManager.setSqlca(sqle, fe);\n");
		findByPK.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		findByPK.append("\t\t\tfe.setMethodID(\"findByPrimaryKey\");\n");
		findByPK.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		findByPK.append("\t\t\tfe.setStackTraceValue(FwExceptionManager.getStackTraceValue(sqle));\n");
		findByPK.append("\t\t\tfe.setExceptionText(String.valueOf(sqle));\n");
		findByPK.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		findByPK.append("\t\t\tfe.setServiceMethod(\"findByPrimaryKey\");\n");
		if(aSimulation){
			findByPK.append("\t\t\tif(aSimul == FwConstants.DB_PRODUCTION_ENV){\n");
			findByPK.append("\t\t\t\tfe.setServiceDescription(PK_SELECT_SQL);\n");
			findByPK.append("\t\t\t} else {\n");
			findByPK.append("\t\t\t\tfe.setServiceDescription(SIMUL_PK_SELECT_SQL);\n");
			findByPK.append("\t\t\t}\n");
		} else {
			findByPK.append("\t\t\t\tfe.setServiceDescription(PK_SELECT_SQL);\n");
		}
		findByPK.append("\t\t\tthrow fe;\n");
		findByPK.append("\t\t}catch(FwException fe){\n");
		findByPK.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		findByPK.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		findByPK.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		findByPK.append("\t\t\tfe.setServiceMethod(\"findByPrimaryKey\");\n");
		findByPK.append("\t\t\tthrow fe;\n");
		findByPK.append("\t\t}catch(Exception e){\n");
		findByPK.append("\t\t\tFwException fe = new FwException();\n");
		findByPK.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		findByPK.append("\t\t\tfe.setMethodID(\"findByPrimaryKey\");\n");
		findByPK.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		findByPK.append("\t\t\tfe.setStackTraceValue(FwExceptionManager.getStackTraceValue(e));\n");
		findByPK.append("\t\t\tfe.setExceptionText(String.valueOf(e));\n");
		findByPK.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		findByPK.append("\t\t\tfe.setServiceMethod(\"findByPrimaryKey\");\n");
		findByPK.append("\t\t\tthrow fe;\n");
		findByPK.append("\t\t}\n");
		findByPK.append("\t\tfinally{\n");
		if(aSubSystem.equals("access")){
			findByPK.append("\t\t\tcloseResultSetAndStatement(rs, statement);\n");			
		} else {
			findByPK.append("\t\t\tcloseConnection(rs, statement);\n");			
		}
		findByPK.append("\t\t}\n");
		findByPK.append("\t\treturn rescargo;\n");
		findByPK.append("\t}\n");
		
		return findByPK.toString();
	}
	
	public static String getInsert(String aTableName, List aAllColumnInfo, boolean aSimulation, String aSubSystem){
		
		StringBuffer insert = new StringBuffer();
		
		insert.append("\t/**\n");
		insert.append("\t* inserts a new row in the table\n");
		insert.append("\t*/\n");
		insert.append("\tpublic boolean insert(ICargo aCargo, Connection aConn) {\n");
		insert.append("\t\t"+aTableName+"_Cargo cargo = ("+aTableName+"_Cargo) aCargo;\n");
		insert.append("\t\tConnection conn = null;\n");
		insert.append("\t\tPreparedStatement statement=null;\n");
		insert.append("\t\ttry{\n");
		insert.append("\t\t\tint count=1;\n");
		insert.append("\t\t\tconn=aConn;\n");
		if(aSimulation){
			insert.append("\t\t\tif(cargo.getSimulation() == FwConstants.DB_PRODUCTION_ENV){\n");
			insert.append("\t\t\t\tstatement = conn.prepareStatement(INSERT_SQL);\n");
			insert.append("\t\t\t} else if(cargo.getSimulation() == FwConstants.DB_SIMULATION_ENV){\n"); 
			insert.append("\t\t\t\tstatement = conn.prepareStatement(SIMUL_INSERT_SQL);\n");
			insert.append("\t\t\t} else {\n");
			insert.append("\t\t\t\tFwException fe = new FwException();\n");
			insert.append("\t\t\t\tfe.setMethodID(\"insert\");\n");
			insert.append("\t\t\t\tfe.setServiceMessage(NO_DB_SCHEMA);\n");
			insert.append("\t\t\t\tthrow fe;\n");
			insert.append("\t\t\t}\n");
		} else {
			insert.append("\t\t\tstatement = conn.prepareStatement(INSERT_SQL);\n");
		}
		for(int i=0; i<aAllColumnInfo.size(); i++) {
			Map columnInfo = (Map)aAllColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			System.out.println(columnName);
			String dataType = (String)columnInfo.get(GeneratorConstants.DATA_TYPE);
			String length = (String)columnInfo.get(GeneratorConstants.LENGTH);
			String scale = (String)columnInfo.get(GeneratorConstants.SCALE);
			String precision = (String)columnInfo.get(GeneratorConstants.PRECISION);
			String javaDataType = GeneraterHelper.getJavaDataType(dataType, length, scale, precision);
			String initCapJavaDataType = initCap(javaDataType.toLowerCase());
			String initCapColumnName = initCap(columnName.toLowerCase());
			if(aSubSystem.equals("access")){
				javaDataType = GeneraterHelper.getJavaDataType((String)columnInfo.get(GeneratorConstants.DAO_DATA_TYPE), length, scale,precision);
				initCapJavaDataType = initCap(javaDataType.toLowerCase());
				String getStr=null;
				if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_SHORT)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_LONG)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_INT)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_DOUBLE) || javaDataType.equals(GeneratorConstants.JAVA_SQL_INT)){
					getStr = initCapJavaDataType+".parse"+initCapJavaDataType+"(cargo.get"+initCapColumnName+"())";
				} else if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_DATE) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIMESTAMP)|| javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIME)) {
					getStr = initCapJavaDataType+".valueOf(cargo.get"+initCapColumnName+"())";
				}
				insert.append("\t\t\tstatement.set"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,"+((javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)||javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_STRING))?"cargo.get"+initCapColumnName+"()":getStr)+");\n");
			} else {		
				//insert.append("\t\t\tstatement.set"+(dataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,cargo.get"+initCapColumnName+"());\n");
				insert.append("\t\t\tstatement.set"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?"String.valueOf(cargo.get"+initCapColumnName+"())":"cargo.get"+initCapColumnName+"()")+");\n");
			}
		}
		insert.append("\t\t\tint res = statement.executeUpdate();\n");
		insert.append("\t\t\tif(res==0) {\n"); 
		insert.append("\t\t\t\tFwException fe = new FwException();\n");
		insert.append("\t\t\t\tfe.setMethodID(\"insert\");\n");
		insert.append("\t\t\t\tfe.setServiceMessage(NO_REC_INSERTED);\n");
		insert.append("\t\t\t\tthrow fe;\n");
		insert.append("\t\t\t}\n");
		insert.append("\t\t}catch(SQLException sqle){\n");
		insert.append("\t\t\tFwException fe = new FwException();\n");
		insert.append("\t\t\tFwExceptionManager.setSqlca(sqle, fe);\n");
		insert.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		insert.append("\t\t\tfe.setMethodID(\"insert\");\n");
		insert.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		insert.append("\t\t\tfe.setStackTraceValue(FwExceptionManager.getStackTraceValue(sqle));\n");
		insert.append("\t\t\tfe.setExceptionText(String.valueOf(sqle));\n");
		insert.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		insert.append("\t\t\tfe.setServiceMethod(\"insert\");\n");
		if(aSimulation){
			insert.append("\t\t\tif(cargo.getSimulation() == FwConstants.DB_PRODUCTION_ENV){\n");
			insert.append("\t\t\t\tfe.setServiceDescription(INSERT_SQL);\n");
			insert.append("\t\t\t} else {\n");
			insert.append("\t\t\t\tfe.setServiceDescription(SIMUL_INSERT_SQL);\n");
			insert.append("\t\t\t}\n");			
		} else {
			insert.append("\t\t\tfe.setServiceDescription(INSERT_SQL);\n");
		}
		insert.append("\t\t\tthrow fe;\n");
		insert.append("\t\t}catch(FwException fe){\n");
		insert.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		insert.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		insert.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		insert.append("\t\t\tfe.setServiceMethod(\"insert\");\n");
		insert.append("\t\t\tthrow fe;\n");
		insert.append("\t\t}catch(Exception e){\n");
		insert.append("\t\t\tFwException fe = new FwException();\n");
		insert.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		insert.append("\t\t\tfe.setMethodID(\"insert\");\n");
		insert.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		insert.append("\t\t\tfe.setStackTraceValue(FwExceptionManager.getStackTraceValue(e));\n");
		insert.append("\t\t\tfe.setExceptionText(String.valueOf(e));\n");
		insert.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		insert.append("\t\t\tfe.setServiceMethod(\"insert\");\n");
		insert.append("\t\t\tthrow fe;\n");
		insert.append("\t\t}\n");
		insert.append("\t\tfinally{\n");
		if(aSubSystem.equals("access")){
			insert.append("\t\t\tcloseStatement(statement);\n");			
		} else {
			insert.append("\t\t\tcloseConnection(statement);\n");			
		}		
		insert.append("\t\t}\n");
		insert.append("\t\treturn true;\n");
		insert.append("\t}\n\n");
		
		return insert.toString();
	}	
	
	public static String getUpdate(String aTableName, List aAllColumnInfo, List aPKColumnInfo, boolean aSimulation, String aSubSystem){
	
		StringBuffer update = new StringBuffer();
		
		update.append("\t/**\n");
		update.append("\t* updates row in the table\n");
		update.append("\t*/\n");
		update.append("\tpublic boolean update(ICargo aCargo, Connection aConn) {\n");
		update.append("\t\t"+aTableName+"_Cargo cargo = ("+aTableName+"_Cargo) aCargo;\n");
		update.append("\t\tConnection conn = null;\n");
		update.append("\t\tPreparedStatement statement=null;\n");
		update.append("\t\ttry{\n");	
		update.append("\t\t\tint count=1;\n");
		update.append("\t\t\tconn=aConn;\n");
		if(aSimulation){
			update.append("\t\t\tif(cargo.getSimulation() == FwConstants.DB_PRODUCTION_ENV){\n");
			update.append("\t\t\t\tstatement = conn.prepareStatement(UPDATE_SQL);\n");
			update.append("\t\t\t} else if(cargo.getSimulation() == FwConstants.DB_SIMULATION_ENV){\n"); 
			update.append("\t\t\t\tstatement = conn.prepareStatement(SIMUL_UPDATE_SQL);\n");
			update.append("\t\t\t} else {\n");
			update.append("\t\t\t\tFwException fe = new FwException();\n");
			update.append("\t\t\t\tfe.setMethodID(\"update\");\n");
			update.append("\t\t\t\tfe.setServiceMessage(NO_DB_SCHEMA);\n");
			update.append("\t\t\t\tthrow fe;\n");
			update.append("\t\t\t}\n");			
		} else {
			update.append("\t\t\tstatement = conn.prepareStatement(UPDATE_SQL);\n");
		}

		// getting the PK columns
		List pkList = new ArrayList();
		for(int i=0; i<aPKColumnInfo.size(); i++){
			Map row = (Map)aPKColumnInfo.get(i);
			pkList.add(row.get(GeneratorConstants.COLUMN_NAME));
		}

		for(int i=0; i<aAllColumnInfo.size(); i++){
			Map columnInfo = (Map)aAllColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			if(pkList.contains(columnName)){
				continue;	
			}
			String dataType = (String)columnInfo.get(GeneratorConstants.DATA_TYPE);
			String length = (String)columnInfo.get(GeneratorConstants.LENGTH);
			String scale = (String)columnInfo.get(GeneratorConstants.SCALE);
			String precision = (String)columnInfo.get(GeneratorConstants.PRECISION);
			String javaDataType = GeneraterHelper.getJavaDataType(dataType, length, scale, precision);
			String initCapJavaDataType = initCap(javaDataType.toLowerCase());
			String initCapColumnName = initCap(columnName.toLowerCase());		
			if(aSubSystem.equals("access")){
				javaDataType = GeneraterHelper.getJavaDataType((String)columnInfo.get(GeneratorConstants.DAO_DATA_TYPE), length, scale,precision);
				initCapJavaDataType = initCap(javaDataType.toLowerCase());
				String getStr=null;
				if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_SHORT)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_LONG)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_INT)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_DOUBLE) || javaDataType.equals(GeneratorConstants.JAVA_SQL_INT)){
					getStr = initCapJavaDataType+".parse"+initCapJavaDataType+"(cargo.get"+initCapColumnName+"())";
				} else if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_DATE) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIMESTAMP)|| javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIME)) {
					getStr = initCapJavaDataType+".valueOf(cargo.get"+initCapColumnName+"())";
				}
				update.append("\t\t\tstatement.set"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,"+((javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)||javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_STRING))?"cargo.get"+initCapColumnName+"()":getStr)+");\n");
			} else {			
				//update.append("\t\t\tstatement.set"+(dataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,cargo.get"+initCapColumnName+"());\n");
				update.append("\t\t\tstatement.set"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?"String.valueOf(cargo.get"+initCapColumnName+"())":"cargo.get"+initCapColumnName+"()")+");\n");	
			}
		}
		for(int i=0; i<aPKColumnInfo.size(); i++){
			Map columnInfo = (Map)aAllColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			String dataType = (String)columnInfo.get(GeneratorConstants.DATA_TYPE);
			String length = (String)columnInfo.get(GeneratorConstants.LENGTH);
			String scale = (String)columnInfo.get(GeneratorConstants.SCALE);
			String precision = (String)columnInfo.get(GeneratorConstants.PRECISION);
			String javaDataType = GeneraterHelper.getJavaDataType(dataType, length, scale, precision);
			String initCapJavaDataType = initCap(javaDataType.toLowerCase());
			String initCapColumnName = initCap(columnName.toLowerCase());		
			if(aSubSystem.equals("access")){
				javaDataType = GeneraterHelper.getJavaDataType((String)columnInfo.get(GeneratorConstants.DAO_DATA_TYPE), length, scale,precision);
				initCapJavaDataType = initCap(javaDataType.toLowerCase());
				String getStr=null;
				if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_SHORT)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_LONG)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_INT)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_DOUBLE)){
					getStr = initCapJavaDataType+".parse"+initCapJavaDataType+"(cargo.get"+initCapColumnName+"())";
				} else if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_DATE) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIMESTAMP)|| javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIME)) {
					getStr = initCapJavaDataType+".valueOf(cargo.get"+initCapColumnName+"())";
				}
				update.append("\t\t\tstatement.set"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,"+((javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)||javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_STRING))?"cargo.get"+initCapColumnName+"()":getStr)+");\n");
			} else {
				update.append("\t\t\tstatement.set"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,cargo.get"+initCapColumnName+"());\n");				
			}
		}
		update.append("\t\t\tint res = statement.executeUpdate();\n");
		update.append("\t\t\tif(res==0) {\n"); 
		update.append("\t\t\t\tFwException fe = new FwException();\n");
		update.append("\t\t\t\tfe.setMethodID(\"update\");\n");
		update.append("\t\t\t\tfe.setServiceMessage(NO_REC_UPDATED);\n");
		update.append("\t\t\t\tthrow fe;\n");
		update.append("\t\t\t}\n");
		update.append("\t\t}catch(SQLException sqle){\n");
		update.append("\t\t\tFwException fe = new FwException();\n");
		update.append("\t\t\tFwExceptionManager.setSqlca(sqle, fe);\n");
		update.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		update.append("\t\t\tfe.setMethodID(\"update\");\n");
		update.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		update.append("\t\t\tfe.setStackTraceValue(FwExceptionManager.getStackTraceValue(sqle));\n");
		update.append("\t\t\tfe.setExceptionText(String.valueOf(sqle));\n");
		update.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		update.append("\t\t\tfe.setServiceMethod(\"update\");\n");
		if(aSimulation){
			update.append("\t\t\tif(cargo.getSimulation() == FwConstants.DB_PRODUCTION_ENV){\n");
			update.append("\t\t\t\tfe.setServiceDescription(UPDATE_SQL);\n");
			update.append("\t\t\t} else {\n");
			update.append("\t\t\t\tfe.setServiceDescription(SIMUL_UPDATE_SQL);\n");
			update.append("\t\t\t}\n");			
		} else {
			update.append("\t\t\tfe.setServiceDescription(UPDATE_SQL);\n");
		}
		update.append("\t\t\tthrow fe;\n");
		update.append("\t\t}catch(FwException fe){\n");
		update.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		update.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		update.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		update.append("\t\t\tfe.setServiceMethod(\"update\");\n");
		update.append("\t\t\tthrow fe;\n");
		update.append("\t\t}catch(Exception e){\n");
		update.append("\t\t\tFwException fe = new FwException();\n");
		update.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		update.append("\t\t\tfe.setMethodID(\"update\");\n");
		update.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		update.append("\t\t\tfe.setStackTraceValue(FwExceptionManager.getStackTraceValue(e));\n");
		update.append("\t\t\tfe.setExceptionText(String.valueOf(e));\n");
		update.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		update.append("\t\t\tfe.setServiceMethod(\"update\");\n");
		update.append("\t\t\tthrow fe;\n");
		update.append("\t\t}\n");
		update.append("\t\tfinally{\n");
		if(aSubSystem.equals("access")){
			update.append("\t\t\tcloseStatement(statement);\n");			
		} else {
			update.append("\t\t\tcloseConnection(statement);\n");			
		}
		update.append("\t\t}\n");
		update.append("\t\treturn true;\n");
		update.append("\t}\n\n");		

		return update.toString();
	}

	public static String getDelete(String aTableName, List aPKColumnInfo, boolean aSimulation, String aSubSystem){
		
		StringBuffer delete = new StringBuffer();
		
		delete.append("\t/**\n");
		delete.append("\t* deletes row in the table\n");
		delete.append("\t*/\n");
		delete.append("\tpublic boolean delete(ICargo aCargo, Connection aConn) {\n");
		delete.append("\t\t"+aTableName+"_Cargo cargo = ("+aTableName+"_Cargo) aCargo;\n");
		delete.append("\t\tConnection conn = null;\n");
		delete.append("\t\tPreparedStatement statement=null;\n");
		delete.append("\t\ttry{\n");
		delete.append("\t\t\tint count=1;\n");
		delete.append("\t\t\tconn=aConn;\n");
		if(aSimulation){
			delete.append("\t\t\tif(cargo.getSimulation() == FwConstants.DB_PRODUCTION_ENV){\n");
			delete.append("\t\t\t\tstatement = conn.prepareStatement(DELETE_SQL);\n");
			delete.append("\t\t\t} else if(cargo.getSimulation() == FwConstants.DB_SIMULATION_ENV){\n"); 
			delete.append("\t\t\t\tstatement = conn.prepareStatement(SIMUL_DELETE_SQL);\n");
			delete.append("\t\t\t} else {\n");
			delete.append("\t\t\t\tFwException fe = new FwException();\n");
			delete.append("\t\t\t\tfe.setMethodID(\"delete\");\n");
			delete.append("\t\t\t\tfe.setServiceMessage(NO_DB_SCHEMA);\n");
			delete.append("\t\t\t\tthrow fe;\n");
			delete.append("\t\t\t}\n");
		} else {
			delete.append("\t\t\tstatement = conn.prepareStatement(DELETE_SQL);\n");
		}
		for(int i=0; i<aPKColumnInfo.size(); i++) {
			Map columnInfo = (Map)aPKColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			String dataType = (String)columnInfo.get(GeneratorConstants.DATA_TYPE);
			String length = (String)columnInfo.get(GeneratorConstants.LENGTH);
			String scale = (String)columnInfo.get(GeneratorConstants.SCALE);
			String precision = (String)columnInfo.get(GeneratorConstants.PRECISION);
			String javaDataType = GeneraterHelper.getJavaDataType(dataType, length, scale, precision);
			String initCapJavaDataType = initCap(javaDataType.toLowerCase());
			String initCapColumnName = initCap(columnName.toLowerCase());
			if(aSubSystem.equals("access")){
				javaDataType = GeneraterHelper.getJavaDataType((String)columnInfo.get(GeneratorConstants.DAO_DATA_TYPE), length, scale,precision);
				initCapJavaDataType = initCap(javaDataType.toLowerCase());
				String getStr=null;
				if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_SHORT)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_LONG)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_INT)||javaDataType.equals(GeneratorConstants.JAVA_TYPE_DOUBLE)){
					getStr = initCapJavaDataType+".parse"+initCapJavaDataType+"(cargo.get"+initCapColumnName+"())";
				} else if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_DATE) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIMESTAMP)|| javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIME)) {
					getStr = initCapJavaDataType+".valueOf(cargo.get"+initCapColumnName+"())";
				}
				delete.append("\t\t\tstatement.set"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,"+((javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)||javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_STRING))?"cargo.get"+initCapColumnName+"()":getStr)+");\n");
			} else {
				//delete.append("\t\t\tstatement.set"+(dataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,cargo.get"+initCapColumnName+"());\n");
				delete.append("\t\t\tstatement.set"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?GeneratorConstants.JAVA_TYPE_STRING:initCapJavaDataType)+"(count++,"+(javaDataType.equalsIgnoreCase(GeneratorConstants.JAVA_TYPE_CHAR)?"String.valueOf(cargo.get"+initCapColumnName+"())":"cargo.get"+initCapColumnName+"()")+");\n");
			}
		}
		delete.append("\t\t\tint res = statement.executeUpdate();\n");
		delete.append("\t\t\tif(res==0) {\n"); 
		delete.append("\t\t\t\tFwException fe = new FwException();\n");
		delete.append("\t\t\t\tfe.setMethodID(\"delete\");\n");
		delete.append("\t\t\t\tfe.setServiceMessage(NO_REC_DELETED);\n");
		delete.append("\t\t\t\tthrow fe;\n");
		delete.append("\t\t\t}\n");
		delete.append("\t\t}catch(SQLException sqle){\n");
		delete.append("\t\t\tFwException fe = new FwException();\n");
		delete.append("\t\t\tFwExceptionManager.setSqlca(sqle, fe);\n");
		delete.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		delete.append("\t\t\tfe.setMethodID(\"delete\");\n");
		delete.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		delete.append("\t\t\tfe.setStackTraceValue(FwExceptionManager.getStackTraceValue(sqle));\n");
		delete.append("\t\t\tfe.setExceptionText(String.valueOf(sqle));\n");
		delete.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		delete.append("\t\t\tfe.setServiceMethod(\"delete\");\n");
		if(aSimulation){
			delete.append("\t\t\tif(cargo.getSimulation() == FwConstants.DB_PRODUCTION_ENV){\n");
			delete.append("\t\t\t\tfe.setServiceDescription(DELETE_SQL);\n");
			delete.append("\t\t\t} else {\n");
			delete.append("\t\t\t\tfe.setServiceDescription(SIMUL_DELETE_SQL);\n");
			delete.append("\t\t\t}\n");			
		} else {
			delete.append("\t\t\tfe.setServiceDescription(DELETE_SQL);\n");
		}
		delete.append("\t\t\tthrow fe;\n");
		delete.append("\t\t}catch(FwException fe){\n");
		delete.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		delete.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		delete.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		delete.append("\t\t\tfe.setServiceMethod(\"delete\");\n");
		delete.append("\t\t\tthrow fe;\n");
		delete.append("\t\t}catch(Exception e){\n");
		delete.append("\t\t\tFwException fe = new FwException();\n");
		delete.append("\t\t\tfe.setClassID(this.getClass().getName());\n");
		delete.append("\t\t\tfe.setMethodID(\"delete\");\n");
		delete.append("\t\t\tfe.setExceptionType(FwConstants.EXP_TYP_DAO);\n");
		delete.append("\t\t\tfe.setStackTraceValue(FwExceptionManager.getStackTraceValue(e));\n");
		delete.append("\t\t\tfe.setExceptionText(String.valueOf(e));\n");
		delete.append("\t\t\tfe.setServiceName(this.getClass().getName());\n");
		delete.append("\t\t\tfe.setServiceMethod(\"delete\");\n");
		delete.append("\t\t\tthrow fe;\n");
		delete.append("\t\t}\n");
		delete.append("\t\tfinally{\n");
		if(aSubSystem.equals("access")){
			delete.append("\t\t\tcloseStatement(statement);\n");			
		} else {
			delete.append("\t\t\tcloseConnection(statement);\n");			
		}
		delete.append("\t\t}\n");
		delete.append("\t\treturn true;\n");
		delete.append("\t}\n\n");
		
		return delete.toString();
	}
	
	private static String initCap(String aText){
		
		if(aText!=null && aText.length()>0){
			String firstLetter = String.valueOf(aText.charAt(0)).toUpperCase();
			return firstLetter+aText.substring(1);
		} else {
			return aText;
		}
	}

	public static String getHistoryType(){
		
		StringBuffer historyType = new StringBuffer();
		
		historyType.append("\t/**\n");
		historyType.append("\t* returns the History Type of the DAO\n"); 
		historyType.append("\t*/\n");
		historyType.append("\tpublic short getHistoryType() {\n");
		historyType.append("\t\treturn HISTORY_TYPE;\n");
		historyType.append("\t}\n");
		
		return historyType.toString();		
	}

	public static String getSubClass(String aSubSystem, String aTableName) {

		StringBuffer subClass = new StringBuffer();
				
//		subClass.append("package gov.wisconsin."+aSubSystem+".data.db2.impl;\n\n");
//		subClass.append("import gov.wisconsin."+aSubSystem+".data.db2.base.Abstract_"+aTableName+"_DAO;\n\n");
		
//		subClass.append("package gov.wisconsin."+aSubSystem+".data.oracle.impl;\n\n");
//		subClass.append("import gov.wisconsin."+aSubSystem+".data.oracle.base.Abstract_"+aTableName+"_DAO;\n\n");
		subClass.append("package gov.selfservice.data.db2.impl;\n\n");
		subClass.append("import gov.selfservice.data.db2.base.Abstract_"+aTableName+"_DAO;\n\n");
		
		subClass.append("/**\n");
		subClass.append("* Data Access Object for "+aTableName+"\n");
		subClass.append("* @author CodeGenerator - Architecture Team\n");
		subClass.append("* @Creation Date "+new Date()+"\n");
		subClass.append("*/\n");
		subClass.append("public class "+aTableName+"_DAO extends Abstract_"+aTableName+"_DAO { \n\n");		
		subClass.append("}\n");
		
		return subClass.toString();			
	}
}
