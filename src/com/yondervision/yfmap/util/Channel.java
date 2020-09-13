package com.yondervision.yfmap.util;

public class Channel {
	public static String getChannel(String value){
		String channel = value;
		if("10".equals(value)){//10	APP
			channel = "05";//05-APP查询			
		}else if("20".equals(value)){//20	微信
			channel = "04";//04-微信查询
		}else if("30".equals(value)){//30	门户网站
			channel = "06";//06-网站查询
		}else if("40".equals(value)){//40	网上业务大厅
			channel = "01";//01-网厅
		}else if("50".equals(value)){//50	自助终端
			channel = "02";//02-查询机
		}else if("60".equals(value)){//60	服务热线
			channel = "03";//03-语音查询
		}else{//综合客服
			channel = "07";
		}		
		return channel;
	}
	
	public static String getZzChannel(String value){
		String channel = value;
		if("10".equals(value)){//10	APP
			channel = "8";//8-APP查询			
		}else if("20".equals(value)){//20	微信
			channel = "7";//7-微信查询
		}else if("30".equals(value)){//30	门户网站
			channel = "1";//1-网站
		}else if("40".equals(value)){//40	网上业务大厅
			channel = "4";//4-网厅
		}else if("50".equals(value)){//50	自助终端
			channel = "3";//3-查询机
		}else if("60".equals(value)){//60	服务热线
			channel = "2";//2-客服
		}else if("70".equals(value)){//70	
			channel = "6";//6-其他
		}else if("80".equals(value)){//80	官方微博
			channel = "9";//9-微博
		}else if("90".equals(value)){//90	
			channel = "9";//5-银行
		}else{//综合客服
			channel = "0";           //柜面
		}		
		return channel;
	}

}
