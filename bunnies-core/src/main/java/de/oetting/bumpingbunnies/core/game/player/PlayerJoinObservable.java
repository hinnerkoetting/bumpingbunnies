package de.oetting.bumpingbunnies.core.game.player;

import de.oetting.bumpingbunnies.core.observer.Observable;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class PlayerJoinObservable extends Observable<Player> {

	public void playerJoined(Player p) {
		notifyAboutNewEvent(p);
	}

	public void playerLeft(Player p) {
		notifyAboutRemoveEvent(p);
	}

}
