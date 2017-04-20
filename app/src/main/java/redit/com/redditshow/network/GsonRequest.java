package redit.com.redditshow.network;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.util.Log;

import redit.com.redditshow.network.reply.ReplyBase;
import redit.com.redditshow.util.Constant;

public class GsonRequest<T extends ReplyBase> extends Request<T> {
	public static final String HEADER_DATE = "Date";
	public static final String HEADER_E_TAG = "ETag";
	private static final String TAG = "GsonRequest";
	/**
	 * Content type for request.
	 */
	private String contentType = "application/json";
	public static final String FORM_CONTENT_TYPE = "application/x-www-form-urlencoded";
	private final Gson gson = new Gson();
	private final Class<T> response;
	private final Response.Listener<T> listener;
	private final ErrorListener errorListener;
	private final Object request;
	private Map<String, String> headers;
	private boolean shouldCacheResponse = false;
	private String body;

	/**
	 * @param method
	 *            method type of the request (Get, Post)
	 * @param url
	 *            URL of the request to make
	 * @param response
	 *            Relevant class object, for Gson's reflection
	 * @param headers
	 *            Map of request headers
	 */
	public GsonRequest(int method, String url, Object request, Class<T> response,
			Map<String, String> headers, Response.Listener<T> listener, ErrorListener errorListener) {
		super(method, url, errorListener);
		this.errorListener = errorListener;
		this.response = response;
		this.headers = headers;
		this.listener = listener;
		this.request = request;
	}

	public void setBody(String body) {
		this.body = body;
	}

	public GsonRequest(int method, String url, Object request, Class<T> response,
			Map<String, String> headers, Response.Listener<T> listener, ErrorListener errorListener,
			RetryPolicy retryPolicy) {
		this(method, url, request, response, headers, listener, errorListener);
		if (retryPolicy != null) {
			setRetryPolicy(retryPolicy);
		}
	}

	/**
	 * @param method
	 *            method type of the request (Get, Post etc)
	 * @param url
	 *            URL of the request to make
	 * @param response
	 *            Relevant class object, for Gson's reflection
	 */
	public GsonRequest(int method, String url, Object request, Class<T> response,
			Response.Listener<T> listener, ErrorListener errorListener) {
		this(method, url, request, response, null, listener, errorListener);
		setRetryPolicy(new DefaultRetryPolicy(0, 0, 0));
	}

	public static Cache.Entry getClientSideCacheHeaders(NetworkResponse response) {
		long now = System.currentTimeMillis();

		Map<String, String> headers = response.headers;
		long serverDate = 0;
		String serverEtag = null;
		String headerValue;

		headerValue = headers.get(HEADER_DATE);
		if (headerValue != null) {
			serverDate = HttpHeaderParser.parseDateAsEpoch(headerValue);
		}

		serverEtag = headers.get(HEADER_E_TAG);

		final long cacheExpired = (long) 24 * 60 * 60 * 1000; // in 24 hours this cache entry expires
		// completely
		final long softExpire = now;
		final long ttl = now + cacheExpired;

		Cache.Entry entry = new Cache.Entry();
		entry.data = response.data;
		entry.etag = serverEtag;
		entry.softTtl = softExpire;
		entry.ttl = ttl;
		entry.serverDate = serverDate;
		entry.responseHeaders = headers;

		return entry;
	}

	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		if (headers == null) {
			headers = new HashMap<String, String>();
		}
		return headers;
	}

	@Override
	protected void deliverResponse(T response) {
		listener.onResponse(response);
	}

	@Override
	public Response<T> parseNetworkResponse(NetworkResponse response) {
		// if (FeedRequest.class.isInstance(request)) {
		// response = new NetworkResponse(503, "{}".getBytes(), new HashMap<String, String>(), false);
		// return Response.error(new VolleyError(response));
		// }
		try {
			String json = new String(response.data, HttpHeaderParser.parseCharset(response.headers));
			if (Constant.DEBUG)
				Log.d(TAG, "Response[" + "]: " + json);
			if (json.contains("structuredBody")) {
				json = json.replace("\"type\":\"text\",\"content\"", "\"type\":\"text\",\"textContent\"");
			}
			T result = gson.fromJson(json, this.response);
			result.responseCode = response.statusCode;
			if (shouldCacheResponse) {
				return Response.success(result, getClientSideCacheHeaders(response));
			} else {
				return Response.success(result, HttpHeaderParser.parseCacheHeaders(response));
			}
		} catch (UnsupportedEncodingException e) {
			if (Constant.DEBUG) Log.e(TAG, e.toString());
			return Response.error(new ParseError(e));
		} catch (JsonSyntaxException e) {
			if (Constant.DEBUG) Log.e(TAG, e.toString());
			return Response.error(new ParseError(e));
		} catch (Exception e) {
			if (Constant.DEBUG) Log.e(TAG, e.toString());
			return Response.error(new ParseError(e));
		}
	}

	@Override
	public String getBodyContentType() {
		return contentType;
	}

	public void setContentType(String contentType) {
		this.contentType = contentType;
	}

	@Override
	public byte[] getBody() {
		String body = "";
		if (request != null) {
			if (contentType.equals(FORM_CONTENT_TYPE)) {
				body = this.body;
			} else {
				body = gson.toJson(request);
			}
		}
		return body.getBytes();
	}

	public void cacheResponse() {
		shouldCacheResponse = true;
	}

	public Object getRequest() {
		return request;
	}

	public ErrorListener getErrorListener() {
		return errorListener;
	}

	public Class<T> getResponse() {
		return response;
	}

	public Response.Listener<T> getListener() {
		return listener;
	}
}
