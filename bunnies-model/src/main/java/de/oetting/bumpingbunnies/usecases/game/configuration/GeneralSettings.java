package de.oetting.bumpingbunnies.usecases.game.configuration;


/**
 * Which which are valid for all players.
 * 
 */
public class GeneralSettings {

	private final WorldConfiguration worldConfiguration;
	private final int speedSetting;
	private final NetworkType networkType;

	public GeneralSettings(WorldConfiguration worldConfiguration, int speedSetting, NetworkType networkType) {
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
