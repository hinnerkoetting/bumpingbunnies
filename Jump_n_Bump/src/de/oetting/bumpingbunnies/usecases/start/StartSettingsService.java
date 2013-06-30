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
		SeekBar numberPlayers = (SeekBar) this.startActivity
				.findViewById(R.id.number_player);
		TextView view = (TextView) this.startActivity
				.findViewById(R.id.settings_number_player_number);
		numberPlayers.setOnSeekBarChangeListener(new ProgressBarValueChanger(
				view, new ProgressToIntValueConverter(1), 0));
		numberPlayers.setProgress(0);
	}

	private void initZoomSetting() {
		SeekBar zoom = (SeekBar) this.startActivity.findViewById(R.id.zoom);
		TextView view = (TextView) this.startActivity
				.findViewById(R.id.settings_zoom_number);
		zoom.setOnSeekBarChangeListener(new ProgressBarValueChanger(view,
				new ProgressToIntValueConverter(1), 4));
	}

	private void initSpeedSetting() {
		SeekBar speed = (SeekBar) this.startActivity.findViewById(R.id.speed);
		TextView view = (TextView) this.startActivity
				.findViewById(R.id.settings_speed);
		speed.setOnSeekBarChangeListener(new ProgressBarValueChanger(view,
				new ProgressToIntValueConverter(5), 5));
	}
}
