package de.oetting.bumpingbunnies.core.observer;

import java.util.LinkedList;
import java.util.List;

public class Observable<S> {

	private final List<Observer<S>> listeners;

	public Observable() {
		this.listeners = new LinkedList<Observer<S>>();
	}

	protected void notifyAboutNewEvent(S object) {
		for (Observer<S> listener : this.listeners) {
			listener.newEvent(object);
		}
	}

	protected void notifyAboutRemoveEvent(S object) {
		for (Observer<S> listener : this.listeners) {
			listener.removeEvent(object);
		}
	}

	public void addListener(Observer<S> listener) {
		if (listener == null)
			throw new NullPointerException();
		this.listeners.add(listener);
	}

	public void removeListeners() {
		listeners.clear();
	}
}
