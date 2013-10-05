package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead.PlayerIsDead;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead.PlayerIsDeadSender;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated.PlayerScoreMessage;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated.PlayerScoreSender;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint.SpawnPointMessage;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint.SpawnPointSender;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.SpawnPoint;
import de.oetting.bumpingbunnies.usecases.game.model.World;

/**
 * Host logic what should be done when a bunny jumps on another bunny.
 * 
 */
public class HostBunnyKillChecker implements BunnyKillChecker {

	private final CollisionDetection collisionDetection;
	private final World world;
	private final SpawnPointGenerator spawnPointGenerator;
	private final List<? extends RemoteSender> sendThreads;
	private final PlayerReviver reviver;

	public HostBunnyKillChecker(List<? extends RemoteSender> sendThreads, CollisionDetection collisionDetection, World world,
			SpawnPointGenerator spawnPointGenerator, PlayerReviver reviver) {
		super();
		this.sendThreads = sendThreads;
		this.collisionDetection = collisionDetection;
		this.spawnPointGenerator = spawnPointGenerator;
		this.reviver = reviver;
		this.world = world;
	}

	@Override
	public void checkForJumpedPlayers() {
		for (Player player : this.world.getAllPlayer()) {
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
		PlayerIsDead killedMessage = new PlayerIsDead(playerKilled.id());

		for (RemoteSender sender : this.sendThreads) {
			new PlayerIsDeadSender(sender).sendMessage(killedMessage);
			new SpawnPointSender(sender).sendMessage(new SpawnPointMessage(spawnPoint, playerKilled.id()));
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
		this.reviver.revivePlayerLater(player);
		// new BunnyDelayedReviver(player,
		// BunnyDelayedReviver.KILL_TIME_MILLISECONDS,
		// this.sendThreads).start();
	}

	@Override
	public void checkForPlayerOutsideOfGameZone() {
		for (Player p : this.world.getAllPlayer()) {
			if (OutsideOfPlayZoneChecker.outsideOfGameZone(p)) {
				handlePlayerOutOfPlayZone(p);
			}
		}
	}

	private void handlePlayerOutOfPlayZone(Player killedPlayer) {
		killedPlayer.increaseScore(-1);
		killPlayer(killedPlayer);
		resetCoordinate(killedPlayer);
		revivePlayerDelayed(killedPlayer);
	}

	@Override
	public void newPlayerJoined(Player p) {
		killPlayer(p);
		revivePlayerDelayed(p);
		resetCoordinate(p);
	}

	@Override
	public void playerLeftTheGame(Player p) {
	}
}
