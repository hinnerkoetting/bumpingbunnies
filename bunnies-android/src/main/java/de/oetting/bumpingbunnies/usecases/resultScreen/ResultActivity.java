package de.oetting.bumpingbunnies.usecases.resultScreen;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.resultScreen.model.ResultPlayerEntry;
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
		List<ResultPlayerEntry> resulEntries = getResult().getResults();
		sort(resulEntries);
		ResultArrayAdapter adapter = new ResultArrayAdapter(this, resulEntries);
		view.setAdapter(adapter);
	}

	private void sort(List<ResultPlayerEntry> resulEntries) {
		Collections.sort(resulEntries);
	}

	private ResultWrapper getResult() {
		return (ResultWrapper) getIntent().getExtras().get(ActivityLauncher.RESULT);
	}

	@Override
	public void onBackPressed() {
		ActivityLauncher.startRoom(this);
	}
}
