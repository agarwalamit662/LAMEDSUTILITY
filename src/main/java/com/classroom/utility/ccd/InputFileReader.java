package com.classroom.utility.ccd;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * @author balasan
 *
 * This class is read the input file which is in .csv format 
 */
public class InputFileReader {

	/**
	 * returns the List from the given .csv input file
	 */
	public List readInputFile(File file) throws Exception {
		
		try {
			List inputList = new ArrayList();
			BufferedReader bw = new BufferedReader(new FileReader(file));
			String line=null;
			while (!((line = bw.readLine()) == null)) {
				List temp = new ArrayList();
				StringTokenizer st = new StringTokenizer(line, ",");
				while(st.hasMoreTokens()){
					temp.add(st.nextToken());	
				}
				inputList.add(temp);
			}
			return inputList;
		}catch(Exception e){
			try {
				BufferedWriter bw =	new BufferedWriter(new FileWriter("C:/GeneratedCode/error.log"));
				bw.write("Error reading input file");
				bw.newLine();
				bw.write(e.toString());
				bw.flush();
				bw.close();
				throw e;
			}catch(Exception te){
				throw te;
			}
		}
	}
}