package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class BunnyDelayedReviver extends Thread {

	private static final Logger LOGGER = LoggerFactory.getLogger(HostBunnyKillChecker.class);
	public static final int KILL_TIME_MILLISECONDS = 1000;
	/**
	 * make sure that player does accidentallynot stay dead for too long on
	 * client
	 */
	public static final int KILL_TIME_CLIENT_MILLISECONDS = 2500;
	private final Player player;
	private int duration;

	public BunnyDelayedReviver(Player player, int duration) {
		super();
		this.player = player;
		this.duration = duration;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(this.duration);
		} catch (InterruptedException e) {
			LOGGER.error("exception", e);
		}
		this.player.setDead(false);
	}
}
