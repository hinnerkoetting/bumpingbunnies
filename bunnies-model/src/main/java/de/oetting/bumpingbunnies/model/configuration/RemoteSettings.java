package de.oetting.bumpingbunnies.model.configuration;

public class RemoteSettings {

	private final String remotePlayerName;

	public RemoteSettings(String remotePlayerName) {
		this.remotePlayerName = remotePlayerName;
	}

	public String getRemotePlayerName() {
		return remotePlayerName;
	}

	@Override
	public String toString() {
		return "RemoteSettings [remotePlayerName=" + remotePlayerName + "]";
	}

}
