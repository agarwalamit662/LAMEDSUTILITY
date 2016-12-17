
package com.classroom.utility.ccd;



import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Generates Cargo object for given input values
 * @author Anand Balasubramanian
 * Created on Apr 10, 2006
 */

public class CargoGenerator {

	public static void generate(String aSubSystem, String aTableName, List aAllColumnInfo, List aPKColumnInfo) throws Exception {
		
		try {
			StringBuffer cargo = new StringBuffer();
			if(aSubSystem.equals("access")){
				GeneraterHelper.updateDataType2String(aAllColumnInfo);				
			}
			if(aSubSystem.equals("access")){
				GeneraterHelper.updateDataType2String(aPKColumnInfo);
			}

			// get package imports
			cargo.append(getPackageAndImports(aSubSystem, aTableName));

			// get class
			cargo.append(getClassDeclaration(aSubSystem, aTableName));
			
			//  fields and getter ans setter methods
			cargo.append(getFields(aAllColumnInfo));
			
			// inspectCargo
			cargo.append(getInspectCargo(aTableName, aAllColumnInfo)); 
		
			// haseCode
			cargo.append(getHashCode(aAllColumnInfo));
			
			// getPrimaryKey
			cargo.append(getPrimaryKey(aTableName, aPKColumnInfo));
			
			// compareAttributes
			if(aSubSystem.equals("access")){
				cargo.append(getCompareAttributes(aTableName, aAllColumnInfo));
			}
			
			// get package
			if(!aSubSystem.equals("wits") && !aSubSystem.equals("access") && !aSubSystem.equals("studio")){
				cargo.append(getPackage());				
			}
			
			// close class
			cargo.append("}");
			
			File dir = new File("C:/GeneratedCode");
			if(!dir.exists()){
				dir.mkdir();
			}
			
			BufferedWriter bw =	new BufferedWriter(new FileWriter("C:/GeneratedCode/ccd/"+aTableName+"_Cargo.java"));
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
	
	public static String getPackageAndImports(String aSubSystem, String aTableName){
		
		StringBuffer packageAndImport = new StringBuffer();
//		packageAndImport.append("package gov.selfservice."+aSubSystem+".business.entities;\n\n");
		packageAndImport.append("package gov.selfservice.business.entities;\n\n");
		packageAndImport.append("import gov.selfservice.framework.business.entities.AbstractCargo;\n");
		packageAndImport.append("import gov.selfservice.framework.business.entities.IPrimaryKey;\n");
//		packageAndImport.append("import gov.selfservice."+aSubSystem+".business.entities."+aTableName+"_PrimaryKey;\n\n");
		packageAndImport.append("import gov.selfservice.business.entities."+aTableName+"_PrimaryKey;\n\n");
	
		packageAndImport.append("import java.sql.Date;\n");
		packageAndImport.append("import java.sql.Timestamp;\n");
		packageAndImport.append("import java.util.Map;\n");
		packageAndImport.append("import java.util.HashMap;\n");
		
		packageAndImport.append("\n");
		return packageAndImport.toString();
	}
	
	public static String getClassDeclaration(String aSubSystem, String aTableName){
	
		StringBuffer classDeclaration = new StringBuffer();
		classDeclaration.append("/**\n");
		classDeclaration.append("* This java bean contains the entities of "+aTableName+"\n");
		classDeclaration.append("* @author CodeGenerator - Architecture Team\n");
		classDeclaration.append("* @Creation Date "+new Date()+"\n");
		classDeclaration.append("* Modified By:\n");
		classDeclaration.append("* Modified on:\n");
		classDeclaration.append("* PCR#\n");
		classDeclaration.append("*/\n");
		classDeclaration.append("public class "+aTableName+"_Cargo extends AbstractCargo implements java.io.Serializable { \n\n");
//		if(!aSubSystem.equals("wits") && !aSubSystem.equals("access") && !aSubSystem.equals("studio")){
	//		classDeclaration.append("\tprivate static final String PACKAGE =\"gov.wisconsin."+aSubSystem+".business.entities.impl."+aTableName+"\";\n\n");
			classDeclaration.append("\tprivate static final String PACKAGE =\"gov.selfservice.business.entities.impl."+aTableName+"\";\n\n");
//		}
		
		return classDeclaration.toString();
	}
	
	public static String getFields(List aAllColumnInfo) {
		
		StringBuffer fields = new StringBuffer();
		StringBuffer getterSetter = new StringBuffer();
		fields.append("\tboolean isDirty = false;\n");
		
		for(int i=0; i<aAllColumnInfo.size(); i++){
			
			Map columnInfo = (Map)aAllColumnInfo.get(i);
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
	
	public static String getInspectCargo(String aTableName, List aAllColumnInfo){
		
		StringBuffer inspectCargo = new StringBuffer();
		
		inspectCargo.append("\n\t/**\n");
		inspectCargo.append("\t*returns the string value of cargo.\n");
		inspectCargo.append("\t*/\n");
		inspectCargo.append("\tpublic String inspectCargo(){\n");
		inspectCargo.append("\t\treturn new StringBuffer().append(\""+aTableName+": \")");
		for(int i=0; i<aAllColumnInfo.size(); i++){
			Map columnInfo = (Map)aAllColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			String lowerCaseColumnName = columnName.toLowerCase();
			inspectCargo.append(".append(\""+lowerCaseColumnName+"=\").append("+lowerCaseColumnName+")");
		}
		inspectCargo.append(".toString();\n");
		inspectCargo.append("\t}\n\n");
		return inspectCargo.toString();
	}
	
	public static String getHashCode(List aAllColumnInfo) {
		
		StringBuffer hashCode = new StringBuffer();
		hashCode.append("\n\t/**\n");
		hashCode.append("\t*returns the hashcode value of cargo.\n");
		hashCode.append("\t*/\n");
		hashCode.append("\tpublic int hashCode(){\n");
		hashCode.append("\t\tint hash = 7;\n");
		
		for(int i=0; i<aAllColumnInfo.size(); i++) {
			Map columnInfo = (Map)aAllColumnInfo.get(i);
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
			} else if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_DATE) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIME) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIMESTAMP)) {
				hashCode.append("\t\thash = 31 * hash + (null == "+lowerCaseColumnName+" ? 0 : "+lowerCaseColumnName+".hashCode());\n");
			} else if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_STRING)){
				hashCode.append("\t\thash = 31 * hash + (null == "+lowerCaseColumnName+" ? 0 : "+lowerCaseColumnName+".trim().hashCode());\n");					
			} else {
				hashCode.append("\t\thash = 31 * hash + (int)"+lowerCaseColumnName+";\n");
			}
		}
		hashCode.append("\t\treturn hash;\n");
		hashCode.append("\t}\n\n");
		return hashCode.toString();
	}
	
	public static String getPrimaryKey(String aTableName, List aPKColumnInfo){
		
		StringBuffer primaryKey = new StringBuffer();
		primaryKey.append("\n\t/**\n");
		primaryKey.append("\t*returns the PrimaryKey object.\n");
		primaryKey.append("\t*/\n");
		primaryKey.append("\tpublic IPrimaryKey getPrimaryKey(){\n");
		primaryKey.append("\t\t"+aTableName+"_PrimaryKey key = new "+aTableName+"_PrimaryKey();\n");		
		
		for(int i=0; i<aPKColumnInfo.size(); i++){
			Map columnInfo = (Map)aPKColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			String initCapColumnName = initCap(columnName.toLowerCase());				
			primaryKey.append("\t\tkey.set"+initCapColumnName+"(this.get"+initCapColumnName+"());\n");			
		}
		primaryKey.append("\t\treturn key;\n");
		primaryKey.append("\t}\n\n");
		return primaryKey.toString();
	}
	
	public static String getCompareAttributes(String aTableName, List aAllColumnInfo){
		
		StringBuffer compareFields = new StringBuffer();
		compareFields.append("\n\t/**\n");
		compareFields.append("\t*compares the given cargo with this cargo and returns the changed attribute-value Map.\n");
		compareFields.append("\t*/\n");
		compareFields.append("\tpublic Map compareAttributes("+aTableName+"_Cargo aCargo){\n");
		compareFields.append("\t\tMap changedAttributeValue = new HashMap();\n");
		for(int i=0; i<aAllColumnInfo.size(); i++){
			Map columnInfo = (Map)aAllColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			String initCapColumnName = initCap(columnName.toLowerCase());		
			String dataType = (String)columnInfo.get(GeneratorConstants.DATA_TYPE);
			String length = (String)columnInfo.get(GeneratorConstants.LENGTH);
			String scale = (String)columnInfo.get(GeneratorConstants.SCALE);
			String precision = (String)columnInfo.get(GeneratorConstants.PRECISION);
			String javaDataType = GeneraterHelper.getJavaDataType(dataType, length, scale, precision);
			String fieldName = initCapColumnName.substring(0,1).toLowerCase()+initCapColumnName.substring(1);
			if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_STRING) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_DATE) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIME) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_TIMESTAMP)) {
				compareFields.append("\t\tif(!(aCargo.get"+initCapColumnName+"()==null && this."+fieldName+"==null) && !(aCargo.get"+initCapColumnName+"()!=null && aCargo.get"+initCapColumnName+"().equals(this."+fieldName+"))) changedAttributeValue.put(\""+fieldName+"\",aCargo.get"+initCapColumnName+"());\n");	
			} else if(javaDataType.equals(GeneratorConstants.JAVA_TYPE_CHAR) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_DOUBLE) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_INT) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_LONG) || javaDataType.equals(GeneratorConstants.JAVA_TYPE_SHORT)) {
				compareFields.append("\t\tif(aCargo.get"+initCapColumnName+"()==this."+fieldName+") changedAttributeValue.add(\""+fieldName+"\",aCargo.get"+initCapColumnName+"());\n");
			}
		}
		compareFields.append("\t\treturn changedAttributeValue;\n");
		compareFields.append("\t}\n\n");
		return compareFields.toString();
	}
	
	public static String getPackage(){
		
		StringBuffer pkg = new StringBuffer();
		pkg.append("\n\t/**\n");
		pkg.append("\t*returns the PACKAGE name.\n");
		pkg.append("\t*/\n");
		pkg.append("\tpublic String getPackage() {\n");
		pkg.append("\t\treturn PACKAGE;\n");
		pkg.append("\t}\n\n");
		return pkg.toString();		
	}
}