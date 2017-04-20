package redit.com.redditshow.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import redit.com.redditshow.R;
import redit.com.redditshow.network.ApiServiceManager;
import redit.com.redditshow.network.request.AllReditRequest;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getCanonicalName();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
//		ApiServiceManager.getInstance().addRequest(new AllReditRequest())
	}
}