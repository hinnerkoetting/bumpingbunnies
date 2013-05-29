package de.jumpnbump.usecases.game.android.input.ai;

import de.jumpnbump.usecases.game.model.Wall;

public class RunnerAiInputState {

	private Wall nextGameObject;

	public void setNextTarget(Wall w) {
		this.nextGameObject = w;
	}

	public Wall getNextTarget() {
		if (this.nextGameObject == null) {
			throw new IllegalArgumentException("No target specified");
		}
		return this.nextGameObject;
	}

	public void removeNextTarget() {
		this.nextGameObject = null;
	}

	public boolean hasTarget() {
		return this.nextGameObject != null;
	}

	public double nextTargetCenterX() {
		return (this.nextGameObject.maxX() + this.nextGameObject.minX()) / 2;
	}

	public double nextTargetCenterY() {
		return (this.nextGameObject.maxY() + this.nextGameObject.minY()) / 2;
	}
}
