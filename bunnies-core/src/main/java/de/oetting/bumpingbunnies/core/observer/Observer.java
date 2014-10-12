package de.oetting.bumpingbunnies.core.observer;

public interface Observer<S> {

	void newEvent(S object);

	void removeEvent(S object);
}
