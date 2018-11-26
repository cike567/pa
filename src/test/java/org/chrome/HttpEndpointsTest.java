package org.chrome;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.html.Json;

/**
 * 
 * @author cike
 *
 */
public class HttpEndpointsTest {

	@Test
	public void testRun() throws IOException {
		String rs = HttpEndpoints.VERSION.get();
		log.info(rs);

		rs = HttpEndpoints.LIST.get();
		log.info(rs);

		rs = HttpEndpoints.PROTOCOL.get();
		log.info(rs);

		String url = "https://s.taobao.com/search?q=";
		rs = HttpEndpoints.NEW.get(url);
		log.info(rs);

		String targetId = new Json(rs).object().getString("id");
		rs = HttpEndpoints.ACTIVATE.get(targetId);
		log.info(rs);

		rs = HttpEndpoints.CLOSE.get(targetId);
		log.info(rs);
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}
