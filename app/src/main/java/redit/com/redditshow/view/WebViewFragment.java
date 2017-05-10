package redit.com.redditshow.view;

import org.apache.commons.lang3.StringEscapeUtils;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import redit.com.redditshow.R;
import redit.com.redditshow.network.reply.model.Child;
import redit.com.redditshow.network.reply.model.Data_;
import redit.com.redditshow.network.reply.model.Image;
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
		TextView title = (TextView) view.findViewById(R.id.title);
		title.setText(child.getData().title);
		webview.setWebViewClient(new WebViewClient() {
			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				super.onPageStarted(view, url, favicon);
				try {
					getView().findViewById(R.id.webProgress).setVisibility(View.VISIBLE);
				} catch (Exception e) {
				}
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
				try {
					getView().findViewById(R.id.webProgress).setVisibility(View.GONE);
				} catch (Exception e) {
				}
			}
		});
		webview.getSettings().setLoadWithOverviewMode(true);
		webview.getSettings().setUseWideViewPort(true);
		webview.getSettings().setJavaScriptEnabled(true);
		try {
			if (TextUtils.isEmpty(getContent(child.getData()))) {
				try {
					webview.loadUrl(getUrl(child.getData()));
				} catch (Exception e1) {
					if (Constant.DEBUG) Log.d(TAG, e1.toString());
				}
			} else {
				Log.d(TAG, getContent(child.getData()));
				webview.loadData(getContent(child.getData()), "text/html", "UTF-8");
			}
		} catch (Exception e) {
			Log.d(TAG, e.toString());
		}

	}

	private String getUrl(Data_ data) {
		if (data == null) {
			return "";
		}
		try {
			Image image = child.getData().preview.images.get(0);
			if (image.variants == null || image.variants.gif == null) {
				return image.source.url;
			} else {
				return image.variants.gif.source.url;
			}

		} catch (Exception e) {
		}
		return "";
	}

	private String getContent(Data_ data) {
		if (data == null) {
			return "";
		}
		try {
			return StringEscapeUtils.unescapeHtml3(child.getData().mediaEmbed.content);
		} catch (Exception e) {
			Log.e(TAG, e.toString());
		}
		return "";
	}
}
