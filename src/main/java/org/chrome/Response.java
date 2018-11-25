package org.chrome;

import java.io.IOException;
import java.util.concurrent.CountDownLatch;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.html.Json;
import org.ws.Message;

import lombok.Setter;

@Setter
public class Response implements Message {

	@Override
	public void handle(String message) throws IOException {
		result = message;
		Json json = new Json(message);
		JSONObject response = json.object();
		log.info("{}", response);
		if (response.has(ID) && response.get(ID).equals(id)) {
			handle(json);
			latch.countDown();
		}
	}

	// TODO
	protected void handle(Json json) {
	}

	public String result() throws InterruptedException {
		latch.await();
		return result;
	}

	public Response(int id) {
		this.id = id;
		latch = new CountDownLatch(1);
	}

	protected String result;

	protected String ID = "id";

	protected Integer id; // protected List<Integer> id;

	protected CountDownLatch latch;

	public final Logger log = LoggerFactory.getLogger(this.getClass());

}
