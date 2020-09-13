/********************************************************** 
 * @CopyRright (c) 2011:www.yondervision.com.cn
 * @ProjectName: yqzl
 * @PackageName: com.yondervison.tools
 * @ClassName: Tools.java
 * @Comments: 
 * @JDK version: JDK jdk1.6.0_10
 * @Author: 林小龙
 * @Create Date：2011-6-17 
 * @Modified By：
 * @Modified Date:                               
 *  Why & What is modified
 * @Version: 1.0
 ***********************************************************/
package com.yondervision.yfmap.util;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * <pre>
 * 常用工具类
 * </pre>
 * 
 * @PackageName: com.yondervison.tools
 * @FileName: Tools.java
 * @author 林小龙
 */
public class Tools {

	/**
	 * <pre>
	 *  调用指定类名的指定static方法名的方法,不支持原子参数的方法调用
	 * </pre>
	 * 
	 * @param className
	 *            方法所在的类名
	 * @param method
	 *            要调用的方法名
	 * @param objects
	 *            调用方法所需的参数
	 * @return 调用方法的返回值
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public static Object invokeStaticMethod(String className,
			String methodname, Object... objects) throws Exception {
		Class[] objClass = new Class[objects.length];
		for (int i = 0; i < objects.length; i++) {
			objClass[i] = objects[i].getClass();
		}
		Class proxy = Class.forName(className);
		Method method = proxy.getDeclaredMethod(methodname, objClass);
		return method.invoke(proxy, objects);
	}

	/**
	 * <pre>
	 * 调用指定对象的指定方法名的方法
	 * </pre>
	 * 
	 * @param proxy
	 *            方法所在的对象
	 * @param method
	 *            要调用的方法名
	 * @param objects
	 *            调用方法所需的参数
	 * @return 调用方法的返回值
	 * @throws Exception 
	 * @throws SecurityException 
	 * @throws Throwable
	 */
	@SuppressWarnings("unchecked")
	public static Object invokeMethod(Object proxy, String methodname,
			Object... objects) throws Exception {
		Class[] objClass = new Class[objects.length];
		for (int i = 0; i < objects.length; i++) {
			objClass[i] = String.class;
		}
		Method method = proxy.getClass()
				.getDeclaredMethod(methodname, objClass);
		return method.invoke(proxy, objects);
	}

	/**
	 * 压缩.gz文件
	 * @param fileName 待压缩的文件名（全路径）
	 * @return 压缩后的文件名（全路径），压缩失败返回null
	 */
	public static String compressionGz(String fileName) {
		String toFileName = null;
		try {
			toFileName = fileName + ".gz";
			GZIPOutputStream out = new GZIPOutputStream(new FileOutputStream(
					toFileName));
			InputStream in = new FileInputStream(fileName);

			byte[] buf = new byte[1024];
			int len;
			while ((len = in.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			in.close();
			out.finish();
			out.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toFileName;
	}

	/**
	 * 解压缩.gz文件
	 * @param fileName 待解压文件名（全路径）
	 * @return 解压后文件名（全路径），解压失败返回null
	 */
	public static String decompressionGz(String fileName) {
		String toFileName = null;
		try {
			GZIPInputStream gzi = new GZIPInputStream(new FileInputStream(
					fileName));
			int to = fileName.lastIndexOf('.');

			toFileName = fileName.substring(0, to);
			BufferedOutputStream bos = new BufferedOutputStream(
					new FileOutputStream(toFileName));
			int b;
			byte[] d = new byte[1024];
			try {

				while ((b = gzi.read(d)) > 0) {
					bos.write(d, 0, b);
				}

			} catch (Exception err) {
				err.printStackTrace();
			}
			gzi.close();
			bos.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return toFileName;
	}
	
	public static String getMessage(String msg){
		String[] msgs = msg.split("###");
		if(msgs.length>1){
			return msgs[1];
		}else{
			return msg;
		}
	}
	
	public static String getCode(String msg){
		String[] msgs = msg.split("###");
		if(msgs.length>1){
			return msgs[0];
		}else{
			return msg;
		}
	}
	
	/**
	 * 手机号脱敏处理
	 * @param tel
	 * @return
	 */
	public static String getDesensitizationTel(String tel){
		StringBuffer value = new StringBuffer();
		int size = tel.length();
		value.append(tel.substring(0, 3));
		for(int i=0;i<(size-7);i++){
			value.append("*");
		}
		value.append(tel.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 银行卡号脱敏处理
	 * @param bank
	 * @return
	 */
	public static String getDesensitizationBank(String bank){
		StringBuffer value = new StringBuffer();
		int size = bank.length();
		value.append(bank.substring(0, 4));
		for(int i=0;i<(size-8);i++){
			value.append("*");
		}
		value.append(bank.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 证件号码脱敏处理
	 * @param certinum
	 * @return
	 */
	public static String getDesensitizationCertinum(String certinum){
		StringBuffer value = new StringBuffer();
		int size = certinum.length();
		value.append(certinum.substring(0, 3));
		for(int i=0;i<(size-7);i++){
			value.append("*");
		}
		value.append(certinum.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 个人公积金账号脱敏处理
	 * @param accnum
	 * @return
	 */
	public static String getDesensitizationAccnum(String accnum){
		StringBuffer value = new StringBuffer();
		int size = accnum.length();
		value.append(accnum.substring(0, 3));
		for(int i=0;i<(size-7);i++){
			value.append("*");
		}
		value.append(accnum.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 个人客户号脱敏处理
	 * @param custid
	 * @return
	 */
	public static String getDesensitizationCustid(String custid){
		StringBuffer value = new StringBuffer();
		int size = custid.length();
		value.append(custid.substring(0, 3));
		for(int i=0;i<(size-6);i++){
			value.append("*");
		}
		value.append(custid.substring(size-3));
		return value.toString();
	}
	
	/**
	 * 单位公积金账号脱敏处理
	 * @param unitaccnum
	 * @return
	 */
	public static String getDesensitizationUnitaccnum(String unitaccnum){
		StringBuffer value = new StringBuffer();
		int size = unitaccnum.length();
		value.append(unitaccnum.substring(0, 3));
		for(int i=0;i<(size-7);i++){
			value.append("*");
		}
		value.append(unitaccnum.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 单位客户号脱敏处理
	 * @param unitcustid
	 * @return
	 */
	public static String getDesensitizationUnitcustid(String unitcustid){
		StringBuffer value = new StringBuffer();
		int size = unitcustid.length();
		value.append(unitcustid.substring(0, 3));
		for(int i=0;i<(size-6);i++){
			value.append("*");
		}
		value.append(unitcustid.substring(size-3));
		return value.toString();
	}
	
	/**
	 * 借款合同号脱敏处理
	 * @param unitcustid
	 * @return
	 */
	public static String getDesensitizationLoancontrnum(String loancontrnum){
		StringBuffer value = new StringBuffer();
		int size = loancontrnum.length();
		value.append(loancontrnum.substring(0, 2));
		for(int i=0;i<(size-6);i++){
			value.append("*");
		}
		value.append(loancontrnum.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 贷款账号脱敏处理
	 * @param lnaccnum
	 * @return
	 */
	public static String getDesensitizationLnaccnum(String lnaccnum){
		StringBuffer value = new StringBuffer();
		int size = lnaccnum.length();
		value.append(lnaccnum.substring(0, 6));
		for(int i=0;i<(size-10);i++){
			value.append("*");
		}
		value.append(lnaccnum.substring(size-4));
		return value.toString();
	}
	
	
	/**
	 * 株洲
	 * 职工金账号脱敏处理
	 * @param accnum
	 * @return
	 */
	public static String getZZDesensitizationAccnum(String accnum){
		StringBuffer value = new StringBuffer();
		int size = accnum.length();
		value.append(accnum.substring(0, 2));
		for(int i=0;i<(size-6);i++){
			value.append("*");
		}
		value.append(accnum.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 株洲
	 * 单位账号脱敏处理
	 * @param unitaccnum
	 * @return
	 */
	public static String getZZDesensitizationUnitaccnum(String unitaccnum){
		StringBuffer value = new StringBuffer();
		int size = unitaccnum.length();
		value.append(unitaccnum.substring(0, 0));
		for(int i=0;i<(size-4);i++){
			value.append("*");
		}
		value.append(unitaccnum.substring(size-4));
		return value.toString();
	}
	
	/**
	 * 株洲
	 * 借款合同号脱敏处理
	 * @param unitcustid
	 * @return
	 */
	public static String getZZDesensitizationLoancontrnum(String loancontrnum){
		StringBuffer value = new StringBuffer();
		int size = loancontrnum.length();
		value.append(loancontrnum.substring(0, 0));
		for(int i=0;i<(size-4);i++){
			value.append("*");
		}
		value.append(loancontrnum.substring(size-4));
		return value.toString();
	}
	
}
