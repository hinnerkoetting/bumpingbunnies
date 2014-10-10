package de.oetting.bumpingbunnies.model.game.objects;

public class OpponentTestFactory {

	public static Opponent create() {
		return OpponentFactory.createAiPlayer("test");
	}
}
