package de.oetting.bumpingbunnies.usecases.start;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.RadioGroup;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.sql.AsyncDatabaseCreation;
import de.oetting.bumpingbunnies.sql.OnDatabaseCreation;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.game.configuration.AiModus;
import de.oetting.bumpingbunnies.usecases.game.configuration.AiModusGenerator;
import de.oetting.bumpingbunnies.usecases.game.configuration.Configuration;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.InputConfigurationGenerator;
import de.oetting.bumpingbunnies.usecases.game.configuration.LocalSettings;
import de.oetting.bumpingbunnies.usecases.game.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.usecases.game.configuration.WorldConfigurationGenerator;
import de.oetting.bumpingbunnies.usecases.game.factories.SingleplayerFactory;
import de.oetting.bumpingbunnies.usecases.start.sql.DummySettingsDao;
import de.oetting.bumpingbunnies.usecases.start.sql.SettingsDao;
import de.oetting.bumpingbunnies.usecases.start.sql.SettingsStorage;

public class StartActivity extends Activity implements OnDatabaseCreation {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(StartActivity.class);
	private StartSettingsService settingsService;
	private SettingsStorage settingsDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		this.settingsService = new StartSettingsService(this);
		this.settingsService.initSettings();
		this.settingsDao = new DummySettingsDao();
		new AsyncDatabaseCreation().createWritableDatabase(this, this);
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
		List<OpponentConfiguration> otherPlayers = createSpOtherPlayerConfiguration();
		return new Configuration(localSettings, generalSettings, otherPlayers);
	}

	private List<OpponentConfiguration> createSpOtherPlayerConfiguration() {
		int numberPlayer = this.settingsService.getNumberOfPlayers();
		List<OpponentConfiguration> list = new ArrayList<OpponentConfiguration>();
		for (int i = 1; i < numberPlayer; i++) {
			list.add(new OpponentConfiguration(new SingleplayerFactory(
					findSelectedAiMode()), new PlayerProperties(i, "Player "
					+ i)));
		}
		return list;
	}

	private void launchGame(GameStartParameter parameter) {
		ActivityLauncher.launchGame(this, parameter);
	}

	private InputConfiguration findSelectedInputConfiguration() {
		RadioGroup inputGroup = findInputConfiguration();
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

	@Override
	public void databaseCreated(SQLiteDatabase database) {
		LOGGER.info("Db created");
		this.settingsDao = new SettingsDao(database);
		fillStoredSettings();
	}

	private void fillStoredSettings() {
		LOGGER.info("Reading settings from database");
		LocalSettings storedSettings = this.settingsDao.readStoredSettings();
		if (storedSettings != null) {
			restoreInputConfiguration(storedSettings);
			restoreZoom(storedSettings);
		} else {
			LOGGER.info("no settings found");
		}
	}

	private void restoreZoom(LocalSettings storedSettings) {
		this.settingsService.setZoom(storedSettings.getZoom());
	}

	private void restoreInputConfiguration(LocalSettings storedSettings) {
		RadioGroup inputconfiguration = findInputConfiguration();
		InputConfiguration storedInputConfiguration = storedSettings
				.getInputConfiguration();
		InputConfigurationGenerator.selectInputConfiguration(
				storedInputConfiguration, inputconfiguration);
	}

	private RadioGroup findInputConfiguration() {
		return (RadioGroup) findViewById(R.id.start_input_group);
	}

	@Override
	protected void onPause() {
		super.onPause();
		storeSelectedLocalSettings();
	}

	@Override
	protected void onResume() {
		super.onResume();
		fillStoredSettings();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.settingsDao.close();
	}

	private void storeSelectedLocalSettings() {
		LOGGER.info("storing local settings");
		LocalSettings selectedSettings = createLocalSettings();
		this.settingsDao.store(selectedSettings);
	}

}
