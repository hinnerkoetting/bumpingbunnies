package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.graphics.factory.BackgroundDrawableFactory;
import de.oetting.bumpingbunnies.core.game.graphics.factory.GameObjectDrawableFactory;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class DefaultDrawablesFactory implements DrawablesFactory {

	private final GameThreadState gameThreadState;
	private final World world;
	private final BackgroundDrawableFactory backgroundDrawableFactory;
	private final GameObjectDrawableFactory gameObjectDrawableFactory;
	private final PlayerDrawableFactory playerDrawableFactory;

	public DefaultDrawablesFactory(GameThreadState gameThreadState, World world, BackgroundDrawableFactory backgroundDrawableFactory,
			GameObjectDrawableFactory gameObjectDrawableFactory, PlayerDrawableFactory playerDrawableFactory) {
		this.gameThreadState = gameThreadState;
		this.world = world;
		this.backgroundDrawableFactory = backgroundDrawableFactory;
		this.gameObjectDrawableFactory = gameObjectDrawableFactory;
		this.playerDrawableFactory = playerDrawableFactory;
	}

	@Override
	public Collection<Drawable> createAllDrawables(CanvasDelegate canvas, int screenWidth, int screenHeight) {

		List<Drawable> drawables = new ArrayList<Drawable>();
		drawables.add(createBackground(screenWidth, screenHeight));
		drawables.addAll(createAllPlayer(canvas));
		drawables.addAll(createStaticObjects(screenWidth, screenHeight));
		drawables.addAll(createAllScores());
		drawables.add(new FpsDrawer(gameThreadState));
		return drawables;
	}

	private Collection<? extends Drawable> createAllPlayer(CanvasDelegate canvas) {
		List<Drawable> players = new LinkedList<Drawable>();
		for (Player p : this.world.getAllPlayer()) {
			players.add(playerDrawableFactory.create(p, canvas));
		}
		return players;
	}

	private Collection<? extends Drawable> createAllScores() {
		double currentX = 0.2;
		double currentY = 0.05;
		List<Drawable> scores = new LinkedList<Drawable>();
		for (Player p : this.world.getAllPlayer()) {
			scores.add(new ScoreDrawer(p, currentX, currentY));
			currentX += 0.2;
			if (currentX > 1) {
				currentY += 0.1;
				currentX = 0.2;
			}
		}
		return scores;
	}

	private List<Drawable> createStaticObjects(int screenWidth, int screenHeight) {
		List<Drawable> drawables = new ArrayList<Drawable>();
		drawables.addAll(createAllDrawables(world.getAllWalls(), screenWidth, screenHeight));
		drawables.addAll(createAllDrawables(world.getAllIcyWalls(), screenWidth, screenHeight));
		drawables.addAll(createAllDrawables(world.getAllJumper(), screenWidth, screenHeight));
		drawables.addAll(createAllDrawables(world.getAllWaters(), screenWidth, screenHeight));
		return drawables;
	}

	private Drawable createBackground(int targetWidth, int targetHeight) {
		return backgroundDrawableFactory.create(targetWidth, targetHeight);
	}

	private List<Drawable> createAllDrawables(List<? extends GameObject> objects, int screenWidth, int screenHeight) {
		List<Drawable> drawers = new LinkedList<Drawable>();
		for (GameObject object : objects) {
			drawers.add(gameObjectDrawableFactory.create(object, screenWidth, screenHeight));
		}
		return drawers;
	}

	@Override
	public Drawable createPlayerDrawable(Player p, CanvasDelegate canvas) {
		throw new IllegalArgumentException("TODO");
	}

}
