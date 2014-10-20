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
import de.oetting.bumpingbunnies.android.sql.AsyncDatabaseCreation;
import de.oetting.bumpingbunnies.android.sql.OnDatabaseCreation;
import de.oetting.bumpingbunnies.core.configuration.GameParameterFactory;
import de.oetting.bumpingbunnies.core.game.ConnectionIdentifierFactory;
import de.oetting.bumpingbunnies.core.input.NoopInputConfiguration;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;
import de.oetting.bumpingbunnies.model.configuration.SettingsEntity;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.usecases.ActivityLauncher;
import de.oetting.bumpingbunnies.usecases.game.android.view.configuration.AiModusGenerator;
import de.oetting.bumpingbunnies.usecases.start.sql.DummySettingsDao;
import de.oetting.bumpingbunnies.usecases.start.sql.SettingsDao;
import de.oetting.bumpingbunnies.usecases.start.sql.SettingsStorage;

public class StartActivity extends Activity implements OnDatabaseCreation {

	private static final Logger LOGGER = LoggerFactory.getLogger(StartActivity.class);
	private SettingsStorage settingsDao;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_start);
		this.settingsDao = new DummySettingsDao();
		new AsyncDatabaseCreation().createReadonlyDatabase(this, this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.game, menu);
		return true;
	}

	/**
	 * Call from Actitity
	 */
	public void onClickSingleplayer(View v) {
		Configuration configuration = createConfiguration();
		GameStartParameter parameter = GameParameterFactory.createSingleplayerParameter(configuration);

		launchGame(parameter);
	}

	public void onClickSettings(View v) {
		ActivityLauncher.startSettings(this);
	}

	private Configuration createConfiguration() {
		SettingsEntity settings = readSettingsFromDb();
		LocalSettings localSettings = createLocalSettings(settings);
		LocalPlayerSettings localPlayerSettings = createLocalPlayerSettings(settings);
		ServerSettings generalSettings = createGeneralSettings(settings);
		List<OpponentConfiguration> otherPlayers = createSpOtherPlayerConfiguration(settings);
		return new Configuration(localSettings, generalSettings, otherPlayers, localPlayerSettings, true);
	}

	private LocalPlayerSettings createLocalPlayerSettings(SettingsEntity settings) {
		return new LocalPlayerSettings(settings.getPlayerName());
	}

	private LocalSettings createLocalSettings(SettingsEntity settings) {
		return new LocalSettings(settings.getInputConfiguration(), settings.getZoom(), settings.isBackground(), settings.isAltPixelformat());
	}

	private List<OpponentConfiguration> createSpOtherPlayerConfiguration(SettingsEntity settings) {
		int numberPlayer = settings.getNumberPlayer();
		List<OpponentConfiguration> list = new ArrayList<OpponentConfiguration>();
		for (int i = 1; i < numberPlayer; i++) {
			ConnectionIdentifier opponent = ConnectionIdentifierFactory.createAiPlayer("" + i);
			list.add(new OpponentConfiguration(findSelectedAiMode(), new PlayerProperties(i, "Player " + i), opponent, new NoopInputConfiguration()));
		}
		return list;
	}

	private void launchGame(GameStartParameter parameter) {
		ActivityLauncher.launchGame(this, parameter);
	}

	private AiModus findSelectedAiMode() {
		RadioGroup radioGroup = (RadioGroup) findViewById(R.id.start_ai_group);
		return AiModusGenerator.createFromRadioGroup(radioGroup);
	}

	public void onClickMultiplayer(View v) {
		SettingsEntity settings = readSettingsFromDb();
		LocalSettings localSettings = createLocalSettings(settings);
		LocalPlayerSettings localPlayerSettings = createLocalPlayerSettings(settings);
		ServerSettings generalSettings = createGeneralSettings(settings);
		ActivityLauncher.startRoom(this, localSettings, generalSettings, localPlayerSettings);
	}

	private SettingsEntity readSettingsFromDb() {
		return this.settingsDao.readStoredSettings();
	}

	private ServerSettings createGeneralSettings(SettingsEntity settings) {
		WorldConfiguration world = findWorldConfiguration();
		int speed = settings.getSpeed();
		/** TODO */
		ServerSettings generalSettings = new ServerSettings(world, speed, NetworkType.WLAN);
		return generalSettings;
	}

	private WorldConfiguration findWorldConfiguration() {
		RadioGroup rg = (RadioGroup) findViewById(R.id.start_world_group);
		return WorldConfigurationGenerator.createWorldConfigurationFromRadioGroup(rg);
	}

	@Override
	public void databaseCreated(SQLiteDatabase database) {
		LOGGER.info("Db created");
		this.settingsDao = new SettingsDao(database);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		this.settingsDao.close();
	}

	@Override
	public void onBackPressed() {
		finish();
		super.moveTaskToBack(true);
	}

}
