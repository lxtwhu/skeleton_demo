package com.lxt.tool;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Replace {
	public static void main(String[] args) throws Exception {
		Properties pro = new Properties();
		HashMap<String,String> data = new HashMap<String,String>();
		if(args==null || !(args.length==3 || args.length==2) ) { System.err.println("Usage: Replace <inputFile> <outputFile> [properties] ");System.exit(-1);}
		File inFile = new File(args[0]);
		File ouFile = new File(args[1]);
		if(! inFile.exists() || !inFile.canRead()) {System.err.println("Error: file ["+inFile.getAbsolutePath()+"] not exist or cannot read !" );System.exit(-1);}
		System.out.print(">       replace ["+inFile.getName()+"]  ... ");
		if(args.length==3) {
			File prFile = new File(args[2]);
			if(! prFile.exists() || !prFile.canRead()) {System.err.println("Error: file ["+prFile.getAbsolutePath()+"] not exist or cannot read !" );System.exit(-1);}
			System.out.print(" using properties ["+prFile.getName()+"] ");
			pro.load(new FileInputStream(prFile));
		}
		System.out.println("");
		BufferedReader in = new BufferedReader(new FileReader(inFile));
		StringBuffer strBuf = new StringBuffer();	
		
		String line = in.readLine();
		while(line!=null) {
			strBuf.append(line+"\n");
			String val = line;	
			while(val.contains("${")) {
				String envKey = val.substring(val.indexOf("${")+2, val.indexOf("}"));
				System.out.print(">              find var ${"+envKey+"}");
				if(pro.getProperty(envKey)!=null && pro.getProperty(envKey).trim().length()>0) {
					data.put(envKey, pro.getProperty(envKey).trim());
					System.out.println(" = ["+data.get(envKey)+"] in prop");
				}else {
					if(System.getenv(envKey)!=null && System.getenv(envKey).trim().length()>0) {
						data.put(envKey, System.getenv(envKey).trim());
						System.out.println(" = ["+data.get(envKey)+"] in env");
					}else {
						System.out.println(" NOT found ");
					}
				}
				val = val.substring(val.indexOf("}")+1);
			}
			line = in.readLine();
		}
		BufferedWriter ou = new BufferedWriter(new FileWriter(ouFile));
		ou.write(templateParse(strBuf.toString(),data));
		ou.flush();
		in.close();
		ou.close();
	}
	/**
     * 将字符串template中由${key}组成的占位符依次替换为data中相应的值覆盖
     * @param template
     * @param data
     * @return
     * @throws Exception
     */
    private static String templateParse(String template, Map<String,String> data) {  
        String regex = "\\$\\{(.+?)\\}";  
        Pattern pattern = Pattern.compile(regex);  
        Matcher matcher = pattern.matcher(template);  
        StringBuffer sb = new StringBuffer();  
        while (matcher.find()) {  
            String name = matcher.group(1);//键名  
            String value = (String) data.get(name);//键值  
//            if (value == null) {  
//                value = matcher.group(0).replaceAll("\\$", "\\\\\\$");
//            } else {   
//                value = value.replaceAll("\\$", "\\\\\\$"); 
//            }  
//            matcher.appendReplacement(sb, value);
            if (value != null) {   
                value = value.replaceAll("\\$", "\\\\\\$"); 
                matcher.appendReplacement(sb, value);
            }
        } 
        matcher.appendTail(sb);  
        return sb.toString();  
    }  
}
