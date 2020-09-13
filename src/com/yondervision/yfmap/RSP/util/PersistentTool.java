package com.yondervision.yfmap.RSP.util;

/******************************************************************************
 File: PersistentTool.java

 Version 2.0
 Date          	  Author                Changes
 Apirl.07,2004     hanzy               Create version 2.0
 Copyright (c) 2003-2008, yondervision.com.cn
 all rights reserved.
 ******************************************************************************/
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.sql.Clob;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Iterator;

import com.yondervision.yfmap.RSP.bean.BaseBean;
import com.yondervision.yfmap.util.PropertiesReader;

/**
 * 实现对象持久化的唯一工具类，该工具类采用单实例模式，似乎不适合采用单实例模式，在多CPU服务器中该单实例模式会影响效率。
 * 为了实现多种类型的持久化方法，该持久化工具最好做成factory模式，暂时只是支持hibernate. *
 * 
 * @author lijc
 */
public class PersistentTool {

	public static Connection con = null;// 创建一个数据库连接
	static Statement st = null;
	static PreparedStatement pre = null;// 创建预编译语句对象，一般都是用这个而不用Statement
	static ResultSet result = null;// 创建一个结果集对象

	public static void conDB() {
		try {
			String PROPERTIES_FILE_NAME = "properties.properties";
			String url = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,
					"oracleUrl00089800").trim();
			String user = PropertiesReader.getProperty(PROPERTIES_FILE_NAME,
					"oracleUser00089800").trim();
			String password = PropertiesReader.getProperty(
					PROPERTIES_FILE_NAME, "oraclePwd00089800").trim();
			Class.forName("oracle.jdbc.driver.OracleDriver");
			// 加载Oracle驱动程序
			System.out.println("开始尝试连接数据库！");
			// String url = "jdbc:oracle:thin:@59.212.225.188:1521:devdb";//
			// 127.0.0.1是本机地址，XE是精简版Oracle的默认数据库名
			// String user = "nbp";// 用户名,系统默认的账户名
			// String password = "nbp";// 你安装时选设置的密码
			con = DriverManager.getConnection(url, user, password);// 获取连接
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static ResultSet excuteQuery(String sql) {
		System.out.println("连接成功！");
		// sql = "select * from rsp002 ";// 预编译语句，“？”代表参数
		try {
			pre = con.prepareStatement(sql);
			// 实例化预编译语句
			// pre.setString(1, "刘显安");// 设置参数，前面的1表示参数的索引，而不是表中列名的索引
			result = pre.executeQuery();// 执行查询，注意括号中不需要再加参数
			while (result.next())
				// 当结果集不为空时
				return result;
			// System.out.println("学号:" + result.getInt("applyid") + "姓名:"
			// + result.getString("title"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static int excuteDel(String sql) {
		System.out.println("连接成功！");
		// sql = "select * from rsp002 ";// 预编译语句，“？”代表参数
		try {
			st = con.createStatement();

			int n = st.executeUpdate(sql);
			return n;
			// System.out.println("学号:" + result.getInt("applyid") + "姓名:"
			// + result.getString("title"));
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return 0;
	}
	public static void executeUpdate(String sql) {
		System.out.println("连接成功！");
		// sql = "select * from rsp002 ";// 预编译语句，“？”代表参数
		try {
			st = con.createStatement();
			st.executeUpdate(sql);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void executeUpdatePre(String sql, BaseBean curBean)
			throws Exception {
		System.out.println("连接成功！");
		// sql = "select * from rsp002 ";// 预编译语句，“？”代表参数
		try {
			pre = con.prepareStatement(sql);
			HashMap ht = curBean.getHashMap();
			Iterator iterator = ht.keySet().iterator();
			int columnSN = 1;// 字段对应的序号
			while (iterator.hasNext()) {
				Object key = iterator.next();
				Object val = ht.get(key);
				// 根据值的类型调用不同的方法进行设置
				if (val != null) {
					setStatementValue(pre, (columnSN++), val);
				}
			}
			pre.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		excuteQuery("");
	}

	public static void closeDB() {
		try {
			// 逐一将上面的几个对象关闭，因为不关闭的话会影响性能、并且占用资源
			// 注意关闭的顺序，最后使用的最先关闭
			if (result != null)
				result.close();
			if (pre != null)
				pre.close();
			if (con != null)
				con.close();
			System.out.println("数据库连接已关闭！");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 返回添加SQL语句。根据BaseBean，自动拼装删除SQL语句。 该方法可以重写，实现灵活的删除方法。主要是直接嵌入SQL语句。
	 * 
	 * @see #getColumnNames()
	 * @see #getTableName()
	 * @see #delete()
	 * 
	 * @return DELETE SQL
	 */
	public static String getAddSQL(BaseBean curBean) {

		// 动态拼装添加SQL语句
		StringBuffer tempSQL = new StringBuffer(256);
		tempSQL.append("INSERT INTO ");
		tempSQL.append(curBean.getTableName());

		// 从BaseBean中获取条件
		HashMap ht = curBean.getHashMap();
		Iterator iterator = ht.keySet().iterator();
		String costStr = "";
		String columnStr = "(";
		while (iterator.hasNext()) {
			String columnName = (String) iterator.next();
			Object value = ht.get(columnName);
			if (value != null) {
				costStr += " ?,";
				columnStr += columnName + ",";
			}
		}
		// 去除最后一个","
		if (costStr.length() > 0) {
			costStr = costStr.substring(0, costStr.lastIndexOf(","));
		}
		if (columnStr.length() > 0) {
			columnStr = columnStr.substring(0, columnStr.lastIndexOf(","));
		}

		// 添加列名
		tempSQL.append(columnStr);
		tempSQL.append(")");
		tempSQL.append(" VALUES(");
		// 设置待添加的对象
		tempSQL.append(costStr);
		tempSQL.append(")");
		return tempSQL.toString();
	}

	private static void setStatementValue(PreparedStatement statement,
			int columnSN, Object val) throws SQLException,
			FileNotFoundException, Exception {
		if (val == null) {
			return;
		} else if (val instanceof Integer) {
			statement.setInt(columnSN, ((Integer) val).intValue());
		} else if (val instanceof Long) {
			statement.setLong(columnSN, ((Long) val).longValue());
		} else if (val instanceof Double) {
			statement.setDouble(columnSN, ((Double) val).doubleValue());
		} else if (val instanceof java.sql.Time) {
			statement.setString(columnSN, val.toString());
		} else if (val instanceof Timestamp) {
			statement.setTimestamp(columnSN, (Timestamp) val);
		} else if (val instanceof java.sql.Date
				|| val instanceof java.util.Date) {
			statement.setString(columnSN, val.toString());
		} else if (val instanceof String) {
			statement.setString(columnSN, (String) val);
		} else if (val instanceof Boolean) {
			statement.setBoolean(columnSN, ((Boolean) val).booleanValue());
		} else if (val instanceof BigDecimal) {
			statement.setBigDecimal(columnSN, (BigDecimal) val);
		} else if (val instanceof Clob) {
			statement.setClob(columnSN, (Clob) val);
		} else if (val instanceof Timestamp) {
			statement.setTimestamp(columnSN, (Timestamp) val);
		} else if (val instanceof Float) {
			statement.setFloat(columnSN, ((Float) val).floatValue());
		} else if (val instanceof Short) {
			statement.setShort(columnSN, ((Short) val).shortValue());
		} else if (val instanceof ByteArrayOutputStream) {
			ByteArrayOutputStream bos = (ByteArrayOutputStream) val;
			int size = bos.size();
			byte[] bytes = bos.toByteArray();
			ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
			statement.setBinaryStream(columnSN, bis, size);
		}
	}
	
	/**
	 * 返回更新SQL语句。根据BaseBean，自动拼装删除SQL语句。 该方法可以重写，实现灵活的删除方法。主要是直接嵌入SQL语句。
	 * 
	 * @see #getColumnNames()
	 * @see #getTableName()
	 * @see #delete()
	 * 
	 * @return DELETE SQL
	 */
	public static String getUpdateSQL(BaseBean curBean) {

		// 动态拼装更新SQL语句
		StringBuffer tempSQL = new StringBuffer(256);
		tempSQL.append("UPDATE ");
		tempSQL.append(curBean.getTableName());
		String whereClause = getWhereClause(curBean);

		// 从BaseBean中获取条件
		HashMap ht = curBean.getHashMap();
		Iterator iterator = ht.keySet().iterator();
		String costStr = "";
		while (iterator.hasNext()) {
			Object key = iterator.next();
			costStr += key + "= ?,";
		}
		// 去除最后一个","
		if (costStr.length() > 0) {
			costStr = costStr.substring(0, costStr.lastIndexOf(","));
			// 设置待修改的对象
			tempSQL.append(" SET ");
			tempSQL.append(costStr);
		}

		if (whereClause != null) {
			tempSQL.append(" WHERE ");
			// 外部设置进来的条件语句。
			tempSQL.append(whereClause);
		}

		return tempSQL.toString();
	}
	
	/**
	 * 返回复杂的条件语句.
	 */
	private static String getWhereClause(BaseBean curBean) {
		String whereClause = curBean.getWhereClause();
		if (whereClause == null) {
			whereClause = " 1 > 0 ";

			// 从BaseBean中获取条件
			HashMap ht = curBean.getHashMap();
			Iterator iterator = ht.keySet().iterator();
			while (iterator.hasNext()) {
				Object key = iterator.next();
				Object val = ht.get(key);
				// 根据字符集设定条件值
				String value = val.toString();

				// 判断类型，如果不是Integer、Double类型的，都转换为"××"形式
				if (!(val instanceof Integer) && !(val instanceof Double)) {
					value = "'" + val.toString() + "'";
				}
				// 拼接查询条件
				whereClause += " AND " + key + "=" + value;
			}
		} else {
			whereClause = whereClause;
		}
		return whereClause;
	}
	
	/**
	 * 返回删除SQL语句。根据BaseBean，自动拼装删除SQL语句。 该方法可以重写，实现灵活的删除方法。主要是直接嵌入SQL语句。
	 * 
	 * @see #getColumnNames()
	 * @see #getTableName()
	 * @see #delete()
	 * 
	 * @return DELETE SQL
	 */
	public static String getDeleteSQL(BaseBean curBean) {
		// 动态拼装删除SQL语句
		StringBuffer tempSQL = new StringBuffer(256);
		tempSQL.append("DELETE FROM ");
		tempSQL.append(curBean.getTableName());
		String whereClause = getWhereClause(curBean);

		if (whereClause != null) {
			tempSQL.append(" WHERE ");

			// 外部设置进来的条件语句。
			tempSQL.append(whereClause);
		}

		return tempSQL.toString();
	}
	
}