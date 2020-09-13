package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.RSP.ApplyPP;
import com.yondervision.yfmap.RSP.server.RspServerUtil;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01046Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 添加公积金代扣人并提交
 * @author LFX
 *
 */
public class Handle01047_00089800 implements CtrlHandleInter {
	
//	private static final ResourceBundle ReadProperties;		
//	static{
//		ReadProperties = ResourceBundle.getBundle("ftp");
//	}
	
	Logger log = Logger.getLogger("YFMAP");
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01046Form form = (AppApi01046Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01047 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);

//		if(CommonUtil.isEmpty(form.getBodyCardNumber()) || CommonUtil.isEmpty(form.getTqyy()) ){
//			modelMap.clear();
//			modelMap.put("recode", "999999");
//			modelMap.put("msg", "上传参数不可为空。");
//			log.error("上传参数不可为空。");
//			return modelMap;
//		}
		AES aes = new AES();
		form.setXingming(aes.decrypt(form.getXingming()));
		// 连接核心系统方式
		String send = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Send"+form.getCenterId()).trim();
		// 报文封装、解析方式
		String msgType = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010MsgType"+form.getCenterId()).trim();
		
		String ip = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_IP"+form.getCenterId()).trim();
		String port = PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "BSP_SOCKET_PORT"+form.getCenterId()).trim();

		
		// 结果map
		HashMap resultMap = new HashMap();
		int seq = Integer.parseInt(form.getSeqno());
		
		if(Constants.method_BSP.equals(send)){

			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());	
			HashMap map = BeanUtil.transBean2Map(form);
			
			
			
			HashMap resultMap1 = new HashMap();
			//写入文件内容
	        StringBuffer sb=new StringBuffer();
	        //姓名 证件类型 证件号 扣款人类型 协议签订类型  代扣顺序  空值  seq值
	        sb.append("ln999	ACCNAME	LINKMANPHONE	ACCNUM	HOSTSERNUM	UNITACCNAME	MATENAME	WORKUNIT	INSTANCE");
	        sb.append("\r\n");
			String fileName = "";
			//本地生成路径
	        String path=PropertiesReader.getProperty("ftp.properties","bsp_local_path");
	        //文件名称
	        fileName = PropertiesReader.getProperty("yingshe.properties","tableid"+form.getCenterId())+form.getIdcardNumber()+"_10"+CommonUtil.getSystemDateNumOnly()+".txt";
	        System.out.println(form.getDetails());
	        String jsonobj = form.getDetails();
	        String matename ="";//扣款顺序
	        String hostsernum ="";//扣款人类型
	        if(!CommonUtil.isEmpty(jsonobj))
	        {
			        JSONObject json = JSONObject.fromObject(jsonobj);
			        int size = Integer.parseInt(json.getString("size"));
			        for(int i=0;i<size;i++){
			        if(CommonUtil.isEmpty(json.getString(i+"_name"))||
			        		CommonUtil.isEmpty(json.getString(i+"_zjlx"))||
			        		CommonUtil.isEmpty(json.getString(i+"_zjhm"))||
			        		CommonUtil.isEmpty(json.getString(i+"_hostsernum"))||
			        		CommonUtil.isEmpty(json.getString(i+"_unitaccname"))||
			        		CommonUtil.isEmpty(json.getString(i+"_matename"))||
			        		CommonUtil.isEmpty(json.getString(i+"_yuanyin"))){
			        	modelMap.clear();
						modelMap.put("recode", "999999");
						modelMap.put("msg", "录入数据不可为空，请检查！");
			        	log.error("上传数据异常:第"+i+"项有字段为空");
			        	return modelMap;
			        }
			        if(hostsernum.equals(json.getString(i+"_hostsernum")))
			        {
			        	modelMap.clear();
						modelMap.put("recode", "999999");
						modelMap.put("msg", "只可以有一个主借款人，请重新选择！");
			        	log.error("上传数据异常:只可以有一个主借款人，请重新选择！");
			        	return modelMap;
			        }
			        String[] t = matename.split(",");
			        for(int j =0;j<t.length;j++)
			        {
			        	if(t[j].equals(json.getString(i+"_matename")))
			        	{
			        		modelMap.clear();
							modelMap.put("recode", "999999");
							modelMap.put("msg", "扣款人顺序不可重复，请重新选择！");
				        	log.error("上传数据异常:扣款人顺序不可重复，请重新选择！");
				        	return modelMap;
			        	}
			        }
			        HashMap map1 =new HashMap();
					/*第1次通信 查询代扣人归集信息*/
					//TODO 需确定上送的信息是借款人信息还是扣款人信息
					map1.put("flag", "1");
					map1.put("bodyCardNumber", json.getString(i+"_zjhm"));
					map1.put("xingming", json.getString(i+"_name"));
					String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKHKSSFX.txt", map1, req);
					log.debug("发往中心报文-查询代扣人归集信息："+xml);
					// 通信
					String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128111");
					log.debug("中心返回报文-查询代扣人归集信息："+rexml);
					
					resultMap1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHKSSFX.txt", rexml, req);
					if(!"0".equals(resultMap1.get("recode"))){				
						modelMap.clear();
						modelMap.put("recode", resultMap1.get("recode"));
						modelMap.put("msg", resultMap1.get("msg"));
						log.error("中心返回报文 状态recode :" + resultMap1.get("recode")+"  ,  描述msg : " + resultMap1.get("msg"));
						return modelMap;
					}
					String grzh = resultMap1.get("grzh").toString();
					if(CommonUtil.isEmpty(grzh)){
						modelMap.clear();
						modelMap.put("recode", "999999");
						modelMap.put("msg", "不存在个人公积金代扣信息，请重新录入！");
						log.error("中心返回报文 状态recode :999999  ,  描述msg : 不存在个人公积金代扣信息，请重新录入！");
						return modelMap;
					}
			        sb.append(json.getString(i+"_name"));//扣款人姓名
			        sb.append("	");
			        sb.append(json.getString(i+"_zjlx"));//扣款人证件类型
			        sb.append("	");
			        sb.append(json.getString(i+"_zjhm"));//扣款人证件号码
			        sb.append("	");
			        sb.append(json.getString(i+"_hostsernum"));//扣款人类型
			        sb.append("	");
			        sb.append(json.getString(i+"_unitaccname"));//协议类型
			        sb.append("	");
			        sb.append(json.getString(i+"_matename"));//扣款顺序
			        sb.append("	");
			        sb.append(json.getString(i+"_yuanyin"));//其他类型原因
			        sb.append("	");
			        sb.append(-seq);//seq值
			        sb.append("\r\n");
			        matename = matename + json.getString(i+"_matename")+",";
			        if("0".equals(json.getString(i+"_hostsernum")))hostsernum="0";
	        	}
	        }else
	        {
	        	modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "上传数据异常");
	        	log.error("上传数据异常:empty");
	        	return modelMap;
	        }
			/*生成文件*/
			/* 第2次通信   查询原有扣款人列表*/
			//TODO flag上送值确定
//			map.put("flag", "");
//			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HTDKCX.txt", map, req);
//			log.debug("发往中心报文-查询合同代扣信息："+xml);
//			
//			// 通信
//			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128061");
//			log.debug("中心返回报文-查询合同代扣信息："+rexml);
//			
//			HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HTDKCX.txt", rexml, req);
//			if(!"0".equals(resultMap2.get("recode"))){				
//				modelMap.clear();
//				modelMap.put("recode", resultMap2.get("recode"));
//				modelMap.put("msg", resultMap2.get("msg"));
//				log.error("中心返回报文 状态recode :" + resultMap2.get("recode")+"  ,  描述msg : " + resultMap2.get("msg"));
//				return modelMap;
//			}
			try
	        {
		        File file=new File(path+fileName);
		        if(!file.exists())
		            file.createNewFile();
		        FileOutputStream out=new FileOutputStream(file,false);        
		       // sb.append(sb.toString()+"\r\n");
		        out.write(sb.toString().getBytes("GBK"));
		        out.close();
		        
		        //TODO  上传 ftp---具体路径，用户名密码需确定
		        FtpUtil f=new FtpUtil("bsp"+form.getCenterId(),"/batch/");
				f.putFileToUploadServer(file);
				file.delete();
	        }
	        catch(IOException ex)
	        {
	        	modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "上传文件异常");
	        	log.error("公积金代扣协议维护提交--上传文件异常:"+ex.getMessage());
	        	return modelMap;
	        }catch(Exception e){
	        	modelMap.clear();
				modelMap.put("recode", "999999");
				modelMap.put("msg", "上传文件异常");
	        	log.error("公积金代扣协议维护提交--上传文件异常:"+e.getMessage());
	        	return modelMap;
	        }
			
			/* 第1次通信   查询贷款合同号 */
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKHTH.txt", map, req);
			log.debug("发往中心报文-查询贷款合同号："+xml);
			
			// 通信
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129124");
			log.debug("中心返回报文-查询贷款合同号 ："+rexml);
			
			HashMap resultMap3 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHTH.txt", rexml, req);
			if(!"0".equals(resultMap3.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap3.get("recode"));
				modelMap.put("msg", resultMap3.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap3.get("recode")+"  ,  描述msg : " + resultMap3.get("msg"));
				return modelMap;
			}
			
			if(CommonUtil.isEmpty(resultMap3.get("loanaccnum"))){
				modelMap.clear();
				modelMap.put("recode", "000001");
				modelMap.put("msg", "未查到合同代码！");
				log.error("中心返回报文 状态recode :000001  ,  描述msg : 交易129124，未查询到借款代码。");
				return modelMap;
			}
			// 借款合同号，第一次通信后查询得到
			map.put("jkhtbh", resultMap3.get("loanaccnum"));	
			
			/* 第2次通信   查询合同代扣信息 */
			//TODO flag上送值确定
			map.put("flag", "1");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HTDKCX.txt", map, req);
			log.debug("发往中心报文-查询合同代扣信息："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128061");
			log.debug("中心返回报文-查询合同代扣信息："+rexml);
			
			HashMap resultMap4 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HTDKCX.txt", rexml, req);
			if(!"0".equals(resultMap4.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap4.get("recode"));
				modelMap.put("msg", resultMap4.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap4.get("recode")+"  ,  描述msg : " + resultMap4.get("msg"));
				return modelMap;
			}
			
			
			/*第3次通信 将网厅代扣信息转入核心临时表*/
			map.put("upfilename", fileName);
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKZRHX.txt", map, req);
			log.debug("发往中心报文-将网厅代扣信息转入核心临时表："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128063");
			log.debug("中心返回报文-将网厅代扣信息转入核心临时表："+rexml);
			
			HashMap resultMap5 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKZRHX.txt", rexml, req);
			if(!"0".equals(resultMap5.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap5.get("recode"));
				modelMap.put("msg", resultMap5.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap5.get("recode")+"  ,  描述msg : " + resultMap5.get("msg"));
				return modelMap;
			}
			
			resultMap.putAll(resultMap1);
			resultMap.putAll(resultMap5);
			resultMap.putAll(resultMap4);
			resultMap.put("filePath", "batch/"+map.get("upfilename"));
			resultMap.put("upfilename", "batch/"+map.get("upfilename"));
			//经办银行
			resultMap.put("agentbankcode", resultMap3.get("agentbankcode"));
			//经办机构
			resultMap.put("instcode", resultMap3.get("instcode"));
		}
			System.out.println("===开始存数据库");
			
			ApplyPP applyPP = new ApplyPP();
			//TODO 若以下部分（resultMap1.get("XXX")）在每个存数据库内的变量都是一样的，则可直接写在ApplyPP中，以免代码太过冗余
			resultMap.put("_ACCNUM", resultMap.get("accnum"));
			//TODO 单位账号
			resultMap.put("_UNITACCNUM", "");
			resultMap.put("_UNITACCNAME", resultMap.get("dwmc"));
			resultMap.put("_ACCNAME", form.getXingming());
			resultMap.put("_DEPUTYIDCARDNUM", form.getBodyCardNumber());
			resultMap.put("_SENDOPERID", form.getBodyCardNumber());
			int seqno  = 0;
			if(!CommonUtil.isEmpty(form.getSeqno()))
			{
				seqno = Integer.parseInt(form.getSeqno());
			}else
			{
				seqno = RspServerUtil.getSEQ("INSTANCE_SEQ.nextval");
			}
			resultMap.put("_IS", -seqno);
			
			resultMap.put("instance", -seqno);
//			resultMap.put("_PORCNAME", PropertiesReader.getProperty("yingshe.properties","_PORCNAME47"+form.getCenterId()));
//			resultMap.put("_PROCID", PropertiesReader.getProperty("yingshe.properties","_PROCID47"+form.getCenterId()));
//			resultMap.put("stepid", PropertiesReader.getProperty("yingshe.properties","stepid47"+form.getCenterId()));
//			resultMap.put("flowid", PropertiesReader.getProperty("yingshe.properties","flowid47"+form.getCenterId()));
//			resultMap.put("counter", PropertiesReader.getProperty("yingshe.properties","counter47"+form.getCenterId()));
//			resultMap.put("selorgid", PropertiesReader.getProperty("yingshe.properties","selorgid47"+form.getCenterId()));
			try {
				String flag = "47"+form.getCenterId();
				HashMap<String,String> temp = applyPP.exec(resultMap,form.getApplyid(),flag);
				if("false".equals(temp.get("recode")))
				{
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", temp.get("msg"));
					log.info(Constants.LOG_HEAD+"appApi01047 end.");
					return modelMap;
				}
				modelMap.clear();
				modelMap.put("recode", "000000");
				modelMap.put("msg", "成功");
				modelMap.put("applayid",temp.get("msg"));
				log.info(Constants.LOG_HEAD+"appApi01047 end.");
				return modelMap;
			} catch (Exception e) {
				modelMap.clear();
				//TODO 存数据库异常错误码
				modelMap.put("recode", "999999");
				modelMap.put("msg", "对冲还贷协议签订存数据库异常");
				log.error("对冲还贷协议签订："+e.getMessage());
				log.info(Constants.LOG_HEAD+"appApi01047 end.");
				e.printStackTrace();
				return modelMap;
			}
		
	}
	public static void main(String[] args) {
		String t = "{\"detail\":{\"1_hostsernum\":\"9\",\"1_name\":\"郭亚玉\",\"0_yuanyin\":\"0\",\"0_name\":\"张兆华\",\"0_zjlx\":\"01\",\"1_zjhm\":\"370902197708263920\",\"1_unitaccname\":\"2\",\"0_matename\":\"1\",\"1_zjlx\":\"01\",\"0_hostsernum\":\"0\",\"1_yuanyin\":\"1\",\"0_unitaccname\":\"2\",\"0_zjhm\":\"460100195906180313\",\"1_matename\":\"2\"},\"size\":2}";
		JSONObject j = JSONObject.fromObject(t);
		System.out.println(j);
	}
}
