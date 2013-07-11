package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.Collections;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;

public class SendingCoordinatesStep implements GameStepAction {

	private final List<StateSender> stateSender;

	public SendingCoordinatesStep(List<StateSender> stateSender) {
		super();
		this.stateSender = Collections.unmodifiableList(stateSender);
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (StateSender ss : this.stateSender) {
			ss.sendPlayerCoordinates();
		}
	}

}
