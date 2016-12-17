package com.classroom.utility.ccd;




import java.io.BufferedWriter;
import java.io.FileWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Generates Collection object for given input values
 * @author Anand Balasubramanian
 * Created on Apr 13, 2006
 */

public class CollectionGenerator {

	public static void generate(String aSubSystem, String aTableName, List aAllColumnInfo) throws Exception {
		
		try {
			StringBuffer cargo = new StringBuffer();
			if(aSubSystem.equals("access")){
				GeneraterHelper.updateDataType2String(aAllColumnInfo);				
			}
			// get package imports
			cargo.append(getPackageAndImports(aSubSystem));

			// get class
			cargo.append(getClassDeclaration(aSubSystem, aTableName));

			// get package
			cargo.append(getPackage());

			//  methods
			cargo.append(getMethods(aSubSystem, aTableName));
			
			// clone results
			cargo.append(getCloneResults(aTableName, aAllColumnInfo)); 
		
			// getGenericResults
			cargo.append(getSetGenericResults(aTableName));
		
			// setAbstractCargo
//			if(!aSubSystem.equals("wits")) {
//				cargo.append(getSetAbstractCargo(aTableName));
//			}

			// close class
			cargo.append("}");

			BufferedWriter bw =	new BufferedWriter(new FileWriter("C:/GeneratedCode/ccd/"+aTableName+"_Collection.java"));
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
//		packageAndImport.append("import gov.wisconsin.framework.business.entities.AbstractCargo;\n");
//		packageAndImport.append("import gov.wisconsin.framework.business.entities.AbstractCollection;\n");
		packageAndImport.append("package gov.selfservice.business.entities;\n\n");
		packageAndImport.append("import gov.selfservice.framework.business.entities.AbstractCargo;\n");
		packageAndImport.append("import gov.selfservice.framework.business.entities.AbstractCollection;\n");

		packageAndImport.append("\n");
		return packageAndImport.toString();
	}
	
	public static String getClassDeclaration(String aSubSystem, String aTableName){
	
		StringBuffer classDeclaration = new StringBuffer();
		classDeclaration.append("/**\n");
		classDeclaration.append("* This class acts as a wrapper for one or many cargos of "+aTableName+"\n");
		classDeclaration.append("* @author CodeGenerator - Architecture Team\n");
		classDeclaration.append("* @Creation Date "+new Date()+"\n");
		classDeclaration.append("* Modified By:\n");
		classDeclaration.append("* Modified on:\n");
		classDeclaration.append("* PCR#\n");
		classDeclaration.append("*/\n");
		classDeclaration.append("public class "+aTableName+"_Collection extends AbstractCollection { \n\n");
		if(aSubSystem.equals("wits") || aSubSystem.equals("studio")){
			//classDeclaration.append("\tprivate static final String PACKAGE =\"gov.wisconsin."+aSubSystem+".business.entities."+aTableName+"\";\n\n");
			classDeclaration.append("\tprivate static final String PACKAGE =\"gov.selfservice.business.entities."+aTableName+"\";\n\n");
		} else {
			//classDeclaration.append("\tprivate static final String PACKAGE =\"gov.wisconsin."+aSubSystem+".business.entities.impl."+aTableName+"\";\n\n");
			classDeclaration.append("\tprivate static final String PACKAGE =\"gov.selfservice.business.entities.impl."+aTableName+"\";\n\n");
		}		
		return classDeclaration.toString();
	}
	
	public static String getMethods(String aSubSystem, String aTableName) {
		
		StringBuffer methods = new StringBuffer();
		
		// add cargo method 
		methods.append("\n\t/**\n");
		methods.append("\t*Adds the given cargo to the collection.\n");
		methods.append("\t*/\n");
		methods.append("\tpublic void addCargo("+aTableName+"_Cargo aNewCargo) {\n");
		methods.append("\t\tadd(aNewCargo);\n"); 
		methods.append("\t}\n");

//		if(aSubSystem.equals("cares")|| aSubSystem.equals("access")) {			
//			// get Abstractcargo method 
//			methods.append("\n\t/**\n");
//			methods.append("\t* Returns an abstract cargo.\n");
//			methods.append("\t*/\n");
//			methods.append("\tpublic AbstractCargo getAbstractCargo() {\n");
//			methods.append("\t\treturn (AbstractCargo)get(0);\n");
//			methods.append("\t}\n");
//		}

		// set results method
		methods.append("\n\t/**\n");
		methods.append("\t*Sets cargo array into collection.\n");
		methods.append("\t*/\n");
		methods.append("\tpublic void setResults("+aTableName+"_Cargo[]cbArray) {\n");
		methods.append("\t\tclear();\n");
		methods.append("\t\tfor (int i = 0; i < cbArray.length; i++) {\n");
		methods.append("\t\t\tadd(cbArray[i]);\n");
		methods.append("\t\t}\n");
		methods.append("\t}\n");

		// set cargo method
		methods.append("\n\t/**\n");
		methods.append("\t*Sets cargo into collection at the given index.\n");
		methods.append("\t*/\n");
		methods.append("\tpublic void setCargo(int idx, "+aTableName+"_Cargo aCargo) {\n");
		methods.append("\t\tset(idx, aCargo);\n");
		methods.append("\t}\n");

		// get Results
		methods.append("\n\t/**\n");
		methods.append("\t*returns all the values in the Collection as Cargo Array.\n");
		methods.append("\t*/\n");
		methods.append("\tpublic "+aTableName+"_Cargo[] getResults() {\n");
		methods.append("\t\t"+aTableName+"_Cargo[] cbArray = new "+aTableName+"_Cargo[size()];\n");
		methods.append("\t\ttoArray(cbArray);\n");
		methods.append("\t\treturn cbArray;\n");
		methods.append("\t}\n");
		
		// getRusult method is removed. only of old files if needed
//		if(false && (aSubSystem.equals("cares") || aSubSystem.equals("access") ||aSubSystem.equals("wits"))) {
//			// get Cargo
//			methods.append("\n\t/**\n");
//			methods.append("\t*returns a cargo from the Collection for the given index.\n");
//			methods.append("\t*/\n");
//			methods.append("\tpublic "+aTableName+"_Cargo getResult(int idx) {\n");
//			methods.append("\treturn ("+aTableName+"_Cargo) get(idx);\n");
//			methods.append("\t}\n");
//		} else {
			// get Cargo
			methods.append("\n\t/**\n");
			methods.append("\t*returns a cargo from the Collection for the given index.\n");
			methods.append("\t*/\n");
			methods.append("\tpublic "+aTableName+"_Cargo getCargo(int idx) {\n");
			methods.append("\treturn ("+aTableName+"_Cargo) get(idx);\n");
			methods.append("\t}\n");
//		}
		return methods.toString();
	}

	public static String getCloneResults(String aTableName, List aAllColumnInfo) {
		
		StringBuffer cloneResults = new StringBuffer();

		cloneResults.append("\n\t/** \n");		cloneResults.append("\t* This one for clone Results.\n");		cloneResults.append("\t*/\n");
		cloneResults.append("\tpublic "+aTableName+"_Cargo[] cloneResults(){\n");
		cloneResults.append("\t\t"+aTableName+"_Cargo[] rescargo = new "+aTableName+"_Cargo[size()];\n");
		cloneResults.append("\t\tfor(int i=0; i<size(); i++){\n");
		cloneResults.append("\t\t\t"+aTableName+"_Cargo cargo = getCargo(i);\n");
		cloneResults.append("\t\t\trescargo[i] = new "+aTableName+"_Cargo();\n");
		for(int i=0; i<aAllColumnInfo.size(); i++){
			Map columnInfo = (Map)aAllColumnInfo.get(i);
			String columnName = (String)columnInfo.get(GeneratorConstants.COLUMN_NAME);
			String initCapColumnName = initCap(columnName.toLowerCase());
			cloneResults.append("\t\t\trescargo[i].set"+initCapColumnName+"(cargo.get"+initCapColumnName+"());\n");	
		}
		cloneResults.append("\t\t\trescargo[i].setRowAction(cargo.getRowAction());\n");
		cloneResults.append("\t\t\trescargo[i].setUser(cargo.getUser());\n");
		cloneResults.append("\t\t\trescargo[i].setDirty(cargo.isDirty());\n");
		cloneResults.append("\t\t}\n");
		cloneResults.append("\t\treturn rescargo;\n");
		cloneResults.append("\t}\n");
		
		return cloneResults.toString();
	}
	
	public static String getSetGenericResults(String aTableName) {
		
		StringBuffer genericResults = new StringBuffer();

		genericResults.append("\n\t/** \n");
		genericResults.append("\t* Set the cargo array object to the collection.\n");
		genericResults.append("\t*/\n");
		genericResults.append("\tpublic void setGenericResults(Object obj) {\n");
		genericResults.append("\t\tif (obj instanceof "+aTableName+"_Cargo[]){\n");
		genericResults.append("\t\t\t"+aTableName+"_Cargo[] cbArray = ("+aTableName+"_Cargo[]) obj;\n");
		genericResults.append("\t\t\tsetResults(cbArray);\n");
		genericResults.append("\t\t}\n");
		genericResults.append("\t}\n");
		
		return genericResults.toString();
	}
	
//	public static String getSetAbstractCargo(String aTableName) {
//		
//		StringBuffer setAbstractCargo = new StringBuffer();
//
//		setAbstractCargo.append("\n\t/** \n");
//		setAbstractCargo.append("\t* Sets the cargo object to the collection.\n");
//		setAbstractCargo.append("\t*/\n");
//		setAbstractCargo.append("\tpublic void setAbstractCargo(AbstractCargo aCargo) {\n");
//		setAbstractCargo.append("\t\tif(size() == 0) {\n");
//		setAbstractCargo.append("\t\t\tadd(aCargo);\n");
//		setAbstractCargo.append("\t\t} else {\n");
//  		setAbstractCargo.append("\t\t\tset(0, aCargo);\n");
//		setAbstractCargo.append("\t\t}\n");
//		setAbstractCargo.append("\t}\n");
//	
//		return setAbstractCargo.toString();	
//	}
	
	private static String initCap(String aText){
		
		if(aText!=null && aText.length()>0){
			String firstLetter = String.valueOf(aText.charAt(0)).toUpperCase();
			return firstLetter+aText.substring(1);
		} else {
			return aText;
		}
	}

	public static String getPackage(){
		
		StringBuffer pkg = new StringBuffer();
		pkg.append("\n\t/**\n");
		pkg.append("\t*returns the PACKAGE name.\n");
		pkg.append("\t*/\n");		
		pkg.append("\tpublic String getPACKAGE() {\n");
		pkg.append("\t\treturn PACKAGE;\n");
		pkg.append("\t}\n\n");
		return pkg.toString();		
	}
}