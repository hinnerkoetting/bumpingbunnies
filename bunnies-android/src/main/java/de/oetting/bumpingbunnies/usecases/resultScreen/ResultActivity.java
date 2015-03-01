package de.oetting.bumpingbunnies.usecases.resultScreen;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultWrapper;

public class ResultActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_result);
		fillView();
	}

	private void fillView() {
		ListView view = (ListView) findViewById(R.id.result_player);
		ResultArrayAdapter adapter = new ResultArrayAdapter(this, getResult()
				.getResults());
		view.setAdapter(adapter);
	}

	private ResultWrapper getResult() {
		return (ResultWrapper) getIntent().getExtras().get(
				ActivityLauncher.RESULT);
	}

	@Override
	public void onBackPressed() {
		ActivityLauncher.toStart(this);
	}
}
