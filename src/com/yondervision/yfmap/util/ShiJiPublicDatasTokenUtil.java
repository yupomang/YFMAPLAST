package com.yondervision.yfmap.util;

import java.util.Date;
import org.apache.log4j.Logger;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.mess.SimpleHttpMessageUtil;
import com.yondervision.yfmap.common.security.EncryptionByMD5;
import com.yondervision.yfmap.util.couchbase.CouchBase;
import net.sf.json.JSONObject;

public class ShiJiPublicDatasTokenUtil {
	//宁波市数据接口共享平台
	public static  String getPublicDataSecretWithCouchBase(String centerId){		
		Logger log = Logger.getLogger("YFMAP");
		String ret=null;
		String centerId1 = "00057400";
		centerId = centerId1;
		String requestSecret="sjpd|requestSecret|"+centerId;

		log.info("requestSecret："+requestSecret);
		try {
			CouchBase cb=CouchBase.getInstance();
			//判断请求秘钥是否存在
			if(cb.get(requestSecret)==null){
				log.info("请求秘钥不存在");
				ret=getPublicDataSecretWithURL(centerId);
			}else{
				log.info("请求秘钥存在");
				ret=(String)cb.get(requestSecret);
			}
		} catch (Exception e) {
			log.info(e);
			e.printStackTrace();
			log.info("Exception ret："+ret);
			return ret;
		}
	  
		System.out.println("Request Secret:"+ret);  
		log.info("[-]getPublicDataSecretWithCouchBase ret="+ret);
		return ret;
	}

    public synchronized static String getPublicDataSecretWithURL(String centerId) throws Exception {
    	
    		Logger log = Logger.getLogger("YFMAP");
//			log.info("执行块睡觉前");
//        	Thread.sleep(20000);  //5秒
// 			log.info("执行块睡觉后");
			CouchBase cb=CouchBase.getInstance();
    		String ret=null;
    		String refreshSecret="sjpd|refreshSecret|"+centerId;
    		String requestSecret="sjpd|requestSecret|"+centerId;
    		String sjpublicDatasUrl = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
    				"sjpublicDatasUrl").trim();
    		String refreshUrl = sjpublicDatasUrl + "app/refreshTokenByKey.htm";
    		String requestUrl = sjpublicDatasUrl + "app/refreshTokenBySec.htm";
    		String appKey = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
    				"sjappKey").trim();
    		String appSecret = PropertiesReader.getProperty(Constants.PROPERTIES_FILE_NAME, 
    				"sjappSecret").trim();
			String result = "";
			SimpleHttpMessageUtil msm = new SimpleHttpMessageUtil();
			Date date = new Date();
			String requestTime = String.valueOf(date.getTime());
			System.out.println("requestTime:" + requestTime);
			String url = "";
			//判断刷新秘钥是否存在
			if(cb.get(refreshSecret)==null){
				log.info("刷新秘钥不存在");
				log.info("sjappKey："+appKey);
				log.info("sjappSecret："+appSecret);
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
		
		//判断如果签名错误，删除刷新秘钥，调用接口http:// 10.68.138.194/gateway/app/refreshTokenByKey.htm来获取刷新秘钥和请求秘钥
		JSONObject json = JSONObject.fromObject(result);
		String msg=json.getString("msg");
		String code=json.getString("code");
		log.info("获取返回结果code："+code);
		if("03".equals(code)){
			cb.delete("sjpd|refreshSecret|00057400");
			log.info("code：03，签名错误，重头来过");
			log.info("sjappKey："+appKey);
			log.info("sjappSecret："+appSecret);
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
			//int expRefresh = 120;//48小时过期，提前一小时更新 测试功能用
			log.info("key="+refreshSecret+",ret="+datas.getString("refreshSecret")+",exp="+expRefresh);
			cb.save(refreshSecret, datas.getString("refreshSecret"), expRefresh); 
			
			//将请求秘钥保存到缓存
			ret=datas.getString("requestSecret");
			int expRequest = 60*10;//15分钟过期，提前5分钟更新
			//int expRequest = 60;//15分钟过期，提前2分钟更新  测试功能用
			log.info("key="+requestSecret+",ret="+ret+",exp="+expRequest);
			cb.save(requestSecret, ret, expRequest);
			}
		return ret;
    }

}
