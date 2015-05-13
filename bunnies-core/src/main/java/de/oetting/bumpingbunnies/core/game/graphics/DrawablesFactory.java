package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.graphics.factory.BackgroundDrawableFactory;
import de.oetting.bumpingbunnies.core.game.graphics.factory.GameObjectDrawableFactory;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.FixedWorldObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;

public class DrawablesFactory {

	private final GameThreadState gameThreadState;
	private final World world;
	private final BackgroundDrawableFactory backgroundDrawableFactory;
	private final GameObjectDrawableFactory gameObjectDrawableFactory;
	private final BunnyDrawableFactory playerDrawableFactory;
	private final DrawableToImageConverter onImageDrawer;
	private final boolean convertAllStaticObjectsToOneImage;

	public DrawablesFactory(GameThreadState gameThreadState, World world,
			BackgroundDrawableFactory backgroundDrawableFactory, GameObjectDrawableFactory gameObjectDrawableFactory,
			BunnyDrawableFactory playerDrawableFactory, DrawableToImageConverter onImageDrawer,
			boolean convertAllStaticObjectsToOneImage) {
		this.gameThreadState = gameThreadState;
		this.world = world;
		this.backgroundDrawableFactory = backgroundDrawableFactory;
		this.gameObjectDrawableFactory = gameObjectDrawableFactory;
		this.playerDrawableFactory = playerDrawableFactory;
		this.onImageDrawer = onImageDrawer;
		this.convertAllStaticObjectsToOneImage = convertAllStaticObjectsToOneImage;
		if (onImageDrawer == null) {
			throw new IllegalArgumentException();
		}
	}

	public Collection<Drawable> createAllDrawables(CanvasAdapter canvas) {

		List<Drawable> drawables = new ArrayList<Drawable>();
		drawables.addAll(createStaticObjects(canvas));
		drawables.addAll(createAllPlayer(canvas));
		drawables.addAll(createAllScores());
//		drawables.add(new FpsDrawer(gameThreadState));
		return drawables;
	}

	private Collection<? extends Drawable> createAllPlayer(CanvasAdapter canvas) {
		List<Drawable> players = new LinkedList<Drawable>();
		for (Bunny player : this.world.getAllConnectedBunnies()) {
			players.add(createPlayerDrawable(player, canvas));
		}
		return players;
	}

	private Collection<? extends Drawable> createAllScores() {
		List<Drawable> scores = new LinkedList<Drawable>();
		for (Bunny p : this.world.getAllConnectedBunnies()) {
			scores.add(createScoreDrawer(p));
		}
		return scores;
	}

	public ScoreDrawer createScoreDrawer(Bunny p) {
		double x = 0.1 + (p.id()) * 0.2;
		double y = 0.05;
		while (x > 1) {
			x--;
			y += 0.1;
		}
		return new ScoreDrawer(p, x, y);
	}

	private List<Drawable> createStaticObjects(CanvasAdapter canvas) {
		List<FixedWorldObject> allStaticObjects = createAlleStaticObjects();
		Collections.sort(allStaticObjects, new ZIndexComparator());
		List<Drawable> staticDrawables = new ArrayList<Drawable>();
		staticDrawables.add(createBackground(canvas));
		staticDrawables.addAll(createAllStaticObjectsDrawables(allStaticObjects, canvas));
		if (convertAllStaticObjectsToOneImage)
			return convertToOneDrawer(staticDrawables);
		else
			return staticDrawables;
	}

	private List<FixedWorldObject> createAlleStaticObjects() {
		List<FixedWorldObject> list = new ArrayList<FixedWorldObject>();
		list.addAll(world.getAllWalls());
		list.addAll(world.getAllIcyWalls());
		list.addAll(world.getAllJumper());
		list.addAll(world.getAllWaters());
		list.addAll(world.getBackgrounds());
		return list;
	}

	private Drawable createBackground(CanvasAdapter canvas) {
		return backgroundDrawableFactory.create(canvas.getOriginalWidth(), canvas.getOriginalHeight());
	}

	private List<Drawable> convertToOneDrawer(List<Drawable> objects) {
		return Collections.singletonList(new AllDrawablesFactory(onImageDrawer)
				.createImagesWhichContainsAllElements(objects));
	}

	private List<Drawable> createAllStaticObjectsDrawables(List<? extends GameObjectWithImage> objects,
			CanvasAdapter canvas) {
		List<Drawable> drawers = new LinkedList<Drawable>();
		for (GameObjectWithImage p : objects) {
			int width = (int) (canvas.transformX(p.maxX()) - canvas.transformX(p.minX()));
			int height = (int) (canvas.transformY(p.minY()) - canvas.transformY(p.maxY()));
			drawers.add(gameObjectDrawableFactory.create(p, width, height));
		}
		return drawers;
	}

	public Drawable createPlayerDrawable(Bunny p, CanvasAdapter canvas) {
		int width = (int) (canvas.transformX(p.maxX()) - canvas.transformX(p.minX()));
		int height = (int) (canvas.transformY(p.minY()) - canvas.transformY(p.maxY()));
		return playerDrawableFactory.create(p, width, height);
	}

}
