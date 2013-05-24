package de.jumpnbump.usecases.game.model;

public class World {

	private Player player1;

	public World() {
		super();
		this.player1 = new Player();
	}

	public Player getPlayer1() {
		return this.player1;
	}

	public void setPlayer1(Player player1) {
		this.player1 = player1;
	}

}
