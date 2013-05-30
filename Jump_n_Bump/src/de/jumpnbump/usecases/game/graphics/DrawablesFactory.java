package de.jumpnbump.usecases.game.graphics;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.jumpnbump.usecases.game.model.GameThreadState;
import de.jumpnbump.usecases.game.model.ModelConstants;
import de.jumpnbump.usecases.game.model.Player;
import de.jumpnbump.usecases.game.model.Wall;
import de.jumpnbump.usecases.game.model.World;

public class DrawablesFactory {

	private final World world;
	private final GameThreadState threadState;

	public DrawablesFactory(World world, GameThreadState threadState) {
		this.world = world;
		this.threadState = threadState;
	}

	public List<Drawable> createAllDrawables() {
		List<Drawable> allDrawables = new LinkedList<Drawable>();
		allDrawables.addAll(createAllScores());
		allDrawables.addAll(createAllPlayers());
		allDrawables.add(createFps());
		allDrawables.addAll(createWalls());
		return allDrawables;
	}

	private Collection<? extends Drawable> createWalls() {
		List<RectDrawer> allWalls = new LinkedList<RectDrawer>();
		for (Wall w : this.world.getAllWalls()) {
			allWalls.add(new RectDrawer(w));
		}
		return allWalls;
	}

	private List<Drawable> createAllScores() {
		int currentX = (int) (0.3 * ModelConstants.MAX_VALUE);
		int y = (int) (0.95 * ModelConstants.MAX_VALUE);
		List<Drawable> scores = new LinkedList<Drawable>();
		for (Player p : this.world.getAllPlayer()) {
			scores.add(new ScoreDrawer(p, currentX, y));
			currentX += 0.1 * ModelConstants.MAX_VALUE;
		}
		return scores;
	}

	private List<Drawable> createAllPlayers() {
		List<Drawable> players = new LinkedList<Drawable>();
		for (Player p : this.world.getAllPlayer()) {
			players.add(new RectDrawer(p));
		}
		return players;
	}

	private Drawable createFps() {
		return new FpsDrawer(this.threadState);
	}
}
