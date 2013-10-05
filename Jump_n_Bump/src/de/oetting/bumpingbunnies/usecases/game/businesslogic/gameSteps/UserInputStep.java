package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerJoinListener;
import de.oetting.bumpingbunnies.usecases.game.factories.OpponentInputFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.OpponentInput;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class UserInputStep implements GameStepAction, PlayerJoinListener {

	private final List<OpponentInput> inputServices;
	private final OpponentInputFactory factory;

	public UserInputStep(List<OpponentInput> inputServices, OpponentInputFactory factory) {
		super();
		this.inputServices = inputServices;
		this.factory = factory;
		if (factory == null) {
			throw new IllegalStateException("not implemented");
		}
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (OpponentInput movementService : this.inputServices) {
			movementService.executeNextStep();
		}
	}

	@Override
	public void newPlayerJoined(Player p) {
		this.inputServices.add(this.factory.create(p));
	}

	@Override
	public void playerLeftTheGame(Player p) {
	}

}
