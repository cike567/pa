package org.chrome.request;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.chrome.Domains;
import org.chrome.Protocol;
import org.chrome.Request;
import org.chrome.enums.Method;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.util.html.Json;

import lombok.Data;

/**
 * 
 * @author cike
 *
 */
@Data
public class Xhr {

	private String url;

	// POST GET
	private String method;

	private String body;

	private String response;

	public Xhr(JSONObject rs) {
		this.url = rs.getString(Domains.URL);
		this.method = rs.getString(Domains.METHOD);
		if (Method.POST.name().equals(this.method)) {
			this.body = rs.getString("postData");
		}

	}

	public static List<Xhr> all(Protocol client) throws IOException, InterruptedException {
		Request network = new Request("Network.enable");
		Integer id = network.getId();
		client.send(network);
		Request reload = new Request("Page.reload");
		reload.setParams("ignoreCache", true);
		client.send(reload);
		List<Xhr> xhrs = xhr(client, id);
		return xhrs;
	}

	private static List<Xhr> xhr(Protocol client, Integer id) throws IOException, InterruptedException {
		List<Xhr> xhrs = new ArrayList<Xhr>();
		String[] iKey = { Domains.ID };
		String[] mKey = { Domains.METHOD };
		String[] tKey = { Domains.PARAMS, Domains.TYPE };
		String[] pKey = { Domains.PARAMS, Domains.REQUEST };
		String[] xKey = { Domains.PARAMS, Domains.REQUEST, Domains.HEADERS, "X-Requested-With" };
		String[] rKey = { Domains.PARAMS, Domains.REQUEST_ID };
		String[] bKey = { Domains.RESULT, Domains.BODY };
		String[] cKey = { Domains.ERROR, Domains.CODE };
		while (true) {
			Json rs = new Json(client.result());
			log.info("#{}", rs);
			if (id.equals(rs.value(iKey))) {
				break;
			}
			if (REQUEST_WILLBESENT.equals(rs.value(mKey)) && Domains.XHR.equals(rs.value(tKey))
					&& XHR.equals(rs.value(xKey))) {
				Request request = response(rs.value(rKey).toString());
				JSONObject params = rs.select(pKey).object();
				Xhr xhr = new Xhr(params);
				Json response = new Json(client.send(request));
				if (response.value(cKey) != null && Integer.parseInt(response.value(cKey).toString()) < 0) {
					continue;
				} else {
					xhr.setResponse(response.value(bKey).toString());
					xhrs.add(xhr);
				}
			}
		}
		return xhrs;
	}

	private static Request response(String requestId) {
		Request request = new Request(GET_RESPONSE_BODY);
		request.setParams(Domains.REQUEST_ID, requestId);
		return request;
	}

	private final static String REQUEST_WILLBESENT = "Network.requestWillBeSent";

	private final static String GET_RESPONSE_BODY = "Network.getResponseBody";

	private final static String XHR = "XMLHttpRequest";

	private static final Logger log = LoggerFactory.getLogger(Xhr.class);

}
