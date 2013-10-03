package de.oetting.bumpingbunnies.usecases.game.businesslogic.gameSteps;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerJoinListener;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.factories.businessLogic.StateSenderFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class SendingCoordinatesStep implements GameStepAction, PlayerJoinListener {

	private final List<StateSender> stateSender;
	private final StateSenderFactory senderFactory;

	public SendingCoordinatesStep(StateSenderFactory senderFactory) {
		super();
		this.stateSender = new LinkedList<StateSender>();
		this.senderFactory = senderFactory;
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
		throw new PlayerDoesNotExist();
	}

	List<StateSender> getStateSender() {
		return this.stateSender;
	}

	public class PlayerDoesNotExist extends RuntimeException {
	}

}
