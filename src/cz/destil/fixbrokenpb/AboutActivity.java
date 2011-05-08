package cz.destil.fixbrokenpb;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.markupartist.android.widget.ActionBar;
import com.markupartist.android.widget.ActionBar.Action;

public class AboutActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.about);
		ActionBar actionBar = (ActionBar) findViewById(R.id.actionbar);
		actionBar.setTitle(R.string.about);
		actionBar.setHomeAction(new Action() {
			@Override
			public int getDrawable() {
				return R.drawable.ic_action_home;
			}

			@Override
			public void performAction(View view) {
				finish();
			}

		});
	}

	public static Intent getIntent(Context context) {
		return new Intent(context, AboutActivity.class);
	}

	public void mail(View view) {
		Intent i = new Intent(Intent.ACTION_SEND);
		i.setType("text/plain");
		i.putExtra(Intent.EXTRA_EMAIL, new String[] { "fix-broken-power-button@googlegroups.com" });
		i.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.problem_report));
		i.putExtra(Intent.EXTRA_TEXT, getString(R.string.problem_report_body));
		startActivity(i);
	}

	public void rate(View view) {
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("market://details?id=cz.destil.fixbrokenpb"));
		startActivity(intent);
	}

	public void web(View view) {
		Intent intent = new Intent(Intent.ACTION_VIEW,
				Uri.parse("https://github.com/destil/Fix-Broken-Power-Button"));
		startActivity(intent);
	}
}
