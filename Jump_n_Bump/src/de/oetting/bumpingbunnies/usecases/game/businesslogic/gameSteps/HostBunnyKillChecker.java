package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.SpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead.PlayerIsDead;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead.PlayerIsDeadSender;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated.PlayerScoreMessage;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated.PlayerScoreSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;

/**
 * Host logic what should be done when a bunny jumps on another bunny.
 * 
 */
public class HostBunnyKillChecker implements BunnyKillChecker {

	private final CollisionDetection collisionDetection;
	private final List<Player> allPlayers;
	private final SpawnPointGenerator spawnPointGenerator;
	private List<RemoteSender> sendThreads;

	public HostBunnyKillChecker(List<RemoteSender> sendThreads, CollisionDetection collisionDetection, List<Player> allPlayers,
			SpawnPointGenerator spawnPointGenerator) {
		super();
		this.sendThreads = sendThreads;
		this.collisionDetection = collisionDetection;
		this.spawnPointGenerator = spawnPointGenerator;
		this.allPlayers = Collections.unmodifiableList(allPlayers);
	}

	@Override
	public void checkForJumpedPlayers() {
		for (Player player : this.allPlayers) {
			Player playerUnder = this.collisionDetection.findPlayerThisPlayerIsStandingOn(player);
			if (playerUnder != null) {
				handleJumpedPlayer(playerUnder, player);
			}
		}
	}

	private void handleJumpedPlayer(Player playerUnder, Player playerTop) {
		increaseScore(playerTop);
		killPlayer(playerUnder);
		resetCoordinate(playerUnder);
		revivePlayerDelayed(playerUnder);
	}

	private void resetCoordinate(Player playerUnder) {
		ResetToScorePoint.resetPlayerToSpawnPoint(this.spawnPointGenerator, playerUnder);
	}

	private void killPlayer(Player playerKilled) {
		playerKilled.setDead(true);
		SpawnPoint spawnPoint = this.spawnPointGenerator.nextSpawnPoint();
		PlayerIsDead killedMessage = new PlayerIsDead(playerKilled.id(), spawnPoint);

		for (RemoteSender sender : this.sendThreads) {
			new PlayerIsDeadSender(sender).sendMessage(killedMessage);
		}
	}

	private void increaseScore(Player playerTop) {
		playerTop.increaseScore(1);
		PlayerScoreMessage newScoreMessage = new PlayerScoreMessage(playerTop.id(), playerTop.getScore());
		for (RemoteSender sender : this.sendThreads) {
			new PlayerScoreSender(sender).sendMessage(newScoreMessage);
		}
	}

	private void revivePlayerDelayed(final Player player) {
		new BunnyDelayedReviver(player, BunnyDelayedReviver.KILL_TIME_CLIENT_MILLISECONDS, this.sendThreads).start();
	}
}
