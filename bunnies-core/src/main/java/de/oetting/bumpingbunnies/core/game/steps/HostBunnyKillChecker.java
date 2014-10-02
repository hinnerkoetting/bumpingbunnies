package de.oetting.bumpingbunnies.core.game.steps;

import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.spawnpoint.ResetToScorePoint;
import de.oetting.bumpingbunnies.core.game.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.core.network.MessageSender;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDead;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreMessage;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointMessage;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.network.MessageId;

/**
 * Host logic what should be done when a bunny jumps on another bunny.
 * 
 */
public class HostBunnyKillChecker implements BunnyKillChecker {

	private final CollisionDetection collisionDetection;
	private final World world;
	private final SpawnPointGenerator spawnPointGenerator;
	private final MessageSender messageSender;
	private final PlayerReviver reviver;

	public HostBunnyKillChecker(CollisionDetection collisionDetection, World world, SpawnPointGenerator spawnPointGenerator, PlayerReviver reviver,
			MessageSender messageSender) {
		super();
		this.collisionDetection = collisionDetection;
		this.spawnPointGenerator = spawnPointGenerator;
		this.reviver = reviver;
		this.world = world;
		this.messageSender = messageSender;
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

		this.messageSender.sendMessage(MessageId.PLAYER_IS_DEAD_MESSAGE, killedMessage);
		this.messageSender.sendMessage(MessageId.SPAWN_POINT, new SpawnPointMessage(spawnPoint, playerKilled.id()));
	}

	private void increaseScore(Player playerTop) {
		playerTop.increaseScore(1);
		PlayerScoreMessage newScoreMessage = new PlayerScoreMessage(playerTop.id(), playerTop.getScore());
		this.messageSender.sendMessage(MessageId.PLAYER_SCORE_UPDATE, newScoreMessage);
	}

	private void revivePlayerDelayed(final Player player) {
		this.reviver.revivePlayerLater(player);
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
