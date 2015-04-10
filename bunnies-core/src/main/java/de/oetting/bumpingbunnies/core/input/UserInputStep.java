package de.oetting.bumpingbunnies.core.input;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.configuration.OpponentInputFactory;
import de.oetting.bumpingbunnies.core.game.steps.GameStepAction;
import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class UserInputStep implements GameStepAction, PlayerJoinListener {

	private final List<OpponentInput> inputServices;
	private final OpponentInputFactory factory;

	public UserInputStep(List<OpponentInput> inputServices, OpponentInputFactory factory) {
		super();
		this.inputServices = inputServices;
		this.factory = factory;
	}

	public UserInputStep(OpponentInputFactory factory) {
		super();
		this.inputServices = new CopyOnWriteArrayList<OpponentInput>();
		this.factory = factory;
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (OpponentInput movementService : this.inputServices) {
			movementService.executeNextStep(deltaStepsSinceLastCall);
		}
	}

	@Override
	public void newEvent(Bunny p) {
		this.inputServices.add(this.factory.create(p));
	}

	@Override
	public void removeEvent(Bunny p) {
	}

}
