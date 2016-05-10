package com.allinfinance.dwr.query;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.allinfinance.system.util.CommonFunction;

public class T50108 {
	
	public String compareDate(String startDate, String endDate) { 
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		String startDate1 = CommonFunction.getDateYestoday(CommonFunction.getCurrentDate()) + "120000";
		String endDate1 = CommonFunction.getCurrentDate() + "120000";
        try {
            Date sd = df.parse(startDate);
            Date ed = df.parse(endDate);
            Date sd1 = df.parse(startDate1);
            Date ed1 = df.parse(endDate1);
            if (sd1.getTime() > sd.getTime()) {
                return "E";
            } else if (ed1.getTime() < ed.getTime()) {
                return "E";
            } else {
                return "S";
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return "S";
    }
}