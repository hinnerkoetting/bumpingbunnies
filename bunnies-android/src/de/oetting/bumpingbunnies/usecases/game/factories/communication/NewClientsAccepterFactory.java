package de.oetting.bumpingbunnies.usecases.game.factories.communication;

import de.oetting.bumpingbunnies.android.game.GameActivity;
import de.oetting.bumpingbunnies.core.networking.DummyNewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.NewClientsAccepter;
import de.oetting.bumpingbunnies.core.networking.init.ConnectionEstablisher;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.communication.AcceptsClientConnectionsDelegate;
import de.oetting.bumpingbunnies.usecases.game.communication.HostNewClientsAccepter;
import de.oetting.bumpingbunnies.usecases.game.configuration.GameStartParameter;
import de.oetting.bumpingbunnies.usecases.networkRoom.services.NetworkBroadcaster;

public class NewClientsAccepterFactory {

	public static NewClientsAccepter create(GameStartParameter parameter, GameActivity origin, World world) {
		final NewClientsAccepter accepter = createClientAccepter(parameter, origin, world);
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

	private static NewClientsAccepter createClientAccepter(GameStartParameter parameter, GameActivity origin, World world) {
		if (parameter.getConfiguration().isHost()) {
			AcceptsClientConnectionsDelegate delegate = new AcceptsClientConnectionsDelegate();
			NetworkBroadcaster bcs = new NetworkBroadcaster(origin);
			ConnectionEstablisher rc = ConnectionEstablisherFactory.create(origin, delegate, parameter.getConfiguration().getGeneralSettings());
			NewClientsAccepter accepter = new HostNewClientsAccepter(bcs, rc, world, parameter.getConfiguration().getGeneralSettings());
			delegate.setAccepter(accepter);
			return accepter;
		} else {
			return new DummyNewClientsAccepter();
		}
	}
}
