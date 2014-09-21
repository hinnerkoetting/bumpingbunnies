package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering.SimpleBitmapResizer;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.FpsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.RectDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.ScoreDrawer;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.usecases.game.model.GameObjectWithImage;
import de.oetting.bumpingbunnies.usecases.game.model.ModelConstants;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AndroidDrawablesFactory implements DrawablesFactory {

	private static final int MIN_SIZE_FOR_DRAWER = ModelConstants.MAX_VALUE / 100000;
	private final World world;
	private final GameThreadState threadState;
	private final Resources resources;
	private boolean drawBackground;

	public AndroidDrawablesFactory(World world, GameThreadState threadState, Resources resources, boolean drawBackground) {
		this.world = world;
		this.threadState = threadState;
		this.resources = resources;
		this.drawBackground = drawBackground;
	}

	@Override
	public List<Drawable> createAllDrawables() {
		List<Drawable> allDrawables = new LinkedList<Drawable>();
		allDrawables.add(createBackground());
		allDrawables.addAll(createAllPlayers());
		allDrawables.addAll(createWalls());
		allDrawables.addAll(createAllScores());
		allDrawables.add(createFps());
		return allDrawables;
	}

	private Drawable createBackground() {
		Bitmap background = BitmapFactory.decodeResource(this.resources, R.drawable.hintergrund2);
		Drawable bg = new BackgroundDrawer(new AndroidImage(background), new SimpleBitmapResizer(), this.drawBackground);
		return bg;
	}

	private Collection<? extends Drawable> createWalls() {
		List<Drawable> allWalls = new LinkedList<Drawable>();

		for (GameObjectWithImage w : this.world.getAllObjects()) {

			// TODO rework
			if (w.maxX() - w.minX() > MIN_SIZE_FOR_DRAWER && w.maxY() - w.minY() > MIN_SIZE_FOR_DRAWER) {
				AndroidImage wrapper = (AndroidImage) w.getBitmap();
				if (wrapper != null && wrapper.getBitmap() != null) {
					Bitmap bitmap = wrapper.getBitmap();
					allWalls.add(ImageDrawerFactory.create(bitmap, w));
				} else {
					allWalls.add(new RectDrawer(w));
				}
			}
		}
		return allWalls;
	}

	private List<Drawable> createAllScores() {
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

	@Override
	public Drawable createPlayerDrawable(Player p) {
		return PlayerDrawerFactory.create(p, this.resources);
	}
}
