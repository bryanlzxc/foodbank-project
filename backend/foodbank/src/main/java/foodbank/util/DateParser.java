package foodbank.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import foodbank.util.MessageConstants.ErrorMessages;
import foodbank.util.exceptions.SettingsUpdateException;

/*
 * Created by: Ng Shirong
 */

public class DateParser {
	
	/*
	 * This code is not functional as of 31/10/2017
	 */
	/*
	public static Date parseDateTime(String dateString) {
        if (dateString == null) return null;
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");
        if (dateString.contains("T")) dateString = dateString.replace('T', ' ');
        if (dateString.contains("Z")) dateString = dateString.replace("Z", "+0000");
        else
            dateString = dateString.substring(0, dateString.lastIndexOf(':')) + dateString.substring(dateString.lastIndexOf(':')+1);
        try {
            System.out.println("Before exception thrown ************ ");
        	return fmt.parse(dateString);
        }
        catch (ParseException e) {
            System.out.println("Parse Exception");
            return null;
        }
    }
    */
	
	/* 
	 * Created by: Lau Peng Liang, Bryan
	 */
	public static String getCurrentDate(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		return format.format(date);
	}
	
	public static Date convertToDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date dateObject = null;
		try { 
			dateObject = format.parse(date);
		} catch (Exception e) {
			throw new SettingsUpdateException(ErrorMessages.DATE_PARSE_ERROR);
		}
		return dateObject;
	}
	
	public static Date convertToDBDate(String date) {
		SimpleDateFormat format = new SimpleDateFormat("EEE MMM dd HH:mm:ss z yyyy");
		Date dateObject = null;
		try {
			dateObject = format.parse(date);
		} catch (Exception e) {
			throw new SettingsUpdateException(ErrorMessages.DATE_PARSE_ERROR);
		}
		return dateObject;
	}
	
	public static String displayDayMonthYearOnly(Date date) {
		SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
		return format.format(date);
	}
	
}
