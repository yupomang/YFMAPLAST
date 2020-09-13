package com.yondervision.yfmap.controller;

import java.util.HashMap;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import com.yondervision.yfmap.dto.CMi401;
import com.yondervision.yfmap.form.AppApi50001Form;
import com.yondervision.yfmap.handle.CtrlHandleInter1;
import com.yondervision.yfmap.service.impl.AppApi401ServiceImpl;
import com.yondervision.yfmap.util.CommonUtil;

@Controller
public class AppApi302Contorller {

	/**
	 * 定制消息单笔
	 * @param form
	 * @param modelMap
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
     @RequestMapping("/appapi30290.{ext}")
     public String appapi30290(CMi401 form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
//	   System.out.println("【单笔定制消息发送接收上传参】"+CommonUtil.getStringParams(form));
// 	   isNull(form, modelMap);
	   form.setMsgsource("30");
	 
	   System.out.println("【单笔定制消息发送接收上传参centerid】"+form.getCenterid());
	   System.out.println("【单笔定制消息发送接收上传参tel】"+form.getTel());
	   System.out.println("【单笔定制消息发送接收上传参detail】"+form.getDetail());
	   System.out.println("【单笔定制消息发送接收上传参tsmsg】"+form.getTsmsg());
 	   AppApi401ServiceImpl appApi401Service=new AppApi401ServiceImpl();
 	   if(CommonUtil.isEmpty(form.getTitle())){
 		   form.setTitle("自定义消息");
 	   }
 	   if(CommonUtil.isEmpty(form.getDetail())){
		   form.setTitle("自定义消息");
	   }
	   request.setAttribute("centerId", form.getCenterid());
	   HashMap result = new HashMap();
	   try{			
		   result = appApi401Service.acction(form, modelMap, form.getCenterid(), form.getSendseqno());
	   }catch(Exception e){
		   modelMap.clear();
		   modelMap.put("recode", "999999");
		   modelMap.put("msg", "单笔定制消息发送异常");
		   modelMap.put("miSeqno", "");
		   return "";
	   }
	   modelMap.clear();
	   modelMap.put("recode", "000000");
	   modelMap.put("msg", "处理成功");
	   modelMap.put("miSeqno", result.get("miSeqno"));
	   return "";
    }	

/**
 * 定制消息批量
 * @param form
 * @param modelMap
 * @param request
 * @param response
 * @return
 * @throws Exception
 */
//    @RequestMapping("/yfmapapi3002.{ext}")
//    public String yfmapapi3002(CMi401 form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
//	  Logger log = Logger.getLogger("YFMAP");
//		
//	  //判断是否为空
//	  isNull(form, modelMap);	  
//	  
//	   request.setAttribute("centerid", form.getCenterid());
//	   	   
//	   Gson gsonbacth = new Gson();
//	   String msg = gsonbacth.toJson(form);
//	   int seqno = 0;	   
//	   YfThread ybmapThread = new YfThread(); 
//  	   ybmapThread.setSeqno(seqno);
//  	   ybmapThread.setMsg(msg);
//  	   Thread thread = new Thread(ybmapThread); 
//  	   thread.start(); 				   		
//		      
//  	   
//	   modelMap.clear();
//	   modelMap.put("recode", "000000");
//	   modelMap.put("msg", "处理成功");
//	   modelMap.put("miSeqno", seqno);
//	   return msg;
//   }
   
     /**
      * 模板消息单笔
      * @param form
      * @param modelMap
      * @param request
      * @param response
      * @return
      * @throws Exception 
      */
     @RequestMapping("/appapi30291.{ext}")
  	public String appapi30291(CMi401 form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
 	   System.out.println("-----------单笔模板消息YFMAP推送开始11111");
 	   System.out.println("-----------单笔模板消息form.getCenterid() : "+form.getCenterid()+"     ,CommonUtil.isEmpty(form.getCenterid()) : "+CommonUtil.isEmpty(form.getCenterid()));
 	   if(CommonUtil.isEmpty(form.getCenterid())){
 		   form.setMsgsource("30");
 		   form.setCenterid("00057400");
 		   System.out.println("-----------单笔模板消息YFMAP推送开始");
 		   System.out.println("模板等推送内容 form.getElements()："+form.getElements());
 		   form.setElements(new String(form.getElements().getBytes(),"GBK")); 
 		   System.out.println("模板等推送内容 form.getElements()："+form.getElements());
 		   System.out.println("模板等推送内容form.getPhone()："+form.getPhone());
 		   
 		   form.setTheme(form.getSmssource());//主题
 		   form.setTsmsg(form.getElements());//发送要素
 		   form.setTiming(CommonUtil.isEmpty(form.getSendlx())?"0":form.getSendlx());//发送类型0-实时发送 1-定时发送
 		   form.setDsdate(form.getSendtime());
 		   form.setTel(form.getPhone());
 		   form.setSendseqno(CommonUtil.getSystemDateNumOnly());
 	   }else{
 		   String[] trs = form.getTsmsg().split(";");
 		   StringBuffer trsTel = new StringBuffer();
 		   for(int i=0;i<trs.length;i++){
 				if(i==2){
 					trsTel.append(form.getTel()+";"+trs[i]+";");
 				}else{
 					trsTel.append(trs[i]+";");
 				}
 		   }
 		   form.setTsmsg(trsTel.toString());
 		   System.out.println("【单笔模板消息发送】"+CommonUtil.getStringParams(form));
 	   }
 	   
 	   
 	   request.setAttribute("centerId", form.getCenterid());
 	   
 	   isNull(form, modelMap); 
 	   HashMap result = new HashMap();
 	   System.out.println("-----------单笔模板消息YFMAP推送开始11111");
 	   AppApi401ServiceImpl appApi401Service=new AppApi401ServiceImpl();
 	   try{	   		
 		   result = appApi401Service.acction2(form, modelMap, form.getCenterid(), form.getSendseqno());	
 		   if(!CommonUtil.isEmpty(result.get("recode"))){
 			   modelMap.clear();
 			   modelMap.put("recode", result.get("recode"));
 			   modelMap.put("msg", "处理成功");
 			   modelMap.put("miSeqno", result.get("miSeqno"));
 		   }else{
 			   modelMap.clear();
 			   modelMap.put("recode", "999999");
 			   modelMap.put("msg", "发送失败");
 			   modelMap.put("miSeqno", result.get("miSeqno"));
 		   }
 	   }catch(Exception e){
 		   modelMap.clear();
 		   modelMap.put("recode", "999998");
 		   modelMap.put("remsg", "发送出现异常");
 		   modelMap.put("miSeqno", "");
 		   return "";
 	   }
 	  
 	   return "";
     }	


    /**
     * 模板消息批量
     * @param form
     * @param modelMap
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping("/appapi30292.{ext}")
    public String appapi30292(CMi401 form,  ModelMap modelMap,  HttpServletRequest request, HttpServletResponse response) throws Exception{
       if(CommonUtil.isEmpty(form.getCenterid())){
    	   form.setMsgsource("30");
     	   form.setCenterid("00087100");
       }else{
    	   System.out.println("【批量模板消息发送】"+CommonUtil.getStringParams(form));
       }
      
 	   System.out.println("-----------批量模板消息YFMAP推送开始");
 	   System.out.println("批量模板等推送内容 form.getFilename()："+form.getFilename());
// 	   System.out.println("批量模板等推送内容form.getMsgsource()："+form.getMsgsource());
 	   request.setAttribute("centerId", form.getCenterid());
		
// 	  SMSSOURCE\tPHONENUMBER\tELEMENTS\tSENDTYPE\tSENDTIME\tISPFLAG\n
// 	  SMSSOURCE：模板代码
// 	  PHONENUMBER：手机号码
// 	  ELEMENTS：发送要素
// 	  SENDTYPE：发送类型0-实时发送 1-定时发送
// 	  SENDTIME：发送时间，日期格式2016-10-01 08:10:00，若为实时发送时间为空
 	   
// 	   form.setTheme(form.getSmssource());//主题
//	   form.setTsmsg(form.getElements());//发送要素
//	   form.setTiming(CommonUtil.isEmpty(form.getSendlx())?"0":form.getSendlx());//发送类型0-实时发送 1-定时发送
//	   form.setDsdate(form.getSendtime());
//	   form.setTel(form.getPhone());
	   form.setSendseqno(CommonUtil.getSystemDateNumOnly());
	  
	   request.setAttribute("centerid", form.getCenterid());
       if(CommonUtil.isEmpty(form.getFilename())){
    	  modelMap.clear();
    	  modelMap.put("recode", "999999");
    	  modelMap.put("msg", "filename数据为空");
    	  modelMap.put("filename", "filename");
    	  System.out.println("filename数据为空");
    	  return "";
       }
	      
	   int seqno = 0;
	   		
	   YfmapThread yfThread1 = new YfmapThread(); 
	   yfThread1.setSendseqno(form.getSendseqno());
	   yfThread1.setCenterid(form.getCenterid());
	   yfThread1.setFilename(form.getFilename());
  	   Thread thread = new Thread(yfThread1);       			
  	   thread.start();
	   
	   modelMap.clear();
	   modelMap.put("recode", "000000");
	   modelMap.put("msg", "处理成功");
//	   modelMap.put("miSeqno", seqno);
	   return "";
   }
    
    @RequestMapping("/appapi30289.{ext}")
	public String appapi30289(AppApi50001Form form, ModelMap modelMap,HttpServletRequest request, HttpServletResponse response) throws Exception{

		CtrlHandleInter1 business = (CtrlHandleInter1) Class.forName("com.yondervision.yfmap.handle.handleImpl.handle"+form.getCenterId()+".Handle50022_"+form.getCenterId()).newInstance();
		business.action(form, modelMap, request, response);	
		
		return "";
	}
        
    public ModelMap isNull(CMi401 form,ModelMap modelMap)
    {
    	 if(CommonUtil.isEmpty(form.getCenterid())){
 		   modelMap.clear();
 		   modelMap.put("recode", "999999");
 		   modelMap.put("msg", "centerid数据为空");
    	 }
         if(CommonUtil.isEmpty(form.getTheme())){
 		   modelMap.clear();
 		   modelMap.put("recode", "999999");
 		   modelMap.put("msg", "theme数据为空");
         }
         if(CommonUtil.isEmpty(form.getTsmsg())){
 		   modelMap.clear();
 		   modelMap.put("recode", "999999");
 		   modelMap.put("msg", "数据为空");
 		   modelMap.put("tsmsg", "tsmsg");
         }	
         if(CommonUtil.isEmpty(form.getMsgsource())){
 		   modelMap.clear();
 		   modelMap.put("recode", "999999");
 		   modelMap.put("msg", "msgsource数据为空");
         }
         return modelMap;	
    }

        
    public class YfmapThread implements Runnable 
    { 
       private String filename;
	   private String sendseqno;
	   private String centerid;
	   
	   public void run() 
	   {    		   
		   try {
			   AppApi401ServiceImpl appApi401Service=new AppApi401ServiceImpl();
			   System.out.println("start#########【开始调用推送消息推送】#########");
			   appApi401Service.acction3(centerid ,filename, sendseqno);   
			   System.out.println("end#########【开始调用推送消息推送】#########");
		   } catch (Exception e) {
			   e.printStackTrace();
		   }
		   int sum = 0;
	  }
	   
		public String getCenterid() {
			return centerid;
		}
		public void setCenterid(String centerid) {
			this.centerid = centerid;
		}
		public String getFilename() {
			return filename;
		}
		public String getSendseqno() {
			return sendseqno;
		}
		public void setFilename(String filename) {
			this.filename = filename;
		}
		public void setSendseqno(String sendseqno) {
			this.sendseqno = sendseqno;
		}
   }
        
}
