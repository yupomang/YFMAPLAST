package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.RSP.bean.BaseBean;
import com.yondervision.yfmap.RSP.util.PersistentTool;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.FtpUtil_DownFiles;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.Base64;
import com.yondervision.yfmap.common.security.Base64Encoder;
import com.yondervision.yfmap.form.AppApi01050Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.haikou.AppApi01024Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * @author LFX
 * 业务办理-显示业务列表文件
 */
public class Handle01052_00089800 implements CtrlHandleInter {
	Logger log = Logger.getLogger("YFMAP");
	
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap)
			throws CenterRuntimeException, Exception {
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01050Form form = (AppApi01050Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01051 start.");
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		log.debug("appApi01024 form:"+form); 
		/* 模拟返回开始  */
		AppApi01024Result app01024Result= new AppApi01024Result();
		//实例号
		 //String instance = form.getInstance();
		 String instance = form.getSeqno();
		 form.setSeqno("-"+instance);
		 //身份证号
	     String bodyCardNumber = form.getBodyCardNumber();
	     
	     //材料类型
	     String materCode = form.getMaterCode();
	     HashMap fileByte = new HashMap();
		if(Constants.method_BSP.equals(send)){
			String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();			
			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());			
			HashMap map = BeanUtil.transBean2Map(form);
			HashMap resultMap = new HashMap();
			PersistentTool.conDB();
		     int maxnumber = 0;
		     String sql1 = "select * from av054 where instanceid = '" +
		    		 form.getSeqno() +
		     		"' and scankey_pat= '" +
		     		form.getBodyCardNumber() +
		     		"' and matercode = '" +
		     		form.getMaterCode() +
		     		"' order by seqno desc ";
		     System.out.println(sql1);
		     ResultSet rs = PersistentTool.excuteQuery(sql1);	
			    if(rs!=null)
			    {
			    	//maxnumber = Integer.parseInt(rs.getString("seqno"));
			    	String sql2 = "delete from av054 where instanceid = '-" +
				    		 form.getSeqno() +
				     		"' and scankey_pat= '" +
				     		form.getBodyCardNumber() +
				     		"' and matercode = '" +
				     		form.getMaterCode()+"'" ;
					    PersistentTool.excuteDel(sql2);
			    }else
			    {
			    	//maxnumber = 0;
			    }
			String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
			String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();
			Calendar tranTime = Calendar.getInstance();
		     int year =  tranTime.get(Calendar.YEAR);
			 String month = (tranTime.get(Calendar.MONTH)+1)+"";
			 String day = tranTime.get(Calendar.DAY_OF_MONTH)+"";
			 month = month.length()==1?"0"+month:month;
			 day = day.length()==1?"0"+day:day;
			 
	        //path
			 String routePath= "00000000"+File.separator+year+"-"+month+File.separator+day+File.separator+"-"+instance+File.separator+materCode+File.separator;
			//第1次通讯，获取材料
			 String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HQCL.txt", map, req);
			 //String xml = MessageCtrMain.encapsulatedPackets(msgType, "D:\\tomcat\\webapps\\ydmi_csp\\WEB-INF\\classes\\com\\yondervision\\yfmap\\config\\messagetemp\\00089800/BSP_REQ_HQCL.txt", map, req);
			log.debug("发往中心报文-获取材料："+xml);
			
			// 通信
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "193012");
			log.debug("中心返回报文-获取材料："+rexml);
			
			HashMap resultMap8 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HQCL.txt", rexml, req);
			//HashMap resultMap8 = MessageCtrMain.analysisPacket(msgType,"D:\\tomcat\\webapps\\ydmi_csp\\WEB-INF\\classes\\com\\yondervision\\yfmap\\config\\messagetemp\\00089800\\BSP_REP_HQCL.txt", rexml, req);
			if(!"0".equals(resultMap8.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap8.get("recode"));
				modelMap.put("msg", resultMap8.get("msg"));
				log.error("中心返回报文 状态recode :"+resultMap8.get("recode")+"  ,  描述msg : "+resultMap8.get("msg"));
				return modelMap;
			}
			String loadPath = PropertiesReader.getProperty("ftp.properties", "ftp"+form.getCenterId()+"_batch_upfile_path");
			FtpUtil_DownFiles fd = new FtpUtil_DownFiles("bsp"+form.getCenterId());
			FTPClient ftpClient = new FTPClient();
			fd.connectServer(ftpClient,fd.getUrl(), fd.getUsername(), fd.getPassword(),fd.getPort());
			String filelist = resultMap8.get("filelist").toString();
			String[] files = filelist.split(";");
			String localPath = fd.getLocalPath();
			if(!CommonUtil.isEmpty(filelist))
			{
				if(files.length>=1)
				{
					for(String filename:files)
					{
						boolean success = fd.ftpDownFiles(ftpClient,filename,loadPath+File.separator+routePath+File.separator,localPath); 
						//boolean success = fd.ftpDownFiles(ftpClient,filename,"/was/workflow/logs/edocfile/00000000/2017-04/25/-7695/108/",localPath); 
						log.debug("文件下载地址:" +loadPath+File.separator+routePath+File.separator);
						log.debug("文件名称：" +files+"=============filename:"+filename);
						log.debug("文件下载状态：" +success);
						System.out.println(success);
						
						File f= new File(localPath+File.separator+filename) ;  // 声明File对象
						
						
						ByteArrayOutputStream output = new ByteArrayOutputStream();
	                    //以写字节的方式写文件   
	                    byte[] buffer = new byte[1024 * 1024];
	                    byte[] img = null; 
	            		int bytesum = 0;
	            		int byteread = 0;
	            		 // 拿到上传文件的输入流   
	            		InputStream stream = new FileInputStream(f);
	            		while ((byteread = stream.read(buffer)) != -1) {
	            			bytesum += byteread;
	            			output.write(buffer, 0, byteread);
	            		}
	            		img = output.toByteArray();
	            		String file = Base64.encode(img);
	            		output.close();
	            		stream.close(); 
				        fileByte.put("filename@"+filename, URLEncoder.encode(file));
				        //String t = URLDecoder.decode(fileByte.get("filename@"+filename).toString());
				        //System.out.println(t.getBytes("UTF-8"));
//				        String t = URLDecoder.decode(fileByte.get("filename@"+filename).toString());
//			            byte bfile[] = Base64.decode(t);
//				        System.out.println(t);
//						File file1 = new File("e:/"+filename);   
//			            FileOutputStream fstream = new FileOutputStream(file1);   
//			           // BufferedOutputStream stream = new BufferedOutputStream(fstream);   
//			            fstream.write(bfile);  
//			            fstream.flush();
//			            fstream.close();
				        //fileByte.put("filename_"+filename, t);
//				        File file = new File("d:/"+filename);   
//			            FileOutputStream fstream = new FileOutputStream(file);   
//			            BufferedOutputStream stream = new BufferedOutputStream(fstream);   
//			            stream.write(data);  
//			            stream.close();
//			            fstream.close();
				        Date date = new Date();
				        //存数据库
			    		BaseBean bean = new BaseBean();
			    		bean.setTableName("av054");
			    		bean.put("instanceid","-"+instance);
			    		bean.put("scankey_pat",bodyCardNumber);
			    		bean.put("matercode",materCode);
		    			bean.put("seqno",maxnumber+1);
			    		bean.put("filename",routePath+filename);
			    		bean.put("transdate",getSystemDate(date, "yyyy-MM-dd"));
			    		bean.put("timestamp",getSystemDate(date, "yyyy-MM-dd HH:mm:ss"));
			    		String sql = PersistentTool.getAddSQL(bean);
			    		System.out.println("sql>>>:"+sql);
			    		// 将信息插入数据表中
			    		
			    		PersistentTool.executeUpdatePre(sql, bean);
			    		maxnumber++;
					}
				}
			}
			BeanUtil.transMap2Bean(resultMap, app01024Result);
		}
//		List<TitleInfoBean> listfile = new ArrayList<TitleInfoBean>();
//		Iterator<Map.Entry<String, String>> it = fileByte.entrySet().iterator();
//		 while (it.hasNext()) {
//			 Map.Entry<String, String> entry = it.next();
//			 TitleInfoBean t = new TitleInfoBean();
//			 t.setTitle(entry.getKey().toString());
//			 t.setInfo(entry.getValue());
//			 listfile.add(t);
//		 }
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
//		modelMap.put("filenames", listfile);
		modelMap.put("filenames", fileByte);
		//modelMap.put("result", app01024Result.getActmp1024());
//		modelMap.put("detail", jsonObject);
		log.info(Constants.LOG_HEAD+"appApi01051 end.");
		PersistentTool.closeDB();
		return modelMap;
	} 
	
	/**
	 * 将指定时间转化成固定格式时间表示
	 * @param date
	 * @param format
	 * @return
	 */
	public static String getSystemDate(Date date,String format) {
		SimpleDateFormat formatter = new SimpleDateFormat(format);
		return formatter.format(date);
	} 
	public static void main(String[] args){
//		AppApi01050Form test = new AppApi01050Form();
//		test.setCenterId("00089800");
//		test.setMaterCode("108");
//		test.setBodyCardNumber("370213198910063629");
//		test.setSeqno("7695");
//		//test.setFlowid("31");
//		ModelMap modelMap = new ModelMap();
//		Handle01052_00089800 temp = new Handle01052_00089800();
//		try {
//			temp.action(test, modelMap);
//		} catch (CenterRuntimeException e) {
//			e.printStackTrace();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
		String filelist="";
		String[] files = filelist.split(";");
		System.out.println(files.length);
		System.out.println(CommonUtil.isEmpty(filelist));
	} 
}
