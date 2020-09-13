/********************************************************** 
 * @CopyRright (c) 2011:www.yondervision.com.cn
 * @ProjectName: yqzl
 * @PackageName: com.yondervison.application.definition
 * @ClassName: StateDefinition.java
 * @Comments: 
 * @JDK version: JDK jdk1.6.0_10
 * @Author: ��С��
 * @Create Date��2011-6-18 
 * @Modified By��
 * @Modified Date:                               
 *  Why & What is modified
 * @Version: 1.0
***********************************************************/ 
package com.yondervision.yfmap.messagectrl;

/**
 * <pre>状态码静态变量定义类</pre>
 * @PackageName: com.yondervison.application.definition
 * @FileName: StateDefinition.java
 * @author 林小龙
 */
public class StateDefinition { 
	/** 通讯开关——开 */
	public static final String COMMUNICATION_SWITCH_ON = "1";

	/** 通讯开关——关 */
	public static final String COMMUNICATION_SWITCH_OFF = "0";
	
	/** 交易处理标志——成功 */
	public static final String TRANSACTIONS_FLAG_SUCCESSFUL = "0";
	
	/** 交易处理标志——失败 */
	public static final String TRANSACTIONS_FLAG_FAILED = "1";
	
	/** 流程控制标志——执行 */
	public static final String FLOW_CONTROL_FLAG_EXECUTION = "1";
	
	/** 流程控制标志——忽略 */
	public static final String FLOW_CONTROL_FLAG_IGNORE = "0";
	
	/** 通讯模式——同步转异步 */
	public static final String COMMUNICATION_MODE_SY_TO_ASY = "12";
	
	/** 通讯模式——异步转同步 */
	public static final String COMMUNICATION_MODE_ASY_TO_SY = "21";
	
	/** 通讯模式——同步转同步 */
	public static final String COMMUNICATION_MODE_SY_TO_SY = "11";
	
	/** 通讯模式——异步转异步 */
	public static final String COMMUNICATION_MODE_ASY_TO_ASY = "22";
	
	/** 批量标志——单笔 */
	public static final String BATCH_FLAG_SIGLE = "0";
	
	/** 批量标志——批量 */
	public static final String BATCH_FLAG_BATCH = "1";
	
	/** 银行端处理方式——交易处理 */
	public static final String BANK_PROCESS_TYPE_TRANS = "00";
	
	/** 银行端处理方式——API处理 */
	public static final String BANK_PROCESS_TYPE_API = "01";
	
	/** 银行端处理方式——不处理 */
	public static final String BANK_PROCESS_TYPE_LGNORE = "02";
	
	/** 数据库更新标志——更新 */
	public static final String UPDATE_DATABASE_FLAG_TRUE = "0";
	
	/** 数据库更新标志——不更新 */
	public static final String UPDATE_DATABASE_FLAG_FALSE = "1";
	
	/** 记日志标志——记日志 */
	public static final String LOG_FLAG_TRUE = "1";
	
	/** 记日志标志——不记日志 */
	public static final String LOG_FLAG_FALSE = "0";
}
