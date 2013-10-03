package de.oetting.bumpingbunnies.usecases.game.graphics;

import java.util.LinkedList;
import java.util.List;

import android.graphics.Canvas;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.businesslogic.PlayerJoinListener;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

/**
 * draws all game elements
 * 
 */
public class Drawer implements PlayerJoinListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(Drawer.class);
	private List<Drawable> allDrawables;
	private DrawablesFactory factory;
	private CanvasDelegate canvasDelegate;
	private boolean needsUpdate;

	public Drawer(DrawablesFactory drawFactory, CanvasDelegate canvasDeleta) {
		this.factory = drawFactory;
		this.allDrawables = new LinkedList<Drawable>();
		this.canvasDelegate = canvasDeleta;
		this.needsUpdate = true;
	}

	public void buildAllDrawables() {
		this.allDrawables.clear();
		this.allDrawables.addAll(this.factory.createAllDrawables());
		LOGGER.info("Added %d drawables", this.allDrawables.size());
	}

	public void draw(Canvas canvas) {
		LOGGER.verbose("drawing...");
		update(canvas);
		drawEverything();
	}

	private void drawEverything() {
		for (Drawable d : this.allDrawables) {
			d.draw(this.canvasDelegate);
		}
	}

	private void update(Canvas canvas) {
		if (this.needsUpdate) {
			this.canvasDelegate.updateDelegate(canvas);

			for (Drawable d : this.allDrawables) {
				d.updateGraphics(this.canvasDelegate);
			}
			this.needsUpdate = false;
		}
	}

	public void setNeedsUpdate(boolean b) {
		this.needsUpdate = b;
	}

	@Override
	public void newPlayerJoined(Player p) {
		Drawable playerDrawer = this.factory.createPlayerDrawable(p);
		this.allDrawables.add(playerDrawer);
	}

	@Override
	public void playerLeftTheGame(Player p) {
		Drawable drawer = findDrawerPlayable(p);
		this.allDrawables.remove(drawer);
	}

	private Drawable findDrawerPlayable(Player p) {
		for (Drawable d : this.allDrawables) {
			if (d.drawsPlayer(p)) {
				return d;
			}
		}
		throw new PlayerDoesNotExist();
	}

	public class PlayerDoesNotExist extends RuntimeException {
	}

}
