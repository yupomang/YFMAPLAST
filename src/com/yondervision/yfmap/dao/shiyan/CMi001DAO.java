package com.yondervision.yfmap.dao.shiyan;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;



/** 
* @ClassName: CMi001DAO
* @author gongqi
* @date Nov 7, 2013 9:33:25 PM   
* 
*/ 
public interface CMi001DAO{
	
	public List<HashMap> selectAppapi00101(HashMap<String,String> map);
	public List<HashMap> selectAppapi00201(HashMap<String,String> map);
	public List<HashMap> selectAppapi00601(HashMap<String,String> map);
	public List<HashMap> selectAppapi00701(HashMap<String,String> map);
	public List<HashMap> selectAppapi40102(HashMap<String,String> map);
	public int UpdateAppflagAppapi40102(HashMap<String,String> map);
	public int UpdateWXflagAppapi40102(HashMap<String,String> map); 
	public List<LinkedHashMap> selectSmsapiZhxx(HashMap<String,String> map);
	public List<LinkedHashMap> selectSmsapiJxxx(HashMap<String,String> map);
	public List<LinkedHashMap> selectSmsapiDkxx(HashMap<String,String> map);
	public List<LinkedHashMap> selectSmsapiJcrz(HashMap<String,String> map);
	public List<LinkedHashMap> selectSmsapiGrtz(HashMap<String,String> map);
	public List<LinkedHashMap> selectSmsapiJxgj(HashMap<String,String> map);
	public List<LinkedHashMap> selectSmsapiDwcj(HashMap<String,String> map);
	public List<LinkedHashMap> selectSmsapiGrgjdxqy(HashMap<String,String> map);
	public List<LinkedHashMap> selectSmsapiZctq(HashMap<String,String> map);
	public List<LinkedHashMap> selectSmsapiXhtq(HashMap<String,String> map);
	public List<LinkedHashMap> selectSmsapiZctqpl(HashMap<String,String> map);
	public List<LinkedHashMap> selectSmsapiXhtqpl(HashMap<String,String> map);
	public List<LinkedHashMap> selectSmsapiLlbd(HashMap<String,String> map);
	public List<LinkedHashMap> selectAppapiJcrz(HashMap<String,String> map);
	public List<LinkedHashMap> selectAppapiGrtz(HashMap<String,String> map);
	public List<LinkedHashMap> selectAppapiJxgj(HashMap<String,String> map);
	public List<LinkedHashMap> selectAppapiDwcj(HashMap<String,String> map);
	public List<LinkedHashMap> selectAppapiGrgjdxqy(HashMap<String,String> map);
	public List<LinkedHashMap> selectAppapiZctq(HashMap<String,String> map);
	public List<LinkedHashMap> selectAppapiXhtq(HashMap<String,String> map);
//	public List<LinkedHashMap> selectAppapiZctqpl(HashMap<String,String> map);
//	public List<LinkedHashMap> selectSmsapiXhtqpl(HashMap<String,String> map);
	public List<LinkedHashMap> selectAppapiLlbd(HashMap<String,String> map);
	public List<HashMap> selectAppapiDkhth(HashMap<String,String> map);
	public List<HashMap> selectAppapiFlag(HashMap<String,String> map);
	public int UpdateSmsflag(HashMap<String,String> map);
	public int UpdateSmsPhonenumber(HashMap<String,String> map); 
	public List<HashMap> selectSmsapiStatus(HashMap<String,String> map);
	public List<HashMap> selectAppapi40103(HashMap<String,String> map);
}
