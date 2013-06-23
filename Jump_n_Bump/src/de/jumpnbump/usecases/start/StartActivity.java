package de.jumpnbump.usecases.start;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;
import de.jumpnbump.R;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.ActivityLauncher;
import de.jumpnbump.usecases.game.businesslogic.GameStartParameter;
import de.jumpnbump.usecases.game.configuration.AiModus;
import de.jumpnbump.usecases.game.configuration.AiModusGenerator;
import de.jumpnbump.usecases.game.configuration.Configuration;
import de.jumpnbump.usecases.game.configuration.InputConfiguration;
import de.jumpnbump.usecases.game.configuration.InputConfigurationGenerator;
import de.jumpnbump.usecases.game.configuration.LocalSettings;
import de.jumpnbump.usecases.game.configuration.OtherPlayerConfiguration;
import de.jumpnbump.usecases.game.configuration.WorldConfiguration;
import de.jumpnbump.usecases.game.configuration.WorldConfigurationGenerator;
import de.jumpnbump.usecases.game.factories.NetworkFactory;

public class StartActivity extends Activity {

	private static final MyLog LOGGER = Logger.getLogger(StartActivity.class);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);

		initZoomSetting();
		initNumberPlayerSettings();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game, menu);
		return true;
	}

	public void onClickSingleplayer(View v) {
		Configuration configuration = createConfiguration();
		GameStartParameter parameter = GameParameterFactory
				.createSingleplayerParameter(configuration);

		launchGame(parameter);
	}

	public void startGame(int playerId) {
		Configuration configuration = createConfiguration();
		GameStartParameter parameter = GameParameterFactory.createParameter(
				playerId, configuration);
		launchGame(parameter);
	}

	private Configuration createConfiguration() {
		InputConfiguration selectedInput = findSelectedInputConfiguration();

		WorldConfiguration world = findSelectedWorld();
		LocalSettings localSettings = new LocalSettings(selectedInput, world,
				getZoom());
		return new Configuration(localSettings,
				createOtherPlayerconfigurations());
	}

	private List<OtherPlayerConfiguration> createOtherPlayerconfigurations() {
		int number = getNumberOfPlayers();
		List<OtherPlayerConfiguration> otherPlayers = new ArrayList<OtherPlayerConfiguration>(
				number);
		// TODO
		AiModus aiModus = findSelectedAiMode();
		for (int i = 1; i < number; i++) {
			// otherPlayers.add(new OtherPlayerConfiguration(
			// new SingleplayerFactory(aiModus)));
			// TODO
			NetworkFactory networkFactory = new NetworkFactory(null, 0);
			OtherPlayerConfiguration otherPlayerConfiguration = new OtherPlayerConfiguration(
					networkFactory, -1);
			otherPlayers.add(otherPlayerConfiguration);
		}
		return otherPlayers;
	}

	private void launchGame(GameStartParameter parameter) {
		ActivityLauncher.launchGame(this, parameter);
	}

	private InputConfiguration findSelectedInputConfiguration() {
		RadioGroup inputGroup = (RadioGroup) findViewById(R.id.start_input_group);
		return InputConfigurationGenerator
				.createInputConfigurationFromRadioGroup(inputGroup);
	}

	private AiModus findSelectedAiMode() {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.start_ai_group);
		return AiModusGenerator.createFromRadioGroup(radioGroup);
	}

	private WorldConfiguration findSelectedWorld() {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.start_world_group);
		return WorldConfigurationGenerator
				.createWorldConfigurationFromRadioGroup(radioGroup);
	}

	private int getNumberOfPlayers() {
		SeekBar numberPlayers = (SeekBar) findViewById(R.id.number_player);
		try {
			return numberPlayers.getProgress() + 1;
		} catch (Exception e) {
			return 2;
		}
	}

	private int getZoom() {
		SeekBar zoom = (SeekBar) findViewById(R.id.zoom);
		return zoom.getProgress() + 1;
	}

	private void initNumberPlayerSettings() {
		SeekBar numberPlayers = (SeekBar) findViewById(R.id.number_player);
		numberPlayers.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				updateNumberPlayer();
			}
		});
		numberPlayers.setProgress(1);
		updateNumberPlayer();
	}

	private void updateNumberPlayer() {
		TextView view = (TextView) findViewById(R.id.settings_number_player_number);
		view.setText(Integer.toString(getNumberOfPlayers()));
	}

	private void initZoomSetting() {
		SeekBar zoom = (SeekBar) findViewById(R.id.zoom);
		zoom.setOnSeekBarChangeListener(new OnSeekBarChangeListener() {

			@Override
			public void onStopTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onStartTrackingTouch(SeekBar seekBar) {
			}

			@Override
			public void onProgressChanged(SeekBar seekBar, int progress,
					boolean fromUser) {
				updateZoom();
			}
		});
		zoom.setProgress(4);
		updateZoom();
	}

	private void updateZoom() {
		TextView view = (TextView) findViewById(R.id.settings_zoom_number);
		view.setText(Integer.toString(getZoom()));
	}

	public void onClickMultiplayer(View v) {
		ActivityLauncher.startRoom(this, createConfiguration());
	}

}
