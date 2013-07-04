package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.ArrayList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.android.GameView;
import de.oetting.bumpingbunnies.usecases.game.android.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;
import de.oetting.bumpingbunnies.usecases.game.communication.NetworkToGameDispatcher;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.communication.factories.AbstractStateSenderFactory;
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
			AbstractOtherPlayersFactory factory, List<RemoteSender> allSender) {

		List<InputService> inputServices = new ArrayList<InputService>(
				this.notControlledPlayers.size());
		NetworkToGameDispatcher networkDispatcher = new NetworkToGameDispatcher();
		for (PlayerConfig config : this.notControlledPlayers) {
			inputServices.add(config.createInputService(allSender,
					networkDispatcher));
		}
		return inputServices;
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
