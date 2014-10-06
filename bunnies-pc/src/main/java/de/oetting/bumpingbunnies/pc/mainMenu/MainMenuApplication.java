package de.oetting.bumpingbunnies.pc.mainMenu;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.pc.main.BunniesMain;

public class MainMenuApplication extends Application {

	private static final Logger LOGGER = LoggerFactory.getLogger(MainMenuApplication.class);

	private Stage primaryStage;

	@Override
	public void start(Stage primaryStage) throws Exception {
		this.primaryStage = primaryStage;
		primaryStage.setTitle("Bumping bunnies");

		// Pane myPane = createView();
		FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/mainMenu.fxml"));
		MainMenuController controller = new MainMenuController(primaryStage);
		loader.setController(controller);
		Pane myPane = (Pane) loader.load();
		Scene myScene = new Scene(myPane);
		primaryStage.setScene(myScene);
		primaryStage.show();
	}

	private Pane createView() {
		Pane myPane = new FlowPane();
		Button button1 = createTwoPlayerButton();
		Button button2 = createAiButton();
		myPane.getChildren().add(0, button1);
		myPane.getChildren().add(1, button2);
		return myPane;
	}

	private Button createTwoPlayerButton() {
		Button button = new Button("Start with two players");
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				startGame(true);
			}

		});
		return button;
	}

	private Button createAiButton() {
		Button button = new Button("Start with AI");
		button.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent arg0) {
				startGame(false);
			}

		});
		return button;
	}

	public static void main(String[] args) {
		launch();
	}

	private void startGame(boolean withTwoHumanPlayers) {
		try {
			BunniesMain bunniesMain = new BunniesMain();
			bunniesMain.setWithTwoHumanPlayers(withTwoHumanPlayers);
			bunniesMain.start(primaryStage);
		} catch (Exception e) {
			LOGGER.error("", e);
			Platform.exit();
		}
	}
}
