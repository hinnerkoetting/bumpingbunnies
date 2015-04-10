package de.oetting.bumpingbunnies.core.game;

import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.OpponentIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.OpponentType;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class TestPlayerFactory {

	public static Bunny createMyPlayer() {
		return new Bunny(0, "", 1, ConnectionIdentifierFactory.createLocalPlayer(""));
	}

	public static Bunny createOpponentPlayer(OpponentType type) {
		return new Bunny(1, "", 1, new ConnectionIdentifier(new OpponentIdentifier(""), type));
	}

	public static Bunny createOpponentPlayer() {
		return new Bunny(1, "", 1, OpponentTestFactory.create());
	}

	public static Bunny createPlayerAtPosition(int x, int y) {
		ConnectionIdentifier opponent = OpponentTestFactory.create();
		Bunny p = new Bunny(-1, "", 1, opponent);
		p.setCenterX(x);
		p.setCenterY(y);
		return p;
	}
}
