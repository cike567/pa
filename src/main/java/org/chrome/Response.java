package org.chrome;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.html.Json;
import org.ws.Message;

import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@NoArgsConstructor
public class Response implements Message {

	@Override
	public void handle(String message) throws IOException {
		result = message;
		Json json = new Json(message);
		JSONObject response = json.object();
		log.info("{}", response);
		if (response.has(ID) && id.contains(response.get(ID))) {
			handle(json);
			latch.countDown();
		}
	}

	protected void handle(Json json) {
	}

	public String result() throws InterruptedException {

		latch.await();

		return result;
	}

	public Integer peek(int i) {
		if (id.size() > i) {
			return id.get(i);
		} else {
			return null;
		}
	}

	public void setId(List<Integer> id) {
		this.id = id;
		latch = new CountDownLatch(id.size());
	}

	public Response(List<Integer> id) {
		setId(id);
	}

	protected String result;

	protected String ID = "id";

	protected List<Integer> id;

	protected CountDownLatch latch;

	public final Logger log = LoggerFactory.getLogger(this.getClass());

}
