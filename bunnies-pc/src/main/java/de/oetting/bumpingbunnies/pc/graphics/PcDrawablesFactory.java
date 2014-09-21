package de.oetting.bumpingbunnies.pc.graphics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.FpsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.RectDrawer;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.model.IcyWall;
import de.oetting.bumpingbunnies.usecases.game.model.Player;
import de.oetting.bumpingbunnies.usecases.game.model.Wall;

public class PcDrawablesFactory implements DrawablesFactory {

	private final World world;
	private final GameThreadState gameThreadState;

	public PcDrawablesFactory(World world, GameThreadState gameThreadState) {
		this.world = world;
		this.gameThreadState = gameThreadState;
	}

	@Override
	public Collection<Drawable> createAllDrawables() {
		FpsDrawer fpsDrawer = new FpsDrawer(gameThreadState);

		List<Drawable> drawables = new ArrayList<Drawable>();
		drawables.addAll( createAllWallDrawables());
		drawables.addAll( createAllIceWallDrawables());
		drawables.add(fpsDrawer);
		return drawables;
	}

	private List<RectDrawer> createAllWallDrawables() {
		List<RectDrawer> drawers = new LinkedList<RectDrawer>();
		for (Wall wall : world.getAllWalls()) {
			RectDrawer rectDrawer = new RectDrawer(wall);
			drawers.add(rectDrawer);
		}
		return drawers;
	}
	private List<RectDrawer> createAllIceWallDrawables() {
		List<RectDrawer> drawers = new LinkedList<RectDrawer>();
		for (IcyWall wall : world.getAllIcyWalls()) {
			RectDrawer rectDrawer = new RectDrawer(wall);
			drawers.add(rectDrawer);
		}
		return drawers;
	}

	@Override
	public Drawable createPlayerDrawable(Player p) {
		return null;
	}

}
