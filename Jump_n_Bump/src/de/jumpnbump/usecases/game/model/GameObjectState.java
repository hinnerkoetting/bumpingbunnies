package de.jumpnbump.usecases.game.model;

public interface GameObjectState<S> {

	void copyContentTo(S other);
}
