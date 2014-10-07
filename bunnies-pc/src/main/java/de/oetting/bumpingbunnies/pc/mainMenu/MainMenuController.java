package de.oetting.bumpingbunnies.pc.mainMenu;

import java.net.InetAddress;
import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.core.configuration.GameParameterFactory;
import de.oetting.bumpingbunnies.core.network.room.Host;
import de.oetting.bumpingbunnies.core.networking.client.CouldNotOpenBroadcastSocketException;
import de.oetting.bumpingbunnies.core.networking.client.ListenForBroadcastsThread;
import de.oetting.bumpingbunnies.core.networking.client.OnBroadcastReceived;
import de.oetting.bumpingbunnies.core.networking.client.factory.ListenforBroadCastsThreadFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.AiModus;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.GeneralSettings;
import de.oetting.bumpingbunnies.model.configuration.InputConfiguration;
import de.oetting.bumpingbunnies.model.configuration.LocalPlayerSettings;
import de.oetting.bumpingbunnies.model.configuration.LocalSettings;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.model.configuration.PlayerProperties;
import de.oetting.bumpingbunnies.model.configuration.WorldConfiguration;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.pc.main.BunniesMain;

public class MainMenuController implements Initializable, OnBroadcastReceived {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainMenuController.class);

	private final Stage primaryStage;
	@FXML
	private Button withTwoPlayersButton;
	@FXML
	private Button withAiButton;
	@FXML
	private Button connectButton;
	@FXML
	private TableView<Host> hostsTable;

	private ListenForBroadcastsThread listenForBroadcastsThread;

	public MainMenuController(Stage primaryStage) {
		this.primaryStage = primaryStage;
	}

	@FXML
	public void onButtonWithTwoPlayers(ActionEvent event) {
		startGameWithTwoPlayers();
	}

	@FXML
	public void onButtonWithAi(ActionEvent event) {
		startGameWithAi();
	}

	private void startGameWithTwoPlayers() {
		Configuration configuration = createConfiguration(new OpponentConfiguration(AiModus.OFF, new PlayerProperties(1, "Player 2"), Opponent.createOpponent(
				"Player2", OpponentType.LOCAL_PLAYER)));
		startGame(GameParameterFactory.createSingleplayerParameter(configuration));
	}

	private Configuration createConfiguration(OpponentConfiguration opponent) {
		LocalSettings localSettings = new LocalSettings(InputConfiguration.KEYBOARD, 1, true, false);
		GeneralSettings generalSettings = new GeneralSettings(WorldConfiguration.CLASSIC, 25, NetworkType.WLAN);
		List<OpponentConfiguration> opponents = Arrays.asList(opponent);
		LocalPlayerSettings localPlayerSettings = new LocalPlayerSettings("Player 1");
		Configuration configuration = new Configuration(localSettings, generalSettings, opponents, localPlayerSettings, true);
		return configuration;
	}

	private void startGameWithAi() {
		Configuration configuration = createConfiguration(new OpponentConfiguration(AiModus.NORMAL, new PlayerProperties(1, "Player 2"),
				Opponent.createOpponent("Player2", OpponentType.AI)));
		startGame(GameParameterFactory.createSingleplayerParameter(configuration));
	}

	private void startGame(GameStartParameter parameter) {
		try {
			BunniesMain bunniesMain = new BunniesMain(parameter);
			bunniesMain.start(primaryStage);
		} catch (Exception e) {
			LOGGER.error("", e);
			Platform.exit();
		}
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		hostsTable.getSelectionModel().selectedIndexProperty().addListener(event -> connectButton.setDisable(false));
		listenForBroadcasts();
	}

	private void listenForBroadcasts() {
		try {
			listenForBroadcastsThread = ListenforBroadCastsThreadFactory.create(this);
			listenForBroadcastsThread.start();
		} catch (CouldNotOpenBroadcastSocketException e) {
			hostsTable.getItems().add(new Host("Cannot listen to broadcasts..."));
		}
	}

	@Override
	public void broadcastReceived(InetAddress senderAddress) {
		Host host = new Host(senderAddress);
		if (!hostsTable.getItems().contains(host))
			hostsTable.getItems().add(host);
	}

	@Override
	public void errorOnBroadcastListening() {
		Platform.exit();
	}

	public void onButtonConnect() {
		listenForBroadcastsThread.cancel();
		throw new IllegalArgumentException();
		// startGame(true);
	}
}