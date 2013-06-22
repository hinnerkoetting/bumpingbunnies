package de.jumpnbump.usecases.game.communication.factories;

import de.jumpnbump.usecases.game.communication.GameNetworkSender;
import de.jumpnbump.usecases.game.communication.RemoteSender;
import de.jumpnbump.usecases.game.communication.StateSender;
import de.jumpnbump.usecases.game.configuration.OtherPlayerConfiguration;
import de.jumpnbump.usecases.game.factories.AbstractOtherPlayersFactory;
import de.jumpnbump.usecases.game.model.Player;

public class StateSenderFactory extends AbstractStateSenderFactory {

	@Override
	public StateSender create(Player player,
			OtherPlayerConfiguration configuration) {
		AbstractOtherPlayersFactory factory = configuration.getFactory();
		RemoteSender sender = factory.createSender();
		return new GameNetworkSender(player, sender);
	}
}
