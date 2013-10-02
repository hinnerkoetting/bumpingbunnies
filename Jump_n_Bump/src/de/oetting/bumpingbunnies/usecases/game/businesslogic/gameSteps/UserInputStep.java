package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.factories.OtherPlayerInputService;

public class UserInputStep implements GameStepAction {

	private final List<OtherPlayerInputService> inputServices;

	public UserInputStep(List<OtherPlayerInputService> inputServices) {
		super();
		this.inputServices = inputServices;
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (OtherPlayerInputService movementService : this.inputServices) {
			movementService.executeNextStep();
		}
	}

}
