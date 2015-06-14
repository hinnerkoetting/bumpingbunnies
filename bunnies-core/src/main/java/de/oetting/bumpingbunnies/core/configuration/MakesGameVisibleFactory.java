package de.oetting.bumpingbunnies.core.configuration;

import de.oetting.bumpingbunnies.core.networking.server.MakesGameVisible;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;

public interface MakesGameVisibleFactory {

	NetworkType forNetworkType();
	
	MakesGameVisible create(ThreadErrorCallback callback);
}
