package com.fithub.service.time;

import java.sql.Timestamp;

/**
 * An interface for the services pertinent to getting,setting & converting time
 * to various formats
 *
 */
public interface TimeHelperService {

	/**
	 * Method prepares current time in the format of timestamp
	 * 
	 * @return current timestamp
	 */
	Timestamp getCurrentTimeStamp();

}
