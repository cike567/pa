package org.ws;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogMessage implements Message {

	@Override
	public void handle(String message) {
		log.info(message);
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Override
	public void close() throws IOException {
		// TODO Auto-generated method stub

	}

}
