package org.ws;

import java.io.IOException;

public interface Message {//

	public void handle(String message) throws IOException;

	public String result() throws InterruptedException;

}
