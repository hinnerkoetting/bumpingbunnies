package de.oetting.bumpingbunnies.core.configuration;

import de.oetting.bumpingbunnies.core.network.DummyNewClientsAccepter;
import de.oetting.bumpingbunnies.core.network.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.server.AcceptsClientConnectionsDelegate;
import de.oetting.bumpingbunnies.core.networking.server.HostNewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.server.NetworkBroadcaster;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.configuration.GameStartParameter;

public class NewClientsAccepterFactory {

	public static NewClientsAccepter create(GameStartParameter parameter, World world, ConnectionEstablisherFactory factory,
			PlayerDisconnectedCallback callback, ThreadErrorCallback errorCallback) {
		return createClientAccepter(parameter, world, factory, callback, errorCallback);
	}

	private static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world, ConnectionEstablisherFactory factory,
			PlayerDisconnectedCallback callback, ThreadErrorCallback errorCallback) {
		if (parameter.getConfiguration().isHost()) {
			AcceptsClientConnectionsDelegate delegate = new AcceptsClientConnectionsDelegate();
			NetworkBroadcaster bcs = new NetworkBroadcaster(errorCallback);
			ConnectionEstablisher rc = factory.create(delegate, parameter.getConfiguration().getGeneralSettings(), null);
			NewClientsAccepter accepter = new HostNewClientsAccepter(bcs, rc, world, parameter.getConfiguration().getGeneralSettings(), callback, errorCallback);
			delegate.setAccepter(accepter);
			return accepter;
		} else {
			return new DummyNewClientsAccepter();
		}
	}
}
