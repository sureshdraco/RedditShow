package redit.com.redditshow.network.request;

import java.util.HashMap;

import com.android.volley.Request;

/**
 * Created by suresh on 4/20/17.
 */

public class AllReditRequest extends ReditRequest {
	@Override
	public int getRequestMethodType() {
		return Request.Method.GET;
	}

	@Override
	public String getBaseUrl() {
		return "https://www.reddit.com/r/all/.json";
	}

	@Override
	public HashMap<String, String> getQueryParams() {
		return new HashMap<>();
	}
}
