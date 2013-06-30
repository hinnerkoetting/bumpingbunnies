package de.oetting.bumpingbunnies.usecases.start;

import android.widget.SeekBar;
import android.widget.TextView;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.start.android.ProgressBarValueChanger;
import de.oetting.bumpingbunnies.usecases.start.android.ProgressToIntValueConverter;

public class StartSettingsService {

	private StartActivity startActivity;

	public StartSettingsService(StartActivity startActivity) {
		super();
		this.startActivity = startActivity;
	}

	public void initSettings() {
		initZoomSetting();
		initNumberPlayerSettings();
		initSpeedSetting();
	}

	private void initNumberPlayerSettings() {
		SeekBar numberPlayers = findNumberPlayerSeekbar();
		int startValue = 0;
		TextView view = (TextView) this.startActivity
				.findViewById(R.id.settings_number_player_number);
		numberPlayers.setOnSeekBarChangeListener(new ProgressBarValueChanger(
				view, new ProgressToIntValueConverter(2), startValue));
		numberPlayers.setProgress(startValue);
	}

	private void initZoomSetting() {
		SeekBar zoom = findZoomSeekbar();
		int startValue = 4;
		TextView view = (TextView) this.startActivity
				.findViewById(R.id.settings_zoom_number);
		zoom.setOnSeekBarChangeListener(new ProgressBarValueChanger(view,
				new ProgressToIntValueConverter(1), 4));
		zoom.setProgress(startValue);
	}

	private void initSpeedSetting() {
		SeekBar speed = findSpeedSeekbar();
		int startValue = 5;
		TextView view = (TextView) this.startActivity
				.findViewById(R.id.settings_speed);
		speed.setOnSeekBarChangeListener(new ProgressBarValueChanger(view,
				new ProgressToIntValueConverter(5), startValue));
		speed.setProgress(startValue);
	}

	public int getNumberOfPlayers() {
		SeekBar numberPlayers = findNumberPlayerSeekbar();
		return numberPlayers.getProgress() + 2;
	}

	public int getZoom() {
		SeekBar zoom = findZoomSeekbar();
		return zoom.getProgress() + 1;
	}

	public int getSpeed() {
		SeekBar seekbar = findSpeedSeekbar();
		return seekbar.getProgress() + 1;
	}

	private SeekBar findZoomSeekbar() {
		return (SeekBar) this.startActivity.findViewById(R.id.zoom);
	}

	private SeekBar findNumberPlayerSeekbar() {
		return (SeekBar) this.startActivity.findViewById(R.id.number_player);
	}

	private SeekBar findSpeedSeekbar() {
		return (SeekBar) this.startActivity.findViewById(R.id.speed);
	}

}
