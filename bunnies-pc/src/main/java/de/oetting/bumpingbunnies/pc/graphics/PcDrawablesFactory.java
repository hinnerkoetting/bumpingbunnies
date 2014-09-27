package de.oetting.bumpingbunnies.pc.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import javafx.scene.image.Image;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.FpsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.ImageDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.RectDrawer;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.model.GameObject;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PcDrawablesFactory implements DrawablesFactory {

	private final World world;
	private final GameThreadState gameThreadState;

	public PcDrawablesFactory(World world, GameThreadState gameThreadState) {
		this.world = world;
		this.gameThreadState = gameThreadState;
	}

	@Override
	public Collection<Drawable> createAllDrawables(int screenWidth, int screenHeight) {
		FpsDrawer fpsDrawer = new FpsDrawer(gameThreadState);

		List<Drawable> drawables = new ArrayList<Drawable>();
		drawables.add(createBackground(screenWidth, screenHeight));
		drawables.addAll(createAllDrawables(world.getAllWalls()));
		drawables.addAll(createAllDrawables(world.getAllIcyWalls()));
		drawables.addAll(createAllDrawables(world.getAllJumper()));
		drawables.addAll(createAllDrawables(world.getAllWaters()));
		drawables.add(fpsDrawer);
		return drawables;
	}

	private Drawable createBackground(int targetWidth, int targetHeight) {
		Image image = new Image(new BackgroundReader().readBackground(), targetWidth, targetHeight, false, true);
		return new ImageDrawer(new ImageWrapper(image));
	}

	private List<RectDrawer> createAllDrawables(List<? extends GameObject> objects) {
		List<RectDrawer> drawers = new LinkedList<RectDrawer>();
		for (GameObject object : objects) {
			RectDrawer rectDrawer = new RectDrawer(object);
			drawers.add(rectDrawer);
		}
		return drawers;
	}

	@Override
	public Drawable createPlayerDrawable(Player p) {
		return null;
	}

}
