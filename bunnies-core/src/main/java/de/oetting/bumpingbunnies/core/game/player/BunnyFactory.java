package de.oetting.bumpingbunnies.core.game.player;

import de.oetting.bumpingbunnies.core.assertion.Guard;
import de.oetting.bumpingbunnies.model.color.Color;
import de.oetting.bumpingbunnies.model.game.objects.ConnectionIdentifier;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.PlayerState;

public class BunnyFactory {

	private int speed;

	public BunnyFactory(int speed) {
		this.speed = speed;
	}

	public Bunny createPlayer(int id, String name, ConnectionIdentifier opponent) {
		Guard.againstNull(opponent);
		Guard.againstNull(name);
		Bunny p = new Bunny(id, name, this.speed, opponent);
		PlayerState state = p.getState();
		state.setCenterX((int) (id * 0.35 * ModelConstants.STANDARD_WORLD_SIZE));
		state.setCenterY((int) (0.99 * ModelConstants.STANDARD_WORLD_SIZE));
		p.setColor(getColor(id));
		return p;
	}

	private int getColor(int index) {
		switch (index) {
		case 0:
			return Color.BLUE;
		case 1:
			return Color.RED;
		case 2:
			return Color.GREEN;
		case 3:
			return Color.CYAN;
		case 4:
			return Color.YELLOW;
		case 5:
			return Color.MAGENTA;
		case 6:
			return Color.WHITE;
		case 7:
			return Color.BLACK;
		case 8:
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
