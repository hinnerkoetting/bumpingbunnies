package de.oetting.bumpingbunnies.model.configuration;

public class RemoteSettings {

	// tcp and udp port
	private final int remotePort;
	private final String remotePlayerName;

	public RemoteSettings(int remotePort, String remotePlayerName) {
		this.remotePort = remotePort;
		this.remotePlayerName = remotePlayerName;
	}

	public int getRemotePort() {
		return remotePort;
	}

	public String getRemotePlayerName() {
		return remotePlayerName;
	}

	@Override
	public String toString() {
		return "RemoteSettings [remotePort=" + remotePort + ", remotePlayerName=" + remotePlayerName + "]";
	}

}
