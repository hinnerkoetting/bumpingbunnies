package de.oetting.bumpingbunnies.pc.configuration;

import java.io.File;
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
			LOGGER.info("Stored configuration at %s", configDirectory() + "/" + configFile());
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public PcConfiguration load() {
		if (!existsConfig()) {
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
		return new File(configDirectory() + configFile()).exists();
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

	private PcConfiguration createDefaultConfig() {
		PcConfiguration configuration = new PcConfiguration();
		configuration.setPlayer1Left(KeyCode.LEFT.getName());
		configuration.setPlayer1Up(KeyCode.UP.getName());
		configuration.setPlayer1Right(KeyCode.RIGHT.getName());
		configuration.setPlayer1Name("Player 1");

		configuration.setPlayer1Left(KeyCode.A.getName());
		configuration.setPlayer1Up(KeyCode.W.getName());
		configuration.setPlayer1Right(KeyCode.D.getName());
		configuration.setPlayer1Name("Player 2");

		configuration.setPlayer1Left(KeyCode.H.getName());
		configuration.setPlayer1Up(KeyCode.U.getName());
		configuration.setPlayer1Right(KeyCode.K.getName());
		configuration.setPlayer1Name("Player 3");

		configuration.setSpeed(25);
		return configuration;
	}
}
