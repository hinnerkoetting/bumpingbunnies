package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Color;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class PlayerFactory {

	private static final Logger LOGGER = LoggerFactory
			.getLogger(PlayerFactory.class);

	private int speed;

	public PlayerFactory(int speed) {
		super();
		this.speed = speed;
	}

	public List<Player> createAllPlayers(int number, String name) {
		List<Player> allPlayers = new LinkedList<Player>();
		for (int i = 0; i < number; i++) {
			allPlayers.add(createPlayer(i, name));
		}
		return allPlayers;
	}

	// public Player createPlayer(int i, String name) {
	// LOGGER.debug("Creating player %d", i);
	// switch (i) {
	// case 0:
	// return createPlayer1(name);
	// case 1:
	// return createPlayer2(name);
	// case 2:
	// return createPlayer3(name);
	// case 3:
	// return createPlayer4(name);
	// default:
	// throw new IllegalArgumentException("Too many players");
	// }
	//
	// }
	//
	// public Player createPlayer1(String name) {
	// int id = 0;
	// Player p = new Player(new Player(id, name, this.speed), id, name,
	// this.speed);
	// PlayerState state = p.getState();
	// state.setCenterX((int) (0.2 * ModelConstants.MAX_VALUE));
	// state.setCenterY((int) (0.9 * ModelConstants.MAX_VALUE));
	// state.setColor(Color.RED);
	// p.calculateRect();
	// return p;
	// }
	//
	// public Player createPlayer2(String name) {
	// int id = 1;
	// Player p = new Player(new Player(id, name, this.speed), id, name,
	// this.speed);
	// PlayerState state = p.getState();
	// state.setCenterX((int) (0.4 * ModelConstants.MAX_VALUE));
	// state.setCenterY((int) (0.99 * ModelConstants.MAX_VALUE));
	// state.setColor(Color.BLUE);
	// p.calculateRect();
	// return p;
	// }
	//
	// public Player createPlayer3(String name) {
	// int id = 2;
	// Player p = new Player(new Player(id, name, this.speed), id, name,
	// this.speed);
	// PlayerState state = p.getState();
	// state.setCenterX((int) (0.9 * ModelConstants.MAX_VALUE));
	// state.setCenterY((int) (0.99 * ModelConstants.MAX_VALUE));
	// state.setColor(Color.MAGENTA);
	// p.calculateRect();
	// return p;
	// }
	//
	// public Player createPlayer4(String name) {
	// int id = 3;
	// Player p = new Player(new Player(id, name, this.speed), id, name,
	// this.speed);
	// PlayerState state = p.getState();
	// state.setCenterX((int) (0.6 * ModelConstants.MAX_VALUE));
	// state.setCenterY((int) (0.99 * ModelConstants.MAX_VALUE));
	// state.setColor(Color.GREEN);
	// p.calculateRect();
	// return p;
	// }

	public Player createPlayer(int id, String name) {
		Player p = new Player(new Player(id, name, this.speed), id, name,
				this.speed);
		PlayerState state = p.getState();
		state.setCenterX((int) (id * 0.35 * ModelConstants.MAX_VALUE));
		state.setCenterY((int) (0.99 * ModelConstants.MAX_VALUE));
		state.setColor(getColor(id));
		p.calculateRect();
		return p;
	}

	private int getColor(int index) {
		switch (index) {
		case 0:
			return Color.RED;
		case 1:
			return Color.BLUE;
		case 2:
			return Color.GRAY;
		case 3:
			return Color.CYAN;
		case 4:
			return Color.BLACK;
		case 5:
			return Color.DKGRAY;
		case 6:
			return Color.GREEN;
		case 7:
			return Color.MAGENTA;
		case 8:
			return Color.WHITE;
		case 9:
			return Color.YELLOW;
		case 10:
			return Color.LTGRAY;
		case 11:
			return 0xff22cc88;
		default:
			throw new IllegalArgumentException("too many players");
		}
	}

	public Player createPlayerAtPosition(int x, int y) {
		Player p = new Player(new Player(-1, "", this.speed), -1, "",
				this.speed);
		p.setCenterX(x);
		p.setCenterY(y);
		return p;
	}
}
