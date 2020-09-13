package com.yondervision.yfmap.result.ningbo;

import java.text.NumberFormat;

public class MoneyNumberTran {

	public String moneyTran(String money){
		if(money==null||"".equals(money)){
			money = "0.00";
		}
		String result = NumberFormat.getCurrencyInstance().format(new Double(money)).substring(1);
		return result;
	}
	public String numberTran(String number){
		if(number==null||"".equals(number)){
			number = "0.00";
		}
		String result = new Double(number)*100+"%";
		return result;
	}
}
