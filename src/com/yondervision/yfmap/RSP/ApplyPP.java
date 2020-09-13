package com.yondervision.yfmap.RSP;

/**
 * 文件名:ApplyPP.java
 * 作者: 张雷
 * 日期:2013-08-14
 * 功能: 申报标签，主要用于发送申报
 * 
 * 修改：1#		20140604	武丹		整体格局修改，共通性代码提出做成私有方法；增加个人和开发商发送申报功能
 *       4#		2016-11-22	张辉		发送申报时，将过期日记录到表中
 * 
 * */

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.ui.ModelMap;

import com.ydyd.pool.DataPool;
import com.yondervision.yfmap.RSP.bean.BaseBean;
import com.yondervision.yfmap.RSP.bean.Svr802bean;
import com.yondervision.yfmap.RSP.server.RspServerUtil;
import com.yondervision.yfmap.RSP.util.PersistentTool;
import com.yondervision.yfmap.RSP.util.RspClientUtil;
import com.yondervision.yfmap.RSP.util.RspResult;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.security.AES;
import com.yondervision.yfmap.form.AppApi01020Form;
import com.yondervision.yfmap.form.AppApi01050Form;
import com.yondervision.yfmap.form.AppApi40102Form;
import com.yondervision.yfmap.handle.handleImpl.handle00089800.Handle01023_1_00089800;
import com.yondervision.yfmap.handle.handleImpl.handle00089800.Handle01052_00089800;
import com.yondervision.yfmap.handle.handleImpl.handle00089800.Handle40109_00089800;
import com.yondervision.yfmap.result.haikou.AppApi01048Result;
import com.yondervision.yfmap.util.CommonUtil;
import com.yondervision.yfmap.util.PropertiesReader;

public class ApplyPP {
	DataPool pool;
	private static final String PROPERTIES_FILE_NAME = "ftp.properties";
	private static final String KEY = "bsp00089800_ftp_upload_path";
	public HashMap<String, String> exec(HashMap map, String applyid,String flag) {
		HashMap<String, String> hm = new HashMap<String, String>();
		hm.put("recode", "true");
		hm.put("msg", "成功");
		pool = new DataPool(map);
		commonDataPool(map,flag);
		// 申报人证件号
		String certno = (String) map.get("_DEPUTYIDCARDNUM");
		if (pool.get("applyid") != null) {
			applyid = pool.get("applyid") + "";
		}
		// String modtype = node.getAttributeValue("modtype").trim();
		String modtype = "0";

		if (CommonUtil.isEmpty(applyid)) {// 判断是否有参数applyid：
			String procid = pool.getString("_PROCID");
			if (!"30003001".equals(procid) && !"30003002".equals(procid)) {
				String checkRtn = checkApply(certno);
				if (!CommonUtil.isEmpty(checkRtn)) {
					// 返回失败
					hm.put("recode", "false");
					hm.put("msg", checkRtn);
					return hm;
				}
			}
			/**
			 * 发送申报
			 * */
			BaseBean bean = new BaseBean(); // 定义BaseBean来接收增加申报的数据bean
			bean.setTableName("RSP002");
			int applyid1 = new Integer(RspServerUtil.getKey("RSP002"));
			// user.setAttribute("msg_applyid", applyid1);
			try {
				PersistentTool.conDB();
				// 插入申报表
				insertRSP002(pool, applyid1);
				// 插入明细表（状态：未处理）
				insertRSP007(applyid1, "0", "");
				PersistentTool.closeDB();
				hm.put("recode", "true");
				hm.put("msg", applyid1 + "");
				return hm;
			} catch (Exception e) {
				e.printStackTrace();
				if (PersistentTool.con != null)
					PersistentTool.closeDB();
			}
		} else {

			/**
			 * 功能: 修改申报
			 * 
			 * */
			PersistentTool.conDB();

			/**
			 * 功能: 修改申报前，如果申报为予办理的，要先撤回
			 * 
			 * */
			String sql = "select state,modtype from rsp002 where applyid="
					+ applyid + "";
			try {
				ResultSet rs = PersistentTool.excuteQuery(sql);
				if (rs != null) {
					if (rs.getRow() > 0) {
						// 已经撤回的申报做修改的时候不需要再做撤回操作
						if ("0".equals(rs.getString("modtype"))) {
							if (rs.getString("state").equals("1")) {
								hm.put("recode", "false");
								hm.put("msg", "申报号为：" + applyid
										+ "撤回申报,申报状态异常1，请联系技术员！");
								return hm;
							}
						}
						if (rs.getString("state").equals("4")) {
							hm.put("recode", "false");
							hm.put("msg", "申报号为：" + applyid
									+ "撤回申报,申报状态异常1，已经受理的申报不能撤回！");
							return hm;
						}
						// 更新申报表
						if (!rs.getString("state").equals("3")) {
							BaseBean bean1 = new BaseBean();
							bean1.setTableName("RSP002"); // 设置要操作的数据表
							bean1.setWhereClause("applyid=" + applyid + ""); // 设置撤回操作的where条件
							bean1.put("state", "3");
							String sql1 = PersistentTool.getUpdateSQL(bean1);
							System.out.println("&&&sql1=" + sql1);
							PersistentTool.executeUpdatePre(sql1, bean1);
							// 插入明细表（状态：撤回）
							insertRSP007(Integer.parseInt(applyid), "3", "");
						}
					} else {
						hm.put("recode", "false");
						hm.put("msg", "申报号为：" + applyid
								+ "撤回申报,申报状态异常2，没有要撤回的信息！！");
						return hm;
					}
				}
				String procid = pool.getString("_PROCID");
				if (!"30003001".equals(procid) && !"30003002".equals(procid)) {
					String checkRtn = checkApply(certno);
					if (!CommonUtil.isEmpty(checkRtn)) {
						// 返回失败
						hm.put("recode", "false");
						hm.put("msg", checkRtn);
						return hm;
					}
				}
				/**
				 * 重新发送申报
				 * */
				BaseBean bean = new BaseBean(); // 定义BaseBean来接收增加申报的数据bean
				bean.setTableName("RSP002");
				int applyid1 = new Integer(RspServerUtil.getKey("RSP002"));
				PersistentTool.conDB();
				// 将原申报状态修改为5：再提出
				BaseBean upoldbean = new BaseBean();
				upoldbean.setTableName("RSP002");
				upoldbean.put("state", "5");
				upoldbean.setWhereClause("applyid=" + applyid);
				String sql1 = PersistentTool.getUpdateSQL(upoldbean);
				System.out.println("&&&sql1=" + sql1);
				PersistentTool.executeUpdatePre(sql1, upoldbean);

				// 插入原申报的明细表（状态：再提出）
				insertRSP007(Integer.parseInt(applyid), "5", "申报" + applyid
						+ "再提出");
				// 删除信息提示表数据
				BaseBean bean6 = new BaseBean();
				bean6.setTableName("portal_message");
				bean6.put("applyid", applyid + "");
				String sql2 = PersistentTool.getDeleteSQL(bean6);
				System.out.println("&&&sql2=" + sql2);
				PersistentTool.excuteDel(sql2);
				// 插入申报表
				insertRSP002(pool, applyid1);
				// 插入明细表（状态：未处理）
				insertRSP007(applyid1, "0", "");
				PersistentTool.closeDB();
				hm.put("recode", "true");
				hm.put("msg", applyid1 + "");
				PersistentTool.closeDB();
				return hm;
			} catch (Exception e1) {
				e1.printStackTrace();
				if (PersistentTool.con != null)
					PersistentTool.closeDB();
			}
		}
		/**
		 * 
		 * 如果是待办任务，要发后台交易告知柜面有待办任务产生
		 * 
		 * */
		if (modtype.equals("1")) {

		}
		return hm;

	}
	
	public static DataPool queryRSP002(String applyid)
	{
		Svr802bean bean=new Svr802bean();
		//System.out.println("********************* applyid== " + applyid);
	    bean.setApplyid(Integer.parseInt(applyid));
	    RspResult rs = null;
	    try {
			rs = RspClientUtil.send(bean);
		} catch (Exception e) {
			e.printStackTrace();
		}
		DataPool pooltemp=(DataPool)rs.getBaseBeans()[0].get("pool");//武丹的方式
		return pooltemp;
	}
	
	public static ResultSet queryRSP002All(String certno,String startdate,String enddate,String state)
	{
		String sql = "select applyid,title,certno,createdate,createtime,counter,state,channel from rsp002 " +
				"where channel in ('02','10') and certno = '" +certno +"'";
		if(!CommonUtil.isEmpty(startdate))
		{
			sql +=" and createdate >='" +startdate+" 00:00:00'";
		}
		if(!CommonUtil.isEmpty(enddate))
		{
			sql +=" and createdate <='" +enddate+" 23:59:59'";
		}
		if(!CommonUtil.isEmpty(state))
		{
			sql +=" and state ='" +state+"'";
		}
			sql +=	"order by createdate desc, createtime desc";
		System.out.println("********************* sql== " + sql);
		ResultSet rs = PersistentTool.excuteQuery(sql);
		return rs;
	}
	
	
	
	/**
	 * 查询RS002附件名称
	 * 
	 * @param user
	 * @return 错误提示信息
	 */
	public static ResultSet getRS002FilePathByApplyid(String applyid) {
		ResultSet rs;
		// sql语句
		StringBuffer sqlBuf = new StringBuffer();
		sqlBuf.append("select applyid,channel,filepath from rsp002 where applyid='");
		sqlBuf.append(applyid+"'");
		// 执行查询
		rs = PersistentTool.excuteQuery(sqlBuf.toString());
		return rs;
	}

	/**
	 * 插入申报表RSP002
	 * 
	 * @param pool
	 * @param node
	 * @param applyid
	 * @throws Exception
	 */
	private static void insertRSP002(DataPool pool, int applyid)
			throws Exception {

		String title = pool.get("_PORCNAME").toString();
		// 登录类型 0-企业 1-个人 2-开发商
		String logintype = "1";
		// TODO 流程号
		Integer flowid = Integer.parseInt(pool.getString("flowid"));
		// TODO 网厅提供
		String selorgid = pool.getString("selorgid");
		// TODO 是否需要临柜 0不需要1需要
		String counter = pool.getString("counter");
		
		
		// 定义BaseBean来接收增加申报的数据bean
		BaseBean bean = new BaseBean();
		bean.setTableName("RSP002");
		bean.put("applyid", applyid);
		bean.put("title", title);
		//filePath
		if(pool.get("filePath")!=null){
			//String filePath =PropertiesReader.getProperty(PROPERTIES_FILE_NAME, KEY)+"/"+ pool.getString("filePath");
			bean.put("filepath", pool.getString("filePath"));		
		}
		// 单位登录的情况下，存放单位账号
		if ("0".equals(logintype)) {
			bean.put("unitaccnum", "");
		} else if ("1".equals(logintype)) {
			bean.put("certno", pool.get("_DEPUTYIDCARDNUM"));
		} else if ("2".equals(logintype)) {
			// 开发商登录的情况下，将借款人身份证号存到certno字段
			String procid = pool.getString("_PROCID");
			if (!"30003001".equals(procid) && !"30003002".equals(procid)) {
				bean.put("certno", pool.get("cerid1").toString());
			}
			bean.put("protonum", "");
		}
		// 为核心提供
		bean.put("funcid", pool.get("_PROCID").toString());
		bean.put("state", "0");
		bean.put("createDate", CommonUtil.getDate());
		bean.put("createTime", CommonUtil.getTime());
		bean.put("modiDate", CommonUtil.getDate());
		bean.put("modiTime", CommonUtil.getTime());
		bean.put("flowid", flowid);
		bean.put("stepid", pool.get("stepid"));
		// System.out.println("upfilename==="+pool1.get("upfilename"));
		// if ((pool1.get("upfilename") + "") != null
		// && !(pool1.get("upfilename") + "").equals("") &&
		// !(pool1.get("upfilename") + "").equals("null")) {
		// String path = pool1.get("upfilename") + "";
		// bean.put("filepath", path);
		// }else
		// bean.put("filepath", "");
		bean.put("selorgtype", "2");
		// 为呼和浩特网厅修改：由个人发起的贷款在个人下生成申报，开发商只能查询，不能操作
		// String procid = mainContext.getProc().getAttributeValue("id");
		// // 根据procid判断是否是由个人发起的，33个人、34开发商登陆
		// if (procid.contains("33")) {
		// bean.put("logintype", "1");
		// } else {
		// bean.put("logintype", logintype);
		// }
		bean.put("logintype", "1");
		// 0=予办理任务，1=待办理任务
		bean.put("modtype", "0");
		// 网厅提供
		bean.put("selorgid", selorgid);

		bean.put("channel", "10");
		if(pool.containsKey("instcode"))
		{
			//机构
			bean.put("instcode", pool.get("instcode")+"");
		}
		if(pool.containsKey("agentbankcode"))
		{
			//银行代码
			bean.put("agentbankcode", pool.get("agentbankcode")+"");
		}
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = null;
		oos = new ObjectOutputStream(bos);
		oos.writeObject(pool);
		oos.close();
		bean.put("pool", bos);// 将数据总线类型数据存入bean中
		// 20130925 增加 将该条申报对应的实例号写到rsp002的beizhu字段中 wudan
		String instanceid = pool.get("_IS").toString();
		bean.put("beizhu", instanceid);
		// 是否需要临柜 0不需要1需要
		bean.put("counter", counter);
		String sql = PersistentTool.getAddSQL(bean);
		System.out.println("&&&sql=" + sql);
		// 将信息插入数据表中
		PersistentTool.executeUpdatePre(sql, bean);
	}

	/**
	 * 插入申报明细表RSP007
	 * 
	 * @param applyid
	 * @throws Exception
	 */
	private static void insertRSP007(int applyid, String state, String beizhu)
			throws Exception {
		BaseBean bean1 = new BaseBean();
		bean1.setTableName("rsp007");
		bean1.put("applyid", applyid + "");
		bean1.put("state", state);// 将修改记录添加到表rsp007中
		bean1.put("modiDate", CommonUtil.getDate());
		bean1.put("modiTime", CommonUtil.getTime());
		bean1.put("beizhu", "");
		bean1.put("recalloper", "");
		String sql = PersistentTool.getAddSQL(bean1);
		System.out.println("&&&sql=" + sql);
		// 将信息插入数据表中
		PersistentTool.executeUpdatePre(sql, bean1);
	}

	 public static void main(String[] args) throws Exception {
		 String certno = "110101198712200507";
		 PersistentTool.conDB();
		 String flag = "36"+"00089800";
		 String centerId = "00000000";
			String stepid = PropertiesReader.getProperty("yingshe.properties","datapool_stepid"+flag);
			String flowid = PropertiesReader.getProperty("yingshe.properties","datapool_flowid"+flag);
			String sql = "select * from av054 where instanceid = '-7659' and scankey_pat= '370213198910063629' and matercode = '108' order by seqno desc ";
		

	 	System.out.println("sql:"+sql);
	 	ResultSet rs = PersistentTool.excuteQuery(sql);
	 	if(rs != null)
	 	{
	 		System.out.println(rs.getString("seqno"));
	 	}

	 }
	 
	// public static void testInsert() throws Exception
	// {
	// String certno = "110101198712200507";
	// String temp = checkApply(certno);
	// if(CommonUtil.isEmpty(temp))
	// {
	// int applyid1 = new Integer(RspServerUtil.getKey("RSP002"));
	// System.out.println("applyid1%%%%"+applyid1);
	// String selorgid = "08981302";
	// String flowid = "296";
	// PersistentTool.conDB();
	// DataPool pool = commonDataPool(-233, "1428262", "", "", "新测试人4",
	// "110101198712200507", "贷款期限变更-展期","60031011");
	// pool.put("loancontrstate", "09");
	// pool.put("jkrxm", "新测试人4");
	// pool.put("enddate", "2042-06-11");
	// pool.put("lllx", "02");
	// pool.put("jkhtbh", "02042017060004");
	// pool.put("dkhkfs", "01");
	// pool.put("remainterms", "300");
	// pool.put("loandate", "2017-06-12");
	// pool.put("retdrawtype", "1");
	// pool.put("loanreason", "test测试");
	// pool.put("oenddate", "2042-06-11");
	// pool.put("exttermcnt", "0");
	// pool.put("afterrate", "3.75");
	// pool.put("dkffe", "400000.00");
	// pool.put("exshterms", "60");
	// pool.put("jkrzjh", "110101198712200507");
	// pool.put("loanrate", "3.000000");
	// pool.put("dkye", "400000.00");
	// pool.put("CURRENT_SYSTEM_DATE", "2017-06-02");
	// pool.put("exttermenddate", "2047-06-11");
	// pool.put("dkqs", "300");
	// pool.put("loanchgtype", "02");
	// pool.put("fammonthinc", "2800.00");
	// pool.put("trycalcflag", "0");
	// insertRSP002(flowid,selorgid,"1",certno,pool,applyid1);
	// insertRSP007(applyid1, "0", "");
	// PersistentTool.closeDB();
	// }else
	// {
	// System.out.println(temp);
	// }
	//
	// }

	 /**
	  * 获取机构代码
	  * @param name
	  * @param idcard
	  * @return
	  */
	 private String getSelorgid(String name,String idcard)
	 {
		 String selorgid = "";
		 	try {
			 	AES aes = new AES();
			 	AppApi01020Form test = new AppApi01020Form();
				test.setCenterId("00089800");
				test.setXingming(aes.encrypt(name.getBytes("UTF-8")));
				test.setBodyCardNumber(idcard);
				ModelMap modelMap = new ModelMap();
				Handle01023_1_00089800 temp = new Handle01023_1_00089800();
				ModelMap modelMap1 = temp.action(test, modelMap);
				if(modelMap1.containsKey("accinstcode"))
				{
					selorgid = modelMap1.get("accinstcode").toString();
				}else
				{
					selorgid = "";
				}
			} catch (CenterRuntimeException e) {
				e.printStackTrace();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return selorgid;
	 }
	 
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public DataPool commonDataPool(HashMap map,String flag) {
		//功能名称
		pool.put("_PORCNAME", PropertiesReader.getProperty("yingshe.properties","datapool_PORCNAME"+flag));
		//功能菜单
		pool.put("_PROCID", PropertiesReader.getProperty("yingshe.properties","datapool_PROCID"+flag));
		pool.put("stepid", PropertiesReader.getProperty("yingshe.properties","datapool_stepid"+flag));
		pool.put("flowid", PropertiesReader.getProperty("yingshe.properties","datapool_flowid"+flag));
		pool.put("counter", PropertiesReader.getProperty("yingshe.properties","datapool_counter"+flag));
		
		//机构从登录接口取值
		String selorgid = getSelorgid(map.get("_ACCNAME").toString(),map.get("_DEPUTYIDCARDNUM").toString());
		pool.put("selorgid", selorgid);
		if(CommonUtil.isEmpty(selorgid))
		{
			pool.put("selorgid", PropertiesReader.getProperty("yingshe.properties","datapool_selorgid"+flag));
		}
		// TODO 个人帐号
		pool.put("_ACCNUM", map.get("_ACCNUM"));
		// TODO 单位帐号
		pool.put("_UNITACCNUM", map.get("_UNITACCNUM"));
		// TODO 单位名称
		pool.put("_UNITACCNAME", map.get("_UNITACCNAME"));
		// TODO 个人姓名
		pool.put("_ACCNAME", map.get("_ACCNAME"));
		// TODO 身份证号
		pool.put("_DEPUTYIDCARDNUM", map.get("_DEPUTYIDCARDNUM"));
		// TODO
		pool.put("_SENDOPERID", map.get("_SENDOPERID"));
		pool.put("_SENDTIME", CommonUtil.getTime());
		pool.put("_SENDDATE", CommonUtil.getDate());
		pool.put("_LOGIP", CommonUtil.getSystemDateNumOnly());
		// TODO
		pool.put("_IS", map.get("_IS"));
	
		// 是否是单位账户 0个人1单位
		pool.put("_ISCROP", "0");
		// 网厅流程节点的审批标识，0同意/1不同意
		pool.put("_APPLY", "0");
		pool.put("_RW", "w");

		pool.put("_CHANNEL", "1");
		pool.put("_PAGEID", "step1");
		pool.put("_TYPE", "init");

		pool.put("_UNITCUSTID", "");
		pool.put("_BRANCHKIND", "0");
		pool.put("_EMPUNITNAME", "");
		pool.put("_WITHKEY", "0");
		pool.put("_USBKEY", "");
		pool.put("_PROTONUM", "");
		return pool;

	}

	/**
	 * 发申报前，校验申报是否存在
	 * 
	 * @param user
	 * @return 错误提示信息
	 */
	private static String checkApply(String certno) {
		String retStr;
		ResultSet rs;
		// 取得登录标志
		String logintype = "1";
		// sql语句
		StringBuffer sqlBuf = new StringBuffer();
		int count = 0;
		// 个人或开发商登录的情况下，验证贷款任务
		if ("1".equals(logintype) || "2".equals(logintype)) {
			sqlBuf.append("select applyid,channel from rsp002 where certno='");
			if ("1".equals(logintype)) {
				sqlBuf.append(certno);
			} else if ("2".equals(logintype)) {
				sqlBuf.append(certno);
			}
			sqlBuf.append("' and (state='0' or state='1')");
			try {
				// 执行查询
				PersistentTool.conDB();
				rs = PersistentTool.excuteQuery(sqlBuf.toString());

				if (rs != null) {
					// 同一借款人存在处理中或未处理申报时，不能再重复提出
					if (rs.getRow() > 0) {
						if("10".equals(rs.getString("channel")))
						{
							retStr = "该借款人存在“处理中”或是“未处理”的移动端申报业务，不能重复提出。申报号为："
									+ rs.getString("applyid");
						}else if("02".equals(rs.getString("channel")))
						{
							retStr = "该借款人存在“处理中”或是“未处理”的网厅申报业务，不能重复提出。申报号为："
									+ rs.getString("applyid");
						}else{
							retStr = "该借款人存在“处理中”或是“未处理”的网上申报业务，不能重复提出。申报号为："
									+ rs.getString("applyid");
						}
						return retStr;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		PersistentTool.closeDB();
		return "";
	}
	
	public void backApply(String applyid) throws Exception
	{
		BaseBean bean1 = new BaseBean();
		bean1.setTableName("RSP002"); // 设置要操作的数据表
		bean1.setWhereClause("applyid='" + applyid + "'"); // 设置撤回操作的where条件
		bean1.put("state", "3");
		String sql1 = PersistentTool.getUpdateSQL(bean1);
		System.out.println("&&&sql1=" + sql1);
		PersistentTool.executeUpdatePre(sql1, bean1);
		// 插入明细表（状态：撤回）
		insertRSP007(Integer.parseInt(applyid), "3", "");
	}
}
