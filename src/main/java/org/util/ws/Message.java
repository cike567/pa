package org.util.ws;

import java.io.IOException;

/**
 * 
 * @author cike
 *
 */
public interface Message {

	/**
	 * 
	 * @param message
	 * @throws IOException
	 */
	public void handle(String message) throws IOException;

	/**
	 * 
	 * @return
	 * @throws InterruptedException
	 */
	public String result() throws InterruptedException;

}
