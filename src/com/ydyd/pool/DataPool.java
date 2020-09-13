package com.ydyd.pool;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;

import com.yondervision.yfmap.RSP.util.DateUtility;
import com.yondervision.yfmap.util.Datelet;






public class DataPool extends HashMap implements Serializable{
    
    private final String SYSDATE = "@SysDate";
    private final String SYSYEAR = "@SysYear";
    private final String SYSMONTH = "@SysMonth";
    private final String SYSDAY   = "@SysDay";
    private final String SYSWEEK  = "@SysWeek";
    private final String SYSTIME = "@SysTime";
    
    /**
     * Ĭ�Ϲ��캯��
     *
     */
    public DataPool(){
        super();
    }
    /**
     * ���캯����ָ���Ĵ�С��ɹ��캯��
     * @param initialCapacity
     */
    public DataPool(int initialCapacity){
        super(initialCapacity);
    }
   
    /**
     * ���캯��
     * @param hashmap
     */
    public DataPool(HashMap hashmap){
        super(hashmap);
    }
    
    public Object getObject(String name){
        Object value = get(name);
        return value;
    }
    
    /**
     * ��ݱ�����ƻ�ñ�����ֵ
     * 
     * Date��2004-4-22
     * @author�����
     * @param name
     * @return String
     */
    public String getString(String name) {
        String temp = getSystDefaultValue(name);
        if(temp == null || temp.length() <= 0){
            //��ݱ�������
            Object value = get(name);

            if(value!=null){
                temp = value.toString();
            }
            else{
                temp = null;
            }
        }
        return temp;
    }
    
    /**
     * ��ݱ�����������λ�û��ֵ
     * @author:���
     * @param name ��¼����е����
     * @param key  ��¼����е�λ�ã���1��ʼ
     * @return
     */
    public String getString(String name,int key){
        //��ݱ�������
        Object value = get(name + "[" + key + "]");
        if(value!=null){
            return value.toString();
        }
        else{
            return "";
        }         
    }
    
    /**
     * 
     * @param name
     * @return
     */
    private String getSystDefaultValue(String name) {
        String temp= "";
        if(name == null || name.length() == 0)
            temp = "";
        else if(name.equalsIgnoreCase(SYSDATE)){
            temp = DateUtility.getStringOfCurrentDate();
        }
        else if(name.equalsIgnoreCase(SYSYEAR)){
            temp = String.valueOf(Datelet.getInstance().getYear());
        }
        else if(name.equalsIgnoreCase(SYSMONTH)){
            temp = String.valueOf(Datelet.getInstance().getMonth());
            if(temp.length()<2){
                temp = "0" + temp;
            }
        }
        else if(name.equalsIgnoreCase(SYSDAY)){
            temp = String.valueOf(Datelet.getInstance().getDay());
        }
        else if(name.equalsIgnoreCase(SYSWEEK)){
            temp = String.valueOf(Datelet.getInstance().getSysWeek());
        }
        else if(name.equalsIgnoreCase("@SysTime"))
                temp = String.valueOf(Datelet.getCurrentTime());
        return temp;
    }
    
   
    /**
     * ��ݱ������ò���ֵ
     * @author:���
     * @param name
     * @return
     */
    public boolean getBoolean(String name){
        Object value = get(name);
        if(value instanceof Boolean){
            Boolean bool = (Boolean)value;
            return bool.booleanValue();
        }
        return true;
    }
    
    /**
     * ��ݱ�����ƻ������ֵ
     * @author:���
     * @param name
     * @return
     */
    public int getInt(String name){
        Object value = get(name);
        int temp = 0;
        if(value==null) temp = 0;
        else if(value instanceof Integer){
            Integer i = (Integer)value;
            temp = i.intValue();
        }
        return temp;
    }
    
    /**
     * ��ݱ�����ƻ��˫����ֵ
     * @author:���
     * @param name
     * @return
     */
    public double getDouble(String name) {
        Object value = get(name);
        double temp = 0.00;
        if(value == null) ;
        else if(value instanceof Double){
            Long l = (Long)value;
            long v = l.longValue();
            temp = (double)v;
        }
        return temp;    
    }
    
    /**
     * ��ȡʱ�������ֶΡ�
     * Date��2004-4-22
     * @author�����
     * @param name
     * @return String
     */
    public Timestamp getTimestamp(String name) {
        Object value = get(name);
        if (value == null)
            return null;
        if (value instanceof Timestamp)
            return (Timestamp) value;
        return null;
    }
    
    /**
     * ������������ֶ�
     * Date��2004-4-22
     * @author�����
     * @param name
     * @return
     */
    public Date getDate(String name){
        Object value = get(name);
        Date date = null;
        
        if(value == null)
            return date;
        else if(value instanceof Date)
            date = (Date) value;
        
        return date;    
    }
}