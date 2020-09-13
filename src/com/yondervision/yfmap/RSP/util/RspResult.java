/********************************************************** 
 * @CopyRright (c) 2010:www.yondervision.com.cn
 * @ProjectName: 深圳市住房公积金
 * @ClassName: RspResult.java
 * @Comments: 
 * @JDK version: JDK 1.5.0_16
 * @Author:HanZhanYuan
 * @Create Date：2010-8-16
 * @Modified By：hanzhanyuan
 * @Modified Date:2011-8-6                               
 *  Why & What is modified
 *  修改原因：添加json格式的数据 
 * @Version: 1.2
 ***********************************************************/
package com.yondervision.yfmap.RSP.util;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.jdom.Attribute;
import org.jdom.Element;

import com.ydyd.pool.DataPool;
import com.yondervision.yfmap.RSP.bean.BaseBean;
import com.yondervision.yfmap.RSP.result.Result;
import com.yondervision.yfmap.common.FtpUtil;


public class RspResult {
	Result rs = null;

	final String rs_counter = "rs_counter";

	final String rs_colnames = "rs_colnames";

	HashMap<String, Object> sigleData = new HashMap<String, Object>();

	public RspResult() {
		this.rs = new Result();
	}

	public void setBeanToSigle(BaseBean bean) {
		String names[] = bean.getColNames();
		for (int i = 0; i < names.length; i++) {
			sigleData.put(names[i], bean.get(names[i]));
		}
	}

	public Element toXmlElement() {		
		Element root = new Element("root");
		for (String key : this.sigleData.keySet()) {
			if (this.sigleData.get(key) != null)
				if (this.sigleData.get(key) instanceof DataPool) {
					DataPool pool = (DataPool) this.sigleData.get(key);
					String str = "0";
					for (Object key0 : pool.keySet()) {
						str +="," + key0 + "=" + pool.get(key0);
					}
					root.setAttribute(key, str);
				} else {
					root.setAttribute(key, "1"+this.sigleData.get(key).toString());
				}
		}
		this.getCounter();
		root.setAttribute(rs_counter, "1"+rs.getCounter() + "");
		BaseBean[] beans = rs.getBaseBeans();
		if (beans != null)
			for (int i = 0; i < beans.length; i++) {
				String[] names = beans[i].getColNames();
				if (i == 0) {
					String strcolnames = "";
					for (int j = 0; j < names.length; j++) {
						if (j != 0)
							strcolnames += ",";
						strcolnames += names[j];
					}
					root.setAttribute(rs_colnames, "1"+strcolnames);
				}
				Element row = new Element("row");
				for (int j = 0; j < names.length; j++) {
					Element data = new Element(names[j]);
					if (beans[i].get(names[j]) instanceof DataPool) {
						DataPool pool = (DataPool) beans[i].get(names[j]);
						for (Object key : pool.keySet()) {
							if (pool.get(key) != null) {
								/*Element keyNode = new Element(key.toString());
								keyNode.setText(pool.get(key).toString());
								data.addContent(keyNode); */
								// 2010-10-27 sxl 深圳 过滤xml中不能读取的特殊字符，将其转换为属性而不是标签
					 
								 String regEx="[$\\[\\]]";
							    Pattern p = Pattern.compile(regEx);      
							    Matcher m = p.matcher(key.toString()); 
							    boolean b = m.find() ;
								//boolean b=key.toString().indexOf("[")!=-1;
								if(!b){
									Element keyNode = new Element(key.toString());
									keyNode.setText(pool.get(key).toString());
									data.addContent(keyNode);//2010-11-3 hzy 我加了这句话,27号小兰漏掉了
								}else{
									Element keyNode = new Element("specialText");
									keyNode.setAttribute("name",key.toString());
									keyNode.setText(pool.get(key).toString());
									data.addContent(keyNode);
								}
							}
						}
					} else {
						if(beans[i].get(names[j])!=null)
							data.setText(beans[i].get(names[j]).toString());
					}
					row.addContent(data);
				}
				root.addContent(row);
			}
		return root;
	}

	public RspResult(Element root) {
		List list = root.getAttributes();
		for (int i = 0; i < list.size(); i++) {
			Attribute aib = (Attribute) list.get(i);
			if (aib.getValue().substring(0, 1).equals("0")) {
				//第一个字符，说明数据是数据总线类型，其格式是0,aaa=bb,aaaf=sss
				DataPool pool = new DataPool();
				String[] str = aib.getValue().split(",");				 
				if(str.length>1)
				for (int j = 1; j < str.length; j++) {
				    if(str[j].split("=").length>1)
					pool.put(str[j].split("=")[0], str[j].split("=")[1]);
				}
				sigleData.put(aib.getName().toUpperCase(), pool);
			} else {
				sigleData.put(aib.getName().toUpperCase(), aib.getValue().substring(1, aib.getValue().length() ));
			}
		}
		String[] names = null;
		if (sigleData.get(rs_colnames.toUpperCase()) != null) {
			names = sigleData.get(rs_colnames.toUpperCase()).toString().split(",");
		} 
		list = root.getChildren();
		Vector vec = new Vector();
		for (int i = 0; i < list.size(); i++) {
			Element row = (Element) list.get(i);
			List dlist = row.getChildren();
			BaseBean bean = new BaseBean();
			bean.setColNames(names);
			for (int j = 0; j < dlist.size(); j++) {
				Element node = (Element) dlist.get(j);
				if (node.getChildren().size() != 0) {
					DataPool pool = new DataPool();

					List plist = node.getChildren();
					for (int k = 0; k < plist.size(); k++) {
						Element pnode = (Element) plist.get(k);
						pool.put(pnode.getName(), pnode.getText());
					}
					bean.put(node.getName(), pool);
				} else {
					bean.put(node.getName(), node.getText());
				}
			}
			vec.add(bean);
		}
		BaseBean[] beans = new BaseBean[list.size()];
		vec.copyInto(beans);
		this.rs = new Result();
		this.rs.setBaseBeans(beans);
		this.rs.setResultSize(10);
		this.rs.setCounter(0);
		if (sigleData.get(rs_counter.toUpperCase()) != null) {
			this.rs.setCounter(Integer.parseInt(sigleData.get(rs_counter.toUpperCase()).toString()));
		}else {
			this.rs.setCounter(0);
		} 
	}

	
	public boolean getfile(String filename){	 
		FtpUtil ftp=new FtpUtil();	 
	    ftp.getFileByServer(filename);
		return true;
	}
	
	
	public RspResult(Result rs) {
		this.rs = rs;
	}

	public void put(String key, Object value) {
		key = key.toUpperCase();
		sigleData.put(key, value);
	}

	public Object get(String key) {

		key = key.toUpperCase();
		return sigleData.get(key);
	}

	public BaseBean[] getBaseBeans() {
		return rs.getBaseBeans();
	}

	public String[] getColNames() {
		return rs.getColNames();
	}

	public String[] getColTypeNames() {
		return rs.getColTypeNames();
	}

	public int getCounter() {
		return rs.getCounter();
	}

	public int getCurrentPageNo() {
		return rs.getCurrentPageNo();
	}

	public int getMaxPageNo() {
		return rs.getMaxPageNo();
	}

	public Vector getResult() {
		return rs.getResult();
	}

	public int getResultSize() {
		return rs.getResultSize();
	}

	public int getStartPosition() {
		return rs.getStartPosition();
	}

	public int getStartPosition(int pageNo) {
		return rs.getStartPosition(pageNo);
	}

	public int hashCode() {
		return rs.hashCode();
	}

	public void setBaseBeans(BaseBean[] baseBeans) {
		rs.setBaseBeans(baseBeans);
		rs.setCounter(baseBeans.length);
	}

	public void setColNames(String[] colNames) {
		rs.setColNames(colNames);
	}

	public void setColTypeNames(String[] colTypeNames) {
		rs.setColTypeNames(colTypeNames);
	}

	public void setCounter(int newCounter) {
		rs.setCounter(newCounter);
	}

	public void setResult(Vector result) {
		rs.setResult(result);
	}

	public void setResultSize(int newResultSize) {
		rs.setResultSize(newResultSize);
	}

	public void setStartPosition(int newStartPosition) {
		rs.setStartPosition(newStartPosition);
	}

	public String toString() {
		return rs.toString();
	}
	/**
	 * 将结果转换为json对象
	 * @author hzy	 * 
	 * */
	public JSONObject toJson(){
		JSONObject obj=new JSONObject();
		obj.put("total", new Integer(this.getCounter()));
		for(String key:sigleData.keySet()){			
			obj.put(key,this.sigleData.get(key));
		}
		JSONArray ary=new JSONArray();
		obj.put("rows", ary);
		BaseBean[] beans=this.getBaseBeans();
		for(int i=0;i<beans.length;i++){
			JSONObject rec=new JSONObject();
			for(int j=0;j<beans[i].getColNames().length;j++){
				String key=beans[i].getColNames()[j];
				Object value=beans[i].get(j);
				rec.put(key, value);				
			}
			ary.add(rec);
		}
		return obj;
	}
	
}
