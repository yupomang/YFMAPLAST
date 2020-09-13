package com.yondervision.yfmap.common.mess;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;

import com.yondervision.yfmap.common.Constants;
import com.yondervision.yfmap.common.exp.CenterRuntimeException;
import com.yondervision.yfmap.common.security.EncryptionInfo;
import com.yondervision.yfmap.messagectrl.MessageCtrMain;

public class FSPSendMessage extends AbsSendMessage{
	
	/**
	 * 通信方法
	 * @param message
	 * @throws Exception
	 */
	public void send() throws CenterRuntimeException {
		Socket socket = null;
		try{
			socket = new Socket(ip, port);
			socket.setSoTimeout(TIME_OUT);
			this.out = socket.getOutputStream();
			this.in = socket.getInputStream();
			encryFlag =  EncryptionInfo.getEncryptionFlag();
			log.info(Constants.LOG_HEAD+"发送报文TO FSP：" + upItem);
			this.sendXML();
			this.readXML();
			log.info(Constants.LOG_HEAD+"FSP 接收报文：" + downItem);			
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
				if(socket!=null){
					socket.close();
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
			System.out.println("TO FSP: "+String.format("%06d", upItem.getBytes("GBK").length+36));
			out.write(String.format("%06d", upItem.getBytes("GBK").length+36).getBytes());//6位长度报文头+36左面补领
			System.out.println("TO FSP: "+("0"+encryFlag+"33"));
			out.write(("0"+encryFlag+"33").getBytes());//第一个零就是0第二个0是加密标志 0不加密1加密 33是APP的渠道码固定
			System.out.println("TO FSP: "+(trancode+"                        "));
			out.write((trancode+"                        ").getBytes());//FSP交易码32位右面补充空格
			wit(out,upItem.getBytes("GBK"));
			out.write(new byte[]{-1});//-1后续无文件内容 如果有文件给 127 紧接着 30位文件名 8位文件长度 文件内容 你这没有文件流用不上就不写了。		
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
		try {
			byte[] length=new byte[6];
			byte[] length1=new byte[36];
			in.read(length);
			in.read(length1);//加密标志在里需要自己截取			
			byte[] message=socketRecv(Integer.parseInt(new String(length).trim())-36,in);
			downItem = new String(message,"GBK");
			//System.out.print("收到的报文："+downItem);
		} catch (IOException e) {
			e.printStackTrace();
			throw new CenterRuntimeException(Constants.ERROR_999999,"读取返回信息失败");
		}
	}
	public String getMessageHead(byte[] btMsg){
		int msgLength=btMsg.length;
		String strByteSize = String.valueOf(msgLength+1);
		String head = new StringBuffer().append(
				"000000".substring(strByteSize.length())).append(
				strByteSize).append(
						encryFlag).toString();
		System.out.println("========>封装发往中心报文:"+head);
		return head;
	}
	
	
	public static void wit(OutputStream out,byte[] message){
		int byteSize = 1000;
		byte[] temp = new byte[byteSize];
		int wLine = 0;
	 	while (wLine < message.length) {
			if (wLine + byteSize > message.length) {
				temp = new byte[message.length - wLine];
			}
			System.arraycopy(message, wLine, temp, 0, temp.length);
			try {
				out.write(temp);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			wLine += temp.length;
		}
	}
	
	public static byte [] socketRecv(int lengthTagLen,InputStream in)   {
		byte[] bTemp = null;
		byte[] bxml = null;
		try {		
			int TotalLen = lengthTagLen;//
			int len = 256;
			bTemp = new byte[len];
			bxml = new byte[TotalLen];
			int curLen = 0;		
			while (curLen < TotalLen) {
				int t = 0;
				if (curLen + len < TotalLen) {
					t = in.read(bTemp);
				} else {
					t = in.read(bTemp, 0, TotalLen - curLen);
				}
				if (t == -1) {
					break;
				}
				System.arraycopy(bTemp, 0, bxml, curLen, t);
				curLen += t;
			}			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return bxml;
	}
}
