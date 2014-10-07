package de.oetting.bumpingbunnies.core.network.factory;

import de.oetting.bumpingbunnies.core.game.main.ThreadLoop;
import de.oetting.bumpingbunnies.core.network.NetworkSendThread;
import de.oetting.bumpingbunnies.core.network.NetworkSendThread.NetworkSendStep;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.world.World;

public class NetworksendThreadFactory {

	public static NetworkSendThread create(World world, RemoteConnectionFactory senderfactory, GameStopper gameStopper) {
		NetworkSendStep step = new NetworkSendStep(world, senderfactory, gameStopper);
		ThreadLoop loop = new ThreadLoop(step, 20);
		NetworkSendThread networkSender = new NetworkSendThread(loop, step);
		Thread thread = new Thread(networkSender);
		thread.start();
		return networkSender;
	}
}
