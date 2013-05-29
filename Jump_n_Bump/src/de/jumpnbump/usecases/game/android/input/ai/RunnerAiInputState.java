package de.jumpnbump.usecases.game.android.input.ai;

import java.util.LinkedList;
import java.util.Queue;

import de.jumpnbump.usecases.game.model.Coordinate;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.Wall;

public class RunnerAiInputState {

	private Queue<Coordinate> coordinates;

	public RunnerAiInputState() {
		this.coordinates = new LinkedList<Coordinate>();
	}

	public void setNextTarget(Wall w, Player p) {
		if (p.getCenterX() > w.maxX()) {
			this.coordinates.add(new Coordinate(w.maxX() - 0.01,
					w.centerY() + 0.1));
		} else {
			this.coordinates.add(new Coordinate(w.minX() + 0.01,
					w.centerY() + 0.1));
		}
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
