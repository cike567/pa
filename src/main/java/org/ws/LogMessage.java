package org.ws;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogMessage implements Message {
	protected String result;

	@Override
	public void handle(String message) {
		this.result = message;
		log.info(message);
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public String result() {
		return result;
	}

}
