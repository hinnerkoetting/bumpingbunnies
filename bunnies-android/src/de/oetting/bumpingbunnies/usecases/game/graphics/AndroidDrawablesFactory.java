package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import de.oetting.bumpingbunnies.R;
import de.oetting.bumpingbunnies.android.game.graphics.bitmapAltering.SimpleBitmapResizer;
import de.oetting.bumpingbunnies.core.game.graphics.CanvasDelegate;
import de.oetting.bumpingbunnies.core.game.graphics.Drawable;
import de.oetting.bumpingbunnies.core.game.graphics.DrawablesFactory;
import de.oetting.bumpingbunnies.core.game.graphics.FpsDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.RectDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.ScoreDrawer;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.GameObjectWithImage;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AndroidDrawablesFactory implements DrawablesFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidDrawablesFactory.class);
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
	public List<Drawable> createAllDrawables(CanvasDelegate canvas, int screenWidth, int screenHeight) {
		LOGGER.info("Target Size is %d:%d ", screenWidth, screenHeight);
		List<Drawable> allDrawables = new LinkedList<Drawable>();
		allDrawables.add(createBackground(screenWidth, screenHeight));
		allDrawables.addAll(createAllPlayers(canvas));
		allDrawables.addAll(createWalls(canvas));
		allDrawables.addAll(createAllScores());
		allDrawables.add(createFps());
		return allDrawables;
	}

	private Drawable createBackground(int screenWidth, int screenHeight) {
		Bitmap background = BitmapFactory.decodeResource(this.resources, R.drawable.hintergrund2);
		ImageWrapper resizedImage = new SimpleBitmapResizer().resize(new ImageWrapper(background), screenWidth, screenHeight);
		Drawable bg = new BackgroundDrawer(resizedImage, this.drawBackground);
		return bg;
	}

	private Collection<? extends Drawable> createWalls(CanvasDelegate canvas) {
		List<Drawable> allWalls = new LinkedList<Drawable>();

		for (GameObjectWithImage w : this.world.getAllObjects()) {

			int width = (int) (canvas.transformX(w.maxX()) - canvas.transformX(w.minX()));
			int height = (int) (canvas.transformY(w.minY()) - canvas.transformY(w.maxY()));
			if (width > 0 && height > 0) {
				if (w.getBitmap() != null) {
					Bitmap bitmap = (Bitmap) w.getBitmap().getBitmap();
					allWalls.add(ImageDrawerFactory.create(bitmap, w, width, height));
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

	private List<Drawable> createAllPlayers(CanvasDelegate canvas) {
		List<Drawable> players = new LinkedList<Drawable>();
		for (Player p : this.world.getAllPlayer()) {
			players.add(PlayerDrawerFactory.create(p, this.resources, canvas));
		}
		return players;
	}

	private Drawable createFps() {
		return new FpsDrawer(this.threadState);
	}

	@Override
	public Drawable createPlayerDrawable(Player p, CanvasDelegate canvas) {
		return PlayerDrawerFactory.create(p, this.resources, canvas);
	}
}
