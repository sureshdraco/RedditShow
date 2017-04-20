package redit.com.redditshow.view;

import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import redit.com.redditshow.R;
import redit.com.redditshow.network.ApiServiceManager;
import redit.com.redditshow.network.ErrorListener;
import redit.com.redditshow.network.SuccessListener;
import redit.com.redditshow.network.reply.Listing;
import redit.com.redditshow.network.reply.ReplyBase;
import redit.com.redditshow.network.reply.model.Child;
import redit.com.redditshow.network.request.AllReditRequest;
import redit.com.redditshow.util.Constant;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = MainActivity.class.getCanonicalName();
	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide fragments for each of the sections. We use a {@link FragmentPagerAdapter} derivative, which will keep
	 * every loaded fragment in memory. If this becomes too memory intensive, it may be best to switch to a {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	private SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	private ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);

		ApiServiceManager.getInstance().addRequest(new AllReditRequest(), Listing.class, new SuccessListener<Listing>() {
			@Override
			public void onSuccessResponse(Listing response) {
				if (Constant.DEBUG) Log.d(TAG, response.toString());
				handleResponse(response);
			}
		}, new ErrorListener() {
			@Override
			public void onErrorResponse(ReplyBase response) {
				if (Constant.DEBUG) Log.d(TAG, response.toString());
			}
		});

	}

	private void handleResponse(Listing response) {
		mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager(), response.data.children);
		mViewPager = (ViewPager) findViewById(R.id.container);
		mViewPager.setAdapter(mSectionsPagerAdapter);
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
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentPagerAdapter {

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
