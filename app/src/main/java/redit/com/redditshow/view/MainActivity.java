package redit.com.redditshow.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import redit.com.redditshow.R;
import redit.com.redditshow.network.ApiServiceManager;
import redit.com.redditshow.network.ErrorListener;
import redit.com.redditshow.network.SuccessListener;
import redit.com.redditshow.network.reply.Listing;
import redit.com.redditshow.network.reply.ReplyBase;
import redit.com.redditshow.network.reply.model.Child;
import redit.com.redditshow.network.request.AllReditRequest;
import redit.com.redditshow.util.Constant;
import redit.com.redditshow.util.PreferenceUtil;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getCanonicalName();
	private Handler handler;
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the sections. We use a {@link FragmentPagerAdapter} derivative, which will keep
	 * every loaded fragment in memory. If this becomes too memory intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;
	private int listingSize;
	private Runnable r;
	private String subredditsStr;
	private TextView status;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		handler = new Handler();
		mViewPager = (ViewPager) findViewById(R.id.container);
		status = (TextView) findViewById(R.id.status);
	}

	private void fetchData() {
		ApiServiceManager.getInstance().addRequest(new AllReditRequest(getSubreddits()), Listing.class, new SuccessListener<Listing>() {
			@Override
			public void onSuccessResponse(Listing response) {
				if (Constant.DEBUG) Log.d(TAG, response.toString());
				try {
					status.setText("Success");
					Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
					handleResponse(response);
				} catch (Exception e) {
					status.setText("Failed");
					Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
				}
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(ReplyBase response) {
				if (Constant.DEBUG) Log.d(TAG, response.toString());
				status.setText("Failed");
				Toast.makeText(MainActivity.this, "Failed", Toast.LENGTH_SHORT).show();
			}
		});
	}

	private List<String> getSubreddits() {
		subredditsStr = PreferenceUtil.getSubreddit(getApplicationContext());
		if (TextUtils.isEmpty(subredditsStr)) {
			return new ArrayList<>();
		}
		String[] list = subredditsStr.split("\n");
		return Arrays.asList(list);
	}

	private void handleResponse(Listing response) {
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), response.data.children);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			@Override
			public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

			}

			@Override
			public void onPageSelected(int position) {
				try {
					handler.removeCallbacks(r);
					handler.postDelayed(r, 5000);
				} catch (Exception e) {
				}
			}

			@Override
			public void onPageScrollStateChanged(int state) {

			}
		});
		listingSize = response.data.children.size();
		r = new Runnable() {
			@Override
			public void run() {
				int current = mViewPager.getCurrentItem();
				current++;
				if (current == listingSize) {
					current = 0;
				}
				mViewPager.setCurrentItem(current, true);
				handler.postDelayed(this, 5000);
			}
		};
		handler.postDelayed(r, 5000);
	}

	@Override
	protected void onPause() {
		super.onPause();
		try {
			handler.removeCallbacks(r);
		} catch (Exception e) {
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (TextUtils.equals(subredditsStr, PreferenceUtil.getSubreddit(getApplicationContext()))) {
			try {
				handler.postDelayed(r, 5000);
			} catch (Exception e) {
			}
		} else {
			fetchData();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main2, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		// noinspection SimplifiableIfStatement
		if (id == R.id.action_settings) {
			startActivity(new Intent(this, SettingsActivity.class));
			return false;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

		private final List<Child> childList;

		public SectionsPagerAdapter(FragmentManager fm, List<Child> childList) {
			super(fm);
			this.childList = childList;
		}

		@Override
		public Fragment getItem(int position) {
			// getItem is called to instantiate the fragment for the given page.
			// Return a PlaceholderFragment (defined as a static inner class below).
			return WebViewFragment.newInstance(childList.get(position));
		}

		@Override
		public int getCount() {
			return childList.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			try {
				return childList.get(position).data.title;
			} catch (Exception e) {
				return "";
			}
		}
	}
}
