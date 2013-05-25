package de.jumpnbump.usecases.game.factories;

import android.graphics.Color;
import de.jumpnbump.usecases.game.model.Player;

public class PlayerFactory {

	public static Player createPlayer1() {
		Player p = new Player();
		p.setCenterX(0.01);
		p.setCenterY(0.01);
		p.setColor(Color.RED);
		return p;
	}

	public static Player createPlayer2() {
		Player p = new Player();
		p.setCenterX(0.02);
		p.setCenterY(0.02);
		p.setColor(Color.BLUE);
		return p;
	}
}
