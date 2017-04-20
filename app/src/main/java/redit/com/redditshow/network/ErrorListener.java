package redit.com.redditshow.network;

import java.io.UnsupportedEncodingException;

import com.android.volley.NoConnectionError;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import android.util.Log;

import redit.com.redditshow.network.reply.ReplyBase;

public abstract class ErrorListener implements Response.ErrorListener {

	private static final String TAG = ErrorListener.class.getCanonicalName();

	@Override
	public void onErrorResponse(final VolleyError error) {
		ReplyBase replyBase = new ReplyBase();
		replyBase = constructErrorReply(error, replyBase);
		onErrorResponse(replyBase);

	}

	private ReplyBase constructErrorReply(VolleyError error, ReplyBase replyBase) {
		if (NoConnectionError.class.isInstance(error)) {
			replyBase.responseCode = ReplyBase.NETWORK_ERROR;
		} else if (error.networkResponse == null) {
			replyBase.responseCode = ReplyBase.SERVER_ERROR;
		} else {
			// if response from server exists
			try {
				String json = new String(error.networkResponse.data,
						HttpHeaderParser.parseCharset(error.networkResponse.headers));
				replyBase = new Gson().fromJson(json, ReplyBase.class);
			} catch (UnsupportedEncodingException e) {
				Log.e(TAG, e.toString());
			} catch (JsonSyntaxException ex) {
				Log.e(TAG, ex.toString());
			}
			if (replyBase == null) {
				replyBase = new ReplyBase();
				replyBase.responseCode = ReplyBase.SERVER_ERROR;
			} else {
				replyBase.responseCode = error.networkResponse.statusCode;
			}
		}

		return replyBase;
	}

	public abstract void onErrorResponse(ReplyBase response);

}
