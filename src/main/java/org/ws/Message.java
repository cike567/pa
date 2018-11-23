package org.ws;

import java.io.Closeable;

public interface Message extends Closeable {
	public void handle(String message);
}
