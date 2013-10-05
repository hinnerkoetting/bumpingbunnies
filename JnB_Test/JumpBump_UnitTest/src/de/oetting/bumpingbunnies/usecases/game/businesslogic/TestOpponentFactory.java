package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class TestOpponentFactory {

	public static Opponent createDummyOpponent() {
		return Opponent.createOpponent("test");
	}

}
