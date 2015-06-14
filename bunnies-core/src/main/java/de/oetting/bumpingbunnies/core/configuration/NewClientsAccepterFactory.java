package de.oetting.bumpingbunnies.core.configuration;

import java.util.Arrays;
import java.util.List;

import de.oetting.bumpingbunnies.core.network.DummyNewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.init.ListensForClientConnections;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.server.AcceptsClientConnectionsDelegate;
import de.oetting.bumpingbunnies.core.networking.server.HostNewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.server.MakesGameVisible;
import de.oetting.bumpingbunnies.core.networking.sockets.SocketFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.model.configuration.NetworkType;
import de.oetting.bumpingbunnies.model.game.world.World;

public class NewClientsAccepterFactory {

	public static NewClientsAccepter create(GameStartParameter parameter, World world,
			PlayerDisconnectedCallback callback, ThreadErrorCallback errorCallback,
			List<MakesGameVisibleFactory> broadcasterFactories, List<SocketFactory> socketFactories) {
		return createClientAccepter(parameter, world, callback, errorCallback, broadcasterFactories, socketFactories);
	}

	private static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world,
			PlayerDisconnectedCallback callback, ThreadErrorCallback errorCallback,
			List<MakesGameVisibleFactory> broadcasterFactories, List<SocketFactory> socketFactories) {
		if (parameter.getConfiguration().isHost()) {
			AcceptsClientConnectionsDelegate delegate = new AcceptsClientConnectionsDelegate();

			MakesGameVisible bcs = createBroadcaster(parameter, errorCallback, broadcasterFactories);
			ListensForClientConnections clientAcceptor = createClientAccepter(parameter, delegate, errorCallback,
					socketFactories);
			NewClientsAccepter accepter = new HostNewClientsAccepter(Arrays.asList(bcs), Arrays.asList(clientAcceptor),
					world, parameter.getConfiguration(), callback, errorCallback);
			delegate.setAccepter(accepter);
			return accepter;
		} else {
			return new DummyNewClientsAccepter();
		}
	}

	private static ListensForClientConnections createClientAccepter(GameStartParameter parameter,
			AcceptsClientConnectionsDelegate delegate, ThreadErrorCallback errorCallback,
			List<SocketFactory> socketfactories) {
		SocketFactory socketFactory = findSocketFactory(parameter, socketfactories);
		return new ListensForClientConnections(socketFactory, delegate, errorCallback);
	}

	private static SocketFactory findSocketFactory(GameStartParameter parameter, List<SocketFactory> socketfactories) {
		for (SocketFactory socketFactory : socketfactories) {
			if (parameter.getConfiguration().getGeneralSettings().getNetworkType()
					.equals(socketFactory.forNetworkType())) {
				return socketFactory;
			}
		}
		throw new IllegalStateException("Cannot find a socketfactory. Expected type is : "
				+ parameter.getConfiguration().getGeneralSettings().getNetworkType() + ". Existing factories are: "
				+ socketfactories);

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
