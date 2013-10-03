package de.oetting.bumpingbunnies.usecases.game.factories;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Color;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Opponent;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.PlayerState;

public class PlayerFactory {

	private int speed;

	public PlayerFactory(int speed) {
		super();
		this.speed = speed;
	}

	public List<Player> createAllPlayers(int number, String name, Opponent opponent) {
		List<Player> allPlayers = new LinkedList<Player>();
		for (int i = 0; i < number; i++) {
			allPlayers.add(createPlayer(i, name, opponent));
		}
		return allPlayers;
	}

	public Player createPlayer(int id, String name, Opponent opponent) {
		Player p = new Player(new Player(id, name, this.speed, opponent), id, name,
				this.speed, opponent);
		PlayerState state = p.getState();
		p.setDead(true);
		// TODO: get from spawnpoints
		state.setCenterX((int) (id * 0.35 * ModelConstants.STANDARD_WORLD_SIZE));
		state.setCenterY((int) (0.99 * ModelConstants.STANDARD_WORLD_SIZE));
		p.setColor(getColor(id));
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

}
