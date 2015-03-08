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

	private static final int MIN_SPEED_VALUE = 30;
	private static final int MIN_ZOOM_VALUE = 4;

	private final Activity origin;

	public SettingsViewAccess(Activity origin) {
		this.origin = origin;
	}

	public void init() {
		initSpeed();
		initZoom();
		SettingsEntity defaultEntity = DefaultConfiguration.createDefaultEntity();
		fillView(defaultEntity);
		setPlayerName(android.os.Build.MODEL);
	}

	public int getZoom() {
		SeekBar zoom = findZoomSeekbar();
		return zoom.getProgress() + MIN_ZOOM_VALUE;
	}

	public void setZoom(int zoomValue) {
		SeekBar zoom = findZoomSeekbar();
		zoom.setProgress(zoomValue - MIN_ZOOM_VALUE);
	}

	public InputConfiguration getInputConfiguration() {
		RadioGroup inputRG = findInputConfigurationRadioGroup();
		return InputConfigurationGenerator.createInputConfigurationFromRadioGroup(inputRG);
	}

	public int getSpeed() {
		SeekBar seekbar = findSpeedSeekbar();
		return seekbar.getProgress() + MIN_SPEED_VALUE;
	}

	public boolean isBackgroundChecked() {
		CheckBox view = (CheckBox) this.origin.findViewById(R.id.settings_background);
		return view.isChecked();
	}

	public boolean isAltPixelformatChecked() {
		CheckBox view = (CheckBox) this.origin.findViewById(R.id.settings_pixelformat);
		return view.isChecked();
	}

	private void initSpeed() {
		SeekBar speed = findSpeedSeekbar();
		int startValue = 10;
		TextView view = (TextView) this.origin.findViewById(R.id.settings_speed);
		speed.setOnSeekBarChangeListener(new ProgressBarValueChanger(view, new ProgressToIntValueConverter(
				MIN_SPEED_VALUE), startValue));
		speed.setProgress(startValue);
	}

	private void initZoom() {
		SeekBar zoom = findZoomSeekbar();
		int startValue = 3;
		TextView view = (TextView) this.origin.findViewById(R.id.settings_zoom_number);
		zoom.setOnSeekBarChangeListener(new ProgressBarValueChanger(view, new ProgressToIntValueConverter(
				MIN_ZOOM_VALUE), startValue));
		zoom.setProgress(startValue);
	}

	public void fillView(SettingsEntity settings) {
		fillInputConfiguration(settings);
		setZoom(settings.getZoom());
		setSpeed(settings.getSpeed());
		setPlayerName(settings.getPlayerName());
		setBackgroundChecked(settings.isBackground());
		setAltPixelformatChecked(settings.isAltPixelformat());
		setMusic(settings.isPlayMusic());
		setSound(settings.isPlaySound());
	}

	private void setPlayerName(String playerName) {
		EditText nameTextfield = findNameTextfield();
		nameTextfield.setText(playerName);
	}

	private void setSpeed(int speed) {
		SeekBar speedSeekbar = findSpeedSeekbar();
		speedSeekbar.setProgress(speed - MIN_SPEED_VALUE);
	}

	private void fillInputConfiguration(SettingsEntity settings) {
		RadioGroup inputconfiguration = findInputConfiguration();
		InputConfiguration storedInputConfiguration = settings.getInputConfiguration();
		InputConfigurationGenerator.selectInputConfiguration(storedInputConfiguration, inputconfiguration);
	}

	public void setBackgroundChecked(boolean b) {
		CheckBox view = (CheckBox) this.origin.findViewById(R.id.settings_background);
		view.setChecked(b);
	}

	public void setAltPixelformatChecked(boolean b) {
		CheckBox view = (CheckBox) this.origin.findViewById(R.id.settings_pixelformat);
		view.setChecked(b);
	}

	private void setMusic(boolean playMusic) {
		CheckBox view = getMusicCheckbox();
		view.setChecked(playMusic);
	}

	private void setSound(boolean playSound) {
		CheckBox view = (CheckBox) this.origin.findViewById(R.id.menu_sound_button);
		view.setChecked(playSound);
	}

	public SettingsEntity readFromView() {
		InputConfiguration inputConfiguration = getInputConfiguration();
		int zoom = getZoom();
		int speed = getSpeed();
		String name = getName();
		boolean background = isBackgroundChecked();
		boolean isAltPixelFormat = isAltPixelformatChecked();
		boolean playMusic = isPlayMusicChecked();
		boolean playSound = isPlaySoundChecked();
		return new SettingsEntity(inputConfiguration, zoom, speed, name, background, isAltPixelFormat, playMusic,
				playSound);
	}

	private boolean isPlayMusicChecked() {
		return getMusicCheckbox().isChecked();
	}

	private boolean isPlaySoundChecked() {
		return getSoundCheckbox().isChecked();
	}

	private CheckBox getMusicCheckbox() {
		return (CheckBox) this.origin.findViewById(R.id.menu_music_button);
	}

	private CheckBox getSoundCheckbox() {
		return (CheckBox) this.origin.findViewById(R.id.menu_sound_button);
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
