package de.oetting.bumpingbunnies.model.configuration;

/**
 * Settings which are valid for all players.
 * 
 */
public class ServerSettings {

	private final WorldConfiguration worldConfiguration;
	private final int speedSetting;
	private final NetworkType networkType;

	public ServerSettings(WorldConfiguration worldConfiguration, int speedSetting, NetworkType networkType) {
		super();
		this.worldConfiguration = worldConfiguration;
		this.speedSetting = speedSetting;
		this.networkType = networkType;
	}

	public int getSpeedSetting() {
		return this.speedSetting;
	}

	public NetworkType getNetworkType() {
		return this.networkType;
	}

	public WorldConfiguration getWorldConfiguration() {
		return this.worldConfiguration;
	}

}
