package org.chrome;

import lombok.Data;

/**
 * 
 * @author cike
 *
 */
@Data
public class Domains {

	public Domains(Request request, String targetId) {
		this(new Response(request.getId(), targetId), request);
	}

	public Domains(Response response, Request request) {
		this.request = request;
		this.response = response;
	}

	private Request request;
	private Response response;

	public static final String METHOD = "method";

	public static final String ID = "id";

	public static final String PARAMS = "params";

	public static final String TYPE = "type";

	public static final String URL = "url";

	public static final String BODY = "body";

	public static final String RESULT = "result";

	public static final String ERROR = "error";

	public static final String CODE = "code";

	public static final String REQUEST = "request";

	public static final String REQUEST_ID = "requestId";

	public static final String HEADERS = "headers";

	public static final String XHR = "XHR";

	public final static String RELOAD_PAGE = "Page.reload";

	public final static String ENABLE_NETWORK = "Network.enable";

	public final static String REQUEST_WILLBESENT = "Network.requestWillBeSent";

	public final static String GET_RESPONSE_BODY = "Network.getResponseBody";

}
