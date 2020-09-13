package com.yondervision.yfmap.dao.shiyan;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.orm.ibatis.support.SqlMapClientDaoSupport;

public class CMi001DAOImpl extends SqlMapClientDaoSupport implements CMi001DAO{

	
	public List<HashMap> selectAppapi00101(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppapi00101", map);
		return result;
	}

	
	public List<HashMap> selectAppapi00201(HashMap<String,String> map) {
		// TODO Auto-generated method stub

//		int pages = Integer.valueOf(map.get("pagenum"));
//		int rows = Integer.valueOf(map.get("pagerows"));
//		pages = pages==0 ? Integer.valueOf(1) : (pages + 1);
//		rows = rows==0 ? Integer.valueOf(10) : rows;
//		int skipResults = (pages-1) * rows;
//		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppapi00201", map,skipResults,rows);
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppapi00201", map);
		return result;
	}

	
	public List<HashMap> selectAppapi00601(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppapi00601", map);
		return result;
	}

	
	public List<HashMap> selectAppapi00701(HashMap<String,String> map) {
		// TODO Auto-generated method stub

//		int pages = Integer.valueOf(map.get("pagenum"));
//		int rows = Integer.valueOf(map.get("pagenum"));
//		pages = pages==0 ? Integer.valueOf(1) : (pages + 1);
//		rows = rows==0 ? Integer.valueOf(10) : rows;
//		int skipResults = (pages-1) * rows;
//		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppapi00701",  map,skipResults,rows);
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppapi00701",  map);
		return result;
	}

	
	public List<HashMap> selectAppapi40102(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppapi40102", map);
		return result;
	}
	
	public int UpdateAppflagAppapi40102(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		int result = getSqlMapClientTemplate().update("CMI001.updateAppFlagAppapi40102", map);
		return result;
	}
	
	public int UpdateWXflagAppapi40102(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		int result = getSqlMapClientTemplate().update("CMI001.updateWXFlagAppapi40102", map);
		return result;
	}
	
	public List<LinkedHashMap> selectSmsapiZhxx(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsZhye", map);
		return result;
	}
	
	public List<LinkedHashMap> selectSmsapiJxxx(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsJxxx", map);
		return result;
	}
	
	public List<LinkedHashMap> selectSmsapiJcrz(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsJcrz", map);
		return result;
	}
	
	public List<LinkedHashMap> selectSmsapiGrtz(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsGrtz", map);
		return result;
	}
	
	public List<LinkedHashMap> selectSmsapiJxgj(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsJxgj", map);
		return result;
	}
	
	public List<LinkedHashMap> selectSmsapiDwcj(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsDwcj", map);
		return result;
	}
	
	public List<LinkedHashMap> selectSmsapiGrgjdxqy(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsGrgjdxqy", map);
		return result;
	}
	
	public List<LinkedHashMap> selectSmsapiZctq(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsZctq", map);
		return result;
	}
	
	public List<LinkedHashMap> selectSmsapiXhtq(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsXhtq", map);
		return result;
	}
	
	public List<LinkedHashMap> selectSmsapiZctqpl(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsZctqpl", map);
		return result;
	}
	
	public List<LinkedHashMap> selectSmsapiXhtqpl(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsXhtqpl", map);
		return result;
	}
	
	public List<LinkedHashMap> selectSmsapiLlbd(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsLlbd", map);
		return result;
	}

	
	public List<LinkedHashMap> selectSmsapiDkxx(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsDkxx", map);
		return result;
	}

	
	public int UpdateSmsflag(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		int result = getSqlMapClientTemplate().update("CMI001.updateSmsFlag", map);
		return result;
	}

	
	public int UpdateSmsPhonenumber(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		int result = getSqlMapClientTemplate().update("CMI001.updateSmsPhonenumber", map);
		return result;
	}

	
	public List<LinkedHashMap> selectAppapiJcrz(HashMap<String, String> map) {
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppJcrz", map);
		return result;
	}

	
	public List<LinkedHashMap> selectAppapiGrtz(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppGrtz", map);
		return result;
	}

	
	public List<LinkedHashMap> selectAppapiJxgj(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppJxgj", map);
		return result;
	}

	
	public List<LinkedHashMap> selectAppapiDwcj(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppDwcj", map);
		return result;
	}

	
	public List<LinkedHashMap> selectAppapiGrgjdxqy(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppGrgjdxqy", map);
		return result;
	}

	
	public List<LinkedHashMap> selectAppapiZctq(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppZctq", map);
		return result;
	}

	
	public List<LinkedHashMap> selectAppapiXhtq(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppXhtq", map);
		return result;
	}

	
	public List<LinkedHashMap> selectAppapiLlbd(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppLlbd", map);
		return result;
	}

	
	public List<HashMap> selectAppapiDkhth(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppDkhth", map);
		return result;
	}

	
	public List<HashMap> selectAppapiFlag(HashMap<String, String> map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppapiFlag", map);
		return result;
	}
	public List<HashMap> selectSmsapiStatus(HashMap<String,String> map){
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectSmsapiStatus", map);
		return result;
	}
	
	public List<HashMap> selectAppapi40103(HashMap<String,String> map) {
		// TODO Auto-generated method stub
		List<HashMap> result = getSqlMapClientTemplate().queryForList("CMI001.selectAppapi40103", map);
		return result;
	}
}
