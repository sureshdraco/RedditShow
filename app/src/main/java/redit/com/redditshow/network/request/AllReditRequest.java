package redit.com.redditshow.network.request;

import java.util.HashMap;
import java.util.List;

import com.android.volley.Request;

/**
 * Created by suresh on 4/20/17.
 */

public class AllReditRequest extends ReditRequest {
	private transient List<String> subReddits;

	public AllReditRequest(List<String> subReddits) {
		this.subReddits = subReddits;
	}

	private String getSubreddits() {
		if (subReddits == null) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (String subreddit : subReddits) {
			stringBuilder.append(subreddit);
			stringBuilder.append("+");
		}
		try {
			return stringBuilder.toString().substring(0, stringBuilder.length() - 1);
		} catch (Exception e) {
		}
		return "all";
	}

	@Override
	public int getRequestMethodType() {
		return Request.Method.GET;
	}

	@Override
	public String getBaseUrl() {
		return "https://www.reddit.com/r/" + getSubreddits() + "/.json?sort=new&limit=100";
	}

	@Override
	public HashMap<String, String> getQueryParams() {
		return new HashMap<>();
	}
}
