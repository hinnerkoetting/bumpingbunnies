package de.oetting.bumpingbunnies.core.game.player;

import de.oetting.bumpingbunnies.core.observer.Observable;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

public class PlayerJoinObservable extends Observable<Bunny> {

	public void playerJoined(Bunny p) {
		notifyAboutNewEvent(p);
	}

	public void playerLeft(Bunny p) {
		notifyAboutRemoveEvent(p);
	}

}
