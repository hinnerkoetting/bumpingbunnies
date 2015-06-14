package de.oetting.bumpingbunnies.core.networking.init;

import de.oetting.bumpingbunnies.model.configuration.NetworkType;


public interface DeviceDiscovery {

	void closeConnections();

	void searchServer();
	
	NetworkType getNetworkType();

}
