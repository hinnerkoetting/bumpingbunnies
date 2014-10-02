package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;

public class TestOpponentFactory {

	public static Opponent createDummyOpponent() {
		return Opponent.createOpponent("test", OpponentType.AI);
	}

}
