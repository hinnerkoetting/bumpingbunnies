package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.usecases.game.factories.OtherPlayerInputService;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayer;
import de.oetting.bumpingbunnies.usecases.game.sound.MusicPlayerFactory;

public class AllPlayerConfig {

	private final PlayerMovementController myPlayerMovement;
	private final List<PlayerConfig> notControlledPlayers;

	public AllPlayerConfig(PlayerMovementController tabletControlledPlayer,
			List<PlayerConfig> notControlledPlayers) {
		this.myPlayerMovement = tabletControlledPlayer;
		this.notControlledPlayers = notControlledPlayers;
	}

	public List<PlayerMovementController> getAllPlayerMovementControllers() {
		List<PlayerMovementController> list = new ArrayList<PlayerMovementController>(
				this.notControlledPlayers.size() + 1);
		list.add(this.myPlayerMovement);
		for (PlayerConfig config : this.notControlledPlayers) {
			list.add(config.getMovementController());
		}
		return list;
	}

	public List<PlayerMovementCalculation> getAllPlayerMovementCalculations(CollisionDetection collisionDetection, World world,
			Context context) {
		List<PlayerMovementCalculation> list = new ArrayList<PlayerMovementCalculation>(
				this.notControlledPlayers.size() + 1);
		Player p = this.myPlayerMovement.getPlayer();
		PlayerMovementCalculation playerMovementCalculation = createMovementCalculation(collisionDetection, p, world, context);
		list.add(playerMovementCalculation);
		for (PlayerConfig config : this.notControlledPlayers) {
			list.add(createMovementCalculation(collisionDetection, config.getMovementController().getPlayer(), world, context));
		}
		return list;
	}

	public List<Player> getAllPlayers() {
		List<Player> allPlayers = new ArrayList<Player>();
		allPlayers.add(this.myPlayerMovement.getPlayer());
		for (PlayerConfig config : this.notControlledPlayers) {
			allPlayers.add(config.getMovementController().getPlayer());
		}
		return allPlayers;
	}

	public PlayerMovementCalculation createMovementCalculation(CollisionDetection colDetection, Player p, World world, Context context) {
		MusicPlayer musicPlayer = MusicPlayerFactory.createNormalJump(context);
		PlayerMovementCalculation playerMovementCalculation = new PlayerMovementCalculation(p, new GameObjectInteractor(colDetection,
				world), colDetection, musicPlayer);
		return playerMovementCalculation;
	}

	public List<OtherPlayerInputService> createOtherInputService(
			NetworkToGameDispatcher networkDispatcher) {
		List<MySocket> allSockets = SocketStorage.getSingleton()
				.getAllSockets();
		List<OtherPlayerInputService> resultReceiver = new ArrayList<OtherPlayerInputService>(
				allSockets.size());

		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		for (PlayerConfig config : this.notControlledPlayers) {
			OtherPlayerInputService is = config.createInputService();
			if (is instanceof PlayerFromNetworkInput) {
				PlayerFromNetworkInput inputservice = (PlayerFromNetworkInput) is;
				stateDispatcher.addInputService(config.getMovementController()
						.getPlayer().id(), inputservice);
			}

			resultReceiver.add(is);
		}
		return resultReceiver;
	}

	public Player getMyPlayer() {
		return this.myPlayerMovement.getPlayer();
	}

	public PlayerMovementController getTabletControlledPlayerMovement() {
		return this.myPlayerMovement;
	}

}
