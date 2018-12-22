package org.chrome;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.html.Json;
import org.util.ws.Message;

import lombok.Setter;

/**
 * 
 * @author cike
 *
 */
@Setter
public class Response implements Message {

	@Override
	public void handle(String message) throws IOException {

		Json json = new Json(message);
		JSONObject response = json.object();
		log.info("handld {}", response);
		// TODO
		Devtools.protocol(targetId).result(message);
		if (response.has(ID) && response.get(ID).equals(id)) {
			result = message;
			latch.countDown();
		}
	}

	@Override
	public String result() throws InterruptedException {
		latch.await();// 5, TimeUnit.SECONDS
		return result;
	}

	public Response(int id, String targetId) {
		this.id = id;
		this.targetId = targetId;
		latch = new CountDownLatch(1);
	}

	protected String result;

	protected final String ID = "id";

	protected Integer id;

	protected String targetId;

	protected CountDownLatch latch;

	public final Logger log = LoggerFactory.getLogger(this.getClass());

}
