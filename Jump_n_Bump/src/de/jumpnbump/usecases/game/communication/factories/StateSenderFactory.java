package de.jumpnbump.usecases.game.communication.factories;

import de.jumpnbump.usecases.game.communication.GameNetworkSender;
import de.jumpnbump.usecases.game.communication.RemoteSender;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.model.Player;

public class StateSenderFactory extends AbstractStateSenderFactory {

	private final RemoteSender networkThread;

	public StateSenderFactory(RemoteSender networkThread) {
		this.networkThread = networkThread;
	}

	@Override
	public StateSender create(Player player) {
		return new GameNetworkSender(player, this.networkThread);
	}
}
