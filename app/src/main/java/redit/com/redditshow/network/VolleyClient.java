package redit.com.redditshow.network;

import java.io.File;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HttpStack;
import com.android.volley.toolbox.HurlStack;

import android.content.Context;
import android.os.Build;

import redit.com.redditshow.util.Constant;

public class VolleyClient {
	public static final RetryPolicy NO_RETRY_POLICY = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
	private static final int NETWORK_THREAD_POOL_SIZE = 3;
	private static final String DEFAULT_CACHE_DIR = "volley";
	private static VolleyClient mInstance;
	private static Context mCtx;
	private RequestQueue requestQueue;

	private VolleyClient(Context context) {
		mCtx = context;
		requestQueue = getRequestQueue();
	}

	public static synchronized VolleyClient getInstance(Context context) {
		if (mInstance == null) {
			mInstance = new VolleyClient(context);
		}
		return mInstance;
	}

	public static RequestQueue newRequestQueue(Context context, HttpStack stack) {
		File cacheDir = new File(context.getCacheDir(), DEFAULT_CACHE_DIR);
		if (stack == null) {
			if (Build.VERSION.SDK_INT >= 9) {
				stack = new HurlStack();
			}
		}
		Network network = new BasicNetwork(stack);
		RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir), network, NETWORK_THREAD_POOL_SIZE);
		queue.start();
		return queue;
	}

	public RequestQueue getRequestQueue() {
		if (requestQueue == null) {
			// getApplicationContext() is key, it keeps you from leaking the
			// Activity or BroadcastReceiver if someone passes one in.
			requestQueue = VolleyClient.newRequestQueue(mCtx.getApplicationContext(), null);
			VolleyLog.DEBUG = Constant.DEBUG;
		}
		return requestQueue;
	}

	public void setRequestQueue(RequestQueue requestQueue) {
		this.requestQueue = requestQueue;
	}
}
