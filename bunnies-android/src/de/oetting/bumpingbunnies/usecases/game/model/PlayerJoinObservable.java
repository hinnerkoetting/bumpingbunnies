package de.oetting.bumpingbunnies.usecases.game.model;

import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;

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
		this.listeners.add(listener);
	}
}
