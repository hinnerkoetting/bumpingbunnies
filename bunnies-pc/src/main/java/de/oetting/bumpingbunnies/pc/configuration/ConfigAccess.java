package de.oetting.bumpingbunnies.pc.configuration;

import java.io.File;

import javax.xml.bind.JAXB;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.pc.configMenu.PcConfiguration;

public class ConfigAccess {

	private static final Logger LOGGER = LoggerFactory.getLogger(ConfigAccess.class);

	public void saveConfiguration(PcConfiguration configuration) {
		ensureConfigDirectoryExists();
		JAXB.marshal(configuration, new File(configDirectory() + configFile()));
		LOGGER.info("Stored configuration at %s", configDirectory() + configFile());
	}

	private void ensureConfigDirectoryExists() {
		new File(configDirectory()).mkdir();
	}

	private String configDirectory() {
		return "config/";
	}

	private String configFile() {
		return "config.xml";
	}
}
