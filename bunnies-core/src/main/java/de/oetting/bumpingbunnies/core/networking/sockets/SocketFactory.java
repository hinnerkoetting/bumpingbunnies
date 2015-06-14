package de.oetting.bumpingbunnies.core.networking.sockets;

import de.oetting.bumpingbunnies.model.configuration.NetworkType;


public interface SocketFactory {

	ServerSocket create();

	NetworkType forNetworkType();
}
