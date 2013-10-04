package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerJoinListener;
import de.oetting.bumpingbunnies.usecases.game.factories.NewOtherPlayerInputServiceFactory;
import de.oetting.bumpingbunnies.usecases.game.factories.OtherPlayerInputService;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class UserInputStep implements GameStepAction, PlayerJoinListener {

	private final List<OtherPlayerInputService> inputServices;
	private final NewOtherPlayerInputServiceFactory factory;

	public UserInputStep(List<OtherPlayerInputService> inputServices, NewOtherPlayerInputServiceFactory factory) {
		super();
		this.inputServices = inputServices;
		this.factory = factory;
		if (factory == null) {
			throw new IllegalStateException();
		}
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (OtherPlayerInputService movementService : this.inputServices) {
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
