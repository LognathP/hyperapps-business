package com.hyperapps.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class CalendarUtil {

	public static String getCurrentDay()
	{
		Calendar calendar = Calendar.getInstance();
		Date date = calendar.getTime();
		return new SimpleDateFormat("EEEE", Locale.ENGLISH).format(date.getTime());
	}
	
	public static String getCurrentTimeHHMM()
	{
		Calendar calendar = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
		return formatter.format(calendar.getTime());
	}
	
	public static boolean getBusinessTimingStatus(String from,String to)
	{
		Date time1;
		boolean stat = false;
		try {
			time1 = new SimpleDateFormat("HH:mm").parse(from);
			Calendar calendar1 = Calendar.getInstance();
			calendar1.setTime(time1);
			calendar1.add(Calendar.DATE, 1);
			Date time2 = new SimpleDateFormat("HH:mm").parse(to);
			Calendar calendar2 = Calendar.getInstance();
			calendar2.setTime(time2);
			calendar2.add(Calendar.DATE, 1);
			Date d = new SimpleDateFormat("HH:mm").parse(getCurrentTimeHHMM());
		    Calendar calendar3 = Calendar.getInstance();
		    calendar3.setTime(d);
		    calendar3.add(Calendar.DATE, 1);
		    Date now = calendar3.getTime();
		    if (now.after(calendar1.getTime()) && now.before(calendar2.getTime())) {
		    	stat = true;
		    }
		    
			
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return stat;
	}
}
