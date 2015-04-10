package de.oetting.bumpingbunnies.core.network;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.main.OneLoopStep;
import de.oetting.bumpingbunnies.core.game.main.ThreadLoop;
import de.oetting.bumpingbunnies.core.network.sockets.NewSocketListener;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.PlayerStateSender;
import de.oetting.bumpingbunnies.core.threads.BunniesThread;
import de.oetting.bumpingbunnies.core.threads.ThreadErrorCallback;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class NetworkPlayerStateSenderThread extends BunniesThread implements NewSocketListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkPlayerStateSenderThread.class);
	private final ThreadLoop loop;
	private final NetworkPlayerStateSenderStep sendStep;
	private boolean canceled;

	public NetworkPlayerStateSenderThread(ThreadLoop loop, NetworkPlayerStateSenderStep sendStep, ThreadErrorCallback errorCallback) {
		super("Send player states", errorCallback);
		this.loop = loop;
		this.sendStep = sendStep;
	}

	@Override
	protected void doRun() throws Exception {
		while (!canceled)
			loop.nextStep();
	}

	public void cancel() {
		canceled = true;
	}

	@Override
	public void newEvent(MySocket p) {
		sendStep.newEvent(p);
	}

	@Override
	public void removeEvent(MySocket p) {
		sendStep.removeEvent(p);
	}

	public static class NetworkPlayerStateSenderStep implements OneLoopStep, NewSocketListener {

		private final List<PlayerStateSender> networkSender;
		private final World world;
		private final RemoteConnectionFactory sendFactory;

		public NetworkPlayerStateSenderStep(World world, RemoteConnectionFactory sendFactory) {
			this.networkSender = new CopyOnWriteArrayList<PlayerStateSender>();
			this.world = world;
			this.sendFactory = sendFactory;
		}

		@Override
		public void nextStep(long delta) {
			List<Bunny> allPlayer = world.getAllPlayer();
			for (Bunny player : allPlayer) {
				for (PlayerStateSender sender : networkSender) {
					sender.sendState(player);
				}
			}
		}

		@Override
		public void newEvent(MySocket socket) {
			LOGGER.info(" creating playerstatesender");
			NetworkSender newNetworkSender = sendFactory.createFastSender(socket);
			newNetworkSender.start();
			networkSender.add(new PlayerStateSender(newNetworkSender));
		}

		@Override
		public void removeEvent(MySocket socket) {
			LOGGER.info("Player left. Removing playerstatesender");
			PlayerStateSender stateSender = findPlayerStateSender(socket);
			stateSender.cancel();
			networkSender.remove(stateSender);
		}

		private PlayerStateSender findPlayerStateSender(MySocket socket) {
			for (PlayerStateSender stateSender : networkSender) {
				if (stateSender.belongsToSocket(socket)) {
					return stateSender;
				}
			}
			throw new CouldNotFindStateSenderException();
		}
	}

	public static class CouldNotFindStateSenderException extends RuntimeException {

	}

}
