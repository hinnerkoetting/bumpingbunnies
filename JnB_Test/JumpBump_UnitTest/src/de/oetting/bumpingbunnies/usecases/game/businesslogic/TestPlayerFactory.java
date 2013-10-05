package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class TestPlayerFactory {

	public static Player createMyPlayer() {
		return new Player(0, "", 1, Opponent.createMyPlayer("test"));
	}

	public static Player createOpponentPlayer() {
		return new Player(0, "", 1, Opponent.createOpponent("test"));
	}

	public static Player createPlayerAtPosition(int x, int y) {
		Opponent opponent = Opponent.createOpponent("test");
		Player p = new Player(new Player(-1, "", 1, opponent), -1, "",
				1, opponent);
		p.setCenterX(x);
		p.setCenterY(y);
		return p;
	}
}
