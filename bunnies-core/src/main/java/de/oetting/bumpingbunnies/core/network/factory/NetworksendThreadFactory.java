package de.oetting.bumpingbunnies.core.network.factory;

import de.oetting.bumpingbunnies.core.game.main.ThreadLoop;
import de.oetting.bumpingbunnies.core.network.NetworkPlayerStateSenderThread;
import de.oetting.bumpingbunnies.core.network.NetworkPlayerStateSenderThread.NetworkPlayerStateSenderStep;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.world.World;

public class NetworksendThreadFactory {

	public static NetworkPlayerStateSenderThread create(World world, RemoteConnectionFactory senderfactory) {
		NetworkPlayerStateSenderStep step = new NetworkPlayerStateSenderStep(world, senderfactory);
		ThreadLoop loop = new ThreadLoop(step, 25);
		NetworkPlayerStateSenderThread networkSender = new NetworkPlayerStateSenderThread(loop, step);
		networkSender.start();
		return networkSender;
	}
}
