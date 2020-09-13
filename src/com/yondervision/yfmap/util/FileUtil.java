package com.yondervision.yfmap.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.yondervision.yfmap.common.FtpUtil;

public class FileUtil {
	public static boolean makeSmsFile(String filepath,String dataFileName,List<LinkedHashMap> listData,String smssource){
		BufferedWriter bwRecvDataFile=null;
		FileWriter fwRecvDataFile = null;
		try {
			File thefile = new File(filepath);
			if (!thefile.exists()) {
				thefile.mkdirs();
			}
			fwRecvDataFile = new FileWriter(filepath+File.separator+dataFileName);
			bwRecvDataFile = new BufferedWriter(fwRecvDataFile);

			String head="SMSSOURCE\tPHONENUMBER\tELEMENTS\tSENDTYPE\tSENDTIME\tISPFLAG\tELEFLAG\tFUNDBUSINESS_ID";
			bwRecvDataFile.append(head);
			bwRecvDataFile.flush();
			bwRecvDataFile.newLine();
			SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
			Date date = new Date();
			for(int i=0;i<listData.size();i++){
				String content = "";
				Map<String,Object> map = listData.get(i);
				String phonenumber = map.get("phonenumber").toString();
				String fundbusiness_id = map.get("fundbusiness_id")==null?"1":map.get("fundbusiness_id").toString();
				for(String key:map.keySet()){
					if(!key.equals("phonenumber")&&!key.equals("fundbusiness_id")){
						if(!content.equals(""))content = content + ";";
						content = content + String.valueOf(map.get(key));
					}
				}
				String value = smssource+"\t"+phonenumber+"\t"+content+"\t1\t"+formatter1.format(date)+"\t1\t1\t"+fundbusiness_id;
				bwRecvDataFile.append(value);
				bwRecvDataFile.flush();
				bwRecvDataFile.newLine();
			}
			fwRecvDataFile.close();
			
			FtpUtil f=new FtpUtil("bsp");
			f.putFileToServer(formatter1.format(date).substring(0, 10)+File.separatorChar+dataFileName);
			return true;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
}
