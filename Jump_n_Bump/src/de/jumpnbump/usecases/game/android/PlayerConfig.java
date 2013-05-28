package de.jumpnbump.usecases.game.android;

import java.util.ArrayList;
import java.util.List;

import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.communication.factories.AbstractStateSenderFactory;
import de.jumpnbump.usecases.game.model.Player;

public class PlayerConfig {

	private final Player tabletControlledPlayer;

	private final List<Player> notControlledPlayers;

	public PlayerConfig(Player tabletControlledPlayer,
			List<Player> notControlledPlayers) {
		this.tabletControlledPlayer = tabletControlledPlayer;
		this.notControlledPlayers = notControlledPlayers;
	}

	public List<StateSender> createStateSender(
			AbstractStateSenderFactory senderFactory) {
		List<StateSender> stateSender = new ArrayList<StateSender>(
				this.notControlledPlayers.size());
		stateSender.add(senderFactory.create(this.tabletControlledPlayer));
		return stateSender;
	}
}
