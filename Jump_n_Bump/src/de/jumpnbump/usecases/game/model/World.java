package de.jumpnbump.usecases.game.model;

import java.util.ArrayList;
import java.util.List;

import de.jumpnbump.usecases.game.factories.PlayerFactory;

public class World {

	private Player player1;
	private Player player2;

	private List<GameObject> allObjects;
	private List<Player> allPlayer;

	public World() {
		super();
		this.player1 = PlayerFactory.createPlayer1();
		this.player2 = PlayerFactory.createPlayer2();
		this.allObjects = new ArrayList<GameObject>(2);
		this.allPlayer = new ArrayList<Player>(2);
		this.allObjects.add(this.player1);
		this.allObjects.add(this.player2);
		this.allPlayer.add(this.player1);
		this.allPlayer.add(this.player2);

	}

	public Player getPlayer1() {
		return this.player1;
	}

	public Player getPlayer2() {
		return this.player2;
	}

	public List<GameObject> getAllObjects() {
		return this.allObjects;
	}

	public List<Player> getAllPlayer() {
		return this.allPlayer;
	}
}
