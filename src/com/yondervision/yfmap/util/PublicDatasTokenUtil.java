package com.yondervision.yfmap.util;

import java.util.Date;

import org.apache.log4j.Logger;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.util.couchbase.CouchBase;

import net.sf.json.JSONObject;

public class PublicDatasTokenUtil {
	//浙江公共数据工作平台
	public static  String getPublicDataSecretWithCouchBase(String centerId){		
		Logger log = Logger.getLogger("YFMAP");
		String ret=null;
		String centerId1 = "00057400";
		centerId = centerId1;
		String requestSecret="pd|requestSecret|"+centerId;
		log.info("requestSecret："+requestSecret);
		try {
			CouchBase cb=CouchBase.getInstance();
			//判断请求秘钥是否存在
			if(cb.get(requestSecret)==null){
				ret=getPublicDataSecretWithURL(centerId);
				log.info("请求密钥不存在");
			}else{
				ret=(String)cb.get(requestSecret);
				log.info("请求密钥存在");
			}
		} catch (Exception e) {
			log.info(e);
			e.printStackTrace();
			log.info("Exception ret："+ret);
			return ret;
		}

		log.info("[-]getPublicDataSecretWithCouchBase ret="+ret);
		return ret;
	}

    public synchronized static String getPublicDataSecretWithURL(String centerId) throws Exception {
    	
		Logger log = Logger.getLogger("YFMAP");
//		log.info("执行块睡觉前");
//      Thread.sleep(20000);  //5秒
// 		log.info("执行块睡觉后");
		CouchBase cb=CouchBase.getInstance();
		String ret=null;
		String refreshSecret="pd|refreshSecret|"+centerId;
		String requestSecret="pd|requestSecret|"+centerId;
		String publicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
    				"appKeyUrl").trim();
		String refreshUrl = publicDatasUrl + "app/refreshTokenByKey.htm";
		String requestUrl = publicDatasUrl + "app/refreshTokenBySec.htm";
		String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
    				"appKey").trim();
		String appSecret = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME,
    				"appSecret").trim();
		String result = "";
		SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
		Date date = new Date();
		String requestTime = String.valueOf(date.getTime());
		String url = "";
		//判断刷新秘钥是否存在
		if(cb.get(refreshSecret)==null){
			log.info("刷新秘钥不存在");
			log.info("appKey："+appKey);
			log.info("appSecret："+appSecret);
			log.info("requestTime："+requestTime);
			String sign = EncryptionByMD5.getMD5((appKey.toLowerCase() + appSecret.toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
			url = refreshUrl + "?appKey=" + appKey + "&sign=" + sign + "&requestTime=" + requestTime;
		}else{
			log.info("刷新秘钥存在");
			log.info("appKey："+appKey);
			log.info("cb.get(refreshSecret).："+cb.get(refreshSecret));
			log.info("requestTime："+requestTime);
			String sign = EncryptionByMD5.getMD5((appKey.toLowerCase() + cb.get(refreshSecret).toString().toLowerCase() + requestTime).getBytes("UTF-8")).toLowerCase();
			url = requestUrl + "?appKey=" + appKey + "&sign=" + sign + "&requestTime=" + requestTime;
		}
		log.info("获取TOKEN："+url);
		result = msm.sendGet(url, "UTF-8");

		log.info("Refresh Secret："+result);
		
		//判断如果签名错误，删除刷新秘钥，调用接口获取刷新秘钥和请求秘钥
		JSONObject json = JSONObject.fromObject(result);
		String msg=json.getString("msg");
		String code=json.getString("code");
		if("03".equals(code)&&"签名错误".equals(msg)){
			cb.delete("pd|refreshSecret|00057400");
			log.info("code：03，签名错误，重头来过");
			log.info("appKey："+appKey);
			log.info("appSecret："+appSecret);
			String requestTime2 = String.valueOf(date.getTime());
			log.info("requestTime2："+requestTime2);
			String sign = EncryptionByMD5.getMD5((appKey.toLowerCase() + appSecret.toLowerCase() + requestTime2).getBytes("UTF-8")).toLowerCase();
			String url2 = refreshUrl + "?appKey=" + appKey + "&sign=" + sign + "&requestTime=" + requestTime2;
			result = msm.sendGet(url2, "UTF-8");
		}
				
		JSONObject tokenjson = JSONObject.fromObject(result);
		log.info("[+]getPublicDataSecretWithCouchBase centerId="+centerId);  
		if("00".equals(tokenjson.getString("code"))){
			JSONObject datas = tokenjson.getJSONObject("datas");
			//将刷新秘钥保存到缓存
			int expRefresh = 3600*47;//48小时过期，提前一小时更新
//			int expRefresh = 120;//48小时过期，提前一小时更新 测试功能用
			log.info("key="+refreshSecret+",ret="+datas.getString("refreshSecret")+",exp="+expRefresh);
			cb.save(refreshSecret, datas.getString("refreshSecret"), expRefresh); 
			
			//将请求秘钥保存到缓存
			ret=datas.getString("requestSecret");
			int expRequest = 60*10;//15分钟过期，提前5分钟更新
//			int expRequest = 60;//15分钟过期，提前2分钟更新  测试功能用
			log.info("key="+requestSecret+",ret="
	    		 +ret+",exp="+expRequest);
			cb.save(requestSecret, ret, expRequest);
			}

		return ret;
    }
    
}
