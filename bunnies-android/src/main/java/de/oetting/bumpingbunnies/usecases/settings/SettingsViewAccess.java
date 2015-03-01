package de.oetting.bumpingbunnies.usecases.settings;

import android.app.Activity;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.input.DefaultConfiguration;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;
import de.oetting.bumpingbunnies.usecases.start.android.ProgressBarValueChanger;
import de.oetting.bumpingbunnies.usecases.start.android.ProgressToIntValueConverter;

/**
 * Offers access methods for Settings view.<br>
 * Allows to write and read values from the view<br>
 * 
 */
public class SettingsViewAccess {

	private final Activity origin;

	public SettingsViewAccess(Activity origin) {
		super();
		this.origin = origin;
	}

	public void init() {
		initSpeed();
		initZoom();
		SettingsEntity defaultEntity = DefaultConfiguration
				.createDefaultEntity();
		fillView(defaultEntity);
	}

	public int getZoom() {
		SeekBar zoom = findZoomSeekbar();
		return zoom.getProgress() + 1;
	}

	public void setZoom(int zoomValue) {
		SeekBar zoom = findZoomSeekbar();
		zoom.setProgress(zoomValue - 1);
	}

	public InputConfiguration getInputConfiguration() {
		RadioGroup inputRG = findInputConfigurationRadioGroup();
		return InputConfigurationGenerator
				.createInputConfigurationFromRadioGroup(inputRG);
	}

	public int getSpeed() {
		SeekBar seekbar = findSpeedSeekbar();
		return seekbar.getProgress() + 20;
	}

	public boolean isBackgroundChecked() {
		CheckBox view = (CheckBox) this.origin
				.findViewById(R.id.settings_background);
		return view.isChecked();
	}

	public boolean isAltPixelformatChecked() {
		CheckBox view = (CheckBox) this.origin
				.findViewById(R.id.settings_pixelformat);
		return view.isChecked();
	}

	private void initSpeed() {
		SeekBar speed = findSpeedSeekbar();
		int startValue = 5;
		TextView view = (TextView) this.origin
				.findViewById(R.id.settings_speed);
		speed.setOnSeekBarChangeListener(new ProgressBarValueChanger(view,
				new ProgressToIntValueConverter(5), startValue));
		speed.setProgress(startValue);
	}

	private void initZoom() {
		SeekBar zoom = findZoomSeekbar();
		int startValue = 4;
		TextView view = (TextView) this.origin
				.findViewById(R.id.settings_zoom_number);
		zoom.setOnSeekBarChangeListener(new ProgressBarValueChanger(view,
				new ProgressToIntValueConverter(1), 4));
		zoom.setProgress(startValue);
	}

	public void fillView(SettingsEntity settings) {
		fillInputConfiguration(settings);
		setZoom(settings.getZoom());
		setSpeed(settings.getSpeed());
		setPlayerName(settings.getPlayerName());
		setBackgroundChecked(settings.isBackground());
		setAltPixelformatChecked(settings.isAltPixelformat());
	}

	private void setPlayerName(String playerName) {
		EditText nameTextfield = findNameTextfield();
		nameTextfield.setText(playerName);
	}

	private void setSpeed(int speed) {
		SeekBar speedSeekbar = findSpeedSeekbar();
		speedSeekbar.setProgress(speed - 20);
	}

	private void fillInputConfiguration(SettingsEntity settings) {
		RadioGroup inputconfiguration = findInputConfiguration();
		InputConfiguration storedInputConfiguration = settings
				.getInputConfiguration();
		InputConfigurationGenerator.selectInputConfiguration(
				storedInputConfiguration, inputconfiguration);
	}

	public void setBackgroundChecked(boolean b) {
		CheckBox view = (CheckBox) this.origin
				.findViewById(R.id.settings_background);
		view.setChecked(b);
	}

	public void setAltPixelformatChecked(boolean b) {
		CheckBox view = (CheckBox) this.origin
				.findViewById(R.id.settings_pixelformat);
		view.setChecked(b);
	}

	public SettingsEntity readFromView() {
		InputConfiguration inputConfiguration = getInputConfiguration();
		int zoom = getZoom();
		int speed = getSpeed();
		String name = getName();
		boolean background = isBackgroundChecked();
		boolean isAltPixelFormat = isAltPixelformatChecked();
		// TODO add music and sound to view
		return new SettingsEntity(inputConfiguration, zoom, 
				speed, name, background, isAltPixelFormat, true, true);
	}

	private String getName() {
		EditText editText = findNameTextfield();
		return editText.getText().toString();
	}

	private EditText findNameTextfield() {
		return (EditText) this.origin.findViewById(R.id.settings_playername);
	}

	private RadioGroup findInputConfigurationRadioGroup() {
		return (RadioGroup) this.origin.findViewById(R.id.start_input_group);
	}

	private RadioGroup findInputConfiguration() {
		return (RadioGroup) this.origin.findViewById(R.id.start_input_group);
	}

	private SeekBar findZoomSeekbar() {
		return (SeekBar) this.origin.findViewById(R.id.zoom);
	}

	private SeekBar findSpeedSeekbar() {
		return (SeekBar) this.origin.findViewById(R.id.speed);
	}

}
