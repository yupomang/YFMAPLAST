package com.yondervision.yfmap.RSP.server;

import java.io.File;
import java.lang.reflect.Method;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;

import com.ydyd.pool.DataPool;
import com.yondervision.yfmap.RSP.util.PersistentTool;
import com.yondervision.yfmap.common.FtpUtil;

public class RspServerUtil {
	public static void setFormBean(HttpServletRequest request, Object bean) {
		Class c = bean.getClass();
		Method[] ms = c.getMethods();
		for (int i = 0; i < ms.length; i++) {
			String name = ms[i].getName();

			if (name.startsWith("set")) {
				Class[] cc = ms[i].getParameterTypes();
				if (cc.length == 1) {
					String type = cc[0].getName(); // parameter type

					try {
						// get property name:
						String prop = Character.toLowerCase(name.charAt(3))
								+ name.substring(4);
						// get parameter value:
						String param = request.getParameter(prop.toLowerCase());
						System.out.println("key="+prop.toLowerCase()+" val=" + param);
						if (param != null && !param.equals("")) {
							// ms.setAccessible(true);
							if (type.equals("java.lang.String")) {

								ms[i].invoke(bean, new Object[] { param });
							} else if (type.equals("int")
									|| type.equals("java.lang.Integer")) {
								ms[i].invoke(bean, new Object[] { new Integer(
										param) });
							} else if (type.equals("long")
									|| type.equals("java.lang.Long")) {
								ms[i].invoke(bean, new Object[] { new Long(
										param) });
							} else if (type.equals("boolean")
									|| type.equals("java.lang.Boolean")) {
								ms[i].invoke(bean, new Object[] { Boolean
										.valueOf(param) });
							} else if (type.equals("DataPool")
									|| type.equals("com.ydyd.pool.DataPool")) {
								DataPool pool = new DataPool();
								String[] infos = param.split("</>");
								for (int j = 0; j < infos.length; j++) {
									int wz = infos[j].indexOf(">");
									String key = infos[j].substring(1, wz);
									//����������е���������[��$���ʱ����Ϊ�������ұ�ǩ��ΪspecialText 2010-10-27 sxl
									if(key.indexOf("specialText")==-1){
										String value = infos[j].substring(wz + 1);
										pool.put(key, value);
									}else{
										String name1=key.substring(key.indexOf("name")+5,key.length());
										System.out.println(name1+"======");
										String value = infos[j].substring(wz + 1);
										pool.put(name1, value);
									}
									//
								}
								ms[i].invoke(bean, new Object[] { pool });
							} else if (type.equals("File")|| type.equals("java.io.File")) {//����õ���Ϣ����ΪFile����
								FtpUtil myFTP = new FtpUtil();// �½�һ��FTP��������							 
								String fileName = param;//�������ļ������
								File file =myFTP.getFileByServer(fileName);
							    ms[i].invoke(bean, new Object[] { file });//�������ļ���·�������set��bean��
							    myFTP.deletefromFTP(fileName);
								 
								 
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static String elementToString(Element Root) {
		Document doc = new Document(Root);
		StringBuffer html = new StringBuffer();
		 
		// DOM����Ϊ�գ���ʼת���ַ�
		if (doc != null) {
			String temp_xml = null;
			// ���xml�������
			Format format = Format.getCompactFormat();  
			format.setEncoding("GBK");  
			XMLOutputter xmlOut = new XMLOutputter(format); 
			//XMLOutputter xmlOut = new XMLOutputter();
			//xmlOut.setEncoding("GBK");		 
			temp_xml = xmlOut.outputString(doc);
			// ɾ��xml�ַ��xml������
			//int pos = temp_xml.indexOf("\r");
			//temp_xml = temp_xml.substring(pos + 1);
			html.append(temp_xml);
		}
		return html.toString();
	}

	
	public synchronized static int getKey(String seqObj){
    	try{
      	seqObj=seqObj.toUpperCase();
      	//YYTPORSP po=new YYTPORSP();
      	//查询
    	String sql = "select SEQNO from portal_sysseq where seqObj='"+seqObj+"'";
    	PersistentTool.conDB();
    	ResultSet rs = PersistentTool.excuteQuery(sql.toString());
		
    	//po.queryBean("select SEQNO from portal_sysseq where seqObj='"+seqObj+"'");
    	int xh=1;
    	try{
    		if(rs!=null)
			{
				// 同一借款人存在处理中或未处理申报时，不能再重复提出
				if (rs.getRow()>0) {
					xh=rs.getInt("SEQNO");
					PersistentTool.executeUpdate("update portal_sysseq set SEQNO=SEQNO+1 where seqObj='"+seqObj+"'");
				}
			}else
			{
				//执行update
				PersistentTool.executeUpdate("insert into portal_sysseq (SEQOBJ,SEQNO) values('"+seqObj+"',1)");
			}
    	}catch(Exception e){
    	}
    	System.out.println("[-]getKey xh="+xh);
    	PersistentTool.closeDB();
    	return xh;
    	}catch(Exception e1){
    		e1.printStackTrace();
    		throw new RuntimeException(e1.getMessage());
    		
    	}
    	//System.out.println("[+]getKey(String seqObj)"+seqObj);
    	//return Integer.parseInt(getKey(seqObj,false)+""+SetCharacterEncodingFilter.bzw);
    }
	
	public synchronized static int getSEQ(String seqObj){
    	try{
      	seqObj=seqObj.toUpperCase();
      	//YYTPORSP po=new YYTPORSP();
      	//查询
    	String sql = "select "+seqObj+" from dual";
    	PersistentTool.conDB();
    	ResultSet rs = PersistentTool.excuteQuery(sql.toString());
		
    	//po.queryBean("select SEQNO from portal_sysseq where seqObj='"+seqObj+"'");
    	int xh=1;
    	try{
    		if(rs!=null)
			{
				// 同一借款人存在处理中或未处理申报时，不能再重复提出
				if (rs.getRow()>0) {
					xh=rs.getInt("nextval");
					//PersistentTool.executeUpdate("update portal_sysseq set SEQNO=SEQNO+1 where seqObj='"+seqObj+"'");
				}
			}else
			{
				//执行update
				//PersistentTool.executeUpdate("insert into portal_sysseq (SEQOBJ,SEQNO) values('"+seqObj+"',1)");
			}
    	}catch(Exception e){
    	}
    	System.out.println("[-]getKey xh="+xh);
    	PersistentTool.closeDB();
    	return xh;
    	}catch(Exception e1){
    		e1.printStackTrace();
    		throw new RuntimeException(e1.getMessage());
    		
    	}
    	//System.out.println("[+]getKey(String seqObj)"+seqObj);
    	//return Integer.parseInt(getKey(seqObj,false)+""+SetCharacterEncodingFilter.bzw);
    }

	public static void main(String[] args) {
		System.out.println(getSEQ("INSTANCE_SEQ.nextval"));
	}
    
}
