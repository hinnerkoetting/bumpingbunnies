package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.List;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.ThreadedNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsRevived.PlayerIsRevivedSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BunnyDelayedReviver extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(HostBunnyKillChecker.class);
	public static final int KILL_TIME_MILLISECONDS = 1000;
	/**
	 * make sure that player does accidentally not stay dead for too long on client
	 */
	public static final int KILL_TIME_CLIENT_MILLISECONDS = 2500;
	private final Player player;
	private int duration;
	private final List<ThreadedNetworkSender> senderList;

	public BunnyDelayedReviver(Player player, int duration, List<ThreadedNetworkSender> sender) {
		super();
		this.player = player;
		this.duration = duration;
		this.senderList = sender;
	}

	@Override
	public void run() {
		waitShortTime();
		LOGGER.info("reviving player %d", this.player.id());
		this.player.setDead(false);
		notifyClients();
	}

	private void notifyClients() {
		Integer playerId = this.player.id();
		for (ThreadedNetworkSender sender : this.senderList) {
			new PlayerIsRevivedSender(sender).sendMessage(playerId);
		}
	}

	private void waitShortTime() {
		try {
			Thread.sleep(this.duration);
		} catch (InterruptedException e) {
			LOGGER.error("exception", e);
		}
	}
}
