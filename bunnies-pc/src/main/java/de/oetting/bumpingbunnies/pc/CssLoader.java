package de.oetting.bumpingbunnies.pc;

import java.util.Arrays;
import java.util.List;

public class CssLoader {

	public List<String> loadCssForMainMenu() {
		return Arrays.asList(loadCommonCss(), loadMainMenuCss());
	}

	public List<String> loadCssForConfiguration() {
		return Arrays.asList(loadCommonCss(), loadConfigurationCss());
	}

	private String loadCommonCss() {
		return this.getClass().getResource("/css/common.css").toExternalForm();
	}

	private String loadMainMenuCss() {
		return this.getClass().getResource("/css/mainMenu.css").toExternalForm();
	}

	private String loadConfigurationCss() {
		return this.getClass().getResource("/css/configuration.css").toExternalForm();
	}

	public String loadScoreCss() {
		return loadCommonCss();
	}
}
