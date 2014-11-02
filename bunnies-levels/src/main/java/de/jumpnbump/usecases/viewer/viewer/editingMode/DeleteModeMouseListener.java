package de.jumpnbump.usecases.viewer.viewer.editingMode;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.util.Optional;

import de.jumpnbump.usecases.viewer.viewer.actions.CanvasObjectsFinder;
import de.oetting.bumpingbunnies.core.world.World;
import de.oetting.bumpingbunnies.model.game.objects.GameObject;

public class DeleteModeMouseListener implements ModeMouseListener {

	private final SelectionModeProvider provider;
	private final CanvasObjectsFinder objectsFinder;

	public DeleteModeMouseListener(SelectionModeProvider provider, CanvasObjectsFinder objectsFinder) {
		this.provider = provider;
		this.objectsFinder = objectsFinder;
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		Optional<? extends GameObject> objectOnScreen = objectsFinder.findClickedObject(e);
		Optional<? extends GameObject> selectedObject = provider.getCurrentSelectedObject();
		if (objectOnScreen.isPresent() && selectedObject.isPresent()) {
			if (objectOnScreen.get().equals(selectedObject.get()))
				deleteObject(selectedObject.get());
			else
				provider.setSelectedObject(objectOnScreen);
		} else {
			provider.setSelectedObject(objectOnScreen);
		}
	}

	private void deleteObject(GameObject selectedObject) {
		World world = provider.getWorld();
		world.getAllIcyWalls().remove(selectedObject);
		world.getAllJumper().remove(selectedObject);
		world.getAllWaters().remove(selectedObject);
		world.getAllWalls().remove(selectedObject);
		provider.repaintCanvas();
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
