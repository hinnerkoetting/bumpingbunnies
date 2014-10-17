package de.oetting.bumpingbunnies.core.game;

import de.oetting.bumpingbunnies.model.game.objects.Opponent;

public class OpponentTestFactory {

	public static Opponent create() {
		return OpponentFactory.createAiPlayer("test");
	}
}
