package org.chrome;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.html.Http;
import org.util.html.Json;

public class EndpointServlet extends HttpServlet {

	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String rs = "";
		try {
			rs = client.send(request(request));
		} catch (InterruptedException e) {
			log.error(e.getMessage());
		}
		response.getWriter().write(rs);
	}

	private Request request(HttpServletRequest request) throws UnsupportedEncodingException {
		JSONObject json = new Json(URLDecoder.decode(request.getQueryString(), Http.UTF8)).object();
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
		client = Devtools.chrome(port).protocol();
	}

	private Protocol client;

	private final Logger log = LoggerFactory.getLogger(this.getClass());
}
