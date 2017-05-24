package com.classroom.utility.controller;

import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.zeroturnaround.zip.ZipUtil;

import com.classroom.utility.ccd.CCDGenerator;

@Controller
public class MainController {

	protected static Map<String, Integer> mapRef;
	public static final String CATALINA_PATH = "catalina.home";
	public static final String TMP_FILES_WKP_OUTPUT = "tmpFilesWKPOutput";
	public static final String TMP_FILES_WKP_OUTPUT_ZIP = "Output.Zip";
	public static final String JDBC_DRVR = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	public static final String CCDS_PATH = "C:/GeneratedCode";
	public static final String SSP_USERNAME = "ssp_ee_dev";
	private static Log log = LogFactory.getLog(MainController.class);
	
    @RequestMapping("/")
    public String homepage(){
        return "homepage";
    }
    
    @RequestMapping("/test")
    public String testpage(){
        return "Test";
    }
    
    
    @RequestMapping(value="/fileUploadCheck", method = RequestMethod.POST)
    public @ResponseBody SSPUtilityResponse workerPortal(@RequestParam(value="file", required=true) MultipartFile file,HttpServletRequest req) throws IOException {
    	
    	String rootPath = System.getProperty(CATALINA_PATH);
    	File dirsZips = new File(rootPath + File.separator + TMP_FILES_WKP_OUTPUT+ File.separator + TMP_FILES_WKP_OUTPUT_ZIP);
		if (dirsZips.exists()){
			dirsZips.delete();
			
		}
    	SSPUtilityResponse response = new SSPUtilityResponse();
    	
    	StringBuilder exceptions = new StringBuilder();
    	
    	String fileName=file.getOriginalFilename();
        OPCPackage opcPackage = null;
        if (!file.isEmpty()) {
    		try {
    			byte[] bytes = file.getBytes();
    			
    			File dir = new File(rootPath + File.separator + "tmpFilesWkp");
    			if (!dir.exists())
    				dir.mkdirs();

    			File serverFile = new File(dir.getAbsolutePath()
    					+ File.separator + fileName);
    			BufferedOutputStream stream = new BufferedOutputStream(
    					new FileOutputStream(serverFile));
    			stream.write(bytes);
    			stream.flush();
    			stream.close();

    			mapRef = new HashMap<>();
    			Connection conn = null;
    	        Statement stmt = null;
    	        File files = null;
    	        try {

    	            String dbURL = "jdbc:sqlserver://usmumpurchase1:44033";
    	            String user = "iewp_ee_dev";
    	            String pass = "iewp_ee_dev";
    	            String driver = JDBC_DRVR;
    	        	Class.forName(driver).newInstance();
    	            conn = DriverManager.getConnection(dbURL, user, pass);
    	            stmt = conn.createStatement();
    	            String sql = "select NAME,FIELD_ID from RT_field_domain";
    	            ResultSet rs = stmt.executeQuery(sql);
    	            while(rs.next()){
    	                  
    	            	mapRef.put(rs.getString(1), rs.getInt(2));
    	            	
    	            }
    	            
    	            try
    	    		{
    	    			
    	    			files = new File(serverFile.getAbsolutePath());
    	    			FileInputStream inputStream = new FileInputStream(files);
    	    			opcPackage = OPCPackage.open(inputStream);
    	    			XSSFWorkbook workbook = new XSSFWorkbook(opcPackage);
    	    			XSSFSheet sheet = workbook.getSheetAt(0);
    	    			Iterator<Row> rowIterator = sheet.iterator();
    	    			DataFormatter formatter = new DataFormatter(); //creating formatter using the default locale
    	    			
    	    			while (rowIterator.hasNext())
    	    			{
    	    				boolean toWrite = true;
    	    				String finalStr="";
    	    				Row row = rowIterator.next();
    	    				String rpTableName = "";
    	    				String desRpTable = "";
    	    				StringBuilder sqlRtTableFieldBuffer = new StringBuilder();
    	    				StringBuilder sqlRtTableFieldValuesBuffer = new StringBuilder();
    	    				//RT_FIELD_VALUES
    	    				//For each row, iterate through all the columns
    	    				if(row.getRowNum() < 6){
    	    					continue;
    	    				}
    	    				Iterator<Cell> cellIterator = row.cellIterator();
    	    				List<String> strList = null;
    	    				while (cellIterator.hasNext())
    	    				{
    	    					Cell cell = cellIterator.next();
    	    					if(cell.getColumnIndex() == 2){
    	    						desRpTable = formatter.formatCellValue(cell);
    	    					}
    	    					
    	    					if(cell.getColumnIndex()==0)
    	    					{
    	    						rpTableName = formatter.formatCellValue(cell);
    	    						
    	    						String tempString = "DELETE FROM RT_FIELD_VALUES WHERE REF_TABLE_FIELD_ID "
    	    								+ "IN (SELECT REF_TABLE_FIELD_ID FROM RT_TABLE_FIELD WHERE "
    	    								+ "REF_TABLE_ID IN "
    	    								+ "(SELECT REF_TABLE_ID FROM RT_TABLE WHERE "
    	    								+ "NAME = '"+formatter.formatCellValue(cell)+"' ))\n"
    	    								+ "DELETE FROM RT_TABLE_FIELD WHERE REF_TABLE_ID "
    	    								
    	    								+ "IN (SELECT REF_TABLE_ID FROM RT_TABLE WHERE NAME = '"+formatter.formatCellValue(cell)+"' )\n"
    	    								
    	    								+ "DELETE FROM RT_TABLE WHERE NAME = '"+formatter.formatCellValue(cell)+"'\n"
    	    										
    	    								+ "DECLARE @ref_table_id bigint \n \n";
    	    						
    	    						XSSFSheet sheetInner = workbook.getSheet(formatter.formatCellValue(cell));
    	    						StringBuilder rowIterBuffer = new StringBuilder();
    	    						StringBuilder rowIterBufferInner = new StringBuilder();
    	    						StringBuilder bfColumns = new StringBuilder();
    	    						StringBuilder bfColumnsRe = new StringBuilder();
    	    						if(sheetInner == null){
    	    							toWrite = false;
    	    							break;
    	    						}
    	    						
    	    						Iterator<Row> rowIteratorInner = sheetInner.iterator();
    	    						int i = 0;
    	    						int matcher = 0;
    	    						int noOfColumns = 0;
    	    						while (rowIteratorInner.hasNext())
    	    						{
    	    							
    	    							Row rowInner = rowIteratorInner.next();
    	    							if(rowInner.getRowNum() == 10){
    	    								matcher = 10;
    	    							}
    	    							if(rowInner.getRowNum() <= 9){
    	    								continue;
    	    							}
    	    							if(matcher == rowInner.getRowNum()){
    	    								matcher++;
    	    							} 
    	    							else if(matcher != rowInner.getRowNum()){
    	    								break;
    	    							}
    	    							i++;
    	    							Iterator<Cell> cellIteratorInner = rowInner.cellIterator();
    	    							
    	    							int j = 0;
    	    							if(i==1){
    	    							while (cellIteratorInner.hasNext())
    	    							{
    	    								
    	    								Cell cellInner = cellIteratorInner.next();
    	    								String cValue = formatter.formatCellValue(cellInner);
    	    								if(cValue != null && cValue.length() > 0){
    	    									noOfColumns++;
    	    									j++;
    	    									bfColumns.append(",@ref_table_field_id_"+String.valueOf(j)+" bigint\n");
    	    									bfColumnsRe.append("set @ref_table_field_id_"+String.valueOf(j)+" = NEXT VALUE FOR RT_TABLE_FIELD_1SQ\n");
    	    									sqlRtTableFieldBuffer.append("INSERT INTO [dbo].[RT_TABLE_FIELD]([REF_TABLE_FIELD_ID],[REF_TABLE_ID],[FIELD_ID],[CREATE_USER_ID],[UPDATE_USER_ID],[CREATE_DT],[UPDATE_DT],[UNIQUE_TRANS_ID],[ARCHIVE_DT]) VALUES (@ref_table_field_id_"+String.valueOf(j)+",@ref_table_id  ,"+String.valueOf(mapRef.get(cValue.toUpperCase()))+",'Amit.Agarwal','Amit.Agarwal',GETDATE(),GETDATE(),NEXT VALUE FOR RT_TABLE_FIELD_0SQ,'2999-12-31 00:00:00')\n");
    	    									if(!mapRef.containsKey(cValue.toUpperCase())){
    	    										System.out.println("The Ref Table: "+rpTableName+" Column : "+cValue+" is not in the RT Table Field ");
    	    									}
    	    								}
    	    							}
    	    							}
    	    							boolean toNotInsertRow = false;
    	    							if(i != 1 ){
    	    								
    	    								
    	    								j = 0;
    	    								while (cellIteratorInner.hasNext())
    	    								{
    	    									
    	    									Cell cellInner = cellIteratorInner.next();
    	    									String cValue = formatter.formatCellValue(cellInner);
    	    									
    	    									if(cellInner.getColumnIndex() == 0 && (cValue == null || cValue.length() == 0)){
    	    										toNotInsertRow = true;
    	    										break;
    	    									}
    	    									if((cValue != null && cValue.length() > 0) || j < noOfColumns){
    	    										j++;
    	    										cValue = cValue.replaceAll("'", "''");
    	    										sqlRtTableFieldValuesBuffer.append("INSERT INTO [dbo].[RT_FIELD_VALUES]([REF_TABLE_FIELD_ID],[FIELD_ROW_ID],[FIELD_VALUE],[CREATE_USER_ID],[UPDATE_USER_ID],[CREATE_DT],[UPDATE_DT],[UNIQUE_TRANS_ID],[ARCHIVE_DT]) VALUES (@ref_table_field_id_"+String.valueOf(j)+" ,@ref_field_row_id_"+String.valueOf(i-1)+" ,'"+cValue+"','Amit.Agarwal','Amit.Agarwal',GETDATE(),GETDATE(),NEXT VALUE FOR RT_FIELD_VALUES_0SQ,'2999-12-31 00:00:00')\n");
    	    									}
    	    								}
    	    								if(!toNotInsertRow){
    	    									rowIterBuffer.append(",@ref_field_row_id_"+String.valueOf(i-1)+" bigint\n");
    	    									rowIterBufferInner.append("set @ref_field_row_id_"+String.valueOf(i-1)+"  = NEXT VALUE FOR RT_FIELD_VALUES_1SQ\n");
    	    								}
    	    							}
    	    							if(toNotInsertRow)
    	    								break;
    	    						}
    	    						
    	    						String colBufferStr = bfColumns.toString();
    	    						String rowBufferStr = rowIterBuffer.toString();
    	    						
    	    						String st1 = "set @ref_table_id = NEXT VALUE FOR RT_TABLE_1SQ\n";
    	    						
    	    						String colBufferStrInner = bfColumnsRe.toString();
    	    						String rowBufferStrInner = rowIterBufferInner.toString();
    	    						
    	    						finalStr = tempString+"\n"+colBufferStr+"\n"+rowBufferStr+"\n"+st1+"\n"
    	    								+ colBufferStrInner+"\n"+rowBufferStrInner;
    	    						
    	    					}
    	    					
    	    				}
    	    				
    	    				String rtTableString = "INSERT INTO [dbo].[RT_TABLE]([REF_TABLE_ID],[TABLE_ID],[VERSION],[NAME],[DESCRIPTION],[COMMENTS],[STATUS],[CREATE_USER_ID],[UPDATE_USER_ID],[CREATE_DT],[UPDATE_DT],[UNIQUE_TRANS_ID],[EFF_BEGIN_DT],[EFF_END_DT],[ARCHIVE_DT]) VALUES "
    	    						+ "(@ref_table_id ,NEXT VALUE FOR RT_TABLE_3SQ,1,"
    	    						+ "'"
    	    						+ rpTableName
    	    						+ "'"
    	    						+ ",'"
    	    						+ desRpTable.replaceAll("'", "''")
    	    						+ "','"
    	    						+ desRpTable.replaceAll("'", "''")
    	    						+ "','A','Amit.Agarwal','Amit.Agarwal',GETDATE(),GETDATE(),NEXT VALUE FOR RT_TABLE_0SQ,GETDATE(),NULL,'2999-12-31 00:00:00')";
    	    				
    	    				String transStart = "BEGIN TRANSACTION\n\nBEGIN TRY\n\n";
    	    				String transEnd = "END TRY\n\nBEGIN CATCH\n\nselect ERROR_MESSAGE() AS ERRORMESSAGE, ERROR_LINE() AS ErrorLine\nIF @@TRANCOUNT > 0  ROLLBACK TRANSACTION\n\nEND CATCH\n\nIF @@TRANCOUNT >0  COMMIT TRANSACTION\n";
    	    				
    	    				finalStr = transStart+finalStr+"\n"+rtTableString+"\n\n"+sqlRtTableFieldBuffer+"\n\n"+sqlRtTableFieldValuesBuffer+"\n\n"+transEnd;
    	    				
    	    				if(toWrite){
    	    					
    	    					File dirs = new File(rootPath + File.separator + TMP_FILES_WKP_OUTPUT+ File.separator + "Output");
    	    					if (!dirs.exists())
    	    						dirs.mkdirs();

    	    					StringBuilder fNameBuff = new StringBuilder();
    	    		            fNameBuff.append(rpTableName+".sql");
    	    		            String fName = fNameBuff.toString();
    	    		            
    	    		            File serverFiles = new File(dirs.getAbsolutePath()
    	    							+ File.separator + fName);
    	    					
    	    					
    	    		            DataOutputStream out = new DataOutputStream(new FileOutputStream(serverFiles));
    	    		            out.writeBytes(finalStr);
    	    					out.close();
    	    		            	
    	    					
    	    				}
    	    			
    	    		}
    	    		
    	    		ZipUtil.pack(new File(rootPath + File.separator + TMP_FILES_WKP_OUTPUT+ File.separator + "Output"), new File(rootPath + File.separator + TMP_FILES_WKP_OUTPUT+ File.separator + TMP_FILES_WKP_OUTPUT_ZIP));
    	    		response.setFilePath(rootPath + File.separator + TMP_FILES_WKP_OUTPUT+ File.separator + TMP_FILES_WKP_OUTPUT_ZIP);
    	            response.setExceptions("");
    	            response.setUtilSuccessfull("Y");
    	            
    	            File dirs = new File(rootPath + File.separator + TMP_FILES_WKP_OUTPUT+ File.separator + "Output");
					if (dirs.exists())
						FileUtils.forceDelete(dirs);
					
					stmt.close();
					conn.close();
					rs.close();
					
    	    		}
					
    	    		catch (Exception e) {
    	    			log.error(e);
    	    			deleteFileWorkerPortal(rootPath);
    	    			exceptions.append(e.toString());
    	    			response = setResponse(exceptions);
    	    		}
    	    		
    	        } catch (SQLException ex) {
    	            log.error(ex);
    	            deleteFileWorkerPortal(rootPath);
    	            exceptions.append(ex.toString());
    	            response = setResponse(exceptions);
    	        } finally {
    	            try {
    	            	if(opcPackage!=null){
    	            		opcPackage.flush();
    	            		opcPackage.close();
    	            	}
    	            	if(files != null){
    	            		files.delete();
    	            	}
    	                if (conn != null && !conn.isClosed()) {
    	                    conn.close();
    	                }
    	                
    	            } catch (SQLException ex) {
    	            	
    	                log.error(ex);
    	                deleteFileWorkerPortal(rootPath);
    	                exceptions.append(ex.toString());
    	                response = setResponse(exceptions);
    	                
    	            }
    	            finally{
    	            	stmt.close();
    	            	if (conn != null && !conn.isClosed()) {
    	                    conn.close();
    	                }
    	            }
    	        }
    			
    		} catch (Exception e) {
    			log.error(e);
    			deleteFileWorkerPortal(rootPath);
    			exceptions.append(e.toString());
    			response = setResponse(exceptions);
    			
    		}
    	} else {
    		deleteFileWorkerPortal(rootPath);
    		exceptions.append("File Is Empty");
    		response = setResponse(exceptions);
    		
    	}
		return response;
        
        
        
    }
    
    @RequestMapping(value = "/ssputility", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SSPUtilityResponse sspUtility(@RequestBody SSPUtility input){
            	
    	SSPUtilityResponse response = new SSPUtilityResponse();
    	StringBuilder exceptions = new StringBuilder();
    	
    	
    	Connection conn = null;
        Statement stmt = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {

            // Change this DB Url When Needed URL AND PORT ONLY
        	//String dbURL = "jdbc:sqlserver://10.12.88.113:44033";
        	//String dbURL = "jdbc:sqlserver://usmumpurchase3:9080";
        	
        	String dbURL = "jdbc:sqlserver://localhost:53344";
        	// DBNAME
        	String user =SSP_USERNAME;
            //DBPASSWORD
        	String pass = SSP_USERNAME;
        	String driver = JDBC_DRVR;
        	Class.forName(driver).newInstance();
            String jdbcutf8 = "&useUnicode=true&characterEncoding=UTF-8";
            java.util.Properties info = new java.util.Properties();

            if (user != null) {
                info.put("user", user);
            }
            if (pass != null) {
                info.put("password", pass);
            }
            info.put("useUnicode", "true");
            info.put("characterEncoding", "UTF-8");
            
            conn = DriverManager.getConnection(dbURL, info);
            String refIds = input.getRefTables();
            String pageId = input.getPageIds(); 
            String msgId = "";
            String msgYorN = input.getErrorMessages();
            
            if(msgYorN != null && !msgYorN.equalsIgnoreCase("N")){
            	StringBuilder bf = new StringBuilder();
            	bf.append("(");
            	String[] msgIds = msgYorN.split(",");
            	for(int k=0; k<msgIds.length;k++){
            		if(k == msgIds.length-1)
            			bf.append("'"+msgIds[k]+"')");
            		else{	
            			bf.append("'"+msgIds[k]+"',");
            		}
            	}
            	msgId = bf.toString();
            }
            
            String dsplyId = "";
            String dsplYorN = input.getDisplayTexts();
            
            if(dsplYorN != null && !dsplYorN.equalsIgnoreCase("N")){
            	StringBuilder bf = new StringBuilder();
            	bf.append("(");
            	String[] dIds = dsplYorN.split(",");
            	for(int k=0; k<dIds.length;k++){
            		if(k == dIds.length-1)
            			bf.append("'"+dIds[k]+"')");
            		else{	
            			bf.append("'"+dIds[k]+"',");
            		}
            	}
            	dsplyId = bf.toString();
            }
            
            if(refIds != null && !refIds.equalsIgnoreCase("N")){
            	StringBuilder bf = new StringBuilder();
            	bf.append("(");
            	String[] dIds = refIds.split(",");
            	for(int k=0; k<dIds.length;k++){
            		if(k == dIds.length-1)
            			bf.append("'"+dIds[k]+"')");
            		else{	
            			bf.append("'"+dIds[k]+"',");
            		}
            	}
            	refIds = bf.toString();
            }
            else{
            	refIds = "";
            }
            
            stmt = conn.createStatement();
            
            String extraDisPlayId = "('','')";
            String sql1 = "select * from PAGE_ELE where PAGE_ID = '"+pageId+"'";
            String sql2 = "select * from DPLY_TXT where TXT_ID in (select txt_id from page_ele where page_id='"+pageId+"')";
            String sql3 = "select * from ELE_DPLY where ELE_ID in (select ELE_ID from page_ele where page_id='"+pageId+"')";
            String sql4 = "select * from PAGE where PAGE_ID = '"+pageId+"'";
            String sql5 = "select * from MSG where MSG_ID in "+msgId;
            String sql6 = "select * from DPLY_TXT where TXT_ID in "+dsplyId;
            
            String sql7 = "select * from LKUP_GRP_FLD where lkup_grp_cd IN "+refIds;
            String sql8 = "select * from LKUP_GRP where LKUP_GRP_CD IN "+refIds;
            String sql9 = "select * from LKUP_FLD where LKUP_FLD_ID in (select LKUP_FLD_ID from LKUP_GRP_FLD where lkup_grp_cd IN "+refIds+")";
            String sql10 = "select * from LKUP where LKUP_GRP_FLD_ID in ( select LKUP_GRP_FLD_ID from LKUP_GRP_FLD where lkup_grp_cd IN "+refIds+")";

            StringBuilder sb1 = new StringBuilder();
            if(pageId != null && !pageId.equalsIgnoreCase("N")){
                   
            sb1.append("delete from PAGE_ELE where PAGE_ID = '"+pageId+"';");
            sb1.append("\n\r");
            rs = stmt.executeQuery(sql1);
            while(rs.next()){
            	
            	StringBuilder innerLoop = new StringBuilder();
            	String tempString = "INSERT INTO PAGE_ELE VALUES("; 
            	innerLoop.append(tempString);
            	innerLoop.append("'"+rs.getString("PAGE_ID").replaceAll("'", "''")+"'");
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("ELE_ID")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("TXT_ID")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("RULE_ID")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("MAX_LTH")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("FS_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("MA_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("TF_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("CC_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("WC_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("CS_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("AG_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("MH_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("TE_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("EA_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("SA_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("AR_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("SC_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("FP_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("BG_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("DC_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("AM_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("FT_IND")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("KC_IND")));
            	innerLoop.append(",");
            	innerLoop.append("'"+rs.getString("APP_TYP").replaceAll("'", "''")+"'");
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("CSS_IND")));
            	innerLoop.append(");");
            	sb1.append(innerLoop.toString()+"\n");
            }
            
            sb1.append("\n\r");
            sb1.append("delete from DPLY_TXT where TXT_ID in (select txt_id from page_ele where page_id='"+pageId+"');");
            sb1.append("\n\r");
            
            rs = stmt.executeQuery(sql2);
            while(rs.next()){
            	
            	
                StringBuilder innerLoop = new StringBuilder();
            	String tempString = "INSERT INTO DPLY_TXT VALUES("; 
            	innerLoop.append(tempString);
            	innerLoop.append(String.valueOf(rs.getInt("TXT_ID")));
            	innerLoop.append(",");
            	innerLoop.append("'"+rs.getString("LANG_CD").replaceAll("'", "''")+"'");
            	innerLoop.append(",");
            	innerLoop.append("'"+rs.getString("CMT_TXT").replaceAll("'", "''")+"'");
            	innerLoop.append(",");
            	innerLoop.append("N'"+rs.getString("TXT_DSC").replaceAll("'", "''")+"'");
            	innerLoop.append(");");
            	sb1.append(innerLoop.toString()+"\n");
            }
            
            
            sb1.append("\n\r");
            sb1.append("delete from ELE_DPLY where ELE_ID in (select ELE_ID from page_ele where page_id='"+pageId+"');");
            sb1.append("\n\r");
            
            rs = stmt.executeQuery(sql3);
            while(rs.next()){
            	
            	
            	
                StringBuilder innerLoop = new StringBuilder();
            	String tempString = "INSERT INTO ELE_DPLY VALUES("; 
            	innerLoop.append(tempString);
            	innerLoop.append(String.valueOf(rs.getInt("ELE_ID")));
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("ELE_SIZE")));
            	innerLoop.append(",");
            	innerLoop.append("'"+rs.getString("HTML_NAM").replaceAll("'", "''")+"'");
            	innerLoop.append(",");
            	innerLoop.append("'"+rs.getString("ELE_DSC").replaceAll("'", "''")+"'");
            	innerLoop.append(");");
            	sb1.append(innerLoop.toString()+"\n");
            }
            
            sb1.append("\n\r");
            sb1.append("delete from PAGE where PAGE_ID = '"+pageId+"';");
            sb1.append("\n\r");
            
            rs = stmt.executeQuery(sql4);
            while(rs.next()){
            	
                StringBuilder innerLoop = new StringBuilder();
            	String tempString = "INSERT INTO PAGE VALUES("; 
            	innerLoop.append(tempString);
            	innerLoop.append("'"+rs.getString("PAGE_ID").replaceAll("'", "''")+"'");
            	innerLoop.append(",");
            	innerLoop.append(String.valueOf(rs.getInt("PROG_PCT")));
            	innerLoop.append(",");
            	innerLoop.append("'"+rs.getString("HELP_PAGE_URL_ADR").replaceAll("'", "''")+"'");
            	innerLoop.append(",");
            	innerLoop.append("'"+rs.getString("PAGE_DSC").replaceAll("'", "''")+"'");
            	innerLoop.append(",");
            	innerLoop.append("'"+rs.getString("PAGE_URL_ADR").replaceAll("'", "''")+"'");
            	innerLoop.append(",");
            	innerLoop.append("'"+rs.getString("AUTH_REQ_SW").replaceAll("'", "''")+"'");
            	innerLoop.append(",");
            	innerLoop.append("'"+rs.getString("PGM_RULE_REQ_SW").replaceAll("'", "''")+"'");
            	innerLoop.append(");");
            	sb1.append(innerLoop.toString()+"\n");
            }
            }
            
            if(!msgId.equalsIgnoreCase("")){
            
            	sb1.append("\n\r");
            	sb1.append("delete from MSG where MSG_ID in "+msgId+";");
            	sb1.append("\n\r");
            
            
            	rs = stmt.executeQuery(sql5);
            	while(rs.next()){
            	
            	
            		StringBuilder innerLoop = new StringBuilder();
            		String tempString = "INSERT INTO MSG VALUES("; 
            		innerLoop.append(tempString);
            		innerLoop.append("'"+rs.getString("MSG_ID").replaceAll("'", "''")+"'");
            		innerLoop.append(",");
            		innerLoop.append("'"+rs.getString("LANG_CD").replaceAll("'", "''")+"'");
            		innerLoop.append(",");
            		innerLoop.append(String.valueOf(rs.getInt("SVR_CD")));
            		innerLoop.append(",");
            		innerLoop.append("N'"+rs.getString("MSG_DSC").replaceAll("'", "''")+"'");
	            	innerLoop.append(");");
	            	sb1.append(innerLoop.toString()+"\n");
            	}
            
            	sb1.append("\n\r");
            
            }
            
            if(!dsplyId.equalsIgnoreCase("")){
            
            sb1.append("\n\r");
            sb1.append("delete from DPLY_TXT where TXT_ID in "+dsplyId+" ;");
            sb1.append("\n\r");
            
            rs = stmt.executeQuery(sql6);
            while(rs.next()){
            	
            	StringBuilder innerLoop = new StringBuilder();
            	String tempString = "INSERT INTO DPLY_TXT VALUES("; 
            	innerLoop.append(tempString);
            	innerLoop.append(String.valueOf(rs.getInt("TXT_ID")));
            	innerLoop.append(",");
            	innerLoop.append("'"+rs.getString("LANG_CD").replaceAll("'", "''")+"'");
            	innerLoop.append(",");
            	innerLoop.append("'"+rs.getString("CMT_TXT").replaceAll("'", "''")+"'");
            	innerLoop.append(",");
            	innerLoop.append("N'"+rs.getString("TXT_DSC").replaceAll("'", "''")+"'");
            	innerLoop.append(");");
            	sb1.append(innerLoop.toString()+"\n");
            }
            
            }
            
            
            if(!refIds.equalsIgnoreCase("")){
                
                sb1.append("\n\r");
                sb1.append("delete from LKUP_GRP_FLD where lkup_grp_cd IN "+refIds+" ;");
                sb1.append("\n\r");
                
                rs = stmt.executeQuery(sql7);
                while(rs.next()){
                	StringBuilder innerLoop = new StringBuilder();
                	String tempString = "INSERT INTO LKUP_GRP_FLD VALUES("; 
                	innerLoop.append(tempString);
                	innerLoop.append(String.valueOf(rs.getInt("LKUP_GRP_FLD_ID")));
                	innerLoop.append(",");
                	innerLoop.append("'"+rs.getString("LANG_CD").replaceAll("'", "''")+"'");
                	innerLoop.append(",");
                	innerLoop.append("'"+rs.getString("LKUP_GRP_CD").replaceAll("'", "''")+"'");
                	innerLoop.append(",");
                	innerLoop.append(String.valueOf(rs.getInt("LKUP_FLD_ID")));
                	innerLoop.append(");");
                	sb1.append(innerLoop.toString()+"\n");
                }
                
                
                sb1.append("\n\r");
                sb1.append("delete from LKUP_GRP where LKUP_GRP_CD IN "+refIds+" ;");
                sb1.append("\n\r");
                
                rs = stmt.executeQuery(sql8);
                while(rs.next()){
  
                	StringBuilder innerLoop = new StringBuilder();
                	String tempString = "INSERT INTO LKUP_GRP VALUES("; 
                	innerLoop.append(tempString);
                	
                	innerLoop.append("'"+rs.getString("LKUP_GRP_CD").replaceAll("'", "''")+"'");
                	innerLoop.append(",");
                	innerLoop.append("'"+rs.getString("LKUP_GRP_DSC").replaceAll("'", "''")+"'");
                	innerLoop.append(");");
                	sb1.append(innerLoop.toString()+"\n");
                }
                
                
                sb1.append("\n\r");
                sb1.append("delete from LKUP_FLD where LKUP_FLD_ID in (select LKUP_FLD_ID from LKUP_GRP_FLD where lkup_grp_cd IN "+refIds+") ;");
                sb1.append("\n\r");
                
                rs = stmt.executeQuery(sql9);
                while(rs.next()){
                	
                	
                	StringBuilder innerLoop = new StringBuilder();
                	String tempString = "INSERT INTO LKUP_FLD VALUES("; 
                	innerLoop.append(tempString);
                	
                	innerLoop.append(String.valueOf(rs.getInt("LKUP_FLD_ID")));
                	innerLoop.append(",");
                	innerLoop.append("'"+rs.getString("LKUP_FLD_DSC").replaceAll("'", "''")+"'");
                	innerLoop.append(");");
                	sb1.append(innerLoop.toString()+"\n");
                }
                
                sb1.append("\n\r");
                sb1.append("delete from LKUP where LKUP_GRP_FLD_ID in ( select LKUP_GRP_FLD_ID from LKUP_GRP_FLD where lkup_grp_cd IN "+refIds+");");
                sb1.append("\n\r");
                
                rs = stmt.executeQuery(sql10);
                while(rs.next()){
                	
                	StringBuilder innerLoop = new StringBuilder();
                	String tempString = "INSERT INTO LKUP VALUES("; 
                	innerLoop.append(tempString);
                	
                	innerLoop.append(String.valueOf(rs.getInt("LKUP_GRP_FLD_ID")));
                	innerLoop.append(",");
                	innerLoop.append("'"+rs.getString("LKUP_CD").replaceAll("'", "''")+"'");
                	
                	innerLoop.append(",");
                	innerLoop.append("'"+rs.getString("CD_ACTV_FLG").replaceAll("'", "''")+"'");
                	
                	innerLoop.append(",");
                	innerLoop.append(String.valueOf(rs.getInt("SORT_ORD")));
                	
                	innerLoop.append(",");
                	innerLoop.append("'"+rs.getString("UPDT_DT").replaceAll("'", "''")+"'");
                	
                	innerLoop.append(",");
                	innerLoop.append("N'"+rs.getString("LKUP_DSC").replaceAll("'", "''")+"'");
                	
                	innerLoop.append(");");
                	sb1.append(innerLoop.toString()+"\n");
                }
                
            }
            
            
            String outPut = new String(sb1.toString().getBytes("UTF-8"), "ISO-8859-1");
            String rootPath = System.getProperty(CATALINA_PATH);
			
			File dirs = new File(rootPath + File.separator + "tmpFiles");
			if (dirs.exists())
				FileUtils.forceDelete(dirs);
			
			File dir = new File(rootPath + File.separator + "tmpFiles");
			if (!dir.exists())
				dir.mkdirs();

            StringBuilder fNameBuff = new StringBuilder();
            fNameBuff.append(pageId);
            fNameBuff.append(".sql");
            String fName = fNameBuff.toString();
            
            File serverFile = new File(dir.getAbsolutePath()
					+ File.separator + fName);
			DataOutputStream out = new DataOutputStream(new FileOutputStream(serverFile));
            out.writeBytes(outPut);
			out.close();
            response.setFilePath(serverFile.getAbsolutePath());
            response.setExceptions("");
            response.setUtilSuccessfull("Y");
            rs.close();
            conn.close();
        } catch (SQLException | FileNotFoundException ex) {
        	log.error(ex);
            exceptions.append(ex.toString());
            response = setResponse(exceptions);
        } catch (Exception e) {
        	log.error(e);
			exceptions.append(e.toString());
			response = setResponse(exceptions);
		}
        finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close();
                }
            } catch (SQLException ex) {
            	log.error(ex);
                exceptions.append(ex.toString());
                response = setResponse(exceptions);
            }
        }
    	
    	return response;
    }
    
    
    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(@RequestParam ("name") String name, final HttpServletRequest request, final HttpServletResponse response) throws Exception {
    	
        File file = new File (name.replace("\\", "/"));
        try (InputStream fileInputStream = new FileInputStream(file);
                OutputStream output = response.getOutputStream();) {

            response.reset();

            response.setContentType("application/octet-stream");
            response.setContentLength((int) (file.length()));

            response.setHeader("Content-Disposition", "attachment; filename=\"" + file.getName() + "\"");

            IOUtils.copyLarge(fileInputStream, output);
            output.flush();
        } catch (IOException e) {
          log.error(e);
        }

    }

    
    
    @RequestMapping(value = "/ssputilityccd", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    public @ResponseBody SSPUtilityResponse getCCD(@RequestBody CCDS ccdInp) throws IOException{
        
    	String input = ccdInp.getTableName();
    	
    	SSPUtilityResponse response = new SSPUtilityResponse();
    	StringBuilder exceptions = new StringBuilder();
    	
    	
    	CCDGenerator ccdGen = new CCDGenerator();
        try {

        	File dirs = new File(CCDS_PATH);
			if (dirs.exists())
				FileUtils.forceDelete(dirs);
				
    		
        	File dirsZips = new File(CCDS_PATH+ File.separator + TMP_FILES_WKP_OUTPUT_ZIP);
    		if (dirsZips.exists()){
    			dirsZips.delete();
    			
    		}
        	ccdGen.generate("access", input.toUpperCase(), SSP_USERNAME, SSP_USERNAME);
        	File fs = new File(CCDS_PATH + File.separator + TMP_FILES_WKP_OUTPUT_ZIP);
        	ZipUtil.pack(new File("C:/GeneratedCode/ccd"), fs);
        	//response = setResponse(exceptions);
        
        	response.setFilePath(fs.getAbsolutePath());
            response.setExceptions("");
            response.setUtilSuccessfull("Y");
        	

        } catch (SQLException | FileNotFoundException ex) {
        	log.error(ex);
        	File dirs = new File(CCDS_PATH);
			try {
				if (dirs.exists())
					FileUtils.forceDelete(dirs);
			} catch (Exception e) {
				log.error(e);
			}
        	ex.printStackTrace();
            exceptions.append(ex.toString());
            response = setResponse(exceptions);
        }
        catch (Exception e) {
        	log.error(e);
        	File dirs = new File(CCDS_PATH);
			try {
				if (dirs.exists())
					FileUtils.forceDelete(dirs);
			} catch (Exception e1) {
				log.error(e1);
			}
			exceptions.append(e.toString());
			response = setResponse(exceptions);
            
		}
        
    	return response;
    }
    
    public SSPUtilityResponse setResponse( StringBuilder exceptions){
    	SSPUtilityResponse response = new SSPUtilityResponse();
    	response.setFilePath("");
        response.setExceptions(exceptions.toString());
        response.setUtilSuccessfull("N");
    	return response;
    }
    
    public void deleteFileWorkerPortal(String rootPath) throws IOException{
    	File dirs = new File(rootPath + File.separator + TMP_FILES_WKP_OUTPUT+ File.separator + "Output");
		if (dirs.exists())
			FileUtils.forceDelete(dirs);
		File dirsZip = new File(rootPath + File.separator + TMP_FILES_WKP_OUTPUT+ File.separator + TMP_FILES_WKP_OUTPUT_ZIP);
		if (dirsZip.exists()){
			dirsZip.delete();
			
		}
    	
    }
}
