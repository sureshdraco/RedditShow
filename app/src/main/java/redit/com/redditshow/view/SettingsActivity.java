package redit.com.redditshow.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SwitchCompat;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import redit.com.redditshow.R;
import redit.com.redditshow.util.PreferenceUtil;

public class SettingsActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.content_settings);
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
		SwitchCompat autoScroll = (SwitchCompat) findViewById(R.id.autoScroll);
		autoScroll.setChecked(PreferenceUtil.getAutoScroll(getApplicationContext()));
		autoScroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				PreferenceUtil.saveAutoScroll(getApplicationContext(), isChecked);
			}
		});
	}

}