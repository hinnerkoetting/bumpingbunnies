package de.jumpnbump.usecases.viewer.viewer.editingMode;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.Optional;

import de.jumpnbump.usecases.viewer.viewer.actions.CanvasObjectsFinder;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.Background;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;
import de.oetting.bumpingbunnies.model.game.objects.GameObjectWithImage;
import de.oetting.bumpingbunnies.model.game.objects.IcyWall;
import de.oetting.bumpingbunnies.model.game.objects.Jumper;
import de.oetting.bumpingbunnies.model.game.objects.Wall;
import de.oetting.bumpingbunnies.model.game.objects.Water;

public class DeleteModeMouseListener implements ModeMouseListener {

	private final SelectionModeProvider provider;
	private final CanvasObjectsFinder objectsFinder;

	public DeleteModeMouseListener(SelectionModeProvider provider, CanvasObjectsFinder objectsFinder) {
		this.provider = provider;
		this.objectsFinder = objectsFinder;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Optional<? extends GameObjectWithImage> objectOnScreen = objectsFinder.findClickedObject(e);
		List<GameObjectWithImage> selectedObjects = provider.getCurrentSelectedObjects();
		if (objectOnScreen.isPresent()) {
			if (selectedObjects.contains(objectOnScreen.get()))
				deleteObject(objectOnScreen.get());
			else
				provider.setSelectedObject(objectOnScreen);
		} else {
			provider.setSelectedObject(objectOnScreen);
		}
	}

	private void deleteObject(GameObject selectedObject) {
		World world = provider.getWorld();
		if (selectedObject instanceof IcyWall)
			world.removeIcyWall((IcyWall) selectedObject);
		else if (selectedObject instanceof Wall)
			world.removeWall((Wall) selectedObject);
		else if (selectedObject instanceof Jumper)
			world.removeJumper((Jumper) selectedObject);
		else if (selectedObject instanceof Water)
			world.removeWater((Water) selectedObject);
		else if (selectedObject instanceof Background)
			world.removeBackground((Background) selectedObject);
		provider.refreshAll();
	}

	@Override
	public void mousePressed(MouseEvent e) {
	}

	@Override
	public void mouseReleased(MouseEvent e) {
	}

	@Override
	public void mouseEntered(MouseEvent e) {
	}

	@Override
	public void mouseExited(MouseEvent e) {
	}

	@Override
	public void mouseDragged(MouseEvent e) {
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		Optional<? extends GameObject> object = objectsFinder.findClickedObject(e);
		if (object.isPresent())
			provider.setCanvasCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		else
			provider.setCanvasCursor(Cursor.getDefaultCursor());
	}
}
