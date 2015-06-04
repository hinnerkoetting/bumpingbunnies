package de.oetting.bumpingbunnies.core.network.factory;

import de.oetting.bumpingbunnies.core.game.main.ThreadLoop;
import de.oetting.bumpingbunnies.core.network.NetworkPlayerStateSenderThread;
import de.oetting.bumpingbunnies.core.network.NetworkPlayerStateSenderThread.NetworkPlayerStateSenderStep;
import de.oetting.bumpingbunnies.core.network.RemoteConnectionFactory;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.model.game.world.World;

public class NetworksendThreadFactory {

	public static NetworkPlayerStateSenderThread create(World world, RemoteConnectionFactory senderfactory, ThreadErrorCallback errorCallback) {
		NetworkPlayerStateSenderStep step = new NetworkPlayerStateSenderStep(world, senderfactory);
		ThreadLoop loop = new ThreadLoop(step, 30);
		NetworkPlayerStateSenderThread networkSender = new NetworkPlayerStateSenderThread(loop, step, errorCallback);
		return networkSender;
	}
}
