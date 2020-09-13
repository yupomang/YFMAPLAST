package com.yondervision.yfmap.service.shiyan;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import com.yondervision.yfmap.dao.shiyan.CMi001DAO;
import com.yondervision.yfmap.dao.shiyan.SmsSendRecoderDAO;
import com.yondervision.yfmap.dto.shiyan.SmsSendRecoder;
import com.yondervision.yfmap.dto.shiyan.SmsSendRecoderKey;

public class AppApiSearchDataServiceImpl implements AppApiSearchDataService{
	private CMi001DAO cmi001Dao;
	private SmsSendRecoderDAO smsSendRecoderDAO;
	public CMi001DAO getCmi001Dao() {
		return cmi001Dao;
	}

	public void setCmi001Dao(CMi001DAO cmi001Dao) {
		this.cmi001Dao = cmi001Dao;
	}

	public SmsSendRecoderDAO getSmsSendRecoderDAO() {
		return smsSendRecoderDAO;
	}

	public void setSmsSendRecoderDAO(SmsSendRecoderDAO smsSendRecoderDAO) {
		this.smsSendRecoderDAO = smsSendRecoderDAO;
	}

	@SuppressWarnings("rawtypes")
	
	public List<HashMap> appApi00101Select(HashMap<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		List<HashMap> list = this.getCmi001Dao().selectAppapi00101(map);
		return list;
	}

	
	public List<HashMap> appApi00201Select(HashMap<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		List<HashMap> list = this.getCmi001Dao().selectAppapi00201(map);
		return list;
	}

	
	public List<HashMap> appApi00601Select(HashMap<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		List<HashMap> list = this.getCmi001Dao().selectAppapi00601(map);
		return list;
	}

	
	public List<HashMap> appApi00701Select(HashMap<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		List<HashMap> list = this.getCmi001Dao().selectAppapi00701(map);
		return list;
	}
	
	public List<HashMap> appApi40102Select(HashMap<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		List<HashMap> list = this.getCmi001Dao().selectAppapi40102(map);
		return list;
	}
	
	public int appApi40102UpdateAppFlag(HashMap<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		int result = this.getCmi001Dao().UpdateAppflagAppapi40102(map);
		return result;
	}
	
	public int appApi40102UpdateWXFlag(HashMap<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		int result = this.getCmi001Dao().UpdateWXflagAppapi40102(map);
		return result;
	}
	
	public List<LinkedHashMap> smsApiZhxxSelect(HashMap<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		List<LinkedHashMap> list = this.getCmi001Dao().selectSmsapiZhxx(map);
		return list;
	}

	public List<LinkedHashMap> smsApiJxxxSelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectSmsapiJxxx(map);
		return result;
	}
	public List<LinkedHashMap> smsApiJcrzSelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectSmsapiJcrz(map);
		return result;
	}

	public List<LinkedHashMap> smsApiDkxxSelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectSmsapiDkxx(map);
		return result;
	}

	public List<LinkedHashMap> smsApiGrtzSelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectSmsapiGrtz(map);
		return result;
	}

	public List<LinkedHashMap> smsApiJxgjSelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectSmsapiJxgj(map);
		return result;
	}

	public List<LinkedHashMap> smsApiDwcjSelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectSmsapiDwcj(map);
		return result;
	}

	public List<LinkedHashMap> smsApiGrgjdxqySelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectSmsapiGrgjdxqy(map);
		return result;
	}

	public List<LinkedHashMap> smsApiZctqSelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectSmsapiZctq(map);
		return result;
	}

	public List<LinkedHashMap> smsApiXhtqSelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectSmsapiXhtq(map);
		return result;
	}

	public List<LinkedHashMap> smsApiXhtqplSelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectSmsapiXhtqpl(map);
		return result;
	}

	public List<LinkedHashMap> smsApiZctqplSelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectSmsapiZctqpl(map);
		return result;
	}

	public List<LinkedHashMap> smsApiLlbdSelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectSmsapiLlbd(map);
		return result;
	}
	
	public int updateSmsflag(HashMap<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		int result = this.getCmi001Dao().UpdateSmsflag(map);
		return result;
	}
	
	public int updateSmsPhonenumber(HashMap<String,String> map) throws Exception {
		// TODO Auto-generated method stub
		int result = this.getCmi001Dao().UpdateSmsPhonenumber(map);
		return result;
	}
	
	public List<HashMap> smsStatusSelect(HashMap<String,String> map) throws Exception{
		// TODO Auto-generated method stub
		List<HashMap> result = this.getCmi001Dao().selectSmsapiStatus(map);
		return result;
	}

	
	public List<LinkedHashMap> appApiJcrzSelect(HashMap<String, String> map)
			throws Exception {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectAppapiJcrz(map);
		String smssource = map.get("smssource");
		List<LinkedHashMap> haveresult = new ArrayList<LinkedHashMap>();
		for(int i=0;i<result.size();i++){
			LinkedHashMap tempmap = result.get(i);
			String certinum = tempmap.get("personal_cardno").toString();
			String fundbusiness_id = tempmap.get("fundbusiness_id").toString();
			SmsSendRecoderKey key = new SmsSendRecoderKey();
			key.setFundbusinessId(fundbusiness_id);
			key.setSmssource(smssource);
			key.setCertinum(certinum);
			SmsSendRecoder recoder = smsSendRecoderDAO.selectByPrimaryKey(key);
			if(recoder!=null){
				haveresult.add(result.get(i));
			}else{
				recoder = new SmsSendRecoder();
				recoder.setCertinum(certinum);
				recoder.setFundbusinessId(fundbusiness_id);
				recoder.setSmssource(smssource);
				try{
					smsSendRecoderDAO.insert(recoder);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		result.removeAll(haveresult);
		return result;
	}

	
	public List<LinkedHashMap> appApiGrtzSelect(HashMap<String, String> map)
			throws Exception {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectAppapiGrtz(map);
		List<LinkedHashMap> haveresult = new ArrayList<LinkedHashMap>();
		String smssource = map.get("smssource");
		for(int i=0;i<result.size();i++){
			LinkedHashMap tempmap = result.get(i);
			String certinum = tempmap.get("personal_cardno").toString();
			String fundbusiness_id = tempmap.get("fundbusiness_id").toString();
			SmsSendRecoderKey key = new SmsSendRecoderKey();
			key.setFundbusinessId(fundbusiness_id);
			key.setSmssource(smssource);
			key.setCertinum(certinum);
			SmsSendRecoder recoder = smsSendRecoderDAO.selectByPrimaryKey(key);
			if(recoder!=null){
				haveresult.add(result.get(i));
			}else{
				recoder = new SmsSendRecoder();
				recoder.setCertinum(certinum);
				recoder.setFundbusinessId(fundbusiness_id);
				recoder.setSmssource(smssource);
				try{
					smsSendRecoderDAO.insert(recoder);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return result;
	}

	
	public List<LinkedHashMap> appApiJxgjSelect(HashMap<String, String> map)
			throws Exception {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectAppapiJxgj(map);
		String smssource = map.get("smssource");
		List<LinkedHashMap> haveresult = new ArrayList<LinkedHashMap>();
		for(int i=0;i<result.size();i++){
			LinkedHashMap tempmap = result.get(i);
			String certinum = tempmap.get("personal_cardno").toString();
			String fundbusiness_id = tempmap.get("fundbusiness_id").toString();
			SmsSendRecoderKey key = new SmsSendRecoderKey();
			key.setFundbusinessId(fundbusiness_id);
			key.setSmssource(smssource);
			key.setCertinum(certinum);
			SmsSendRecoder recoder = smsSendRecoderDAO.selectByPrimaryKey(key);
			if(recoder!=null){
				haveresult.add(result.get(i));
			}else{
				recoder = new SmsSendRecoder();
				recoder.setCertinum(certinum);
				recoder.setFundbusinessId(fundbusiness_id);
				recoder.setSmssource(smssource);
				try{
					smsSendRecoderDAO.insert(recoder);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		result.removeAll(haveresult);
		return result;
	}

	
	public List<LinkedHashMap> appApiDwcjSelect(HashMap<String, String> map)
			throws Exception {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectAppapiDwcj(map);
		return result;
	}

	
	public List<LinkedHashMap> appApiZctqSelect(HashMap<String, String> map)
			throws Exception {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectAppapiZctq(map);
		return result;
	}

	
	public List<LinkedHashMap> appApiXhtqSelect(HashMap<String, String> map)
			throws Exception {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectAppapiXhtq(map);
		return result;
	}

	
	public List<LinkedHashMap> appApiLlbdSelect(HashMap<String, String> map)
			throws Exception {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectAppapiLlbd(map);
		return result;
	}

	
	public List<LinkedHashMap> appApiGrgjdxqySelect(HashMap<String, String> map)
			throws Exception {
		// TODO Auto-generated method stub
		List<LinkedHashMap> result = this.getCmi001Dao().selectAppapiGrgjdxqy(map);
		return result;
	}

	
	public List<HashMap> appApiDkhthSelect(HashMap<String, String> map)
			throws Exception {
		// TODO Auto-generated method stub
		List<HashMap> result = this.getCmi001Dao().selectAppapiDkhth(map);
		return result;
	}
	
	public List<HashMap> appApiFlagSelect(HashMap<String, String> map)
			throws Exception {
		// TODO Auto-generated method stub
		List<HashMap> result = this.getCmi001Dao().selectAppapiFlag(map);
		return result;
	}
}
