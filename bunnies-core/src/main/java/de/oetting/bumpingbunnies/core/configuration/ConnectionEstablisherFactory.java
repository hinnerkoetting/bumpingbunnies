package de.oetting.bumpingbunnies.core.configuration;

import de.oetting.bumpingbunnies.core.network.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.model.configuration.GeneralSettings;

public interface ConnectionEstablisherFactory {

	ConnectionEstablisher create(AcceptsClientConnections newClientsAccepter, GeneralSettings settings);

}
