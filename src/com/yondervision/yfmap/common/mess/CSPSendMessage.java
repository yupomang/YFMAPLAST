package com.yondervision.yfmap.common.mess;

import java.io.IOException;
import java.net.Socket;
import java.util.HashMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.security.EncryptionInfo;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;

public class CSPSendMessage extends AbsSendMessage{
	
	/**
	 * 通信方法
	 * @param message
	 * @throws Exception
	 */
	public void send() throws CenterRuntimeException {
		try{
			Socket socket = new Socket(ip, port);
			socket.setSoTimeout(TIME_OUT);
			this.out = socket.getOutputStream();
			this.in = socket.getInputStream();
			encryFlag =  EncryptionInfo.getEncryptionFlag();
			upItem = upItem.replaceAll("\n", "");
			upItem = upItem.replaceAll("\t", "");
            upItem = upItem.replaceAll("\r", "").trim();
			log.info(Constants.LOG_HEAD+"发送报文：" + upItem);
			
			this.sendXML();
			this.readXML();
			log.info(Constants.LOG_HEAD+"接收报文：" + downItem);
		}catch(Exception e){
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"通信失败");
		}finally{
			try {
				if(out!=null){
					out.close();
				}
				if(in!=null){
					in.close();
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw new CenterRuntimeException(Constants.ERROR_999999,"关闭流失败");
			}
		}
	}
	/**
	 * 发送报文
	 * 
	 * @param out
	 *            输出流
	 * @param msg
	 *            发送的报文
	 */
	private void sendXML() {
		try {
			int byteSize = 4096;
			byte[] btMsg = null;
			//upItem = upItem+"9";  德阳后面加9代表结束报文
			if("1".equals(encryFlag)){
				btMsg = EncryptionInfo.encryptionMessage(upItem);
			}else{
				btMsg = upItem.getBytes(encode);
			}
			System.out.println("YFMAP log "+ip+"===port"+port);
			head = getMessageHead(btMsg);
			byte[] temp = new byte[byteSize];
			int wLine = 0;
			out.write(head.getBytes());
			while (wLine < btMsg.length) {
				if (wLine + byteSize > btMsg.length) {
					temp = new byte[btMsg.length - wLine];
				}
				System.arraycopy(btMsg, wLine, temp, 0, temp.length);
				out.write(temp);
				wLine += temp.length;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"发送信息失败");
		} finally {
			try {
				out.flush();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	/**
	 * 从流中读取XML
	 * 
	 * @param in
	 *            输入流
	 * @param setLength
	 *            是否加入长度
	 * @return String 串报文（纯净）
	 * @throws IOException
	 */
	@SuppressWarnings("unused")
	private void readXML() throws IOException {
//		try {
//			byte[] bwcdbyte = new byte[7];
////			byte[] isps = new byte[1];
//			in.read(bwcdbyte);
////			in.read(isps);
//			// 前8位标识报文长度
//			String strbwcd = new String(bwcdbyte);
////			encryFlag =  new String(isps);
//			System.out.println("YFMAP=========>读取返回报文前7位长度为 ： "+strbwcd);
////			encryFlag =  new String(isps);
////			System.out.println("YFMAP=========>读取返回报文第9位加密标识为 ： "+encryFlag);
//			int TotalLen = Integer.parseInt(strbwcd);
//			int len = 128;
//			byte[] b = new byte[len];
//			byte[] bMessage = new byte[TotalLen];
//			int curLen = 0;
//			while (curLen < TotalLen) {
//				int t = 0;
//				if (curLen + len < TotalLen) {
//					t = in.read(b);
//				} else {
//					t = in.read(b, 0, TotalLen - curLen);
//				}
//				if (t == -1) {
//					break;
//				}
//				System.arraycopy(b, 0, bMessage, curLen, t);
//				curLen += t;
//			}
////			if("1".equals(encryFlag)){
////				downItem = EncryptionInfo.dencryptionMessage(bMessage);
////			}else{
//				downItem = new String(bMessage,encode);
////			}
//		} catch (IOException e) {
//			e.printStackTrace();
//			throw new CenterRuntimeException(Constants.ERROR_999999,"读取返回信息失败");
//		}
		
		//以上为大连
		
		//以下为济南
		try {
			byte[] bwcdbyte = new byte[8];//济南，榆林，德阳
//			byte[] bwcdbyte = new byte[6];//秦皇岛,保山,吉林
			byte[] isps = new byte[1];
			in.read(bwcdbyte);
			in.read(isps);
			// 前8位标识报文长度
			String strbwcd = new String(bwcdbyte);
			System.out.println("YFMAP=========>读取返回报文前"+bwcdbyte.length+"位长度为 ： "+strbwcd);
			encryFlag =  new String(isps);
			System.out.println("YFMAP=========>读取返回报文加密标识为 ： "+encryFlag);
			int TotalLen = Integer.parseInt(strbwcd);
			int len = 128;
			byte[] b = new byte[len];
			byte[] bMessage = new byte[TotalLen];
			int curLen = 0;
			while (curLen < TotalLen) {
				int t = 0;
				if (curLen + len < TotalLen) {
					t = in.read(b);
				} else {
					t = in.read(b, 0, TotalLen - curLen);
				}
				if (t == -1) {
					break;
				}
				System.arraycopy(b, 0, bMessage, curLen, t);
				curLen += t;
			}
			if("1".equals(encryFlag)){
				downItem = EncryptionInfo.dencryptionMessage(bMessage);
				downItem = downItem.trim();
			}else{
				downItem = new String(bMessage,encode);
				downItem = downItem.trim();
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"读取返回信息失败");
		}
	}
	public String getMessageHead(byte[] btMsg){
		int msgLength=btMsg.length;
		System.out.println("====getMessageHead====>msgLength:"+msgLength);
		//String strByteSize = String.valueOf(msgLength+1);
		String strByteSize = String.valueOf(msgLength);
		String head = new StringBuffer().append(
//				"000000".substring(strByteSize.length())).append(大连
//				"00000000".substring(strByteSize.length())).append(//济南，榆林
//				"000000".substring(strByteSize.length())).append(//保山
				"00000000".substring(strByteSize.length())).append(//秦皇岛、吉林
				strByteSize).append(encryFlag).toString();
		System.out.println("========>封装发往中心报文:"+head+upItem);
		return head;
	}
	public static void main(String[] args) throws Exception {
		
//		AbsSendMessage bm = (AbsSendMessage)AbsSendMessage.getInstance("1");
		//new CspMessage("127.0.0.1", 10030);
//		bm.setIp("127.0.0.1");
//		bm.setPort(10030);
//		bm.setUpItem("<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><reqflag>1</reqflag><msgtype>01</msgtype><tr_code>A00001</tr_code><corp_no></corp_no><oreq_no></oreq_no><tr_acdt>1324</tr_acdt><tr_time>1234</tr_time><ans_code>1324</ans_code><ans_info>1324</ans_info><particular_code>1324</particular_code><particular_info></particular_info><reserved>2</reserved></head><body><amt></amt><amt1></amt1><amt2></amt2><free_use1></free_use1><free_use2></free_use2><free_use3></free_use3><free_use4></free_use4><free_use5></free_use5><free_use6></free_use6></body></root>");
//		bm.send();
//		String message="<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><reqflag>1</reqflag><msgtype>01</msgtype><tr_code>A00001</tr_code><corp_no></corp_no><oreq_no></oreq_no><tr_acdt>1324</tr_acdt><tr_time>1234</tr_time><ans_code>1324</ans_code><ans_info>1324</ans_info><particular_code>1324</particular_code><particular_info></particular_info><reserved>2</reserved></head><body><amt></amt><amt1></amt1><amt2></amt2><free_use1></free_use1><free_use2></free_use2><free_use3></free_use3><free_use4></free_use4><free_use5></free_use5><free_use6></free_use6></body></root>";
//		System.out.print(CommunicateUtil.sendMessage("1", "127.0.0.1", 10030, message, "100000"));
//		String message1="<AuthFlag>0</><TellCode>9999</><ChkCode>0000</><BrcCode>00000001</><TranIP>[@setVariable,tranIP,13,0,0@]</><TranCode>[@setVariable,jym,13,0,0@]</><ChannelSeq>000</><TranChannel>0</><TranDate>[@setVariable,date_10,10,0,0@]</><STimeStamp>[@setVariable,time,6,0,0@]</><MTimeStamp>[@setVariable,date_26,26,0,0@]</><AuthCode2>1112</><AuthCode3>1112</><AuthCode1>1112</><OppOperList></><HostBank>null</><SubBank>null</><BusiSeq>[@setVariable,BusiSeq,10,0,0@]</><NoteMsg></><TranSeq>[@setVariable,sa,10,0,0@]</><RspCode>[@setVariable,YQ06415,10,0,0@]</><RspMsg>[@setVariable,YQ06417,50,0,0@]</><FinancialDate>[@setVariable,date_10,10,0,0@]</><sjzmessage>[@setVariable,yewubaowen,9999,0,0@]</sjzmessage><OppOperList></>";
//		System.out.print(CommunicateUtil.sendMessage("2", "127.0.0.1", 6051, message1, "100000"));
//		String xmlMessage="<?xml version=\"1.0\" encoding = \"GBK\"?><root><head><reqflag>1</reqflag><msgtype>01</msgtype><tr_code>A00001</tr_code><corp_no></corp_no><oreq_no></oreq_no><tr_acdt>1324</tr_acdt><tr_time>1234</tr_time><ans_code>1324</ans_code><ans_info>1324</ans_info><particular_code>1324</particular_code><particular_info></particular_info><reserved>2</reserved></head><body><amt></amt><amt1></amt1><amt2></amt2><free_use1></free_use1><free_use2></free_use2><free_use3></free_use3><free_use4></free_use4><free_use5></free_use5><free_use6></free_use6></body></root>";
//		Map map=XMLTools.XmlString2Map(xmlMessage);
//		for(Object key:map.keySet()){
//			System.out.println(key+"==="+map.get(key));
//		}
//		System.out.print(XMLTools.GetXMLFromMap(map,0));
//		String xml = MessageCtrMain.encapsulatedPackets("2", CommonUtil.getFullURL(Constants.msgPath+"00063100")+"/REQ_YECX.xml", map, "123123333321");
//		String rexml = CommunicateUtil.sendMessage(send, "127.0.0.1", 10030, xml, "100000");
//		String rexml = "<?xml version=\"1.0\" encoding = \"UTF-8\"?><root><head><reqflag>1</reqflag><msgtype></msgtype><tr_code>600012</tr_code><corp_no>494432304</corp_no><oreq_no></oreq_no><tr_acdt></tr_acdt><tr_time></tr_time><ans_code>0</ans_code><ans_info>交易处理成功...</ans_info><particular_code>0000</particular_code><particular_info>交易处理成功...</particular_info><reserved></reserved></head><body><unitaccname>111</unitaccname><unitaccnum>111</unitaccnum><accnum>1</accnum><accname>1</accname><accinstcode>1</accinstcode><peraccstate>1</peraccstate><basenumber>1</basenumber><indiprop>1</indiprop><balance>1</balance><ypayamt>1</ypayamt><ydrawamt>1</ydrawamt><keepbal>1</keepbal><lastpaydate>1</lastpaydate></body></root>";
		String message1="<root><head><tr_code>700001</tr_code><reqflag>0</reqflag><msgtype>11</msgtype><corp_no>00000001</corp_no><user_no>9999</user_no><req_no>10000000001</req_no><oreq_no>10000000001</oreq_no><tr_acdt>2015-11-17</tr_acdt><tr_time>052551</tr_time><channel>8</channel><sign>01</sign><key>0</key><reserved>0</reserved></head><body><certinum>130302195703163510</certinum><cardno>0</cardno><pwd>000000</pwd></body></root>";
//		HashMap resultMap = MessageCtrMain.analysisPacket("5","D:/work/研发部svn/xt/03 在建项目/15 移动互联公积金应用项目（2713050003）/02 开发管理/90 代码/01.work/YFMAP/src/com/yondervision/yfmap/config/messagetemp/00063100/REQ_A00013.txt", message1, "12312321");
//		for(Object key:resultMap.keySet()){
			System.out.println(message1.getBytes("GBK").length);
//		}
	}
}
