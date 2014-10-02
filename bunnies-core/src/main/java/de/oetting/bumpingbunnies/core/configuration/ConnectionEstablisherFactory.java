package de.oetting.bumpingbunnies.core.configuration;

import de.oetting.bumpingbunnies.core.networking.AcceptsClientConnections;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.usecases.game.configuration.GeneralSettings;

public interface ConnectionEstablisherFactory {

	ConnectionEstablisher create(AcceptsClientConnections newClientsAccepter, GeneralSettings settings);

}
