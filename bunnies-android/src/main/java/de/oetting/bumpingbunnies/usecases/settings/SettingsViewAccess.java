package de.oetting.bumpingbunnies.usecases.settings;

import android.app.Activity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.input.DefaultConfiguration;
import de.oetting.bumpingbunnies.core.configuration.ConfigurationConstants;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;
import de.oetting.bumpingbunnies.model.configuration.SpeedMode;
import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;
import de.oetting.bumpingbunnies.usecases.start.OptimalZoom;
import de.oetting.bumpingbunnies.usecases.start.android.ProgressBarValueChanger;
import de.oetting.bumpingbunnies.usecases.start.android.ProgressToIntValueConverter;

/**
 * Offers access methods for Settings view.<br>
 * Allows to write and read values from the view<br>
 * 
 */
public class SettingsViewAccess {

	private static final int MIN_ZOOM_VALUE = 4;
	private static final Logger LOGGER = LoggerFactory.getLogger(SettingsViewAccess.class);

	private final Activity origin;

	public SettingsViewAccess(Activity origin) {
		this.origin = origin;
	}

	public void init() {
		initSpeed();
		initZoom();
		SettingsEntity defaultEntity = DefaultConfiguration.createDefaultEntity(OptimalZoom.computeOptimalZoom(origin));
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
		if (getMediumSpeedButton().isChecked())
			return mediumSpeed();
		if (getFastSpeedButton().isChecked())
			return fastSpeed();
		return slowSpeed();
	}

	private int mediumSpeed() {
		return SpeedMode.MEDIUM.getSpeed();
	}

	private int fastSpeed() {
		return SpeedMode.FAST.getSpeed();
	}

	private int slowSpeed() {
		return SpeedMode.SLOW.getSpeed();
	}

	private void initSpeed() {
		RadioButton button = getMediumSpeedButton();
		button.setChecked(true);
	}

	private RadioButton getMediumSpeedButton() {
		return (RadioButton) origin.findViewById(R.id.medium);
	}

	private RadioButton getFastSpeedButton() {
		return (RadioButton) origin.findViewById(R.id.fast);
	}

	private CompoundButton getSlowSpeedButton() {
		return (RadioButton) origin.findViewById(R.id.slow);
	}

	private void initZoom() {
		SeekBar zoom = findZoomSeekbar();
		int startValue = 3;
		TextView view = (TextView) this.origin.findViewById(R.id.settings_zoom_number);
		zoom.setOnSeekBarChangeListener(new ProgressBarValueChanger(view, new ProgressToIntValueConverter(
				MIN_ZOOM_VALUE), startValue));
		zoom.setProgress(0);
		zoom.setMax(OptimalZoom.computeMaximumZoom(origin) - MIN_ZOOM_VALUE);
	}

	public void fillView(SettingsEntity settings) {
		fillInputConfiguration(settings);
		setZoom(settings.getZoom());
		setSpeed(settings.getSpeed());
		setPlayerName(settings.getPlayerName());
		setMusic(settings.isPlayMusic());
		setSound(settings.isPlaySound());
		setLefthanded(settings.isLefthanded());
		setVictoryLimit(settings.getVictoryLimit());
	}

	private void setVictoryLimit(int victoryLimit) {
		EditText view = getVictoryLimitTextview();
		view.setText(Integer.toString(victoryLimit));
	}

	private EditText getVictoryLimitTextview() {
		return (EditText) origin.findViewById(R.id.victory_limit);
	}

	private void setLefthanded(boolean lefthanded) {
		findLefthandedCheckbox().setChecked(lefthanded);
	}

	private CheckBox findLefthandedCheckbox() {
		return (CheckBox) origin.findViewById(R.id.left_handed_cb);
	}

	private void setPlayerName(String playerName) {
		EditText nameTextfield = findNameTextfield();
		nameTextfield.setText(playerName);
	}

	private void setSpeed(int speed) {
		if (speed == slowSpeed())
			getSlowSpeedButton().setChecked(true);
		else if (speed == fastSpeed())
			getFastSpeedButton().setChecked(true);
		else
			getMediumSpeedButton().setChecked(true);
	}

	private void fillInputConfiguration(SettingsEntity settings) {
		RadioGroup inputconfiguration = findInputConfiguration();
		InputConfiguration storedInputConfiguration = settings.getInputConfiguration();
		InputConfigurationGenerator.selectInputConfiguration(storedInputConfiguration, inputconfiguration);
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
		boolean playMusic = isPlayMusicChecked();
		boolean playSound = isPlaySoundChecked();
		boolean leftHanded = isLefthandedChecked();
		int victoryLimit = getVictoryLimit();
		return new SettingsEntity(inputConfiguration, zoom, speed, name, playMusic,
				playSound, leftHanded, victoryLimit);
	}

	private int getVictoryLimit() {
		EditText victoryLimitTextview = getVictoryLimitTextview();
		try {
			return Integer.parseInt(victoryLimitTextview.getText().toString());
		} catch (Exception e) {
			LOGGER.info("Could not read victory limit. Value was: %s", victoryLimitTextview.getText());
		}
		return ConfigurationConstants.DEFAULT_VICTORY_LIMIT;
	}

	private boolean isLefthandedChecked() {
		return findLefthandedCheckbox().isChecked();
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

}
