package de.oetting.bumpingbunnies.model.configuration;

import java.util.List;

import de.oetting.bumpingbunnies.model.configuration.input.InputConfiguration;

public class Configuration {

	private final List<OpponentConfiguration> otherPlayers;
	private final LocalSettings localSettings;
	private final ServerSettings generalSettings;
	private final LocalPlayerSettings localPlayerSettings;
	private final boolean host;

	public Configuration(LocalSettings localSettings, ServerSettings generalSettings, List<OpponentConfiguration> otherPlayers,
			LocalPlayerSettings localPlayersettings, boolean host) {
		this.generalSettings = generalSettings;
		this.otherPlayers = otherPlayers;
		this.localSettings = localSettings;
		this.localPlayerSettings = localPlayersettings;
		this.host = host;
	}

	public InputConfiguration getInputConfiguration() {
		return this.localSettings.getInputConfiguration();
	}

	public WorldConfiguration getWorldConfiguration() {
		return this.generalSettings.getWorldConfiguration();
	}

	public int getNumberPlayer() {
		return this.otherPlayers.size() + 1;
	}

	public int getZoom() {
		return this.localSettings.getZoom();
	}

	public List<OpponentConfiguration> getOtherPlayers() {
		return this.otherPlayers;
	}

	public ServerSettings getGeneralSettings() {
		return this.generalSettings;
	}

	public LocalPlayerSettings getLocalPlayerSettings() {
		return this.localPlayerSettings;
	}

	public LocalSettings getLocalSettings() {
		return this.localSettings;
	}

	public boolean isHost() {
		return this.host;
	}

}
