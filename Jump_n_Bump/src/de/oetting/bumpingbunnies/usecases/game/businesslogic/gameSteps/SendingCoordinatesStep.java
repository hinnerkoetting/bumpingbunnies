package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerJoinListener;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.factories.businessLogic.StateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class SendingCoordinatesStep implements GameStepAction, PlayerJoinListener {

	private final List<StateSender> stateSender;
	private final StateSenderFactory senderFactory;

	public SendingCoordinatesStep(List<StateSender> stateSender, StateSenderFactory senderFactory) {
		super();
		this.stateSender = stateSender;
		this.senderFactory = senderFactory;
		if (senderFactory == null) {
			throw new IllegalArgumentException();
		}
	}

	@Override
	public void executeNextStep(long deltaStepsSinceLastCall) {
		for (StateSender ss : this.stateSender) {
			ss.sendPlayerCoordinates();
		}
	}

	@Override
	public void newPlayerJoined(Player p) {
		StateSender newStateSender = this.senderFactory.create(p);
		this.stateSender.add(newStateSender);
	}

	@Override
	public void playerLeftTheGame(Player p) {
		StateSender ss = findStateSenderForPlayer(p);
		this.stateSender.remove(ss);
	}

	private StateSender findStateSenderForPlayer(Player p) {
		for (StateSender ss : this.stateSender) {
			if (ss.sendsStateToPlayer(p)) {
				return ss;
			}
		}
		return null;
	}

}
