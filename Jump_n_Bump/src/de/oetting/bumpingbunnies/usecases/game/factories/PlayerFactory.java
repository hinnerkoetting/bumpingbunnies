package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Color;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class PlayerFactory {

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

	public Player createPlayer(int id, String name) {
		Player p = new Player(new Player(id, name, this.speed), id, name,
				this.speed);
		PlayerState state = p.getState();
		p.setDead(true);
		// TODO: get from spawnpoints
		state.setCenterX((int) (id * 0.35 * ModelConstants.STANDARD_WORLD_SIZE));
		state.setCenterY((int) (0.99 * ModelConstants.STANDARD_WORLD_SIZE));
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
			return Color.GREEN;
		case 3:
			return Color.CYAN;
		case 4:
			return Color.BLACK;
		case 7:
			return Color.MAGENTA;
		case 8:
			return Color.WHITE;
		case 9:
			return Color.YELLOW;
		case 5:
			return Color.DKGRAY;
		case 6:
			return Color.GRAY;
		default:
			return createRandomColor();
		}
	}

	private int createRandomColor() {
		double random = Math.random();
		int baseColor = (int) (random * 0x00FFFFFF);
		return 0xFF000000 + baseColor;
	}

	public Player createPlayerAtPosition(int x, int y) {
		Player p = new Player(new Player(-1, "", this.speed), -1, "",
				this.speed);
		p.setCenterX(x);
		p.setCenterY(y);
		return p;
	}
}
