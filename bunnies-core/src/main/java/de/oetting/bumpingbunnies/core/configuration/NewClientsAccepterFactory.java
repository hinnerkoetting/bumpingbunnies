package de.oetting.bumpingbunnies.core.configuration;

import de.oetting.bumpingbunnies.core.networking.DummyNewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.core.networking.server.AcceptsClientConnectionsDelegate;
import de.oetting.bumpingbunnies.core.networking.server.HostNewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.server.NetworkBroadcaster;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.configuration.GameStartParameter;

public class NewClientsAccepterFactory {

	public static NewClientsAccepter create(GameStartParameter parameter, World world, ConnectionEstablisherFactory factory) {
		final NewClientsAccepter accepter = createClientAccepter(parameter, world, factory);
		startAsynchronous(accepter);
		return accepter;
	}

	private static void startAsynchronous(final NewClientsAccepter accepter) {
		new Thread(new Runnable() {

			@Override
			public void run() {
				accepter.start();
			}
		}).start();
	}

	private static NewClientsAccepter createClientAccepter(GameStartParameter parameter, World world, ConnectionEstablisherFactory factory) {
		if (parameter.getConfiguration().isHost()) {
			AcceptsClientConnectionsDelegate delegate = new AcceptsClientConnectionsDelegate();
			NetworkBroadcaster bcs = new NetworkBroadcaster();
			ConnectionEstablisher rc = factory.create(delegate, parameter.getConfiguration().getGeneralSettings());
			NewClientsAccepter accepter = new HostNewClientsAccepter(bcs, rc, world, parameter.getConfiguration().getGeneralSettings());
			delegate.setAccepter(accepter);
			return accepter;
		} else {
			return new DummyNewClientsAccepter();
		}
	}
}