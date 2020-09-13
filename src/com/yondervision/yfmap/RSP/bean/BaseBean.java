/*********************************************************
 * File:BaseBean.java
 * 
 * Version 1.0
 * 
 * Date     2004-1-2
 * Author   HuangYong
 * 
 * Copyright (C) 2003 ydyd.
 * all rights reserved.
 * 
 ********************************************************/

package com.yondervision.yfmap.RSP.bean;

import java.io.Serializable;
import java.util.HashMap;

/**
 * @author HuangYong
 * ˵�����洢����е���ݣ���Ҫ���������ѯ
 */
public class BaseBean implements Serializable{
    private String[] colNames=null;
    private String[] colTypeNames=null;
    private HashMap hashmap = new HashMap();
    
    public int length = 0;
    
    /** table name */
    public String tableName = null;
    
    /** sql where 
     *  like "columnName='something'"
     *  if your sql is updateSQL or deleteSQL,
     *  the whereClause must be not null
     **/
    public String whereClause = null;
    
    public String getWhereClause() {
        return whereClause;
    }
    /** set sql where 
     *  like " columnName='something'"
     *  if your sql is updateSQL or deleteSQL,
     *  the whereClause must be not null
     **/
    public void setWhereClause(String whereClause) {
        this.whereClause = whereClause;
    }

    public String getTableName() {
        return tableName;
    }

    public HashMap getHashMap() {
        return hashmap;
    }
    /** table name */
    public void setTableName(String tableName) {
        this.tableName = tableName;
    }

    public void put(Object obj1, Object obj2) {
        this.hashmap.put(obj1.toString().toUpperCase(), obj2);
    }
    
    public Object get(String name) {
        return this.hashmap.get(name.toUpperCase());
    }
    public Object get(int i) {
        return this.hashmap.get(colNames[i].toUpperCase());
    }
    /**
     * @return int
     */
    public int getLength() {
        return length;
    }

    public int getSize() {
        return hashmap.size();
    }
    
    /**
     * Sets the length.
     * @param length The length to set
     */
    public void setLength(int length) {
        this.length = length;
    }

	public String[] getColNames() {
		return colNames;
	}

	public void setColNames(String[] colNames) {
		this.colNames = colNames;
	}

	public String[] getColTypeNames() {
		return colTypeNames;
	}

	public void setColTypeNames(String[] colTypeNames) {
		this.colTypeNames = colTypeNames;
	}
	

}
