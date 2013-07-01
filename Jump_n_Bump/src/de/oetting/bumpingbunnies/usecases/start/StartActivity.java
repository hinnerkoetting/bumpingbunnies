package de.oetting.bumpingbunnies.usecases.start;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RadioGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.configuration.AiModus;
import de.oetting.bumpingbunnies.usecases.game.configuration.AiModusGenerator;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.InputConfigurationGenerator;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.NetworkSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.OtherPlayerConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfigurationGenerator;
import de.oetting.bumpingbunnies.usecases.game.factories.SingleplayerFactory;

public class StartActivity extends Activity {

	private StartSettingsService settingsService;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		this.settingsService = new StartSettingsService(this);
		this.settingsService.initSettings();
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

	private Configuration createConfiguration() {
		LocalSettings localSettings = createLocalSettings();

		GeneralSettings generalSettings = createGeneralSettings();
		List<OtherPlayerConfiguration> otherPlayers = createSpOtherPlayerConfiguration();
		NetworkSettings networkSettings = new NetworkSettings(false);
		return new Configuration(localSettings, generalSettings,
				networkSettings, otherPlayers);
	}

	private List<OtherPlayerConfiguration> createSpOtherPlayerConfiguration() {
		int numberPlayer = this.settingsService.getNumberOfPlayers();
		List<OtherPlayerConfiguration> list = new ArrayList<OtherPlayerConfiguration>();
		for (int i = 1; i < numberPlayer; i++) {
			list.add(new OtherPlayerConfiguration(new SingleplayerFactory(
					findSelectedAiMode()), i));
		}
		return list;
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

	public void onClickMultiplayer(View v) {
		LocalSettings localSettings = createLocalSettings();
		GeneralSettings generalSettings = createGeneralSettings();
		ActivityLauncher.startRoom(this, localSettings, generalSettings);
	}

	private LocalSettings createLocalSettings() {
		InputConfiguration selectedInput = findSelectedInputConfiguration();

		LocalSettings localSettings = new LocalSettings(selectedInput,
				this.settingsService.getZoom());
		return localSettings;
	}

	private GeneralSettings createGeneralSettings() {
		WorldConfiguration world = findSelectedWorld();
		int speed = this.settingsService.getSpeed();
		GeneralSettings settings = new GeneralSettings(world, speed);
		return settings;
	}
}
