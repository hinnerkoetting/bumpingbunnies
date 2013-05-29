package de.jumpnbump.usecases.game.android.input.ai;

import java.util.LinkedList;
import java.util.Queue;

import de.jumpnbump.usecases.game.model.Coordinate;
import de.jumpnbump.usecases.game.model.Wall;

public class RunnerAiInputState {

	private Queue<Coordinate> coordinates;

	public RunnerAiInputState() {
		this.coordinates = new LinkedList<Coordinate>();
	}

	public void setNextTarget(Wall w) {
		this.coordinates.add(new Coordinate(w.maxX(), w.centerY()));
		this.coordinates.add(new Coordinate(w.centerX(), w.centerY()));
	}

	public Coordinate getNextTarget() {
		if (this.coordinates.isEmpty()) {
			throw new IllegalArgumentException("No target specified");
		}
		return this.coordinates.peek();
	}

	public void removeNextTarget() {
		this.coordinates.poll();
	}

	public boolean hasTarget() {
		return !this.coordinates.isEmpty();
	}

	public double nextTargetCenterX() {
		return this.coordinates.peek().getX();
	}

	public double nextTargetCenterY() {
		return this.coordinates.peek().getY();
	}
}
