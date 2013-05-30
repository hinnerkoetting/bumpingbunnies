package de.jumpnbump.usecases.game.businesslogic;

import java.util.ArrayList;
import java.util.List;

import de.jumpnbump.usecases.game.android.GameView;
import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.android.input.analog.AnalogInputService;
import de.jumpnbump.usecases.game.android.input.gamepad.GamepadInputService;
import de.jumpnbump.usecases.game.android.input.multiTouch.MultiTouchInputService;
import de.jumpnbump.usecases.game.android.input.pointer.PointerInputService;
import de.jumpnbump.usecases.game.android.input.rememberPointer.RememberPointerInputService;
import de.jumpnbump.usecases.game.android.input.touch.TouchService;
import de.jumpnbump.usecases.game.android.input.touch.TouchWithJumpService;
import de.jumpnbump.usecases.game.android.input.touchFling.TouchFlingService;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.factories.AbstractInputServiceFactory;
import de.jumpnbump.usecases.game.factories.AbstractOtherPlayersFactorySingleton;
import de.jumpnbump.usecases.game.factories.UserInputFactory;
import de.jumpnbump.usecases.game.model.World;

public class PlayerConfigFactory {

	private final PlayerMovementController tabletControlledPlayer;
	private final List<PlayerMovementController> notControlledPlayers;
	private final GameView gameView;
	private final World world;

	public PlayerConfigFactory(PlayerMovementController tabletControlledPlayer,
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

	public MultiTouchInputService createMultiTouchService() {
		return UserInputFactory.createMultiTouch(this.tabletControlledPlayer,
				this.gameView);
	}

	public PointerInputService createPointerInputService() {
		return UserInputFactory.createPointer(this.tabletControlledPlayer,
				this.gameView);
	}

	public RememberPointerInputService createRememberPointerInputService() {
		return UserInputFactory.createRememberPointer(
				this.tabletControlledPlayer, this.gameView);
	}

	public AnalogInputService createAnalogInputService() {
		return UserInputFactory.createAnalog(this.tabletControlledPlayer);
	}

	public TouchFlingService createTouchFlingService() {
		return UserInputFactory.createTouchFling(this.tabletControlledPlayer,
				this.gameView);
	}

	public List<PlayerMovementController> getAllPlayerMovementControllers() {
		List<PlayerMovementController> list = new ArrayList<PlayerMovementController>(
				this.notControlledPlayers.size() + 1);
		list.add(this.tabletControlledPlayer);
		list.addAll(this.notControlledPlayers);
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
		for (PlayerMovementController movement : this.notControlledPlayers) {
			inputServices.add(inputServiceFactory.create(informationSupplier,
					movement, this.world));
		}
		return inputServices;
	}

	public PlayerMovementController getTabletControlledPlayer() {
		return this.tabletControlledPlayer;
	}

	public GameView getGameView() {
		return this.gameView;
	}

}
