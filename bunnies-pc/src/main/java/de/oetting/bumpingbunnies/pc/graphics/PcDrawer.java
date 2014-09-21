package de.oetting.bumpingbunnies.pc.graphics;

import javafx.scene.canvas.Canvas;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.logger.Logger;
import de.oetting.bumpingbunnies.logger.LoggerFactory;
import de.oetting.bumpingbunnies.usecases.game.model.Player;

public class PcDrawer implements Drawer {

	private static final Logger LOGGER = LoggerFactory.getLogger(PcDrawer.class);
	private final ObjectsDrawer objectsDrawer;
	private final CanvasWrapper canvas;

	public PcDrawer(ObjectsDrawer objectsDrawer, Canvas canvas) {
		super();
		this.objectsDrawer = objectsDrawer;
		this.canvas = new CanvasWrapper(canvas);
	}

	@Override
	public void newPlayerJoined(Player p) {
		LOGGER.info("Player joined");
	}

	@Override
	public void playerLeftTheGame(Player p) {
		LOGGER.info("Player left");
	}

	@Override
	public void draw() {
		// canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(),
		// canvas.getHeight());
		objectsDrawer.draw(canvas);
	}

	@Override
	public void setNeedsUpdate(boolean b) {
	}

}
