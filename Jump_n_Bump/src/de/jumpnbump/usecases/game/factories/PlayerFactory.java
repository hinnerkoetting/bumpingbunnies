package de.jumpnbump.usecases.game.factories;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Color;
import de.jumpnbump.logger.Logger;
import de.jumpnbump.logger.MyLog;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;

public class PlayerFactory {

	private static final MyLog LOGGER = Logger.getLogger(PlayerFactory.class);

	public static List<Player> createAllPlayers(int number) {
		List<Player> allPlayers = new LinkedList<Player>();
		for (int i = 0; i < number; i++) {
			allPlayers.add(PlayerFactory.createPlayer(i));
		}
		return allPlayers;
	}

	public static Player createPlayer(int i) {
		LOGGER.debug("Creating player %d", i);
		switch (i) {
		case 0:
			return createPlayer1();
		case 1:
			return createPlayer2();
		case 2:
			return createPlayer3();
		case 3:
			return createPlayer4();
		default:
			throw new IllegalArgumentException("Too many players");
		}

	}

	public static Player createPlayer1() {
		int id = 0;
		Player p = new Player(new Player(id), id);
		PlayerState state = p.getState();
		state.setCenterX((int) (0.2 * ModelConstants.MAX_VALUE));
		state.setCenterY((int) (0.9 * ModelConstants.MAX_VALUE));
		state.setColor(Color.RED);
		p.calculateRect();
		return p;
	}

	public static Player createPlayer2() {
		int id = 1;
		Player p = new Player(new Player(id), id);
		PlayerState state = p.getState();
		state.setCenterX((int) (0.4 * ModelConstants.MAX_VALUE));
		state.setCenterY((int) (0.99 * ModelConstants.MAX_VALUE));
		state.setColor(Color.BLUE);
		p.calculateRect();
		return p;
	}

	public static Player createPlayer3() {
		int id = 2;
		Player p = new Player(new Player(id), id);
		PlayerState state = p.getState();
		state.setCenterX((int) (0.9 * ModelConstants.MAX_VALUE));
		state.setCenterY((int) (0.99 * ModelConstants.MAX_VALUE));
		state.setColor(Color.MAGENTA);
		p.calculateRect();
		return p;
	}

	public static Player createPlayer4() {
		int id = 3;
		Player p = new Player(new Player(id), id);
		PlayerState state = p.getState();
		state.setCenterX((int) (0.6 * ModelConstants.MAX_VALUE));
		state.setCenterY((int) (0.99 * ModelConstants.MAX_VALUE));
		state.setColor(Color.GREEN);
		p.calculateRect();
		return p;
	}

	public static Player createPlayerAtPosition(int x, int y) {
		Player p = new Player(new Player(-1), -1);
		p.setCenterX(x);
		p.setCenterY(y);
		return p;
	}
}
