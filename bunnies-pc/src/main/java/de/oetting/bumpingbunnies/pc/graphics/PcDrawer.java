package de.oetting.bumpingbunnies.pc.graphics;

import javafx.scene.canvas.Canvas;
import de.oetting.bumpingbunnies.core.game.graphics.ObjectsDrawer;
import de.oetting.bumpingbunnies.core.graphics.CanvasWrapper;
import de.oetting.bumpingbunnies.core.graphics.Drawer;
import de.oetting.bumpingbunnies.model.game.objects.Player;

public class PcDrawer implements Drawer {

	private final ObjectsDrawer objectsDrawer;
	private final CanvasWrapper canvasWrapper;
	private boolean needsUpdate;

	public PcDrawer(ObjectsDrawer objectsDrawer, Canvas canvas) {
		super();
		this.objectsDrawer = objectsDrawer;
		this.canvasWrapper = new PcCanvasWrapper(canvas);
		needsUpdate = true;
	}

	@Override
	public void newPlayerJoined(Player p) {
		if (!needsUpdate)
			objectsDrawer.newPlayerJoined(p);
	}

	@Override
	public void playerLeftTheGame(Player p) {
		objectsDrawer.playerLeftTheGame(p);
	}

	@Override
	public void draw() {
		Canvas canvas = (Canvas) canvasWrapper.getCanvasImpl();
		if (needsUpdate) {
			objectsDrawer.buildAllDrawables(canvasWrapper, (int) canvas.getWidth(), (int) canvas.getHeight());
			needsUpdate = false;
		}
		canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
		objectsDrawer.draw(canvasWrapper);
	}

	@Override
	public void setNeedsUpdate(boolean b) {
		this.needsUpdate = b;
	}

}
