package de.oetting.bumpingbunnies.usecases.game.communication.factories;

import de.oetting.bumpingbunnies.usecases.game.communication.GameNetworkSender;
import de.oetting.bumpingbunnies.usecases.game.communication.RemoteSender;
import de.oetting.bumpingbunnies.usecases.game.communication.StateSender;
import de.oetting.bumpingbunnies.usecases.game.configuration.OpponentConfiguration;
import de.oetting.bumpingbunnies.usecases.game.factories.AbstractOtherPlayersFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class StateSenderFactory extends AbstractStateSenderFactory {

	@Override
	public StateSender create(Player player,
			OpponentConfiguration configuration) {
		AbstractOtherPlayersFactory factory = configuration.getFactory();
		RemoteSender sender = factory.createSender();
		return new GameNetworkSender(player, sender);
	}
}
