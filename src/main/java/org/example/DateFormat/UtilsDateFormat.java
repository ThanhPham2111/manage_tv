package org.example.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;

public class UtilsDateFormat {
    private final static SimpleDateFormat sdfDate = new SimpleDateFormat("dd-MM-yyyy");
    private final static SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss");
    private final static SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public static String formatDate(java.util.Date date){
        return sdfDate.format(date);
    }

    public static String formatTime(java.util.Date date){
        return sdfTime.format(date);
    }

    public static String formatDateTime(java.util.Date date){
        return formatDate(date) + " " + formatTime(date);
    }

    // dateString phải có format "dd-MM-yyy HH:mm:ss nha ae"
    public static java.util.Date stringToDate(String dateString) throws ParseException{
        return sdf.parse(dateString);
    }
}
