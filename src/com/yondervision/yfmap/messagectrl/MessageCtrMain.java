/********************************************************** 
 * @CopyRright (c) 2011:www.yondervision.com.cn
 * @ProjectName: yqzl
 * @PackageName: com.yondervison.messageCtr
 * @ClassName: MessageCtrMain.java
 * @Comments: 
 * @JDK version: JDK jdk1.6.0_10
 * @Author: ��С��
 * @Create Date��2011-6-17 
 * @Modified By��
 * @Modified Date:                               
 *  Why & What is modified
 * @Version: 1.0
 ***********************************************************/
package com.yondervision.yfmap.messagectrl;

import java.util.HashMap;

import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.util.CommonUtil;

/**
 * <pre>报文封装解析处理类</pre>
 * @PackageName: com.yondervison.messageCtr
 * @FileName: MessageCtrMain.java
 * @author 林小龙
 */
public class MessageCtrMain {
	
	/** 报文存放在HashMap中的key */
	public static final String MESSAGE_KEY = "__message_key";
	
//	public static final String TRANS_SEQ_KEY = "__trans_seq";
	
	/** 共享区域对象中的HashMap中对应【银行的处理状态，0=成功;1=失败】的key */
	public static final String RESPONSE_CODE_KEY = "__response_code";
	
	/** 共享区域对象中的HashMap中对应【银行返回的处理结果说明】的key */
	public static final String RESPONSE_INFO_KEY = "__response_info";
	
	/** 批扣业务文件名在HashMap中对应的key */
	public static final String SEND_FILE_NAME_KEY = "YQ03308";
	
	/** 存放报文数据文件名在HashMap中对应的key */
	public static final String RECEIVE_FILE_NAME_KEY = "__reveive_file_name";
	
	/** 批量文件分隔符 */
	public static final String SPLITER = "@|$";
	
	/** 发送报文模板存放的跟路径 */
	public static final String TEMP_PATH = "com/yondervision/yfmap/config/messagetemp/";
	
	/** 数据格式转换类 */
	public final static String MESSAGE_CTR_METHOD_CLASS = "com.yondervision.yfmap.messagectrl.MessageDataConversion";
	

	/**
	 * 把HashMap类型的数据封装成指定模板类型的指定报文模板的报文
	 * 
	 * @param msgType
	 *            报文模板类型
	 * @param msgTempFileName
	 *            报文模板名称
	 * @param msgData
	 *            报文中要用的数据
	 * @return 封装后的报文
	 * @throws CenterRuntimeException
	 */
	public static String encapsulatedPackets(String msgType, String msgTempFileName, HashMap<String, String> msgData, String transSeq) throws CenterRuntimeException {
		AbsMessageCtr msgCtr = AbsMessageCtr.getInstance(msgType);
		String strMessage = msgCtr.encapsulatedPackets(msgTempFileName, msgData,transSeq);
		return strMessage;
	}

	/**
	 * 把指定模板类型的指定报文模板的报文解析成HashMap类型的数据
	 * 
	 * @param msgType
	 *            报文模板类型
	 * @param msgTempFileName
	 *            报文模板名称
	 * @param msgData
	 *            报文
	 * @return 解析出的报文数据
	 * @throws YQZLException
	 */
	public static HashMap<String, String> analysisPacket(String msgType, String msgTempFileName, String msgData, String transSeq) throws CenterRuntimeException {
		AbsMessageCtr msgCtr = AbsMessageCtr.getInstance(msgType);
		HashMap<String, String> mapMsg = msgCtr.analysisPacket(msgTempFileName, msgData, transSeq);
		return mapMsg;
	}
	
//	public static void main(String[] args) {
//		SimpleDateFormat formatter = new SimpleDateFormat(MessageDataConversion.dateTimeFormat);
//		String strDateTime = formatter.format(new Date());
//		String seqNo = "06310011107120000034";// 交易序号
//		HashMap<String, String> msgData = new HashMap<String, String>();
//		msgData.put("YQ03301", seqNo);
//		msgData.put("YQ03310", strDateTime);
//		msgData.put("YQ03306", "3");
//		msgData.put("YQ03307", "323423.38");
//		msgData.put(SEND_FILE_NAME_KEY, "C:/RECV01081242.DAT");
//		try {
////			String tempFileName = "D:/eclipse/project/yqzlteam/project/yqzl/src/com/yondervison/resources/messagetemp/06310/01/REQ_2GRPEXCH2.xml";
////			E:\workspace\yqzl\WebRoot\WEB-INF\classes\com\yondervison\resources\messagetemp\06310\01
//			String tempFileName = "E:/workspace/yqzl/WebRoot/WEB-INF/classes/com/yondervison/resources/messagetemp/06310/01/AAA.xml";
//			String xmlMessage = encapsulatedPackets("2", tempFileName, msgData, seqNo);
//			System.out.println(xmlMessage);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}
	
	
//	public static void main(String[] args){		
//		String msgTempFileName = "";
//		String msgData = "";
//		String req = CommonUtil.getSystemDateNumOnly();
//		HashMap<String, String> map = analysisPacket("2", msgTempFileName, msgData, req);
//	}
}
