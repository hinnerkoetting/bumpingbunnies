package de.oetting.bumpingbunnies.usecases.game.android.input;

public interface VibratorService {

	void vibrate(int id);

	void releaseVibrate(int id);
}
