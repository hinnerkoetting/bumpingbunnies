package de.oetting.bumpingbunnies.core.configuration;

import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.networking.init.AcceptsClients;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.ServerSettings;

public interface ConnectionEstablisherFactory {

	AcceptsClients create(AcceptsClientConnections newClientsAccepter, ServerSettings settings, ThreadErrorCallback errorCallback);

}
