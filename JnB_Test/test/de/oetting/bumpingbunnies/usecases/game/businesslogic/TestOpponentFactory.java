package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.OpponentType;

public class TestOpponentFactory {

	public static Opponent createDummyOpponent() {
		return Opponent.createOpponent("test", OpponentType.AI);
	}

}
