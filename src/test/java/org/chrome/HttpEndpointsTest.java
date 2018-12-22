package org.chrome;

import java.io.IOException;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.html.Http;
import org.util.html.Json;

/**
 * 
 * @author cike
 *
 */
public class HttpEndpointsTest {

	@Test
	public void testRun() throws IOException {
		Devtools.protocol();
		String rs = get(Endpoint.VERSION);
		log.info("version {}", rs);

		rs = get(Endpoint.LIST);
		log.info("list {}", rs);

		rs = get(Endpoint.PROTOCOL);
		// log.info(rs);

		String url = "https://s.taobao.com/search?q=";
		rs = get(Endpoint.NEW, url);
		log.info("new {}", rs);

		String targetId = new Json(rs).object().getString("id");
		rs = get(Endpoint.ACTIVATE, targetId);
		log.info("activate {}", rs);

		rs = get(Endpoint.CLOSE, targetId);
		log.info("close {}", rs);
	}

	private String get(Endpoint endpoint, String... query) throws IOException {
		return Http.get(endpoint, 9222, query);
	}

	private final Logger log = LoggerFactory.getLogger(this.getClass());

}
