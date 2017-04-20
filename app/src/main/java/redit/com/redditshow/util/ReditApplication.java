package redit.com.redditshow.util;

import android.app.Application;

import redit.com.redditshow.network.ApiServiceManager;

public class ReditApplication extends Application {

	private static final String TAG = ReditApplication.class.getCanonicalName();
	public static Application appContext;

	@Override
	public void onCreate() {
		super.onCreate();
		appContext = this;
		ApiServiceManager.createInstance(this);
	}
}