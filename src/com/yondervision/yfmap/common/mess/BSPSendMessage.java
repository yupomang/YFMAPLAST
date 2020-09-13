package com.yondervision.yfmap.common.mess;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ConnectException;
import java.net.InetAddress;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;


public class BSPSendMessage extends AbsSendMessage{
   public void send() throws CenterRuntimeException{ 
	   try{
		   int instanceid=0;
		   StringBuffer headStringBuffer = new StringBuffer();
		   
		   //原for(int i=1;i<=8-String.valueOf(upItem.toString().trim().length()).length();i++){
		   //以下为农垦，处理中文问题，长度计算少问题.getBytes("GBK").length
		   for(int i=1;i<=8-String.valueOf(upItem.toString().trim().getBytes("GBK").length).length();i++){
			   headStringBuffer.append("0");
		   }
//		   headStringBuffer.append(""+upItem.toString().trim().getBytes("GBK").length());
		   headStringBuffer.append(""+upItem.toString().trim().getBytes("GBK").length);
			// 交易代码 6 后台交易代码
		   headStringBuffer.append(this.trancode);
			// 报文类型 2 “RQ”请求报文“RS”回应报文
		   headStringBuffer.append("RQ");
			// 文件传送标志 1 0-无文件 1-有文件上传 2-有文件下传
		   headStringBuffer.append("0");
			// 加密卡ID 8 getenv(“DEVICE_ID”) 前补零
		   headStringBuffer.append("00000001");
			// 报文校验码 16 不包括报文头 实际8字节，扩展成16进制的ASCII 16字节
		   headStringBuffer.append("################");
			// 保留 8
		   headStringBuffer.append("        ");
			// 服务名称 8 后台服务程序名
//		   headStringBuffer.append("yd" + this.trancode);//包钢、威海、昆明使用"yd" + this.trancode
//		   headStringBuffer.append("00066800");//茂名
		   headStringBuffer.append("00000000");//贵港
//		   headStringBuffer.append("000000002");//株洲
//		   headStringBuffer.append("000000002");//内蒙区直
//		   headStringBuffer.append("00000000");//哈尔滨农垦000000000
			// 服务协议 1 0:本地 1:CICS 2:MQ 3:TCPIP 4:TEXUDO 5:SNA
		   headStringBuffer.append("2");//海南、遵义2，其它为1  内蒙区直没有
	//	   String ip=java.net.InetAddress.getLocalHost().getHostAddress();
	//	   StringBuffer message=new StringBuffer("");
	//	   message.append( "<SubBank>aaa</><HostBank>aaa</><FinancialDate>");
	//	   message.append( getDT(0, 0, "yyyy-MM-dd"));
	//	   message.append( "</><OppOperList>12310</><AuthFlag>1231</><ChannelSeq>"+instanceid+"</><TranIP>"+ip+"</><TranChannel>29</><AuthCode1>1231</><AuthCode2>1231</><AuthCode3>1231</><ChkCode>1231</><TellCode>00000000</><BrcCode>00000000</><TranCode>");
	//	   message.append( trancode + "</><TranDate>" );
	//	   message.append( getDT(0, 0, "yyyy-MM-dd") + "</><STimeStamp>" );
	//	   message.append( getDT(0, 0, "yyyy-MM-dd kk:mm:ss ") );
	//	   message.append("</><MTimeStamp>" );
	//	   message.append( getDT(0, 0, "yyyy-MM-dd kk:mm:ss ") + "</>" );
	//	   for(String key:upmap.keySet()){
	//		   message.append("<"+key+">"+upmap.get(key)+"</>");
	//	   }
		   log.info(headStringBuffer.toString()+upItem.toString() + "9");
		   log.info("ip:"+ip+"    port:"+port);	
		   Socket c  = new Socket(ip, port);
		   c.setSoTimeout(TIME_OUT);
		   log.info("ip:"+ip+"    port:"+port);		   
//		   PrintWriter out = new PrintWriter(c.getOutputStream(), true);
		   
		   //PrintWriter out = new PrintWriter(new OutputStreamWriter(c.getOutputStream(),"GBK"),true);//遵义WEBLOGIC字符乱码修改
		   /***
		    * 遵义FSP问题
		    */
		   out = c.getOutputStream();
		   int byteSize = 1024;
		   byte[] btMsg = (headStringBuffer.toString()+upItem.toString()+"9").getBytes("GBK");
		   byte[] temp = new byte[byteSize];
		   int wLine = 0;
		   while (wLine < btMsg.length) {
			   if (wLine + byteSize > btMsg.length) {
				   temp = new byte[btMsg.length - wLine];
			   }
			   System.arraycopy(btMsg, wLine, temp, 0, temp.length);
			   out.write(temp);	
			   wLine += temp.length;
		   }
		   out.flush();
		   /***
		    * 遵义FSP问题
		    */
		   
//		   out = new PrintWriter(c.getOutputStream(), true);//其它中心使用
//		   
//		   out.println(headStringBuffer.toString()+upItem.toString() + "9");
		   log.info(headStringBuffer.toString()+upItem.toString() + "9");
		   InputStream is = c.getInputStream();
		   //log.info("is=============="+is);
		   //log.info("111111111111111111111");
		   MessageConfig[] scary = new MessageConfig[] {
					new MessageConfig("bwcd", "报文体长度", 8),
					new MessageConfig("jydm", "交易代码", 6),
					new MessageConfig("bwlx", "报文类型", 2),
					new MessageConfig("csbz", "文件传送标志", 1),
					new MessageConfig("jym", "报文校验码（MAC)", 32),
					new MessageConfig("wfcx", "服务程序名", 8),
					new MessageConfig("bwxy", "报文协议", 1) };
			byte[] b = new byte[100];
			int pyl = 0;
			HashMap hs = new HashMap();
			//log.info("222222222222222222222");
			for (int i = 0; i < scary.length; i++) {
				MessageConfig sc = scary[i];
				pyl += is.read(b, 0, sc.length);
				String tmp = new String(b, 0, sc.length);
				//log.info("b==================" + new String(b));
				//log.info("sc.length=" + sc.length + "sc.name=" + sc.name + "src=" + tmp);
				hs.put(sc.name, tmp);
			}
			//log.info("333333333333333333333");
			int bwcd = 0; 
			//log.info("hs=" + hs);
			bwcd = Integer.parseInt(hs.get("bwcd").toString());
			byte[] bw = new byte[bwcd];
			int sumBwLen = is.read(bw); 
			if (sumBwLen < bw.length) {
				byte[] tmpbyte = new byte[50];
				while (sumBwLen < bw.length) {
					int t = 0;
					if (sumBwLen + 50 < bw.length)
						t = is.read(tmpbyte);
					else
						t = is.read(tmpbyte, 0, bw.length - sumBwLen);
					System.arraycopy(tmpbyte, 0, bw, sumBwLen, t);
					sumBwLen += t;
				} 
			} 
			//log.info("4444444444444444444");
		downItem = new String(bw,encode);
		if(c!=null){
			c.close();
		}
		
		log.info("BSPSendMessage 接收CSP返回报文："+downItem);
	}catch(Exception e){
		e.printStackTrace();
		log.info("往BSP发送信息失败BSPSend Fail");
		throw new CenterRuntimeException(Constants.ERROR_999999,"往BSP发送信息失败BSPSend Fail");
	}
	//		downmap=new HashMap<String,String>();
	//		String[] infos = returnvalue.split("</>");
	//		for (int i = 0; i < infos.length; i++) {
	//			int wz = infos[i].indexOf(">");
	//			
	//			String key = infos[i].substring(1, wz);
	//			String value = infos[i].substring(wz + 1);
	//			downmap.put(key, value);
	//		}
   }
   public String getRet(){	   
	    return downItem;
   }
   private static String getDT(int date, int hour, String format) {
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(new Date().getTime());
		c.add(Calendar.HOUR_OF_DAY, hour);
		c.add(Calendar.DAY_OF_YEAR, date);
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		return dateFormat.format(c.getTime()).trim();
   }
	public static void main(String[] args) throws Exception
	{
		AbsSendMessage bm = (AbsSendMessage) Class.forName("com.yondervision.yfmap.common.mess.BspMessage").newInstance();
		bm.setIp("127.0.0.1");
		bm.setPort(6051);
		bm.setTranCode("100000");
		String mess="<AuthFlag>0</><TellCode>9999</><ChkCode>0000</><BrcCode>00000001</><TranIP>[@setVariable,tranIP,13,0,0@]</><TranCode>[@setVariable,jym,13,0,0@]</><ChannelSeq>000</><TranChannel>0</><TranDate>[@setVariable,date_10,10,0,0@]</><STimeStamp>[@setVariable,time,6,0,0@]</><MTimeStamp>[@setVariable,date_26,26,0,0@]</><AuthCode2>1112</><AuthCode3>1112</><AuthCode1>1112</><OppOperList></><HostBank>null</><SubBank>null</><BusiSeq>[@setVariable,BusiSeq,10,0,0@]</><NoteMsg></><TranSeq>[@setVariable,sa,10,0,0@]</><RspCode>[@setVariable,YQ06415,10,0,0@]</><RspMsg>[@setVariable,YQ06417,50,0,0@]</><FinancialDate>[@setVariable,date_10,10,0,0@]</><sjzmessage>[@setVariable,yewubaowen,9999,0,0@]</sjzmessage><OppOperList></>";
//		Map<String,String> map= new HashMap<String,String>();
		bm.setUpItem(mess);
		bm.send();
		System.out.println(bm.getRet());
		 
	}
	
	
	protected Socket socketSend(String ip, int port, byte[] smsg, int timeout,
			int localport) throws ConnectException, IOException {
		Socket socket = null;
		OutputStream out = null;
		
			if (localport == 0) {

				socket = new Socket(ip, port);
			} else {
				InetAddress i = InetAddress.getLocalHost();
				socket = new Socket(ip, port, i, localport);
			}

			socket.setSoTimeout(timeout * 1000);
			int byteSize = 1024;
			out = socket.getOutputStream();
			byte[] btMsg = smsg;
			byte[] temp = new byte[byteSize];
			int wLine = 0;
			while (wLine < btMsg.length) {
				if (wLine + byteSize > btMsg.length) {
					temp = new byte[btMsg.length - wLine];
				}
				System.arraycopy(btMsg, wLine, temp, 0, temp.length);
				out.write(temp);

				wLine += temp.length;
			}
			out.flush();

		return socket;
	}

	
	
}
