package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

/**
 * draws all game elements
 * 
 */
public class ObjectsDrawer implements PlayerJoinListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectsDrawer.class);
	private List<Drawable> allDrawables;
	private DrawablesFactory factory;
	private CanvasDelegate canvasDelegate;
	private List<Drawable> drawablesWhichNeedToBeUpdated;

	public ObjectsDrawer(DrawablesFactory drawFactory, CanvasDelegate canvasDelegate) {
		this.factory = drawFactory;
		this.canvasDelegate = canvasDelegate;
		this.allDrawables = new CopyOnWriteArrayList<Drawable>();
		this.drawablesWhichNeedToBeUpdated = new CopyOnWriteArrayList<Drawable>();
	}

	public void buildAllDrawables(CanvasWrapper canvas, int screenWidth, int screenHeight) {
		this.allDrawables.clear();
		this.allDrawables.addAll(this.factory.createAllDrawables(canvasDelegate, screenWidth, screenHeight));
		this.drawablesWhichNeedToBeUpdated.clear();
		// this.drawablesWhichNeedToBeUpdated.addAll(this.factory.createAllDrawables(screenWidth,
		// screenHeight));
		LOGGER.info("Added %d drawables", this.allDrawables.size());
		canvasDelegate.updateDelegate(canvas);
	}

	public void draw(CanvasWrapper canvas) {
		LOGGER.verbose("drawing...");
		// update(canvas);
		drawEverything();
	}

	private void drawEverything() {
		for (Drawable d : this.allDrawables) {
			d.draw(this.canvasDelegate);
		}
	}

	private void update(CanvasWrapper canvas) {
		updateDrawables();
	}

	private void updateDrawables() {
		for (Drawable d : this.drawablesWhichNeedToBeUpdated) {
			d.updateGraphics(this.canvasDelegate);
		}
		this.allDrawables.addAll(this.drawablesWhichNeedToBeUpdated);
		this.drawablesWhichNeedToBeUpdated.clear();
	}

	@Override
	public void newPlayerJoined(Player p) {
		Drawable playerDrawer = this.factory.createPlayerDrawable(p, canvasDelegate);
		this.drawablesWhichNeedToBeUpdated.add(playerDrawer);
	}

	@Override
	public void playerLeftTheGame(Player p) {
		Drawable drawer = findDrawerPlayable(p);
		this.allDrawables.remove(drawer);
		this.drawablesWhichNeedToBeUpdated.remove(drawer);
	}

	private Drawable findDrawerPlayable(Player p) {
		Drawable drawablefromAll = findDrawerPlayable(this.allDrawables, p);
		if (drawablefromAll != null) {
			return drawablefromAll;
		} else {
			Drawable fromNeedsToBeUpdates = findDrawerPlayable(this.drawablesWhichNeedToBeUpdated, p);
			if (fromNeedsToBeUpdates != null) {
				return fromNeedsToBeUpdates;
			}
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
