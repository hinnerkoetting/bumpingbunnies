package de.oetting.bumpingbunnies.core.game.steps;

import de.oetting.bumpingbunnies.core.game.movement.CollisionDetection;
import de.oetting.bumpingbunnies.core.game.spawnpoint.ResetToScorePoint;
import de.oetting.bumpingbunnies.core.game.spawnpoint.SpawnPointGenerator;
import de.oetting.bumpingbunnies.core.network.MessageSender;
import de.oetting.bumpingbunnies.core.network.MySocket;
import de.oetting.bumpingbunnies.core.network.sockets.SocketStorage;
import de.oetting.bumpingbunnies.core.networking.messaging.playerIsDead.PlayerIsDeadMessage;
import de.oetting.bumpingbunnies.core.networking.messaging.playerScoreUpdated.PlayerScoreMessage;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointMessage;
import de.oetting.bumpingbunnies.core.networking.messaging.spawnPoint.SpawnPointSender;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
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
	private final PlayerDisconnectedCallback disconnectCallback;
	private final MusicPlayer musicPlayer;

	public HostBunnyKillChecker(CollisionDetection collisionDetection, World world, SpawnPointGenerator spawnPointGenerator, PlayerReviver reviver,
			MessageSender messageSender, PlayerDisconnectedCallback disconnectCallback, MusicPlayer musicPlayer) {
		this.collisionDetection = collisionDetection;
		this.spawnPointGenerator = spawnPointGenerator;
		this.reviver = reviver;
		this.world = world;
		this.messageSender = messageSender;
		this.disconnectCallback = disconnectCallback;
		this.musicPlayer = musicPlayer;
	}

	@Override
	public void checkForJumpedPlayers() {
		for (Bunny player : this.world.getAllConnectedBunnies()) {
			Bunny playerUnder = this.collisionDetection.findPlayerThisPlayerIsStandingOn(player);
			if (playerUnder != null && !playerUnder.isDead()) {
				handleJumpedPlayer(playerUnder, player);
			}
		}
	}

	private void handleJumpedPlayer(Bunny playerUnder, Bunny playerTop) {
		increaseScore(playerTop);
		killPlayer(playerUnder);
		revivePlayerDelayed(playerUnder);
		playSound();
	}

	private void playSound() {
		musicPlayer.start();
	}

	private void killPlayer(Bunny playerKilled) {
		playerKilled.setDead(true);
		PlayerIsDeadMessage killedMessage = new PlayerIsDeadMessage(playerKilled.id());

		this.messageSender.sendMessage(MessageId.PLAYER_IS_DEAD_MESSAGE, killedMessage);
		assignSpawnpoint(playerKilled);
	}

	private void increaseScore(Bunny playerTop) {
		playerTop.increaseScore(1);
		PlayerScoreMessage newScoreMessage = new PlayerScoreMessage(playerTop.id(), playerTop.getScore());
		this.messageSender.sendMessage(MessageId.PLAYER_SCORE_UPDATE, newScoreMessage);
	}

	private void revivePlayerDelayed(final Bunny player) {
		this.reviver.revivePlayerLater(player);
	}

	@Override
	public void checkForPlayerOutsideOfGameZone() {
		for (Bunny p : this.world.getAllConnectedBunnies()) {
			if (OutsideOfPlayZoneChecker.outsideOfGameZone(p)) {
				handlePlayerOutOfPlayZone(p);
			}
		}
	}

	private void handlePlayerOutOfPlayZone(Bunny killedPlayer) {
		killedPlayer.increaseScore(-1);
		killPlayer(killedPlayer);
		revivePlayerDelayed(killedPlayer);
	}

	@Override
	public void newEvent(Bunny p) {
		sendSpawnPointOnlyToThisPlayer(p);
		revivePlayerDelayed(p);
	}

	private void sendSpawnPointOnlyToThisPlayer(Bunny player) {
		SpawnPoint spawnPoint = this.spawnPointGenerator.nextSpawnPoint();
		if (!player.getOpponent().isLocalPlayer()) {
			MySocket playerSocket = SocketStorage.getSingleton().findSocket(player.getOpponent());
			SpawnPointMessage message = new SpawnPointMessage(spawnPoint, player.id());
			new SpawnPointSender(SimpleNetworkSenderFactory.createNetworkSender(playerSocket, disconnectCallback)).sendMessage(message);
		}
		ResetToScorePoint.resetPlayerToSpawnPoint(spawnPoint, player);
	}

	private void assignSpawnpoint(Bunny player) {
		SpawnPoint spawnPoint = this.spawnPointGenerator.nextSpawnPoint();
		this.messageSender.sendMessage(MessageId.SPAWN_POINT, new SpawnPointMessage(spawnPoint, player.id()));
		ResetToScorePoint.resetPlayerToSpawnPoint(spawnPoint, player);
	}

	@Override
	public void removeEvent(Bunny p) {
	}
}
