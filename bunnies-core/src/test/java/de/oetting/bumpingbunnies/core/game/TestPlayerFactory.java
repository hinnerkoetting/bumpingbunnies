package de.oetting.bumpingbunnies.core.game;

import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.OpponentIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class TestPlayerFactory {

	public static Player createMyPlayer() {
		return new Player(0, "", 1, OpponentFactory.createLocalPlayer(""));
	}

	public static Player createOpponentPlayer(OpponentType type) {
		return new Player(1, "", 1, new ConnectionIdentifier(new OpponentIdentifier(""), type));
	}

	public static Player createOpponentPlayer() {
		return new Player(1, "", 1, OpponentTestFactory.create());
	}

	public static Player createPlayerAtPosition(int x, int y) {
		ConnectionIdentifier opponent = OpponentTestFactory.create();
		Player p = new Player(-1, "", 1, opponent);
		p.setCenterX(x);
		p.setCenterY(y);
		return p;
	}
}
