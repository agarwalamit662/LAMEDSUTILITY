package com.classroom.utility.ccd;




import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Generates PrimaryKey object for given input values
 * @author Anand Balasubramanian
 * Created on Apr 13, 2006
 */

public class PKGenerator {
	public static void generate(String aSubSystem, String aTableName, List aPKColumnInfo) throws Exception {
		
		try {
			StringBuffer cargo = new StringBuffer();
			if(aSubSystem.equals("access")){
				GeneraterHelper.updateDataType2String(aPKColumnInfo);
			}
			// get package imports
			cargo.append(getPackageAndImports(aSubSystem));

			// get class
			cargo.append(getClassDeclaration(aSubSystem, aTableName));
			
			//  fields and getter ans setter methods
			cargo.append(getFields(aPKColumnInfo));
			
			// inspectCargo
			cargo.append(getInspectPK(aTableName, aPKColumnInfo)); 
		
			// haseCode
			cargo.append(getHashCode(aPKColumnInfo));
			
			// close class
			cargo.append("}");

			BufferedWriter bw =	new BufferedWriter(new FileWriter("C:/GeneratedCode/ccd/"+aTableName+"_PrimaryKey.java"));
			bw.write(cargo.toString());
			bw.flush();
			bw.close();

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
	
	public static String getPackageAndImports(String aSubSystem){
		
		StringBuffer packageAndImport = new StringBuffer();
//		packageAndImport.append("package gov.wisconsin."+aSubSystem+".business.entities;\n\n");
//		packageAndImport.append("import gov.wisconsin.framework.business.entities.IPrimaryKey;\n\n");
		packageAndImport.append("package gov.selfservice.business.entities;\n\n");
		packageAndImport.append("import gov.selfservice.framework.business.entities.IPrimaryKey;\n\n");
		packageAndImport.append("import java.sql.Timestamp;\n");
		packageAndImport.append("import java.sql.Date;\n");
		packageAndImport.append("import java.sql.Time;\n");
		
		packageAndImport.append("\n");
		return packageAndImport.toString();
	}
	
	public static String getClassDeclaration(String aSubSystem, String aTableName){
	
		StringBuffer classDeclaration = new StringBuffer();
		classDeclaration.append("/**\n");
		classDeclaration.append("* This java bean contains the primary keys for the table "+aTableName+"\n");
		classDeclaration.append("* @author CodeGenerator - Architecture Team\n");
		classDeclaration.append("* @Creation Date "+new Date()+"\n");
		classDeclaration.append("* Modified By:\n");
		classDeclaration.append("* Modified on:\n");
		classDeclaration.append("* PCR#\n");
		classDeclaration.append("*/\n");
		classDeclaration.append("public class "+aTableName+"_PrimaryKey implements java.io.Serializable, IPrimaryKey { \n\n");

		return classDeclaration.toString();
	}
	
	public static String getFields(List aPKColumnInfo) {
		
		StringBuffer fields = new StringBuffer();
		StringBuffer getterSetter = new StringBuffer();
		
		for(int i=0; i<aPKColumnInfo.size(); i++){
			
			Map columnInfo = (Map)aPKColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			String dataType = (String)columnInfo.get(GeneratorConstants.DATA_TYPE);
			String length = (String)columnInfo.get(GeneratorConstants.LENGTH);
			String scale = (String)columnInfo.get(GeneratorConstants.SCALE);
			String precision = (String)columnInfo.get(GeneratorConstants.PRECISION);
			String javaDataType = GeneraterHelper.getJavaDataType(dataType, length, scale, precision);
			String lowerCaseColumnName = columnName.toLowerCase();
			String initCapColumnName = initCap(lowerCaseColumnName);
			
			fields.append("\tprivate "+javaDataType+" "+lowerCaseColumnName+";\n");
			
			getterSetter.append("\n\t/**\n");
			getterSetter.append("\t*returns the "+lowerCaseColumnName+" value.\n");
			getterSetter.append("\t*/\n");
			getterSetter.append("\tpublic "+javaDataType+" get"+initCapColumnName+"() {\n");
			getterSetter.append("\t\treturn "+lowerCaseColumnName+";\n");
			getterSetter.append("\t}\n\n");
			
			getterSetter.append("\n\t/**\n");
			getterSetter.append("\t*sets the "+lowerCaseColumnName+" value.\n");
			getterSetter.append("\t*/\n");
			getterSetter.append("\tpublic void set"+initCapColumnName+"("+javaDataType+" "+lowerCaseColumnName+") {\n");
			getterSetter.append("\t\tthis."+lowerCaseColumnName+"="+lowerCaseColumnName+";\n");
			getterSetter.append("\t}\n\n");
		}
		return fields.toString() +"\n"+getterSetter.toString();
	}

	private static String initCap(String aText){
		
		if(aText!=null && aText.length()>0){
			String firstLetter = String.valueOf(aText.charAt(0)).toUpperCase();
			return firstLetter+aText.substring(1);
		} else {
			return aText;
		}
	}
	
	public static String getInspectPK(String aTableName, List aPKColumnInfo){
		
		StringBuffer inspectCargo = new StringBuffer();
		
		inspectCargo.append("\n\t/**\n");
		inspectCargo.append("\t*returns the string value of cargo.\n");
		inspectCargo.append("\t*/\n");
		inspectCargo.append("\tpublic String inspectPrimaryKey(){\n");
		inspectCargo.append("\t\treturn new StringBuffer().append(\""+aTableName+": \")");
		for(int i=0; i<aPKColumnInfo.size(); i++){
			Map columnInfo = (Map)aPKColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			String lowerCaseColumnName = columnName.toLowerCase();
			inspectCargo.append(".append(\""+lowerCaseColumnName+"=\").append("+lowerCaseColumnName+")");
		}
		inspectCargo.append(".toString();\n");
		inspectCargo.append("\t}\n\n");
		return inspectCargo.toString();
	}
	
	public static String getHashCode(List aPKColumnInfo) {
		
		StringBuffer hashCode = new StringBuffer();
		hashCode.append("\n\t/**\n");
		hashCode.append("\t*returns the hashcode value of cargo.\n");
		hashCode.append("\t*/\n");
		hashCode.append("\tpublic int hashCode(){\n");
		hashCode.append("\t\tint hash = 7;\n");
		
		for(int i=0; i<aPKColumnInfo.size(); i++) {
			Map columnInfo = (Map)aPKColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			String dataType = (String)columnInfo.get(GeneratorConstants.DATA_TYPE);
			String length = (String)columnInfo.get(GeneratorConstants.LENGTH);
			String scale = (String)columnInfo.get(GeneratorConstants.SCALE);
			String precision = (String)columnInfo.get(GeneratorConstants.PRECISION);
			String javaDataType = GeneraterHelper.getJavaDataType(dataType, length, scale, precision);
			String lowerCaseColumnName = columnName.toLowerCase();
				
			if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_LONG)) {
				hashCode.append("\t\thash = 31 * hash + (int)("+lowerCaseColumnName+" ^ ("+lowerCaseColumnName+" >>> 32));\n");
			} else if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_DOUBLE)) {
				hashCode.append("\t\thash = 31 * hash + (int)(Double.doubleToLongBits("+lowerCaseColumnName+") ^ (Double.doubleToLongBits("+lowerCaseColumnName+") >>> 32));\n");
			} else if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_STRING) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_DATE) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIME) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIMESTAMP)) {
				hashCode.append("\t\thash = 31 * hash + (null == "+lowerCaseColumnName+" ? 0 : "+lowerCaseColumnName+".hashCode());\n");	
			} else {
				hashCode.append("\t\thash = 31 * hash + (int)"+lowerCaseColumnName+";\n");
			}
		}
		hashCode.append("\t\treturn hash;\n");
		hashCode.append("\t}\n\n");
		return hashCode.toString();
	}
}
