package de.oetting.bumpingbunnies.core.game.player;

import de.oetting.bumpingbunnies.model.color.Color;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Opponent;
import de.oetting.bumpingbunnies.model.game.objects.Player;
import de.oetting.bumpingbunnies.model.game.objects.PlayerState;

public class PlayerFactory {

	private int speed;

	public PlayerFactory(int speed) {
		super();
		this.speed = speed;
	}

	public Player createPlayer(int id, String name, Opponent opponent) {
		Player p = new Player(id, name, this.speed, opponent);
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
