package de.oetting.bumpingbunnies.core.network;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.main.OneLoopStep;
import de.oetting.bumpingbunnies.core.game.main.ThreadLoop;
import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.networking.communication.messageInterface.NetworkSender;
import de.oetting.bumpingbunnies.core.networking.sender.PlayerStateSender;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class NetworkSendThread implements Runnable, PlayerJoinListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(NetworkSendThread.class);
	private final ThreadLoop loop;
	private final NetworkSendStep sendStep;
	private boolean canceled;

	public NetworkSendThread(ThreadLoop loop, NetworkSendStep sendStep) {
		this.loop = loop;
		this.sendStep = sendStep;
	}

	@Override
	public void run() {
		while (!canceled)
			loop.nextStep();
	}

	public void cancel() {
		canceled = true;
	}

	@Override
	public void newPlayerJoined(Player p) {
		if (!p.getOpponent().isLocalPlayer())
			sendStep.newPlayerJoined(p);
	}

	@Override
	public void playerLeftTheGame(Player p) {
		sendStep.playerLeftTheGame(p);
	}

	public static class NetworkSendStep implements OneLoopStep, PlayerJoinListener {

		private final List<PlayerStateSender> networkSender;
		private final World world;
		private final RemoteConnectionFactory sendFactory;

		public NetworkSendStep(World world, RemoteConnectionFactory sendFactory) {
			this.networkSender = new CopyOnWriteArrayList<PlayerStateSender>();
			this.world = world;
			this.sendFactory = sendFactory;
		}

		@Override
		public void nextStep(long delta) {
			List<Player> allPlayer = world.getAllPlayer();
			for (Player player : allPlayer) {
				for (PlayerStateSender sender : networkSender) {
					sender.sendState(player);
				}
			}
		}

		@Override
		public void newPlayerJoined(Player p) {
			LOGGER.info("Player joined, creating playerstatesender");
			NetworkSender newNetworkSender = sendFactory.create(p);

			networkSender.add(new PlayerStateSender(newNetworkSender));
		}

		@Override
		public void playerLeftTheGame(Player p) {
			LOGGER.info("Player left. Removing playerstatesender");
			PlayerStateSender stateSender = findPlayerStateSender(p);
			networkSender.remove(stateSender);
		}

		private PlayerStateSender findPlayerStateSender(Player p) {
			for (PlayerStateSender stateSender : networkSender) {
				if (stateSender.belongsToPlayer(p)) {
					return stateSender;
				}
			}
			throw new CouldNotFindStateSenderException();
		}
	}

	public static class CouldNotFindStateSenderException extends RuntimeException {

	}
}
