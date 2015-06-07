package de.oetting.bumpingbunnies.core.game.steps;

import java.util.List;
import java.util.Random;

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
import de.oetting.bumpingbunnies.core.networking.messaging.stop.GameStopper;
import de.oetting.bumpingbunnies.core.networking.receive.PlayerDisconnectedCallback;
import de.oetting.bumpingbunnies.core.networking.sender.SimpleNetworkSenderFactory;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.configuration.Configuration;
import de.oetting.bumpingbunnies.model.game.MusicPlayer;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.SpawnPoint;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.model.network.MessageId;

/**
 * Host logic what should be done when a bunny jumps on another bunny.
 * 
 */
public class HostBunnyKillChecker implements BunnyKillChecker {

	private static final Logger LOGGER = LoggerFactory.getLogger(HostBunnyKillChecker.class);
	private final CollisionDetection collisionDetection;
	private final World world;
	private final SpawnPointGenerator spawnPointGenerator;
	private final MessageSender messageSender;
	private final PlayerReviver reviver;
	private final PlayerDisconnectedCallback disconnectCallback;
	private final MusicPlayer musicPlayer;
	private final GameStopper gameStopper;
	private final Configuration configuration;
	private final ScoreboardSynchronisation scoreSynchronisation;
	private final Random random;

	public HostBunnyKillChecker(CollisionDetection collisionDetection, World world,
			SpawnPointGenerator spawnPointGenerator, PlayerReviver reviver, MessageSender messageSender,
			PlayerDisconnectedCallback disconnectCallback, MusicPlayer musicPlayer, GameStopper gameStopper,
			Configuration configuration, ScoreboardSynchronisation scoreSynchronisation) {
		this.collisionDetection = collisionDetection;
		this.spawnPointGenerator = spawnPointGenerator;
		this.reviver = reviver;
		this.world = world;
		this.messageSender = messageSender;
		this.disconnectCallback = disconnectCallback;
		this.musicPlayer = musicPlayer;
		this.gameStopper = gameStopper;
		this.configuration = configuration;
		this.scoreSynchronisation = scoreSynchronisation;
		this.random = new Random(System.currentTimeMillis());
	}

	@Override
	public void checkForJumpedPlayers() {
		for (Bunny bunny : this.world.getAllConnectedBunnies()) {
			Bunny collidingBunny = collisionDetection.findBunnyThisBunnyIsCollidingWith(bunny);
			if (collidingBunny != null && collidingBunny.minY() < bunny.minY()) {
				if (!bunny.isDead() && !collidingBunny.isDead())
					onBunnyWasJumped(collidingBunny, bunny);
			}
		}
	}

	private void onBunnyWasJumped(Bunny playerUnder, Bunny playerTop) {
		increaseScore(playerTop);
		killPlayer(playerUnder);
		revivePlayerDelayed(playerUnder);
		playSound();
		checkForEndgameCondition();
		scoreSynchronisation.scoreIsChanged();
	}

	void checkForEndgameCondition() {
		boolean endgame = isEndgameConditionFulfilled();
		if (endgame)
			gameStopper.gameStopped();
	}

	private boolean isEndgameConditionFulfilled() {
		int max = getMaxScore();
		int secondMax = getSecondMaxScore(max);
		return getMaxScore() >= configuration.getGeneralSettings().getVictoryLimit() && secondMax <= max - 2;
	}

	private int getMaxScore() {
		int max = Integer.MIN_VALUE;
		List<Bunny> bunnies = world.getAllConnectedBunnies();
		for (Bunny bunny : bunnies) {
			if (bunny.getScore() > max) {
				max = bunny.getScore();
			}
		}
		return max;
	}

	private int getSecondMaxScore(int maxScore) {
		if (world.getAllConnectedBunnies().size() < 2)
			return world.getAllConnectedBunnies().get(0).getScore();
		int countOfNumberWithMaxScore = 0;
		int secondMax = Integer.MIN_VALUE;
		List<Bunny> bunnies = world.getAllConnectedBunnies();
		for (Bunny bunny : bunnies) {
			if (bunny.getScore() > secondMax) {
				if (bunny.getScore() < maxScore)
					secondMax = bunny.getScore();
				else
					countOfNumberWithMaxScore++;
			}
		}
		assert countOfNumberWithMaxScore > 0 : "At least one player must have max score";
		if (countOfNumberWithMaxScore > 1)
			return maxScore;
		return secondMax;
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
		scoreSynchronisation.scoreIsChanged();
	}

	@Override
	public void newEvent(Bunny p) {
		sendSpawnPointOnlyToThisPlayer(p);
		revivePlayerDelayed(p);
	}

	private void sendSpawnPointOnlyToThisPlayer(Bunny player) {
		SpawnPoint spawnPoint = findSpawnpoint();
		if (!player.getOpponent().isLocalPlayer()) {
			MySocket playerSocket = SocketStorage.getSingleton().findSocket(player.getOpponent());
			SpawnPointMessage message = new SpawnPointMessage(spawnPoint, player.id());
			new SpawnPointSender(SimpleNetworkSenderFactory.createNetworkSender(playerSocket, disconnectCallback))
					.sendMessage(message);
		}
		ResetToScorePoint.resetPlayerToSpawnPoint(spawnPoint, player);
	}

	private void assignSpawnpoint(Bunny player) {
		SpawnPoint spawnPoint = findSpawnpoint();
		if (spawnPoint != null) {
			this.messageSender.sendMessage(MessageId.SPAWN_POINT, new SpawnPointMessage(spawnPoint, player.id()));
			ResetToScorePoint.resetPlayerToSpawnPoint(spawnPoint, player);
		} else {
			reviver.revivePlayerLater(player);
		}
	}

	private SpawnPoint findSpawnpoint() {
		int maxCount = 5;
		SpawnPoint spawn;
		do {
			spawn = this.spawnPointGenerator.nextSpawnPoint();
		} while (noPlayerIsClose(spawn) && --maxCount > 0);
		if (maxCount > 0) {
			return spawn;
		}
		return createEmergencySpawn() ;
	}

	private SpawnPoint createEmergencySpawn() {
		LOGGER.info("creating emergency spawn");
		int randomX = (int) (ModelConstants.STANDARD_WORLD_SIZE * 0.1 + random.nextInt((int) (ModelConstants.STANDARD_WORLD_SIZE * 0.8)));
		int randomY =  ModelConstants.STANDARD_WORLD_SIZE + random.nextInt(ModelConstants.STANDARD_WORLD_SIZE / 2);
		return new SpawnPoint(randomX, randomY);
	}

	private boolean noPlayerIsClose(SpawnPoint spawn) {
		for (Bunny bunny : world.getAllConnectedBunnies()) {
			if (Math.abs(bunny.getCenterX() - spawn.getX()) < ModelConstants.STANDARD_WORLD_SIZE / 100
					&& Math.abs(bunny.getCenterY() - spawn.getY()) < ModelConstants.STANDARD_WORLD_SIZE / 100) {
				return false;
			}
		}
		return true;
	}

	@Override
	public void removeEvent(Bunny p) {
	}

	@Override
	public void addJoinListener(JoinObserver main) {
		main.addJoinListener(scoreSynchronisation);
	}
}
