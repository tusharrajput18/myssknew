package in.co.vsys.myssksamaj.utils;

import android.text.format.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * @author Abhijeet.J
 */
public class DateFormatter {
    public static final String CHAT_DATE_TIME = "EEE, d MMM, H:mm a";
    public static String DAY_NO = "d";
    public static String SHORT_DATE = "d MMM yy";
    public static String DISPLAY_DOCUMENT_DATE_INDIA = "d/M/yyyy H:mm a";
    public static String DOB_DATE = "d MMM yyyy";
    public static String EVENT_DATE = "d MMMM";
    public static String EVENT_DETAILS_DATE = "d MMM h:mm a";
    public static String DAY_DATE = "EEE, d MMM yyyy";
    public static String FULL_MONTH = "MMMM";
    public static String MONTH_YEAR = "MMMM yyyy";
    public static String DB_DATE = "yyyy-MM-dd";
    public static String DB_DATE_TIME = "yyyy-MM-dd H:mm:ss";
    public static String DB_DATE_TIME_EZACUS = "d/M/yyyy H:mm a";
    public static String DB_TIME = "hh:mm:ss";
    public static String DISPLAY_DATE = "EEE, d MMMM yyyy";
    public static String DISPLAY_DATE_TIME = "EEE d MMM yy, hh:mm a";
    public static String DELIVERY_DATE = "EEE d MMM";
    public static String DISPLAY_SHORT_DATE_TIME = "EEE d MMM, hh:mm a";
    public static String DISPLAY_SHORT_DATE = "EEE d MMM yyyy";
    public static String DISPLAY_FULL_DATE = "EEE d MMM yyyy hh:mm a";
    public static String TIME = "h:mm a";

    public static String formatDBDate(Calendar calendar) {
        SimpleDateFormat format = new SimpleDateFormat(DB_DATE_TIME);
        String date = format.format(calendar.getTime());
        return date;
    }

    public static String formatDate(String dbDate, String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DB_DATE_TIME, Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(dbDate));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        String date = format.format(cal.getTime());
        return date;
    }

    public static String formatDate(Calendar calendar, String dateFormat) {
        SimpleDateFormat format = new SimpleDateFormat(dateFormat);
        String date = format.format(calendar.getTime());
        return date;
    }

    public static String formatDate(String inputString, String inputFormat, String outputFormat) {

        Calendar calendar = getCalendarFromString(inputString, inputFormat);

        SimpleDateFormat format = new SimpleDateFormat(outputFormat);
        String date = format.format(calendar.getTime());
        return date.trim();
    }

    public static Calendar getCalendarFromString(String dateString, String dateFormat) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat, Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(dateString));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    /**
     * Provide string, calendar and date format
     * Whichever is null, the other will convert
     *
     * @return
     */
    public static String extractDate(Calendar cal, String dateString, String dateFormat) {
        try {
            if (cal != null)
                return formatDate(cal, dateFormat);

            if (Utilities.getString(dateString).isEmpty())
                return "";

            return formatDate(dateString, dateFormat);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return "";
    }

    public static void resetTimeOfCalendar(Calendar cal) {
        if (cal == null)
            return;
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
    }

    public static void setDateToFirst(Calendar cal) {
        if (cal == null)
            return;
        cal.set(Calendar.DAY_OF_MONTH, 1);
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
    }

    public static void setDateToLast(Calendar cal) {
        if (cal == null)
            return;
        cal.set(Calendar.DAY_OF_MONTH, cal.getMaximum(Calendar.DAY_OF_MONTH));
        cal.set(Calendar.HOUR_OF_DAY, 1);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
    }

    /**
     * Get difference in hours
     */
    public static long getDateDiff(Calendar calfrom, Calendar calTo) {
        long diff;
        if (calfrom.getTimeInMillis() > calTo.getTimeInMillis())
            return -1;

        diff = calTo.getTimeInMillis() - calfrom.getTimeInMillis();

        if (diff < DateUtils.HOUR_IN_MILLIS)
            return 0;

        diff = diff / DateUtils.HOUR_IN_MILLIS;
        return diff;
    }

    public static String getDisplayTime(String timeStr) {
        String displayTime = "";
        int time = Integer.parseInt(timeStr);
        if (time == 24) {
            time -= 12;
            displayTime = time + " am";
        } else if (time > 12) {
            time -= 12;
            displayTime = time + " pm";
        } else if (time == 12) {
            displayTime = time + " pm";
        } else {
            displayTime = time + " am";
        }
        return displayTime;
    }

    public static String getEventDate(String fromDate, String toDate) {
        StringBuilder stringBuilder = new StringBuilder("");
        try {
            Calendar fromCal = getCalendarFromString(fromDate, DB_DATE_TIME);
            Calendar toCal = getCalendarFromString(toDate, DB_DATE_TIME);

            long diff = getDateDiff(fromCal, toCal);

            // different dates show date like: 25 November (8 pm) - 26 Novemeber (9 pm)
            if (diff > 24) {
                stringBuilder.append(formatDate(fromCal, "d MMMM (h a)"));
                stringBuilder.append(formatDate(toCal, " - d MMMM (h a)"));
            } else {
                // same dates show date like: 25 November (8 pm - 9 pm)
                stringBuilder.append(formatDate(fromCal, "d MMMM (h a"));
                stringBuilder.append(formatDate(toCal, " - h a)"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }

    public static String getSessionTime(int hourOfDay) {

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay);

        return formatDate(calendar, "h a");
    }
}