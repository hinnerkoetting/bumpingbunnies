package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class TestPlayerFactory {

	public static Player createMyPlayer() {
		return new Player(0, "", 1, Opponent.createMyPlayer("test"));
	}

	public static Player createOpponentPlayer(OpponentType type) {
		return new Player(1, "", 1, Opponent.createOpponent("opponent", type));
	}

	public static Player createOpponentPlayer() {
		return new Player(1, "", 1, TestOpponentFactory.createDummyOpponent());
	}

	public static Player createPlayerAtPosition(int x, int y) {
		Opponent opponent = TestOpponentFactory.createDummyOpponent();
		Player p = new Player(-1, "", 1, opponent);
		p.setCenterX(x);
		p.setCenterY(y);
		return p;
	}
}
