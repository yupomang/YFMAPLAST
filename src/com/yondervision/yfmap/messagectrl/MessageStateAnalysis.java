/********************************************************** 
 * @CopyRright (c) 2011:www.yondervision.com.cn
 * @ProjectName: yqzl
 * @PackageName: com.yondervison.messageCtr
 * @ClassName: MessageStateAnalysis.java
 * @Comments: 
 * @JDK version: JDK jdk1.6.0_10
 * @Author: ��С��
 * @Create Date��2011-7-1 
 * @Modified By��
 * @Modified Date:                               
 *  Why & What is modified
 * @Version: 1.0
***********************************************************/ 
package com.yondervision.yfmap.messagectrl;

import java.util.HashMap;

/**
 * <pre></pre>
 * @PackageName: com.yondervision.yfmap.messagectrl
 * @FileName: MessageStateAnalysis.java
 * @author 林小龙
 */
public class MessageStateAnalysis {
	
	/** YQ011表中的参数类型 */
	public static final String PARAMETERS_TYPE_KEY = "1001";
	
	/**
	 * <pre>通过交易渠道码存放标识数据</pre>
	 * @param strChannelCode 交易渠道码
	 * @param msgData 由返回报文解析出的数据
	 * @throws Exception
	 */
	public static void pubStateAnalysis(String strChannelCode,
			HashMap<String, String> msgData) throws Exception {
		
		//返回码在报文中配置的名称
		String strReCodeKey = "";
		//返回信息在报文中的配置名称
		String strReMsgKey = "";
		//返回码表示成功的值
		String strSuccVal = "";
		
		
		/*
		 * 将返回信息另存到msgData的指定位置
		 */
		if (strSuccVal.equals(msgData.get(strReCodeKey))) {
			msgData.put(MessageCtrMain.RESPONSE_CODE_KEY, StateDefinition.TRANSACTIONS_FLAG_SUCCESSFUL);
		} else {
			msgData.put(MessageCtrMain.RESPONSE_CODE_KEY, StateDefinition.TRANSACTIONS_FLAG_FAILED);
		}
		msgData.put(MessageCtrMain.RESPONSE_INFO_KEY, msgData.get(strReMsgKey));
	}
}

