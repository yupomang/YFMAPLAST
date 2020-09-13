package com.yondervision.yfmap.common;

/**
 * @author Caozhongyan
 *
 */
public class Constants {
	/**
	 * YD系统管理员
	 */
	public static final String YD_ADMIN="00000000";
	
	/**
	 * 数据无效
	 */
	public static final String IS_NOT_VALIDFLAG="0";
	/**
	 * 数据有效
	 */
	public static final String IS_VALIDFLAG="1";
	
	/**
	 * 版本无效
	 */
	public static final String IS_NOT_USABLEFLAG="0";
	/**
	 * 版本有效
	 */
	public static final String IS_USABLEFLAGG="1";
	
	/**
	 * 格式：yyMMdd
	 */
	public static final String DATE_FORMAT_XH_YYMMDD = "yyMMdd";

	/**
	 * 格式：yyyy-MM-dd
	 */
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	/**
	 * 格式：HH:mm:ss
	 */
	public static final String TIME_FORMAT = "HH:mm:ss";

	/**
	 * 格式：yyyy-MM-dd HH:mm:ss.SSS
	 */
	public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss.SSS";

	/**
	 * 格式：yyyy-MM-dd HH:mm:ss:SSS000
	 */
	public static final String DATE_TIME_FORMAT_M = "yyyy-MM-dd HH:mm:ss:SSS000";
	
	/**
	 * 格式：HH:mm:ss
	 */
	public static final String DATE_TIME_FORMAT_HMS = "HH:mm:ss";
	
	/**
	 * 配置文件文件名
	 */
	public static final String PROPERTIES_FILE_NAME = "properties.properties";
	
	/**
	 * 是叶子节点
	 */
	public static final String IS_LEAF_FLG = "1";
	
	/**
	 * 不是叶子节点
	 */
	public static final String IS_NOT_LEAF_FLG = "0";
	
	/** 等额本息还款 */
	public static final String REPAY_TYPE_A = "10";
	/** 等额本金还款 */
	public static final String REPAY_TYPE_B = "20";
	/**
	 * 公积金贷款利率类型
	 */
	public static final String RETA_TYPE_10 = "10";
	/**
	 * 公积金贷款利率类型
	 */
	public static final String RETA_TYPE_20 = "20";
	/**
	 * 公积金贷款利率类型
	 */
	public static final String RETA_TYPE_30 = "30";
	/**
	 * 公积金贷款利率类型
	 */
	public static final String RETA_TYPE_40 = "40";
	
	/**
	 * WEB请求成功返回码
	 */
	public static final String WEB_SUCCESS_CODE = "000000";
	
	/**
	 * WEB请求成功返回信息
	 */
	public static final String WEB_SUCCESS_MSG = "成功";
	
	/**
	 * APP请求成功返回码
	 */
	public static final String APP_SUCCESS_CODE = "000000";
	
	/**
	 * APP请求成功返回信息
	 */
	public static final String APP_SUCCESS_MSG = "成功";
	
	/**
	 * MI308表中表示顶级咨询内容的parentId
	 */
	public static final String MI308_SRC_PARENT_ID = "src";
	
	/**
	 * 推送信息初始状态
	 */
	public static final String PUSH_MSG_DEF_STATE = "0";
	
	/**
	 * 推送信息已推送
	 */
	public static final String PUSH_MSG_ARD_PUSH = "3";
	
	public static final String ERROR_999999="999999";
	
	public static final String LOG_HEAD = "YFMAP--->>>";
	
	public static final String LOG_ERROR = "YFMAP---######";
	
	public static final String propertiesFile = "properties.properties";
	
	public static final String msgPath = "WEB-INF/classes/com/yondervision/yfmap/config/messagetemp/";
	public static final String method_XML = "1";
	public static final String method_BSP = "2";
	public static final String method_FSP = "3";
	public static final String sucess = "000000";
	
	//唐山
	public static final String sucess_ts = "0";
	
	//00041100 start
	public static final String logTableYwlb_gr="0";
	public static final String logTableYwlb_dw="1";
	public static final String logTableYwlb_gg="2";
	public static final String logFileAandTable_cg="1";
	public static final String logFileAandTable_xb="0";
	//00041100 end
	
	/**
	 * 渠道来源:APP
	 */
	public static final String 	CHANNELTYPE_APP = "10";
	/**
	 * 渠道来源:微信
	 */
	public static final String 	CHANNELTYPE_WEIXIN = "20";

}
