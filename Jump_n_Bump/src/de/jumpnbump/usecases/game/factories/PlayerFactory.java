package de.jumpnbump.usecases.game.factories;

import android.graphics.Color;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;

public class PlayerFactory {

	public static Player createPlayer1() {
		int id = 0;
		Player p = new Player(new Player(id), id);
		PlayerState state = p.getState();
		state.setCenterX(0.2);
		state.setCenterY(0.90);
		state.setColor(Color.RED);
		p.calculateRect();
		return p;
	}

	public static Player createPlayer2() {
		int id = 1;
		Player p = new Player(new Player(id), id);
		PlayerState state = p.getState();
		state.setCenterX(0.9);
		state.setCenterY(0.99);
		state.setColor(Color.BLUE);
		p.calculateRect();
		return p;
	}

	public static Player createPlayerAtPosition(double x, double y) {
		Player p = new Player(new Player(-1), -1);
		p.setCenterX(x);
		p.setCenterY(y);
		return p;
	}
}
