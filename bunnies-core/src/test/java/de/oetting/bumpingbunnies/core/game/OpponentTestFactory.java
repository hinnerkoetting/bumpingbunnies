package de.oetting.bumpingbunnies.core.game;

import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;

public class OpponentTestFactory {

	public static ConnectionIdentifier create() {
		return ConnectionIdentifierFactory.createAiPlayer("test");
	}
}
