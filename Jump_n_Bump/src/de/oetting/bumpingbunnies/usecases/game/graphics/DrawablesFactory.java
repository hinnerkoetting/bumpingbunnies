package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.content.res.Resources;
import de.oetting.bumpingbunnies.usecases.game.model.FixedWorldObject;
import de.oetting.bumpingbunnies.usecases.game.model.GameThreadState;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.World;

public class DrawablesFactory {

	private final World world;
	private final GameThreadState threadState;
	private final Resources resources;

	public DrawablesFactory(World world, GameThreadState threadState,
			Resources resources) {
		this.world = world;
		this.threadState = threadState;
		this.resources = resources;
	}

	public List<Drawable> createAllDrawables() {
		List<Drawable> allDrawables = new LinkedList<Drawable>();
		allDrawables.addAll(createAllPlayers());
		allDrawables.addAll(createWalls());
		allDrawables.addAll(createAllScores());
		allDrawables.add(createFps());
		return allDrawables;
	}

	private Collection<? extends Drawable> createWalls() {
		List<RectDrawer> allWalls = new LinkedList<RectDrawer>();
		for (FixedWorldObject w : this.world.getAllWalls()) {
			allWalls.add(new RectDrawer(w));
		}
		return allWalls;
	}

	private List<Drawable> createAllScores() {
		double currentX = 0.2;
		double y = 0.05;
		List<Drawable> scores = new LinkedList<Drawable>();
		for (Player p : this.world.getAllPlayer()) {
			scores.add(new ScoreDrawer(p, currentX, y));
			currentX += 0.2;
		}
		return scores;
	}

	private List<Drawable> createAllPlayers() {
		List<Drawable> players = new LinkedList<Drawable>();
		for (Player p : this.world.getAllPlayer()) {
			players.add(PlayerDrawerFactory.create(p, this.resources));
		}
		return players;
	}

	private Drawable createFps() {
		return new FpsDrawer(this.threadState);
	}
}
