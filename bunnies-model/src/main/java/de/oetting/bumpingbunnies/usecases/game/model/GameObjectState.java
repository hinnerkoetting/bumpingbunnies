package de.oetting.bumpingbunnies.usecases.game.model;

public interface GameObjectState<S> {

	void copyContentTo(S other);
}
