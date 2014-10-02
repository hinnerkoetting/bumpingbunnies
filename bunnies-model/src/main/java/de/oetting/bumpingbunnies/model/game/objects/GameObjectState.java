package de.oetting.bumpingbunnies.model.game.objects;

public interface GameObjectState<S> {

	void copyContentTo(S other);
}
