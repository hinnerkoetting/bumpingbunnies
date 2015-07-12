package de.oetting.bumpingbunnies.core.configuration;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.main.GameMain;
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
			List<MakesGameVisibleFactory> broadcasterFactories, List<SocketFactory> socketFactories, GameMain gameMain) {
		return createClientAccepter(parameter, world, callback, errorCallback, broadcasterFactories, socketFactories, gameMain);
	}

	private static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world,
			PlayerDisconnectedCallback callback, ThreadErrorCallback errorCallback,
			List<MakesGameVisibleFactory> broadcasterFactories, List<SocketFactory> socketFactories, GameMain gameMain) {
		if (parameter.getConfiguration().isHost()) {
			AcceptsClientConnectionsDelegate delegate = new AcceptsClientConnectionsDelegate();

			List<MakesGameVisible> broadcaster = createBroadcaster(parameter, errorCallback, broadcasterFactories);
			List<ListensForClientConnections> clientAcceptor = createClientAccepter(parameter, delegate, errorCallback,
					socketFactories);
			NewClientsAccepter accepter = new HostNewClientsAccepter(broadcaster, clientAcceptor,
					gameMain, parameter.getConfiguration(), callback, errorCallback);
			delegate.setAccepter(accepter);
			return accepter;
		} else {
			return new DummyNewClientsAccepter();
		}
	}

	private static List<ListensForClientConnections> createClientAccepter(GameStartParameter parameter,
			AcceptsClientConnectionsDelegate delegate, ThreadErrorCallback errorCallback,
			List<SocketFactory> allSocketfactories) {
		List<SocketFactory> findUsedSocketFactories = findUsedSocketFactories(parameter, allSocketfactories);
		List<ListensForClientConnections> result = new ArrayList<ListensForClientConnections>(allSocketfactories.size());
		for (SocketFactory socketFactory : findUsedSocketFactories) {
			result.add(new ListensForClientConnections(socketFactory, delegate, errorCallback));
		}
		return result;
	}

	private static List<SocketFactory> findUsedSocketFactories(GameStartParameter parameter,
			List<SocketFactory> socketfactories) {
		List<SocketFactory> factories = new ArrayList<SocketFactory>();
		for (NetworkType type : parameter.getConfiguration().getGeneralSettings().getNetworkTypes()) {
			factories.add(findSocketFactory(type, socketfactories));
		}
		return factories;

	}

	private static SocketFactory findSocketFactory(NetworkType type, List<SocketFactory> socketfactories) {
		for (SocketFactory socketFactory : socketfactories) {
			if (type.equals(socketFactory.forNetworkType())) {
				return socketFactory;
			}
		}
		throw new IllegalStateException("Cannot find a socketfactory. Expected type is : " + type
				+ ". Existing factories are: " + socketfactories);
	}

	private static List<MakesGameVisible> createBroadcaster(GameStartParameter parameter, ThreadErrorCallback errorCallback,
			List<MakesGameVisibleFactory> broadcasterFactories) {
		List<MakesGameVisible> broadcaster = new ArrayList<MakesGameVisible>();
		for (NetworkType networkType: parameter.getConfiguration().getGeneralSettings().getNetworkTypes()) {
			broadcaster.add( findBroadcaster(errorCallback, broadcasterFactories, networkType));
		}
		return broadcaster;
	}

	private static MakesGameVisible findBroadcaster(ThreadErrorCallback errorCallback,
			List<MakesGameVisibleFactory> broadcasterFactories, NetworkType networkType) {
		for (MakesGameVisibleFactory factory : broadcasterFactories)
			if (factory.forNetworkType().equals(networkType))
				return factory.create(errorCallback);
		throw new IllegalStateException(
				"Trying to create a broadcaster for the network type. But no broadcaster could not be found. Requested type is :"
						+ networkType + ". Existing factories are " + broadcasterFactories);
	}
}
