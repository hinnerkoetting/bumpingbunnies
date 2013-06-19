package de.jumpnbump.usecases.game.android.input;

import android.os.Vibrator;
import android.util.SparseArray;

public class VibrateOnceService implements VibratorService {

	private static final int TIME_VIBRATE = 10;
	private Vibrator vibrator;
	private SparseArray<Object> vibrating = new SparseArray<Object>();

	public VibrateOnceService(Vibrator vibrator) {
		this.vibrator = vibrator;
	}

	@Override
	public void vibrate(int id) {
		if (this.vibrating.get(id) == null) {
			this.vibrating.put(id, new Object());
			this.vibrator.vibrate(TIME_VIBRATE);
		}
	}

	@Override
	public void releaseVibrate(int id) {
		this.vibrating.remove(id);
	}

}
