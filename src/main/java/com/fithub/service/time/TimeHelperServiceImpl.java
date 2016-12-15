package com.fithub.service.time;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * An implementation of our TimeHelperService
 *
 */
@Service
public class TimeHelperServiceImpl implements TimeHelperService {

	private static final Logger LOG = LoggerFactory.getLogger(TimeHelperService.class);

	@Override
	public Date dateFormatter(String dateToBeFormatted) {
		// Reference :
		// http://www.mkyong.com/java/java-date-and-calendar-examples/
		Date date = null;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/mm/dd");

		try {
			date = dateFormat.parse(dateToBeFormatted);
		} catch (ParseException exception) {
			LOG.debug("Error parsing date={}", dateToBeFormatted);
		}
		return date;
	}

	@Override
	public Timestamp getCurrentTimeStamp() {

		Timestamp timeStamp = new Timestamp(new Date().getTime());
		return timeStamp;
	}

}
