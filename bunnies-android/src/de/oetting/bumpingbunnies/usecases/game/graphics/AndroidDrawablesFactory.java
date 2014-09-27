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
import de.oetting.bumpingbunnies.core.game.graphics.PlayerDrawerFactory;
import de.oetting.bumpingbunnies.core.game.graphics.PlayerImagesReader;
import de.oetting.bumpingbunnies.core.game.graphics.RectDrawer;
import de.oetting.bumpingbunnies.core.game.graphics.ScoreDrawer;
import de.oetting.bumpingbunnies.core.game.main.GameThreadState;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.AndroidPlayerImagesProvier;
import de.oetting.bumpingbunnies.usecases.game.model.GameObjectWithImage;
import de.oetting.bumpingbunnies.usecases.game.model.ImageWrapper;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class AndroidDrawablesFactory implements DrawablesFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(AndroidDrawablesFactory.class);
	private final World world;
	private final GameThreadState threadState;
	private final Resources resources;
	private final PlayerDrawerFactory playerFactory;
	private boolean drawBackground;

	public AndroidDrawablesFactory(World world, GameThreadState threadState, Resources resources, boolean drawBackground) {
		this.world = world;
		this.threadState = threadState;
		this.resources = resources;
		this.drawBackground = drawBackground;
		this.playerFactory = new PlayerDrawerFactory(new AndroidPlayerImagesProvier(new PlayerImagesReader()), new AndroidImagesColoror(),
				new AndroidImagesMirrorer());
	}

	@Override
	public List<Drawable> createAllDrawables(CanvasDelegate canvas) {
		List<Drawable> allDrawables = new LinkedList<Drawable>();
		allDrawables.add(createBackground(canvas));
		allDrawables.addAll(createAllPlayers(canvas));
		allDrawables.addAll(createStaticObjects(canvas));
		allDrawables.addAll(createAllScores());
		allDrawables.add(createFps());
		return allDrawables;
	}

	private Drawable createBackground(CanvasDelegate canvas) {
		Bitmap background = BitmapFactory.decodeResource(this.resources, R.drawable.hintergrund2);
		Bitmap resizedImage = new SimpleBitmapResizer().resize(background, canvas.getOriginalWidth(), canvas.getOriginalHeight());
		Drawable bg = new BackgroundDrawer(new ImageWrapper(resizedImage), this.drawBackground);
		return bg;
	}

	private Collection<? extends Drawable> createStaticObjects(CanvasDelegate canvas) {
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
			int width = (int) (canvas.transformX(p.maxX()) - canvas.transformX(p.minX()));
			int height = (int) (canvas.transformY(p.minY()) - canvas.transformY(p.maxY()));
			players.add(playerFactory.create(width, height, p));
		}
		return players;
	}

	private Drawable createFps() {
		return new FpsDrawer(this.threadState);
	}

	@Override
	public Drawable createPlayerDrawable(Player p, CanvasDelegate canvas) {
		int width = (int) (canvas.transformX(p.maxX()) - canvas.transformX(p.minX()));
		int height = (int) (canvas.transformY(p.minY()) - canvas.transformY(p.maxY()));
		return playerFactory.create(width, height, p);
	}
}
