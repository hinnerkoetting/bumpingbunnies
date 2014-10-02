package de.oetting.bumpingbunnies.core.game.player;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class PlayerJoinObservable {

	private final List<PlayerJoinListener> listeners;

	public PlayerJoinObservable() {
		super();
		this.listeners = new LinkedList<PlayerJoinListener>();
	}

	public void playerJoined(Player p) {
		notifyAboutJoin(p);
	}

	private void notifyAboutJoin(Player p) {
		for (PlayerJoinListener l : this.listeners) {
			l.newPlayerJoined(p);
		}
	}

	public void playerLeft(Player p) {
		for (PlayerJoinListener l : this.listeners) {
			l.playerLeftTheGame(p);
		}
	}

	public void addListener(PlayerJoinListener listener) {
		if (listener == null)
			throw new NullPointerException();
		this.listeners.add(listener);
	}
}
