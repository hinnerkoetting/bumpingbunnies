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
	public Collection<Drawable> createAllDrawables(CanvasDelegate canvas) {

		List<Drawable> drawables = new ArrayList<Drawable>();
		drawables.add(createBackground(canvas));
		drawables.addAll(createAllPlayer(canvas));
		drawables.addAll(createStaticObjects(canvas));
		drawables.addAll(createAllScores());
		drawables.add(new FpsDrawer(gameThreadState));
		return drawables;
	}

	private Collection<? extends Drawable> createAllPlayer(CanvasDelegate canvas) {
		List<Drawable> players = new LinkedList<Drawable>();
		for (Player player : this.world.getAllPlayer()) {
			players.add(createPlayerDrawable(player, canvas));
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

	private List<Drawable> createStaticObjects(CanvasDelegate canvas) {
		List<Drawable> drawables = new ArrayList<Drawable>();
		drawables.addAll(createAllDrawables(world.getAllWalls(), canvas));
		drawables.addAll(createAllDrawables(world.getAllIcyWalls(), canvas));
		drawables.addAll(createAllDrawables(world.getAllJumper(), canvas));
		drawables.addAll(createAllDrawables(world.getAllWaters(), canvas));
		return drawables;
	}

	private Drawable createBackground(CanvasDelegate canvas) {
		return backgroundDrawableFactory.create(canvas.getOriginalWidth(), canvas.getOriginalHeight());
	}

	private List<Drawable> createAllDrawables(List<? extends GameObject> objects, CanvasDelegate canvas) {
		List<Drawable> drawers = new LinkedList<Drawable>();
		for (GameObject p : objects) {
			int width = (int) (canvas.transformX(p.maxX()) - canvas.transformX(p.minX()));
			int height = (int) (canvas.transformY(p.minY()) - canvas.transformY(p.maxY()));
			drawers.add(gameObjectDrawableFactory.create(p, width, height));
		}
		return drawers;
	}

	@Override
	public Drawable createPlayerDrawable(Player p, CanvasDelegate canvas) {
		int width = (int) (canvas.transformX(p.maxX()) - canvas.transformX(p.minX()));
		int height = (int) (canvas.transformY(p.minY()) - canvas.transformY(p.maxY()));
		return playerDrawableFactory.create(p, width, height);
	}

}
