package de.oetting.bumpingbunnies.core.game.graphics;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import de.oetting.bumpingbunnies.core.game.steps.PlayerJoinListener;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.model.game.objects.Bunny;

/**
 * draws all game elements
 * 
 */
public class ObjectsDrawer implements PlayerJoinListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(ObjectsDrawer.class);
	private List<Drawable> allDrawables;
	private List<Bunny> toBeUpdatedPlayers;
	private DrawablesFactory factory;
	private CanvasAdapter canvasDelegate;

	public ObjectsDrawer(DrawablesFactory drawFactory, CanvasAdapter canvasDelegate) {
		this.factory = drawFactory;
		this.canvasDelegate = canvasDelegate;
		this.allDrawables = new CopyOnWriteArrayList<Drawable>();
		this.toBeUpdatedPlayers = new ArrayList<Bunny>();
	}

	public void buildAllDrawables(CanvasWrapper canvas, int screenWidth, int screenHeight) {
		canvasDelegate.updateDelegate(canvas);
		canvasDelegate.startDrawPhase();
		this.allDrawables.clear();
		this.allDrawables.addAll(this.factory.createAllDrawables(canvasDelegate));
		LOGGER.info("Added %d drawables", this.allDrawables.size());
		canvasDelegate.endDrawPhase();
	}

	public void draw(CanvasWrapper canvas) {
		LOGGER.verbose("drawing...");
		if (!toBeUpdatedPlayers.isEmpty()) 
			updateDrawables();
		drawEverything();
	}

	private void updateDrawables() {
		canvasDelegate.startDrawPhase();
		synchronized (toBeUpdatedPlayers) {
			for (Bunny p : toBeUpdatedPlayers) {
				Drawable playerDrawer = this.factory.createPlayerDrawable(p, canvasDelegate);
				this.allDrawables.add(playerDrawer);
				if(factory.withScores())
					this.allDrawables.add(factory.createScoreDrawer(p));
			}
			toBeUpdatedPlayers.clear();
		}
		canvasDelegate.endDrawPhase();
	}

	private void drawEverything() {
		canvasDelegate.startDrawPhase();
		for (Drawable d : this.allDrawables) {
			d.draw(this.canvasDelegate);
		}
		canvasDelegate.endDrawPhase();
	}

	@Override
	public void newEvent(Bunny p) {
		synchronized (toBeUpdatedPlayers) {
			toBeUpdatedPlayers.add(p);
		}
	}

	@Override
	public void removeEvent(Bunny p) {
		if (toBeUpdatedPlayers.contains(p)) {
			toBeUpdatedPlayers.remove(p);
		} else {
			List<Drawable> drawers = findDrawerForPlayer(p);
			this.allDrawables.removeAll(drawers);
		}
	}

	private List<Drawable> findDrawerForPlayer(Bunny p) {
		List<Drawable> allDrawablesForPlayer = new ArrayList<Drawable>();
		for (Drawable d : allDrawables) {
			if (d.drawsPlayer(p)) {
				allDrawablesForPlayer.add(d);
			}
		}
		return allDrawablesForPlayer;
	}

}
