package Utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import android.app.AlarmManager;
import android.text.format.Time;

public class TimeUtils {
	public static long spanInMillis(Time startTime, Time endTime) {
		long diff = endTime.toMillis(true) - startTime.toMillis(true);
		if (diff >= 0)
			return diff;
		else
			return AlarmManager.INTERVAL_DAY - Math.abs(diff);
	}

	public static boolean isTimeInSpan(Time startTime, Time endTime,
			Time timeToCheck) {
		return timeToCheck.after(startTime) && timeToCheck.before(endTime);
	}

	public static Time strToTime(String str) {
		Time t = new Time();
		t.setToNow();
		int index = str.indexOf(':');
        int secondIndex = str.lastIndexOf(':');
		t.hour = Integer.valueOf(str.substring(0, index));
		t.minute = Integer.valueOf(str.substring(index + 1, index + 1+2));
        t.second = Integer.valueOf(str.substring(secondIndex+1,secondIndex+1+2));
		return t;
	}

	public static String timeToStr(Time time) {
		return time.hour + ":" + time.minute;
	}

	public static Time now() {
		Time t = new Time();
		t.setToNow();
		return t;
	}

	public static String DateTimeString(String format, Date date) {
		return new SimpleDateFormat(format, Locale.US).format(date);
	}

	public static String DateTimeString(String format) {
		return DateTimeString(format, new Date());
	}

	public static Date getDateFromString(String format, String string) {
		try {
			return new SimpleDateFormat(format, Locale.US).parse(string);
		} catch (ParseException e) {
			e.printStackTrace();
			return new Date();
		}
	}

	public static String CalendarToTimeString(Calendar calendar) {
		return calendar.get(Calendar.HOUR_OF_DAY) + ":"
				+ calendar.get(Calendar.MINUTE);
	}
    public static Calendar TimeStringToCalendar(String dateTime,String format) {
        Calendar cal = Calendar.getInstance();
        try {
            Date date =  new SimpleDateFormat(format, Locale.US).parse(dateTime);
            cal.setTime(date);
            return cal;
        } catch (ParseException e) {
            e.printStackTrace();
            return cal;
        }
    }

	/**
	 * @param date1
	 * @param date2
	 * @return Compare two Time objects and return a negative number if a is
	 *         less than b, a positive number if a is greater than b, or 0 if
	 *         they are equal.
	 */
	public static int compareDate(Date date1, Date date2) {
		if (date1.getTime() < date2.getTime())
			return -1;
		else if (date1.getTime() > date2.getTime())
			return 1;
		else
			return 0;
	}

	public static int compareTime(Time firstTime, Time secondTime) {
		firstTime.year = secondTime.year;
		firstTime.month = secondTime.month;
		return Time.compare(firstTime, secondTime);
	}
}