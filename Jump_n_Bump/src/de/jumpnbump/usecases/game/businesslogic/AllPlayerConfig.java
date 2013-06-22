package de.jumpnbump.usecases.game.businesslogic;

import java.util.ArrayList;
import java.util.List;

import de.jumpnbump.usecases.game.android.GameView;
import de.jumpnbump.usecases.game.android.calculation.CoordinatesCalculation;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.factories.AbstractInputServiceFactory;
import de.jumpnbump.usecases.game.factories.AbstractOtherPlayersFactorySingleton;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.World;

public class AllPlayerConfig {

	private final PlayerMovementController tabletControlledPlayer;
	private final List<PlayerConfig> notControlledPlayers;
	private final GameView gameView;
	private final World world;
	private final CoordinatesCalculation coordinateCalculations;

	public AllPlayerConfig(PlayerMovementController tabletControlledPlayer,
			List<PlayerConfig> notControlledPlayers, GameView gameView,
			World world, CoordinatesCalculation coordinateCalculations) {
		this.tabletControlledPlayer = tabletControlledPlayer;
		this.notControlledPlayers = notControlledPlayers;
		this.gameView = gameView;
		this.world = world;
		this.coordinateCalculations = coordinateCalculations;
	}

	public List<StateSender> createStateSender(
			AbstractStateSenderFactory senderFactory) {
		List<StateSender> stateSender = new ArrayList<StateSender>(
				this.notControlledPlayers.size());
		stateSender.add(senderFactory.create(this.tabletControlledPlayer
				.getPlayer()));
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
			AbstractOtherPlayersFactorySingleton factory) {
		InformationSupplier informationSupplier = factory
				.createInformationSupplier();
		AbstractInputServiceFactory inputServiceFactory = factory
				.getInputServiceFactory();

		List<InputService> inputServices = new ArrayList<InputService>(
				this.notControlledPlayers.size());
		for (PlayerConfig config : this.notControlledPlayers) {
			inputServices.add(config.createInputService());
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
