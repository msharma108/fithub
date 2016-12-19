package com.fithub.service.time;

import java.sql.Timestamp;
import java.util.Date;

/**
 * An interface for the services pertinent to getting,setting & converting time
 * to various formats
 *
 */
public interface TimeHelperService {

	/**
	 * Method formats provided date in "yy/mm/dd" format
	 * 
	 * @param dateToBeFormatted
	 * @return Formatted date
	 */
	Date dateFormatter(String dateToBeFormatted);

	/**
	 * Method prepares current time in the format of timestamp
	 * 
	 * @return current timestamp
	 */
	Timestamp getCurrentTimeStamp();

}
