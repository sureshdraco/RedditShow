package redit.com.redditshow.network;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;

import android.content.Context;
import android.text.TextUtils;

import redit.com.redditshow.network.reply.ReplyBase;
import redit.com.redditshow.network.request.ReditRequest;

public class ApiServiceManager {

	private static ApiServiceManager instance;
	private RequestQueue requestQueue;

	private ApiServiceManager(Context context) {
		this.requestQueue = VolleyClient.getInstance(context).getRequestQueue();
	}

	public static ApiServiceManager getInstance() {
		if (instance == null) {
			throw new IllegalStateException("ApiServiceManager not initialized");
		}
		return instance;
	}

	public static ApiServiceManager createInstance(Context context) {
		instance = new ApiServiceManager(context);
		return instance;
	}

	public RequestQueue getRequestQueue() {
		return requestQueue;
	}

	public <T extends ReplyBase> Request<T> addRequest(ReditRequest request, Class<T> replyClass,
			SuccessListener<T> responseListener, ErrorListener errorListener) {
		return addRequest(request, replyClass, responseListener, errorListener,
				VolleyClient.NO_RETRY_POLICY, null, false, "application/json", "");
	}

	public <T extends ReplyBase> void addFormRequest(ReditRequest svtRequest, Class<T> replyClass, SuccessListener<T> responseListener,
			ErrorListener errorListener, String body) {
		addRequest(svtRequest, replyClass, responseListener, errorListener,
				VolleyClient.NO_RETRY_POLICY, null, false, GsonRequest.FORM_CONTENT_TYPE, body);
	}

	public <T extends ReplyBase> Request<T> addRequest(ReditRequest reditRequest, Class<T> replyClass,
			SuccessListener<T> responseListener, ErrorListener errorListener, RetryPolicy retryPolicy,
			String requestTag, boolean enableClientSideCaching, String contentType, String formBody) {
		GsonRequest<T> gsonRequest = new GsonRequest<T>(reditRequest.getRequestMethodType(), reditRequest.getApiUrl(), reditRequest,
				replyClass, reditRequest.getHeaders(), responseListener, errorListener, retryPolicy);
		if (!TextUtils.isEmpty(contentType)) {
			gsonRequest.setContentType(contentType);
		}
		if (!TextUtils.isEmpty(formBody)) {
			gsonRequest.setBody(formBody);
		}
		if (enableClientSideCaching) {
			gsonRequest.cacheResponse();
		}
		if (!TextUtils.isEmpty(requestTag)) {
			gsonRequest.setTag(requestTag);
		}
		requestQueue.add(gsonRequest);
		return gsonRequest;
	}
}
