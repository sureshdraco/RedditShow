package redit.com.redditshow.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import redit.com.redditshow.R;
import redit.com.redditshow.util.PreferenceUtil;

public class SettingsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_settings);
		final EditText subreddits = (EditText) findViewById(R.id.subreddits);
		subreddits.setText(PreferenceUtil.getSubreddit(getApplicationContext()));
		subreddits.setSelection(subreddits.getText().length());
		findViewById(R.id.save).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				PreferenceUtil.saveSubreddit(getApplicationContext(), subreddits.getText().toString());
				Toast.makeText(SettingsActivity.this, "Saved", Toast.LENGTH_SHORT).show();
				finish();
			}
		});
	}

}