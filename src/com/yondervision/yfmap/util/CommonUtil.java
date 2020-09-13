/**
 * 工程名称：YBMAP
 * 包名：          com.yondervision.mi.util
 * 文件名：     CommonUtil.java
 * 创建日期：2013-9-29
 */
package com.yondervision.yfmap.util;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.NoRollRuntimeErrorException;
import com.yondervision.yfmap.dao.CMi008DAO;
import com.yondervision.yfmap.dao.impl.Mi008DAOImpl;
import com.yondervision.yfmap.dto.Mi008;
import com.yondervision.yfmap.dto.Mi008Example;
import com.yondervision.yfmap.dto.Mi008Example.Criteria;

/**
 * 公共方法类
 * 
 * @author LinXiaolong
 * 
 */
public class CommonUtil {

//	@Autowired
//	private CMi008DAO cmi008Dao;
//	
//	/**
//	 * @param cmi008Dao the cmi008Dao to set
//	 */
//	public void setCmi008Dao(CMi008DAO cmi008Dao) {
//		this.cmi008Dao = cmi008Dao;
//	}
	
	/**
	 * 判断对象是否为空
	 * 
	 * @param obj
	 *            要判断的对象
	 * @return true:对象为空；false:对象不为空
	 */
	@SuppressWarnings("unchecked")
	public static boolean isEmpty(Object obj) {
		if (obj instanceof String) {
			String temp = (String) obj;
			return temp == null || temp.equals("");
		}
		if (obj instanceof Object[]) {
			Object[] temp = (Object[]) obj;
			return temp == null || temp.length == 0;
		}
		if (obj instanceof List) {
			List temp = (List) obj;
			return temp == null || temp.size() == 0;
		}
		return obj == null;
	}

	/**
	 * 根据在工程中的相对路径取得绝对路径
	 * 
	 * @param pURL
	 *            在工程中的相对路径
	 * @return 系统中的绝对路径
	 * @throws IOException 
	 */
	public static String getFullURL(String pURL) throws IOException {
		pURL = pURL.replace("/", File.separator).replace("\\\\", File.separator);
		pURL = pURL.startsWith(File.separator) ? pURL : File.separator + pURL;
		String fullURL = SpringContextUtil.getApplicationContext().getResource(pURL).getFile().getAbsolutePath();
		
		return fullURL.endsWith(File.separator) ? fullURL : fullURL+File.separator;
	}
	
	/**
	 * 取得当前系统时间，格式为yyyy-MM-dd HH:mm:ss.SSS
	 * @return 当前系统时间，格式为yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static String getSystemDate() {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_TIME_FORMAT);
		return formatter.format(new Date());
	} 
	
	/**
	 * 取得当前系统时间，格式为yyyyMMddHHmmssSSS
	 * @return 当前系统时间，格式为yyyyMMddHHmmssSSS
	 */
	public static String getSystemDateNumOnly() {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmssSSS");
		return formatter.format(new Date());
	}
	
	/**
	 * 取得当前系统日期，格式为yyyy-MM-dd
	 * @return 当前系统日期，格式为yyyy-MM-dd
	 */
	public static String getDate() {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_YYYY_MM_DD);
		return formatter.format(new Date());
	}
	
	/**
	 * 取得当前系统时间，格式为HH:mm:ss
	 * @return 当前系统时间，格式为HH:mm:ss
	 */
	public static String getTime() {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.TIME_FORMAT);
		return formatter.format(new Date());
	}
	
	/**
	 * 取得当前系统日期，格式为yyyyMMdd
	 * @return 当前系统日期，格式为yyyyMMdd
	 */
	public static String getDate1() {
		SimpleDateFormat formatter = new SimpleDateFormat(Constants.DATE_FORMAT_XH_YYMMDD);
		return formatter.format(new Date());
	}
	
	/**
	 * 采号
	 * @param key 采号操作的表
	 * @param basic 采号步长
	 * @return 
	 * @throws Exception 
	 * @throws Exception
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor=NoRollRuntimeErrorException.class)
	public synchronized Long genKeyAndIncrease(String key, int basic) throws Exception{
		System.out.println("采号步长："+basic);
		CMi008DAO cmi008Dao = (CMi008DAO)com.yondervision.yfmap.util.SpringContextUtil.getBean("cmi008Dao");
		Long ret=null;
		Mi008Example m8e = new Mi008Example();
		Criteria ca = m8e.createCriteria();
		ca.andSyskeyEqualTo(key);
		List<Mi008> list = cmi008Dao.selectByExampleForUpdate(m8e);
		if(list.size()==0){
			Mi008 record = new Mi008();
			record.setSyskey(key);
			record.setSysseq(new Long(basic));
			record.setModitime(Datelet.getCurrentDateTime());
			cmi008Dao.insert(record);
			ret = Long.valueOf(basic);
		}else{ 
			Mi008 record = list.get(0);
			Mi008 recordTmp = new Mi008();
			recordTmp.setSyskey(record.getSyskey());
			recordTmp.setSysseq(record.getSysseq()+basic);
			recordTmp.setModitime(Datelet.getCurrentDateTime());
			cmi008Dao.updateByExample(recordTmp, m8e);
			ret = Long.valueOf(recordTmp.getSysseq());
		}

		return ret-basic+1;
	}
	
	/**
	 * 生成一个递增序号
	 * 
	 * @param key
	 *            种子	 *            
	 * @return 递增的序号
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor=NoRollRuntimeErrorException.class)
	public Long genKey(String key) throws Exception{
		return genKeyAndIncrease(key, 1);
	    /*Mi008DAO   mi008Dao=	(Mi008DAO)SpringContextUtil.getBean("mi008Dao");	 
		Mi008Example m8e=new Mi008Example();
		Criteria ca= m8e.createCriteria();
		ca.andSyskeyEqualTo(key); 
		List<Mi008> list=mi008Dao.selectByExample(m8e);
		 
		if(list.size()==0){	
			Mi008 record =new Mi008();
			record.setSyskey(key);
			record.setSysseq(new Long(1));
			record.setModitime(Datelet.getCurrentDateTime());
			mi008Dao.insert(record);
			return new Long(1);
		}else{ 
			Mi008 record=list.get(0);
			Mi008 recordTmp = new Mi008();
			recordTmp.setSyskey(record.getSyskey());
			recordTmp.setSysseq(record.getSysseq()+1);
			recordTmp.setModitime(Datelet.getCurrentDateTime());
			mi008Dao.updateByExample(recordTmp, m8e);
			return new Long(record.getSysseq()+1);
		}*/
	}
	
	
	/**
	 * 生成一个递增序号，按照要求在前面补0
	 * 
	 * @param key
	 *            种子	 
	 * @param ws
	 *            要求返回的位数要求          
	 * @return 递增的序号
	 * @throws Exception 
	 */
	@Transactional(propagation = Propagation.REQUIRES_NEW, noRollbackFor=NoRollRuntimeErrorException.class)
	public String genKey(String key,int ws) throws Exception{
		Long seq=genKey(key);
		int tmplen=(seq+"").length();
		StringBuffer zero=new StringBuffer();
		for(int i=0;i<ws-tmplen;i++){
			zero.append("0");
		}
		return zero.toString()+seq;
	}
	
	public static String genKeyAndCommit(String key,int ws) throws Exception{
		Long seq=genKeyAndCommit(key);
		int tmplen=(seq+"").length();
		StringBuffer zero=new StringBuffer();
		for(int i=0;i<ws-tmplen;i++){
			zero.append("0");
		}
		return zero.toString()+seq;
	}
	
	public static Long genKeyAndCommit(String key) throws Exception{
		Mi008DAOImpl mi008Dao=(Mi008DAOImpl)SpringContextUtil.getBean("mi008Dao");	 
		mi008Dao.getSqlMapClient().startTransaction();
		Mi008Example m8e=new Mi008Example();
		Criteria ca= m8e.createCriteria();
		ca.andSyskeyEqualTo(key); 
		List<Mi008> list=mi008Dao.selectByExample(m8e);
		long ret=0;
		if(list.size()==0){	
			Mi008 record =new Mi008();
			record.setSyskey(key);
			record.setSysseq(new Long(1));
			record.setModitime(Datelet.getCurrentDateTime());
			mi008Dao.insert(record);
			ret= Long.valueOf("1");
		}else{ 
			Mi008 record=list.get(0);
			Mi008 recordTmp = new Mi008();
			recordTmp.setSyskey(record.getSyskey());
			recordTmp.setSysseq(record.getSysseq()+1);
			recordTmp.setModitime(Datelet.getCurrentDateTime());
			mi008Dao.updateByExample(recordTmp, m8e);
			ret= Long.valueOf(record.getSysseq()+1);
		} 		
		mi008Dao.getSqlMapClient().commitTransaction();
		mi008Dao.getSqlMapClient().endTransaction();
		return ret;
	}
	
	/**
	 * 记录参数日志时使用，将JAVABEAN转为String。
	 * @param javaBean 参数的form对象
	 * @return 可输出的参数日志
	 */
	public static String getStringParams(Object javaBean) {
		ObjectMapper mapper = new ObjectMapper();
		return mapper.convertValue(javaBean, Map.class).toString();
	}
	
	//匹配输入数字
    public static boolean getMatchingNumber(String str){
        boolean flag = false;
        String pattern = "\\d+";
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        flag = m.matches();
        return flag;
    }
    
  //获得字符串中变量名（变量名只有一个的情况）
    public static String getKey(String elString){
        	String result = "";
        	for(int i=0;i<elString.length();i++){
        		char temp = elString.charAt(i);
        		if(temp==' '||temp=='!'||temp=='='){
        			break;
        		}
        		result +=temp;
        	} 
        	return result;
        }

    //判断字符串逻辑表达式是否为真    
    public static Boolean isTrue(String key,String elValue,String elString){  
            ScriptEngineManager manager = new ScriptEngineManager();  
            ScriptEngine engine = manager.getEngineByName("js");  
            engine.put(key,elValue);  
            boolean eval = false;  
            try {  
                eval = (Boolean) engine.eval(elString);  
            } catch (ScriptException e) {  
                e.printStackTrace();  
            }       
            return eval;  
        }  
        
      /**
      	*多个变量
         * 判断elString表达式中变量名key取elValue值是否为真 
         * @param key
         * @param elValue
         * @param elString
         * @return
         */
        public static Boolean isTrue(HashMap<String,String> maps,String elString){  
            ScriptEngineManager manager = new ScriptEngineManager();  
            ScriptEngine engine = manager.getEngineByName("js");  

            for(String key : maps.keySet()){ 
            	engine.put(key, maps.get(key));
            }
            boolean eval = false;  
            try {  
                eval = (Boolean) engine.eval(elString);  
            } catch (ScriptException e) {  
                e.printStackTrace();  
            }       
            return eval;  
        }  
        
        /**
         * 比较String类型日期大小
         * @param date1
         * @param date2
         * @param format
         * @return
         */
        public static int compare_date(String date1, String date2,String format) {
        	SimpleDateFormat df = new SimpleDateFormat(format);
        	try {
                   Date dt1 = df.parse(date1);
                   Date dt2 = df.parse(date2);
                   if (dt1.getTime() > dt2.getTime()) {
                       //System.out.println("dt1 在dt2前");
                       return 1;
                   } else if (dt1.getTime() < dt2.getTime()) {
                      //System.out.println("dt1在dt2后");
                	   return -1;
                   } else {
                	   return 0;
                   }
             } catch (Exception exception) {
                 exception.printStackTrace();
             }
             return 0;
         }
}
