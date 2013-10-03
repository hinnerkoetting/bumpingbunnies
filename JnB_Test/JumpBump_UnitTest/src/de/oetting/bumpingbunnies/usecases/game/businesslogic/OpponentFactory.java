package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.model.Opponent;

public class OpponentFactory {

	public static Opponent createDummy() {
		return new Opponent("dummy");
	}
}
