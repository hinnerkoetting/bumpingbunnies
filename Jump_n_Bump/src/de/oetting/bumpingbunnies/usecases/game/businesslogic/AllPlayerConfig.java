package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.PlayerFromNetworkInput;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class AllPlayerConfig {

	private final PlayerMovementController myPlayerMovement;
	private final List<PlayerConfig> notControlledPlayers;
	private final CoordinatesCalculation coordinateCalculations;

	public AllPlayerConfig(PlayerMovementController tabletControlledPlayer,
			List<PlayerConfig> notControlledPlayers,
			CoordinatesCalculation coordinateCalculations) {
		this.myPlayerMovement = tabletControlledPlayer;
		this.notControlledPlayers = notControlledPlayers;
		this.coordinateCalculations = coordinateCalculations;
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

	public List<PlayerMovementCalculation> getAllPlayerMovementCalculations(CollisionDetection collisionDetection, World world) {
		List<PlayerMovementCalculation> list = new ArrayList<PlayerMovementCalculation>(
				this.notControlledPlayers.size() + 1);
		Player p = this.myPlayerMovement.getPlayer();
		PlayerMovementCalculation playerMovementCalculation = createMovementCalculation(collisionDetection, p, world);
		list.add(playerMovementCalculation);
		for (PlayerConfig config : this.notControlledPlayers) {
			list.add(createMovementCalculation(collisionDetection, config.getMovementController().getPlayer(), world));
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

	public PlayerMovementCalculation createMovementCalculation(CollisionDetection colDetection, Player p, World world) {
		PlayerMovementCalculation playerMovementCalculation = new PlayerMovementCalculation(p, new GameObjectInteractor(colDetection,
				world), colDetection);
		return playerMovementCalculation;
	}

	public List<InputService> createOtherInputService(
			NetworkToGameDispatcher networkDispatcher) {
		List<MySocket> allSockets = SocketStorage.getSingleton()
				.getAllSockets();
		List<InputService> resultReceiver = new ArrayList<InputService>(
				allSockets.size());

		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		for (PlayerConfig config : this.notControlledPlayers) {
			InputService is = config.createInputService();
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

	public CoordinatesCalculation getCoordinateCalculations() {
		return this.coordinateCalculations;
	}

}
