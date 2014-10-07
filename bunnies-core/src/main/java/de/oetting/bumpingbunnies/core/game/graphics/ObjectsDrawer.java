package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Player;

/**
 * draws all game elements
 * 
 */
public class ObjectsDrawer implements PlayerJoinListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectsDrawer.class);
	private List<Drawable> allDrawables;
	private List<Player> toBeUpdatedPlayers;
	private DrawablesFactory factory;
	private CanvasDelegate canvasDelegate;

	public ObjectsDrawer(DrawablesFactory drawFactory, CanvasDelegate canvasDelegate) {
		this.factory = drawFactory;
		this.canvasDelegate = canvasDelegate;
		this.allDrawables = new CopyOnWriteArrayList<Drawable>();
		this.toBeUpdatedPlayers = new ArrayList<Player>();
	}

	public void buildAllDrawables(CanvasWrapper canvas, int screenWidth, int screenHeight) {
		canvasDelegate.updateDelegate(canvas);
		this.allDrawables.clear();
		this.allDrawables.addAll(this.factory.createAllDrawables(canvasDelegate));
		LOGGER.info("Added %d drawables", this.allDrawables.size());
	}

	public void draw(CanvasWrapper canvas) {
		LOGGER.verbose("drawing...");
		if (!toBeUpdatedPlayers.isEmpty())
			updateDrawables();
		drawEverything();
	}

	private void updateDrawables() {
		synchronized (toBeUpdatedPlayers) {
			for (Player p : toBeUpdatedPlayers) {
				Drawable playerDrawer = this.factory.createPlayerDrawable(p, canvasDelegate);
				this.allDrawables.add(playerDrawer);
			}
			toBeUpdatedPlayers.clear();
		}
	}

	private void drawEverything() {
		for (Drawable d : this.allDrawables) {
			d.draw(this.canvasDelegate);
		}
	}

	@Override
	public void newPlayerJoined(Player p) {
		synchronized (toBeUpdatedPlayers) {
			toBeUpdatedPlayers.add(p);
		}
	}

	@Override
	public void playerLeftTheGame(Player p) {
		Drawable drawer = findDrawerPlayable(p);
		this.allDrawables.remove(drawer);
	}

	private Drawable findDrawerPlayable(Player p) {
		Drawable drawablefromAll = findDrawerPlayable(this.allDrawables, p);
		if (drawablefromAll != null) {
			return drawablefromAll;
		}
		throw new PlayerDoesNotExist();
	}

	private Drawable findDrawerPlayable(List<Drawable> drawables, Player search) {
		for (Drawable d : drawables) {
			if (d.drawsPlayer(search)) {
				return d;
			}
		}
		return null;
	}

	public class PlayerDoesNotExist extends RuntimeException {
	}

}
