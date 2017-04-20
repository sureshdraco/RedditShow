package redit.com.redditshow.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import redit.com.redditshow.R;
import redit.com.redditshow.network.reply.model.Child;
import redit.com.redditshow.util.Constant;

public class WebViewFragment extends Fragment {
	private static final String ARG_DATA = "data";
	private static final String TAG = WebViewFragment.class.getCanonicalName();

	private Child child;

	public WebViewFragment() {
		// Required empty public constructor
	}

	public static WebViewFragment newInstance(Child child) {
		WebViewFragment fragment = new WebViewFragment();
		Bundle args = new Bundle();
		args.putParcelable(ARG_DATA, child);
		fragment.setArguments(args);
		return fragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if (getArguments() != null) {
			child = getArguments().getParcelable(ARG_DATA);
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_web_view, container, false);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		WebView webview = (WebView) view.findViewById(R.id.webView);
		webview.setWebViewClient(new WebViewClient());
		try {
			webview.loadUrl(child.getData().url);
		} catch (Exception e) {
			if (Constant.DEBUG) Log.d(TAG, e.toString());
		}
	}
}
