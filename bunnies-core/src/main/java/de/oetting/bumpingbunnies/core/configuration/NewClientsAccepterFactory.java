package de.oetting.bumpingbunnies.core.configuration;

import java.util.List;

import de.oetting.bumpingbunnies.core.network.DummyNewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.init.ClientAccepter;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.server.AcceptsClientConnectionsDelegate;
import de.oetting.bumpingbunnies.core.networking.server.HostNewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.server.MakesGameVisible;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.game.world.World;

public class NewClientsAccepterFactory {

	public static NewClientsAccepter create(GameStartParameter parameter, World world,
			ConnectionEstablisherFactory factory, PlayerDisconnectedCallback callback,
			ThreadErrorCallback errorCallback, List<MakesGameVisibleFactory> broadcasterFactories) {
		return createClientAccepter(parameter, world, factory, callback, errorCallback, broadcasterFactories);
	}

	private static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world,
			ConnectionEstablisherFactory factory, PlayerDisconnectedCallback callback,
			ThreadErrorCallback errorCallback, List<MakesGameVisibleFactory> broadcasterFactories) {
		if (parameter.getConfiguration().isHost()) {
			AcceptsClientConnectionsDelegate delegate = new AcceptsClientConnectionsDelegate();

			MakesGameVisible bcs = createBroadcaster(parameter, errorCallback, broadcasterFactories);
			ClientAccepter clientAcceptor = factory.create(delegate, parameter.getConfiguration().getGeneralSettings(),
					null);
			NewClientsAccepter accepter = new HostNewClientsAccepter(bcs, clientAcceptor, world,
					parameter.getConfiguration(), callback, errorCallback);
			delegate.setAccepter(accepter);
			return accepter;
		} else {
			return new DummyNewClientsAccepter();
		}
	}

	private static MakesGameVisible createBroadcaster(GameStartParameter parameter, ThreadErrorCallback errorCallback,
			List<MakesGameVisibleFactory> broadcasterFactories) {
		NetworkType networkType = parameter.getConfiguration().getGeneralSettings().getNetworkType();
		for (MakesGameVisibleFactory factory : broadcasterFactories)
			if (factory.forNetworkType().equals(networkType))
				return factory.create(errorCallback);
		throw new IllegalStateException(
				"Trying to create a broadcaster for the network type. But no broadcaster could not be found. Requested type is :"
						+ networkType + ". Existing factories are " + broadcasterFactories);
	}
}
