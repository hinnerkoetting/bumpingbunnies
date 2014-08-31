package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import de.oetting.bumpingbunnies.core.game.steps.BunnyKillChecker;
import de.oetting.bumpingbunnies.core.game.steps.OutsideOfPlayZoneChecker;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.CollisionDetection;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.NetworkSendControl;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerIsDead.PlayerIsDead;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.playerScoreUpdated.PlayerScoreMessage;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.spawnPoint.SpawnPointMessage;
import de.oetting.bumpingbunnies.usecases.game.communication.objects.MessageId;
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
	private final NetworkSendControl sendControl;
	private final PlayerReviver reviver;

	public HostBunnyKillChecker(CollisionDetection collisionDetection, World world,
			SpawnPointGenerator spawnPointGenerator, PlayerReviver reviver, NetworkSendControl sendControl) {
		super();
		this.collisionDetection = collisionDetection;
		this.spawnPointGenerator = spawnPointGenerator;
		this.reviver = reviver;
		this.world = world;
		this.sendControl = sendControl;
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

		this.sendControl.sendMessage(MessageId.PLAYER_IS_DEAD_MESSAGE, killedMessage);
		this.sendControl.sendMessage(MessageId.SPAWN_POINT, new SpawnPointMessage(spawnPoint, playerKilled.id()));
	}

	private void increaseScore(Player playerTop) {
		playerTop.increaseScore(1);
		PlayerScoreMessage newScoreMessage = new PlayerScoreMessage(playerTop.id(), playerTop.getScore());
		this.sendControl.sendMessage(MessageId.PLAYER_SCORE_UPDATE, newScoreMessage);
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
