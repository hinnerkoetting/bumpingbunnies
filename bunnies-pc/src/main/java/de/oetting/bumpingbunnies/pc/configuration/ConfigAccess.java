package de.oetting.bumpingbunnies.pc.configuration;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javafx.scene.input.KeyCode;

import javax.xml.bind.JAXB;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.pc.configMenu.PcConfiguration;

public class ConfigAccess {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigAccess.class);

	public void saveConfiguration(PcConfiguration configuration) {
		try {
			ensureConfigDirectoryExists();
			JAXB.marshal(configuration, Files.newOutputStream(createConfigFilePath()));
			LOGGER.info("Stored configuration at %s", createConfigFilePath().toAbsolutePath().toString());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public PcConfiguration load() {
		if (!existsConfig()) {
			LOGGER.info("Config does not exist. Creating default config...");
			createDefaultConfig();
		}
		return loadConfig();
	}

	private PcConfiguration loadConfig() {
		try {
			return JAXB.unmarshal(Files.newInputStream(createConfigFilePath()), PcConfiguration.class);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private Path createConfigFilePath() {
		return Paths.get(configDirectory(), configFile());
	}

	private boolean existsConfig() {
		return Files.exists(createConfigFilePath());
	}

	private void ensureConfigDirectoryExists() {
		try {
			Path directory = Paths.get(configDirectory());
			if (!Files.isDirectory(directory))
				Files.createDirectory(directory);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	private String configDirectory() {
		return "config";
	}

	private String configFile() {
		return "config.xml";
	}

	private void createDefaultConfig() {
		PcConfiguration configuration = createDefaultObject();
		saveConfiguration(configuration);
	}

	private PcConfiguration createDefaultObject() {
		PcConfiguration configuration = new PcConfiguration();
		configuration.getPlayer1Configuration().setPlayerLeft(KeyCode.LEFT.getName());
		configuration.getPlayer1Configuration().setPlayerUp(KeyCode.UP.getName());
		configuration.getPlayer1Configuration().setPlayerRight(KeyCode.RIGHT.getName());
		configuration.getPlayer1Configuration().setPlayerName("Angel");

		configuration.getPlayer2Configuration().setPlayerLeft(KeyCode.A.getName());
		configuration.getPlayer2Configuration().setPlayerUp(KeyCode.W.getName());
		configuration.getPlayer2Configuration().setPlayerRight(KeyCode.D.getName());
		configuration.getPlayer2Configuration().setPlayerName("Siggi");

		configuration.getPlayer3Configuration().setPlayerLeft(KeyCode.H.getName());
		configuration.getPlayer3Configuration().setPlayerUp(KeyCode.U.getName());
		configuration.getPlayer3Configuration().setPlayerRight(KeyCode.K.getName());
		configuration.getPlayer3Configuration().setPlayerName("Uschi");

		configuration.setSpeed(25);
		configuration.setPlayMusic(true);
		configuration.setPlaySound(true);
		return configuration;
	}
}
