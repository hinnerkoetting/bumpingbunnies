package de.oetting.bumpingbunnies.model.configuration;

import java.util.Collections;
import java.util.Set;

/**
 * Settings which are valid for all players.
 * 
 */
public class ServerSettings {

	private final WorldConfiguration worldConfiguration;
	private final int speedSetting;
	private final Set<NetworkType> networkTypes;
	private final int victoryLimit;
	private final boolean gameIsCurrentlyPaused;

	public ServerSettings(WorldConfiguration worldConfiguration, int speedSetting, Set<NetworkType> networkTypes,
			int victoryLimit, boolean gameIsCurrentlyPaused) {
		this.worldConfiguration = worldConfiguration;
		this.speedSetting = speedSetting;
		this.networkTypes = networkTypes;
		this.victoryLimit = victoryLimit;
		this.gameIsCurrentlyPaused = gameIsCurrentlyPaused;
	}

	public int getSpeedSetting() {
		return this.speedSetting;
	}

	public Set<NetworkType> getNetworkTypes() {
		return Collections.unmodifiableSet(this.networkTypes);
	}

	public WorldConfiguration getWorldConfiguration() {
		return this.worldConfiguration;
	}

	public int getVictoryLimit() {
		return victoryLimit;
	}

	public boolean isGameIsCurrentlyPaused() {
		return gameIsCurrentlyPaused;
	}
	
	public ServerSettings cloneWithGamePausedSettings(boolean value) {
		return new ServerSettings(getWorldConfiguration(), getSpeedSetting(), getNetworkTypes(), getVictoryLimit(), value);
	}
}
