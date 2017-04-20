package redit.com.redditshow.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
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
		try {
			webview.loadUrl(getUrl(child.getData()));
			webview.setPadding(0, 0, 0, 0);
			webview.setInitialScale(getScale(child.getData().preview.images.get(0).source.width));
			webview.getSettings().setSupportZoom(true);
		} catch (Exception e) {
			if (Constant.DEBUG) Log.d(TAG, e.toString());
		}
	}
	
	private int getScale(int picWidth) {
		Display display = ((WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();
		int width = display.getWidth();
		Double val = new Double(width) / new Double(picWidth);
		val = val * 100d;
		return val.intValue();
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
}
