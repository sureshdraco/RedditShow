package redit.com.redditshow.util;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferenceUtil {

	public static final String PREF_STORAGE_LOCATION = "reddit";
	public static final String SUBREDDIT = "subReddit";
	public static final String AUTOSCROLL = "autoScroll";

	public static SharedPreferences getPref(Context context) {
		return context.getSharedPreferences(PREF_STORAGE_LOCATION, 0);
	}

	private static SharedPreferences.Editor getPrefEditor(Context context) {
		return getPref(context).edit();
	}

	public static String getSubreddit(Context context) {
		return getPref(context).getString(SUBREDDIT, "all\n");
	}

	public static void saveSubreddit(Context context, String subreddit) {
		getPrefEditor(context).putString(SUBREDDIT, subreddit).apply();
	}

	public static boolean getAutoScroll(Context context) {
		return getPref(context).getBoolean(AUTOSCROLL, false);
	}

	public static void saveAutoScroll(Context context, boolean autoscroll) {
		getPrefEditor(context).putBoolean(AUTOSCROLL, autoscroll).apply();
	}
}
