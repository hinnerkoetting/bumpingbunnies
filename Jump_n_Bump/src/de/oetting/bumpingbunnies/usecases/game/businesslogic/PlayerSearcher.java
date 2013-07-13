package de.oetting.bumpingbunnies.usecases.game.businesslogic;

import java.util.List;

import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PlayerSearcher {

	public static Player findPlayer(List<Player> players, int id) {
		for (Player p : players) {
			if (p.id() == id) {
				return p;
			}
		}
		throw new IllegalStateException("Could not find player with id " + id);
	}
}
