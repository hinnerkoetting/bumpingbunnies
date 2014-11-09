package de.oetting.bumpingbunnies.pc;

import java.util.Arrays;
import java.util.List;

public class CssLoader {

	public List<String> loadCssForMainMenu() {
		return Arrays.asList(loadCommonCss(), loadMainMenuCss());
	}

	private String loadCommonCss() {
		return this.getClass().getResource("/css/common.css").toExternalForm();
	}

	private String loadMainMenuCss() {
		return this.getClass().getResource("/css/mainMenu.css").toExternalForm();
	}
}
