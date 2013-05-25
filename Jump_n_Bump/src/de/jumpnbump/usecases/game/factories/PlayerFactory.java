package de.jumpnbump.usecases.game.factories;

import android.graphics.Color;
import de.jumpnbump.usecases.game.model.Player;

public class PlayerFactory {

	private static int GLOBAL_COUNT = 0;

	public static Player createPlayer1() {
		int id = GLOBAL_COUNT++;
		Player p = new Player(new Player(id), id);
		p.setCenterX(0.01);
		p.setCenterY(0.01);
		p.setColor(Color.RED);
		return p;
	}

	public static Player createPlayer2() {
		int id = GLOBAL_COUNT++;
		Player p = new Player(new Player(id), id);
		p.setCenterX(0.5);
		p.setCenterY(0.02);
		p.setColor(Color.BLUE);
		return p;
	}
}
