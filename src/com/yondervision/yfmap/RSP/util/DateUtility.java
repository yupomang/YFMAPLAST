/******************************************************************************
 File DateUtility.java

 Version 1.0
 Date            Author          Changes
 Apr.9,2003     Zhu Xuecai     Created

 Copyright (c), 2003
 all rights reserved
 ******************************************************************************/
package com.yondervision.yfmap.RSP.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtility {

	public DateUtility() {
	}

	public static java.util.Date getCurrentDatetime() {
		return new java.util.Date(System.currentTimeMillis());
	}

	public static String getStringOfCurrentDatetime() {
		return datetimeToString(getCurrentDatetime());
	}

	public static String getStringOfCurrentDate() {
		return dateToString(getCurrentDatetime());
	}

	public static String getStringOfDayend(String day) throws Exception {
		if (day == null)
			return null;
		day = day.trim();
		if (day.length() == 0)
			return day;
		else if (day.length() == 10)
			return day + " 24:00:00";
		else if (day.length() == 19)
			return day.substring(0, 10) + " 24:00:00";
		else
			throw new Exception("���ڸ�ʽ����");
	}

	public static String datetimeToString(java.util.Date datetime) {
		if (datetime != null) {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			// formatter.setTimeZone(java.util.TimeZone.getTimeZone("CTT"));
			return formatter.format(datetime);
		}
		return null;
	}

	public static java.util.Date stringToDatetime(String datetime) {
		if (datetime != null) {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			// formatter.setTimeZone(java.util.TimeZone.getTimeZone("CTT"));
			return formatter.parse(datetime, new java.text.ParsePosition(0));
		}
		return null;
	}

	public static String dateToString(java.util.Date date) {
		if (date != null) {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			// formatter.setTimeZone(java.util.TimeZone.getTimeZone("CTT"));
			return formatter.format(date);
		}
		return null;
	}

	public static java.util.Date stringToDate(String date) {
		if (date != null) {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					"yyyy-MM-dd");
			// formatter.setTimeZone(java.util.TimeZone.getTimeZone("CTT"));
			return formatter.parse(date, new java.text.ParsePosition(0));
		}
		return null;
	}

	public static String timeToString(java.util.Date time) {
		if (time != null) {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					"HH:mm:ss");
			// formatter.setTimeZone(java.util.TimeZone.getTimeZone("CTT"));
			return formatter.format(time);
		}
		return null;
	}

	public static java.util.Date stringToTime(String time) {
		if (time != null) {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					"HH:mm:ss");
			// formatter.setTimeZone(java.util.TimeZone.getTimeZone("CTT"));
			return formatter.parse(time, new java.text.ParsePosition(0));
		}
		return null;
	}

	public static java.util.Date stringToDatetime(String date, String time) {
		if ((date != null) && (time != null)) {
			java.text.SimpleDateFormat formatter = new java.text.SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			// formatter.setTimeZone(java.util.TimeZone.getTimeZone("CTT"));
			return formatter.parse(date + " " + time,
					new java.text.ParsePosition(0));
		}
		return null;
	}

	public static String getDate() {
		Calendar rightNow = Calendar.getInstance();

		int iY = rightNow.get(Calendar.YEAR);
		int iM = rightNow.get(Calendar.MONTH) + 1;
		int iD = rightNow.get(Calendar.DAY_OF_MONTH);

		String strYear = "" + iY;
		String strMon = "";
		String strDay = "";

		if (iM < 10) {
			strMon = "0" + iM;
		} else {
			strMon = "" + iM;
		}

		if (iD < 10) {
			strDay = "0" + iD;
		} else {
			strDay = "" + iD;
		}

		return (strYear + "-" + strMon + "-" + strDay);
	}

	/*
	 * ��ô������ڣ��ڶ��죩 2010-10-27 2:49 ���� ����
	 */
	public static String getNextDate() {
		Calendar rightNow = Calendar.getInstance();
		rightNow.add(Calendar.DAY_OF_MONTH, 1);
		int iY = rightNow.get(Calendar.YEAR);
		int iM = rightNow.get(Calendar.MONTH) + 1;
		int iD = rightNow.get(Calendar.DAY_OF_MONTH);

		String strYear = "" + iY;
		String strMon = "";
		String strDay = "";

		if (iM < 10) {
			strMon = "0" + iM;
		} else {
			strMon = "" + iM;
		}

		if (iD < 10) {
			strDay = "0" + iD;
		} else {
			strDay = "" + iD;
		}

		return (strYear + "-" + strMon + "-" + strDay);
	}

	public static String getTime() {
		Calendar rightNow = Calendar.getInstance();
		// int iHour0 = rightNow.get( Calendar.HOUR ); // 12
		int iH = rightNow.get(Calendar.HOUR_OF_DAY); // 24
		int iM = rightNow.get(Calendar.MINUTE);
		int iS = rightNow.get(Calendar.SECOND);

		String strH = null;
		String strM = null;
		String strS = null;

		if (iH < 10) {
			strH = "0" + iH;
		} else {
			strH = "" + iH;
		}

		if (iM < 10) {
			strM = "0" + iM;
		} else {
			strM = "" + iM;
		}

		if (iS < 10) {
			strS = "0" + iS;
		} else {
			strS = "" + iS;
		}
		return (strH + ":" + strM + ":" + strS);
	}

	public static String getDateTime() {
		return (getDate() + " " + getTime());
	}

	public static String getDateTimeStr() {
		Calendar rightNow = Calendar.getInstance();
		int dY = rightNow.get(Calendar.YEAR);
		int dM = rightNow.get(Calendar.MONTH) + 1;
		int dD = rightNow.get(Calendar.DAY_OF_MONTH);

		String strYear = "" + dY;
		String strMon = "";
		String strDay = "";

		if (dM < 10) {
			strMon = "0" + dM;
		} else {
			strMon = "" + dM;
		}

		if (dD < 10) {
			strDay = "0" + dD;
		} else {
			strDay = "" + dD;
		}

		String str = strYear + strMon + strDay;
		return str;
	}

	/*
	 * ������ں�ʵ���String���ͣ�û��ð�ţ�20110624150840
	 * (�����getDateTime()����Ϊ��2011-06-24 15:08:40)
	 * ���� 
	 */
	public static String getAllCalStr(){
		Calendar rightNow = Calendar.getInstance();
		int dY = rightNow.get(Calendar.YEAR);
		int dM = rightNow.get(Calendar.MONTH) + 1;
		int dD = rightNow.get(Calendar.DAY_OF_MONTH);
		int dH = rightNow.get(Calendar.HOUR_OF_DAY);
		int dMI= rightNow.get(Calendar.MINUTE);
		int dS = rightNow.get(Calendar.SECOND);

		String strYear = "" + dY;
		String strMon = "";
		String strDay = "";
		String strHou = "";
		String strMin = "";
		String strSec = "";

		if (dM < 10) {
			strMon = "0" + dM;
		} else {
			strMon = "" + dM;
		}

		if (dD < 10) {
			strDay = "0" + dD;
		} else {
			strDay = "" + dD;
		}
		
		if(dH < 10){
			strHou = "0" + dH;
		}else {
			strHou = "" + dH;
		}
		
		if(dMI < 10){
			strMin = "0" + dMI;
		}else {
			strMin = "" + dMI;
		}
		
		if(dS < 10){
			strSec = "0" + dS;
		}else {
			strSec = "" + dS;
		}

		String str = strYear + strMon + strDay + strHou + strMin + strSec;
		return str;
	}
 

 
	public static String cal2String(Calendar cal){
    	int iY;
		int iM;
		int iD;
    	iY = cal.get(Calendar.YEAR);
		iM = cal.get(Calendar.MONTH) + 1;
		iD = cal.get(Calendar.DAY_OF_MONTH);
		String strYear = "" + iY;
		String strMon = "";
		String strDay = "";
		if (iM < 10) {
			strMon = "0" + iM;
		} else {
			strMon = "" + iM;
		}
		if (iD < 10) {
			strDay = "0" + iD;
		} else {
			strDay = "" + iD;
		}
		String Date = strYear + "-" + strMon + "-" + strDay;
    	return Date;
    }
	
	public static Calendar String2cal(String strdate){
    	Calendar cal = Calendar.getInstance();
    	try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Date date = sdf.parse(strdate);
			cal.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
		return cal;
    }

	/*
	 * ���num��ǰ����
	 */
	public static String GetDateBeforNum(int num){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, num*-1);
		String Date = cal2String(cal) + " 00:00:00";
    	return Date;
	}
	
	/*
	 * ���num��ǰ����(YYYYmmdd)
	 */
	public static String GetDateBeforNumYMD(int num){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_MONTH, num*-1);
		String Date = cal2String(cal);
    	return Date;
	}
	
	/*
	 * ���num����ǰ����
	 */
	public static String GetMonthBeforNum(int num){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, num*-1);
		String Date = cal2String(cal) + " 00:00:00";
    	return Date;
	}
	/*
	 * ���num��ǰ����
	 */
	public static String GetYearBeforNum(int num){
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.YEAR, num*-1);
		String Date = cal2String(cal);
    	return Date;
	}
}