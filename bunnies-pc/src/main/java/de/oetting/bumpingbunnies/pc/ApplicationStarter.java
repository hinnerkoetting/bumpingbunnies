package de.oetting.bumpingbunnies.pc;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;

public class ApplicationStarter {

	protected static final Logger LOGGER = LoggerFactory.getLogger(ApplicationStarter.class);

	public void startApplication(final Application application, final Stage primaryStage) {
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					application.start(primaryStage);
				} catch (Exception e) {
					LOGGER.error("", e);
					Platform.exit();
				}
			}
		});
	}
}
