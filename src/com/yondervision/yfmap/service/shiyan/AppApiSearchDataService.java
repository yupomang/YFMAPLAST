package com.yondervision.yfmap.service.shiyan;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;


public interface AppApiSearchDataService {
	
	public List<HashMap> appApi00101Select(HashMap<String,String> map) throws Exception;
	public List<HashMap> appApi00201Select(HashMap<String,String> map) throws Exception;
	public List<HashMap> appApi00601Select(HashMap<String,String> map) throws Exception;
	public List<HashMap> appApi00701Select(HashMap<String,String> map) throws Exception;
	public List<HashMap> appApi40102Select(HashMap<String,String> map) throws Exception;
	public int appApi40102UpdateAppFlag(HashMap<String,String> map) throws Exception ;
	public int appApi40102UpdateWXFlag(HashMap<String,String> map) throws Exception ;
	public List<LinkedHashMap> smsApiZhxxSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> smsApiJxxxSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> smsApiDkxxSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> smsApiJcrzSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> smsApiGrtzSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> smsApiJxgjSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> smsApiDwcjSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> smsApiGrgjdxqySelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> smsApiZctqSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> smsApiXhtqSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> smsApiZctqplSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> smsApiXhtqplSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> smsApiLlbdSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> appApiJcrzSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> appApiGrtzSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> appApiJxgjSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> appApiDwcjSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> appApiGrgjdxqySelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> appApiZctqSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> appApiXhtqSelect(HashMap<String,String> map) throws Exception;
//	public List<LinkedHashMap> smsApiZctqplSelect(HashMap<String,String> map) throws Exception;
//	public List<LinkedHashMap> smsApiXhtqplSelect(HashMap<String,String> map) throws Exception;
	public List<LinkedHashMap> appApiLlbdSelect(HashMap<String,String> map) throws Exception;
	public List<HashMap> appApiFlagSelect(HashMap<String, String> map)throws Exception;
	public List<HashMap> appApiDkhthSelect(HashMap<String,String> map) throws Exception;
	public int updateSmsflag(HashMap<String,String> map) throws Exception ;
	public int updateSmsPhonenumber(HashMap<String,String> map) throws Exception ;
	public List<HashMap> smsStatusSelect(HashMap<String,String> map) throws Exception;
}
