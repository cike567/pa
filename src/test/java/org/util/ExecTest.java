package org.util;

import java.io.IOException;

import org.junit.Test;

/**
 * 
 * @author cike
 *
 */
public class ExecTest {

	@Test
	public void testRun() throws IOException {
		// Exec.kill("chrome");
		CMD.exec("chrome.exe --remote-debugging-port=9222 --headless");
	}

}
