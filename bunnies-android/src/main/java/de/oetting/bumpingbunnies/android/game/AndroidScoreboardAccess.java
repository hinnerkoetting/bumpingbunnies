package de.oetting.bumpingbunnies.android.game;

import java.util.List;

import android.app.Activity;
import de.oetting.bumpingbunnies.core.game.steps.ScoreboardAccess;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class AndroidScoreboardAccess implements ScoreboardAccess {

	private final ScoreboardArrayAdapter scoreArrayAdapter;
	private final Activity activity;

	public AndroidScoreboardAccess(ScoreboardArrayAdapter scoreArrayAdapter, Activity activity) {
		this.scoreArrayAdapter = scoreArrayAdapter;
		this.activity = activity;
	}

	
	@Override
	public void replaceBunnies(final List<Bunny> replace) {
		activity.runOnUiThread(new Runnable() {
			
			@Override
			public void run() {
				scoreArrayAdapter.clear();
				for (Bunny bunny: replace)
					scoreArrayAdapter.add(bunny);
			}
		});
		
	}

}
