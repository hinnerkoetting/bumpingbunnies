package de.jumpnbump.usecases.game.model;

import de.jumpnbump.usecases.game.factories.PlayerFactory;

public class World {

	private Player player1;
	private Player player2;

	public World() {
		super();
		this.player1 = PlayerFactory.createPlayer1();
		this.player2 = PlayerFactory.createPlayer2();
	}

	public Player getPlayer1() {
		return this.player1;
	}

	public Player getPlayer2() {
		return this.player2;
	}

}
