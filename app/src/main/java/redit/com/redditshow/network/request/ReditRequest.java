package redit.com.redditshow.network.request;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import android.text.TextUtils;

public abstract class ReditRequest {

	protected transient TreeMap<String, String> requestHeader = new TreeMap<String, String>();

	public abstract int getRequestMethodType();

	public abstract String getBaseUrl();

	public String getApiUrl() {
		String query = getQueryPart();
		if (TextUtils.isEmpty(query)) {
			return getBaseUrl();
		} else {
			return getBaseUrl() + "?" + getQueryPart();
		}
	}

	private String getQueryPart() {
		HashMap<String, String> queryParams = getQueryParams();
		if (queryParams == null || queryParams.isEmpty()) {
			return "";
		}
		StringBuilder queryBuilder = new StringBuilder();
		for (String key : queryParams.keySet()) {
			queryBuilder.append("&" + key + "=");
			queryBuilder.append(queryParams.get(key));
		}
		return queryBuilder.toString();
	}

	public abstract HashMap<String, String> getQueryParams();

	public Map<String, String> getHeaders() {
		return requestHeader;
	}
}
