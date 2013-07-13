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
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.communication.messages.player.PlayerStateDispatcher;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractOtherPlayersFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class AllPlayerConfig {

	private final PlayerMovementController tabletControlledPlayer;
	private final List<PlayerConfig> notControlledPlayers;
	private final GameView gameView;
	private final CoordinatesCalculation coordinateCalculations;

	public AllPlayerConfig(PlayerMovementController tabletControlledPlayer,
			List<PlayerConfig> notControlledPlayers, GameView gameView,
			World world, CoordinatesCalculation coordinateCalculations) {
		this.tabletControlledPlayer = tabletControlledPlayer;
		this.notControlledPlayers = notControlledPlayers;
		this.gameView = gameView;
		this.coordinateCalculations = coordinateCalculations;
	}

	public List<StateSender> createStateSender() {
		List<StateSender> stateSender = new ArrayList<StateSender>(
				this.notControlledPlayers.size());
		for (PlayerConfig config : this.notControlledPlayers) {
			AbstractOtherPlayersFactory factory = config
					.getOtherPlayerFactory();
			AbstractStateSenderFactory senderFactory = factory
					.createStateSenderFactory();
			stateSender.add(senderFactory.create(
					this.tabletControlledPlayer.getPlayer(),
					config.getConfiguration()));
		}
		return stateSender;
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

	public List<InputService> createOtherInputService(
			NetworkToGameDispatcher networkDispatcher,
			AbstractOtherPlayersFactory factory, List<RemoteSender> allSender) {
		List<MySocket> allSockets = SocketStorage.getSingleton()
				.getAllSockets();
		List<InputService> resultReceiver = new ArrayList<InputService>(
				allSockets.size());

		PlayerStateDispatcher stateDispatcher = new PlayerStateDispatcher(networkDispatcher);
		for (PlayerConfig config : this.notControlledPlayers) {
			InputService is = config.createInputService(allSender,
					networkDispatcher);
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
