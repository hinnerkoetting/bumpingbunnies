package de.oetting.bumpingbunnies.core.game.main;

import de.oetting.bumpingbunnies.core.configuration.ConnectionEstablisherFactory;
import de.oetting.bumpingbunnies.core.configuration.NewClientsAccepterFactory;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;

public class CommonGameMainFactory {

	public static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world, ConnectionEstablisherFactory establisherFactory,
			PlayerDisconnectedCallback callback, ThreadErrorCallback errorCallback) {
		return NewClientsAccepterFactory.create(parameter, world, establisherFactory, callback, errorCallback);
	}
}
