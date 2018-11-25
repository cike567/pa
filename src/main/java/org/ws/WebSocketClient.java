package org.ws;

import java.io.IOException;
import java.net.URI;

import javax.websocket.ClientEndpoint;
import javax.websocket.CloseReason;
import javax.websocket.ContainerProvider;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Setter;

@ClientEndpoint
@Setter
public class WebSocketClient {

	public WebSocketClient(String uri) {
		this(uri, new LogMessage());
	}

	public WebSocketClient(String uri, Message message) {
		this.message = message;
		log.info(uri);
		try {
			connect(uri);
		} catch (Throwable e) {
			log.error(e.getMessage());
		}
	}

	private void connect(String uri) throws Throwable {
		WebSocketContainer container = ContainerProvider.getWebSocketContainer();
		int size = 1024 * 1024;
		container.setDefaultMaxTextMessageBufferSize(size);
		session = container.connectToServer(this, new URI(uri));
	}

	public void send(String text) throws IOException {
		log.info(text);
		session.getBasicRemote().sendText(text);
	}

	@OnOpen
	public void onOpen(Session session) {
		log.info("Connected to endpoint: " + session.getBasicRemote());
	}

	@OnMessage
	public void onMessage(String msg) throws IOException {
		message.handle(msg);
	}

	@OnError
	public void onError(Throwable t) {
		log.info(t.getMessage());
	}

	@OnClose
	public void close(CloseReason c) {
		log.info(c.toString());
	}

	private Message message;

	private Session session;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
}