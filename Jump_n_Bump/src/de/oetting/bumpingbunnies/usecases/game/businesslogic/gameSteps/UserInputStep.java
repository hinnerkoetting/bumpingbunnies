package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.android.input.InputService;

public class UserInputStep implements GameStepAction {

	private final List<InputService> inputServices;

	public UserInputStep(List<InputService> inputServices) {
		super();
		this.inputServices = Collections.unmodifiableList(inputServices);
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (InputService movementService : this.inputServices) {
			movementService.executeUserInput();
		}
	}

}
