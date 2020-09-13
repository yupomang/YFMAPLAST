package com.yondervision.yfmap.handle.handleImpl.handle00089800;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.ui.ModelMap;

import com.yondervision.yfmap.RSP.ApplyPP;
import com.yondervision.yfmap.RSP.server.RspServerUtil;
import com.yondervision.yfmap.RSP.util.PersistentTool;
import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.FtpUtil;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.mess.CommunicateUtil;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01036Form;
import com.yondervision.yfmap.form.AppApiCommonForm;
import com.yondervision.yfmap.handle.CtrlHandleInter;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;
import com.yondervision.yfmap.result.TitleInfoBean;
import com.yondervision.yfmap.result.haikou.AppApi00201Result;
import com.yondervision.yfmap.result.haikou.AppApi0104601Result;
import com.yondervision.yfmap.result.haikou.AppApi01046Result;
import com.yondervision.yfmap.util.BeanUtil;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.JavaBeanUtil;
import com.yondervision.yfmap.util.PropertiesReader;
/**
 * 公积金代扣协议维护查询
 * @author LFX
 *
 */
public class Handle01046_00089800 implements CtrlHandleInter {
	
	Logger log = Logger.getLogger("YFMAP");
	
	private static final ResourceBundle ReadProperties;		
	static{
		ReadProperties = ResourceBundle.getBundle("ftp");
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public ModelMap action(AppApiCommonForm form1, ModelMap modelMap) throws CenterRuntimeException,Exception{
		String PROPERTIES_FILE_NAME = "properties.properties";
		AppApi01036Form form = (AppApi01036Form)form1;
		log.info(Constants.LOG_HEAD+"appApi01046 start.");
		log.debug("YFMAP 公积金查询 form 参数:"+form);
		AppApi01046Result app01046Result = new AppApi01046Result(); 
		List<AppApi0104601Result> fileList = new ArrayList<AppApi0104601Result>();
		List<AppApi0104601Result> fileList_empty = new ArrayList<AppApi0104601Result>();
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
		
		if(Constants.method_BSP.equals(send)){

			form.setKey(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Key"+form.getCenterId()).trim());
			form.setSendDate(CommonUtil.getDate());
			String req = form.getUserId()+CommonUtil.getSystemDateNumOnly();
			form.setSendSeqno(req);
			form.setSendTime(CommonUtil.getTime());
			form.setType(PropertiesReader.getProperty(PROPERTIES_FILE_NAME, "appapi010Type"+form.getCenterId()).trim());	
			HashMap map = BeanUtil.transBean2Map(form);
			
			
			/* 第1次通信   查询贷款合同号 */
			String xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_DKHTH.txt", map, req);
			log.debug("发往中心报文-查询贷款合同号："+xml);
			
			// 通信
			String rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "129124");
			log.debug("中心返回报文-查询贷款合同号 ："+rexml);
			
			HashMap resultMap1 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_DKHTH.txt", rexml, req);
			if(!"0".equals(resultMap1.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap1.get("recode"));
				modelMap.put("msg", resultMap1.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap1.get("recode")+"  ,  描述msg : " + resultMap1.get("msg"));
				return modelMap;
			}
			
			if(CommonUtil.isEmpty(resultMap1.get("loanaccnum"))){
				modelMap.clear();
				modelMap.put("recode", "000001");
				modelMap.put("msg", "无公积金贷款记录，无法办理此业务！");
				log.error("中心返回报文 状态recode :000001  ,  描述msg : 交易129124，未查询到借款代码。");
				return modelMap;
			}
			// 借款合同号，第一次通信后查询得到
			map.put("loancontrcode", resultMap1.get("loanaccnum"));	
			
			/* 第2次通信   查询合同代扣信息 */
			//TODO flag上送值确定
			map.put("flag", "1");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HTDKCX.txt", map, req);
			log.debug("发往中心报文-查询合同代扣信息："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128061");
			log.debug("中心返回报文-查询合同代扣信息："+rexml);
			
			HashMap resultMap2 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HTDKCX.txt", rexml, req);
			if(!"0".equals(resultMap2.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap2.get("recode"));
				modelMap.put("msg", resultMap2.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap2.get("recode")+"  ,  描述msg : " + resultMap2.get("msg"));
				return modelMap;
			}
			
			map.put("flag", "2");
			xml = MessageCtrMain.encapsulatedPackets(msgType, CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"/BSP_REQ_HTDKCX.txt", map, req);
			log.debug("发往中心报文-查询合同代扣信息："+xml);
			
			// 通信
			rexml = CommunicateUtil.sendMessage(send, ip, Integer.parseInt(port), xml, "128061");
			log.debug("中心返回报文-查询合同代扣信息："+rexml);
			
			HashMap resultMap3 = MessageCtrMain.analysisPacket(msgType,CommonUtil.getFullURL(Constants.msgPath+form.getCenterId())+"BSP_REP_HTDKCX.txt", rexml, req);
			if(!"0".equals(resultMap3.get("recode"))){				
				modelMap.clear();
				modelMap.put("recode", resultMap3.get("recode"));
				modelMap.put("msg", resultMap3.get("msg"));
				log.error("中心返回报文 状态recode :" + resultMap3.get("recode")+"  ,  描述msg : " + resultMap3.get("msg"));
				return modelMap;
			}
			if(CommonUtil.isEmpty(form.getApplyid()))
			{
				//下传filename值
				String filename = (String)resultMap3.get("downfilename");
				if(!CommonUtil.isEmpty(filename)){
					
				
					FtpUtil f=new FtpUtil("bsp"+form.getCenterId());
				    f.getFileByServer("/"+filename);
				    File filerename = new File(ReadProperties.getString("bsp_local_path")+filename);
				    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+filename));
				    //========================================================================== 
				    File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+filename);
					
					
					System.out.println("代扣信息文件："+filename);
					if(file!=null){
						FileInputStream ffis = new FileInputStream(file);
						InputStreamReader isr = new InputStreamReader(ffis, "GBK");
						BufferedReader breader = new BufferedReader(isr);
						String line = breader.readLine();
						
						while (line != null) {// 判断读取到的字符串是否不为空
							AppApi0104601Result temp = new AppApi0104601Result();
							try {
								//TODO 下传文件解析
								String[] values = line.split("~");
								log.debug("读取文件  ："+line);			
								// 带看完文件补充
								//姓名 证件类型 证件号 扣款人类型 协议签订类型  代扣顺序  空值  seq值
								//TODO 姓名
								temp.setAccname(values[0]);
								//TODO 证件类型 
								temp.setLinkmanphone(values[1]);
								//TODO 证件号
								temp.setAccnum(values[2]);
								//TODO 扣款人类型
								temp.setHostsernum(values[3]);
								//TODO 协议签订类型
								temp.setUnitaccname(values[4]);
								//TODO 代扣顺序
								temp.setMatename(values[5]);
								//TODO 空值
								temp.setWorkunit(values[6]);
								//TODO seq值
								temp.setInstance(values[7]);
								//只允许修改本人的代扣协议2017-07-10
//								if(form.getBodyCardNumber().equals(values[2]))
//								{
									fileList.add(temp);
//								}
								line = breader.readLine();
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
						breader.close();
						isr.close();
						ffis.close();
						file.delete();
						filerename.delete();
					}
				}else{
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "查询代扣人归集信息异常");
					return modelMap;
				}	
			}else
			{
				String filename ="";
				PersistentTool.conDB();
				ApplyPP app = new ApplyPP();
				ResultSet rs = app.getRS002FilePathByApplyid(form.getApplyid());
				if(rs!=null)
				{
					filename = rs.getString("filepath");
				}else
				{
					modelMap.clear();
					modelMap.put("recode", "999999");
					modelMap.put("msg", "获取批量文件失败-RS002");
					log.error("中心返回报文 状态recode :" + modelMap.get("recode")+"  ,  描述msg : " + modelMap.get("msg"));
					return modelMap;
				}
				PersistentTool.closeDB();
				FtpUtil f=new FtpUtil("bsp"+form.getCenterId(),"");
			    f.getFileByUploadServer("/"+filename);
			    File filerename = new File(ReadProperties.getString("bsp_local_path")+filename);
			    FileUtils.copyFile(filerename, new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+filename));
			    //========================================================================== 
			    File file = new File(ReadProperties.getString("bsp_local_path")+form.getSendDate()+"/"+filename);
				
				
				System.out.println("代扣信息文件："+filename);
				if(file!=null){
					FileInputStream ffis = new FileInputStream(file);
					InputStreamReader isr = new InputStreamReader(ffis, "GBK");
					BufferedReader breader = new BufferedReader(isr);
					String line = breader.readLine();
					int i =0 ;
					try {
					while (line != null) {// 判断读取到的字符串是否不为空
						if(i>0)
						{
							AppApi0104601Result temp = new AppApi0104601Result();
							
								//TODO 下传文件解析
								String[] values = line.split("	");
								log.debug("读取文件  ："+line);			
								// 带看完文件补充
								//姓名 证件类型 证件号 扣款人类型 协议签订类型  代扣顺序  空值  seq值
								//TODO 姓名
								temp.setAccname(values[0]);
								//TODO 证件类型 
								temp.setLinkmanphone(values[1]);
								//TODO 证件号
								temp.setAccnum(values[2]);
								//TODO 扣款人类型
								temp.setHostsernum(values[3]);
								//TODO 协议签订类型
								temp.setUnitaccname(values[4]);
								//TODO 代扣顺序
								temp.setMatename(values[5]);
								//TODO 空值
								temp.setWorkunit(values[6]);
								//TODO seq值
								temp.setInstance(values[7]);
								//只允许修改本人的代扣协议2017-07-10
//								if(form.getBodyCardNumber().equals(values[2]))
//								{
									fileList.add(temp);
//								}
							}
						
						line = breader.readLine();
						i++;
						
					}
					} catch (IOException e) {
						e.printStackTrace();
					}
					breader.close();
					isr.close();
					ffis.close();
					file.delete();
					filerename.delete();
				}
			}
			
			//放置空list、为公积金列表添加删除
			AppApi0104601Result temp = new AppApi0104601Result();
			//姓名 证件类型 证件号 扣款人类型 协议签订类型  代扣顺序  空值  seq值
			//TODO 姓名
			temp.setAccname("");
			//TODO 证件类型 
			temp.setLinkmanphone("");
			//TODO 证件号
			temp.setAccnum("");
			//TODO 扣款人类型
			temp.setHostsernum("");
			//TODO 协议签订类型
			temp.setUnitaccname("");
			//TODO 代扣顺序
			temp.setMatename("");
			//TODO 空值
			temp.setWorkunit("");
			//TODO seq值
			temp.setInstance("");
			fileList_empty.add(temp);
			
			//借款人合同编号
			resultMap.put("jkhtbh", resultMap2.get("jkhtbh"));
			//担保方式
			resultMap.put("dkdblx", resultMap2.get("dkdblx"));
			//贷款余额
			resultMap.put("dkye", resultMap2.get("dkye"));
			//合同贷款金额
			resultMap.put("htdkje", resultMap2.get("htdkje"));
			//终止日期
			resultMap.put("enddate", resultMap2.get("enddate"));
			//modelMap.put("app01046Result", app01046Result);
			BeanUtil.transMap2Bean(resultMap, app01046Result);
			
		}
		app01046Result.setJkrxm(form.getXingming());
		app01046Result.setJkrzjh(form.getBodyCardNumber());
		app01046Result.setDkdblx(PropertiesReader.getProperty("yingshe.properties","guarmode"+app01046Result.getDkdblx()+form.getCenterId()));
		
		List<TitleInfoBean> appapi00101Result = null;
		appapi00101Result = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01046"+form.getCenterId()+".result", app01046Result);
		Iterator<TitleInfoBean> it1 = appapi00101Result.iterator();
		while (it1.hasNext()) {
			TitleInfoBean titleInfoBean = (TitleInfoBean) it1.next();
			log.info("title="+titleInfoBean.getTitle()+"\tinfo="+titleInfoBean.getInfo());
		}
		
		//公积金人列表
		List<List<TitleInfoBean>> detail = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<fileList.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01046"+form.getCenterId()+".result01", fileList.get(i));	
			detail.add(result1);
		}
		
		List<List<TitleInfoBean>> fileList_emp = new ArrayList<List<TitleInfoBean>>();			
		for(int i=0;i<fileList_empty.size();i++){
			List<TitleInfoBean> result1 = new ArrayList<TitleInfoBean>();
			result1 = JavaBeanUtil.javaBeanToListTitleInfoBean("appapi01046"+form.getCenterId()+".result01", fileList_empty.get(i));	
			fileList_emp.add(result1);
		}
		
		String reasonStr = "0:无,1:借款人配偶,2:联合借款人配偶";
		List<TitleInfoBean> reasonlist = new ArrayList<TitleInfoBean>();
		String[] yhbm_temp = reasonStr.split(",");
		for(int i=0;i<yhbm_temp.length;i++)
		{
			TitleInfoBean t = new TitleInfoBean();
			String yhbm_t[] = yhbm_temp[i].split(":");
			if(yhbm_t.length>=0)t.setTitle(yhbm_t[0]);
			if(yhbm_t.length>=1)t.setInfo(yhbm_t[1]);
			reasonlist.add(t);
		}
		modelMap.clear();
		modelMap.put("recode", "000000");
		modelMap.put("msg", "成功");
		modelMap.put("result", appapi00101Result);
		//公积金代扣信息列表（从下传文件中取出）
		modelMap.put("fileResult", detail);
		modelMap.put("fileList_empty", fileList_emp);
		modelMap.put("seqno", RspServerUtil.getSEQ("INSTANCE_SEQ.nextval")+"");
		modelMap.put("reasonList", reasonlist);
		log.info(Constants.LOG_HEAD+"appApi01046 end.");
		
		return modelMap;
	
	}
}
