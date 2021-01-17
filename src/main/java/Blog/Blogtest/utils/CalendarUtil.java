package Blog.Blogtest.utils;

import java.util.Calendar;

public class CalendarUtil {
	public static String checktime(Calendar cal) {
		String tips="";
		int calyear=cal.get(Calendar.YEAR);
		Calendar calendar= Calendar.getInstance();
		int calendaryear=calendar.get(Calendar.YEAR);
		if(calyear == calendaryear) {
			int calmonth=cal.get(Calendar.MONTH);
			int calendarmonth=calendar.get(Calendar.MONTH);
			if(calmonth == calendarmonth) {
				int caldate=cal.get(Calendar.DATE);
				int calendardate=calendar.get(Calendar.DATE);
				if(caldate == calendardate) {
					int calhour=cal.get(Calendar.HOUR_OF_DAY);
					int calendarhour=calendar.get(Calendar.HOUR_OF_DAY);
					if(calhour == calendarhour) {
						tips="用户刚注册";
					}else {
						tips=String.valueOf(Math.abs(calhour-calendarhour))+"小时";
					}
				}else {
					tips=String.valueOf(Math.abs(caldate-calendardate))+"天";
				}
			}else {
				tips=String.valueOf(Math.abs(calmonth-calendarmonth))+"个月";
			}
		}else {
			tips=String.valueOf(Math.abs(calyear-calendaryear))+"年";
		}
		return tips;
	}
}
