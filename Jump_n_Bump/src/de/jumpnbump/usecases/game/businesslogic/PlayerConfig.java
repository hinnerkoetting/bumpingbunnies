package de.jumpnbump.usecases.game.businesslogic;

import java.util.ArrayList;
import java.util.List;

import de.jumpnbump.usecases.game.android.GameView;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.gamepad.GamepadInputService;
import de.jumpnbump.usecases.game.android.input.touch.TouchService;
import de.jumpnbump.usecases.game.android.input.touch.TouchWithJumpService;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.factories.AbstractInputServiceFactory;
import de.jumpnbump.usecases.game.factories.AbstractOtherPlayersFactorySingleton;
import de.jumpnbump.usecases.game.factories.UserInputFactory;
import de.jumpnbump.usecases.game.model.World;

public class PlayerConfig {

	private final PlayerMovementController tabletControlledPlayer;
	private final List<PlayerMovementController> notControlledPlayers;
	private final GameView gameView;
	private final World world;

	public PlayerConfig(PlayerMovementController tabletControlledPlayer,
			List<PlayerMovementController> notControlledPlayers,
			GameView gameView, World world) {
		this.tabletControlledPlayer = tabletControlledPlayer;
		this.notControlledPlayers = notControlledPlayers;
		this.gameView = gameView;
		this.world = world;
	}

	public List<StateSender> createStateSender(
			AbstractStateSenderFactory senderFactory) {
		List<StateSender> stateSender = new ArrayList<StateSender>(
				this.notControlledPlayers.size());
		stateSender.add(senderFactory.create(this.tabletControlledPlayer
				.getPlayer()));
		return stateSender;
	}

	public TouchService createTouchService() {
		return UserInputFactory.createTouch(this.tabletControlledPlayer,
				this.gameView);
	}

	public TouchWithJumpService createTouchWithJumpService() {
		return UserInputFactory.createTouchWithJump(
				this.tabletControlledPlayer, this.gameView);
	}

	public GamepadInputService createGamepadService() {
		return UserInputFactory.createGamepad(this.tabletControlledPlayer);
	}

	public List<PlayerMovementController> getAllPlayerMovementControllers() {
		List<PlayerMovementController> list = new ArrayList<PlayerMovementController>(
				this.notControlledPlayers.size() + 1);
		list.add(this.tabletControlledPlayer);
		list.addAll(this.notControlledPlayers);
		return list;
	}

	public InputService createNetworkInputService(
			AbstractOtherPlayersFactorySingleton factory) {
		InformationSupplier informationSupplier = factory
				.createInformationSupplier();
		AbstractInputServiceFactory inputServiceFactory = factory
				.getInputServiceFactory();
		return inputServiceFactory.create(informationSupplier,
				this.notControlledPlayers.get(0), this.world);
	}

}
