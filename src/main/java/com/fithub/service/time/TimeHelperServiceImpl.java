package com.fithub.service.time;

import java.sql.Timestamp;
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
	public Timestamp getCurrentTimeStamp() {

		Timestamp timeStamp = new Timestamp(new Date().getTime());
		LOG.debug("Getting current timestamp", timeStamp.toString());
		return timeStamp;
	}

}
