package org.chrome;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.html.Json;
import org.ws.Message;

import lombok.Setter;

//@NoArgsConstructor
@Setter
public class Response implements Message {

	@Override
	public void handle(String message) {
		JSONObject response = new Json(message).object();
		log.info("{}", response);
		if (response.has(ID) && id.contains(response.get(ID))) {
			latch.countDown();
		}
	}

	public void close() throws IOException {
		try {
			latch.await();
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
	}

	public Response(List<Integer> id) {
		this.id = id;
		latch = new CountDownLatch(id.size());
	}

	private String ID = "id";

	private List<Integer> id;

	private CountDownLatch latch;

	public final Logger log = LoggerFactory.getLogger(this.getClass());

}
