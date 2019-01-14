package org.chrome.endpoint;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.chrome.Devtools;
import org.chrome.Domains;
import org.chrome.Protocol;
import org.chrome.Request;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.Http;
import org.util.Json;
import org.util.Stream;

public class EndpointServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uri = request.getRequestURI();
		String arg = request.getQueryString();
		if (arg != null) {
			uri += "?" + arg;
		}
		String http = String.format("http://localhost:%d%s", port, uri);
		System.out.println(http);
		response.getWriter().write(Http.get(http));
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rs = "";
		try {
			if (WS.equals(request.getRequestURI())) {
				String body = Stream.read(request.getInputStream());
				rs = client.send(request(body));
			}
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
		response.getWriter().write(rs);
	}

	private Request request(String request) throws UnsupportedEncodingException {
		JSONObject json = new Json(URLDecoder.decode(request, Http.UTF8)).object();
		String method = json.getString(Domains.METHOD);
		Map<String, Object> params = new HashMap<String, Object>();
		if (json.has(Domains.PARAMS)) {
			params = json.getJSONObject(Domains.PARAMS).toMap();
		}
		return new Request(method, params);
	}

	public EndpointServlet() {
		client = Devtools.protocol();
	}

	public EndpointServlet(int port) {
		this.port = port;
		client = Devtools.chrome(port).protocol();
	}

	private String WS = "/json/ws";

	private int port;

	private Protocol client;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
}
