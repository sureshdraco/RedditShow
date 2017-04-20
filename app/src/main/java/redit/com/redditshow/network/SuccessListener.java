package redit.com.redditshow.network;

import com.android.volley.Response;

import redit.com.redditshow.network.reply.ReplyBase;

public abstract class SuccessListener<T extends ReplyBase> implements Response.Listener<T> {
	public abstract void onSuccessResponse(T response);

	@Override
	public void onResponse(T response) {
		onSuccessResponse(response);
	}
}
