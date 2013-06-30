package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Color;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.MyLog;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class PlayerFactory {

	private static final MyLog LOGGER = Logger.getLogger(PlayerFactory.class);

	private int speed;

	public PlayerFactory(int speed) {
		super();
		this.speed = speed;
	}

	public List<Player> createAllPlayers(int number) {
		List<Player> allPlayers = new LinkedList<Player>();
		for (int i = 0; i < number; i++) {
			allPlayers.add(createPlayer(i));
		}
		return allPlayers;
	}

	public Player createPlayer(int i) {
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

	public Player createPlayer1() {
		int id = 0;
		Player p = new Player(new Player(id, this.speed), this.speed, id);
		PlayerState state = p.getState();
		state.setCenterX((int) (0.2 * ModelConstants.MAX_VALUE));
		state.setCenterY((int) (0.9 * ModelConstants.MAX_VALUE));
		state.setColor(Color.RED);
		p.calculateRect();
		return p;
	}

	public Player createPlayer2() {
		int id = 1;
		Player p = new Player(new Player(id, this.speed), this.speed, id);
		PlayerState state = p.getState();
		state.setCenterX((int) (0.4 * ModelConstants.MAX_VALUE));
		state.setCenterY((int) (0.99 * ModelConstants.MAX_VALUE));
		state.setColor(Color.BLUE);
		p.calculateRect();
		return p;
	}

	public Player createPlayer3() {
		int id = 2;
		Player p = new Player(new Player(id, this.speed), this.speed, id);
		PlayerState state = p.getState();
		state.setCenterX((int) (0.9 * ModelConstants.MAX_VALUE));
		state.setCenterY((int) (0.99 * ModelConstants.MAX_VALUE));
		state.setColor(Color.MAGENTA);
		p.calculateRect();
		return p;
	}

	public Player createPlayer4() {
		int id = 3;
		Player p = new Player(new Player(id, this.speed), this.speed, id);
		PlayerState state = p.getState();
		state.setCenterX((int) (0.6 * ModelConstants.MAX_VALUE));
		state.setCenterY((int) (0.99 * ModelConstants.MAX_VALUE));
		state.setColor(Color.GREEN);
		p.calculateRect();
		return p;
	}

	public Player createPlayerAtPosition(int x, int y) {
		Player p = new Player(new Player(-1, this.speed), this.speed, -1);
		p.setCenterX(x);
		p.setCenterY(y);
		return p;
	}
}
