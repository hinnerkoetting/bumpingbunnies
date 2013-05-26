package de.jumpnbump.usecases.game.factories;

import android.graphics.Color;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.PlayerState;

public class PlayerFactory {

	public static Player createPlayer1() {
		int id = IdCounter.getNextId();
		Player p = new Player(new Player(id), id);
		PlayerState state = p.getState();
		state.setCenterX(0.2);
		state.setCenterY(0.01);
		state.setColor(Color.RED);
		return p;
	}

	public static Player createPlayer2() {
		int id = IdCounter.getNextId();
		Player p = new Player(new Player(id), id);
		PlayerState state = p.getState();
		state.setCenterX(0.8);
		state.setCenterY(0.01);
		state.setColor(Color.BLUE);
		return p;
	}
}
