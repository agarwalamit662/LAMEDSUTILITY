package com.classroom.utility.ccd;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
//import oracle.sql.NUMBER;

/**
 * This class used to get the matching data types between DB2 and Java
 * @author Anand Balasubramanian
 * Created on Apr 10, 2006
 */

public class GeneraterHelper {
	
	private static Map dataTypeMapping = new HashMap();

	static {
		dataTypeMapping.put(GeneratorConstants.DB2_TYPE_VARCHAR, GeneratorConstants.JAVA_TYPE_STRING);
		dataTypeMapping.put(GeneratorConstants.DB2_TYPE_VARCHAR2, GeneratorConstants.JAVA_TYPE_STRING);
		dataTypeMapping.put(GeneratorConstants.DB2_TYPE_NUMBER, GeneratorConstants.JAVA_TYPE_INT);
		dataTypeMapping.put(GeneratorConstants.DB2_TYPE_SMALLINT, GeneratorConstants.JAVA_TYPE_SHORT);
		dataTypeMapping.put(GeneratorConstants.DB2_TYPE_INTEGER, GeneratorConstants.JAVA_TYPE_INT);
		dataTypeMapping.put(GeneratorConstants.DB2_TYPE_DATE, GeneratorConstants.JAVA_TYPE_DATE);
		dataTypeMapping.put(GeneratorConstants.DB2_TYPE_TIME, GeneratorConstants.JAVA_TYPE_TIME);
		dataTypeMapping.put(GeneratorConstants.DB2_TYPE_TIMESTAMP, GeneratorConstants.JAVA_TYPE_TIMESTAMP);
		dataTypeMapping.put(GeneratorConstants.DB2_TYPE_TIMESTAMP2, GeneratorConstants.JAVA_TYPE_TIMESTAMP);
		//dataTypeMapping.put("NUMBER", GeneratorConstants.JAVA_TYPE_DOUBLE);
		//dataTypeMapping.put(GeneratorConstants.DB2_TYPE_NUMBER,GeneratorConstants.DB2_TYPE_NUMBER);
	}

	public static String getJavaDataType(String aDB2DataType, String aLength, String aScale, String aPrecision) {
		
		String javaDataType = (String)dataTypeMapping.get(aDB2DataType);
		int length = Integer.parseInt(aLength);
		if(javaDataType==null){
			if(aDB2DataType.equals(GeneratorConstants.DB2_TYPE_INTEGER)) {
				javaDataType = GeneratorConstants.JAVA_TYPE_INT;
			} else if(aDB2DataType.equals(GeneratorConstants.DB2_TYPE_SMALLINT)) {
					javaDataType = GeneratorConstants.JAVA_TYPE_SHORT;				
			} else if(aDB2DataType.equals(GeneratorConstants.DB2_TYPE_CHAR)) {
				if(aLength.equals("1")) {
					javaDataType = GeneratorConstants.JAVA_TYPE_CHAR;		
				} else {
					javaDataType = GeneratorConstants.JAVA_TYPE_STRING;
				}
			} else if(aDB2DataType.equals(GeneratorConstants.DB2_TYPE_BIGINT)) {
					javaDataType = GeneratorConstants.JAVA_TYPE_LONG;		
			} else if(aDB2DataType.equals(GeneratorConstants.DB2_TYPE_DECIMAL)) {
				if(aScale.equals("0")) {
					javaDataType = GeneratorConstants.JAVA_TYPE_LONG;		
				} else {
					javaDataType = GeneratorConstants.JAVA_TYPE_DOUBLE;
				}
			} else if(aDB2DataType.equals(GeneratorConstants.DB2_TYPE_NUMBER)){
				if(!aScale.equals("0"))
					javaDataType = GeneratorConstants.JAVA_TYPE_DOUBLE;
				else if(length<4)
					javaDataType = GeneratorConstants.JAVA_TYPE_SHORT;
				else if(length<10)
					javaDataType = GeneratorConstants.JAVA_TYPE_INT;
				else if(length<19)
					javaDataType = GeneratorConstants.JAVA_TYPE_LONG;
				else
					javaDataType = "int";
					
			}
			else if(aDB2DataType.equals(GeneratorConstants.SQL_TYPE_INT))
			{
				javaDataType = "int";
			}
			else if(aDB2DataType.equals(GeneratorConstants.DB2_TYPE_DATE)) {
				javaDataType = GeneratorConstants.JAVA_TYPE_DATE;
			} else if(aDB2DataType.equals(GeneratorConstants.DB2_TYPE_TIMESTAMP)) {
				javaDataType = GeneratorConstants.JAVA_TYPE_TIMESTAMP;
			} else if(aDB2DataType.equals(GeneratorConstants.DB2_TYPE_TIME)){
				javaDataType = GeneratorConstants.JAVA_TYPE_TIME;
			}
			else if(aDB2DataType.equals(GeneratorConstants.DB2_TYPE_DATETIME2)){
				javaDataType = GeneratorConstants.JAVA_TYPE_TIMESTAMP;
			}
			/*VARCHAR is the same as VARCHAR2 except that VARCHAR2 will be of varible length,
			 * therefore we may explicitly check for VARCHAR2
			*/
			else if(aDB2DataType.equals(GeneratorConstants.DB2_TYPE_VARCHAR) || 
					aDB2DataType.equals(GeneratorConstants.DB2_TYPE_VARCHAR2)){
				if(aLength.equals("1")) {
					javaDataType = GeneratorConstants.JAVA_TYPE_CHAR;		
				} else {
					javaDataType = GeneratorConstants.JAVA_TYPE_STRING;
				}
			}
		}
		return javaDataType;
	}
	
	public static void updateDataType2String(List aColumnInfo){
		for(int i=0; i<aColumnInfo.size(); i++){
			((Map) aColumnInfo.get(i)).put(GeneratorConstants.DATA_TYPE, GeneratorConstants.DB2_TYPE_VARCHAR);
		}
	}
}