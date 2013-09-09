package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.communication.MySocket;
import de.oetting.bumpingbunnies.usecases.game.android.GameView;
import de.oetting.bumpingbunnies.usecases.game.android.SocketStorage;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.android.input.network.NetworkInputService;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class AllPlayerConfig {

	private final PlayerMovementController tabletControlledPlayer;
	private final List<PlayerConfig> notControlledPlayers;
	private final GameView gameView;
	private final CoordinatesCalculation coordinateCalculations;
	private World world;

	public AllPlayerConfig(PlayerMovementController tabletControlledPlayer,
			List<PlayerConfig> notControlledPlayers, GameView gameView,
			World world, CoordinatesCalculation coordinateCalculations) {
		this.tabletControlledPlayer = tabletControlledPlayer;
		this.notControlledPlayers = notControlledPlayers;
		this.gameView = gameView;
		this.world = world;
		this.coordinateCalculations = coordinateCalculations;
	}

	public List<PlayerMovementController> getAllPlayerMovementControllers() {
		List<PlayerMovementController> list = new ArrayList<PlayerMovementController>(
				this.notControlledPlayers.size() + 1);
		list.add(this.tabletControlledPlayer);
		for (PlayerConfig config : this.notControlledPlayers) {
			list.add(config.getMovementController());
		}
		return list;
	}

	public List<PlayerMovementCalculation> getAllPlayerMovementCalculations() {
		List<PlayerMovementCalculation> list = new ArrayList<PlayerMovementCalculation>(
				this.notControlledPlayers.size() + 1);
		CollisionDetection colDetection = new CollisionDetection(this.world);
		Player p = this.tabletControlledPlayer.getPlayer();
		PlayerMovementCalculation playerMovementCalculation = createMovementCalculation(colDetection, p);
		list.add(playerMovementCalculation);
		for (PlayerConfig config : this.notControlledPlayers) {
			list.add(createMovementCalculation(colDetection, config.getMovementController().getPlayer()));
		}
		return list;
	}

	public List<Player> getAllPlayers() {
		List<Player> allPlayers = new ArrayList<Player>();
		allPlayers.add(this.tabletControlledPlayer.getPlayer());
		for (PlayerConfig config : this.notControlledPlayers) {
			allPlayers.add(config.getMovementController().getPlayer());
		}
		return allPlayers;
	}

	public PlayerMovementCalculation createMovementCalculation(CollisionDetection colDetection, Player p) {
		PlayerMovementCalculation playerMovementCalculation = new PlayerMovementCalculation(p, new GameObjectInteractor(colDetection,
				this.world), colDetection);
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
			if (is instanceof NetworkInputService) {
				NetworkInputService inputservice = (NetworkInputService) is;
				stateDispatcher.addInputService(config.getMovementController()
						.getPlayer().id(), inputservice);
			}

			resultReceiver.add(is);
		}
		return resultReceiver;
	}

	public Player getTabletControlledPlayer() {
		return this.tabletControlledPlayer.getPlayer();
	}

	public PlayerMovementController getTabletControlledPlayerMovement() {
		return this.tabletControlledPlayer;
	}

	public CoordinatesCalculation getCoordinateCalculations() {
		return this.coordinateCalculations;
	}

	public GameView getGameView() {
		return this.gameView;
	}

}
