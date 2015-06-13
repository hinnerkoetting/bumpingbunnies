package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.graphics.calculation.CoordinatesCalculation;
import de.oetting.bumpingbunnies.core.game.graphics.calculation.OneImageSize;
import de.oetting.bumpingbunnies.core.game.graphics.factory.BackgroundDrawableFactory;
import de.oetting.bumpingbunnies.core.game.graphics.factory.GameObjectDrawableFactory;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;
import de.oetting.bumpingbunnies.model.game.objects.FixedWorldObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.ModelConstants;
import de.oetting.bumpingbunnies.model.game.world.World;
import de.oetting.bumpingbunnies.model.game.world.ZIndexComparator;

public class DrawablesFactory {

	private final GameThreadState gameThreadState;
	private final World world;
	private final BackgroundDrawableFactory backgroundDrawableFactory;
	private final GameObjectDrawableFactory gameObjectDrawableFactory;
	private final BunnyDrawerFactory playerDrawableFactory;
	private final DrawableToImageConverter onImageDrawer;
	private final boolean convertAllStaticObjectsToOneImage;
	private final CoordinatesCalculation coordinatesCalculation;
	private boolean withScores;

	public DrawablesFactory(GameThreadState gameThreadState, World world,
			BackgroundDrawableFactory backgroundDrawableFactory, GameObjectDrawableFactory gameObjectDrawableFactory,
			BunnyDrawerFactory playerDrawableFactory, DrawableToImageConverter onImageDrawer,
			boolean convertAllStaticObjectsToOneImage, boolean withScores, CoordinatesCalculation coordinatesCalculation) {
		this.gameThreadState = gameThreadState;
		this.world = world;
		this.backgroundDrawableFactory = backgroundDrawableFactory;
		this.gameObjectDrawableFactory = gameObjectDrawableFactory;
		this.playerDrawableFactory = playerDrawableFactory;
		this.onImageDrawer = onImageDrawer;
		this.convertAllStaticObjectsToOneImage = convertAllStaticObjectsToOneImage;
		this.withScores = withScores;
		this.coordinatesCalculation = coordinatesCalculation;
		if (onImageDrawer == null) {
			throw new IllegalArgumentException();
		}
	}

	public Collection<Drawable> createAllDrawables(CanvasAdapter canvas) {

		List<Drawable> drawables = new ArrayList<Drawable>();
		drawables.addAll(createStaticObjects(canvas));
		drawables.addAll(createAllPlayer(canvas));
		if (withScores)
			drawables.addAll(createAllScores());
		drawables.add(new FpsDrawer(gameThreadState));
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
		return new ScoreDrawer(p, world);
	}

	private List<Drawable> createStaticObjects(CanvasAdapter canvas) {
		List<FixedWorldObject> allStaticObjects = createAlleStaticObjects();
		Collections.sort(allStaticObjects, new ZIndexComparator());
		List<Drawable> staticDrawables = new ArrayList<Drawable>();
		staticDrawables.add(createBackground(canvas));
		staticDrawables.addAll(createAllStaticObjectsDrawables(allStaticObjects, canvas));
		if (convertAllStaticObjectsToOneImage)
			return convertToOneDrawer(staticDrawables, canvas);
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
		if (convertAllStaticObjectsToOneImage) {
			int bitmapWidth = OneImageSize.computeWidthForOneImage(coordinatesCalculation, canvas);
			int bitmapHeight = OneImageSize.computeHeightForOneImage(coordinatesCalculation, canvas);
			return backgroundDrawableFactory.create(bitmapWidth, bitmapHeight);
		} else
			return backgroundDrawableFactory.create(canvas.getOriginalWidth(), canvas.getOriginalHeight());
	}

	private List<Drawable> convertToOneDrawer(List<Drawable> objects, CanvasAdapter canvas) {
		List<Drawable> drawables = new ArrayList<Drawable>();
		if (canvas.getOriginalWidth() > OneImageSize.computeWidthForOneImage(coordinatesCalculation, canvas) ||  canvas.getOriginalHeight()>  OneImageSize.computeHeightForOneImage(coordinatesCalculation, canvas) ) {
			//if the screen is bigger than the game we need to clean to background outside of the game
			drawables.add(new ClearBackgroundDrawer()); 
		}
		drawables.add(new AllDrawablesFactory(onImageDrawer).createImagesWhichContainsAllElements(objects));
			
		return drawables;
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
		int width = (int) (canvas.transformX(ModelConstants.BUNNY_DRAWN_WIDTH) - canvas.transformX(0));
		int height = (int) (canvas.transformY(0) - canvas.transformY(ModelConstants.BUNNY_DRAWN_HEIGHT));
		return playerDrawableFactory.create(width, height, p);
	}

	public boolean withScores() {
		return withScores;
	}

}
