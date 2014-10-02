package de.oetting.bumpingbunnies.core.game.steps;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.network.StateSender;
import de.oetting.bumpingbunnies.core.network.StateSenderFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class SendingCoordinatesStep implements GameStepAction, PlayerJoinListener {

	private final List<StateSender> stateSender;
	private final StateSenderFactory senderFactory;

	public SendingCoordinatesStep(StateSenderFactory senderFactory) {
		super();
		this.stateSender = new CopyOnWriteArrayList<StateSender>();
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
