package de.jumpnbump.usecases.game.businesslogic;

import de.jumpnbump.usecases.game.android.input.InputService;
import de.jumpnbump.usecases.game.communication.InformationSupplier;
import de.jumpnbump.usecases.game.factories.AbstractInputServiceFactory;
import de.jumpnbump.usecases.game.factories.AbstractOtherPlayersFactory;
import de.jumpnbump.usecases.game.model.World;

public class PlayerConfig {

	private AbstractOtherPlayersFactory otherPlayerFactory;
	private PlayerMovementController movementController;
	private World world;

	public PlayerConfig(
			AbstractOtherPlayersFactory otherPlayerFactory,
			PlayerMovementController movementController, World world) {
		super();
		this.otherPlayerFactory = otherPlayerFactory;
		this.movementController = movementController;
		this.world = world;
	}

	public PlayerMovementController getMovementController() {
		return this.movementController;
	}

	public InputService createInputService() {
		InformationSupplier informationSupplier = this.otherPlayerFactory
				.createInformationSupplier();
		AbstractInputServiceFactory inputServiceFactory = this.otherPlayerFactory
				.getInputServiceFactory();
		return inputServiceFactory.create(informationSupplier,
				this.movementController, this.world);
	}
}
